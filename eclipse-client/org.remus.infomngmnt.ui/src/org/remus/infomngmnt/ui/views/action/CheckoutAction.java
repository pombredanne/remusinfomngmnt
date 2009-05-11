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

package org.remus.infomngmnt.ui.views.action;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.progress.CancelableRunnable;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
import org.remus.infomngmnt.ui.remote.SynchronizationWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckoutAction extends BaseSelectionListenerAction {

	public CheckoutAction() {
		super("Checkout");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
				ISharedImages.IMG_OBJ_FOLDER));
	}

	@Override
	public void run() {
		ProgressMonitorDialog pmd = new ProgressMonitorDialog(UIUtil.getDisplay().getActiveShell());
		final ChangeSet[] changeSet = new ChangeSet[1];
		try {
			pmd.run(true, true, new CancelableRunnable() {

				@Override
				protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
					monitor.beginTask("Prepare checkout", IProgressMonitor.UNKNOWN);
					ChangeSetManager manager = new ChangeSetManager();
					changeSet[0] = manager.createCheckOutChangeSet(null, getStructuredSelection()
							.toList(), null, ChangeSetManager.MODE_CHECKOUT_REPLACE, monitor);
					return Status.OK_STATUS;
				}

			});
			if (changeSet[0] != null) {
				SynchronizationWizard synchronizationWizard = new SynchronizationWizard(
						SynchronizationWizard.CHECKOUTMODE);
				synchronizationWizard.init(changeSet[0]);
				WizardDialog wz = new WizardDialog(UIUtil.getPrimaryWindow().getShell(),
						synchronizationWizard);
				wz.open();
			}
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected boolean updateSelection(final IStructuredSelection selection) {
		if (!selection.isEmpty()
				&& ModelUtil.hasEqualAttribute(selection.toList(),
						InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_ID)) {
			return true;
			// String repositoryId = (String) ((EObject)
			// selection.toList().get(0))
			// .eGet(InfomngmntPackage.Literals.REMOTE_OBJECT__REPOSITORY_TYPE_ID);
			// IRepositoryActionContributor actionContributor =
			// UIPlugin.getDefault().getService(
			// IRepositoryExtensionService.class).getItemByRepositoryId(repositoryId)
			// .getActionContributor();

		}
		return false;
	}

}
