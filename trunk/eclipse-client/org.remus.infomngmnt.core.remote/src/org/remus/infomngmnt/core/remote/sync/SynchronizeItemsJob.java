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

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.services.ISynchronizationManager;
import org.remus.infomngmnt.core.services.IApplicationModel;

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
	public List<Notification> run(final IProgressMonitor monitor) {
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
		IApplicationModel appService = RemoteActivator.getDefault().getServiceTracker().getService(
				IApplicationModel.class);
		ISynchronizationManager syncService = RemoteActivator.getDefault().getServiceTracker()
				.getService(ISynchronizationManager.class);

		SELECT select = new SELECT(new FROM(appService.getModel().getRootCategories()), new WHERE(
				typeRelationCondition.AND(isSyncRootFolderCondition)));
		Set<? extends EObject> eObjects = select.execute().getEObjects();
		for (final EObject eObject : eObjects) {
			syncService.scheduleSynchronization((Category) eObject);
		}
		return returnValue.getChildren();
	}

}
