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
package org.remus.infomngmnt.password;

import org.eclipse.ui.INewWizard;

import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
public class NewPasswordWizard extends NewInfoObjectWizard implements INewWizard {

	public NewPasswordWizard() {
		// TODO Autso-generated constructor stub
	}

	@Override
	protected String getInfoTypeId() {
		return PasswordPlugin.PASSWORD_INFO_ID;
	}

}
