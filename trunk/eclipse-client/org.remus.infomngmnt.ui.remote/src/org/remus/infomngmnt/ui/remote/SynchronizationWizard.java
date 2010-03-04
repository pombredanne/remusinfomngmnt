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

import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.ChangeSetItem;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizationWizard extends Wizard {

	private SynchronizeChangeSetWizardPage page1;

	private DiffModel diffModel;

	private ChangeSetItem item;

	public SynchronizationWizard() {
		setWindowTitle("Synchronizing with Remote Repository");
	}

	@Override
	public void addPages() {
		addPage(this.page1 = new SynchronizeChangeSetWizardPage(this.diffModel, this.item));

	}

	public void init(final DiffModel diffModel, final ChangeSetItem item) {
		this.diffModel = diffModel;
		this.item = item;
	}

	@Override
	public void createPageControls(final Composite pageContainer) {
		// TODO Auto-generated method stub
		// super.createPageControls(pageContainer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return true;
	}

}
