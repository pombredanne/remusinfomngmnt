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
	public static final String NODE_ADDRESS_WORK_STREET = "addWorkStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_POST_OFFICE_BOX = "addWorkPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_LOCALITY = "addWorkLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_REGION = "addWorkRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_POSTAL = "addWorkPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_WORK_COUNTRY = "addWorkCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_HOME_STREET = "addHomeStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_POST_OFFICE_BOX = "addHomePob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_LOCALITY = "addHomeLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_REGION = "addHomeRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_POSTAL = "addHomePostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_HOME_COUNTRY = "addHomeCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_INTERNAT_STREET = "addInternatStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_POST_OFFICE_BOX = "addInternatPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_LOCALITY = "addInternatLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_REGION = "addInternatRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_POSTAL = "addInternatPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_INTERNAT_COUNTRY = "addInternatCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_POSTAL_STREET = "addPostalStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_POST_OFFICE_BOX = "addPostalPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_LOCALITY = "addPostalLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_REGION = "addPostalRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_POSTAL = "addPostalPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_POSTAL_COUNTRY = "addPostalCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_PARCEL_STREET = "addParcelStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_POST_OFFICE_BOX = "addParcelPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_LOCALITY = "addParcelLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_REGION = "addParcelRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_POSTAL = "addParcelPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_PARCEL_COUNTRY = "addParcelCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_DOMESTIC_STREET = "addDomesticStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_POST_OFFICE_BOX = "addDomesticPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_LOCALITY = "addDomesticLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_REGION = "addDomesticRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_POSTAL = "addDomesticPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_DOMESTIC_COUNTRY = "addDomesticCountry"; //$NON-NLS-1$
	
	public static final String NODE_ADDRESS_OTHER_STREET = "addOtherStreet"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_POST_OFFICE_BOX = "addOtherPob"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_LOCALITY = "addOtherLocality"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_REGION = "addOtherRegion"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_POSTAL = "addOtherPostal"; //$NON-NLS-1$
	public static final String NODE_ADDRESS_OTHER_COUNTRY = "addOtherCountry"; //$NON-NLS-1$
	

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
