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

package org.remus.infomngmnt.connector.slideshare.ui;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.SynchronizableObject;
import org.eclipse.remus.SynchronizationMetadata;
import org.eclipse.ui.handlers.HandlerUtil;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UploadSlidesHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection
				&& ((IStructuredSelection) currentSelection).getFirstElement() instanceof SynchronizableObject) {
			SynchronizationMetadata synchronizationMetaData = ((SynchronizableObject) ((IStructuredSelection) currentSelection)
					.getFirstElement()).getSynchronizationMetaData();
			if (synchronizationMetaData != null) {
				final String repositoryId = synchronizationMetaData.getRepositoryId();
				final UploadSlideDialog diag = new UploadSlideDialog(HandlerUtil
						.getActiveShell(event), repositoryId);
				diag.open();
			}
		}
		return null;
	}
}
