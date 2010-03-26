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

package org.remus.infomngmnt.video.newwizard;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;
import org.remus.infomngmnt.ui.operation.LoadFileToTmpFromPathRunnable;
import org.remus.infomngmnt.ui.rules.internal.transfer.FileTransferWrapper;
import org.remus.infomngmnt.ui.rules.service.ICreationTrigger;
import org.remus.infomngmnt.ui.rules.transfer.TransferWrapper;
import org.remus.infomngmnt.ui.rules.wizard.NewObjectWizardDelegate;
import org.remus.rules.RuleValue;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class VideoCreationWizard extends NewObjectWizardDelegate implements ICreationTrigger {

	/**
	 * @param wrappingWizard
	 */
	public VideoCreationWizard(final NewInfoObjectWizard wrappingWizard) {
		super(new NewVideoWizard());

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
				new ProgressMonitorDialog(UIUtil.getDisplay().getActiveShell()).run(true, false,
						runnable);
				this.wrappingWizard.setFiles(new IFile[] { runnable.getTmpFile() });
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