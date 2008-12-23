/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.remus.infomngmnt.ui.remote.NewRepositoryWizard;
import org.remus.infomngmnt.ui.remote.RepositoryDialog;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewRepositoryHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShellChecked(event);
		RepositoryDialog dialog = new RepositoryDialog(shell);
		if (dialog.open() == IDialogConstants.OK_ID) {
			NewRepositoryWizard wizardClass = dialog.getSelectedObject().getWizardClass();
			WizardDialog wizDialog = new WizardDialog(shell,wizardClass);
			wizDialog.create();
			wizDialog.open();
		}
		return null;
	}

}
