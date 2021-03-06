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

package org.remus.infomngmnt.birtreport.handler;

import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.swt.program.Program;
import org.remus.infomngmnt.birtreport.extension.BirtReportRepresentation;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenInBrowserHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		InformationUnit unit = InformationHandlerUtil
				.getInformationUnitFromExecutionEvent(event);
		if (unit != null) {
			Program.launch(BirtReportRepresentation.createURL(
					"frameset", WebViewer.HTML, unit)); //$NON-NLS-1$
		}
		return null;
	}

}
