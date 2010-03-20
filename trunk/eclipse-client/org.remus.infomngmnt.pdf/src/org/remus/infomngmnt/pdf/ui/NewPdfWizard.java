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

package org.remus.infomngmnt.pdf.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.commands.CreateBinaryReferenceCommand;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.pdf.Activator;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;
import org.remus.infomngmnt.ui.operation.LoadFileToTmpFromPathRunnable;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewPdfWizard extends NewInfoObjectWizard {

	/**
	 * 
	 */
	public NewPdfWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("New photo/graphic");

	}

	@Override
	protected Command getAdditionalCommands() {
		IFile tmpFile = ((GeneralPdfPage) this.page1).getTmpFile();

		if (tmpFile != null) {
			LoadFileToTmpFromPathRunnable loadImageRunnable = new LoadFileToTmpFromPathRunnable();
			loadImageRunnable.setFilePath(tmpFile.getLocation().toOSString());
			try {
				getContainer().run(true, false, loadImageRunnable);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			IEditingHandler service = Activator.getDefault().getServiceTracker().getService(
					IEditingHandler.class);
			CreateBinaryReferenceCommand addFileToInfoUnit = CommandFactory.addFileToInfoUnit(
					tmpFile, this.newElement, service.getNavigationEditingDomain());
			Activator.getDefault().getServiceTracker().ungetService(service);
			return addFileToInfoUnit;
		}
		return super.getAdditionalCommands();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {

		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralPdfPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralPdfPage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralPdfPage((Category) null);
		}
		if (this.files != null) {
			this.page1.setFiles(this.files);
		}
		setCategoryToPage();

	}

	@Override
	protected void performActionAfterCreation() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new InformationEditorInput((IFile) this.newElement.getAdapter(IFile.class)),
					InformationEditor.ID);
		} catch (Exception e) {
			// will come soon.
		}
		// we also reveal the created list-item, that can be found in the
		// navigation
		UIUtil.selectAndReveal(this.newElement.getAdapter(InformationUnitListItem.class),
				PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		UIUtil.selectAndReveal(this.newElement, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		return Activator.TYPE_ID;
	}

}
