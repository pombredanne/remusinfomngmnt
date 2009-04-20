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

package org.remus.infomngmnt.video.internal;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.video.VideoActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class VideoCreationFactory extends AbstractCreationFactory {

	/**
	 * 
	 */
	public VideoCreationFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public InformationUnit createNewObject() {
		InformationUnit newObject = super.createNewObject();
		newObject.setType(VideoActivator.TYPE_ID);
		InformationUnit width = InfomngmntFactory.eINSTANCE.createInformationUnit();
		width.setType(VideoActivator.NODE_NAME_WIDTH);
		InformationUnit height = InfomngmntFactory.eINSTANCE.createInformationUnit();
		height.setType(VideoActivator.NODE_NAME_HEIGHT);
		InformationUnit mediaType = InfomngmntFactory.eINSTANCE.createInformationUnit();
		mediaType.setType(VideoActivator.NODE_NAME_MEDIATYPE);
		InformationUnit cuePoints = InfomngmntFactory.eINSTANCE.createInformationUnit();
		cuePoints.setType(VideoActivator.NODE_NAME_CUEPOINTS);
		InformationUnit rawData = InfomngmntFactory.eINSTANCE.createInformationUnit();
		rawData.setType(VideoActivator.NODE_NAME_RAWDATA);

		newObject.getChildValues().add(width);
		newObject.getChildValues().add(height);
		newObject.getChildValues().add(mediaType);
		newObject.getChildValues().add(cuePoints);
		newObject.getChildValues().add(rawData);

		return newObject;
	}

}
