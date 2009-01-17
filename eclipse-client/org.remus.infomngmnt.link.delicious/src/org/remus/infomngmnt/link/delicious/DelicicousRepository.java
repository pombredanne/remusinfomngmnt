/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.link.delicious;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.emf.common.util.EList;

import del.icio.us.Delicious;
import del.icio.us.DeliciousConstants;
import del.icio.us.beans.Post;
import del.icio.us.beans.Tag;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.remote.ILoginCallBack;
import org.remus.infomngmnt.link.LinkActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DelicicousRepository extends AbstractExtensionRepository {

	private Delicious api;

	public static final String KEY_TAG = "KEY_TAG"; //$NON-NLS-1$

	public static final String KEY_LINK = "KEY_LINK"; //$NON-NLS-1$

	/**
	 * By convention you have to wait between two requests to the
	 * delicious platform at least 1 second, otherwise you get 
	 * throttled automatically. So we have to use this intervall
	 * between two requests.
	 */
	private static final long WAIT_INTERVALL = 5000;

	public static long lastApiCall;

	private final PropertyChangeListener credentialsMovedListener = new PropertyChangeListener() {
		public void propertyChange(final PropertyChangeEvent evt) {
			reset();
		}
	};

	final ISchedulingRule mutexRule = new ISchedulingRule() {
		public boolean isConflicting(final ISchedulingRule rule) {
			return rule == DelicicousRepository.this.mutexRule;
		}
		public boolean contains(final ISchedulingRule rule) {
			return rule == DelicicousRepository.this.mutexRule;
		}
	};
	
	
	public void applyChangeSet(final ChangeSet changeSet) {
		EList<ChangeSetItem> changeSetItems = changeSet.getChangeSetItems();
		for (ChangeSetItem changeSetItem : changeSetItems) {
			proceedChangeSetItem(changeSetItem);
		}
		
	}
	
	private void proceedChangeSetItem(final ChangeSetItem changeSetItem) {
		/*
		 * if the local container is not set, we have a "checkout"
		 */
		if (changeSetItem.getLocalContainer() == null) {
			proceedCheckout(changeSetItem, null, changeSetItem.getRemoteOriginalObject());
		}
		
	}

	private void proceedCheckout(final ChangeSetItem changeSetItem, final Category parentRemoteCategory, final RemoteObject remoteObject) {
		if (KEY_TAG.equals(remoteObject.getRepositoryTypeObjectId())) {
			Tag wrappedObject = (Tag) remoteObject.getWrappedObject();
			Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
			createCategory.setId(new UniversalUniqueIdentifier().toString());
			createCategory.setLabel(wrappedObject.getTag());
			
			SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE.createSynchronizationMetadata();
			metadata.setHash(wrappedObject.getTag());
			metadata.setReadonly(false);
			metadata.setSyncState(SynchronizationState.IN_SYNC);
			metadata.setRepositoryId(((ChangeSet) changeSetItem.eContainer()).getRepository().getId());
			metadata.setUrl("TEST");
			createCategory.setSynchronizationMetaData(metadata);
			if (parentRemoteCategory != null) {
				parentRemoteCategory.getChildren().add(createCategory);
			} else {
				changeSetItem.setRemoteConvertedContainer(createCategory);
			}
			changeSetItem.getSyncCategoryActionMap().put(createCategory, SynchronizationAction.ADD_LOCAL);
			if (remoteObject instanceof RemoteContainer) {
				RemoteObject[] children = getChildren(new NullProgressMonitor(), (RemoteContainer) remoteObject, false);
				for (RemoteObject remoteObject2 : children) {
					proceedCheckout(changeSetItem, createCategory, remoteObject2);
				}
			}
			
			
		} else if (KEY_LINK.equals(remoteObject.getRepositoryTypeObjectId())) {
			IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(LinkActivator.LINK_INFO_ID);
			if (infoTypeByType != null) {
				InformationUnit newObject = infoTypeByType.getCreationFactory().createNewObject();
				Post post = (Post) remoteObject.getWrappedObject();
				newObject.setLabel(remoteObject.getName());
				newObject.setStringValue(post.getHref());
				newObject.setDescription(post.getExtended());
				newObject.setKeywords(post.getTag());
				newObject.setCreationDate(post.getTimeAsDate());
				newObject.setId(new UniversalUniqueIdentifier().toString());
				InformationUnitListItem createInformationUnitListItem = InfomngmntFactory.eINSTANCE.createInformationUnitListItem();

				// transfer the needed information
				createInformationUnitListItem.setId(newObject.getId());
				createInformationUnitListItem.setLabel(newObject.getLabel());
				createInformationUnitListItem.setType(newObject.getType());

				SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE.createSynchronizationMetadata();
				metadata.setHash(post.getHash());
				metadata.setReadonly(false);
				metadata.setRepositoryId(((ChangeSet) changeSetItem.eContainer()).getRepository().getId());
				metadata.setSyncState(SynchronizationState.IN_SYNC);
				metadata.setUrl("http://test");
				createInformationUnitListItem.setSynchronizationMetaData(metadata);
				
				if (parentRemoteCategory != null) {
					parentRemoteCategory.getInformationUnit().add(createInformationUnitListItem);
				} else {
					changeSetItem.getRemoteConvertedContainer().getInformationUnit().add(createInformationUnitListItem);
				}
				changeSetItem.getRemoteFullObjectMap().put(createInformationUnitListItem, newObject);
				changeSetItem.getSyncInformationUnitActionMap().put(createInformationUnitListItem, SynchronizationAction.ADD_LOCAL);
			}
		}
		
	}




	public RemoteObject[] getChildren(final IProgressMonitor monitor,
			final RemoteContainer container, final boolean showOnlyContainer) {
		List<RemoteObject> returnValue = new LinkedList<RemoteObject>();
		if (container instanceof RemoteRepository) {
			IProgressMonitor sub = new SubProgressMonitor(monitor, IProgressMonitor.UNKNOWN);
			List<Tag> tags = getApi().getTags();
			for (Tag tag : tags) {
				RemoteContainer remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteContainer();
				remoteContainer.setId(tag.getTag());
				remoteContainer.setRepositoryTypeObjectId(KEY_TAG);
				remoteContainer.setName(tag.getTag());
				remoteContainer.setUrl(getRepositoryUrl() + tag.getTag());
				remoteContainer.setWrappedObject(tag);
				remoteContainer.setRepositoryTypeId(container.getRepositoryTypeId());
				returnValue.add(remoteContainer);
			}

		} else if (!showOnlyContainer){
			List<Post> posts = getApi().getAllPosts(container.getName());
			for (Post post : posts) {
				RemoteObject remoteContainer = InfomngmntFactory.eINSTANCE.createRemoteObject();
				remoteContainer.setId(post.getHash());
				remoteContainer.setName(post.getDescription());
				remoteContainer.setUrl(getRepositoryUrl() + post.getHash());
				remoteContainer.setRepositoryTypeObjectId(KEY_LINK);
				remoteContainer.setWrappedObject(post);
				remoteContainer.setRepositoryTypeId(container.getRepositoryTypeId());
				returnValue.add(remoteContainer);
			}
		}
		return returnValue.toArray(new RemoteObject[returnValue.size()]);
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.remote.IRepository#getRepositoryUrl()
	 */
	public String getRepositoryUrl() {
		return DeliciousConstants.API_ENDPOINT;
	}


	private Delicious getApi() {
		System.out.println("CONTACTING API");
		while ((System.currentTimeMillis() - DelicicousRepository.lastApiCall) < WAIT_INTERVALL) {
			try {
				System.out.println("WAITING");
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// does nothing.
			}
		}
		if (this.api == null) {
			getCredentialProvider().setIdentifier(getLocalRepositoryId());
			this.api = new Delicious(
					getCredentialProvider().getUserName(),
					getCredentialProvider().getPassword());
			getCredentialProvider().addPropertyChangeListener(this.credentialsMovedListener);
		}
		this.lastApiCall = System.currentTimeMillis();
		return this.api;
	}


	public IStatus validate() {
		try {
			getApi().getRecentPosts();
			return Status.OK_STATUS;
		} catch (Exception e) {
			return StatusCreator.newStatus("Error connecting...",e);
		}
	}

	public void login(final ILoginCallBack callback, final IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	public void reset() {
		this.api = null;
		getCredentialProvider().removePropertyChangeListener(this.credentialsMovedListener);
	}



	public ISchedulingRule getRule() {
		return this.mutexRule;
	}

	public void commit(final InformationUnitListItem[] items, final IProgressMonitor monitor) {
		for (InformationUnitListItem infoUnit : items) {
			SynchronizationMetadata synchronizationMetaData = infoUnit.getSynchronizationMetaData();
			if (LinkActivator.LINK_INFO_ID.equals(infoUnit.getType())) {
				InformationUnit adapter = (InformationUnit) infoUnit.getAdapter(InformationUnit.class);
				if (adapter != null) {
					getApi().addPost(
							adapter.getStringValue(), 
							adapter.getDescription(),
							null, 
							adapter.getKeywords(), 
							adapter.getCreationDate(), 
							true, true);
				}
			}
			synchronizationMetaData.setSyncState(SynchronizationState.IN_SYNC);
		}
		
	}

	public String getTypeIdByObject(final RemoteObject remoteObject) {
		return LinkActivator.LINK_INFO_ID;
	}

	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		if (KEY_LINK.equals(remoteObject.getRepositoryTypeObjectId())) {
			IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(LinkActivator.LINK_INFO_ID);
			InformationUnit newObject = infoTypeByType.getCreationFactory().createNewObject();
			Post post = (Post) remoteObject.getWrappedObject();
			newObject.setLabel(remoteObject.getName());
			newObject.setStringValue(post.getHref());
			newObject.setDescription(post.getExtended());
			newObject.setKeywords(post.getTag());
			newObject.setCreationDate(post.getTimeAsDate());
			return newObject;
		}
		return null;
		
	}




}
