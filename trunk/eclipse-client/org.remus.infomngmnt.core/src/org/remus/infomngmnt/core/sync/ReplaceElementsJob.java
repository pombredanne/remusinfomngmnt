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
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ReplaceElementsJob extends Job {

	private final List<SynchronizableObject> objects;

	public ReplaceElementsJob(final List<SynchronizableObject> objects) {
		super(NLS.bind("Replacing {0} element(s)", objects.size()));
		this.objects = objects;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		List<SynchronizableObject> filteredList = CollectionUtils.filter(this.objects, new CollectionFilter<SynchronizableObject>() {
			public boolean select(final SynchronizableObject item) {
				return !ModelUtil.containsParent(ReplaceElementsJob.this.objects, item);
			}
		});
		monitor.beginTask(getName(), this.objects.size() == 1 ? IProgressMonitor.UNKNOWN : this.objects.size());
		for (SynchronizableObject synchronizableObject : filteredList) {

			monitor.setTaskName(NLS.bind("Try to download \'{0}\' from remote-repository", synchronizableObject.getSynchronizationMetaData().getHash()));
			String repositoryId = synchronizableObject.getSynchronizationMetaData().getRepositoryId();
			RemoteRepository localRepository = InfomngmntEditPlugin.getPlugin().getService(IRepositoryService.class).getRepositoryById(repositoryId);
			RemoteObject remoteObject = localRepository.getRepositoryImplementation()
			.getRemoteObjectBySynchronizableObject(synchronizableObject);
			if (remoteObject == null) {
				
				/*
				 *  something went wrong. Show a notification here.
				 *  It's the best to do a synchronization at this
				 *  time.
				 */

			}
			else if (remoteObject instanceof RemoteContainer && synchronizableObject instanceof Category) {
				monitor.setTaskName(NLS.bind("Found object in repository. Preparing replace for \'{0}\'", remoteObject.getName()));
				ChangeSet changeSet = new ChangeSetManager().createCheckOutChangeSet(Collections.<RemoteContainer> singletonList((RemoteContainer)remoteObject), localRepository);
				Category targetCategory = ((Category) synchronizableObject.eContainer());
				EditingDomain navigationEditingDomain = EditingUtil.getInstance().getNavigationEditingDomain();
				Command deleteCategory = CommandFactory.DELETE_CATEGORY((Category) synchronizableObject, navigationEditingDomain);
				System.out.println(deleteCategory.canExecute());
				navigationEditingDomain.getCommandStack().execute(deleteCategory);
				ChangeSetExecutor changeSetExecutor = new ChangeSetExecutor();
				monitor.setTaskName("Calculating differences...");
				changeSetExecutor.setChangeSet(changeSet);
				changeSetExecutor.prepareDiff(targetCategory);
				DiffModel makeDiff = changeSetExecutor.makeDiff();
				monitor.setTaskName("Getting data from repository...");
				changeSetExecutor.performCheckout(makeDiff.getOwnedElements());
				
			} else if (remoteObject instanceof RemoteObject && synchronizableObject instanceof InformationUnitListItem) {
				// TODO implement replacement of single info units.
			}
			monitor.worked(1);
		}
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().flush();
		monitor.done();
		return Status.OK_STATUS;
	}

}
