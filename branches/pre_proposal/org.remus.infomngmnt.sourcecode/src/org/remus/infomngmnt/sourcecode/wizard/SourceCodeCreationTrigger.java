/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.sourcecode.wizard;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.ui.extension.AbstractCreationTrigger;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SourceCodeCreationTrigger extends AbstractCreationTrigger {

	/**
	 * 
	 */
	public SourceCodeCreationTrigger() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.ui.extension.AbstractCreationTrigger#
	 * handleCreationRequest()
	 */
	@Override
	public void handleCreationRequest() {
		NewSourceCodeWizard newLinkWizard = new NewSourceCodeWizard();
		newLinkWizard.init(UIUtil.getPrimaryWindow().getWorkbench(), new StructuredSelection(
				new Object[0]));
		newLinkWizard.setDefaults(getValue(), getRuleValue(), getTransferType());
		WizardDialog wizard = new WizardDialog(UIUtil.getPrimaryWindow().getShell(), newLinkWizard);
		wizard.open();

	}

}
