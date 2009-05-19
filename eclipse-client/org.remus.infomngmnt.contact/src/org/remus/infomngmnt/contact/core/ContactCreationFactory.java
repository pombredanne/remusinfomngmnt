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

		// general
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
		InformationUnit persRole = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persRole.setType(ContactActivator.NODE_NAME_PERS_ROLE);
		InformationUnit persOrganisation = InfomngmntFactory.eINSTANCE.createInformationUnit();
		persOrganisation.setType(ContactActivator.NODE_NAME_PERS_ORGANISATION);

		// group phone number
		pnHome.setType(ContactActivator.NODE_NAME_PN_HOME);
		InformationUnit pnWork = InfomngmntFactory.eINSTANCE.createInformationUnit();
		pnWork.setType(ContactActivator.NODE_NAME_PN_WORK);
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

		// group email and instant messaging
		InformationUnit mailDef = InfomngmntFactory.eINSTANCE.createInformationUnit();
		mailDef.setType(ContactActivator.NODE_MAIL_DEF);
		InformationUnit mailCollection = InfomngmntFactory.eINSTANCE.createInformationUnit();
		mailCollection.setType(ContactActivator.NODE_MAILS);

		InformationUnit imAim = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imAim.setType(ContactActivator.NODE_INSTMESS_AIM);
		InformationUnit imGadu = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imGadu.setType(ContactActivator.NODE_INSTMESS_GADU);
		InformationUnit imGw = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imGw.setType(ContactActivator.NODE_INSTMESS_GROUPWISE);
		InformationUnit imIcq = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imIcq.setType(ContactActivator.NODE_INSTMESS_ICQ);
		InformationUnit imIrc = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imIrc.setType(ContactActivator.NODE_INSTMESS_IRC);
		InformationUnit imJabber = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imJabber.setType(ContactActivator.NODE_INSTMESS_JABBER);
		InformationUnit imMsn = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imMsn.setType(ContactActivator.NODE_INSTMESS_MSN);
		InformationUnit imMw = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imMw.setType(ContactActivator.NODE_INSTMESS_MEANWHILE);
		InformationUnit imSkype = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imSkype.setType(ContactActivator.NODE_INSTMESS_SKYPE);
		InformationUnit imYahoo = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imYahoo.setType(ContactActivator.NODE_INSTMESS_YAHOO);
		InformationUnit imDefault = InfomngmntFactory.eINSTANCE.createInformationUnit();
		imDefault.setType(ContactActivator.NODE_INSTMESS_DEFAULT);

		InformationUnit blogFeed = InfomngmntFactory.eINSTANCE.createInformationUnit();
		blogFeed.setType(ContactActivator.NODE_BLOG_FEED);
		InformationUnit frontpage = InfomngmntFactory.eINSTANCE.createInformationUnit();
		frontpage.setType(ContactActivator.NODE_FRONTPAGE);

		// section details
		InformationUnit detDep = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detDep.setType(ContactActivator.NODE_DETAILS_DEPARTMENT);
		InformationUnit detBur = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detBur.setType(ContactActivator.NODE_DETAILS_BUREAU);
		InformationUnit detNaMan = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detNaMan.setType(ContactActivator.NODE_DETAILS_NAME_MANAGER);
		InformationUnit detNaAss = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detNaAss.setType(ContactActivator.NODE_DETAILS_NAME_ASSISTANT);
		InformationUnit detJob = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detJob.setType(ContactActivator.NODE_DETAILS_JOB);
		InformationUnit detTitle = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detTitle.setType(ContactActivator.NODE_DETAILS_TITLE);
		InformationUnit detNaNick = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detNaNick.setType(ContactActivator.NODE_DETAILS_NAME_NICK);
		InformationUnit detBirth = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detBirth.setType(ContactActivator.NODE_DETAILS_BIRTHDAY);
		InformationUnit detJubi = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detJubi.setType(ContactActivator.NODE_DETAILS_JUBILEE);
		InformationUnit detNaPart = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detNaPart.setType(ContactActivator.NODE_DETAILS_NAME_PARTNER);
		InformationUnit detNotes = InfomngmntFactory.eINSTANCE.createInformationUnit();
		detNotes.setType(ContactActivator.NODE_DETAILS_NOTES);

		// section misc
		InformationUnit miscPubKey = InfomngmntFactory.eINSTANCE.createInformationUnit();
		miscPubKey.setType(ContactActivator.NODE_MISC_PUBKEY);
		InformationUnit miscMime = InfomngmntFactory.eINSTANCE.createInformationUnit();
		miscMime.setType(ContactActivator.NODE_MISC_MIME);

		// general
		returnValue.setType(ContactActivator.TYPE_ID);
		returnValue.getChildValues().add(rawDataImage);
		returnValue.getChildValues().add(rawDataLogo);
		returnValue.getChildValues().add(origFilePath);

		// group person
		returnValue.getChildValues().add(persAdditional);
		returnValue.getChildValues().add(persFirst);
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
		returnValue.getChildValues().add(pnMobile);
		returnValue.getChildValues().add(pnModem);
		returnValue.getChildValues().add(pnOthers);
		returnValue.getChildValues().add(pnPager);
		returnValue.getChildValues().add(pnPcs);
		returnValue.getChildValues().add(pnVideo);
		returnValue.getChildValues().add(pnVoice);
		returnValue.getChildValues().add(pnWork);

		// group address
		returnValue.getChildValues().add(createAdress(ContactActivator.NODE_NAME_WORK_ADRESS));
		returnValue.getChildValues().add(createAdress(ContactActivator.NODE_NAME_HOME_ADRESS));
		returnValue.getChildValues().add(createAdress(ContactActivator.NODE_NAME_INTERNAT_ADRESS));
		returnValue.getChildValues().add(createAdress(ContactActivator.NODE_NAME_POSTAL_ADRESS));
		returnValue.getChildValues().add(createAdress(ContactActivator.NODE_NAME_PARCEL_ADRESS));
		returnValue.getChildValues().add(createAdress(ContactActivator.NODE_NAME_DOMESTIC_ADRESS));
		returnValue.getChildValues().add(createAdress(ContactActivator.NODE_NAME_OTHER_ADRESS));

		// group email and instant messaging
		returnValue.getChildValues().add(mailDef);
		returnValue.getChildValues().add(mailCollection);

		returnValue.getChildValues().add(imAim);
		returnValue.getChildValues().add(imDefault);
		returnValue.getChildValues().add(imGadu);
		returnValue.getChildValues().add(imGw);
		returnValue.getChildValues().add(imIcq);
		returnValue.getChildValues().add(imIrc);
		returnValue.getChildValues().add(imJabber);
		returnValue.getChildValues().add(imMsn);
		returnValue.getChildValues().add(imMw);
		returnValue.getChildValues().add(imSkype);
		returnValue.getChildValues().add(imYahoo);

		returnValue.getChildValues().add(blogFeed);
		returnValue.getChildValues().add(frontpage);

		// section details
		returnValue.getChildValues().add(detBirth);
		returnValue.getChildValues().add(detBur);
		returnValue.getChildValues().add(detDep);
		returnValue.getChildValues().add(detJob);
		returnValue.getChildValues().add(detJubi);
		returnValue.getChildValues().add(detNaAss);
		returnValue.getChildValues().add(detNaMan);
		returnValue.getChildValues().add(detNaNick);
		returnValue.getChildValues().add(detNaPart);
		returnValue.getChildValues().add(detNotes);
		returnValue.getChildValues().add(detTitle);

		// section misc
		returnValue.getChildValues().add(miscMime);
		returnValue.getChildValues().add(miscPubKey);

		return returnValue;
	}

	private InformationUnit createAdress(final String type) {
		InformationUnit adress = InfomngmntFactory.eINSTANCE.createInformationUnit();
		adress.setType(type);
		InformationUnit street = InfomngmntFactory.eINSTANCE.createInformationUnit();
		street.setType(ContactActivator.NODE_NAME_ADRESS_STREET);
		InformationUnit postOfficeBox = InfomngmntFactory.eINSTANCE.createInformationUnit();
		postOfficeBox.setType(ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX);
		InformationUnit workLocality = InfomngmntFactory.eINSTANCE.createInformationUnit();
		workLocality.setType(ContactActivator.NODE_NAME_ADRESS_LOCALITY);
		InformationUnit region = InfomngmntFactory.eINSTANCE.createInformationUnit();
		region.setType(ContactActivator.NODE_NAME_ADRESS_REGION);
		InformationUnit postal = InfomngmntFactory.eINSTANCE.createInformationUnit();
		postal.setType(ContactActivator.NODE_NAME_ADRESS_POSTAL);
		InformationUnit country = InfomngmntFactory.eINSTANCE.createInformationUnit();
		country.setType(ContactActivator.NODE_NAME_ADRESS_COUNTRY);
		InformationUnit longitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		longitude.setType(ContactActivator.NODE_NAME_ADRESS_LONGITUDE);
		InformationUnit latitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		latitude.setType(ContactActivator.NODE_NAME_ADRESS_LATITUDE);
		adress.getChildValues().add(street);
		adress.getChildValues().add(postOfficeBox);
		adress.getChildValues().add(workLocality);
		adress.getChildValues().add(region);
		adress.getChildValues().add(postal);
		adress.getChildValues().add(country);
		adress.getChildValues().add(longitude);
		adress.getChildValues().add(latitude);

		return adress;
	}
}
