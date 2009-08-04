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

package org.remus.infomngmnt.mindmap.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.xmind.ui.internal.WorkbookFactory;
import org.xmind.ui.mindmap.MindMapUI;

import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.operation.LoadFileToTmpFromPathRunnable;
import org.remus.infomngmnt.core.transfertypes.FileTransferWrapper;
import org.remus.infomngmnt.mindmap.MindmapActivator;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewMindmapWizard extends NewInfoObjectWizard {

	private IFile tmpFile;

	public NewMindmapWizard() {
		super();
		setWindowTitle("New Mindmap");
	}

	@Override
	protected Command getAdditionalCommands() {
		if (this.tmpFile != null) {
			return CommandFactory.addFileToInfoUnit(this.tmpFile, this.newElement, EditingUtil
					.getInstance().getNavigationEditingDomain());
		} else {
			try {
				IFile emptyFile = ResourceUtil
						.createTempFile(MindMapUI.FILE_EXT_XMIND.substring(1));
				emptyFile.setContents(WorkbookFactory.createEmptyWorkbookStream(), true, false,
						new NullProgressMonitor());
				return CommandFactory.addFileToInfoUnit(emptyFile, this.newElement, EditingUtil
						.getInstance().getNavigationEditingDomain());
			} catch (CoreException e) {
				return null;
			}
		}
	}

	@Override
	protected String getInfoTypeId() {
		return MindmapActivator.INFO_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New Mindmap");
		this.page1.setMessage("This wizard enables you to create a new meeting protocol");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(MindmapActivator
				.getDefault(), "icons/iconexperience/new_wizard.png"));
	}

	@Override
	protected void setDefaults(final Object value, final RuleValue ruleValue,
			final TransferWrapper transferType) throws CoreException {
		if (transferType instanceof FileTransferWrapper && value instanceof String[]) {
			String[] filenames = (String[]) value;
			if (filenames.length > 0) {
				String fileName = filenames[0];
				if (MindMapUI.FILE_EXT_XMIND.substring(1).equals(
						new Path(fileName).getFileExtension())) {
					ProgressMonitorDialog pmd = new ProgressMonitorDialog(getShell());
					LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
					runnable.setFilePath(fileName);
					try {
						pmd.run(true, false, runnable);
						this.tmpFile = runnable.getTmpFile();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		super.setDefaults(value, ruleValue, transferType);
	}

}
