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

package org.remus.infomngmnt.contact.core;

import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.KeyValueObject;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.preferences.ContactPreferenceInitializer;
import org.remus.infomngmnt.core.model.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactUtil {

	private static final String FORMATTED_REGEXP = "\\$[a-zA-Z0-9_-]+";

	public static String getFormattedName(final InformationUnit unit) {
		StringWriter returnValue = new StringWriter();
		String string = ContactActivator.getDefault().getPreferenceStore().getString(
				ContactPreferenceInitializer.FORMATTED_NAME_PATTERN);
		Pattern compile = Pattern.compile(FORMATTED_REGEXP);
		Matcher matcher = compile.matcher(string);
		int lastEnd = 0;
		while (matcher.find()) {
			String group = matcher.group();
			matcher.start();
			returnValue.append(string.substring(lastEnd, matcher.start()));
			lastEnd = matcher.end();
			String substring = group.substring(1);
			InformationUnit childByType = InformationUtil.getChildByType(unit, substring);
			if (childByType != null && childByType.getStringValue() != null) {
				returnValue.append(childByType.getStringValue());
			}
		}
		return StringUtils.strip(returnValue.toString());
	}

	public static String getFormattedAdress(final InformationUnit unit) {
		StringWriter returnValue = new StringWriter();
		String string = ContactActivator.getDefault().getPreferenceStore().getString(
				ContactPreferenceInitializer.FORMATTED_ADRESS_PATTERN);
		Pattern compile = Pattern.compile(FORMATTED_REGEXP, Pattern.MULTILINE);
		Matcher matcher = compile.matcher(string);
		int lastEnd = 0;
		while (matcher.find()) {
			String group = matcher.group();
			matcher.start();
			returnValue.append(string.substring(lastEnd, matcher.start()));
			lastEnd = matcher.end();
			String substring = group.substring(1);
			InformationUnit childByType = InformationUtil.getChildByType(unit, substring);
			if (childByType != null && childByType.getStringValue() != null) {
				returnValue.append(childByType.getStringValue());
			}
		}
		return returnValue.toString();
	}

	public static KeyValueObject[] getImObjectCollection() {
		KeyValueObject[] returnValue = new KeyValueObject[11];

		returnValue[0] = new KeyValueObject(ContactActivator.NODE_INSTMESS_AIM, "AIM");
		returnValue[1] = new KeyValueObject(ContactActivator.NODE_INSTMESS_GADU, "Gadu-Gadu");
		returnValue[2] = new KeyValueObject(ContactActivator.NODE_INSTMESS_GROUPWISE, "GroupWise");
		returnValue[3] = new KeyValueObject(ContactActivator.NODE_INSTMESS_ICQ, "ICQ");
		returnValue[4] = new KeyValueObject(ContactActivator.NODE_INSTMESS_IRC, "IRC");
		returnValue[5] = new KeyValueObject(ContactActivator.NODE_INSTMESS_JABBER, "Jabber");
		returnValue[6] = new KeyValueObject(ContactActivator.NODE_INSTMESS_MSN, "MSN");
		returnValue[7] = new KeyValueObject(ContactActivator.NODE_INSTMESS_MEANWHILE, "Meanwhile");
		returnValue[8] = new KeyValueObject(ContactActivator.NODE_INSTMESS_SMS, "SMS");
		returnValue[9] = new KeyValueObject(ContactActivator.NODE_INSTMESS_SKYPE, "Skype");
		returnValue[10] = new KeyValueObject(ContactActivator.NODE_INSTMESS_YAHOO, "Yahoo!");

		return returnValue;
	}

	public static KeyValueObject[] getAdressCollection() {
		KeyValueObject[] returnValue = new KeyValueObject[7];

		returnValue[0] = new KeyValueObject(ContactActivator.NODE_NAME_WORK_ADRESS, "Work");
		returnValue[1] = new KeyValueObject(ContactActivator.NODE_NAME_HOME_ADRESS, "Home");
		returnValue[2] = new KeyValueObject(ContactActivator.NODE_NAME_INTERNAT_ADRESS,
				"International");
		returnValue[3] = new KeyValueObject(ContactActivator.NODE_NAME_POSTAL_ADRESS, "Postal");
		returnValue[4] = new KeyValueObject(ContactActivator.NODE_NAME_PARCEL_ADRESS, "Parcel");
		returnValue[5] = new KeyValueObject(ContactActivator.NODE_NAME_DOMESTIC_ADRESS, "Domestic");
		returnValue[6] = new KeyValueObject(ContactActivator.NODE_NAME_OTHER_ADRESS, "Other");

		return returnValue;
	}

	public static KeyValueObject[] getPhoneCollection() {
		KeyValueObject[] returnValue = new KeyValueObject[13];

		returnValue[0] = new KeyValueObject(ContactActivator.NODE_NAME_PN_HOME, "Home");
		returnValue[1] = new KeyValueObject(ContactActivator.NODE_NAME_PN_WORK, "Work");
		returnValue[2] = new KeyValueObject(ContactActivator.NODE_NAME_PN_VOICE, "Voice");
		returnValue[3] = new KeyValueObject(ContactActivator.NODE_NAME_PN_FAX, "Fax");
		returnValue[4] = new KeyValueObject(ContactActivator.NODE_NAME_PN_MOBILE, "Mobile");
		returnValue[5] = new KeyValueObject(ContactActivator.NODE_NAME_PN_MAILBOX, "Mailbox");
		returnValue[6] = new KeyValueObject(ContactActivator.NODE_NAME_PN_MODEM, "Modem");
		returnValue[7] = new KeyValueObject(ContactActivator.NODE_NAME_PN_CAR, "Car");
		returnValue[8] = new KeyValueObject(ContactActivator.NODE_NAME_PN_ISDN, "ISDN");
		returnValue[9] = new KeyValueObject(ContactActivator.NODE_NAME_PN_VIDEO, "Video");
		returnValue[10] = new KeyValueObject(ContactActivator.NODE_NAME_PN_PCS, "PCS");
		returnValue[11] = new KeyValueObject(ContactActivator.NODE_NAME_PN_PAGER, "Pager");
		returnValue[12] = new KeyValueObject(ContactActivator.NODE_NAME_PN_OTHERS, "Other");

		return returnValue;
	}

}
