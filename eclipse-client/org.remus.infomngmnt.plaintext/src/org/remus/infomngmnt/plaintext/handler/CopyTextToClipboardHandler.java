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

package org.remus.infomngmnt.plaintext.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CopyTextToClipboardHandler extends AbstractHandler {

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
			InformationStructureRead read = InformationStructureRead.newSession(unit);

			new Clipboard(UIUtil.getDisplay()).setContents(new Object[] { read
					.getValueByNodeId("contents") }, new Transfer[] { TextTransfer.getInstance() });
		}
		return null;
	}

}
