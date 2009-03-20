package org.remus.infomngmnt.password.core;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.password.PasswordPlugin;

public class PlainTextCreationFactory extends AbstractCreationFactory {
	
	public PlainTextCreationFactory() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public InformationUnit createNewObject() {
		InformationUnit returnValue = super.createNewObject();
		returnValue.setType(PasswordPlugin.PASSWORD_INFO_ID);
		InformationUnit url = InfomngmntFactory.eINSTANCE.createInformationUnit();
		url.setType(PasswordPlugin.NODE_URL);
		InformationUnit userName = InfomngmntFactory.eINSTANCE.createInformationUnit();
		userName.setType(PasswordPlugin.NODE_USERNAME);
		returnValue.getChildValues().add(url);
		returnValue.getChildValues().add(userName);
		return returnValue;
	}

}
