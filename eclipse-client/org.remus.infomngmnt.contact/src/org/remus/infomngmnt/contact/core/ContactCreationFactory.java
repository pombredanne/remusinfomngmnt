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

		// group address
		InformationUnit addWorkStreet = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addWorkStreet.setType(ContactActivator.NODE_ADDRESS_WORK_STREET);
		InformationUnit addWorkPob = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addWorkPob.setType(ContactActivator.NODE_ADDRESS_WORK_POST_OFFICE_BOX);
		InformationUnit addWorkLocality = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addWorkLocality.setType(ContactActivator.NODE_ADDRESS_WORK_LOCALITY);
		InformationUnit addWorkRegion = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addWorkRegion.setType(ContactActivator.NODE_ADDRESS_WORK_REGION);
		InformationUnit addWorkPostal = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addWorkPostal.setType(ContactActivator.NODE_ADDRESS_WORK_POSTAL);
		InformationUnit addWorkCountry = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addWorkCountry.setType(ContactActivator.NODE_ADDRESS_WORK_COUNTRY);
		InformationUnit addWorkLatitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addWorkLatitude.setType(ContactActivator.NODE_ADDRESS_WORK_LATITUDE);
		InformationUnit addWorkLongitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addWorkLongitude.setType(ContactActivator.NODE_ADDRESS_WORK_LONGITUDE);

		InformationUnit addHomeStreet = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addHomeStreet.setType(ContactActivator.NODE_ADDRESS_HOME_STREET);
		InformationUnit addHomePob = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addHomePob.setType(ContactActivator.NODE_ADDRESS_HOME_POST_OFFICE_BOX);
		InformationUnit addHomeLocality = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addHomeLocality.setType(ContactActivator.NODE_ADDRESS_HOME_LOCALITY);
		InformationUnit addHomeRegion = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addHomeRegion.setType(ContactActivator.NODE_ADDRESS_HOME_REGION);
		InformationUnit addHomePostal = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addHomePostal.setType(ContactActivator.NODE_ADDRESS_HOME_POSTAL);
		InformationUnit addHomeCountry = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addHomeCountry.setType(ContactActivator.NODE_ADDRESS_HOME_COUNTRY);
		InformationUnit addHomeLatitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addHomeLatitude.setType(ContactActivator.NODE_ADDRESS_HOME_LATITUDE);
		InformationUnit addHomeLongitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addHomeLongitude.setType(ContactActivator.NODE_ADDRESS_HOME_LONGITUDE);

		InformationUnit addParcelStreet = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addParcelStreet.setType(ContactActivator.NODE_ADDRESS_PARCEL_STREET);
		InformationUnit addParcelPob = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addParcelPob.setType(ContactActivator.NODE_ADDRESS_PARCEL_POST_OFFICE_BOX);
		InformationUnit addParcelLocality = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addParcelLocality.setType(ContactActivator.NODE_ADDRESS_PARCEL_LOCALITY);
		InformationUnit addParcelRegion = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addParcelRegion.setType(ContactActivator.NODE_ADDRESS_PARCEL_REGION);
		InformationUnit addParcelPostal = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addParcelPostal.setType(ContactActivator.NODE_ADDRESS_PARCEL_POSTAL);
		InformationUnit addParcelCountry = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addParcelCountry.setType(ContactActivator.NODE_ADDRESS_PARCEL_COUNTRY);
		InformationUnit addParcelLatitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addParcelLatitude.setType(ContactActivator.NODE_ADDRESS_PARCEL_LATITUDE);
		InformationUnit addParcelLongitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addParcelLongitude.setType(ContactActivator.NODE_ADDRESS_PARCEL_LONGITUDE);

		InformationUnit addPostalStreet = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addPostalStreet.setType(ContactActivator.NODE_ADDRESS_POSTAL_STREET);
		InformationUnit addPostalPob = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addPostalPob.setType(ContactActivator.NODE_ADDRESS_POSTAL_POST_OFFICE_BOX);
		InformationUnit addPostalLocality = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addPostalLocality.setType(ContactActivator.NODE_ADDRESS_POSTAL_LOCALITY);
		InformationUnit addPostalRegion = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addPostalRegion.setType(ContactActivator.NODE_ADDRESS_POSTAL_REGION);
		InformationUnit addPostalPostal = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addPostalPostal.setType(ContactActivator.NODE_ADDRESS_POSTAL_POSTAL);
		InformationUnit addPostalCountry = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addPostalCountry.setType(ContactActivator.NODE_ADDRESS_POSTAL_COUNTRY);
		InformationUnit addPostalLatitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addPostalLatitude.setType(ContactActivator.NODE_ADDRESS_POSTAL_LATITUDE);
		InformationUnit addPostalLongitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addPostalLongitude.setType(ContactActivator.NODE_ADDRESS_POSTAL_LONGITUDE);

		InformationUnit addInternatStreet = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addInternatStreet.setType(ContactActivator.NODE_ADDRESS_INTERNAT_STREET);
		InformationUnit addInternatPob = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addInternatPob.setType(ContactActivator.NODE_ADDRESS_INTERNAT_POST_OFFICE_BOX);
		InformationUnit addInternatLocality = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addInternatLocality.setType(ContactActivator.NODE_ADDRESS_INTERNAT_LOCALITY);
		InformationUnit addInternatRegion = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addInternatRegion.setType(ContactActivator.NODE_ADDRESS_INTERNAT_REGION);
		InformationUnit addInternatPostal = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addInternatPostal.setType(ContactActivator.NODE_ADDRESS_INTERNAT_POSTAL);
		InformationUnit addInternatCountry = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addInternatCountry.setType(ContactActivator.NODE_ADDRESS_INTERNAT_COUNTRY);
		InformationUnit addInternatLatitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addInternatLatitude.setType(ContactActivator.NODE_ADDRESS_INTERNAT_LATITUDE);
		InformationUnit addInternatLongitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addInternatLongitude.setType(ContactActivator.NODE_ADDRESS_INTERNAT_LONGITUDE);

		InformationUnit addDomesticStreet = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addDomesticStreet.setType(ContactActivator.NODE_ADDRESS_DOMESTIC_STREET);
		InformationUnit addDomesticPob = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addDomesticPob.setType(ContactActivator.NODE_ADDRESS_DOMESTIC_POST_OFFICE_BOX);
		InformationUnit addDomesticLocality = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addDomesticLocality.setType(ContactActivator.NODE_ADDRESS_DOMESTIC_LOCALITY);
		InformationUnit addDomesticRegion = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addDomesticRegion.setType(ContactActivator.NODE_ADDRESS_DOMESTIC_REGION);
		InformationUnit addDomesticPostal = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addDomesticPostal.setType(ContactActivator.NODE_ADDRESS_DOMESTIC_POSTAL);
		InformationUnit addDomesticCountry = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addDomesticCountry.setType(ContactActivator.NODE_ADDRESS_DOMESTIC_COUNTRY);
		InformationUnit addDomesticLatitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addDomesticLatitude.setType(ContactActivator.NODE_ADDRESS_DOMESTIC_LATITUDE);
		InformationUnit addDomesticLongitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addDomesticLongitude.setType(ContactActivator.NODE_ADDRESS_DOMESTIC_LONGITUDE);

		InformationUnit addOtherStreet = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addOtherStreet.setType(ContactActivator.NODE_ADDRESS_OTHER_STREET);
		InformationUnit addOtherPob = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addOtherPob.setType(ContactActivator.NODE_ADDRESS_OTHER_POST_OFFICE_BOX);
		InformationUnit addOtherLocality = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addOtherLocality.setType(ContactActivator.NODE_ADDRESS_OTHER_LOCALITY);
		InformationUnit addOtherRegion = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addOtherRegion.setType(ContactActivator.NODE_ADDRESS_OTHER_REGION);
		InformationUnit addOtherPostal = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addOtherPostal.setType(ContactActivator.NODE_ADDRESS_OTHER_POSTAL);
		InformationUnit addOtherCountry = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addOtherCountry.setType(ContactActivator.NODE_ADDRESS_OTHER_COUNTRY);
		InformationUnit addOtherLatitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addOtherLatitude.setType(ContactActivator.NODE_ADDRESS_OTHER_LATITUDE);
		InformationUnit addOtherLongitude = InfomngmntFactory.eINSTANCE.createInformationUnit();
		addOtherLongitude.setType(ContactActivator.NODE_ADDRESS_OTHER_LONGITUDE);

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
		returnValue.getChildValues().add(pnMobile);
		returnValue.getChildValues().add(pnModem);
		returnValue.getChildValues().add(pnOthers);
		returnValue.getChildValues().add(pnPager);
		returnValue.getChildValues().add(pnPcs);
		returnValue.getChildValues().add(pnVideo);
		returnValue.getChildValues().add(pnVoice);
		returnValue.getChildValues().add(pnWork);

		// group address
		returnValue.getChildValues().add(addWorkCountry);
		returnValue.getChildValues().add(addWorkLocality);
		returnValue.getChildValues().add(addWorkPob);
		returnValue.getChildValues().add(addWorkPostal);
		returnValue.getChildValues().add(addWorkRegion);
		returnValue.getChildValues().add(addWorkStreet);
		returnValue.getChildValues().add(addWorkLatitude);
		returnValue.getChildValues().add(addWorkLongitude);

		returnValue.getChildValues().add(addHomeCountry);
		returnValue.getChildValues().add(addHomeLocality);
		returnValue.getChildValues().add(addHomePob);
		returnValue.getChildValues().add(addHomePostal);
		returnValue.getChildValues().add(addHomeRegion);
		returnValue.getChildValues().add(addHomeStreet);
		returnValue.getChildValues().add(addHomeLatitude);
		returnValue.getChildValues().add(addHomeLongitude);

		returnValue.getChildValues().add(addParcelCountry);
		returnValue.getChildValues().add(addParcelLocality);
		returnValue.getChildValues().add(addParcelPob);
		returnValue.getChildValues().add(addParcelPostal);
		returnValue.getChildValues().add(addParcelRegion);
		returnValue.getChildValues().add(addParcelStreet);
		returnValue.getChildValues().add(addParcelLatitude);
		returnValue.getChildValues().add(addParcelLongitude);

		returnValue.getChildValues().add(addPostalCountry);
		returnValue.getChildValues().add(addPostalLocality);
		returnValue.getChildValues().add(addPostalPob);
		returnValue.getChildValues().add(addPostalPostal);
		returnValue.getChildValues().add(addPostalRegion);
		returnValue.getChildValues().add(addPostalStreet);
		returnValue.getChildValues().add(addPostalLatitude);
		returnValue.getChildValues().add(addPostalLongitude);

		returnValue.getChildValues().add(addInternatCountry);
		returnValue.getChildValues().add(addInternatLocality);
		returnValue.getChildValues().add(addInternatPob);
		returnValue.getChildValues().add(addInternatPostal);
		returnValue.getChildValues().add(addInternatRegion);
		returnValue.getChildValues().add(addInternatStreet);
		returnValue.getChildValues().add(addInternatLatitude);
		returnValue.getChildValues().add(addInternatLongitude);

		returnValue.getChildValues().add(addDomesticCountry);
		returnValue.getChildValues().add(addDomesticLocality);
		returnValue.getChildValues().add(addDomesticPob);
		returnValue.getChildValues().add(addDomesticPostal);
		returnValue.getChildValues().add(addDomesticRegion);
		returnValue.getChildValues().add(addDomesticStreet);
		returnValue.getChildValues().add(addDomesticLatitude);
		returnValue.getChildValues().add(addDomesticLongitude);

		returnValue.getChildValues().add(addOtherCountry);
		returnValue.getChildValues().add(addOtherLocality);
		returnValue.getChildValues().add(addOtherPob);
		returnValue.getChildValues().add(addOtherPostal);
		returnValue.getChildValues().add(addOtherRegion);
		returnValue.getChildValues().add(addOtherStreet);
		returnValue.getChildValues().add(addOtherLongitude);
		returnValue.getChildValues().add(addOtherLatitude);

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
}
