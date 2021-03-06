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

package org.remus.infomngmnt.pdf.handler;

import java.lang.reflect.InvocationTargetException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.ui.handlers.HandlerUtil;
import org.remus.infomngmnt.pdf.messages.Messages;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SplitPdfHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final InformationUnit unit = InformationHandlerUtil
				.getInformationUnitFromExecutionEvent(event);
		ProgressMonitorDialog pmd = new ProgressMonitorDialog(HandlerUtil.getActiveShell(event));
		final int[] pagecount = new int[1];
		try {
			pmd.run(true, true, new CancelableRunnable() {

				@Override
				protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
					monitor.beginTask(Messages.SplitPdfHandler_ScanningPDF, IProgressMonitor.UNKNOWN);
					IFile binaryReferenceFile = InformationUtil.getBinaryReferenceFile(unit);
					try {
						PDDocument load = PDDocument.load(binaryReferenceFile.getLocationURI()
								.toURL());
						pagecount[0] = load.getNumberOfPages();
						load.close();
						return Status.OK_STATUS;
					} catch (Exception e) {
						return StatusCreator.newStatus(Messages.SplitPdfHandler_ErrorInspectingPDF);
					}
				}
			});
			new SplitDialog(HandlerUtil.getActiveShell(event), unit, pagecount[0]).open();
		} catch (InvocationTargetException e) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event), Messages.SplitPdfHandler_Error,
					Messages.SplitPdfHandler_ErrorReadingPDF);
		} catch (InterruptedException e) {
			// do nothing
		}
		return null;
	}

}
