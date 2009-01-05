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

package org.remus.infomngmnt.link;

import org.eclipse.jface.preference.IPreferenceStore;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.link.preferences.LinkPreferenceInitializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkCreationFactory extends AbstractCreationFactory {

	/**
	 * 
	 */
	public LinkCreationFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public InformationUnit createNewObject() {
		IPreferenceStore store = LinkActivator.getDefault().getPreferenceStore();
		InformationUnit returnValue = super.createNewObject();
		returnValue.setType(LinkActivator.LINK_INFO_ID);
		InformationUnit screenShot = InfomngmntFactory.eINSTANCE.createInformationUnit();
		screenShot.setType(LinkRepresentation.SCREENSHOT_TYPE);
		screenShot.setBoolValue(store.getBoolean(LinkPreferenceInitializer.MAKE_SCREENSHOT));
		InformationUnit indexTarget = InfomngmntFactory.eINSTANCE.createInformationUnit();
		indexTarget.setType(LinkRepresentation.INDEXWEBPAGE_TYPE);
		indexTarget.setBoolValue(store.getBoolean(LinkPreferenceInitializer.INDEX_DOCUMENT));
		InformationUnit linkContent = InfomngmntFactory.eINSTANCE.createInformationUnit();
		linkContent.setType(LinkRepresentation.INDEXWEBPAGECONTENT_TYPE);
		returnValue.getChildValues().add(screenShot);
		returnValue.getChildValues().add(indexTarget);
		returnValue.getChildValues().add(linkContent);
		return returnValue;
	}

}
