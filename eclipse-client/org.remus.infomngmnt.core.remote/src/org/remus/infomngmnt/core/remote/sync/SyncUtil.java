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

package org.remus.infomngmnt.core.remote.sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.compare.diff.metamodel.AttributeChange;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.core.remote.AbstractExtensionRepository;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.model.remote.IRepository;
import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncUtil {

	public static SynchronizationAction getAction(final ChangeSetItem item, final EObject object) {
		EMap<Category, SynchronizationAction> syncActions = item.getSyncCategoryActionMap();
		Set<Category> keySet = syncActions.keySet();
		for (Category synchronizableObject : keySet) {
			if (synchronizableObject.eClass() == object.eClass()) {
				if (synchronizableObject.getId().equals(((Category) object).getId())) {
					return syncActions.get(synchronizableObject);
				}
			}

		}
		EMap<SynchronizableObject, SynchronizationAction> syncObjectActionMap = item
				.getSyncObjectActionMap();
		Set<SynchronizableObject> keySet2 = syncObjectActionMap.keySet();
		for (SynchronizableObject synchronizableObject2 : keySet2) {
			if (synchronizableObject2.eClass() == object.eClass()) {
				if (synchronizableObject2 instanceof AbstractInformationUnit
						&& ((AbstractInformationUnit) synchronizableObject2).getId().equals(
								((AbstractInformationUnit) object).getId())) {
					return syncObjectActionMap.get(synchronizableObject2);
				}
			}
		}
		return null;
	}

	public static InformationUnit getFullObjectFromChangeSet(final ChangeSetItem changeSet,
			final InformationUnitListItem listItem, final IRepository repository,
			final IProgressMonitor monitor) throws RemoteException {
		Set<InformationUnitListItem> keySet = changeSet.getRemoteFullObjectMap().keySet();
		for (InformationUnitListItem informationUnitListItem : keySet) {
			if (informationUnitListItem.getId().equals(listItem.getId())) {
				InformationUnit informationUnit = changeSet.getRemoteFullObjectMap().get(
						informationUnitListItem);
				if (informationUnit == null) {
					informationUnit = repository.getFullObject(listItem, monitor);
				}
				return informationUnit;
			}
		}
		return repository.getFullObject(listItem, monitor);

	}

	public static String getRepositoryId(final InformationUnit item) {

		InformationUnitListItem adapter = (InformationUnitListItem) item
				.getAdapter(InformationUnitListItem.class);
		return adapter.getSynchronizationMetaData().getRepositoryId();

	}

	public static IRepository getRepositoryImplemenationByRepositoryId(final String reproId) {
		RemusServiceTracker serviceTracker = RemoteActivator.getDefault().getServiceTracker();
		IRepositoryService repositoryService = serviceTracker.getService(IRepositoryService.class);
		RemoteRepository repositoryById = repositoryService.getRepositoryById(reproId);
		return getRepositoryImplemenationByRemoteRepository(repositoryById);
	}

	public static IRepository getRepositoryImplemenationByRemoteRepository(
			final RemoteRepository remoteRepository) {
		RemusServiceTracker serviceTracker = RemoteActivator.getDefault().getServiceTracker();
		if (remoteRepository != null) {
			IRepositoryExtensionService repositoryExtensionService = serviceTracker
					.getService(IRepositoryExtensionService.class);
			try {
				AbstractExtensionRepository itemByRepository = repositoryExtensionService
						.getItemByRepository(remoteRepository);
				return itemByRepository;
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				serviceTracker.ungetService(repositoryExtensionService);
			}
		}

		return null;
	}

	public static void changeSynchronizationAction(final ChangeSetItem changeSetItem,
			final SynchronizableObject object, final SynchronizationAction newAction) {
		SynchronizationAction action = getAction(changeSetItem, object);
		switch (action) {
		case ADD_LOCAL:
			if (newAction == SynchronizationAction.REPLACE_REMOTE) {
				if (object instanceof Category) {
					changeSetItem.getSyncCategoryActionMap().put((Category) object,
							SynchronizationAction.DELETE_REMOTE);
				} else if (object instanceof InformationUnitListItem) {
					changeSetItem.getSyncObjectActionMap().put(object,
							SynchronizationAction.DELETE_REMOTE);
				}
				return;
			}
		case DELETE_LOCAL:
			if (newAction == SynchronizationAction.REPLACE_REMOTE) {
				if (object instanceof Category) {
					changeSetItem.getSyncCategoryActionMap().put((Category) object,
							SynchronizationAction.ADD_REMOTE);
				} else if (object instanceof InformationUnitListItem) {
					changeSetItem.getSyncObjectActionMap().put(object,
							SynchronizationAction.ADD_REMOTE);
				}
				return;
			}
		case REPLACE_LOCAL:
		case REPLACE_REMOTE:
		case RESOLVE_CONFLICT:
			if (newAction == SynchronizationAction.REPLACE_REMOTE) {
				if (object instanceof Category) {
					changeSetItem.getSyncCategoryActionMap().put((Category) object,
							SynchronizationAction.REPLACE_REMOTE);
				} else if (object instanceof InformationUnitListItem) {
					changeSetItem.getSyncObjectActionMap().put(object,
							SynchronizationAction.REPLACE_REMOTE);
				}
				return;

			}
			if (newAction == SynchronizationAction.REPLACE_LOCAL) {
				if (object instanceof Category) {
					changeSetItem.getSyncCategoryActionMap().put((Category) object,
							SynchronizationAction.REPLACE_LOCAL);
				} else if (object instanceof InformationUnitListItem) {
					changeSetItem.getSyncObjectActionMap().put(object,
							SynchronizationAction.REPLACE_LOCAL);
				}
				return;
			}
			break;
		case ADD_REMOTE:
			if (newAction == SynchronizationAction.REPLACE_LOCAL) {
				if (object instanceof Category) {
					changeSetItem.getSyncCategoryActionMap().put((Category) object,
							SynchronizationAction.DELETE_LOCAL);
				} else if (object instanceof InformationUnitListItem) {
					changeSetItem.getSyncObjectActionMap().put(object,
							SynchronizationAction.DELETE_LOCAL);
				}
				return;
			}
		case DELETE_REMOTE:
			if (newAction == SynchronizationAction.REPLACE_LOCAL) {
				if (object instanceof Category) {
					changeSetItem.getSyncCategoryActionMap().put((Category) object,
							SynchronizationAction.REPLACE_LOCAL);
				} else if (object instanceof InformationUnitListItem) {
					changeSetItem.getSyncObjectActionMap().put(object,
							SynchronizationAction.REPLACE_LOCAL);
				}
				return;
			}
		default:
			break;
		}
	}

	public static List<DiffElement> computeDiffs(final InformationUnit obj1,
			final InformationUnit obj2) {
		// Matching model elements
		try {
			MatchModel match = MatchService.doMatch(obj1, obj2, Collections
					.<String, Object> emptyMap());
			// Computing differences
			DiffModel diff = DiffService.doDiff(match, false);

			// Merges all differences from model1 to model2
			return new ArrayList<DiffElement>(diff.getOwnedElements());
		} catch (InterruptedException e) {
			return Collections.EMPTY_LIST;
		}
	}

	public static AttributeChange getAttributeChange(final InformationUnit obj1,
			final InformationUnit obj2, final EAttribute attribute) {
		List<DiffElement> computeDiffs = computeDiffs(obj1, obj2);
		return getAttributeChange(computeDiffs, attribute);
	}

	public static AttributeChange getAttributeChange(final List<DiffElement> elements,
			final EAttribute attributeToCompare) {
		for (DiffElement diffElement : elements) {
			if (diffElement instanceof DiffGroup) {
				EList<DiffElement> subDiffElements = diffElement.getSubDiffElements();
				AttributeChange attributeChange = getAttributeChange(subDiffElements,
						attributeToCompare);
				if (attributeChange != null) {
					return attributeChange;
				}

			}
			if (diffElement instanceof AttributeChange) {
				EAttribute attribute = ((AttributeChange) diffElement).getAttribute();
				if (attribute == attributeToCompare) {
					return (AttributeChange) diffElement;
				}
			}
		}
		return null;
	}
}
