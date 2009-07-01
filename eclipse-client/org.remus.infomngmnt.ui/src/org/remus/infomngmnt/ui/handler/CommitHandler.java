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

package org.remus.infomngmnt.ui.handler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.remus.infomngmnt.Adapter;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.remote.RemoteUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommitHandler extends AbstractRemoteHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object doExecute(final ExecutionEvent event) throws ExecutionException {
		final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		Job job = new Job("Comitting") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				if (currentSelection instanceof IStructuredSelection) {
					List list = ((IStructuredSelection) currentSelection).toList();
					Map<RemoteRepository, List<Adapter>> groupedRemoteRepository = RemoteUtil
							.groupByRemoteRepsoitory(list);
					Set<RemoteRepository> keySet = groupedRemoteRepository.keySet();
					for (RemoteRepository remoteRepository : keySet) {
						List<Adapter> items2commit = groupedRemoteRepository.get(remoteRepository);
						for (Adapter item2commit : items2commit) {
							try {
								recursivelyCommit(item2commit, monitor, remoteRepository
										.getRepositoryImplementation());
							} catch (RemoteException e) {
								return e.getStatus();
							}
						}
					}
				}
				return Status.OK_STATUS;
			}

			private void recursivelyCommit(final Adapter item2commit,
					final IProgressMonitor monitor, final IRepository iRepository)
					throws RemoteException {
				RemoteObject newHash = null;
				if (item2commit instanceof Category) {
					newHash = iRepository.commit((Category) item2commit, monitor);
					List<InformationUnitListItem> allChildren = ModelUtil.getAllChildren(
							item2commit, InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT);
					for (InformationUnitListItem informationUnitListItem : allChildren) {
						recursivelyCommit(informationUnitListItem, monitor, iRepository);

					}
					List<Category> catChildren = ModelUtil.getAllChildren(item2commit,
							InfomngmntPackage.Literals.CATEGORY);
					for (Category category : catChildren) {
						recursivelyCommit(category, monitor, iRepository);
					}
					newHash = iRepository.commit((Category) item2commit, monitor);
				} else if (item2commit instanceof InformationUnitListItem) {
					newHash = iRepository.commit((InformationUnitListItem) item2commit, monitor);
				}
				SynchronizationMetadata adapter = (SynchronizationMetadata) item2commit
						.getAdapter(SynchronizationMetadata.class);
				adapter.setHash(newHash.getHash());
				adapter.setUrl(newHash.getUrl());
				adapter.setLastSynchronisation(new Date());
				adapter.setSyncState(SynchronizationState.IN_SYNC);
			}
		};
		job.setUser(true);
		job.schedule();
		return null;
	}

}
