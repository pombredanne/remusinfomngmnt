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

package org.remus.infomngmnt.connector.googlecontacts;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.StructuredPostalAddress;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactConverter {

	private static Map<String, String> staticPhoneTypeMapper;
	private static Map<String, String> staticImTypeMapper;

	static {
		staticPhoneTypeMapper = new HashMap<String, String>();
		staticPhoneTypeMapper.put("http://schemas.google.com/g/2005#home",
				ContactActivator.NODE_NAME_PN_HOME);
		staticPhoneTypeMapper.put("http://schemas.google.com/g/2005#work",
				ContactActivator.NODE_NAME_PN_WORK);
		staticPhoneTypeMapper.put("http://schemas.google.com/g/2005#home_fax",
				ContactActivator.NODE_NAME_PN_MODEM);
		staticPhoneTypeMapper.put("http://schemas.google.com/g/2005#work_fax",
				ContactActivator.NODE_NAME_PN_FAX);
		staticPhoneTypeMapper.put("http://schemas.google.com/g/2005#pager",
				ContactActivator.NODE_NAME_PN_PAGER);
		staticPhoneTypeMapper.put("http://schemas.google.com/g/2005#home_other",
				ContactActivator.NODE_NAME_PN_OTHERS);

		staticImTypeMapper = new HashMap<String, String>();
		staticImTypeMapper.put("http://schemas.google.com/g/2005#GOOGLE_TALK",
				ContactActivator.NODE_INSTMESS_GROUPWISE);
		staticImTypeMapper.put("http://schemas.google.com/g/2005#AIM",
				ContactActivator.NODE_INSTMESS_AIM);
		staticImTypeMapper.put("http://schemas.google.com/g/2005#YAHOO",
				ContactActivator.NODE_INSTMESS_YAHOO);
		staticImTypeMapper.put("http://schemas.google.com/g/2005#SKYPE",
				ContactActivator.NODE_INSTMESS_SKYPE);
		staticImTypeMapper.put("http://schemas.google.com/g/2005#QQ",
				ContactActivator.NODE_INSTMESS_GADU);
		staticImTypeMapper.put("http://schemas.google.com/g/2005#MSN",
				ContactActivator.NODE_INSTMESS_MSN);
		staticImTypeMapper.put("http://schemas.google.com/g/2005#ICQ",
				ContactActivator.NODE_INSTMESS_ICQ);
		staticImTypeMapper.put("http://schemas.google.com/g/2005#JABBER",
				ContactActivator.NODE_INSTMESS_JABBER);
	}

	static String getKeyByValue(final Map<String, String> collection, final String value) {
		Set<String> keySet = collection.keySet();
		for (String string : keySet) {
			if (value.equals(collection.get(string))) {
				return string;
			}
		}
		return null;

	}

	public InformationUnit fromRepo(final ContactEntry entry) {
		InformationStructureEdit edit = InformationStructureEdit
				.newSession(ContactActivator.TYPE_ID);
		InformationUnit newContact = edit.newInformationUnit();

		if (entry.getName().hasGivenName()) {
			edit.setValue(newContact, ContactActivator.NODE_NAME_PERS_NAME_FIRST, entry.getName()
					.getGivenName().getValue());
		}
		if (entry.getName().hasFamilyName()) {
			edit.setValue(newContact, ContactActivator.NODE_NAME_PERS_NAME_LAST, entry.getName()
					.getFamilyName().getValue());
		}
		if (entry.getName().getAdditionalName() != null) {
			edit.setValue(newContact, ContactActivator.NODE_NAME_PERS_NAME_ADDITIONAL, entry
					.getName().getAdditionalName().getValue());
		}
		if (entry.getName().hasFullName() && !entry.getName().hasGivenName()
				&& !entry.getName().hasFamilyName()) {
			String value = entry.getName().getFullName().getValue();
			String[] split = value.split(" ");
			if (split.length > 1) {
				edit.setValue(newContact, ContactActivator.NODE_NAME_PERS_NAME_FIRST, split[0]);
				String[] lastname = new String[split.length - 1];
				System.arraycopy(split, 1, lastname, 0, split.length - 1);
				edit.setValue(newContact, ContactActivator.NODE_NAME_PERS_NAME_LAST, StringUtils
						.join(lastname, " "));

			} else {
				edit.setValue(newContact, ContactActivator.NODE_NAME_PERS_NAME_LAST, value);
			}
		}
		List<Organization> organizations = entry.getOrganizations();
		if (organizations != null && organizations.size() > 0) {
			if (organizations.get(0).hasOrgName()) {
				edit.setValue(newContact, ContactActivator.NODE_NAME_PERS_ORGANISATION,
						organizations.get(0).getOrgName().getValue());
			}
			if (organizations.get(0).getOrgJobDescription() != null) {
				edit.setValue(newContact, ContactActivator.NODE_NAME_PERS_ROLE, organizations
						.get(0).getOrgJobDescription().getValue());
			}
		}
		List<PhoneNumber> phoneNumbers = entry.getPhoneNumbers();
		if (phoneNumbers != null) {
			for (PhoneNumber phoneNumber : phoneNumbers) {
				String rel = phoneNumber.getRel();
				String string = staticPhoneTypeMapper.get(rel);
				if (string != null) {
					edit.setValue(newContact, string, phoneNumber.getPhoneNumber());
				}
			}

		}

		List<Email> emailAddresses = entry.getEmailAddresses();
		for (Email email : emailAddresses) {
			if (email.getPrimary()) {
				edit.setValue(newContact, ContactActivator.NODE_MAIL_DEF, email.getAddress());
			}
			InformationUnit emailNode = edit.createSubType(ContactActivator.NODE_MAIL, email
					.getAddress());
			edit.addDynamicNode(newContact, emailNode, null);
		}
		if (entry.getWebsites().size() > 0) {
			edit.setValue(newContact, ContactActivator.NODE_FRONTPAGE, entry.getWebsites().get(0)
					.getHref());
		}

		List<Im> imAddresses = entry.getImAddresses();
		for (Im im : imAddresses) {
			String rel = im.getProtocol();
			String string = staticPhoneTypeMapper.get(rel);
			if (string != null) {
				edit.setValue(newContact, string, im.getAddress());
			}
		}

		List<StructuredPostalAddress> postalAddresses = entry.getStructuredPostalAddresses();
		for (StructuredPostalAddress postalAddress : postalAddresses) {
			System.out.println(postalAddress.getRel());

		}
		return newContact;
	}

	public ContactEntry toRepo(final InformationUnitListItem item, final ContactEntry contact) {
		InformationUnit adapter = (InformationUnit) item.getAdapter(InformationUnit.class);
		if (adapter != null && ContactActivator.TYPE_ID.equals(adapter.getType())) {
			InformationStructureRead read = InformationStructureRead.newSession(adapter);

			Name name = new Name();
			final String NO_YOMI = null;
			name.setGivenName(new GivenName((String) read
					.getValueByNodeId(ContactActivator.NODE_NAME_PERS_NAME_FIRST), NO_YOMI));
			name.setFamilyName(new FamilyName((String) read
					.getValueByNodeId(ContactActivator.NODE_NAME_PERS_NAME_LAST), NO_YOMI));
			name.setFullName(new FullName(name.getGivenName().getValue() + " "
					+ name.getFamilyName().getValue(), NO_YOMI));
			contact.setName(name);
			contact.setContent(new PlainTextConstruct(adapter.getDescription()));

			contact.getEmailAddresses().clear();
			EList<InformationUnit> dynamicList = read.getDynamicList(ContactActivator.NODE_MAILS);
			for (InformationUnit informationUnit : dynamicList) {
				InformationStructureRead emailRead = InformationStructureRead.newSession(
						informationUnit, ContactActivator.TYPE_ID);
				Email primaryMail = new Email();
				primaryMail.setAddress((String) emailRead
						.getValueByNodeId(ContactActivator.NODE_MAIL));
				primaryMail.setPrimary(primaryMail.getAddress() != null
						&& primaryMail.getAddress().equals(
								read.getValueByNodeId(ContactActivator.NODE_MAIL_DEF)));
				contact.addEmailAddress(primaryMail);
			}

			contact.getImAddresses().clear();
			Collection<String> values = staticImTypeMapper.values();
			for (String string : values) {
				Object valueByNodeId = read.getValueByNodeId(string);
				if (valueByNodeId != null) {
					Im im = new Im();
					im.setAddress(valueByNodeId.toString());
					im.setProtocol(getKeyByValue(staticImTypeMapper, string));
					im.setPrimary(valueByNodeId.equals(read
							.getValueByNodeId(ContactActivator.NODE_INSTMESS_DEFAULT)));
					contact.addImAddress(im);
				}
			}

			contact.getPhoneNumbers().clear();
			values = staticPhoneTypeMapper.values();
			for (String string : values) {
				Object valueByNodeId = read.getValueByNodeId(string);
				if (valueByNodeId != null) {
					PhoneNumber phone = new PhoneNumber();
					phone.setRel(getKeyByValue(staticPhoneTypeMapper, string));
					phone.setPhoneNumber(valueByNodeId.toString());
					contact.addPhoneNumber(phone);
				}
			}
			return contact;

		}
		return null;

	}
}
