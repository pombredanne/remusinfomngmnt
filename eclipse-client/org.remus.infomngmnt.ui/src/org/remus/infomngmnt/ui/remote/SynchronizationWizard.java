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

package org.remus.infomngmnt.ui.remote;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.ChangeSet;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizationWizard extends Wizard {
	
	private ChangeSetWizardPage page1;
	private ChangeSet changeSet;

	public SynchronizationWizard() {
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		addPage(this.page1 = new ChangeSetWizardPage(this.changeSet));
		
	}
	
	public void init(final ChangeSet changeSet) {
		this.changeSet = changeSet;
	}
	
	@Override
	public void createPageControls(final Composite pageContainer) {
		// TODO Auto-generated method stub
		//super.createPageControls(pageContainer);
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
