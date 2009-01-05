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
package org.remus.infomngmnt.core.extension;

import java.util.Date;

import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <toms@tomosch.de>
 *
 */
public abstract class AbstractCreationFactory {


	protected EditingDomain editingDomain;


	public InformationUnit createNewObject() {
		InformationUnit newInfoObject = InfomngmntFactory.eINSTANCE.createInformationUnit();
		newInfoObject.setCreationDate(new Date());
		return newInfoObject;
	}



}
