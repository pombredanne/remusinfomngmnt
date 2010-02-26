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
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.connector.youtube.SiteInspector;
import org.remus.infomngmnt.connector.youtube.YoutubeActivator;
import org.remus.infomngmnt.connector.youtube.preferences.PreferenceInitializer;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.operation.DownloadFileJob;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.core.remote.OperationNotSupportedException;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.StatusCreator;
import org.remus.infomngmnt.video.VideoActivator;

import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.mediarss.MediaCategory;
import com.google.gdata.data.youtube.PlaylistEntry;
import com.google.gdata.data.youtube.PlaylistFeed;
import com.google.gdata.data.youtube.PlaylistLinkEntry;
import com.google.gdata.data.youtube.PlaylistLinkFeed;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaGroup;
import com.google.gdata.util.ServiceException;

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

	private IContainer container;

	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

	private IFile tmpVideoFile;

	private IPreferenceStore preferenceStore;

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
			final IProgressMonitor monitor) throws OperationNotSupportedException {
		throw new OperationNotSupportedException(StatusCreator.newStatus("Adding is not supported"));
	}

	public RemoteObject commit(final SynchronizableObject item2commit,
			final IProgressMonitor monitor) throws RemoteException {
		throw new OperationNotSupportedException(StatusCreator
				.newStatus("Committing is not supported"));
	}

	public void deleteFromRepository(final SynchronizableObject item, final IProgressMonitor monitor)
			throws RemoteException {
		throw new OperationNotSupportedException(StatusCreator
				.newStatus("Deleting is not supported"));

	}

	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainers)
			throws RemoteException {
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
				if (!showOnlyContainers) {
					return buildVideoEntries(container);
				}
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private RemoteObject[] buildVideoEntries(final RemoteContainer container)
			throws RemoteException {
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
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error collecting video-information", e));
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);

	}

	private RemoteObject createRemoteObject(final VideoEntry videoEntry,
			final RemoteContainer container) throws RemoteException {
		/*
		 * Youtube unfortunately has no edited flag when editing video-metadata.
		 * We have to generate our own hash based on the metadata that can be
		 * edited.
		 */
		MessageDigest instance;
		try {
			instance = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new RemoteException(StatusCreator.newStatus("Error creating MD5", e));
		}

		StringWriter sw = new StringWriter();
		YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();
		if (mediaGroup != null) {
			if (mediaGroup.getDescription() != null) {

				sw.append(mediaGroup.getDescription().getPlainTextContent());
			}
			List<MediaCategory> keywordsGroup = mediaGroup.getCategories();
			StringBuilder sb = new StringBuilder();
			if (keywordsGroup != null) {
				for (MediaCategory mediaCategory : keywordsGroup) {

					sb.append(mediaCategory.getContent());
				}
			}

		}
		instance.update(sw.toString().getBytes());
		RemoteObject remoteVideo = InfomngmntFactory.eINSTANCE.createRemoteObject();
		remoteVideo.setHash(asHex(instance.digest()));
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
		RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteContainer.setName(NLS.bind("Subscriptions of {0}", getCredentialProvider()
				.getUserName()));
		remoteContainer.setId(KEY_SUBSCRIPTION_FOLDER);
		remoteContainer.setRepositoryTypeObjectId(KEY_SUBSCRIPTION_FOLDER);
		remoteContainer.setHash(KEY_SUBSCRIPTION_FOLDER);
		return remoteContainer;
	}

	private RemoteContainer buildPlayListFeed() {
		RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteContainer.setName(NLS.bind("Playlist of {0}", getCredentialProvider().getUserName()));
		remoteContainer.setId(KEY_PLAYLIST_FOLDER);
		remoteContainer.setRepositoryTypeObjectId(KEY_PLAYLIST_FOLDER);
		remoteContainer.setHash(KEY_PLAYLIST_FOLDER);
		return remoteContainer;
	}

	private RemoteContainer buildFavoritesFeed() {
		RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteContainer
				.setName(NLS.bind("Favorites of {0}", getCredentialProvider().getUserName()));
		remoteContainer.setId(KEY_FAVORITES_FOLDER);
		remoteContainer.setRepositoryTypeObjectId(KEY_FAVORITES_FOLDER);
		remoteContainer.setHash(KEY_FAVORITES_FOLDER);
		return remoteContainer;
	}

	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) {
		try {
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(VideoActivator.TYPE_ID);
			InformationUnit createNewObject = edit.newInformationUnit();
			edit.setValue(createNewObject, VideoActivator.NODE_NAME_MEDIATYPE, "flv");

			RemoteObject remoteObjectBySynchronizableObject = getRemoteObjectBySynchronizableObject(
					informationUnitListItem, monitor);
			Object wrappedObject = remoteObjectBySynchronizableObject.getWrappedObject();
			if (wrappedObject instanceof VideoEntry) {
				YouTubeMediaGroup mediaGroup = ((VideoEntry) wrappedObject).getMediaGroup();
				if (mediaGroup != null) {
					if (mediaGroup.getDescription() != null) {
						createNewObject.setDescription(mediaGroup.getDescription()
								.getPlainTextContent());
					}
					List<String> keywords = mediaGroup.getKeywords().getKeywords();
					StringBuilder sb = new StringBuilder();
					for (String string : keywords) {
						sb.append(string).append(" ");
					}
					createNewObject.setKeywords(sb.toString());
				}
			}
			return createNewObject;
		} catch (CoreException e) {
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

	@Override
	public IFile getBinaryReferences(final InformationUnitListItem remoteObject,
			final InformationUnit localInfoFragment, final IProgressMonitor monitor)
			throws RemoteException {
		if (VideoActivator.TYPE_ID.equals(localInfoFragment.getType())) {
			SynchronizationMetadata adapter = (SynchronizationMetadata) remoteObject
					.getAdapter(SynchronizationMetadata.class);
			String url = NLS.bind(getPreferences().getString(PreferenceInitializer.VIDEO_HTML_URL),
					getVideoIdFromUrl(adapter.getUrl()));
			IFile tempFile = ResourceUtil.createTempFile();
			try {
				DownloadFileJob downloadWebsiteJob = new DownloadFileJob(new URL(url), tempFile,
						getFileReceiveAdapter());
				downloadWebsiteJob.run(monitor);
				URL downloadUrl = getDownloadUrl(tempFile, true);
				if (downloadUrl == null) {
					throw new RemoteException(StatusCreator
							.newStatus("Error calculating download url."));
				}
				IFile videoFile = ResourceUtil.createTempFile("flv");

				DownloadFileJob downloadVidJob = new DownloadFileJob(downloadUrl, videoFile,
						getFileReceiveAdapter());
				IStatus run = downloadVidJob.run(monitor);

				if (VideoActivator.TYPE_ID.equals(localInfoFragment.getType())) {
					return videoFile;
				}
			} catch (Exception e) {

			}
		}
		return null;
	}

	/**
	 * @throws RemoteException
	 * 
	 */
	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			final IProgressMonitor monitor) throws RemoteException {
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

	@Override
	public String getRepositoryUrl() {
		return NLS.bind(getPreferences().getString(PreferenceInitializer.GDATA_SERVER_URL),
				getCredentialProvider().getUserName());
	}

	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return VideoActivator.TYPE_ID;
	}

	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// do nothing

	}

	private URL getPlaylistUrl() {
		try {
			return new URL(NLS.bind(getPreferences().getString(PreferenceInitializer.PLAYLIST_URL),
					getCredentialProvider().getUserName()));
		} catch (MalformedURLException e) {
			return null;
		}

	}

	private URL getFavoritesUrl() {
		try {
			return new URL(NLS.bind(
					getPreferences().getString(PreferenceInitializer.FAVORITES_URL),
					getCredentialProvider().getUserName()));
		} catch (MalformedURLException e) {
			return null;
		}

	}

	private URL getDownloadUrl(final IFile content, final boolean hd) {
		String url;

		// if (hd) {
		// url = NLS.bind(getPreferences().getString(
		// PreferenceInitializer.HIGH_DEFINITION_DOWNLOAD_URL), this.id, hash);
		// } else {
		// url = NLS.bind(getPreferences().getString(
		// PreferenceInitializer.HIGH_QUALITY_DOWNLOAD_URL), this.id, hash);
		// }
		try {
			Map<String, String> urlMap = SiteInspector.getUrlMap(content);
			if (urlMap.get("22") != null) {
				return new URL(urlMap.get("22"));
			} else if (urlMap.get("34") != null) {
				return new URL(urlMap.get("34"));
			} else if (urlMap.get("5") != null) {
				return new URL(urlMap.get("5"));
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CoreException e) {
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

	@Override
	public boolean onlyDownload() {
		return true;
	}

	@Override
	public boolean hasBinaryReferences() {
		return true;
	}

	@Override
	public IStatus validate() {
		try {
			getYoutubeService().getFeed(getFavoritesUrl(), VideoFeed.class);
		} catch (Exception e) {
			return StatusCreator.newStatus("Error validating connector", e);
		}
		return Status.OK_STATUS;
	}

	private IPreferenceStore getPreferences() {
		if (this.preferenceStore == null) {
			this.preferenceStore = YoutubeActivator.getDefault().getPreferenceStore();
		}
		return this.preferenceStore;
	}

	private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', };

	public static String asHex(final byte hash[]) {
		char buf[] = new char[hash.length * 2];
		for (int i = 0, x = 0; i < hash.length; i++) {
			buf[x++] = HEX_CHARS[(hash[i] >>> 4) & 0xf];
			buf[x++] = HEX_CHARS[hash[i] & 0xf];
		}
		return new String(buf);
	}

}
