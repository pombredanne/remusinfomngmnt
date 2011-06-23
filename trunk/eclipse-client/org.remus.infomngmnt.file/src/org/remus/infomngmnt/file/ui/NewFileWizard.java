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

package org.remus.infomngmnt.file.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.eclipse.ui.IWorkbench;
import org.remus.infomngmnt.file.Activator;
import org.remus.infomngmnt.file.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewFileWizard extends NewInfoObjectWizard {

	private final IEditingHandler editingHandler;

	/**
	 * 
	 */
	public NewFileWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages.NewFileWizard_NewFile);
		editingHandler = Activator.getDefault().getServiceTracker()
				.getService(IEditingHandler.class);

	}

	@Override
	protected Command getAdditionalCommands() {
		IFile tmpFile = ((GeneralFilePage) page1).getTmpFile();

		if (tmpFile != null) {
			return CommandFactory.addFileToInfoUnit(tmpFile, newElement,
					editingHandler.getNavigationEditingDomain());
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
	public void init(final IWorkbench workbench,
			final IStructuredSelection selection) {

		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			page1 = new GeneralFilePage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			page1 = new GeneralFilePage((InformationUnitListItem) firstElement);
		} else {
			page1 = new GeneralFilePage((Category) null);
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
