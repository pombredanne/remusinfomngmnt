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
package org.remus.infomngmnt.password.core;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.password.PasswordPlugin;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
public class PasswordCreationFactory extends AbstractCreationFactory {

	public PasswordCreationFactory() {
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
