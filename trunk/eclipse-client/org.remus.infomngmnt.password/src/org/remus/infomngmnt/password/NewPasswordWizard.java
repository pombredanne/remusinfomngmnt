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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.password.messages.Messages;


/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
public class NewPasswordWizard extends NewInfoObjectWizard implements INewWizard {

	public NewPasswordWizard() {
		setWindowTitle(Messages.NewPasswordWizard_Title);
	}

	@Override
	protected String getInfoTypeId() {
		return PasswordPlugin.PASSWORD_INFO_ID;
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		super.init(workbench, selection);
		this.page1.setTitle(Messages.NewPasswordWizard_Title);
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(PasswordPlugin
				.getDefault(), "icons/iconexperience/wizards/create_password_wizard.png")); //$NON-NLS-1$
		this.page1.setMessage(Messages.NewPasswordWizard_Subtitle);
	}

}
