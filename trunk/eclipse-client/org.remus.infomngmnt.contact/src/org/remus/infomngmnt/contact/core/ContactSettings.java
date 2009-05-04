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
public class ContactSettings {
	public static final String[] DEFAULT_ITEMS_COMBO_NAME_FORMATTED = {"Common Name", "Complete Name", "Converse Name, Comma Separated"};
	public static final String[] DEFAULT_ITMES_COMBO_ADDRESS_CHOOSER = {"Home", "Work", "International", "Postal", "Parcel", "Domestic", "Other"};
	public static final String[] DEFAULT_ITMES_COMBO_COUNTRY_CHOOSER = {"Armenia","Belgium","Germany","France","United States"};
	public static final String[] DEFAULT_ITMES_COMBO_IM_ADDRESS_CHOOSER = {"AIM","Gadu-Gadu","GroupWise","ICQ","IRC", "Jabber", "MSN", "Meanwhile", "SMS", "Skype", "Yahoo!"};
	public static final String[] DEFAULT_ITEMS_COMBO_PHONENUMBER_CHOOSER = {"Home", "Work", "Messanger", "Voice", "Fax", "Mobile", "Video", "Mailbox", "Modem", "Car", "ISDN", "PCS", "Pager", "Others"};
	
	public static final String AC_COMBO_NAME_FORMATTED_INDEX = "cbnaforind";
	public static final String AC_USER_SETTINGS = "usersettings";
	public static final String AC_COMBO_DEFAULT_ADDRESS_INDEX = "cbdefaddrind";
	public static final String CUR_STREET = "curStreet";
	public static final String CUR_LOCALITY = "curLocality";
	public static final String CUR_POB = "curPob";
	public static final String CUR_REGION = "curRegion";
	public static final String CUR_POSTAL = "curPostal";
	public static final String CUR_COUNTRY = "curCountry";
}
