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
