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

package org.remus.infomngmnt.image.ui;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.ui.extension.AbstractCreationTrigger;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageCreationTrigger extends AbstractCreationTrigger {

	/**
	 * 
	 */
	public ImageCreationTrigger() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.extension.AbstractCreationTrigger#handleCreationRequest()
	 */
	@Override
	public void handleCreationRequest() {
		NewImageWizard newImageWizard = new NewImageWizard();
		newImageWizard.init(UIUtil.getPrimaryWindow().getWorkbench(), new StructuredSelection(new Object[0]));
		newImageWizard.setDefaults(getValue(), getRuleValue());
		WizardDialog wizard = new WizardDialog(UIUtil.getPrimaryWindow().getShell(),newImageWizard);
		wizard.open();

	}

}
