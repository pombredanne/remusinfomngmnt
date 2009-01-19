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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.core.sync.ChangeSetExecutor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizationWizard extends Wizard {

	private ChangeSetWizardPage page1;
	private final ChangeSetExecutor changeSetExecutor;

	public static final int CHECKOUTMODE = 1; 
	public static final int SHAREMODE = 2; 
	public static final int SYNCMODE = 3; 
	private final int mode;

	public SynchronizationWizard(final int mode) {
		this.mode = mode;
		this.changeSetExecutor = new ChangeSetExecutor();
		setNeedsProgressMonitor(true);
		
	}

	@Override
	public void addPages() {
		addPage(this.page1 = new ChangeSetWizardPage(this.changeSetExecutor.getChangeSet()));

	}

	public void init(final ChangeSet changeSet) {
		this.changeSetExecutor.setChangeSet(changeSet);
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
		DiffModel diffModel = this.page1.getDiffModel();
		EList<DiffElement> ownedElements = diffModel.getOwnedElements();
		switch (this.mode) {
		case CHECKOUTMODE:
			this.changeSetExecutor.performCheckout(ownedElements);
			break;
		default:
			break;
		}
		return true;
	}



}
