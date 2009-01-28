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
		InformationUnit width = InfomngmntFactory.eINSTANCE.createInformationUnit();
		width.setType(ImagePlugin.NODE_NAME_WIDTH);
		InformationUnit height = InfomngmntFactory.eINSTANCE.createInformationUnit();
		height.setType(ImagePlugin.NODE_NAME_HEIGHT);
		InformationUnit links = InfomngmntFactory.eINSTANCE.createInformationUnit();
		links.setType(ImagePlugin.NODE_NAME_LINKS);
		returnValue.getChildValues().add(rawData);
		returnValue.getChildValues().add(origFilePath);
		returnValue.getChildValues().add(width);
		returnValue.getChildValues().add(height);
		returnValue.getChildValues().add(links);
		return returnValue;
	}

}
