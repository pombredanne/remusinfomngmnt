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

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.ui.swt.DisplayKeyPoster;
import org.eclipse.remus.common.ui.swt.PostEvent;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.ui.handlerutil.InformationHandlerUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.password.PasswordPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FillCredentialsHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		InformationUnit unit = InformationHandlerUtil.getInformationUnitFromExecutionEvent(event);
		Display display = HandlerUtil.getActiveShell(event).getDisplay();
		if (unit != null) {
			InformationStructureRead read = InformationStructureRead.newSession(unit);
			String username = (String) read.getValueByNodeId(PasswordPlugin.NODE_USERNAME);
			String password = unit.getStringValue();
			java.util.List<PostEvent> events = new ArrayList<PostEvent>();
			events.add(DisplayKeyPoster.ALT_TAB(display));
			events.add(DisplayKeyPoster.STRING(display, username));
			events.add(DisplayKeyPoster.TAB(display));
			events.add(DisplayKeyPoster.STRING(display, password));
			events.add(DisplayKeyPoster.ENTER(display));
			DisplayKeyPoster.postEvents(events);
		}
		return null;
	}

}
