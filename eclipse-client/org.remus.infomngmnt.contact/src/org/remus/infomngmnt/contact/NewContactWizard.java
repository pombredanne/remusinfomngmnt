package org.remus.infomngmnt.contact;

import org.eclipse.ui.INewWizard;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

public class NewContactWizard extends NewInfoObjectWizard implements INewWizard {

	public NewContactWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getInfoTypeId() {
		return ContactActivator.PASSWORD_INFO_ID;
	}

}
