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

package org.remus.infomngmnt.sourcecode.core;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SourceCodeCreationFactory extends AbstractCreationFactory {

	@Override
	public InformationUnit createNewObject() {
		InformationUnit informationUnit = super.createNewObject();
		informationUnit.setType(SourceCodePlugin.TYPE_ID);

		InformationUnit sourceCodeType = InfomngmntFactory.eINSTANCE.createInformationUnit();
		sourceCodeType.setType(SourceCodePlugin.SRCTYPE_NAME);
		informationUnit.getChildValues().add(sourceCodeType);
		return informationUnit;
	}
}
