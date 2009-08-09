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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommitElementsJob extends Job {

	private final List<SynchronizableObject> objects;

	public CommitElementsJob(final List<SynchronizableObject> objects) {
		super(NLS.bind("Committing {0} element(s)", objects.size()));
		this.objects = objects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		List<SynchronizableObject> filteredList = CollectionUtils.filter(this.objects,
				new CollectionFilter<SynchronizableObject>() {
					public boolean select(final SynchronizableObject item) {
						return !ModelUtil.containsParent(CommitElementsJob.this.objects, item);
					}
				});
		monitor.beginTask(getName(), this.objects.size() == 1 ? IProgressMonitor.UNKNOWN
				: this.objects.size());
		for (SynchronizableObject synchronizableObject : filteredList) {

			monitor.setTaskName(NLS.bind("Try to commit \"{0}\" to remote-repository",
					synchronizableObject.getSynchronizationMetaData().getUrl()));
			String repositoryId = synchronizableObject.getSynchronizationMetaData()
					.getRepositoryId();
			RemoteRepository localRepository = InfomngmntEditPlugin.getPlugin().getService(
					IRepositoryService.class).getRepositoryById(repositoryId);
			RemoteObject remoteObject = null;
			try {
				remoteObject = localRepository.getRepositoryImplementation()
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
				ChangeSetManager changeSetManager = new ChangeSetManager();
				try {
					changeSet = changeSetManager.createChangeSet(targetCategory, monitor);
					EList<ChangeSetItem> changeSetItems = changeSet.getChangeSetItems();
					for (ChangeSetItem changeSetItem2 : changeSetItems) {
						DiffModel createDiffModel = changeSetManager.createDiffModel(
								changeSetItem2, targetCategory, true);
						changeSetManager.prepareSyncActions(createDiffModel.getOwnedElements(),
								changeSetItem2, targetCategory);
						changeSetManager.committAllLocal(changeSetItem2);
						new ChangeSetExecutor().synchronize(createDiffModel.getOwnedElements(),
								changeSetItem2, monitor, targetCategory);

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
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().flush();
		monitor.done();
		return Status.OK_STATUS;
	}
}
