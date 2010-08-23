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

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.program.Program;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenInExternalApplication extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		List<InformationUnit> informationUnitsFromExecutionEvent = InformationHandlerUtil
				.getInformationUnitsFromExecutionEvent(event);
		for (InformationUnit informationUnit : informationUnitsFromExecutionEvent) {

			if (informationUnit != null) {
				IFile binaryReferenceFile = InformationUtil.getBinaryReferenceFile(informationUnit);
				if (binaryReferenceFile != null) {
					Program.launch(binaryReferenceFile.getLocation().toOSString());
				}
			}
		}

		return null;
	}

}
