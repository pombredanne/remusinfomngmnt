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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.photos.Extras;
import com.aetrion.flickr.photos.Photo;
import com.aetrion.flickr.photos.PhotoList;
import com.aetrion.flickr.photosets.Photoset;
import com.aetrion.flickr.photosets.Photosets;
import com.aetrion.flickr.photosets.PhotosetsInterface;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.image.ImagePlugin;

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
				RemoteObject remoteObject = InfomngmntFactory.eINSTANCE.createRemoteObject();
				remoteObject.setHash(String.valueOf(photo.getLastUpdate().getTime()));
				remoteObject.setId(photo.getId());
				remoteObject.setName(photo.getTitle());
				remoteObject.setUrl(container.getUrl() + "/" + photo.getId());
				remoteObject.setWrappedObject(photo);
				returnValue.add(remoteObject);
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus(
					"Error while loading photos from a set", e));
		}
		return returnValue;
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
				Photoset photoset = (Photoset) object;
				RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE
						.createRemoteContainer();
				remoteContainer.setRepositoryTypeObjectId(ID_SET);
				remoteContainer.setWrappedObject(photoset);
				remoteContainer.setId(photoset.getId());
				remoteContainer.setName(photoset.getTitle());
				remoteContainer.setUrl(FLICKR_URL + ID_SET_COLLECTION + "/" + photoset.getId());
				returnValue.add(remoteContainer);
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus("Error collecting data of photosets",
					e));
		}
		return returnValue;

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
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		Object wrappedObject = remoteObject.getWrappedObject();
		if (wrappedObject instanceof Photo) {
			Photo currentPhoto = (Photo) wrappedObject;
			IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
					getTypeIdByObject(remoteObject));
			if (infoTypeByType != null) {
				InformationUnit informationUnit = infoTypeByType.getCreationFactory()
						.createNewObject();
				informationUnit.setLabel(currentPhoto.getTitle());
				informationUnit.setKeywords(StringUtils.join(currentPhoto.getTags(), " "));
				informationUnit.setDescription(currentPhoto.getDescription());
				InformationUnit originalFilePath = InformationUtil.getChildByType(informationUnit,
						ImagePlugin.ORIGINAL_FILEPATH);
				originalFilePath.setStringValue("flickr." + currentPhoto.getOriginalFormat());
				return informationUnit;
			}
		}
		return null;
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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

}
