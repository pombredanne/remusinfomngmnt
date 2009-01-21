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

package org.remus.infomngmnt.plaintext.extension;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;

/**
 * The PLAINTEXT information type has a really simple structure.
 * There are no special sub nodes of information, so we have nothing
 * to do in this creation factory. The super-type just creates a new
 * InformationUnit Object and the client sets the type.
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UnformattedTextCreationFactory extends AbstractCreationFactory {

	@Override
	public InformationUnit createNewObject() {
		InformationUnit returnValue = super.createNewObject();
		returnValue.setType("PLAINTEXT");
		return returnValue;
	}
	

}
