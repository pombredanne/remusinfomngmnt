/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.internal.file.commands;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.remus.infomngmnt.file.ui.OpenFileDialog;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenFileHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final List<InformationUnit> informationUnitsFromExecutionEvent = InformationHandlerUtil
				.getInformationUnitsFromExecutionEvent(event);
		final Shell activeShell = HandlerUtil.getActiveShell(event);
		for (final InformationUnit informationUnit : informationUnitsFromExecutionEvent) {
			IFile binaryReferenceFile = InformationUtil
					.getBinaryReferenceFile(informationUnit);
			if (binaryReferenceFile != null) {
				activeShell.getDisplay().asyncExec(new Runnable() {

					public void run() {
						new OpenFileDialog(activeShell, informationUnit).open();
					}
				});
				Program.launch(binaryReferenceFile.getLocation().toOSString());
			}
		}
		return null;
	}

}
