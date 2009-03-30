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

package org.remus.infomngmnt.image.importt;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImportImageFromDiskWizard extends Wizard implements IImportWizard {

	private IWorkbench workbench;
	private ImportImagesFromDiskWizardPage page1;

	/**
	 * 
	 */
	public ImportImageFromDiskWizard() {
		setNeedsProgressMonitor(true);
	}

	@Override
	public void addPages() {
		addPage(this.page1 = new ImportImagesFromDiskWizardPage(new ImportImageObject()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true,
					new ImportImagesFromDiscRunnable(this.page1.getImportObject()));
			return true;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		this.workbench = workbench;

	}

}
