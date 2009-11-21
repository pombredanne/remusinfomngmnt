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

package org.remus.infomngmnt.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.ui.perspective.Perspective;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SwitchToInfoPerspective extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final IPerspectiveDescriptor perspectiveDescriptor = PlatformUI.getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(Perspective.PERSPECTIVE_ID);
		Display.getDefault().asyncExec(new Runnable() {

			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
						.setPerspective(perspectiveDescriptor);

			}
		});
		return null;
	}

}
