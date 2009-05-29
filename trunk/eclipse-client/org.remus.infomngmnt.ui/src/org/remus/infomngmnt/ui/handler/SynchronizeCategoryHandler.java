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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.core.sync.ChangeSetExecutor;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
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
			final ChangeSetManager manager = new ChangeSetManager();
			final Category cat = (Category) ((IStructuredSelection) currentSelection)
					.getFirstElement();
			final ChangeSetItem[] changeSetItem = new ChangeSetItem[1];
			final DiffModel[] diffModel = new DiffModel[1];
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(HandlerUtil
					.getActiveShell(event));
			try {
				dialog.run(true, true, new CancelableRunnable() {
					@Override
					protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
						try {
							ChangeSet changeSet = manager.createChangeSet(cat, monitor);
							if (changeSet != null && changeSet.getChangeSetItems().size() > 0) {
								changeSetItem[0] = changeSet.getChangeSetItems().get(0);
								diffModel[0] = manager.createDiffModel(changeSetItem[0], cat);
								manager.prepareSyncActions(diffModel[0].getOwnedElements(),
										changeSetItem[0], cat);
							}
						} catch (CoreException e) {
							return e.getStatus();
						}
						return Status.OK_STATUS;
					}

				});
			} catch (InvocationTargetException e1) {
				ErrorDialog.openError(HandlerUtil.getActiveShell(event),
						"Error contacting repository",
						"An error occured while connecting to the repository", StatusCreator
								.newStatus("Error synchronizing", e1.getCause()));
			} catch (InterruptedException e1) {
				return null;
			}
			if (changeSetItem[0] != null) {
				SynchronizationWizard synchronizationWizard = new SynchronizationWizard();
				synchronizationWizard.init(diffModel[0], changeSetItem[0]);
				if (changeSetItem[0].getSyncCategoryActionMap().size() == 0
						&& changeSetItem[0].getSyncObjectActionMap().size() == 0) {
					MessageDialog.openInformation(HandlerUtil.getActiveShell(event), "No changes",
							"No changes found");
				} else {
					WizardDialog wz = new WizardDialog(UIUtil.getPrimaryWindow().getShell(),
							synchronizationWizard);
					if (wz.open() == IDialogConstants.OK_ID) {
						Job job = new Job("Synchronizing objects") {

							@Override
							protected IStatus run(final IProgressMonitor monitor) {
								ChangeSetExecutor executor = new ChangeSetExecutor();
								try {
									executor.synchronize(diffModel[0].getOwnedElements(),
											changeSetItem[0], monitor, cat);
								} catch (CoreException e) {
									return e.getStatus();
								}
								return Status.OK_STATUS;
							}
						};
						job.setUser(true);
						job.schedule();
					}
				}

			}
		}
		return null;
	}
}
