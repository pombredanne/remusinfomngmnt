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

package org.remus.infomngmnt.pdf.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.remus.rules.RuleValue;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.remus.ui.rules.script.DefaultScriptConstants;
import org.eclipse.remus.ui.rules.service.ICreationTrigger;
import org.eclipse.remus.ui.rules.transfer.TransferWrapper;
import org.eclipse.remus.ui.rules.wizard.NewObjectWizardDelegate;
import org.eclipse.swt.widgets.Display;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PdfCreationTrigger extends NewObjectWizardDelegate implements
		ICreationTrigger {

	/**
	 * @param wrappingWizard
	 */
	public PdfCreationTrigger() {
		super(new NewPdfWizard());

	}

	@Override
	protected void setDefaults(final Object value, final RuleValue ruleValue,
			final TransferWrapper transferType) throws CoreException {
		Object droppedFiles = options.get(DefaultScriptConstants.KEY_FILES);
		if (droppedFiles != null && droppedFiles instanceof String[]) {
			String[] paths = (String[]) droppedFiles;
			/*
			 * We're just taking the first element. For dropping multiple
			 * elements into the application we have to provide an import
			 * wizard.
			 */
			String string = paths[0];
			LoadFileToTmpFromPathRunnable runnable = new LoadFileToTmpFromPathRunnable();
			runnable.setFilePath(string);
			try {
				new ProgressMonitorDialog(Display.getDefault().getActiveShell())
						.run(true, false, runnable);
				wrappingWizard.setFiles(new IFile[] { runnable.getTmpFile() });
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
