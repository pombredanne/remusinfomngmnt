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

package org.remus.infomngmnt.connector.flickr;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Exif;
import com.aetrion.flickr.photos.Extras;
import com.aetrion.flickr.photos.Note;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photosets.Photoset;
import com.aetrion.flickr.photosets.Photosets;
import com.aetrion.flickr.photosets.PhotosetsInterface;
import com.aetrion.flickr.tags.Tag;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.operation.DownloadFileJob;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.comments.ShapableInfoDelegate;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FlickrConnector extends AbstractExtensionRepository implements IRepository {

	private Flickr api;

	public static final String FLICKR_URL = "http://flickr.com/"; //$NON-NLS-1$

	public static final String ID_SET_COLLECTION = "ID_SET_COLLECTION"; //$NON-NLS-1$

	public static final String ID_SET = "ID_SET"; //$NON-NLS-1$

	public static final String ID_GROUP_COLLECTION = "ID_GROUP_COLLECTION";

	public static final String ID_FAVORITES = "ID_FAVORITES";

	/**
	 * 
	 */
	public FlickrConnector() {
		// TODO Auto-generated constructor stub
	}

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	private IContainer container;

	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

	private IFile tmpFile;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#addToRepository(org.remus
	 * .infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject addToRepository(final SynchronizableObject item,
			final IProgressMonitor monitor) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#commit(org.remus.infomngmnt
	 * .SynchronizableObject, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public String commit(final SynchronizableObject item2commit, final IProgressMonitor monitor)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#deleteFromRepository(org
	 * .remus.infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void deleteFromRepository(final SynchronizableObject item, final IProgressMonitor monitor)
			throws RemoteException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getChildren(org.eclipse.
	 * core.runtime.IProgressMonitor, org.remus.infomngmnt.RemoteContainer,
	 * boolean)
	 */
	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainers)
			throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		if (container instanceof RemoteRepository) {
			returnValue.add(buildPhotoSetContainer(monitor));
			returnValue.add(buildFavoritesContainer(monitor));
		} else {
			if (ID_SET_COLLECTION.equals(container.getRepositoryTypeObjectId())) {
				returnValue.addAll(buildPhotoSets(monitor));
			} else if (ID_FAVORITES.equals(container.getRepositoryTypeObjectId())
					&& !showOnlyContainers) {
				returnValue.addAll(buildFavoriteEntries());
			} else if (ID_SET.equals(container.getRepositoryTypeObjectId()) && !showOnlyContainers) {
				returnValue.addAll(buildPhotoSetEntries(container));
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private List<RemoteObject> buildPhotoSetEntries(final RemoteContainer container)
			throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		String id2 = container.getId();
		try {
			PhotoList photos = getApi().getPhotosetsInterface().getPhotos(id2,
					Collections.singleton(Extras.LAST_UPDATE), Flickr.PRIVACY_LEVEL_NO_FILTER,
					Integer.MAX_VALUE, 1);
			for (Object object : photos) {
				Photo photo = (Photo) object;
				// we have to do a second request to get the original secret

				returnValue.add(buildPhoto(photo, container.getUrl()));
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus(
					"Error while loading photos from a set", e));
		}
		return returnValue;
	}

	private RemoteObject buildPhoto(final Photo photo, final String baseUrl) {
		RemoteObject remoteObject = InfomngmntFactory.eINSTANCE.createRemoteObject();
		remoteObject.setHash(String.valueOf(photo.getLastUpdate().getTime()));
		remoteObject.setId(photo.getId());
		remoteObject.setName(photo.getTitle());
		if (photo.getOriginalSecret() != null && photo.getOriginalSecret().length() > 0) {
			remoteObject.setUrl(baseUrl + "/" + photo.getId() + "." + photo.getOriginalSecret());
		} else {
			remoteObject.setUrl(baseUrl + "/" + photo.getId() + "." + photo.getSecret());
		}
		remoteObject.setWrappedObject(photo);
		return remoteObject;

	}

	private List<RemoteObject> buildFavoriteEntries() throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		try {
			PhotoList photos = getApi().getFavoritesInterface().getList(
					getFlickrCredentialProvider().getInternalId(), Integer.MAX_VALUE, 1,
					Collections.singleton(Extras.LAST_UPDATE));
			for (Object object : photos) {
				Photo photo = (Photo) object;
				RemoteObject remoteObject = InfomngmntFactory.eINSTANCE.createRemoteObject();
				remoteObject.setHash(String.valueOf(photo.getLastUpdate().getTime()));
				remoteObject.setId(photo.getId());
				remoteObject.setName(photo.getTitle());
				remoteObject.setUrl(FLICKR_URL + ID_FAVORITES + "/" + photo.getId());
				remoteObject.setWrappedObject(photo);
				returnValue.add(remoteObject);
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus(
					"Error while loading photos from a set", e));
		}
		return returnValue;
	}

	private RemoteContainer buildFavoritesContainer(final IProgressMonitor monitor) {
		RemoteContainer container = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		container.setUrl(FLICKR_URL + ID_FAVORITES);
		container.setRepositoryTypeObjectId(ID_FAVORITES);
		container.setName("My Favorites");
		container.setHash(ID_FAVORITES);
		return container;
	}

	@SuppressWarnings("unchecked")
	private List<RemoteContainer> buildPhotoSets(final IProgressMonitor monitor)
			throws RemoteException {
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		PhotosetsInterface photosetsInterface = getApi().getPhotosetsInterface();
		try {
			Photosets list = photosetsInterface.getList(getFlickrCredentialProvider()
					.getInternalId());
			Collection photosets = list.getPhotosets();
			for (Object object : photosets) {
				returnValue.add(buildPhotoSet((Photoset) object));
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus("Error collecting data of photosets",
					e));
		}
		return returnValue;

	}

	private RemoteContainer buildPhotoSet(final Photoset object) {
		Photoset photoset = object;
		RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		remoteContainer.setRepositoryTypeObjectId(ID_SET);
		remoteContainer.setWrappedObject(photoset);
		remoteContainer.setId(photoset.getId());
		remoteContainer.setName(photoset.getTitle());
		remoteContainer.setUrl(FLICKR_URL + ID_SET_COLLECTION + "/" + photoset.getId());
		return remoteContainer;
	}

	private RemoteContainer buildPhotoSetContainer(final IProgressMonitor monitor) {
		RemoteContainer container = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		container.setUrl(FLICKR_URL + ID_SET_COLLECTION);
		container.setRepositoryTypeObjectId(ID_SET_COLLECTION);
		container.setName("My Sets");
		container.setHash(ID_SET_COLLECTION);
		return container;
	}

	@Override
	public IFile[] getBinaryReferences(final InformationUnitListItem remoteListItem,
			final IProgressMonitor monitor) throws RemoteException {
		return new IFile[] { this.tmpFile };

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getFullObject(org.remus.
	 * infomngmnt.InformationUnitListItem,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InformationUnit getFullObject(final InformationUnitListItem informationUnitListItem,
			final IProgressMonitor monitor) throws RemoteException {
		RemoteObject remoteObjectBySynchronizableObject = getRemoteObjectBySynchronizableObject(
				informationUnitListItem, monitor);
		Object wrappedObject = remoteObjectBySynchronizableObject.getWrappedObject();
		if (wrappedObject instanceof Photo) {
			Photo currentPhoto = (Photo) wrappedObject;
			IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
					getTypeIdByObject(remoteObjectBySynchronizableObject));
			if (infoTypeByType != null) {
				InformationUnit informationUnit = infoTypeByType.getCreationFactory()
						.createNewObject();

				informationUnit.setLabel(currentPhoto.getTitle());
				/*
				 * Collecting the tags of the photo
				 */
				Collection tags = currentPhoto.getTags();
				List<String> tagValues = new ArrayList<String>();
				for (Object object : tags) {
					Tag tag = (Tag) object;
					tagValues.add(tag.getValue());
				}
				informationUnit.setKeywords(StringUtils.join(tagValues, " "));
				informationUnit.setDescription(currentPhoto.getDescription());
				InformationUnit originalFilePath = InformationUtil.getChildByType(informationUnit,
						ImagePlugin.ORIGINAL_FILEPATH);
				originalFilePath.setStringValue("flickr." + currentPhoto.getOriginalFormat());

				try {
					/*
					 * Grab the EXIF-Data
					 */
					Collection exifs = getApi().getPhotosInterface().getExif(currentPhoto.getId(),
							currentPhoto.getSecret());
					if (exifs.size() > 0) {
						InformationUnit exifNode = InformationUtil
								.createNew(ImagePlugin.NODE_NAME_EXIF);
						for (Object object : exifs) {
							Exif exif = (Exif) object;
							String stringValue = exif.getClean();
							if (stringValue == null) {
								stringValue = exif.getRaw();
							}
							if (stringValue != null) {
								InformationUnit exifDataNode = InformationUtil.createNew(exif
										.getLabel(), stringValue);
								exifNode.getChildValues().add(exifDataNode);
							}
						}
						informationUnit.getChildValues().add(exifNode);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String originalUrl;
				try {
					originalUrl = currentPhoto.getOriginalUrl();
				} catch (FlickrException e) {
					originalUrl = currentPhoto.getLargeUrl();
				}
				IFile tempFile = ResourceUtil.createTempFile(currentPhoto.getOriginalFormat());
				DownloadFileJob job;
				try {
					job = new DownloadFileJob(new URL(originalUrl), tempFile,
							getFileReceiveAdapter());
					IStatus run = job.run(monitor);
					if (run.isOK()) {
						InputStream contents = tempFile.getContents();
						ImageData[] load = new ImageLoader().load(contents);
						int width = load[0].width;
						int height = load[0].height;
						InformationUtil
								.getChildByType(informationUnit, ImagePlugin.NODE_NAME_WIDTH)
								.setLongValue(width);
						InformationUtil.getChildByType(informationUnit,
								ImagePlugin.NODE_NAME_HEIGHT).setLongValue(height);
						StreamCloser.closeStreams(contents);
						this.tmpFile = tempFile;
						/*
						 * now we can grab all the notes.
						 */
						EditingDomain tmpEditingdomain = EditingUtil.getInstance()
								.createNewEditingDomain();
						Collection notes = currentPhoto.getNotes();
						if (notes.size() > 0) {
							/*
							 * Unfortunately the nodes bounds are relative to
							 * the medium file. We have to download the medium
							 * file to get its size to transform all notes to
							 * the downloaded image size..sigh
							 */
							IFile mediumFile = ResourceUtil.createTempFile(currentPhoto
									.getOriginalFormat());
							job = new DownloadFileJob(new URL(currentPhoto.getMediumUrl()),
									mediumFile, getFileReceiveAdapter());
							IStatus runMedium = job.run(monitor);
							if (runMedium.isOK()) {
								InputStream mediumContents = mediumFile.getContents();
								ImageData[] load2 = new ImageLoader().load(mediumContents);
								int width2 = load2[0].width;
								int height2 = load2[0].height;
								StreamCloser.closeStreams(mediumContents);
								mediumFile.delete(true, monitor);
								for (Object object : notes) {
									Note note = (Note) object;
									Rectangle bounds = note.getBounds();
									InformationUnit createNew = InformationUtil
											.createNew(ImagePlugin.NODE_NAME_LINK);
									ShapableInfoDelegate shapableInfoDelegate = new ShapableInfoDelegate(
											createNew, new Dimension(width2, height2),
											tmpEditingdomain);
									shapableInfoDelegate.setLocation(bounds.getLocation());
									shapableInfoDelegate.setSize(bounds.getSize());
									shapableInfoDelegate.setText(note.getText());
									shapableInfoDelegate.dispose();
									InformationUtil.getChildByType(informationUnit,
											ImagePlugin.NODE_NAME_LINKS).getChildValues().add(
											createNew);
								}
							}
						}

					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return informationUnit;
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.core.remote.IRepository#
	 * getRemoteObjectBySynchronizableObject
	 * (org.remus.infomngmnt.SynchronizableObject,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public RemoteObject getRemoteObjectBySynchronizableObject(final SynchronizableObject object,
			final IProgressMonitor monitor) throws RemoteException {
		String url = object.getSynchronizationMetaData().getUrl();
		Path path = new Path(url);
		try {
			if (path.segmentCount() == 1) {
				return getRepositoryById(object.getSynchronizationMetaData().getRepositoryId());
			} else if (path.segmentCount() == 2 && ID_SET_COLLECTION.equals(path.lastSegment())) {
				return buildPhotoSetContainer(monitor);
			} else if (path.segmentCount() == 2 && ID_FAVORITES.equals(path.lastSegment())) {
				return buildFavoritesContainer(monitor);
			} else if (path.segmentCount() == 3 && ID_SET_COLLECTION.equals(path.segment(1))) {
				Photoset info = getApi().getPhotosetsInterface().getInfo(path.lastSegment());
				return buildPhotoSet(info);
			} else if (path.segmentCount() == 3 && ID_FAVORITES.equals(path.segment(1))) {
				String[] split = path.lastSegment().split("\\.");
				Photo photo = getApi().getPhotosInterface().getInfo(split[0], split[1]);
				return buildPhoto(photo, path.removeLastSegments(1).toString());
			} else if (path.segmentCount() == 4 && ID_SET_COLLECTION.equals(path.segment(1))) {
				String[] split = path.lastSegment().split("\\.");
				Photo photo = getApi().getPhotosInterface().getInfo(split[0], split[1]);
				return buildPhoto(photo, path.removeLastSegments(1).toString());
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus(
					"Error resolving remoteobject from local object", e));
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#getRepositoryUrl()
	 */
	public String getRepositoryUrl() {
		return FLICKR_URL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getTypeIdByObject(org.remus
	 * .infomngmnt.RemoteObject)
	 */
	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return ImagePlugin.TYPE_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#login(org.remus.infomngmnt
	 * .core.remote.ILoginCallBack, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#reset()
	 */
	public void reset() {

		this.api = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#validate()
	 */
	public IStatus validate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasBinaryReferences() {
		return true;
	}

	private Flickr getApi() {
		if (this.api == null) {
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
			this.api = ((FlickrCredentials) getCredentialProvider()).getFlickr();
		}
		return this.api;
	}

	private FlickrCredentials getFlickrCredentialProvider() {
		return (FlickrCredentials) getCredentialProvider();
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

}
