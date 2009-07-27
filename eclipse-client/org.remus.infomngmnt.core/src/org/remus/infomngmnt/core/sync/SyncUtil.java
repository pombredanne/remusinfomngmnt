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

import java.util.Date;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;

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

	public static Notification synchronizeCategory(final Category eObject,
			final IProgressMonitor monitor) throws CoreException {
		ChangeSetManager manager = new ChangeSetManager();
		Notification returnValue = InfomngmntFactory.eINSTANCE.createNotification();
		ChangeSet createChangeSet = manager.createChangeSet(eObject, monitor);
		if (createChangeSet != null && createChangeSet.getChangeSetItems().size() > 0) {
			ChangeSetItem changeSetItem = createChangeSet.getChangeSetItems().get(0);
			DiffModel createDiffModel = manager.createDiffModel(changeSetItem, eObject);
			manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem, eObject);
			manager.updateFromRemote(changeSetItem);
			ChangeSetExecutor executor = new ChangeSetExecutor();
			executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem, monitor,
					eObject);
			Set<SynchronizableObject> keySet = changeSetItem.getSyncObjectActionMap().keySet();
			for (SynchronizableObject synchronizableObject : keySet) {
				Notification notification = InfomngmntFactory.eINSTANCE.createNotification();
				InformationUnitListItem item = (InformationUnitListItem) synchronizableObject;
				IInfoType infoType = InformationExtensionManager.getInstance().getInfoTypeByType(
						item.getType());
				if (infoType != null) {
					notification.setImage(infoType.getImage());
				}
				notification.setTimeStamp(new Date());
				notification.setMessage(NLS.bind("Updated \"{0}\"", item.getLabel()));
				notification.getAffectedInfoUnitIds().add(item.getId());
				notification.setImportance(NotificationImportance.MEDIUM);
				// notification.setSource(synchronizableObject.getSynchronizationMetaData()
				// .getRepositoryId());
				notification.setSeverity(Severity.OK);
				returnValue.getChildren().add(notification);
			}
		}
		return returnValue;

	}

}
