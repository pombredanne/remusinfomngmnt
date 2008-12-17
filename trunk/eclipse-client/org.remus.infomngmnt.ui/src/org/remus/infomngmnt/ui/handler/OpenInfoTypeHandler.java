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
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.dialogs.InfoUnitSelectionDialog;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.provider.NavigationCellLabelProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenInfoTypeHandler extends AbstractHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		InfoUnitSelectionDialog diag = new InfoUnitSelectionDialog(HandlerUtil.getActiveShell(event),false);
		diag.setListLabelProvider(new NavigationCellLabelProvider());
		if (diag.open() == IDialogConstants.OK_ID) {
			Object[] result = diag.getResult();
			for (Object object : result) {
				if (object instanceof InformationUnitListItem) {
					try {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
								new InformationEditorInput((InformationUnitListItem) object), InformationEditor.ID);
					} catch (PartInitException e) {
						ErrorDialog.openError(HandlerUtil.getActiveShell(event), "Error opening element", "Error opening element", e.getStatus());
					}
				}
			}

		}
		return null;
	}

}
