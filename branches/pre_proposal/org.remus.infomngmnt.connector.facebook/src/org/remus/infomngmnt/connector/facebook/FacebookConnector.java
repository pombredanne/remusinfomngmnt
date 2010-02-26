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

package org.remus.infomngmnt.connector.facebook;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.util.IdFactory;
import org.remus.infomngmnt.util.StatusCreator;

import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJaxbRestClient;
import com.google.code.facebookapi.ProfileField;
import com.google.code.facebookapi.schema.Album;
import com.google.code.facebookapi.schema.FqlQueryResponse;
import com.google.code.facebookapi.schema.FriendsGetResponse;
import com.google.code.facebookapi.schema.PhotosGetAlbumsResponse;
import com.google.code.facebookapi.schema.User;
import com.google.code.facebookapi.schema.UsersGetInfoResponse;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FacebookConnector extends AbstractExtensionRepository implements IRepository {

	private static final String MY_PROFILE_FOLDER_URL = "http://facebook/myProfileFolder";
	private static final String MY_GALLERY_FOLDER_URL = "http://facebook/myGalleryFolder";
	private static final String MY_FRIENDS_FOLDER_URL = "http://facebook/myFriedsFolder";

	private FacebookJaxbRestClient api;

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
	public RemoteObject commit(final SynchronizableObject item2commit,
			final IProgressMonitor monitor) throws RemoteException {
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
			returnValue.add(buildMyProfileFolder());
			returnValue.add(buildMyPhotoGalleryFolder());
			returnValue.add(buildMyFriendsFolder());
		} else if (MY_FRIENDS_FOLDER_URL.equals(container.getUrl())) {
			returnValue.addAll(buildUserFolders());
		} else if (MY_GALLERY_FOLDER_URL.equals(container.getUrl())) {
			try {
				returnValue.addAll(buildMyGalleryFolders(getApi().users_getLoggedInUser()));
			} catch (FacebookException e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error getting current id of logged in user", e));
			}
		} else if (container instanceof RemoteContainer
				&& container.getWrappedObject() instanceof User) {
			if (!showOnlyContainers) {
				returnValue.add(buildUserProfile(((User) container.getWrappedObject()).getUid()));
			}
			returnValue
					.addAll(buildMyGalleryFolders(((User) container.getWrappedObject()).getUid()));
		} else if (MY_PROFILE_FOLDER_URL.equals(container.getUrl())) {
			if (!showOnlyContainers) {
				try {
					returnValue.add(buildStream());
					returnValue.add(buildUserProfile(getApi().users_getLoggedInUser()));
					returnValue.add(buildNotifications());
				} catch (FacebookException e) {
					throw new RemoteException(StatusCreator.newStatus(
							"Error getting current id of logged in user", e));
				}
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private RemoteObject buildStream() throws RemoteException {
		RemoteObject returnValue = null;
		try {
			String streamQuery = StringUtils
					.join(
							"SELECT post_id, actor_id, target_id, message,comments,likes FROM stream WHERE source_id in (SELECT target_id FROM connection WHERE source_id=\'",
							String.valueOf(getApi().users_getLoggedInUser()), "\')");
			FqlQueryResponse fqlQuery = (FqlQueryResponse) getApi().fql_query(streamQuery);
			JAXBContext newInstance = JAXBContext.newInstance(FqlQueryResponse.class);
			Marshaller createMarshaller = newInstance.createMarshaller();
			ByteArrayOutputStream stringOutputStream = new ByteArrayOutputStream();
			createMarshaller.marshal(fqlQuery, stringOutputStream);
			MessageDigest instance;
			try {
				instance = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				throw new RemoteException(StatusCreator.newStatus("Error initializing MD5", e));
			}
			instance.update(stringOutputStream.toByteArray());
			returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setId(IdFactory.createId());
			returnValue.setUrl(StringUtils.join("http://facebook/mystream"));
			returnValue.setName("My Stream");
			returnValue.setHash(asHex(instance.digest()));
			returnValue.setWrappedObject(fqlQuery);
		} catch (FacebookException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting stream", e));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	private RemoteObject buildNotifications() {
		// TODO Auto-generated method stub
		return null;
	}

	private RemoteObject buildUserProfile(final Long uid) throws RemoteException {
		RemoteObject returnValue;
		try {
			UsersGetInfoResponse usersGetInfo = getApi().users_getInfo(
					Collections.singletonList(uid), EnumSet.allOf(ProfileField.class));
			User user2 = usersGetInfo.getUser().get(0);
			returnValue = InfomngmntFactory.eINSTANCE.createRemoteObject();
			returnValue.setId(String.valueOf(user2.getUid()));
			returnValue.setWrappedObject(user2);
			returnValue.setHash(String.valueOf(user2.getProfileUpdateTime()));
			returnValue.setName(StringUtils.join(user2.getFirstName(), " ", user2.getLastName()));
		} catch (FacebookException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting userdetails", e));
		}

		return returnValue;
	}

	private Collection<? extends RemoteObject> buildMyGalleryFolders(final Long userId)
			throws RemoteException {
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		try {
			PhotosGetAlbumsResponse photosGetAlbums = getApi().photos_getAlbums(userId);
			List<Album> albums = photosGetAlbums.getAlbum();
			for (Album album : albums) {
				RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE
						.createRemoteContainer();
				createRemoteContainer.setId(String.valueOf(album.getAid()));
				createRemoteContainer.setHash(String.valueOf(album.getAid()));
				createRemoteContainer.setName(album.getName());
				createRemoteContainer.setUrl(StringUtils.join("http://facebook/", String
						.valueOf(userId), "/album/", String.valueOf(album.getAid())));
				returnValue.add(createRemoteContainer);
			}
		} catch (FacebookException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting albums", e));
		}
		return returnValue;
	}

	private Collection<? extends RemoteObject> buildUserFolders() throws RemoteException {
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		try {
			FriendsGetResponse friendsGet = getApi().friends_get();
			List<Long> uid = friendsGet.getUid();
			UsersGetInfoResponse usersGetInfo = getApi().users_getInfo(uid,
					Collections.singleton(ProfileField.NAME));
			List<User> user = usersGetInfo.getUser();
			for (User user2 : user) {
				RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE
						.createRemoteContainer();
				createRemoteContainer.setId(String.valueOf(user2.getUid()));
				createRemoteContainer.setHash(String.valueOf(user2.getUid()));
				createRemoteContainer.setName(user2.getName());
				createRemoteContainer.setUrl(StringUtils.join("http://facebook/user/", String
						.valueOf(user2.getUid())));
				createRemoteContainer.setWrappedObject(user2);
				returnValue.add(createRemoteContainer);
			}
		} catch (FacebookException e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting user-details", e));
		}
		return returnValue;

	}

	private RemoteObject buildMyPhotoGalleryFolder() {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setUrl(MY_GALLERY_FOLDER_URL);
		createRemoteContainer.setId(MY_GALLERY_FOLDER_URL);
		createRemoteContainer.setHash(MY_GALLERY_FOLDER_URL);
		createRemoteContainer.setName("My Photo Galleries");
		return createRemoteContainer;
	}

	private RemoteObject buildMyFriendsFolder() {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setUrl(MY_FRIENDS_FOLDER_URL);
		createRemoteContainer.setId(MY_FRIENDS_FOLDER_URL);
		createRemoteContainer.setHash(MY_FRIENDS_FOLDER_URL);
		createRemoteContainer.setName("My Friends");
		return createRemoteContainer;
	}

	private RemoteObject buildMyProfileFolder() {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setUrl(MY_PROFILE_FOLDER_URL);
		createRemoteContainer.setId(MY_PROFILE_FOLDER_URL);
		createRemoteContainer.setHash(MY_PROFILE_FOLDER_URL);
		createRemoteContainer.setName("My Profile");
		return createRemoteContainer;
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

	@Override
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		return super.getPrefetchedInformationUnit(remoteObject);
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
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getTypeIdByObject(org.remus
	 * .infomngmnt.RemoteObject)
	 */
	public String getTypeIdByObject(final RemoteObject remoteObject) {
		if (!(remoteObject instanceof RemoteContainer)
				&& remoteObject.getWrappedObject() instanceof User) {
			return ContactActivator.TYPE_ID;
		}
		return null;
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

	public FacebookJaxbRestClient getApi() {
		if (this.api == null) {
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
		}
		return this.api = ((FacebookCredentialProvider) getCredentialProvider()).getFacebook();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#reset()
	 */
	public void reset() {
		this.api = null;
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
