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
package org.remus.infomngmnt.contact.core;
/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;

public class ContactCreationFactory extends AbstractCreationFactory {

	public ContactCreationFactory() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public InformationUnit createNewObject() {
		InformationUnit returnValue = super.createNewObject();
				
		returnValue.setType(ContactActivator.TYPE_ID);
		InformationUnit rawDataImage = InfomngmntFactory.eINSTANCE.createInformationUnit();
		rawDataImage.setType(ContactActivator.NODE_NAME_RAWDATA_IMAGE);
		InformationUnit rawDataLogo = InfomngmntFactory.eINSTANCE.createInformationUnit();
		rawDataLogo.setType(ContactActivator.NODE_NAME_RAWDATA_LOGO);
		InformationUnit origFilePath = InfomngmntFactory.eINSTANCE.createInformationUnit();
		origFilePath.setType(ContactActivator.ORIGINAL_FILEPATH);		
		InformationUnit pnHome = InfomngmntFactory.eINSTANCE.createInformationUnit();
		// group person
		InformationUnit persTitle = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persTitle.setType(ContactActivator.NODE_NAME_PERS_NAME_TITLE);
		InformationUnit persFirst = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persFirst.setType(ContactActivator.NODE_NAME_PERS_NAME_FIRST);
		InformationUnit persAdditional = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persAdditional.setType(ContactActivator.NODE_NAME_PERS_NAME_ADDITIONAL);
		InformationUnit persLast = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persLast.setType(ContactActivator.NODE_NAME_PERS_NAME_LAST);
		InformationUnit persTitleAfter = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persTitleAfter.setType(ContactActivator.NODE_NAME_PERS_NAME_TITLE_AFTER);
		InformationUnit persFormatted = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persFormatted.setType(ContactActivator.NODE_NAME_PERS_NAME_FORMATTED);
		InformationUnit persRole = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persRole.setType(ContactActivator.NODE_NAME_PERS_ROLE);
		InformationUnit persOrganisation = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persOrganisation.setType(ContactActivator.NODE_NAME_PERS_ORGANISATION);
		InformationUnit persComplete = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persComplete.setType(ContactActivator.NODE_NAME_PERS_NAME_COMPLETE);
		// group phone number
		pnHome.setType(ContactActivator.NODE_NAME_PN_HOME);
		InformationUnit pnWork = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnWork.setType(ContactActivator.NODE_NAME_PN_WORK);
		InformationUnit pnMessanger = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnMessanger.setType(ContactActivator.NODE_NAME_PN_MESSANGER);
		InformationUnit pnVoice = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnVoice.setType(ContactActivator.NODE_NAME_PN_VOICE);
		InformationUnit pnFax = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnFax.setType(ContactActivator.NODE_NAME_PN_FAX);
		InformationUnit pnMobile = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnMobile.setType(ContactActivator.NODE_NAME_PN_MOBILE);
		InformationUnit pnMailbox = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnMailbox.setType(ContactActivator.NODE_NAME_PN_MAILBOX);
		InformationUnit pnModem = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnModem.setType(ContactActivator.NODE_NAME_PN_MODEM);
		InformationUnit pnCar = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnCar.setType(ContactActivator.NODE_NAME_PN_CAR);
		InformationUnit pnIsdn = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnIsdn.setType(ContactActivator.NODE_NAME_PN_ISDN);
		InformationUnit pnVideo = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnVideo.setType(ContactActivator.NODE_NAME_PN_VIDEO);
		InformationUnit pnPcs = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnPcs.setType(ContactActivator.NODE_NAME_PN_PCS);
		InformationUnit pnPager = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnPager.setType(ContactActivator.NODE_NAME_PN_PAGER);
		InformationUnit pnOthers = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnOthers.setType(ContactActivator.NODE_NAME_PN_OTHERS);
		
		returnValue.setType(ContactActivator.PASSWORD_INFO_ID);
		returnValue.getChildValues().add(rawDataImage);
		returnValue.getChildValues().add(rawDataLogo);
		returnValue.getChildValues().add(origFilePath);
		// group person
		returnValue.getChildValues().add(persAdditional);
		returnValue.getChildValues().add(persComplete);
		returnValue.getChildValues().add(persFirst);
		returnValue.getChildValues().add(persFormatted);
		returnValue.getChildValues().add(persLast);
		returnValue.getChildValues().add(persOrganisation);
		returnValue.getChildValues().add(persRole);
		returnValue.getChildValues().add(persTitle);
		returnValue.getChildValues().add(persTitleAfter);
		// group phone number
		returnValue.getChildValues().add(pnCar);
		returnValue.getChildValues().add(pnFax);
		returnValue.getChildValues().add(pnHome);
		returnValue.getChildValues().add(pnIsdn);
		returnValue.getChildValues().add(pnMailbox);
		returnValue.getChildValues().add(pnMessanger);
		returnValue.getChildValues().add(pnMobile);
		returnValue.getChildValues().add(pnModem);
		returnValue.getChildValues().add(pnOthers);
		returnValue.getChildValues().add(pnPager);
		returnValue.getChildValues().add(pnPcs);
		returnValue.getChildValues().add(pnVideo);
		returnValue.getChildValues().add(pnVoice);
		returnValue.getChildValues().add(pnWork);
		
		return returnValue;
	}
}
