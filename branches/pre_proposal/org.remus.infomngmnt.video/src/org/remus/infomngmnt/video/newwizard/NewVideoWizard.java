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
package org.remus.infomngmnt.video.newwizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.operation.LoadFileToTmpFromPathRunnable;
import org.remus.infomngmnt.core.transfertypes.FileTransferWrapper;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.video.VideoActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewVideoWizard extends NewInfoObjectWizard {

	/**
	 * 
	 */
	public NewVideoWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("New video");

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
			this.page1 = new GeneralVideoPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralVideoPage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralVideoPage((Category) null);
		}

	}

	@Override
	protected Command getAdditionalCommands() {
		IFile tmpFile = ((GeneralVideoPage) this.page1).getTmpFile();
		if (tmpFile != null) {
			return CommandFactory.addFileToInfoUnit(tmpFile, this.newElement, EditingUtil
					.getInstance().getNavigationEditingDomain());
		}
		return super.getAdditionalCommands();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		return VideoActivator.TYPE_ID;
	}

	@Override
	protected void setDefaults(final Object value, final RuleValue ruleValue,
			final TransferWrapper transferType) throws CoreException {
		if (transferType instanceof FileTransferWrapper) {
			String[] paths = (String[]) value;
			/*
			 * We're just taking the first element. For dropping multiple
			 * elements into the application we have to provide an import
			 * wizard.
			 */
			String string = paths[0];
			LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
			runnable.setFilePath(string);
			try {
				new ProgressMonitorDialog(getShell()).run(true, false, runnable);
				((GeneralVideoPage) this.page1).setTmpFile(runnable.getTmpFile());
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		super.setDefaults(value, ruleValue, transferType);
	}

}
