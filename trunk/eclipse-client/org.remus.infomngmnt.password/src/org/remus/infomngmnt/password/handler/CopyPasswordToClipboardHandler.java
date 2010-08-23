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

package org.remus.infomngmnt.password.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.ui.handlerutil.CopyToClipboardHandler;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.swt.dnd.TextTransfer;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CopyPasswordToClipboardHandler extends CopyToClipboardHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		InformationUnit unit = InformationHandlerUtil.getInformationUnitFromExecutionEvent(event);
		if (unit != null) {
			copyToCliboard(event, unit.getStringValue(), TextTransfer.getInstance());
		}
		return null;
	}

}
