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

package org.remus.infomngmnt.image.ui;

import java.lang.reflect.InvocationTargetException;

import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.operation.LoadImageRunnable;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.edit.DisposableEditingDomain;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.eclipse.ui.IWorkbench;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewImageWizard extends NewInfoObjectWizard {

	private final IEditingHandler editingHandler;

	/**
	 * 
	 */
	public NewImageWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("New photo/graphic");
		editingHandler = ImagePlugin.getDefault().getServiceTracker()
				.getService(IEditingHandler.class);

	}

	@Override
	protected Command getAdditionalCommands() {
		IFile tmpFile = ((GeneralImagePage) page1).getTmpFile();

		if (tmpFile != null) {
			DisposableEditingDomain editingDomain = editingHandler
					.createNewEditingDomain();
			final LoadImageRunnable loadImageRunnable = new LoadImageRunnable();
			loadImageRunnable.setImagePath(tmpFile.getLocation().toOSString());
			loadImageRunnable.setImageNode(newElement);
			loadImageRunnable.setDomain(editingDomain);
			editingDomain.getCommandStack().flush();
			editingDomain.dispose();
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
			page1 = new GeneralImagePage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			page1 = new GeneralImagePage((InformationUnitListItem) firstElement);
		} else {
			page1 = new GeneralImagePage((Category) null);
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
		return ImagePlugin.TYPE_ID;
	}

}
