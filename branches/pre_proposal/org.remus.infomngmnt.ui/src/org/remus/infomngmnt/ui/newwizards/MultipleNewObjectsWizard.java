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

package org.remus.infomngmnt.ui.newwizards;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.wizard.IWizardPage;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MultipleNewObjectsWizard extends NewInfoObjectWizard {

	protected EList<InformationUnit> newObjects;

	public MultipleNewObjectsWizard() {
		setNeedsProgressMonitor(true);
	}

	@Override
	public IWizardPage getStartingPage() {
		IWizardPage startingPage = super.getStartingPage();
		if (startingPage instanceof IMultipleInfoObjectSetter) {
			((IMultipleInfoObjectSetter) startingPage).setInformationUnits(this.newObjects);
		}
		return startingPage;
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage instanceof IMultipleInfoObjectSetter) {
			((IMultipleInfoObjectSetter) nextPage).setInformationUnits(this.newObjects);
		}
		return nextPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		Job job = new Job("Creating new items") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				monitor.beginTask("Creating new items", MultipleNewObjectsWizard.this.newObjects
						.size());
				for (InformationUnit newElement : MultipleNewObjectsWizard.this.newObjects) {
					EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack()
							.execute(
									CommandFactory.CREATE_INFOTYPE(newElement, findCategory(),
											monitor));
					// we also reveal the created list-item, that can be found
					// in the navigation
					// UIUtil.selectAndReveal(newElement.getAdapter(InformationUnitListItem.class),
					// PlatformUI.getWorkbench().getActiveWorkbenchWindow());
					// UIUtil.selectAndReveal(newElement,
					// PlatformUI.getWorkbench().getActiveWorkbenchWindow());
					monitor.worked(1);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setNewObjects(final EList<InformationUnit> newObjects) {
		this.newObjects = newObjects;
	}

}
