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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.remote.internal.ChangeSetManager;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.model.remote.IChangeHandler;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommitElementsJob extends AbstractSynchronizationJob {

	private final List<SynchronizableObject> objects;
	private List<SynchronizableObject> filteredList;

	public CommitElementsJob(final List<SynchronizableObject> objects) {
		super(NLS.bind("Committing {0} element(s)", objects.size()));
		this.objects = objects;
		doPrepare();
	}

	@Override
	public void doPrepare() {
		this.filteredList = CollectionUtils.filter(this.objects,
				new CollectionFilter<SynchronizableObject>() {
					public boolean select(final SynchronizableObject item) {
						return !ModelUtil.containsParent(CommitElementsJob.this.objects, item);
					}
				});

	}

	@Override
	protected List<SynchronizableObject> getAffectedObjects() {
		return this.filteredList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus doRun(final IProgressMonitor monitor) {
		monitor.beginTask(getName(), this.objects.size() == 1 ? IProgressMonitor.UNKNOWN
				: this.objects.size());
		for (SynchronizableObject synchronizableObject : this.filteredList) {

			monitor.setTaskName(NLS.bind("Try to commit \"{0}\" to remote-repository",
					synchronizableObject.getSynchronizationMetaData().getUrl()));
			String repositoryId = synchronizableObject.getSynchronizationMetaData()
					.getRepositoryId();
			IRepositoryService service = RemoteActivator.getDefault().getServiceTracker()
					.getService(IRepositoryService.class);
			RemoteRepository localRepository = this.repositoryService
					.getRepositoryById(repositoryId);
			RemoteObject remoteObject = null;
			try {
				remoteObject = this.extensionService.getItemByLocalObject(synchronizableObject)
						.getRemoteObjectBySynchronizableObject(synchronizableObject, monitor);
			} catch (RemoteException e1) {
				return e1.getStatus();
			}
			if (remoteObject == null) {

				/*
				 * something went wrong. Show a notification here. It's the best
				 * to do a synchronization at this time.
				 */

			} else if (remoteObject instanceof RemoteContainer
					&& synchronizableObject instanceof Category) {
				monitor.setTaskName(NLS.bind(
						"Found object in repository. Preparing replace for \"{0}\"", remoteObject
								.getName()));
				Category targetCategory = (Category) synchronizableObject;
				ChangeSet changeSet;
				IChangeHandler changeSetManager = new ChangeSetManager();
				try {
					changeSet = changeSetManager.createChangeSet(targetCategory, monitor);
					EList<ChangeSetItem> changeSetItems = changeSet.getChangeSetItems();
					for (ChangeSetItem changeSetItem2 : changeSetItems) {
						DiffModel createDiffModel = changeSetManager.createDiffModel(
								changeSetItem2, targetCategory, true);
						changeSetManager.prepareSyncActions(createDiffModel.getOwnedElements(),
								changeSetItem2, targetCategory);
						changeSetManager.committAllLocal(changeSetItem2);
						ChangeSetExecutor changeSetExecutor = new ChangeSetExecutor();
						changeSetExecutor.synchronize(createDiffModel.getOwnedElements(),
								changeSetItem2, monitor, targetCategory);
						changeSetExecutor.dispose();

					}
				} catch (CoreException e) {
					return e.getStatus();
				}

			} else if (remoteObject instanceof RemoteObject
					&& synchronizableObject instanceof InformationUnitListItem) {
				// TODO implement replacement of single info units.
			}
			monitor.worked(1);
		}
		IEditingHandler service = RemoteActivator.getDefault().getServiceTracker().getService(
				IEditingHandler.class);
		service.getNavigationEditingDomain().getCommandStack().flush();
		RemoteActivator.getDefault().getServiceTracker().ungetService(service);

		monitor.done();
		return Status.OK_STATUS;
	}

}
