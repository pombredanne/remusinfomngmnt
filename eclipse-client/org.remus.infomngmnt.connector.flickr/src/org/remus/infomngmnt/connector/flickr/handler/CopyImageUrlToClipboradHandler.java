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

package org.remus.infomngmnt.connector.flickr.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.core.remote.RemoteException;
import org.remus.infomngmnt.core.remote.sync.SyncUtil;
import org.remus.infomngmnt.model.remote.IRepository;
import org.remus.infomngmnt.ui.handlerutil.CopyToClipboardHandler;
import org.remus.infomngmnt.ui.handlerutil.InformationHandlerUtil;
import org.remus.infomngmnt.ui.util.CancelableRunnable;
import org.remus.infomngmnt.util.StatusCreator;

import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.photos.Photo;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CopyImageUrlToClipboradHandler extends CopyToClipboardHandler {

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
			final String[] originalUrl = new String[1];
			if (remoteRepository != null) {
				CancelableRunnable runnable = new CancelableRunnable() {

					@Override
					protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
						monitor.beginTask("Contacting Flickr Repository", IProgressMonitor.UNKNOWN);
						RemoteObject remoteObject;
						try {
							remoteObject = remoteRepository.getRemoteObjectBySynchronizableObject(
									adapter, monitor);
						} catch (RemoteException e) {
							return StatusCreator.newStatus("Error contacting Flickr Repository", e);
						}
						if (remoteObject != null
								&& remoteObject.getWrappedObject() instanceof Photo) {
							Photo wrappedObject = (Photo) remoteObject.getWrappedObject();

							try {
								originalUrl[0] = wrappedObject.getOriginalUrl();
							} catch (FlickrException e) {
								originalUrl[0] = wrappedObject.getLargeUrl();
							}

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
			if (originalUrl[0] != null) {
				copyToCliboard(event, originalUrl[0], TextTransfer.getInstance());
			} else {
				copyToCliboard(event, "Error copying url", TextTransfer.getInstance());
			}
		}

		return null;
	}

}
