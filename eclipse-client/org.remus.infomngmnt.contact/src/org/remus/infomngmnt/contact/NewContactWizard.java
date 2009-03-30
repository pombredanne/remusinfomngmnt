/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.contact;
/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
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
