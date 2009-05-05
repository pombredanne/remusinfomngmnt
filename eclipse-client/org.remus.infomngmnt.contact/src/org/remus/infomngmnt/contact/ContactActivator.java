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
package org.remus.infomngmnt.contact;
/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ContactActivator extends AbstractUIPlugin {

	public static String PASSWORD_INFO_ID = "CONTACT";
	
	public static final String TYPE_ID = "IMAGE"; //$NON-NLS-1$
	
	public static final String NODE_NAME_RAWDATA_IMAGE = "imageData"; //$NON-NLS-1$
	
	public static final String NODE_NAME_RAWDATA_LOGO = "logoData"; //$NON-NLS-1$
	
	public static final String NODE_NAME_EXIF = "exifdata"; //$NON-NLS-1$
	
	public static final String ORIGINAL_FILEPATH = "originFilePath"; //$NON-NLS-1$

	// group person
	public static final String NODE_NAME_PERS_NAME_TITLE = "persTitle"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_FIRST = "persFirst"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_ADDITIONAL = "persAdditional"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_LAST = "persLast"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_TITLE_AFTER = "persTitleAfter"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_FORMATTED = "persFormatted"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_ROLE = "persRole"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_ORGANISATION = "persOrganisation"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_COMPLETE = "persComplete"; //$NON-NLS-1$
	
	// group phone number
	public static final String NODE_NAME_PN_HOME = "pnHome"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_WORK = "pnWork"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_MESSANGER = "pnMessanger"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_VOICE = "pnVoice"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_FAX = "pnFAX"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_MOBILE = "pnMobile"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_MAILBOX = "pnMailbox"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_MODEM = "pnModem"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_CAR = "pnCar"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_ISDN = "pnIsdn"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_VIDEO = "pnVideo"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_PCS = "pnPcs"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_PAGER = "pnPager"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_OTHERS = "pnOthers"; //$NON-NLS-1$
	
	// group address
	public static final String NODE_ADDRESS_WORK_STREET = "workStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_POST_OFFICE_BOX = "workPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_LOCALITY = "workLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_REGION = "workRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_POSTAL = "workPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_COUNTRY = "workCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_HOME_STREET = "homeStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_POST_OFFICE_BOX = "homePob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_LOCALITY = "homeLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_REGION = "homeRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_POSTAL = "homePostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_COUNTRY = "homeCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_INTERNAT_STREET = "internatStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_POST_OFFICE_BOX = "internatPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_LOCALITY = "internatLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_REGION = "internatRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_POSTAL = "internatPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_COUNTRY = "internatCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_POSTAL_STREET = "postalStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_POST_OFFICE_BOX = "postalPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_LOCALITY = "postalLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_REGION = "postalRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_POSTAL = "postalPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_COUNTRY = "postalCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_PARCEL_STREET = "parcelStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_POST_OFFICE_BOX = "parcelPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_LOCALITY = "parcelLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_REGION = "parcelRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_POSTAL = "parcelPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_COUNTRY = "parcelCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_DOMESTIC_STREET = "domesticStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_POST_OFFICE_BOX = "domesticPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_LOCALITY = "domesticLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_REGION = "domesticRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_POSTAL = "domesticPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_COUNTRY = "domesticCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_OTHER_STREET = "otherStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_POST_OFFICE_BOX = "otherPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_LOCALITY = "otherLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_REGION = "otherRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_POSTAL = "otherPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_COUNTRY = "otherCountry"; //$NON-NLS-1$
	
	//group email and instant messaging
	public static final String NODE_MAIL_1 = "email1"; //$NON-NLS-1$
	public static final String NODE_MAIL_2 = "email2"; //$NON-NLS-1$
	public static final String NODE_MAIL_3 = "email3"; //$NON-NLS-1$
	public static final String NODE_MAIL_4 = "email4"; //$NON-NLS-1$
	public static final String NODE_MAIL_5 = "email5"; //$NON-NLS-1$
	public static final String NODE_MAIL_6 = "email6"; //$NON-NLS-1$
	public static final String NODE_MAIL_7 = "email7"; //$NON-NLS-1$
	public static final String NODE_MAIL_8 = "email8"; //$NON-NLS-1$
	public static final String NODE_MAIL_9 = "email9"; //$NON-NLS-1$
	public static final String NODE_MAIL_10 = "email10"; //$NON-NLS-1$
	public static final String NODE_MAIL_DEF = "emaildef"; //$NON-NLS-1$

	public static final String NODE_INSTMESS_AIM = "instMessAim"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_GADU = "instMessGadu"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_GROUPWISE = "instMessGW"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_ICQ = "instMessIcq"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_IRC = "instMessIrc"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_JABBER = "instMessJabber"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_MSN = "instMessMsn"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_MEANWHILE = "instMessMW"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_SMS = "instMessSms"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_SKYPE = "instMessSkype"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_YAHOO = "instMessYahoo"; //$NON-NLS-1$
	public static final String NODE_INSTMESS_DEFAULT = "instMessDef"; //$NON-NLS-1$
	
	public static final String NODE_BLOG_FEED = "blogFeed"; //$NON-NLS-1$
	public static final String NODE_FRONTPAGE = "frontpage"; //$NON-NLS-1$
	
	// section details
	public static final String NODE_DETAILS_DEPARTMENT = "detDep"; //$NON-NLS-1$
	public static final String NODE_DETAILS_BUREAU = "detBur"; //$NON-NLS-1$
	public static final String NODE_DETAILS_NAME_MANAGER = "detNaMan"; //$NON-NLS-1$
	public static final String NODE_DETAILS_NAME_ASSISTANT = "detNaAss"; //$NON-NLS-1$
	public static final String NODE_DETAILS_JOB = "detJob"; //$NON-NLS-1$
	public static final String NODE_DETAILS_TITLE = "detTitle"; //$NON-NLS-1$
	public static final String NODE_DETAILS_NAME_NICK = "detNaNick"; //$NON-NLS-1$
	public static final String NODE_DETAILS_BIRTHDAY = "detBirth"; //$NON-NLS-1$
	public static final String NODE_DETAILS_JUBILEE = "detJubi"; //$NON-NLS-1$
	public static final String NODE_DETAILS_NAME_PARTNER = "detNaPart"; //$NON-NLS-1$
	public static final String NODE_DETAILS_NOTES = "detNotes"; //$NON-NLS-1$
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.contact";

	// The shared instance
	private static ContactActivator plugin;
	
	/**
	 * The constructor
	 */
	public ContactActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ContactActivator getDefault() {
		return plugin;
	}

}
