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

package org.eclipse.remus.application.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.ui.editors.InformationEditor;
import org.eclipse.remus.ui.editors.InformationEditorInput;
import org.eclipse.remus.ui.viewer.dialogs.InfoUnitSelectionDialog;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenInfoTypeHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		InfoUnitSelectionDialog diag = InfoUnitSelectionDialog.create(HandlerUtil
				.getActiveShell(event), null);
		if (diag.open() == IDialogConstants.OK_ID) {
			Object[] result = diag.getResult();
			for (Object object : result) {
				if (object instanceof InformationUnitListItem) {
					try {
						PlatformUI
								.getWorkbench()
								.getActiveWorkbenchWindow()
								.getActivePage()
								.openEditor(
										new InformationEditorInput((InformationUnitListItem) object),
										InformationEditor.ID);
					} catch (PartInitException e) {
						ErrorDialog.openError(HandlerUtil.getActiveShell(event),
								"Error opening element", "Error opening element", e.getStatus());
					}
				}
			}

		}
		return null;
	}
}
