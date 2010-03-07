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

import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class ContactActivator extends AbstractUIPlugin {

	public static String TYPE_ID = "CONTACT";

	public static final String NODE_NAME_RAWDATA_IMAGE = "imageData"; //$NON-NLS-1$

	public static final String NODE_NAME_RAWDATA_LOGO = "logoData"; //$NON-NLS-1$

	public static final String ORIGINAL_FILEPATH = "originalfilepath";

	// group person
	public static final String NODE_NAME_PERS_NAME_TITLE = "title"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_FIRST = "firstname"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_ADDITIONAL = "additionals"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_LAST = "lastname"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_NAME_TITLE_AFTER = "titleafter"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_ROLE = "role"; //$NON-NLS-1$
	public static final String NODE_NAME_PERS_ORGANISATION = "organization"; //$NON-NLS-1$

	// group phone number
	public static final String NODE_NAME_PN_HOME = "pnHome"; //$NON-NLS-1$
	public static final String NODE_NAME_PN_WORK = "pnWork"; //$NON-NLS-1$
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
	public static final String NODE_NAME_ADRESSES = "adresses"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS = "adress"; //$NON-NLS-1$

	public static final String NODE_NAME_WORK_ADRESS = "workAdress"; //$NON-NLS-1$
	public static final String NODE_NAME_HOME_ADRESS = "homeAdress"; //$NON-NLS-1$
	public static final String NODE_NAME_INTERNAT_ADRESS = "internatAdress"; //$NON-NLS-1$
	public static final String NODE_NAME_POSTAL_ADRESS = "postalAdress"; //$NON-NLS-1$
	public static final String NODE_NAME_PARCEL_ADRESS = "parcelAdress"; //$NON-NLS-1$
	public static final String NODE_NAME_DOMESTIC_ADRESS = "domesticAdress"; //$NON-NLS-1$
	public static final String NODE_NAME_OTHER_ADRESS = "otherAdress"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS_TYPE = "otherAdress"; //$NON-NLS-1$

	public static final String NODE_NAME_ADRESS_STREET = "street"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS_POST_OFFICE_BOX = "postofficebox"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS_LOCALITY = "locality"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS_REGION = "region"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS_POSTAL = "postal"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS_COUNTRY = "country"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS_LONGITUDE = "longitude"; //$NON-NLS-1$
	public static final String NODE_NAME_ADRESS_LATITUDE = "latitude"; //$NON-NLS-1$

	// group email and instant messaging
	public static final String NODE_MAILS = "emails"; //$NON-NLS-1$
	public static final String NODE_MAIL = "email"; //$NON-NLS-1$
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

	// section misc
	public static final String NODE_MISC_PUBKEY = "miscPubKey"; //$NON-NLS-1$
	public static final String NODE_MISC_MIME = "miscMime"; //$NON-NLS-1$

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.contact";

	// The shared instance
	private static ContactActivator plugin;

	private RemusServiceTracker serviceTracker;

	/**
	 * The constructor
	 */
	public ContactActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		this.serviceTracker = new RemusServiceTracker(getBundle());
		plugin = this;
	}

	public RemusServiceTracker getServiceTracker() {
		return this.serviceTracker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
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
