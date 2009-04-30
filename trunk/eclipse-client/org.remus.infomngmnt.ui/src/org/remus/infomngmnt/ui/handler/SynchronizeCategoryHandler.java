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

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.remote.SynchronizationWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizeCategoryHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection
				&& ((IStructuredSelection) currentSelection).getFirstElement() instanceof Category
				&& ((Category) ((IStructuredSelection) currentSelection).getFirstElement())
						.getSynchronizationMetaData() != null) {
			ChangeSetManager manager = new ChangeSetManager();
			Category cat = (Category) ((IStructuredSelection) currentSelection).getFirstElement();
			SynchronizationMetadata metaData = cat.getSynchronizationMetaData();
			RemoteRepository remoteRepository = UIPlugin.getDefault().getService(
					IRepositoryService.class).getRepositoryById(metaData.getRepositoryId());
			RemoteObject remoteObject = remoteRepository.getRepositoryImplementation()
					.getRemoteObjectBySynchronizableObject(cat, new NullProgressMonitor());
			if (remoteObject != null && remoteObject instanceof RemoteContainer) {
				ChangeSet changeSet = manager.createCheckOutChangeSet(cat, Collections
						.<RemoteContainer> singletonList((RemoteContainer) remoteObject),
						remoteRepository, ChangeSetManager.MODE_CHECKOUT_REPLACE);
				SynchronizationWizard synchronizationWizard = new SynchronizationWizard(
						SynchronizationWizard.SYNCMODE);
				synchronizationWizard.init(changeSet);
				WizardDialog wz = new WizardDialog(UIUtil.getPrimaryWindow().getShell(),
						synchronizationWizard);
				wz.open();
			}

		}
		return null;
	}

}
