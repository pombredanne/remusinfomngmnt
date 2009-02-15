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

package org.remus.infomngmnt.link.delicious.actions;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
import org.remus.infomngmnt.ui.remote.SynchronizationWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckoutTagAction extends BaseSelectionListenerAction {

	public CheckoutTagAction() {
		super("Categories");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
	}



	@Override
	public void run() {
		ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createCheckOutChangeSet(null, getStructuredSelection().toList(), null);
		if (changeSet != null) {
			SynchronizationWizard synchronizationWizard = new SynchronizationWizard(SynchronizationWizard.CHECKOUTMODE);
			synchronizationWizard.init(changeSet);
			WizardDialog wz = new WizardDialog(UIUtil.getPrimaryWindow().getShell(), synchronizationWizard);
			wz.open();
		}
		super.run();
	}






}
