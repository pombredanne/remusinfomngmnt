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
package org.remus.infomngmnt.contact.ui.general;
/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import org.eclipse.swt.widgets.Combo;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ContactSettings;

public class AddressComboChooser {

	private String curStreet;
	private String curLocality;
	private String curCountry;
	private String curPob;
	private String curRegion;
	private String curPostal;
	private String curLongitude;
	private String curLatitude;

	public AddressComboChooser(Combo combo, String[] items) {
		if (combo.getText().contains(items[0])) {
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_STREET, ContactActivator.NODE_ADDRESS_HOME_STREET);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POB, ContactActivator.NODE_ADDRESS_HOME_POST_OFFICE_BOX);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LOCALITY, ContactActivator.NODE_ADDRESS_HOME_LOCALITY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_REGION, ContactActivator.NODE_ADDRESS_HOME_REGION);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POSTAL, ContactActivator.NODE_ADDRESS_HOME_POSTAL);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_COUNTRY, ContactActivator.NODE_ADDRESS_HOME_COUNTRY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LONGITUDE, ContactActivator.NODE_ADDRESS_HOME_LONGITUDE);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LATITUDE, ContactActivator.NODE_ADDRESS_HOME_LATITUDE);	
		}
		else if (combo.getText().contains(items[1])) {
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_STREET,ContactActivator.NODE_ADDRESS_WORK_STREET);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POB, ContactActivator.NODE_ADDRESS_WORK_POST_OFFICE_BOX);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LOCALITY, ContactActivator.NODE_ADDRESS_WORK_LOCALITY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_REGION, ContactActivator.NODE_ADDRESS_WORK_REGION);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POSTAL, ContactActivator.NODE_ADDRESS_WORK_POSTAL);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_COUNTRY, ContactActivator.NODE_ADDRESS_WORK_COUNTRY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LONGITUDE, ContactActivator.NODE_ADDRESS_WORK_LONGITUDE);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LATITUDE, ContactActivator.NODE_ADDRESS_WORK_LATITUDE);
		}
		else if (combo.getText().contains(items[2])) {
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_STREET, ContactActivator.NODE_ADDRESS_INTERNAT_STREET);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POB, ContactActivator.NODE_ADDRESS_INTERNAT_POST_OFFICE_BOX);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LOCALITY, ContactActivator.NODE_ADDRESS_INTERNAT_LOCALITY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_REGION, ContactActivator.NODE_ADDRESS_INTERNAT_REGION);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POSTAL, ContactActivator.NODE_ADDRESS_INTERNAT_POSTAL);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_COUNTRY, ContactActivator.NODE_ADDRESS_INTERNAT_COUNTRY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LONGITUDE, ContactActivator.NODE_ADDRESS_INTERNAT_LONGITUDE);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LATITUDE, ContactActivator.NODE_ADDRESS_INTERNAT_LATITUDE);
		}
		else if (combo.getText().contains(items[3])) {
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_STREET, ContactActivator.NODE_ADDRESS_POSTAL_STREET);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POB, ContactActivator.NODE_ADDRESS_POSTAL_POST_OFFICE_BOX);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LOCALITY, ContactActivator.NODE_ADDRESS_POSTAL_LOCALITY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_REGION, ContactActivator.NODE_ADDRESS_POSTAL_REGION);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POSTAL, ContactActivator.NODE_ADDRESS_POSTAL_POSTAL);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_COUNTRY, ContactActivator.NODE_ADDRESS_POSTAL_COUNTRY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LONGITUDE, ContactActivator.NODE_ADDRESS_POSTAL_LONGITUDE);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LATITUDE, ContactActivator.NODE_ADDRESS_POSTAL_LATITUDE);
		}
		else if (combo.getText().contains(items[4])) {
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_STREET, ContactActivator.NODE_ADDRESS_PARCEL_STREET);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POB, ContactActivator.NODE_ADDRESS_PARCEL_POST_OFFICE_BOX);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LOCALITY, ContactActivator.NODE_ADDRESS_PARCEL_LOCALITY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_REGION, ContactActivator.NODE_ADDRESS_PARCEL_REGION);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POSTAL, ContactActivator.NODE_ADDRESS_PARCEL_POSTAL);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_COUNTRY, ContactActivator.NODE_ADDRESS_PARCEL_COUNTRY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LONGITUDE, ContactActivator.NODE_ADDRESS_PARCEL_LONGITUDE);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LATITUDE, ContactActivator.NODE_ADDRESS_PARCEL_LATITUDE);
		}
		else if (combo.getText().contains(items[5])) {
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_STREET, ContactActivator.NODE_ADDRESS_DOMESTIC_STREET);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POB, ContactActivator.NODE_ADDRESS_DOMESTIC_POST_OFFICE_BOX);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LOCALITY, ContactActivator.NODE_ADDRESS_DOMESTIC_LOCALITY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_REGION, ContactActivator.NODE_ADDRESS_DOMESTIC_REGION);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POSTAL, ContactActivator.NODE_ADDRESS_DOMESTIC_POSTAL);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_COUNTRY, ContactActivator.NODE_ADDRESS_DOMESTIC_COUNTRY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LONGITUDE, ContactActivator.NODE_ADDRESS_DOMESTIC_LONGITUDE);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LATITUDE, ContactActivator.NODE_ADDRESS_DOMESTIC_LATITUDE);
		}
		else if (combo.getText().contains(items[6])) {
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_STREET, ContactActivator.NODE_ADDRESS_OTHER_STREET);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POB, ContactActivator.NODE_ADDRESS_OTHER_POST_OFFICE_BOX);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LOCALITY, ContactActivator.NODE_ADDRESS_OTHER_LOCALITY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_REGION, ContactActivator.NODE_ADDRESS_OTHER_REGION);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_POSTAL, ContactActivator.NODE_ADDRESS_OTHER_POSTAL);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_COUNTRY, ContactActivator.NODE_ADDRESS_OTHER_COUNTRY);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LONGITUDE, ContactActivator.NODE_ADDRESS_OTHER_LONGITUDE);
			ContactActivator.getDefault().getDialogSettings().put(ContactSettings.CUR_LATITUDE, ContactActivator.NODE_ADDRESS_OTHER_LATITUDE);
		}
		curStreet = ContactActivator.getDefault().getDialogSettings().get(ContactSettings.CUR_STREET);
		curLocality = ContactActivator.getDefault().getDialogSettings().get(ContactSettings.CUR_LOCALITY);
		curCountry = ContactActivator.getDefault().getDialogSettings().get(ContactSettings.CUR_COUNTRY);
		curPob = ContactActivator.getDefault().getDialogSettings().get(ContactSettings.CUR_POB);
		curPostal = ContactActivator.getDefault().getDialogSettings().get(ContactSettings.CUR_POSTAL);
		curRegion = ContactActivator.getDefault().getDialogSettings().get(ContactSettings.CUR_REGION);
		curLatitude = ContactActivator.getDefault().getDialogSettings().get(ContactSettings.CUR_LATITUDE);
		curLongitude = ContactActivator.getDefault().getDialogSettings().get(ContactSettings.CUR_LONGITUDE);
	}

	public String getCurStreet() {
		return curStreet;
	}

	public String getCurLocality() {
		return curLocality;
	}

	public String getCurCountry() {
		return curCountry;
	}

	public String getCurPob() {
		return curPob;
	}

	public String getCurRegion() {
		return curRegion;
	}

	public String getCurPostal() {
		return curPostal;
	}

	public String getCurLongitude() {
		return curLongitude;
	}

	public String getCurLatitude() {
		return curLatitude;
	}
}
