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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.Birthday;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.Nickname;
import com.google.gdata.data.contacts.Occupation;
import com.google.gdata.data.extensions.AdditionalName;
import com.google.gdata.data.extensions.City;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.PoBox;
import com.google.gdata.data.extensions.PostCode;
import com.google.gdata.data.extensions.Region;
import com.google.gdata.data.extensions.Street;
import com.google.gdata.data.extensions.StructuredPostalAddress;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactConverter {

	private static Map<String, String> staticPhoneTypeMapper;
	private static Map<String, String> staticImTypeMapper;
	private static Map<String, String> staticAdressTypeMapper;

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

		staticAdressTypeMapper = new HashMap<String, String>();
		staticAdressTypeMapper.put("http://schemas.google.com/g/2005#home",
				ContactActivator.NODE_NAME_HOME_ADRESS);
		staticAdressTypeMapper.put("http://schemas.google.com/g/2005#work",
				ContactActivator.NODE_NAME_WORK_ADRESS);
		staticAdressTypeMapper.put("http://schemas.google.com/g/2005#other",
				ContactActivator.NODE_NAME_OTHER_ADRESS);
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
			String rel = postalAddress.getRel();
			String string = staticAdressTypeMapper.get(rel);
			if (string != null) {
				if (postalAddress.hasCity()) {
					edit.setValueByPath(newContact, postalAddress.getCity().getValue(), string,
							ContactActivator.NODE_NAME_ADRESS_LOCALITY);
				}
				if (postalAddress.hasPobox()) {
					edit.setValueByPath(newContact, postalAddress.getPobox().getValue(), string,
							ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX);
				}
				if (postalAddress.hasStreet()) {
					edit.setValueByPath(newContact, postalAddress.getStreet().getValue(), string,
							ContactActivator.NODE_NAME_ADRESS_STREET);
				}
				if (postalAddress.hasPostcode()) {
					edit.setValueByPath(newContact, postalAddress.getPostcode().getValue(), string,
							ContactActivator.NODE_NAME_ADRESS_POSTAL);
				}
				if (postalAddress.hasRegion()) {
					edit.setValueByPath(newContact, postalAddress.getRegion().getValue(), string,
							ContactActivator.NODE_NAME_ADRESS_REGION);
				}

			}

		}
		if (entry.hasBirthday()) {
			String when = entry.getBirthday().getWhen();
			try {
				Date parse = new SimpleDateFormat("yyyy-MM-dd").parse(when);
				edit.setValue(newContact, ContactActivator.NODE_DETAILS_BIRTHDAY, parse);
			} catch (ParseException e) {
				// do nothing
			}
		}
		if (entry.hasOccupation()) {
			edit.setValue(newContact, ContactActivator.NODE_DETAILS_JOB, entry.getOccupation()
					.getValue());
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
			String additionalName = (String) read
					.getValueByNodeId(ContactActivator.NODE_NAME_PERS_NAME_ADDITIONAL);
			if (additionalName != null && additionalName.trim().length() > 0) {
				name.setAdditionalName(new AdditionalName(additionalName, NO_YOMI));
			}
			contact.setName(name);
			contact.setContent(new PlainTextConstruct(adapter.getDescription()));

			contact.getEmailAddresses().clear();
			EList<InformationUnit> dynamicList = read.getDynamicList(ContactActivator.NODE_MAILS);
			for (InformationUnit informationUnit : dynamicList) {
				InformationStructureRead emailRead = InformationStructureRead.newSession(
						informationUnit, ContactActivator.TYPE_ID);
				Email primaryMail = new Email();
				String email = (String) emailRead.getValueByNodeId(ContactActivator.NODE_MAIL);
				boolean primarySet = false;
				if (email != null && email.trim().length() > 0) {
					primaryMail.setAddress(email);
					boolean b = primaryMail.getAddress() != null
							&& primaryMail.getAddress().equals(
									read.getValueByNodeId(ContactActivator.NODE_MAIL_DEF));
					primaryMail.setPrimary(b && !primarySet);
					if (b) {
						primarySet = true;
					}
					primaryMail.setRel("http://schemas.google.com/g/2005#work");
					contact.addEmailAddress(primaryMail);
				}
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

			contact.getStructuredPostalAddresses().clear();
			values = staticAdressTypeMapper.values();
			for (String string : values) {
				/*
				 * Workadress
				 */
				String street = ((String) read.getValueByPath(string,
						ContactActivator.NODE_NAME_ADRESS_STREET));
				String pob = ((String) read.getValueByPath(string,
						ContactActivator.NODE_NAME_ADRESS_POST_OFFICE_BOX));
				String locality = ((String) read.getValueByPath(string,
						ContactActivator.NODE_NAME_ADRESS_LOCALITY));
				String region = ((String) read.getValueByPath(string,
						ContactActivator.NODE_NAME_ADRESS_REGION));
				String postal = ((String) read.getValueByPath(string,
						ContactActivator.NODE_NAME_ADRESS_POSTAL));
				String country = ((String) read.getValueByPath(string,
						ContactActivator.NODE_NAME_ADRESS_COUNTRY));
				if (street != null && street.trim().length() > 0 && locality != null
						&& locality.trim().length() > 0) {
					StructuredPostalAddress adress = new StructuredPostalAddress();
					adress.setStreet(new Street(street == null ? "" : street));
					if (pob != null) {
						adress.setPobox(new PoBox(pob));
					}
					// Region is required
					if (region != null && region.trim().length() > 0) {
						adress.setRegion(new Region(region));
					} else {
						adress.setRegion(new Region("n.a."));
					}
					adress.setCity(new City(locality == null ? "" : locality));
					adress.setPostcode(new PostCode(postal == null ? "" : postal));
					adress.setRel(getKeyByValue(staticAdressTypeMapper, string));
					contact.getStructuredPostalAddresses().add(adress);
				}

			}

			Date date = (Date) read.getValueByNodeId(ContactActivator.NODE_DETAILS_BIRTHDAY);
			if (date != null) {
				Birthday birthday = new Birthday(new SimpleDateFormat("yyyy-MM-dd").format(date));
				contact.setBirthday(birthday);
			}
			contact.setOccupation(new Occupation((String) read
					.getValueByNodeId(ContactActivator.NODE_DETAILS_JOB)));

			String nick = (String) read.getValueByNodeId(ContactActivator.NODE_DETAILS_NAME_NICK);
			if (nick != null && nick.trim().length() > 0) {
				contact.setNickname(new Nickname(nick));
			}
			return contact;

		}
		return null;

	}
}
