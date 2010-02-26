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

package org.remus.infomngmnt.connector.slideshare;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.slidesharejava.Constants;
import net.sf.slidesharejava.SlideShareException;
import net.sf.slidesharejava.Slideshare;
import net.sf.slidesharejava.beans.Contact;
import net.sf.slidesharejava.beans.ConverstionState;
import net.sf.slidesharejava.beans.Group;
import net.sf.slidesharejava.beans.SearchResult;
import net.sf.slidesharejava.beans.Slideshow;
import net.sf.slidesharejava.beans.SlideshowTag;
import net.sf.slidesharejava.beans.User;
import net.sf.slidesharejava.beans.UserGroup;
import net.sf.slidesharejava.beans.UserTag;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.filetransfer.IRetrieveFileTransferContainerAdapter;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.operation.DownloadFileJob;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.onlineresource.OnlineResourceActivator;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.Proxy;
import org.remus.infomngmnt.util.ProxyUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SlideshareConnector extends AbstractExtensionRepository implements IRepository {

	private static final String URL_MYCONTACTS = "http://slideshare.net/mycontacts";
	private static final String URL_MYGROUPS = "http://slideshare.net/mygroups";
	private static final String URL_MYSLIDES = "http://slideshare.net/myslides/";
	private static final String URL_MYSEARCHES = "http://slideshare.net/mysearches/";
	private static final String URL_GROUP = "http://slideshare.net/group/";
	private static final String URL_CONTACTS = "http://slideshare.net/contacts/";
	private static final String URL_SLIDESHARE_ID = "http://slideshare.net/id/";

	public static final String ID_FRAGMENT = "id"; //$NON-NLS-1$
	public static final String USERTAGS_FRAGMENT = "usertags"; //$NON-NLS-1$
	public static final String MYSEARCHES_FRAGMENT = "mysearches"; //$NON-NLS-1$
	public static final String MYSLIDES_FRAGMENT = "myslides"; //$NON-NLS-1$
	public static final String GROUP_FRAGMENT = "group"; //$NON-NLS-1$
	public static final String CONTACT_FRAGMENT = "contacts"; //$NON-NLS-1$
	private static final String URL_MY_SLIDESHARE_ID = URL_MYSLIDES + ID_FRAGMENT;

	/**
	 * 
	 */
	public SlideshareConnector() {
		// TODO Auto-generated constructor stub
	}

	private IContainer container;
	private IRetrieveFileTransferContainerAdapter fileReceiveAdapter;

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
		// not supported
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
		// not supported
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
		if (item instanceof InformationUnitListItem) {
			String url = item.getSynchronizationMetaData().getUrl();
			Path path = new Path(url);
			if (ID_FRAGMENT.equals(path.segment(path.segmentCount() - 2))
					&& MYSLIDES_FRAGMENT.equals(path.segment(path.segmentCount() - 3))) {
				try {
					Slideshow slideShow = getApi().getSlideShow(path.lastSegment(), null, true);
					SlideshowTag[] tags = slideShow.getTags();
					List<String> newTags = new ArrayList<String>();
					for (SlideshowTag slideshowTag : tags) {
						if (!slideshowTag.getName().equals(
								((Category) item.eContainer()).getLabel())) {
							newTags.add(slideshowTag.getName());
						}
					}
					getApi().editSlideShow(slideShow.getId(), slideShow.getTitle(),
							slideShow.getDescription(),
							org.apache.commons.lang.StringUtils.join(newTags, " "),
							slideShow.isPrivateSlide(), slideShow.isSecretUrl(),
							slideShow.isAllowEmbed(), slideShow.isSharedWithContacts());
				} catch (SlideShareException e) {
					// do nothing
				} catch (IOException e) {
					throw new RemoteException(StatusCreator.newStatus(
							"Error deleting slides from tag category", e));
				}
			}

		}

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
			returnValue.add(buildMySlides());
			returnValue.add(buildMyContacts());
			returnValue.add(buildMyGroups());
			returnValue.add(buildMySearches());
		} else if (URL_MYSLIDES.equals(container.getUrl())) {
			returnValue.addAll(buildMyTags());
		} else if (URL_MYGROUPS.equals(container.getUrl())) {
			returnValue.addAll(buildGroups());
		} else if (URL_MYCONTACTS.equals(container.getUrl())) {
			returnValue.addAll(buildContacts());
		} else if (!showOnlyContainers && container.getWrappedObject() instanceof UserTag) {
			returnValue.addAll(buildMySlidesByTag(((UserTag) container.getWrappedObject())
					.getName()));
		} else if (URL_MYSEARCHES.equals(container.getUrl())) {
			returnValue.addAll(buildMySearchContainer());
		} else if (!showOnlyContainers && container.getWrappedObject() instanceof SearchResult) {
			SearchResult result = (SearchResult) container.getWrappedObject();
			Slideshow[] slideshows = result.getSlideshows();
			for (Slideshow slideshow : slideshows) {
				if (slideshow.getStatus() == ConverstionState.CONVERTED)
					returnValue.add(buildSlideShow(slideshow, false));
			}
		} else if (!showOnlyContainers && container.getWrappedObject() instanceof UserGroup) {
			UserGroup group = (UserGroup) container.getWrappedObject();
			try {
				Group slidesByGroup = getApi().getSlidesByGroup(group.getQueryName(), true);
				Slideshow[] slideshows = slidesByGroup.getSlideshows();
				for (Slideshow slideshow : slideshows) {
					if (slideshow.getStatus() == ConverstionState.CONVERTED)
						returnValue.add(buildSlideShow(slideshow, false));
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error getting groups", e));
			}
		} else if (!showOnlyContainers && container.getWrappedObject() instanceof Contact) {
			try {
				User slidesByUser = getApi().getSlidesByUser(
						((Contact) container.getWrappedObject()).getUserName(), true);
				Slideshow[] slideshows = slidesByUser.getSlideshows();
				for (Slideshow slideshow : slideshows) {
					if (slideshow.getStatus() == ConverstionState.CONVERTED)
						returnValue.add(buildSlideShow(slideshow, false));
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error getting users slide", e));
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private Collection<? extends RemoteObject> buildContacts() throws RemoteException {
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		try {
			Contact[] userContacts = getApi()
					.getUserContacts(getCredentialProvider().getUserName());
			for (Contact contact : userContacts) {
				returnValue.add(buildContact(contact));
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting contacts", e));
		}
		return returnValue;
	}

	private RemoteContainer buildContact(final Contact contact) {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setHash(contact.getUserName());
		createRemoteContainer.setId(contact.getUserName());
		createRemoteContainer.setName(contact.getUserName());
		createRemoteContainer.setUrl(StringUtils.join(URL_CONTACTS, contact.getUserName()));
		createRemoteContainer.setWrappedObject(contact);
		return createRemoteContainer;
	}

	private Collection<? extends RemoteObject> buildGroups() throws RemoteException {
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		try {
			UserGroup[] userGroups = getApi().getUserGroups(getCredentialProvider().getUserName());
			for (UserGroup userGroup : userGroups) {
				returnValue.add(buildGroup(userGroup));
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting groups", e));
		}
		return returnValue;
	}

	private RemoteContainer buildGroup(final UserGroup userGroup) {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setHash(userGroup.getQueryName());
		createRemoteContainer.setId(userGroup.getQueryName());
		createRemoteContainer.setName(userGroup.getName());
		createRemoteContainer.setUrl(StringUtils.join(URL_GROUP, userGroup.getQueryName()));
		createRemoteContainer.setWrappedObject(userGroup);
		return createRemoteContainer;
	}

	private Collection<? extends RemoteObject> buildMySearchContainer() throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		RemoteRepository repositoryById = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(getLocalRepositoryId());

		String[] split = org.apache.commons.lang.StringUtils.split(repositoryById.getOptions().get(
				SlideshareActivator.REPOSITORY_OPTIONS_SEARCH_KEY), "|");
		for (String string : split) {
			returnValue.add(buildSearchContainer(string));
		}
		return returnValue;
	}

	private RemoteObject buildSearchContainer(final String string) throws RemoteException {
		try {
			RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE
					.createRemoteContainer();
			createRemoteContainer.setHash(string);
			createRemoteContainer.setId(string);
			createRemoteContainer.setName("Search for \"" + string + "\"");
			SearchResult searchSlideshows = getApi().searchSlideshows(string, 0, 100, null, null,
					null, null, false, false, false, true);
			createRemoteContainer.setWrappedObject(searchSlideshows);

			createRemoteContainer.setUrl(StringUtils.join(URL_MYSEARCHES, URLEncoder.encode(string,
					"UTF-8")));

			return createRemoteContainer;
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus("Error querying slideshare", e));
		}

	}

	private Collection<? extends RemoteObject> buildMySlidesByTag(final String name)
			throws RemoteException {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		try {
			User slidesByUser = getApi().getSlidesByUser(getCredentialProvider().getUserName(),
					true);
			Slideshow[] slideshows = slidesByUser.getSlideshows();
			for (Slideshow slideshow : slideshows) {
				SlideshowTag[] tags = slideshow.getTags();
				for (SlideshowTag slideshowTag : tags) {
					if (slideshowTag.getName().equals(name)
							&& slideshow.getStatus() == ConverstionState.CONVERTED) {
						returnValue.add(buildSlideShow(slideshow, true));
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator
					.newStatus("Error building slideshows by tag", e));
		}
		return returnValue;
	}

	private RemoteObject buildSlideShow(final Slideshow slideshow, final boolean ownSlides) {
		RemoteObject slideShow = InfomngmntFactory.eINSTANCE.createRemoteObject();
		slideShow.setId(slideshow.getId());
		slideShow.setName(StringEscapeUtils.unescapeXml(slideshow.getTitle()));
		String hash = null;
		if (slideshow.getUpdateDate() != null) {
			hash = String.valueOf(slideshow.getUpdateDate().getTime());
		} else {
			hash = String.valueOf(slideshow.getCreationDate().getTime());
		}
		slideShow.setHash(hash);
		if (ownSlides) {
			slideShow.setUrl(StringUtils.join(URL_MYSLIDES, ID_FRAGMENT, "/", slideShow.getId()));
		} else {
			slideShow.setUrl(URL_SLIDESHARE_ID + slideshow.getId());
		}
		slideShow.setWrappedObject(slideshow);
		return slideShow;
	}

	private Collection<? extends RemoteObject> buildMyTags() throws RemoteException {
		List<RemoteContainer> returnValue = new ArrayList<RemoteContainer>();
		try {
			UserTag[] userTags = getApi().getUserTags();
			for (UserTag userTag : userTags) {
				returnValue.add(buildUserTag(userTag));
			}
		} catch (Exception e) {
			throw new RemoteException(StatusCreator.newStatus("Error getting users tags", e));
		}
		return returnValue;
	}

	private RemoteContainer buildUserTag(final UserTag userTag) {
		RemoteContainer userTagContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		userTagContainer.setHash(DigestUtils.md5Hex(userTag.getName()));
		userTagContainer.setId(userTag.getName());
		userTagContainer.setName(userTag.getName());
		userTagContainer.setUrl(StringUtils.join("http://slideshare/usertags/", userTag.getName()));
		userTagContainer.setWrappedObject(userTag);
		return userTagContainer;
	}

	private RemoteObject buildMySearches() {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setHash("MySearches");
		createRemoteContainer.setId("MySearches");
		createRemoteContainer.setName("My Searches");
		createRemoteContainer.setUrl(URL_MYSEARCHES);
		return createRemoteContainer;
	}

	private RemoteObject buildMyGroups() {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setHash("MyGroups");
		createRemoteContainer.setId("MyGroups");
		createRemoteContainer.setName("My Groups");
		createRemoteContainer.setUrl(URL_MYGROUPS);
		return createRemoteContainer;
	}

	private RemoteObject buildMyContacts() {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setHash("MyContacts");
		createRemoteContainer.setId("MyContacts");
		createRemoteContainer.setName("My Contacts");
		createRemoteContainer.setUrl(URL_MYCONTACTS);
		return createRemoteContainer;
	}

	private RemoteObject buildMySlides() {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setHash("MySlides");
		createRemoteContainer.setId("MySlides");
		createRemoteContainer.setName("My Slides");
		createRemoteContainer.setUrl(URL_MYSLIDES);
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
		return null;
	}

	@Override
	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		if (remoteObject.getWrappedObject() instanceof Slideshow) {
			Slideshow wrappedObject = (Slideshow) remoteObject.getWrappedObject();
			InformationStructureEdit edit = InformationStructureEdit
					.newSession(getTypeIdByObject(remoteObject));
			InformationUnit returnValue = edit.newInformationUnit();
			edit.setValue(returnValue, OnlineResourceActivator.NODE_NAME_CREATED, wrappedObject
					.getId());
			edit.setValue(returnValue, "@description", wrappedObject.getDescription());
			SlideshowTag[] tags = wrappedObject.getTags();
			List<String> sb = new ArrayList<String>();
			for (SlideshowTag slideshowTag : tags) {
				sb.add(slideshowTag.getName());
			}
			edit.setValue(returnValue, "@keywords", org.apache.commons.lang.StringUtils.join(sb,
					" "));
			edit.setValue(returnValue, OnlineResourceActivator.NODE_NAME_URL, wrappedObject
					.getUrl());
			edit.setValue(returnValue, OnlineResourceActivator.NODE_NAME_EMBED, wrappedObject
					.getEmbededCode());
			edit.setValue(returnValue, OnlineResourceActivator.NODE_NAME_CREATED, wrappedObject
					.getCreationDate());
			return returnValue;
		}
		return null;
	}

	@Override
	public IFile getBinaryReferences(final InformationUnitListItem syncObject,
			final InformationUnit localInfoFragment, final IProgressMonitor monitor)
			throws RemoteException {
		if (OnlineResourceActivator.NODE_NAME_THUMBNAIL.equals(localInfoFragment.getType())) {
			RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(syncObject, monitor);
			Slideshow wrappedObject = (Slideshow) remoteObject.getWrappedObject();
			String thumbnailUrl = wrappedObject.getThumbnailUrl();
			IFile createTempFile = ResourceUtil.createTempFile("jpg");
			try {
				DownloadFileJob downloadFileJob = new DownloadFileJob(new URL(StringEscapeUtils
						.unescapeXml(thumbnailUrl)), createTempFile, getFileReceiveAdapter());
				IStatus run = downloadFileJob.run(monitor);
				if (run.isOK()) {
					return createTempFile;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (OnlineResourceActivator.NODE_NAME_ATTACHMENT.equals(localInfoFragment.getType())) {
			RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(syncObject, monitor);
			Slideshow wrappedObject = (Slideshow) remoteObject.getWrappedObject();
			String downloadUrl = wrappedObject.getDownloadUrl();
			IFile createTempFile = ResourceUtil
					.createTempFile(wrappedObject.getFormat().getValue());

			try {
				if (downloadUrl != null) {
					DownloadFileJob downloadFileJob = new DownloadFileJob(new URL(StringEscapeUtils
							.unescapeXml(downloadUrl)), createTempFile, getFileReceiveAdapter());
					IStatus run = downloadFileJob.run(monitor);
					if (run.isOK()) {
						return createTempFile;
					}
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return super.getBinaryReferences(syncObject, localInfoFragment, monitor);
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
		if (ID_FRAGMENT.equals(path.segment(path.segmentCount() - 2))) {
			try {
				Slideshow slideShow = getApi().getSlideShow(path.lastSegment(), null, true);
				if (slideShow.getStatus() == ConverstionState.CONVERTED)
					return buildSlideShow(slideShow, MYSLIDES_FRAGMENT.equals(path.segment(path
							.segmentCount() - 3)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (USERTAGS_FRAGMENT.equals(path.segment(path.segmentCount() - 2))) {
			try {
				UserTag[] userTags = getApi().getUserTags();
				for (UserTag userTag : userTags) {
					if (userTag.getName().equals(path.lastSegment())) {
						return buildUserTag(userTag);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (CONTACT_FRAGMENT.equals(path.segment(path.segmentCount() - 2))) {
			try {
				Contact[] userContacts = getApi().getUserContacts(
						getCredentialProvider().getUserName());
				for (Contact contact2 : userContacts) {
					if (contact2.getUserName().equals(path.lastSegment())) {
						return buildContact(contact2);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (GROUP_FRAGMENT.equals(path.segment(path.segmentCount() - 2))) {
			try {
				UserGroup[] userGroups = getApi().getUserGroups(
						getCredentialProvider().getUserName());
				for (UserGroup group : userGroups) {
					if (group.getQueryName().equals(path.lastSegment())) {
						return buildGroup(group);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (URL_MYSEARCHES.equals(url)) {
			return buildMySearches();
		} else if (URL_MYCONTACTS.equals(url)) {
			return buildMyContacts();
		} else if (URL_MYSLIDES.equals(url)) {
			return buildMySlides();
		} else if (MYSEARCHES_FRAGMENT.equals(path.segment(path.segmentCount() - 2))) {
			try {
				return buildSearchContainer(URLDecoder.decode(path.lastSegment(), "UTF-8"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		return OnlineResourceActivator.TYPE_ID;
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

	}

	public Slideshare getApi() {
		Slideshare slideshare = new Slideshare(SlideshareActivator.API_KEY,
				SlideshareActivator.SHARED_SECRED, getCredentialProvider().getUserName(),
				getCredentialProvider().getPassword());
		Proxy proxyByUrl = ProxyUtil.getProxyByUrl(Constants.API_ENDPOINT);
		if (proxyByUrl != null) {
			slideshare.setProxyConfiguration(proxyByUrl.getAddress().getHostName(), proxyByUrl
					.getAddress().getPort());
			slideshare.setProxyAuthenticationConfiguration(proxyByUrl.getUsername(), proxyByUrl
					.getPassword());
		}
		return slideshare;
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
