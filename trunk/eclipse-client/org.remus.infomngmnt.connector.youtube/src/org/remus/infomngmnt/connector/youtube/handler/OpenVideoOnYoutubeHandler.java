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

package org.remus.infomngmnt.connector.youtube.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.RemoteObject;
import org.eclipse.remus.core.remote.RemoteException;
import org.eclipse.remus.core.remote.sync.SyncUtil;
import org.eclipse.remus.model.remote.IRepository;
import org.eclipse.remus.ui.handlerutil.CopyToClipboardHandler;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.remus.ui.util.CancelableRunnable;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.handlers.HandlerUtil;


import com.google.gdata.data.youtube.VideoEntry;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenVideoOnYoutubeHandler extends CopyToClipboardHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		InformationUnit infoUnit = InformationHandlerUtil
				.getInformationUnitFromExecutionEvent(event);
		if (infoUnit != null) {
			final InformationUnitListItem adapter = (InformationUnitListItem) infoUnit
					.getAdapter(InformationUnitListItem.class);
			final IRepository remoteRepository = SyncUtil
					.getRepositoryImplemenationByRepositoryId(adapter.getSynchronizationMetaData()
							.getRepositoryId());
			if (remoteRepository != null) {
				CancelableRunnable runnable = new CancelableRunnable() {

					@Override
					protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
						monitor
								.beginTask("Contacting Youtube Repository",
										IProgressMonitor.UNKNOWN);
						RemoteObject remoteObject;
						try {
							remoteObject = remoteRepository.getRemoteObjectBySynchronizableObject(
									adapter, monitor);
						} catch (RemoteException e) {
							return StatusCreator
									.newStatus("Error contacting Youtube Repository", e);
						}
						if (remoteObject != null
								&& remoteObject.getWrappedObject() instanceof VideoEntry) {
							VideoEntry wrappedObject = (VideoEntry) remoteObject.getWrappedObject();
							Program.launch(wrappedObject.getHtmlLink().getHref());
						}
						return Status.OK_STATUS;
					}
				};
				ProgressMonitorDialog pmd = new ProgressMonitorDialog(HandlerUtil
						.getActiveShell(event));
				try {
					pmd.run(true, true, runnable);
				} catch (Exception e) {
					ErrorDialog.openError(HandlerUtil.getActiveShell(event), "Error",
							"Error contacting Flickr",
							e.getCause() instanceof CoreException ? ((CoreException) e.getCause())
									.getStatus() : null);
				}

			}
		}

		return null;
	}

}
