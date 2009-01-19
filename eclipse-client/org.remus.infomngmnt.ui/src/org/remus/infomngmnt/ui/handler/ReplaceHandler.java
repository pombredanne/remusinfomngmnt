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

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
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
import org.remus.infomngmnt.core.sync.ChangeSetExecutor;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ReplaceHandler extends AbstractRemoteHandler {

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.handler.AbstractRemoteHandler#doExecute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	protected Object doExecute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			// we assume only Synchronizable Objects
			final List<SynchronizableObject> list = ((IStructuredSelection) currentSelection).toList();
			List<SynchronizableObject> filteredList = CollectionUtils.filter(list, new CollectionFilter<SynchronizableObject>() {
				public boolean select(final SynchronizableObject item) {
					return !ModelUtil.containsParent(list, item);
				}
			});
			for (SynchronizableObject synchronizableObject : filteredList) {
				String repositoryId = synchronizableObject.getSynchronizationMetaData().getRepositoryId();
				RemoteRepository localRepository = UIPlugin.getDefault().getService(IRepositoryService.class).getRepositoryById(repositoryId);
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
					ChangeSet changeSet = new ChangeSetManager().createCheckOutChangeSet(Collections.<RemoteContainer> singletonList((RemoteContainer)remoteObject));
					changeSet.setTargetCategory((Category) synchronizableObject.eContainer());
					EditingDomain navigationEditingDomain = EditingUtil.getInstance().getNavigationEditingDomain();
					Command deleteCategory = CommandFactory.DELETE_CATEGORY((Category) synchronizableObject, navigationEditingDomain);
					navigationEditingDomain.getCommandStack().execute(deleteCategory);
					ChangeSetExecutor changeSetExecutor = new ChangeSetExecutor();
					changeSetExecutor.setChangeSet(changeSet);
					//changeSetExecutor.performCheckout(ownedElements)
					
			}
		}
		}
		return null;
	}

}
