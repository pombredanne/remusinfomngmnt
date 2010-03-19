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
package org.remus.infomngmnt.connector.googlecontacts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageLoader;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.streams.StreamCloser;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.remote.AbstractExtensionRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.model.remote.ILoginCallBack;
import org.remus.infomngmnt.model.remote.IRepository;
import org.remus.infomngmnt.util.StatusCreator;

import com.google.gdata.client.Query;
import com.google.gdata.client.Service.GDataRequest;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ContentType;
import com.google.gdata.util.ServiceException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactsRepository extends AbstractExtensionRepository implements IRepository {

	private ContactsService api;

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	private String groupsFeed;

	private String contactFeed;

	private final ContactConverter converter;

	public ContactsRepository() {
		this.converter = new ContactConverter();
	}

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
		if (item instanceof InformationUnitListItem) {
			ContactEntry repo = this.converter.toRepo((InformationUnitListItem) item,
					new ContactEntry());
			if (repo != null) {
				try {
					RemoteObject remoteObjectBySynchronizableObject = getRemoteObjectBySynchronizableObject(
							(SynchronizableObject) item.eContainer(), monitor);
					if (remoteObjectBySynchronizableObject != null) {
						ContactGroupEntry wrappedObject = (ContactGroupEntry) remoteObjectBySynchronizableObject
								.getWrappedObject();
						GroupMembershipInfo groupMembershipInfo = new GroupMembershipInfo();
						groupMembershipInfo.setHref(wrappedObject.getId());
						repo.getGroupMembershipInfos().add(groupMembershipInfo);
						ContactEntry insert = getApi().insert(new URL(this.contactFeed), repo);
						addContactPhoto(insert, getApi(), (InformationUnitListItem) item);
						ContactEntry entry = getApi().getEntry(
								new URL(insert.getId().replace("/base/", "/full/")),
								ContactEntry.class);
						if (entry != null) {
							return buildContact(entry, wrappedObject.getId());
						}

					}
				} catch (Exception e) {
					throw new RemoteException(StatusCreator.newStatus("Error adding contact", e));
				}
			}
		} else if (item instanceof Category) {
			ContactGroupEntry contactGroupEntry = new ContactGroupEntry();
			contactGroupEntry.setTitle(TextConstruct.plainText(((Category) item).getLabel()));
			try {
				ContactGroupEntry insert = getApi().insert(new URL(this.groupsFeed),
						contactGroupEntry);
				return buildGroup(insert);
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error creating new group", e));
			}
		}
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
		RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(item2commit, monitor);
		if (remoteObject != null) {
			if (item2commit instanceof InformationUnitListItem
					&& remoteObject.getWrappedObject() instanceof ContactEntry) {
				ContactEntry repo = this.converter.toRepo((InformationUnitListItem) item2commit,
						(ContactEntry) remoteObject.getWrappedObject());
				try {
					ContactEntry update = repo.update();

					/*
					 * Adding photo
					 */
					addContactPhoto(update, getApi(), (InformationUnitListItem) item2commit);
					RemoteObject remoteParent = getRemoteObjectBySynchronizableObject(
							(SynchronizableObject) item2commit.eContainer(), monitor);
					return buildContact(update, remoteParent.getId());
				} catch (Exception e) {
					throw new RemoteException(StatusCreator
							.newStatus("Error updating a contact", e));
				}
			} else if (item2commit instanceof Category
					&& remoteObject.getWrappedObject() instanceof ContactGroupEntry) {
				ContactGroupEntry wrappedObject = (ContactGroupEntry) remoteObject
						.getWrappedObject();
				wrappedObject
						.setTitle(TextConstruct.plainText(((Category) item2commit).getLabel()));
				try {
					ContactGroupEntry update = wrappedObject.update();
					return buildGroup(update);
				} catch (Exception e) {
					throw new RemoteException(StatusCreator.newStatus("Error updating category", e));
				}

			}
		}
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
		RemoteObject remoteObject = getRemoteObjectBySynchronizableObject(item, monitor);
		if (remoteObject != null) {
			Object wrappedObject = remoteObject.getWrappedObject();
			try {
				if (wrappedObject instanceof ContactEntry) {
					((ContactEntry) wrappedObject).delete();
				} else if (wrappedObject instanceof ContactGroupEntry) {
					((ContactGroupEntry) wrappedObject).delete();
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error deleting remoteobject"));
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
		if (container instanceof RemoteRepository) {
			return buildGroups();
		} else if (container instanceof RemoteContainer && !showOnlyContainers) {
			return buildContacts(container);
		}
		return new RemoteObject[0];
	}

	private RemoteObject[] buildContacts(final RemoteContainer container) {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		try {
			Query myQuery = new Query(new URL(this.contactFeed));
			myQuery.setStringCustomParameter("group", container.getId());
			ContactFeed resultFeed = getApi().query(myQuery, ContactFeed.class);
			for (ContactEntry entry : resultFeed.getEntries()) {
				if (!entry.hasDeleted()) {
					returnValue.add(buildContact(entry, container.getId()));
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private RemoteObject buildContact(final ContactEntry entry, final String parentId) {
		RemoteObject createRemoteObject = InfomngmntFactory.eINSTANCE.createRemoteObject();
		createRemoteObject.setHash(entry.getUpdated().toString());
		createRemoteObject.setId(entry.getId());
		createRemoteObject.setWrappedObject(entry);
		createRemoteObject.setName(entry.getTitle().getPlainText());
		createRemoteObject.setUrl(StringUtils.join(parentId, "_", entry.getId()));
		return createRemoteObject;
	}

	private RemoteObject[] buildGroups() {
		List<RemoteObject> returnValue = new ArrayList<RemoteObject>();
		try {
			ContactGroupFeed groupFeed = getApi().getFeed(new URL(this.groupsFeed),
					ContactGroupFeed.class);
			for (ContactGroupEntry entry : groupFeed.getEntries()) {
				returnValue.add(buildGroup(entry));
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	private RemoteContainer buildGroup(final ContactGroupEntry entry) {
		RemoteContainer createRemoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
		createRemoteContainer.setHash(entry.getUpdated().toString());
		createRemoteContainer.setId(entry.getId());
		createRemoteContainer.setWrappedObject(entry);
		createRemoteContainer.setName(entry.getTitle().getPlainText());
		createRemoteContainer.setUrl(entry.getId());
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
		ContactEntry wrappedObject = (ContactEntry) remoteObject.getWrappedObject();
		Link photoLink = wrappedObject.getContactPhotoLink();
		byte[] convertStreamToByte = new byte[0];
		InformationUnit fromRepo = this.converter.fromRepo(wrappedObject);
		try {
			GDataRequest request = getApi().createLinkQueryRequest(photoLink);
			request.execute();
			InputStream resultStream = request.getResponseStream();
			ImageLoader loader = new ImageLoader();
			loader.load(resultStream);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			loader.save(baos, SWT.IMAGE_PNG);
			convertStreamToByte = baos.toByteArray();
			StreamCloser.closeStreams(resultStream);
			if (convertStreamToByte != null && convertStreamToByte.length > 0) {
				InformationStructureEdit edit = InformationStructureEdit
						.newSession(ContactActivator.TYPE_ID);
				edit.setValue(fromRepo, ContactActivator.NODE_NAME_RAWDATA_IMAGE,
						convertStreamToByte);
			}
		} catch (Exception e) {
			// No photo
		}
		return fromRepo;

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
		if (url.equals(getRepositoryUrl())) {
			return getRepositoryById(object.getSynchronizationMetaData().getRepositoryId());
		}
		if (object instanceof Category) {
			try {
				ContactGroupEntry entry = getApi().getEntry(
						new URL(url.replace("/base/", "/full/")), ContactGroupEntry.class);
				if (entry != null) {
					return buildGroup(entry);
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error resolving online contact",
						e));
			}
		} else if (object instanceof InformationUnitListItem) {
			try {
				String[] split = url.split("_");
				ContactGroupEntry cateEntry = getApi().getEntry(
						new URL(split[0].replace("/base/", "/full/")), ContactGroupEntry.class);
				ContactEntry entry = getApi().getEntry(
						new URL(split[1].replace("/base/", "/full/")), ContactEntry.class);
				if (entry != null) {
					return buildContact(entry, cateEntry.getId());
				}
			} catch (Exception e) {
				throw new RemoteException(StatusCreator.newStatus("Error resolving online contact",
						e));
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#getRepositoryUrl()
	 */
	@Override
	public String getRepositoryUrl() {
		return "http://www.google.com/m8/feeds/";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.IRepository#getTypeIdByObject(org.remus
	 * .infomngmnt.RemoteObject)
	 */
	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return ContactActivator.TYPE_ID;
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

	public static void addContactPhoto(final ContactEntry entry, final ContactsService service,
			final InformationUnitListItem item) throws ServiceException, IOException {
		Link photoLink = entry.getContactPhotoLink();
		URL photoUrl = new URL(photoLink.getHref());
		service.delete(photoUrl, photoLink.getEtag());
		GDataRequest request = service.createRequest(GDataRequest.RequestType.UPDATE, photoUrl,
				new ContentType("image/jpeg"));

		OutputStream requestStream = request.getRequestStream();
		InformationUnit adapter = (InformationUnit) item.getAdapter(InformationUnit.class);
		if (adapter != null) {
			InformationStructureRead read = InformationStructureRead.newSession(adapter);
			byte[] valueByNodeId = (byte[]) read
					.getValueByNodeId(ContactActivator.NODE_NAME_RAWDATA_IMAGE);
			if (valueByNodeId != null && valueByNodeId.length > 0) {

				requestStream.write(valueByNodeId);
				request.execute();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#reset()
	 */
	public void reset() {
		this.api = null;
		getCredentialProvider().removePropertyChangeListener(this.credentialsMovedListener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.remote.IRepository#validate()
	 */
	@Override
	public IStatus validate() {
		try {
			reset();
			getApi();
		} catch (RemoteException e) {
			return e.getStatus();
		}
		return Status.OK_STATUS;

	}

	public synchronized ContactsService getApi() throws RemoteException {
		if (this.api == null) {
			getCredentialProvider().setIdentifier(getLocalRepositoryId());
			this.api = new ContactsService("Remus Information Management");
			try {
				this.api.setUserCredentials(getCredentialProvider().getUserName(),
						getCredentialProvider().getPassword());
				this.groupsFeed = StringUtils.join(getRepositoryUrl(), "groups/",
						getCredentialProvider().getUserName(), "/full");
				this.contactFeed = StringUtils.join(getRepositoryUrl(), "contacts/",
						getCredentialProvider().getUserName(), "/full");
			} catch (AuthenticationException e) {
				throw new RemoteException(StatusCreator.newStatus(
						"Error while validating credentials", e));
			}
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);

		}
		return this.api;
	}

}
