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

package org.remus.infomngmnt.ui.remote.wizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.ChangeSet;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckOutMultipeInfoUnitsWizard extends Wizard implements INewWizard {

	private ChangeSet changeSet;
	private GeneralCheckoutPage page1;

	@Override
	public void addPages() {
		addPage(this.page1);
	}

	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		ProgressMonitorDialog pmd = new ProgressMonitorDialog(workbench.getActiveWorkbenchWindow().getShell());
		
	}
	
	protected void setChangeSet(final ChangeSet synchronize) {
		this.changeSet = synchronize;
	}

	@Override
	public boolean performFinish() {
		Job job = new Job("Creating new items") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return true;
	}
	
	@Override
	public void createPageControls(final Composite pageContainer) {
		// do nothing
	}

}
