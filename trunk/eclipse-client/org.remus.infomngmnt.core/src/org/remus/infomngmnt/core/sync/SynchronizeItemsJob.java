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

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.model.ApplicationModelPool;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizeItemsJob extends AbstractJob {

	/**
	 * 
	 */
	public SynchronizeItemsJob() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.jobs.AbstractJob#run(org.eclipse.core.runtime
	 * .IProgressMonitor)
	 */
	@Override
	public Notification run(final IProgressMonitor monitor) {
		EObjectCondition typeRelationCondition = new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject eObject) {
				return eObject.eClass() == InfomngmntPackage.Literals.CATEGORY;
			}
		};

		EObjectCondition isSyncRootFolderCondition = new EObjectCondition() {

			@Override
			public boolean isSatisfied(final EObject eObject) {
				return ((Category) eObject).getSynchronizationMetaData() != null
						&& eObject.eContainer() != null
						&& ((SynchronizableObject) eObject.eContainer())
								.getSynchronizationMetaData() == null;
			}

		};
		Notification returnValue = InfomngmntFactory.eINSTANCE.createNotification();
		returnValue.setMessage("Synchronizing all folders");

		SELECT select = new SELECT(new FROM(ApplicationModelPool.getInstance().getModel()
				.getRootCategories()), new WHERE(typeRelationCondition
				.AND(isSyncRootFolderCondition)));
		Set<? extends EObject> eObjects = select.execute().getEObjects();
		for (EObject eObject : eObjects) {
			final ChangeSetManager manager = new ChangeSetManager();
			Notification sync = InfomngmntFactory.eINSTANCE.createNotification();
			int updateCount = 0;
			try {
				ChangeSet createChangeSet = manager.createChangeSet((Category) eObject, monitor);
				if (createChangeSet != null && createChangeSet.getChangeSetItems().size() > 0) {
					ChangeSetItem changeSetItem = createChangeSet.getChangeSetItems().get(0);
					DiffModel createDiffModel = manager.createDiffModel(changeSetItem,
							(Category) eObject);
					manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem,
							(Category) eObject);
					manager.updateFromRemote(changeSetItem);
					updateCount = changeSetItem.getSyncObjectActionMap().size()
							+ changeSetItem.getSyncCategoryActionMap().size();

					sync.setMessage(NLS.bind("Updated {0} elements on item \"{1}\"", updateCount,
							((Category) eObject).getLabel()));
					ChangeSetExecutor executor = new ChangeSetExecutor();
					executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem,
							monitor, (Category) eObject);
					sync.setSeverity(Severity.OK);
					sync.setImportance(updateCount > 0 ? NotificationImportance.MEDIUM
							: NotificationImportance.NONE);

				}
			} catch (Exception e) {
				sync.setSeverity(Severity.ERROR);
				sync.setDetails(e.getMessage());
				sync.setImportance(NotificationImportance.HIGH);
			}
			returnValue.getChildren().add(sync);

		}
		return returnValue;
	}

}
