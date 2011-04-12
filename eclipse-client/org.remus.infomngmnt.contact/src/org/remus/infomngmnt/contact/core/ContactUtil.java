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
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.common.core.util.KeyValueObject;
import org.eclipse.remus.util.InformationUtil;

import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.messages.Messages;
import org.remus.infomngmnt.contact.preferences.ContactPreferenceInitializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactUtil {

	private static final String FORMATTED_REGEXP = "\\$[a-zA-Z0-9_-]+"; //$NON-NLS-1$

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
				ContactPreferenceInitializer.FORMATTED_ADDRESS_PATTERN);
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

		returnValue[0] = new KeyValueObject(ContactActivator.NODE_INSTMESS_AIM, Messages.ContactUtil_AIM);
		returnValue[1] = new KeyValueObject(ContactActivator.NODE_INSTMESS_GADU, Messages.ContactUtil_GaduGadu);
		returnValue[2] = new KeyValueObject(ContactActivator.NODE_INSTMESS_GROUPWISE, Messages.ContactUtil_GroupWise);
		returnValue[3] = new KeyValueObject(ContactActivator.NODE_INSTMESS_ICQ, Messages.ContactUtil_ICQ);
		returnValue[4] = new KeyValueObject(ContactActivator.NODE_INSTMESS_IRC, Messages.ContactUtil_IRC);
		returnValue[5] = new KeyValueObject(ContactActivator.NODE_INSTMESS_JABBER, Messages.ContactUtil_Jabber);
		returnValue[6] = new KeyValueObject(ContactActivator.NODE_INSTMESS_MSN, Messages.ContactUtil_MSN);
		returnValue[7] = new KeyValueObject(ContactActivator.NODE_INSTMESS_MEANWHILE, Messages.ContactUtil_Meanwhile);
		returnValue[8] = new KeyValueObject(ContactActivator.NODE_INSTMESS_SMS, Messages.ContactUtil_SMS);
		returnValue[9] = new KeyValueObject(ContactActivator.NODE_INSTMESS_SKYPE, Messages.ContactUtil_Skype);
		returnValue[10] = new KeyValueObject(ContactActivator.NODE_INSTMESS_YAHOO, Messages.ContactUtil_Yahoo);

		return returnValue;
	}

	public static KeyValueObject[] getAdressCollection() {
		KeyValueObject[] returnValue = new KeyValueObject[7];

		returnValue[0] = new KeyValueObject(ContactActivator.NODE_NAME_WORK_ADDRESS, Messages.ContactUtil_Work);
		returnValue[1] = new KeyValueObject(ContactActivator.NODE_NAME_HOME_ADDRESS, Messages.ContactUtil_Home);
		returnValue[2] = new KeyValueObject(ContactActivator.NODE_NAME_INTERNAT_ADDRESS,
				Messages.ContactUtil_International);
		returnValue[3] = new KeyValueObject(ContactActivator.NODE_NAME_POSTAL_ADDRESS, Messages.ContactUtil_Postal);
		returnValue[4] = new KeyValueObject(ContactActivator.NODE_NAME_PARCEL_ADDRESS, Messages.ContactUtil_Parcel);
		returnValue[5] = new KeyValueObject(ContactActivator.NODE_NAME_DOMESTIC_ADDRESS, Messages.ContactUtil_Domestic);
		returnValue[6] = new KeyValueObject(ContactActivator.NODE_NAME_OTHER_ADDRESS, Messages.ContactUtil_Other);

		return returnValue;
	}

	public static KeyValueObject[] getPhoneCollection() {
		KeyValueObject[] returnValue = new KeyValueObject[13];

		returnValue[0] = new KeyValueObject(ContactActivator.NODE_NAME_PN_HOME, Messages.ContactUtil_Home);
		returnValue[1] = new KeyValueObject(ContactActivator.NODE_NAME_PN_WORK, Messages.ContactUtil_Work);
		returnValue[2] = new KeyValueObject(ContactActivator.NODE_NAME_PN_VOICE, Messages.ContactUtil_Voice);
		returnValue[3] = new KeyValueObject(ContactActivator.NODE_NAME_PN_FAX, Messages.ContactUtil_Fax);
		returnValue[4] = new KeyValueObject(ContactActivator.NODE_NAME_PN_MOBILE, Messages.ContactUtil_Mobile);
		returnValue[5] = new KeyValueObject(ContactActivator.NODE_NAME_PN_MAILBOX, Messages.ContactUtil_Mailbox);
		returnValue[6] = new KeyValueObject(ContactActivator.NODE_NAME_PN_MODEM, Messages.ContactUtil_Modem);
		returnValue[7] = new KeyValueObject(ContactActivator.NODE_NAME_PN_CAR, Messages.ContactUtil_Car);
		returnValue[8] = new KeyValueObject(ContactActivator.NODE_NAME_PN_ISDN, Messages.ContactUtil_ISDN);
		returnValue[9] = new KeyValueObject(ContactActivator.NODE_NAME_PN_VIDEO, Messages.ContactUtil_Video);
		returnValue[10] = new KeyValueObject(ContactActivator.NODE_NAME_PN_PCS, Messages.ContactUtil_PCS);
		returnValue[11] = new KeyValueObject(ContactActivator.NODE_NAME_PN_PAGER, Messages.ContactUtil_Pager);
		returnValue[12] = new KeyValueObject(ContactActivator.NODE_NAME_PN_OTHERS, Messages.ContactUtil_Other);

		return returnValue;
	}

}
