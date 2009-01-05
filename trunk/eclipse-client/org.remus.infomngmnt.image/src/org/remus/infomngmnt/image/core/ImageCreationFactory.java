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

package org.remus.infomngmnt.image.core;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.image.ImagePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImageCreationFactory extends AbstractCreationFactory {

	@Override
	public InformationUnit createNewObject() {
		
		InformationUnit returnValue = super.createNewObject();
		returnValue.setType(ImagePlugin.TYPE_ID);
		InformationUnit rawData = InfomngmntFactory.eINSTANCE.createInformationUnit();
		rawData.setType(ImagePlugin.NODE_NAME_RAWDATA);
		InformationUnit origFilePath = InfomngmntFactory.eINSTANCE.createInformationUnit();
		origFilePath.setType(ImagePlugin.ORIGINAL_FILEPATH);
		returnValue.getChildValues().add(rawData);
		returnValue.getChildValues().add(origFilePath);
		return returnValue;
	}

}
