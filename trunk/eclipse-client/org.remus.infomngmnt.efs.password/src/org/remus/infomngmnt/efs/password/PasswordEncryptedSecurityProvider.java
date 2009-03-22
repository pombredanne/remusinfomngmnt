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

package org.remus.infomngmnt.efs.password;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;

import org.remus.infomngmnt.efs.extension.AbstractSecurityProvider;
import org.remus.infomngmnt.efs.password.ui.PasswordPrompt;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PasswordEncryptedSecurityProvider extends AbstractSecurityProvider {

	private static AESCryptoManager cryptoManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.efs.extension.AbstractSecurityProvider#initialize
	 * (org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public boolean initialize(final Shell parentShell) {
		PasswordPrompt prompt = new PasswordPrompt(parentShell);
		if (prompt.open() == IDialogConstants.OK_ID) {
			cryptoManager = new AESCryptoManager(prompt.getPwd().getBytes());
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.efs.extension.AbstractSecurityProvider#
	 * initializeForProject(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public boolean initializeForProject(final Shell shell) {
		// TODO Auto-generated method stub
		return false;
	}

	static AESCryptoManager getCryptoManager() {
		return cryptoManager;
	}

}
