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

package org.remus.infomngmnt.core.sync;

import java.util.List;

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ChangeSetManager {
	
	public ChangeSet createCheckOutChangeSet(final List<RemoteContainer> remoteContainers) {
		
		/*
		 * At first we make sure that all elements are located within
		 * the same repository.
		 */
		if (ModelUtil.hasEqualAttribute(remoteContainers, InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_ID)) {
			/*
			 * We acquire the RemoteRepository object.
			 */
			RemoteRepository repository = null;
			
			for (RemoteContainer object : remoteContainers) {
				repository = RemoteUtil.getRemoteRepository(object);
				break;
			}
			/*
			 * Now it's time to create a changeset
			 */
			ChangeSet createChangeSet = InfomngmntFactory.eINSTANCE.createChangeSet();
			/*
			 * A changeset is always bound to a remote-repository.
			 */
			createChangeSet.setRepository(repository);
			/*
			 * Next important step is to filter all RemoteContainers which are 
			 * children of other selected RemoteContainers.
			 */
			List<RemoteContainer> filteredList = CollectionUtils.filter(remoteContainers, new CollectionFilter<RemoteContainer>() {

				public boolean select(final RemoteContainer item) {
					return !ModelUtil.containsParent(remoteContainers, item);
				}
			});
			/*
			 * At this place we have to populate the changeset.
			 * Therefore we have to ask the repositories for the 
			 * children and create a datastructure that can be applied
			 * to the local datastructure.
			 */
			IRepository repositoryImplementation = repository.getRepositoryImplementation();
			for (RemoteContainer remoteObject : filteredList) {
				RemoteContainer copiedItem = (RemoteContainer) EcoreUtil.copy(remoteObject);
				ChangeSetItem createChangeSetItem = InfomngmntFactory.eINSTANCE.createChangeSetItem();
				createChangeSetItem.setRemoteOriginalObject(copiedItem);
				createChangeSet.getChangeSetItems().add(createChangeSetItem);
				
				
				Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
				createCategory.setId(new UniversalUniqueIdentifier().toString());
				createCategory.setLabel(remoteObject.getName());
				
				SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE.createSynchronizationMetadata();
				metadata.setHash(remoteObject.getHash());
				metadata.setReadonly(/* TODO implement */ false);
				metadata.setSyncState(SynchronizationState.IN_SYNC);
				metadata.setRepositoryId(repository.getId());
				metadata.setUrl(remoteObject.getUrl());
				createCategory.setSynchronizationMetaData(metadata);
				createChangeSetItem.setRemoteOriginalObject(copiedItem);
				createChangeSetItem.setRemoteConvertedContainer(createCategory);
				RemoteObject[] children = repositoryImplementation.getChildren(null, copiedItem, false);
				for (RemoteObject remoteObject2 : children) {
					fillRemoteContainer(createChangeSetItem, remoteObject2, repository, createCategory);
				}
			}
			return createChangeSet;
		}
		return null;
	}
	
	/**
	 * This is a recursiv method which gets the children from the repository implementation
	 * and converts the result into data-objects which can be appended to the local data-
	 * structure. At the same time a {@link SynchronizationMetadata} is created which holds
	 * important data of the repository.
	 * @param changeSetItem
	 * @param remoteObject2
	 * @param remoteRepository
	 * @param parentCategory
	 */
	public void fillRemoteContainer(final ChangeSetItem changeSetItem, final RemoteObject remoteObject2,
			final RemoteRepository remoteRepository, final Category parentCategory) {
		if (remoteObject2 instanceof RemoteContainer) {
			Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
			createCategory.setId(new UniversalUniqueIdentifier().toString());
			
			SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE.createSynchronizationMetadata();
			metadata.setHash(remoteObject2.getHash());
			metadata.setReadonly(/* TODO implement */  false);
			metadata.setSyncState(SynchronizationState.IN_SYNC);
			metadata.setRepositoryId(remoteRepository.getId());
			metadata.setUrl(remoteObject2.getUrl());
			createCategory.setSynchronizationMetaData(metadata);
			if (parentCategory != null) {
				parentCategory.getChildren().add(createCategory);
			} else {
				changeSetItem.setRemoteConvertedContainer(createCategory);
			}
			
			
			RemoteObject[] children = remoteRepository.getRepositoryImplementation().getChildren(new NullProgressMonitor(), (RemoteContainer) remoteObject2, false);
			for (RemoteObject newChildren : children) {
				fillRemoteContainer(changeSetItem, newChildren, remoteRepository, createCategory);
			}
		} else {
			InformationUnitListItem createInformationUnitListItem = InfomngmntFactory.eINSTANCE.createInformationUnitListItem();

			// transfer the needed information
			createInformationUnitListItem.setId(new UniversalUniqueIdentifier().toString());
			createInformationUnitListItem.setLabel(remoteObject2.getName());
			createInformationUnitListItem.setType(remoteRepository.getRepositoryImplementation().getTypeIdByObject(remoteObject2));

			SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE.createSynchronizationMetadata();
			metadata.setHash(remoteObject2.getHash());
			metadata.setReadonly(/* TODO implement */  false);
			metadata.setRepositoryId(remoteRepository.getId());
			metadata.setSyncState(SynchronizationState.IN_SYNC);
			metadata.setUrl(remoteObject2.getUrl());
			createInformationUnitListItem.setSynchronizationMetaData(metadata);
			if (parentCategory != null) {
				parentCategory.getInformationUnit().add(createInformationUnitListItem);
			}
			InformationUnit prefetchedInformationUnit = remoteRepository.getRepositoryImplementation().getPrefetchedInformationUnit(
					remoteObject2);
			changeSetItem.getRemoteFullObjectMap().put(
					createInformationUnitListItem, prefetchedInformationUnit 
			);
			if (prefetchedInformationUnit != null) {
				prefetchedInformationUnit.setId(createInformationUnitListItem.getId());
			}
			prefetchedInformationUnit.setId(createInformationUnitListItem.getId());
			changeSetItem.getSyncInformationUnitActionMap().put(createInformationUnitListItem, SynchronizationAction.ADD_LOCAL);
		}
		
		
	}

}
