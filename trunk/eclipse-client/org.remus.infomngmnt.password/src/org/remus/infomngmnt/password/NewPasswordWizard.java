package org.remus.infomngmnt.password;

import org.eclipse.ui.INewWizard;

import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

public class NewPasswordWizard extends NewInfoObjectWizard implements INewWizard {

	public NewPasswordWizard() {
		// TODO Autso-generated constructor stub
	}

	@Override
	protected String getInfoTypeId() {
		return PasswordPlugin.PASSWORD_INFO_ID;
	}

}
