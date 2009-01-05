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

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.ui.newwizards.CheckOutWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckoutLinkAction extends BaseSelectionListenerAction {
	public CheckoutLinkAction() {
		super("Link(s)");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
	}
	
	
	@Override
	public void run() {
		CheckOutWizard wizard = new CheckOutWizard() {
			@Override
			protected String getInfoTypeId() {
				return LinkActivator.LINK_INFO_ID;
			}
		};
		wizard.init(UIUtil.getPrimaryWindow().getWorkbench(), getStructuredSelection());
		WizardDialog wizardDialog = new WizardDialog(
				UIUtil.getPrimaryWindow().getShell(),wizard);
		wizardDialog.open();
		super.run();
	}
}
