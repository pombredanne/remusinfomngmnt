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

package org.remus.infomngmnt.efs.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.efs.EFSActivator;
import org.remus.infomngmnt.efs.internal.model.SecurityWrapper;
import org.remus.infomngmnt.efs.ui.InitializeSecurityProviderDialog;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenSecurityProviderDialog extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		List<SecurityWrapper> securityProvideProjects = EFSActivator.getDefault()
				.getSecurityProvideProjects();
		if (securityProvideProjects.size() > 0) {
			InitializeSecurityProviderDialog diag = new InitializeSecurityProviderDialog(
					HandlerUtil.getActiveShell(event), securityProvideProjects);
			diag.open();
		} else {
			MessageDialog.openInformation(HandlerUtil.getActiveShell(event),
					"Nothing to configure", "You have not set up any security-enabled projects.");
		}
		return null;
	}

}
