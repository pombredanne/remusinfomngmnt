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

package org.remus.infomngmnt.ui.handler;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.core.sync.ReplaceElementsJob;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ReplaceHandler extends AbstractRemoteHandler {

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.handler.AbstractRemoteHandler#doExecute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	protected Object doExecute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			// we assume only Synchronizable Objects
			final List<SynchronizableObject> list = ((IStructuredSelection) currentSelection).toList();
			boolean openConfirm = MessageDialog.openConfirm(
					HandlerUtil.getActiveShell(event),
					"Confirm",
					"You're about to overwrite your local data. Do you want to continue?");
			if (openConfirm) {
				ReplaceElementsJob job = new ReplaceElementsJob(list);
				job.setUser(true);
				job.schedule();
			}
		}
		return null;
	}

}
