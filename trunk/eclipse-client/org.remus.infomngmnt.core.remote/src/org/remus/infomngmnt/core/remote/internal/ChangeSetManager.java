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

package org.remus.infomngmnt.core.remote.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeRightTarget;
import org.eclipse.emf.compare.diff.metamodel.MoveModelElement;
import org.eclipse.emf.compare.diff.metamodel.UpdateAttribute;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.EObjectTypeRelationCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.AbstractInformationUnit;
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
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;
import org.remus.infomngmnt.common.core.util.IdFactory;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.remote.AbstractExtensionRepository;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.remote.RemoteUtil;
import org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.core.remote.sync.ChangeSetException;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.model.remote.IChangeHandler;
import org.remus.infomngmnt.model.remote.IRepository;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ChangeSetManager implements IChangeHandler {

	public static final int MODE_SYNC = 1;
	public static final int MODE_CHECKOUT_REPLACE = 2;

	private static final String COMPARE_FOLDER = "compare";
	private IRepositoryExtensionService extensionService;
	private IRepositoryService repositoryService;
	private IApplicationModel applicationService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#createChangeSet(
	 * org.remus.infomngmnt.Category, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ChangeSet createChangeSet(final Category cat, final IProgressMonitor monitor)
			throws CoreException {
		SynchronizationMetadata metaData = cat.getSynchronizationMetaData();

		AbstractExtensionRepository repository = this.extensionService.getItemByLocalObject(cat);

		RemoteObject remoteObject = repository.getRemoteObjectBySynchronizableObject(cat, monitor);
		if (remoteObject != null && remoteObject instanceof RemoteContainer) {
			return createChangeSet(cat, Collections
					.<RemoteContainer> singletonList((RemoteContainer) remoteObject),
					this.repositoryService.getRepositoryById(metaData.getRepositoryId()),
					ChangeSetManager.MODE_CHECKOUT_REPLACE, monitor);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#createChangeSet(
	 * org.remus.infomngmnt.Category, java.util.List,
	 * org.remus.infomngmnt.RemoteRepository, int,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ChangeSet createChangeSet(final Category localContainer,
			final List<RemoteContainer> remoteContainers, final RemoteRepository localRepository,
			final int mode, final IProgressMonitor monitor) throws ChangeSetException {

		/*
		 * At first we make sure that all elements are located within the same
		 * repository.
		 */
		if (ModelUtil.hasEqualAttribute(remoteContainers,
				InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_ID)) {
			/*
			 * We acquire the RemoteRepository object.
			 */
			RemoteRepository repository = null;

			if (localRepository == null) {
				for (RemoteContainer object : remoteContainers) {
					repository = RemoteUtil.getRemoteRepository(object);
					break;
				}
			} else {
				repository = localRepository;
			}
			/*
			 * Now it's time to create a changeset
			 */
			ChangeSet createChangeSet = InfomngmntFactory.eINSTANCE.createChangeSet();

			if (localContainer != null) {
				createChangeSet.setTargetCategory(localContainer);

			}

			/*
			 * We assume that
			 */

			/*
			 * A changeset is always bound to a remote-repository.
			 */
			createChangeSet.setRepository(repository);
			/*
			 * Next important step is to filter all RemoteContainers which are
			 * children of other selected RemoteContainers.
			 */
			List<RemoteContainer> filteredList = CollectionUtils.filter(remoteContainers,
					new CollectionFilter<RemoteContainer>() {

						public boolean select(final RemoteContainer item) {
							return !ModelUtil.containsParent(remoteContainers, item);
						}
					});
			/*
			 * At this place we have to populate the changeset. Therefore we
			 * have to ask the repositories for the children and create a
			 * datastructure that can be applied to the local datastructure.
			 */
			monitor.beginTask("Requesting objects from repository", IProgressMonitor.UNKNOWN);
			IRepository repositoryImplementation;
			if (localContainer != null) {
				repositoryImplementation = this.extensionService
						.getItemByLocalObject(localContainer);
			} else {

				try {
					repositoryImplementation = this.extensionService
							.getItemByRepository(repository);
				} catch (CoreException e) {
					throw new ChangeSetException(e.getStatus());
				}
			}

			for (RemoteContainer remoteObject : filteredList) {
				RemoteContainer copiedItem = (RemoteContainer) EcoreUtil.copy(remoteObject);
				ChangeSetItem createChangeSetItem = InfomngmntFactory.eINSTANCE
						.createChangeSetItem();
				createChangeSetItem.setRemoteOriginalObject(copiedItem);
				createChangeSet.getChangeSetItems().add(createChangeSetItem);

				Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
				createCategory.setLabel(remoteObject.getName());

				SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE
						.createSynchronizationMetadata();
				metadata.setHash(remoteObject.getHash());
				metadata.setReadonly(/* TODO implement */false);
				metadata.setSyncState(SynchronizationState.IN_SYNC);
				metadata.setRepositoryId(repository.getId());
				metadata.setUrl(remoteObject.getUrl());
				createCategory.setSynchronizationMetaData(metadata);
				createCategory.setId(findId(createCategory, createChangeSetItem));
				createChangeSetItem.setRemoteOriginalObject(copiedItem);
				createChangeSetItem.setRemoteConvertedContainer(createCategory);
				try {
					monitor.setTaskName(NLS.bind("Contacting repository for item \"{0}\"",
							copiedItem.getName()));
					RemoteObject[] children = repositoryImplementation.getChildren(monitor,
							copiedItem, false);
					for (RemoteObject remoteObject2 : children) {
						fillRemoteContainer(createChangeSetItem, remoteObject2, repository,
								createCategory, monitor);
					}
				} catch (RemoteException e) {
					throw new ChangeSetException(StatusCreator.newStatus(
							"Error creating changeset", e));
				}
			}
			return createChangeSet;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#fillRemoteContainer
	 * (org.remus.infomngmnt.ChangeSetItem, org.remus.infomngmnt.RemoteObject,
	 * org.remus.infomngmnt.RemoteRepository, org.remus.infomngmnt.Category,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void fillRemoteContainer(final ChangeSetItem changeSetItem,
			final RemoteObject remoteObject2, final RemoteRepository remoteRepository,
			final Category parentCategory, final IProgressMonitor monitor) throws RemoteException {
		monitor.subTask(NLS.bind("Found item \"{0}\"", remoteObject2.getName()));

		/*
		 * We have to look if the root element is present.
		 */
		AbstractExtensionRepository itemByLocalObject;
		Category category = ((ChangeSet) changeSetItem.eContainer()).getTargetCategory();
		if (category != null) {
			itemByLocalObject = this.extensionService.getItemByLocalObject(category);
		} else {
			try {
				itemByLocalObject = this.extensionService.getItemByRepository(remoteRepository);
			} catch (CoreException e) {
				throw new RemoteException(e.getStatus());
			}
		}
		if (remoteObject2 instanceof RemoteContainer) {
			Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();

			SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE
					.createSynchronizationMetadata();
			metadata.setHash(remoteObject2.getHash());
			metadata.setReadonly(/* TODO implement */false);
			metadata.setSyncState(SynchronizationState.IN_SYNC);
			metadata.setRepositoryId(remoteRepository.getId());
			metadata.setUrl(remoteObject2.getUrl());

			createCategory.setSynchronizationMetaData(metadata);
			createCategory.setId(findId(createCategory, changeSetItem));
			createCategory.setLabel(remoteObject2.getName());
			if (parentCategory != null) {
				parentCategory.getChildren().add(createCategory);
			} else {
				changeSetItem.setRemoteConvertedContainer(createCategory);
			}

			RemoteObject[] children = itemByLocalObject.getChildren(new NullProgressMonitor(),
					(RemoteContainer) remoteObject2, false);
			for (RemoteObject newChildren : children) {
				fillRemoteContainer(changeSetItem, newChildren, remoteRepository, createCategory,
						monitor);
			}
		} else {
			fillInfoObject(changeSetItem, remoteObject2, remoteRepository, parentCategory);
		}
	}

	private void fillInfoObject(final ChangeSetItem changeSetItem,
			final RemoteObject remoteObject2, final RemoteRepository remoteRepository,
			final Category parentCategory) throws RemoteException {
		InformationUnitListItem createInformationUnitListItem = InfomngmntFactory.eINSTANCE
				.createInformationUnitListItem();
		AbstractExtensionRepository itemByLocalObject;
		Category category = ((ChangeSet) changeSetItem.eContainer()).getTargetCategory();
		if (category != null) {
			itemByLocalObject = this.extensionService.getItemByLocalObject(category);
		} else {
			try {
				itemByLocalObject = this.extensionService.getItemByRepository(remoteRepository);
			} catch (CoreException e) {
				throw new RemoteException(e.getStatus());
			}
		}
		// transfer the needed information

		createInformationUnitListItem.setLabel(remoteObject2.getName());
		createInformationUnitListItem.setType(itemByLocalObject.getTypeIdByObject(remoteObject2));

		SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE
				.createSynchronizationMetadata();
		metadata.setHash(remoteObject2.getHash());
		metadata.setReadonly(/* TODO implement */false);
		metadata.setRepositoryId(remoteRepository.getId());
		metadata.setSyncState(SynchronizationState.IN_SYNC);
		metadata.setUrl(remoteObject2.getUrl());
		createInformationUnitListItem.setSynchronizationMetaData(metadata);
		if (parentCategory != null) {
			parentCategory.getInformationUnit().add(createInformationUnitListItem);
		}
		InformationUnit prefetchedInformationUnit = itemByLocalObject
				.getPrefetchedInformationUnit(remoteObject2);
		changeSetItem.getRemoteFullObjectMap().put(createInformationUnitListItem,
				prefetchedInformationUnit);
		createInformationUnitListItem.setId(findId(createInformationUnitListItem, changeSetItem));
		if (prefetchedInformationUnit != null) {
			prefetchedInformationUnit.setId(createInformationUnitListItem.getId());
		}

	}

	private String findId(final Category createCategory, final ChangeSetItem changeSetItem) {
		Category localContainer = ((ChangeSet) changeSetItem.eContainer()).getTargetCategory();
		if (localContainer != null) {
			if (((SynchronizableObject) localContainer).getSynchronizationMetaData() != null
					&& createCategory.getSynchronizationMetaData().getUrl().equals(
							((SynchronizableObject) localContainer).getSynchronizationMetaData()
									.getUrl())) {
				return localContainer.getId();
			}
			EObjectCondition typeRelationCondition = new EObjectCondition() {
				@Override
				public boolean isSatisfied(final EObject eObject) {
					return eObject.eClass() == InfomngmntPackage.Literals.CATEGORY;
				}
			};
			EObjectCondition valueCondition = new EObjectCondition() {
				@Override
				public boolean isSatisfied(final EObject eObject) {
					return ((SynchronizableObject) eObject).getSynchronizationMetaData() != null
							&& createCategory.getSynchronizationMetaData().getUrl().equals(
									((SynchronizableObject) eObject).getSynchronizationMetaData()
											.getUrl());
				}
			};
			SELECT select = new SELECT(new FROM(localContainer), new WHERE(typeRelationCondition
					.AND(valueCondition)));
			IQueryResult execute = select.execute();
			Set<? extends EObject> eObjects = execute.getEObjects();
			if (eObjects.size() > 0) {
				Iterator<? extends EObject> iterator = eObjects.iterator();
				while (iterator.hasNext()) {
					EObject eObject = iterator.next();
					return ((Category) eObject).getId();
				}
			}
		}
		return IdFactory.createNewId(null);

	}

	private String findId(final InformationUnitListItem infoUnit, final ChangeSetItem changeSetItem) {
		Category localContainer = ((ChangeSet) changeSetItem.eContainer()).getTargetCategory();
		if (localContainer != null) {

			EObjectCondition typeRelationCondition = new EObjectTypeRelationCondition(
					InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM);
			EObjectCondition valueCondition = new EObjectCondition() {
				@Override
				public boolean isSatisfied(final EObject eObject) {
					boolean b = ((SynchronizableObject) eObject).getSynchronizationMetaData() != null
							&& infoUnit.getSynchronizationMetaData().getUrl().equals(
									((SynchronizableObject) eObject).getSynchronizationMetaData()
											.getUrl())
							&& ((SynchronizableObject) eObject).getSynchronizationMetaData()
									.getSyncState() != SynchronizationState.IGNORED
							&& ((SynchronizableObject) eObject).getSynchronizationMetaData()
									.getSyncState() != SynchronizationState.NOT_ADDED;
					return b;
				}
			};
			SELECT select = new SELECT(new FROM(localContainer), new WHERE(typeRelationCondition
					.AND(valueCondition)));
			IQueryResult execute = select.execute();
			Set<? extends EObject> eObjects = execute.getEObjects();
			if (eObjects.size() > 0) {
				Iterator<? extends EObject> iterator = eObjects.iterator();
				while (iterator.hasNext()) {
					EObject eObject = iterator.next();
					return ((InformationUnitListItem) eObject).getId();
				}
			}
		}
		return IdFactory.createNewId(null);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#createDiffModel(
	 * org.remus.infomngmnt.ChangeSetItem, org.remus.infomngmnt.Category)
	 */
	public DiffModel createDiffModel(final ChangeSetItem changeSetItem,
			final Category targetCategory) throws ChangeSetException {
		return createDiffModel(changeSetItem, targetCategory, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#createDiffModel(
	 * org.remus.infomngmnt.ChangeSetItem, org.remus.infomngmnt.Category,
	 * boolean)
	 */
	public synchronized DiffModel createDiffModel(final ChangeSetItem changeSetItem,
			final Category targetCategory, final boolean replaceAllLocal) throws ChangeSetException {
		DiffModel returnValue = null;
		/*
		 * We have to unset all local ids and replace them with the remote-hash.
		 */
		Category remoteCopy = (Category) EcoreUtil
				.copy(changeSetItem.getRemoteConvertedContainer());
		Category copy = (Category) EcoreUtil.copy(targetCategory);

		CategoryUtil.recursivelySortId(remoteCopy);
		CategoryUtil.recursivelySortId(copy);
		filterIgnoredItems(copy);

		/*
		 * If a repository is a readonly connector, we have to customize the
		 * repository. We're deleting all content within the local container and
		 * removing all elements that are local present in the remote container.
		 * The result is a container that contains only the item that are on the
		 * repository and not local (including the local deleted entries).
		 */
		if (!replaceAllLocal
				&& copy.getSynchronizationMetaData() != null
				&& copy.getSynchronizationMetaData().getRepositoryId() != null
				&& this.repositoryService.getRepositoryById(copy.getSynchronizationMetaData()
						.getRepositoryId()) != null
				&& this.extensionService.getItemByLocalObject(targetCategory).onlyDownload()) {
			deleteAllNonEmptyRemoteItems(remoteCopy);
			copy.getChildren().clear();
			copy.getInformationUnit().clear();
		}

		/*
		 * The root containers name must not be integrated within the compare.
		 */
		copy.setLabel(remoteCopy.getLabel());

		TreeIterator<EObject> eAllContents = copy.eAllContents();
		while (eAllContents.hasNext()) {
			EObject eObject = eAllContents.next();
			if (eObject instanceof InformationUnitListItem) {
				/*
				 * Additionally we have to unset the workspace-path
				 */
				eObject
						.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH);
				eObject.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__UNREAD);

			}
			if (eObject instanceof SynchronizationMetadata) {
				/*
				 * 
				 */
				(eObject)
						.eUnset(InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION);
			}
		}
		/*
		 * If we replace all we have to force differences on every item. That's
		 * done with a hash.
		 */
		if (replaceAllLocal) {
			TreeIterator<EObject> eAllContents2 = copy.eAllContents();
			while (eAllContents2.hasNext()) {
				EObject eObject = eAllContents2.next();
				if (eObject instanceof SynchronizationMetadata) {
					/*
					 * Additionally we have to unset the workspace-path
					 */
					eObject.eSet(InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH, "");
				}
			}
		}

		String compareId = IdFactory.createNewId(null);
		Resource res1 = new ResourceImpl(URI.createFileURI(RemoteActivator.getDefault()
				.getStateLocation().append(COMPARE_FOLDER).append(compareId)
				.addFileExtension("xml").toOSString()));
		res1.getContents().add(remoteCopy);

		Resource res2 = new ResourceImpl(URI.createFileURI(RemoteActivator.getDefault()
				.getStateLocation().append(COMPARE_FOLDER).append(compareId + "_local")
				.addFileExtension("xml").toOSString()));
		res2.getContents().add(copy);

		MatchModel match;
		try {
			match = MatchService.doMatch(copy, remoteCopy, Collections.<String, Object> emptyMap());
			returnValue = DiffService.doDiff(match, false);

		} catch (Exception e) {
			throw new ChangeSetException(StatusCreator.newStatus("Error computing difference", e));
		}

		return returnValue;
		// Computing differences
	}

	private void deleteAllNonEmptyRemoteItems(final Category remoteCopy) {
		EList<Category> children = remoteCopy.getChildren();
		List<Category> cat2delete = new ArrayList<Category>();
		List<InformationUnitListItem> items2delete = new ArrayList<InformationUnitListItem>();
		for (Category category : children) {
			if (CategoryUtil.getCategoryById(category.getId()) != null) {
				cat2delete.add(category);
			} else {
				deleteAllNonEmptyRemoteItems(category);
			}
		}
		EList<InformationUnitListItem> informationUnit = remoteCopy.getInformationUnit();
		for (InformationUnitListItem informationUnitListItem : informationUnit) {
			if (this.applicationService.getItemByIdLocalDeletedIncluded(informationUnitListItem
					.getId(), new NullProgressMonitor()) != null) {
				items2delete.add(informationUnitListItem);
			}
		}
		for (Category cat : cat2delete) {
			remoteCopy.getChildren().remove(cat);
		}
		for (InformationUnitListItem listItem : items2delete) {
			remoteCopy.getInformationUnit().remove(listItem);
		}

	}

	private void filterIgnoredItems(final Category copy) {
		List<InformationUnitListItem> informationUnit = new ArrayList<InformationUnitListItem>(copy
				.getInformationUnit());
		for (InformationUnitListItem informationUnitListItem : informationUnit) {
			if (informationUnitListItem.getSynchronizationMetaData() == null
					|| informationUnitListItem.getSynchronizationMetaData().getSyncState() == SynchronizationState.IGNORED) {
				copy.getInformationUnit().remove(informationUnitListItem);
			}
		}
		EList<Category> children = copy.getChildren();
		List<Category> arrayList = new ArrayList<Category>(children);
		for (Category category : arrayList) {
			if (category.getSynchronizationMetaData().getSyncState() == SynchronizationState.IGNORED) {
				copy.getChildren().remove(category);
			}
		}
		EList<Category> children2 = copy.getChildren();
		for (Category category : children2) {
			filterIgnoredItems(category);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#prepareSyncActions
	 * (org.eclipse.emf.common.util.EList, org.remus.infomngmnt.ChangeSetItem,
	 * org.remus.infomngmnt.Category)
	 */
	public void prepareSyncActions(final EList<DiffElement> diffModel, final ChangeSetItem item,
			final Category targetCategory) {
		for (DiffElement diffElement : diffModel) {
			/*
			 * The diff model has an element which is not local present, We have
			 * to check if the model was deleted local.
			 */
			if (diffElement instanceof ModelElementChangeRightTarget) {
				final ModelElementChangeRightTarget addOp = (ModelElementChangeRightTarget) diffElement;
				EObject rightElement = addOp.getRightElement();
				if (rightElement instanceof AbstractInformationUnit) {
					item.getSyncObjectActionMap().put((SynchronizableObject) rightElement,
							SynchronizationAction.ADD_LOCAL);

				} else if (rightElement instanceof Category) {
					item.getSyncCategoryActionMap().put((Category) rightElement,
							SynchronizationAction.ADD_LOCAL);
				}
			}
			if (diffElement instanceof DiffGroup) {
				diffElement.getSubDiffElements();
				prepareSyncActions(diffElement.getSubDiffElements(), item, targetCategory);
			}
			/*
			 * An update on an attribute was detected. We have to find the
			 * parent SynchronizableObject.
			 */
			if (diffElement instanceof UpdateAttribute) {
				final UpdateAttribute addOp = (UpdateAttribute) diffElement;
				EObject rightElement = addOp.getRightElement();

				EObject parentByClass = ModelUtil.getParentByClass(rightElement,
						InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM);
				if (parentByClass == null) {
					parentByClass = ModelUtil.getParentByClass(rightElement,
							InfomngmntPackage.Literals.CATEGORY);
					if (parentByClass != null) {
						Category categoryById = CategoryUtil
								.getCategoryById(((Category) parentByClass).getId());
						if (addOp.getAttribute() == InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH) {
							if (categoryById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_EDITED) {
								item.getSyncCategoryActionMap().put((Category) parentByClass,
										SynchronizationAction.RESOLVE_CONFLICT);
							} else {
								item.getSyncObjectActionMap().put((Category) parentByClass,
										SynchronizationAction.REPLACE_LOCAL);
							}

						} else {
							if (categoryById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_EDITED) {
								item.getSyncCategoryActionMap().put((Category) parentByClass,
										SynchronizationAction.REPLACE_REMOTE);
							} else if (categoryById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_DELETED) {
								item.getSyncCategoryActionMap().put((Category) parentByClass,
										SynchronizationAction.DELETE_REMOTE);
							} else {
								item.getSyncCategoryActionMap().put((Category) parentByClass,
										SynchronizationAction.REPLACE_LOCAL);
							}
						}
					}
				}
				if (diffElement instanceof MoveModelElement) {
					System.out.println("Move");
				} else {
					/*
					 * Search if the IU was edited locally. If so we have to
					 * resolve a conflict.
					 */
					SynchronizableObject itemById = null;
					if (parentByClass instanceof AbstractInformationUnit) {
						itemById = this.applicationService.getItemByIdLocalDeletedIncluded(
								((AbstractInformationUnit) parentByClass).getId(),
								new NullProgressMonitor());
					} else {
						itemById = CategoryUtil.getCategoryById(((Category) parentByClass).getId());
					}

					if (addOp.getAttribute() == InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH) {
						if (itemById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_EDITED) {
							if (itemById instanceof Category) {
								item.getSyncCategoryActionMap().put((Category) parentByClass,
										SynchronizationAction.RESOLVE_CONFLICT);
							} else {
								item.getSyncObjectActionMap().put(
										(SynchronizableObject) parentByClass,
										SynchronizationAction.RESOLVE_CONFLICT);
							}
						} else {
							if (itemById instanceof Category) {
								item.getSyncCategoryActionMap().put((Category) parentByClass,
										SynchronizationAction.REPLACE_LOCAL);
							} else {
								item.getSyncObjectActionMap().put(
										(SynchronizableObject) parentByClass,
										SynchronizationAction.REPLACE_LOCAL);
							}
						}

					} else {
						if (item.getSyncObjectActionMap().get(parentByClass) == null) {
							if (itemById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_EDITED) {

								item.getSyncObjectActionMap().put(
										(SynchronizableObject) parentByClass,
										SynchronizationAction.REPLACE_REMOTE);

							} else if (itemById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_DELETED) {
								if (parentByClass instanceof Category) {
									item.getSyncCategoryActionMap().put((Category) parentByClass,
											SynchronizationAction.DELETE_REMOTE);
								} else {
									item.getSyncObjectActionMap().put(
											(SynchronizableObject) parentByClass,
											SynchronizationAction.DELETE_REMOTE);
								}
							} else {
								item.getSyncObjectActionMap().put(
										(SynchronizableObject) parentByClass,
										SynchronizationAction.REPLACE_LOCAL);
							}
						}
					}
				}
			}
			/*
			 * Something was deleted at the repository. Or something was added
			 * locally and is not present at the repository.
			 */
			if (diffElement instanceof ModelElementChangeLeftTarget) {
				ModelElementChangeLeftTarget removeOp = (ModelElementChangeLeftTarget) diffElement;
				EObject leftElement = removeOp.getLeftElement();
				if (leftElement instanceof SynchronizationMetadata) {
					leftElement = leftElement.eContainer();
				}
				if (((SynchronizableObject) leftElement).getSynchronizationMetaData() != null
						&& ((SynchronizableObject) leftElement).getSynchronizationMetaData()
								.getSyncState() == SynchronizationState.NOT_ADDED) {
					if (leftElement instanceof InformationUnitListItem) {
						item.getSyncObjectActionMap().put((SynchronizableObject) leftElement,
								SynchronizationAction.ADD_REMOTE);
					} else if (leftElement instanceof Category) {
						item.getSyncCategoryActionMap().put((Category) leftElement,
								SynchronizationAction.ADD_REMOTE);
					}
				} else {
					if (leftElement instanceof InformationUnitListItem) {
						item.getSyncObjectActionMap().put((SynchronizableObject) leftElement,
								SynchronizationAction.DELETE_LOCAL);
					} else if (leftElement instanceof Category) {
						item.getSyncCategoryActionMap().put((Category) leftElement,
								SynchronizationAction.DELETE_LOCAL);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#replaceAllLocal(
	 * org.remus.infomngmnt.ChangeSetItem)
	 */
	public void replaceAllLocal(final ChangeSetItem changeSetItem2) {
		EMap<Category, SynchronizationAction> syncCategoryActionMap = changeSetItem2
				.getSyncCategoryActionMap();
		if (syncCategoryActionMap != null) {
			Set<Category> keySet = syncCategoryActionMap.keySet();
			for (Category category : keySet) {
				syncCategoryActionMap.put(category, SynchronizationAction.REPLACE_LOCAL);
			}
		}
		EMap<SynchronizableObject, SynchronizationAction> syncObjectActionMap = changeSetItem2
				.getSyncObjectActionMap();
		if (syncObjectActionMap != null) {
			Set<SynchronizableObject> keySet = syncObjectActionMap.keySet();
			for (SynchronizableObject synchronizableObject : keySet) {
				syncObjectActionMap.put(synchronizableObject, SynchronizationAction.REPLACE_LOCAL);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#committAllLocal(
	 * org.remus.infomngmnt.ChangeSetItem)
	 */
	public void committAllLocal(final ChangeSetItem changeSetItem2) {
		EMap<Category, SynchronizationAction> syncCategoryActionMap = changeSetItem2
				.getSyncCategoryActionMap();
		if (syncCategoryActionMap != null) {
			Set<Category> keySet = syncCategoryActionMap.keySet();
			for (Category category : keySet) {
				SynchronizationAction synchronizationAction = syncCategoryActionMap.get(category);
				switch (synchronizationAction) {
				case RESOLVE_CONFLICT:
				case REPLACE_LOCAL:
				case ADD_LOCAL:
				case DELETE_LOCAL:
					syncCategoryActionMap.remove(category);
				default:
					break;
				}
			}
		}
		EMap<SynchronizableObject, SynchronizationAction> syncObjectActionMap = changeSetItem2
				.getSyncObjectActionMap();
		if (syncObjectActionMap != null) {
			Set<SynchronizableObject> keySet = new HashSet<SynchronizableObject>(
					syncObjectActionMap.keySet());
			for (SynchronizableObject object : keySet) {
				SynchronizationAction synchronizationAction = syncObjectActionMap.get(object);
				switch (synchronizationAction) {
				case RESOLVE_CONFLICT:
				case REPLACE_LOCAL:
				case ADD_LOCAL:
				case DELETE_LOCAL:
					syncObjectActionMap.remove(object);
				default:
					break;
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.remote.sync.IChangeHandler#updateFromRemote
	 * (org.remus.infomngmnt.ChangeSetItem)
	 */
	public void updateFromRemote(final ChangeSetItem changeSet) {
		EMap<Category, SynchronizationAction> syncCategoryActionMap = changeSet
				.getSyncCategoryActionMap();
		if (syncCategoryActionMap != null) {
			Set<Category> keySet = syncCategoryActionMap.keySet();
			for (Category category : keySet) {
				SynchronizationAction synchronizationAction = syncCategoryActionMap.get(category);
				switch (synchronizationAction) {
				case RESOLVE_CONFLICT:
				case ADD_REMOTE:
				case REPLACE_REMOTE:
				case DELETE_REMOTE:
					syncCategoryActionMap.remove(category);
				default:
					break;
				}
			}
		}
		EMap<SynchronizableObject, SynchronizationAction> syncObjectActionMap = changeSet
				.getSyncObjectActionMap();
		if (syncObjectActionMap != null) {
			Set<SynchronizableObject> keySet = new HashSet<SynchronizableObject>(
					syncObjectActionMap.keySet());
			for (SynchronizableObject object : keySet) {
				SynchronizationAction synchronizationAction = syncObjectActionMap.get(object);
				switch (synchronizationAction) {
				case RESOLVE_CONFLICT:
				case ADD_REMOTE:
				case REPLACE_REMOTE:
				case DELETE_REMOTE:
				case DELETE_LOCAL:
					syncObjectActionMap.remove(object);
				default:
					break;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.core.remote.sync.IChangeHandler#
	 * syncSingleInformationUnit(org.remus.infomngmnt.InformationUnitListItem,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public ChangeSet syncSingleInformationUnit(final InformationUnitListItem item,
			final IProgressMonitor monitor) throws ChangeSetException {
		ChangeSet changeSet = InfomngmntFactory.eINSTANCE.createChangeSet();
		if (item.getSynchronizationMetaData() != null
				&& item.getSynchronizationMetaData().getSyncState() != SynchronizationState.IGNORED
				&& item.getSynchronizationMetaData().getSyncState() != SynchronizationState.LOCAL_DELETED
				&& item.getSynchronizationMetaData().getSyncState() != SynchronizationState.NOT_ADDED) {
			SynchronizationMetadata metaData = item.getSynchronizationMetaData();

			/*
			 * At first we call the remote-repository to find the appended
			 * object within that repository.
			 */
			RemoteRepository remoteRepository = this.repositoryService.getRepositoryById(metaData
					.getRepositoryId());
			RemoteObject remoteObject;
			try {
				remoteObject = this.extensionService.getItemByLocalObject(item)
						.getRemoteObjectBySynchronizableObject(item, monitor);
				if (remoteObject == null) {
					/*
					 * Item was removed on the repository
					 */
					throw new ChangeSetException(StatusCreator
							.newStatus("The requested item was not found on the repository."));
				}
			} catch (RemoteException e) {
				throw new ChangeSetException(StatusCreator.newStatus(
						"Error creating changeset for information unit", e));
			}
			changeSet.setRepository(remoteRepository);

			/*
			 * We construct a copy of the local container, the category with
			 * only this one item.
			 */
			Category localCopiedContainer = (Category) EcoreUtil.copy(item.eContainer());
			localCopiedContainer.getChildren().clear();
			localCopiedContainer.getInformationUnit().clear();
			localCopiedContainer.getInformationUnit().add(
					(InformationUnitListItem) EcoreUtil.copy(item));
			changeSet.setTargetCategory(localCopiedContainer);

			RemoteObject copiedItem = (RemoteObject) EcoreUtil.copy(remoteObject);

			ChangeSetItem createChangeSetItem = InfomngmntFactory.eINSTANCE.createChangeSetItem();
			changeSet.getChangeSetItems().add(createChangeSetItem);
			createChangeSetItem.setLocalContainer(localCopiedContainer);

			/*
			 * Based on the local container we create a remote container which
			 * is always identical. In this remote container we're adding a
			 * comparable item for the Diffprocessor to populate the needed
			 * synchronization actions.
			 */
			Category copiedRemoteContainer = (Category) EcoreUtil.copy(localCopiedContainer);
			copiedRemoteContainer.getInformationUnit().clear();
			createChangeSetItem.setRemoteConvertedContainer(copiedRemoteContainer);
			try {
				fillInfoObject(createChangeSetItem, copiedItem, remoteRepository,
						copiedRemoteContainer);
			} catch (RemoteException e) {
				throw new ChangeSetException(StatusCreator.newStatus("Error creating changeset", e));
			}

		}
		return changeSet;
	}

	public void setRepositoryExtensionService(final IRepositoryExtensionService service) {
		this.extensionService = service;

	}

	public void setRepositoryService(final IRepositoryService service) {
		this.repositoryService = service;

	}

	public void setApplicationService(final IApplicationModel service) {
		this.applicationService = service;

	}

}
