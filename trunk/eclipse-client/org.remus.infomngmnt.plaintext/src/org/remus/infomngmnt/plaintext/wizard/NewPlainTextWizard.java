/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.plaintext.wizard;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewPlainTextWizard extends NewInfoObjectWizard {

	/**
	 * <p>
	 * Nothing special. We're just reusing the functioality
	 * from the {@link NewInfoObjectWizard} and setting the
	 * specific information type.
	 * </p>
	 * @return the new info object
	 */
	@Override
	protected InformationUnit createNewInformationUnit() {
		InformationUnit newInfoObject = super.createNewInformationUnit();
		newInfoObject.setType("PLAINTEXT");
		return newInfoObject;
	}

}
