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
import org.eclipse.emf.compare.diff.metamodel.AddModelElement;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.MoveModelElement;
import org.eclipse.emf.compare.diff.metamodel.RemoveModelElement;
import org.eclipse.emf.compare.diff.metamodel.UpdateAttribute;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EObject;
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
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.IdFactory;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.remote.RemoteUtil;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ChangeSetManager {

	public static final int MODE_SYNC = 1;
	public static final int MODE_CHECKOUT_REPLACE = 2;

	private static final String COMPARE_FOLDER = "compare";

	public ChangeSet createChangeSet(final Category cat, final IProgressMonitor monitor)
			throws CoreException {
		SynchronizationMetadata metaData = cat.getSynchronizationMetaData();
		RemoteRepository remoteRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(metaData.getRepositoryId());
		RemoteObject remoteObject = remoteRepository.getRepositoryImplementation()
				.getRemoteObjectBySynchronizableObject(cat, monitor);
		if (remoteObject != null && remoteObject instanceof RemoteContainer) {
			return createChangeSet(cat, Collections
					.<RemoteContainer> singletonList((RemoteContainer) remoteObject),
					remoteRepository, ChangeSetManager.MODE_CHECKOUT_REPLACE, monitor);
		}
		return null;
	}

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
			monitor.subTask("Requesting objects from repository");
			IRepository repositoryImplementation = repository.getRepositoryImplementation();
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

	/**
	 * This is a recursiv method which gets the children from the repository
	 * implementation and converts the result into data-objects which can be
	 * appended to the local data- structure. At the same time a
	 * {@link SynchronizationMetadata} is created which holds important data of
	 * the repository.
	 * 
	 * @param changeSetItem
	 * @param remoteObject2
	 * @param remoteRepository
	 * @param parentCategory
	 * @param mode
	 * @throws RemoteException
	 */
	public void fillRemoteContainer(final ChangeSetItem changeSetItem,
			final RemoteObject remoteObject2, final RemoteRepository remoteRepository,
			final Category parentCategory, final IProgressMonitor monitor) throws RemoteException {
		monitor.subTask(NLS.bind("Found item \"{0}\"", remoteObject2.getName()));
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

			RemoteObject[] children = remoteRepository.getRepositoryImplementation().getChildren(
					new NullProgressMonitor(), (RemoteContainer) remoteObject2, false);
			for (RemoteObject newChildren : children) {
				fillRemoteContainer(changeSetItem, newChildren, remoteRepository, createCategory,
						monitor);
			}
		} else {
			InformationUnitListItem createInformationUnitListItem = InfomngmntFactory.eINSTANCE
					.createInformationUnitListItem();

			// transfer the needed information

			createInformationUnitListItem.setLabel(remoteObject2.getName());
			createInformationUnitListItem.setType(remoteRepository.getRepositoryImplementation()
					.getTypeIdByObject(remoteObject2));

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
			InformationUnit prefetchedInformationUnit = remoteRepository
					.getRepositoryImplementation().getPrefetchedInformationUnit(remoteObject2);
			changeSetItem.getRemoteFullObjectMap().put(createInformationUnitListItem,
					prefetchedInformationUnit);
			createInformationUnitListItem
					.setId(findId(createInformationUnitListItem, changeSetItem));
			if (prefetchedInformationUnit != null) {
				prefetchedInformationUnit.setId(createInformationUnitListItem.getId());
			}
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
					return ((SynchronizableObject) eObject).getSynchronizationMetaData() != null
							&& infoUnit.getSynchronizationMetaData().getUrl().equals(
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
					return ((InformationUnitListItem) eObject).getId();
				}
			}
		}
		return IdFactory.createNewId(null);

	}

	public DiffModel createDiffModel(final ChangeSetItem changeSetItem,
			final Category targetCategory) throws ChangeSetException {
		return createDiffModel(changeSetItem, targetCategory, false);
	}

	public DiffModel createDiffModel(final ChangeSetItem changeSetItem,
			final Category targetCategory, final boolean replaceAllLocal) throws ChangeSetException {
		DiffModel returnValue = null;
		/*
		 * We have to unset all local ids and replace them with the remote-hash.
		 */
		Category remoteCopy = (Category) EcoreUtil
				.copy(changeSetItem.getRemoteConvertedContainer());

		Category copy = (Category) EcoreUtil.copy(targetCategory);
		TreeIterator<EObject> eAllContents = copy.eAllContents();
		while (eAllContents.hasNext()) {
			EObject eObject = eAllContents.next();
			if (eObject instanceof InformationUnitListItem) {
				/*
				 * Additionally we have to unset the workspace-path
				 */
				eObject
						.eUnset(InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH);
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

		EditingUtil.getInstance().saveObjectToResource(
				remoteCopy,
				InfomngmntEditPlugin.getPlugin().getStateLocation().append(COMPARE_FOLDER).append(
						IdFactory.createNewId(null)).addFileExtension("xml").toOSString());

		EditingUtil.getInstance().saveObjectToResource(
				copy,
				InfomngmntEditPlugin.getPlugin().getStateLocation().append(COMPARE_FOLDER).append(
						IdFactory.createNewId(null)).addFileExtension("xml").toOSString());

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

	public void prepareSyncActions(final EList<DiffElement> diffModel, final ChangeSetItem item,
			final Category targetCategory) {
		for (DiffElement diffElement : diffModel) {
			/*
			 * The diff model has an element which is not local present, We have
			 * to check if the model was deleted local.
			 */
			if (diffElement instanceof AddModelElement) {
				final AddModelElement addOp = (AddModelElement) diffElement;
				EObject rightElement = addOp.getRightElement();
				if (rightElement instanceof AbstractInformationUnit) {

					item.getSyncObjectActionMap().put((SynchronizableObject) rightElement,
							SynchronizationAction.ADD_LOCAL);

				} else if (rightElement instanceof Category) {
					// TODO implement for deleted items

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
						itemById = ApplicationModelPool.getInstance().getItemById(
								((AbstractInformationUnit) parentByClass).getId(),
								new NullProgressMonitor());
					} else {
						itemById = CategoryUtil.getCategoryById(((Category) parentByClass).getId());
					}

					if (addOp.getAttribute() == InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH) {
						if (itemById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_EDITED) {
							item.getSyncObjectActionMap().put((SynchronizableObject) parentByClass,
									SynchronizationAction.RESOLVE_CONFLICT);
						} else {
							item.getSyncObjectActionMap().put((SynchronizableObject) parentByClass,
									SynchronizationAction.REPLACE_LOCAL);
						}

					} else {
						if (itemById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_EDITED) {
							item.getSyncObjectActionMap().put((SynchronizableObject) parentByClass,
									SynchronizationAction.REPLACE_REMOTE);
						} else if (itemById.getSynchronizationMetaData().getSyncState() == SynchronizationState.LOCAL_DELETED) {
							item.getSyncObjectActionMap().put((SynchronizableObject) parentByClass,
									SynchronizationAction.DELETE_REMOTE);
						} else {
							item.getSyncObjectActionMap().put((SynchronizableObject) parentByClass,
									SynchronizationAction.REPLACE_LOCAL);
						}
					}
				}
			}
			/*
			 * Something was deleted at the repository. Or something was added
			 * locally and is not present at the repository.
			 */
			if (diffElement instanceof RemoveModelElement) {
				RemoveModelElement removeOp = (RemoveModelElement) diffElement;
				EObject leftElement = removeOp.getLeftElement();
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

}
