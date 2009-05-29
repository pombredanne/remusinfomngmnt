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

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ShareItemHandler extends AbstractRemoteHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object doExecute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			List list = ((IStructuredSelection) currentSelection).toList();
			for (Object object : list) {
				if (object instanceof InformationUnitListItem) {
					SynchronizationMetadata adapter = (SynchronizationMetadata) ((InformationUnitListItem) object).getAdapter(SynchronizationMetadata.class);
					RemoteRepository repositoryById = UIPlugin.getDefault().getService(IRepositoryService.class).getRepositoryById(adapter.getRepositoryId());
					try {
						RemoteObject addToRepository = repositoryById.getRepositoryImplementation().addToRepository((InformationUnitListItem) object, null);
						adapter.setHash(addToRepository.getHash());
						adapter.setReadonly(/* TODO implement */ false);
						adapter.setSyncState(SynchronizationState.IN_SYNC);
						adapter.setUrl(addToRepository.getUrl());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

}
