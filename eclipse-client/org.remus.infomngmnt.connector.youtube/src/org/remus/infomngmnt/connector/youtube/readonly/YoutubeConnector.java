/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.connector.youtube.readonly;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.osgi.util.NLS;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.PlaylistEntry;
import com.google.gdata.data.youtube.PlaylistFeed;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.util.ServiceException;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.common.core.md5.MD5;
import org.remus.infomngmnt.connector.youtube.SiteInspector;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.operation.DownloadFileJob;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.video.VideoActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class YoutubeConnector extends AbstractExtensionRepository {

	private YouTubeService youtubeService;

	public static final String KEY_FAVORITES_FOLDER = "KEY_FAVORITES_FOLDER"; //$NON-NLS-1$

	public static final String KEY_PLAYLIST_FOLDER = "KEY_PLAYLIST_FOLDER"; //$NON-NLS-1$

	public static final String KEY_SUBSCRIPTION_FOLDER = "KEY_SUBSCRIPTION_FOLDER"; //$NON-NLS-1$

	public static final String KEY_PLAYLIST = "KEY_PLAYLIST"; //$NON-NLS-1$

	public static final String KEY_VIDEO = "KEY_VIDEO"; //$NON-NLS-1$

	public static final String INTERNAL_PROTOCOL = "http"; //$NON-NLS-1$

	MD5 md5 = new MD5();

	private IContainer container;

	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

	/**
	 * The name of the server hosting the YouTube GDATA feeds
	 */
	public static final String YOUTUBE_GDATA_SERVER = "http://gdata.youtube.com";

	public static final String VIDEO_HTML_URL = "http://www.youtube.com/watch?v="; //$NON-NLS-1$

	/**
	 * The prefix of the User Feeds
	 */
	public static final String USER_FEED_PREFIX = YOUTUBE_GDATA_SERVER + "/feeds/api/users/";

	/**
	 * The URL suffix of the test user's playlists feed
	 */
	public static final String PLAYLISTS_FEED_SUFFIX = "/playlists";

	/**
	 * The URL suffix of the test user's favorites feed
	 */
	public static final String FAVORITES_FEED_SUFFIX = "/favorites";

	private IFile tmpVideoFile;

	/**
	 * @return the youtubeService
	 */
	public YouTubeService getYoutubeService() {
		if (this.youtubeService == null) {
			this.youtubeService = new YouTubeService("gdataSample-YouTube-1");
		}
		return this.youtubeService;
	}

	public RemoteObject addToRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	public String commit(final SynchronizableObject item2commit, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainers) {
		List<RemoteObject> returnValue = new LinkedList<RemoteObject>();
		if (container instanceof RemoteRepository) {
			IProgressMonitor sub = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);
			RemoteContainer favoritesFeed = buildFavoritesFeed();
			RemoteContainer playListFeed = buildPlayListFeed();
			RemoteContainer subscriptionFeed = buildSubscriptionFeed();
			setInternalUrl(favoritesFeed, container);
			setInternalUrl(playListFeed, container);
			setInternalUrl(subscriptionFeed, container);
			return new RemoteContainer[] { favoritesFeed, playListFeed, subscriptionFeed };
		} else if (container instanceof RemoteContainer) {
			if (KEY_PLAYLIST_FOLDER.equals(container.getId())) {
				return buildPlayListCollection(container);
			} else {
				return buildVideoEntries(container);
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private RemoteObject[] buildVideoEntries(final RemoteContainer container) {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		if (KEY_FAVORITES_FOLDER.equals(container.getId())) {
			try {
				VideoFeed videoFeed = getYoutubeService().getFeed(getFavoritesUrl(),
						VideoFeed.class);
				List<VideoEntry> videoEntries = videoFeed.getEntries();
				for (VideoEntry videoEntry : videoEntries) {
					returnValue.add(createRemoteObject(videoEntry, container));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (KEY_PLAYLIST.equals(container.getRepositoryTypeObjectId())) {
			PlaylistLinkEntry wrappedObject = (PlaylistLinkEntry) container.getWrappedObject();
			try {
				PlaylistFeed playlistFeed = wrappedObject.getService().getFeed(
						new URL(wrappedObject.getFeedUrl()), PlaylistFeed.class);
				List<PlaylistEntry> entries = playlistFeed.getEntries();
				for (PlaylistEntry playlistEntry : entries) {
					returnValue.add(createRemoteObject(playlistEntry, container));
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);

	}

	private RemoteObject createRemoteObject(final VideoEntry videoEntry,
			final RemoteContainer container) {
		RemoteObject remoteVideo = InfomngmntFactory.eINSTANCE.createRemoteObject();
		remoteVideo.setHash(videoEntry.getId());
		remoteVideo.setId(SiteInspector.getId(videoEntry.getHtmlLink().getHref()));
		remoteVideo.setName(videoEntry.getTitle().getPlainText());
		remoteVideo.setRepositoryTypeObjectId(KEY_VIDEO);
		remoteVideo.setWrappedObject(videoEntry);
		setInternalUrl(remoteVideo, container);
		return remoteVideo;
	}

	private RemoteObject[] buildPlayListCollection(final RemoteContainer container) {
		List<RemoteContainer> returnValue = new LinkedList<RemoteContainer>();
		try {
			PlaylistLinkFeed playlistLinkFeed = getYoutubeService().getFeed(getPlaylistUrl(),
					PlaylistLinkFeed.class);
			List<PlaylistLinkEntry> playlistEntries = playlistLinkFeed.getEntries();
			for (PlaylistLinkEntry playlistLinkEntry : playlistEntries) {
				RemoteContainer playListEntryContainer = InfomngmntFactory.eINSTANCE
						.createRemoteContainer();
				playListEntryContainer.setId(playlistLinkEntry.getId());
				playListEntryContainer.setName(playlistLinkEntry.getTitle().getPlainText());
				playListEntryContainer.setRepositoryTypeObjectId(KEY_PLAYLIST);
				playListEntryContainer.setWrappedObject(playlistLinkEntry);
				setInternalUrl(playListEntryContainer, container);
				returnValue.add(playListEntryContainer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue.toArray(new RemoteContainer[returnValue.size()]);
	}

	private RemoteContainer buildSubscriptionFeed() {
		this.md5.Update(KEY_SUBSCRIPTION_FOLDER);
		RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteContainer.setName(NLS.bind("Subscriptions of {0}", getCredentialProvider()
				.getUserName()));
		remoteContainer.setId(KEY_SUBSCRIPTION_FOLDER);
		remoteContainer.setRepositoryTypeObjectId(KEY_SUBSCRIPTION_FOLDER);
		remoteContainer.setHash(this.md5.asHex());
		return remoteContainer;
	}

	private RemoteContainer buildPlayListFeed() {
		this.md5.Update(KEY_PLAYLIST_FOLDER);
		RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteContainer.setName(NLS.bind("Playlist of {0}", getCredentialProvider().getUserName()));
		remoteContainer.setId(KEY_PLAYLIST_FOLDER);
		remoteContainer.setRepositoryTypeObjectId(KEY_PLAYLIST_FOLDER);
		remoteContainer.setHash(this.md5.asHex());
		return remoteContainer;
	}

	private RemoteContainer buildFavoritesFeed() {
		this.md5.Update(KEY_FAVORITES_FOLDER);
		RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteContainer
				.setName(NLS.bind("Favorites of {0}", getCredentialProvider().getUserName()));
		remoteContainer.setId(KEY_FAVORITES_FOLDER);
		remoteContainer.setRepositoryTypeObjectId(KEY_FAVORITES_FOLDER);
		remoteContainer.setHash(this.md5.asHex());
		return remoteContainer;
	}

	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) {
		SynchronizationMetadata adapter = (SynchronizationMetadata) informationUnitListItem
				.getAdapter(SynchronizationMetadata.class);
		String url = VIDEO_HTML_URL + getVideoIdFromUrl(adapter.getUrl());
		IFile tempFile = ResourceUtil.createTempFile();
		try {
			DownloadFileJob downloadWebsiteJob = new DownloadFileJob(new URL(url), tempFile,
					getFileReceiveAdapter());
			downloadWebsiteJob.run(monitor);
			String hash = SiteInspector.getHash(tempFile);
			String youtTubeId = SiteInspector.getId(url);
			this.tmpVideoFile = ResourceUtil.createTempFile("flv");
			DownloadFileJob downloadVidJob = new DownloadFileJob(getDownloadUrl(youtTubeId, hash),
					this.tmpVideoFile, getFileReceiveAdapter());
			downloadVidJob.run(monitor);
			AbstractCreationFactory abstractCreationFactory = InformationExtensionManager
					.getInstance().getInfoTypeByType(VideoActivator.TYPE_ID).getCreationFactory();
			InformationUnit createNewObject = abstractCreationFactory.createNewObject();
			InformationUtil.getChildByType(createNewObject, VideoActivator.NODE_NAME_MEDIATYPE)
					.setStringValue("flv");

			// runnable.run(monitor);
			tempFile.delete(true, monitor);
			return createNewObject;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void setInternalUrl(final RemoteObject remoteObject, final RemoteContainer parentObject) {
		String url = null;
		if (parentObject != null && !(parentObject instanceof RemoteRepository)) {
			url = parentObject.getUrl();
		} else {
			url = INTERNAL_PROTOCOL + "://youtube/";
		}
		if (KEY_FAVORITES_FOLDER.equals(remoteObject.getRepositoryTypeObjectId())) {
			url += KEY_FAVORITES_FOLDER + "/";
		} else if (KEY_PLAYLIST_FOLDER.equals(remoteObject.getRepositoryTypeObjectId())) {
			url += KEY_PLAYLIST_FOLDER + "/";
		} else if (KEY_SUBSCRIPTION_FOLDER.equals(remoteObject.getRepositoryTypeObjectId())) {
			url += KEY_PLAYLIST_FOLDER + "/";
		} else if (KEY_PLAYLIST.equals(remoteObject.getRepositoryTypeObjectId())) {
			url += remoteObject.getName() + "/";
		} else if (KEY_VIDEO.equals(remoteObject.getRepositoryTypeObjectId())) {
			url += "?" + remoteObject.getId();
		}
		remoteObject.setUrl(url);
	}

	private String getVideoIdFromUrl(final String url) {
		try {
			return new URL(url).getQuery();
		} catch (MalformedURLException e) {

		}
		return null;
	}

	private String getExternalUrl(final String url) {
		try {
			URL url2 = new URL(url);
			String path = url2.getPath();
			String[] split = path.split("/");
			if (split.length > 0) {
				String string = split[0];
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public IFile[] getBinaryReferences() {
		return new IFile[] { this.tmpVideoFile };
	}

	/**
	 * 
	 */
	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			final IProgressMonitor monitor) {
		String url = object.getSynchronizationMetaData().getUrl();
		try {
			URL url2 = new URL(url);
			String path = url2.getPath();
			String[] origSplit = path.split("/");
			String[] split = new String[origSplit.length - 1];
			System.arraycopy(origSplit, 1, split, 0, origSplit.length - 1);
			if (KEY_PLAYLIST_FOLDER.equals(split[0])) {
				RemoteContainer buildPlayListFeed = buildPlayListFeed();
				setInternalUrl(buildPlayListFeed, getRepositoryById(object
						.getSynchronizationMetaData().getRepositoryId()));
				if (split.length > 1) {
					String playListName = split[1];
					RemoteObject[] children = getChildren(monitor, buildPlayListFeed, false);
					for (RemoteObject remoteObject : children) {
						if (remoteObject.getName().equals(playListName)) {
							if (url2.getQuery() != null) {
								RemoteObject[] children2 = getChildren(monitor,
										(RemoteContainer) remoteObject, false);
								for (RemoteObject remoteObject2 : children2) {
									if (remoteObject2.getId().equals(url2.getQuery())) {
										return remoteObject2;
									}
								}
								return null;
							} else {
								return remoteObject;
							}
						} else {
							return null;
						}
					}
				} else {
					return buildPlayListFeed;
				}
			} else if (KEY_FAVORITES_FOLDER.equals(split[0])) {
				RemoteContainer buildFavoritesFeed = buildFavoritesFeed();
				setInternalUrl(buildFavoritesFeed, getRepositoryById(object
						.getSynchronizationMetaData().getRepositoryId()));
				if (url2.getQuery() != null) {
					RemoteObject[] children2 = getChildren(monitor, buildFavoritesFeed, false);
					for (RemoteObject remoteObject2 : children2) {
						if (remoteObject2.getId().equals(url2.getQuery())) {
							return remoteObject2;
						}
					}
					return null;
				} else {
					return buildFavoritesFeed;
				}
			} else if (KEY_SUBSCRIPTION_FOLDER.equals(split[0])) {
				RemoteContainer buildSubscriptionFeed = buildSubscriptionFeed();
				setInternalUrl(buildSubscriptionFeed, getRepositoryById(object
						.getSynchronizationMetaData().getRepositoryId()));
				if (url2.getQuery() != null) {
					RemoteObject[] children2 = getChildren(monitor, buildSubscriptionFeed, false);
					for (RemoteObject remoteObject2 : children2) {
						if (remoteObject2.getId().equals(url2.getQuery())) {
							return remoteObject2;
						}
					}
					return null;
				} else {
					return buildSubscriptionFeed;
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getRepositoryUrl() {
		return YoutubeConnector.USER_FEED_PREFIX + getCredentialProvider().getUserName()
				+ YoutubeConnector.PLAYLISTS_FEED_SUFFIX;
	}

	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return VideoActivator.TYPE_ID;
	}

	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// do nothing

	}

	private URL getPlaylistUrl() {
		try {
			return new URL(USER_FEED_PREFIX + getCredentialProvider().getUserName()
					+ PLAYLISTS_FEED_SUFFIX);
		} catch (MalformedURLException e) {
			return null;
		}

	}

	private URL getFavoritesUrl() {
		try {
			return new URL(USER_FEED_PREFIX + getCredentialProvider().getUserName()
					+ FAVORITES_FEED_SUFFIX);
		} catch (MalformedURLException e) {
			return null;
		}

	}

	private URL getDownloadUrl(final String id, final String hash) {
		String url = "http://www.youtube.com/get_video?video_id=" + id + "&t=" + hash + "&fmt=22";
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private IRetrieveFileTransferContainerAdapter getFileReceiveAdapter() {
		if (this.container == null) {
			try {
				this.container = ContainerFactory.getDefault().createContainer();
			} catch (final ContainerCreateException e) {
				throw new RuntimeException("Error initializing sync-container", e);
			}
			this.fileReceiveAdapter = (IRetrieveFileTransferContainerAdapter) this.container
					.getAdapter(IRetrieveFileTransferContainerAdapter.class);
		}
		return this.fileReceiveAdapter;
	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public IStatus validate() {
		// TODO Auto-generated method stub
		return null;
	}

}
