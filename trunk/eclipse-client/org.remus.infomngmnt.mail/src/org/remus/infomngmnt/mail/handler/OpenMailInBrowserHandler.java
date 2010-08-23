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

package org.remus.infomngmnt.mail.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.swt.program.Program;

import org.remus.infomngmnt.mail.MailActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenMailInBrowserHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		List<InformationUnit> unit = InformationHandlerUtil
				.getInformationUnitsFromExecutionEvent(event);
		for (InformationUnit informationUnit : unit) {
			if (informationUnit != null) {
				InformationStructureRead read = InformationStructureRead
						.newSession(informationUnit);
				String url = (String) read.getValueByNodeId(MailActivator.NODE_NAME_MORE_INFO);
				if (url != null) {
					Program.launch(url);
				}
			}
		}
		return null;
	}

}
