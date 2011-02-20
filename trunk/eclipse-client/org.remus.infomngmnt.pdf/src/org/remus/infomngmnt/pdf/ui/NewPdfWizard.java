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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.commands.CreateBinaryReferenceCommand;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.ui.IWorkbench;
import org.remus.infomngmnt.pdf.Activator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewPdfWizard extends NewInfoObjectWizard {

	/**
	 * 
	 */
	public NewPdfWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("New PDF Document");

	}

	@Override
	protected Command getAdditionalCommands() {

		IFile tmpFile = getTmpFile();

		if (tmpFile != null) {
			try {
				PDDocument pdfDocument = PDDocument.load(tmpFile
						.getLocationURI().toURL());
				PDDocumentInformation info = pdfDocument
						.getDocumentInformation();
				InformationStructureEdit edit = InformationStructureEdit
						.newSession(Activator.TYPE_ID);

				edit.setValue(newElement, Activator.AUTHOR, info.getAuthor());
				edit.setValue(newElement, Activator.TITLE, info.getTitle());
				edit.setValue(newElement, Activator.CREATOR, info.getCreator());
				edit.setValue(newElement, Activator.PRODUCER,
						info.getProducer());
				try {
					edit.setValue(newElement, Activator.CREATION_DATE, info
							.getCreationDate().getTime());
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (Throwable e1) {
				// We do nothing here.
			}

			final LoadFileToTmpFromPathRunnable loadImageRunnable = new LoadFileToTmpFromPathRunnable();
			loadImageRunnable.setFilePath(tmpFile.getLocation().toOSString());
			getShell().getDisplay().syncExec(new Runnable() {

				public void run() {
					try {
						new ProgressMonitorDialog(getShell()).run(true, false,
								loadImageRunnable);
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			});
			IEditingHandler service = Activator.getDefault()
					.getServiceTracker().getService(IEditingHandler.class);
			CreateBinaryReferenceCommand addFileToInfoUnit = CommandFactory
					.addFileToInfoUnit(tmpFile, newElement,
							service.getNavigationEditingDomain());
			Activator.getDefault().getServiceTracker().ungetService(service);
			return addFileToInfoUnit;
		}
		return super.getAdditionalCommands();
	}

	protected IFile getTmpFile() {
		return ((GeneralPdfPage) page1).getTmpFile();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench,
			final IStructuredSelection selection) {

		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			page1 = new GeneralPdfPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			page1 = new GeneralPdfPage((InformationUnitListItem) firstElement);
		} else {
			page1 = new GeneralPdfPage((Category) null);
		}
		if (files != null) {
			page1.setFiles(files);
		}
		setCategoryToPage();

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
