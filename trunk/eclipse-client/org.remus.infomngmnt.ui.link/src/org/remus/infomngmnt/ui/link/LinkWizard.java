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

package org.remus.infomngmnt.ui.link;

import org.eclipse.jface.wizard.Wizard;
import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkWizard extends Wizard {

	private NewLinkWizardPage page1;
	private final InformationUnit info;

	public LinkWizard(InformationUnit info) {
		this.info = info;
	}

	@Override
	public void addPages() {
		//addPage(this.page1 = new NewLinkWizardPage(this.info));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return false;
	}

}
