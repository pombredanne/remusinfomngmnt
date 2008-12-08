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

package org.remus.infomngmnt.link;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.link.ui.NewLinkWizard;
import org.remus.infomngmnt.ui.extension.AbstractCreationTrigger;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkCreationTrigger extends AbstractCreationTrigger {



	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.extension.AbstractCreationTrigger#handleCreationRequest()
	 */
	@Override
	public void handleCreationRequest() {
		NewLinkWizard newLinkWizard = new NewLinkWizard();
		newLinkWizard.init(UIUtil.getPrimaryWindow().getWorkbench(), new StructuredSelection(new Object[0]));
		WizardDialog wizard = new WizardDialog(UIUtil.getPrimaryWindow().getShell(),newLinkWizard);
		wizard.open();
	}

}
