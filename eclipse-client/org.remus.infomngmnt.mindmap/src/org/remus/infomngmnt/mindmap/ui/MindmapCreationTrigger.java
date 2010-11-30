/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
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

import org.xmind.ui.mindmap.MindMapUI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.rules.RuleValue;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.remus.ui.rules.internal.transfer.FileTransferWrapper;
import org.eclipse.remus.ui.rules.service.ICreationTrigger;
import org.eclipse.remus.ui.rules.transfer.TransferWrapper;
import org.eclipse.remus.ui.rules.wizard.NewObjectWizardDelegate;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MindmapCreationTrigger extends NewObjectWizardDelegate implements
		ICreationTrigger {

	/**
	 * @param wrappingWizard
	 */
	public MindmapCreationTrigger() {
		super(new NewMindmapWizard());

	}

	@Override
	protected void setDefaults(final Object value, final RuleValue ruleValue,
			final TransferWrapper transferType) throws CoreException {
		if (transferType instanceof FileTransferWrapper
				&& value instanceof String[]) {
			String[] filenames = (String[]) value;
			if (filenames.length > 0) {
				String fileName = filenames[0];
				if (MindMapUI.FILE_EXT_XMIND.substring(1).equals(
						new Path(fileName).getFileExtension())) {
					ProgressMonitorDialog pmd = new ProgressMonitorDialog(
							UIUtil.getDisplay().getActiveShell());
					LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
					runnable.setFilePath(fileName);
					try {
						pmd.run(true, false, runnable);
						wrappingWizard.setFiles(new IFile[] { runnable
								.getTmpFile() });
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
