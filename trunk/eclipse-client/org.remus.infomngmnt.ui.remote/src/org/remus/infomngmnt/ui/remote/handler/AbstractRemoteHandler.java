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

package org.remus.infomngmnt.ui.remote.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractRemoteHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public final Object execute(final ExecutionEvent event) throws ExecutionException {
		Object doExecute = doExecute(event);
		UIPlugin.getDefault().getEditService().getNavigationEditingDomain().getCommandStack()
				.flush();
		return doExecute;
	}

	protected abstract Object doExecute(ExecutionEvent event) throws ExecutionException;

}
