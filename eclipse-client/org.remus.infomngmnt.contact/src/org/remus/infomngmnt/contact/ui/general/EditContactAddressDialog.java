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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.databinding.AbstractBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.ComboBindingWidget;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ContactSettings;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.geodata.google.GMapsApi;

public class EditContactAddressDialog extends TitleAreaDialog {

	private InformationUnit contact;
	private EditGeneralPage editGeneralPage;
	private FormToolkit toolkit;
	private Button bt_Ok;
	private Composite area;
	private Combo combo_Address;
	private Text tx_Street;
	private Text tx_Pob;
	private Text tx_Locality;
	private Text tx_Region;
	private Text tx_Postal;
	private Combo combo_Country;
	private Text tx_Latitude;
	private Text tx_Longitude;
	private Button bt_GetCoords;
	private Group group_Geo;

	public EditContactAddressDialog(FormToolkit toolkit, Shell parentShell, InformationUnit contact, EditGeneralPage editGeneralPage) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.contact = contact;
		this.toolkit = toolkit;
		this.editGeneralPage = editGeneralPage;
	}
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		this.bt_Ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.bt_Ok.setEnabled(true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	@Override
	public boolean close() {
		setUserSettings();
		return super.close();
	}
	@Override
	protected void okPressed(){
		super.okPressed();
	}
	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group_Properties = new Group(this.area, SWT.NONE);
		group_Properties.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_ProportiesGroup = new GridLayout();
		gl_ProportiesGroup.numColumns = 2;
		group_Properties.setLayout(gl_ProportiesGroup);

		GridData gd_text_span_2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text_span_2.horizontalSpan = 2;
		GridData gd_text_fill_horizontal = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		
		combo_Address = new Combo(group_Properties, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_Address.setLayoutData(gd_text_span_2);
		combo_Address.setItems(ContactSettings.DEFAULT_ITMES_COMBO_ADDRESS_CHOOSER);
		
		toolkit.createLabel(group_Properties, "Street:");		
		tx_Street = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_Street.setLayoutData(gd_text_fill_horizontal);
		
		toolkit.createLabel(group_Properties, "Post Office Box:");
		tx_Pob = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_Pob.setLayoutData(gd_text_fill_horizontal);
		
		toolkit.createLabel(group_Properties, "Locality:");
		tx_Locality = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_Locality.setLayoutData(gd_text_fill_horizontal);
		
		toolkit.createLabel(group_Properties, "Region:");
		tx_Region = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_Region.setLayoutData(gd_text_fill_horizontal);
		
		toolkit.createLabel(group_Properties, "Postal Code:");
		tx_Postal = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_Postal.setLayoutData(gd_text_fill_horizontal);
		
		toolkit.createLabel(group_Properties, "Country:");
		combo_Country = new Combo(group_Properties, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo_Country.setLayoutData(gd_text_span_2);
		
		createGeographicalButtons(group_Properties, gd_text_fill_horizontal);
		
		createTextValueBindings();
		createListener();
		
		setContactProportiesFromActivatorToGenerationDialog();
		
		validatePage();
		
		return this.area;
	}

	private void createGeographicalButtons(Group group_Properties, GridData gd_text_fill_horizontal) {
		
		group_Geo = new Group(group_Properties, SWT.BORDER);
		group_Geo.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));

		final GridLayout gl_GeoGroup = new GridLayout();
		GridData gd_GeoGroup_span_2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_GeoGroup_span_2.horizontalSpan = 2;
		gl_GeoGroup.numColumns = 3;
		group_Geo.setLayoutData(gd_GeoGroup_span_2);
		group_Geo.setLayout(gl_GeoGroup);
		
		toolkit.createLabel(group_Geo, "Latitude:");
		tx_Latitude = toolkit.createText(group_Geo, null, SWT.BORDER);
		tx_Latitude.setLayoutData(gd_text_fill_horizontal);
		
		bt_GetCoords = toolkit.createButton(group_Geo, "Get Coords", SWT.NONE);
		
		toolkit.createLabel(group_Geo, "Longitude:");
		tx_Longitude = toolkit.createText(group_Geo, null, SWT.BORDER);
		tx_Longitude.setLayoutData(gd_text_fill_horizontal);
	}

	private void createListener() {
		combo_Address.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				createTextValueBindings();
				validatePage();
			}
		});
		bt_GetCoords.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e) {
				//GMapsApi gma = new GMapsApi();
				try {
					String curKey = GMapsApi.getApiKey();
					Point2D p2d = GMapsApi.getGeoCoordFromGMaps(curKey, "borstrasse+radebeul+01445");
					tx_Latitude.setText(String.valueOf(p2d.getX()));
					tx_Longitude.setText(String.valueOf(p2d.getY()));
					
				} catch (Exception e2) {
					MessageBox msgb = new MessageBox(new Shell(),SWT.NONE);
					msgb.setMessage("Please go to Extras -> Preferences -> GeoData -> Google Maps API Key");
					msgb.setText("False Key Found");
					msgb.open();
				}
			}
		});
		tx_Street.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		tx_Locality.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		tx_Pob.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		tx_Postal.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		tx_Region.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
	}
	private void validatePage() {
		if (tx_Street.getText().length() > 1 && (tx_Postal.getText().length() > 1 || tx_Region.getText().length() > 1)) {
			setGroupGeo(true);
		}
		else setGroupGeo(false);		
	}
	private void setGroupGeo(boolean b) {
		group_Geo.setEnabled(b);
		tx_Latitude.setEditable(false);
		tx_Latitude.setEnabled(false);
		tx_Longitude.setEditable(false);
		tx_Longitude.setEnabled(false);
		bt_GetCoords.setEnabled(b);		
	}
	private void setContactProportiesFromActivatorToGenerationDialog() {
		try {
			combo_Address.select(ContactActivator.getDefault().getDialogSettings().getInt(ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}
	}
	private void setUserSettings() {
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX, combo_Address.getSelectionIndex());	
	}
	private void createTextValueBindings() {

		AddressComboChooser acc = new AddressComboChooser(combo_Address, ContactSettings.DEFAULT_ITMES_COMBO_ADDRESS_CHOOSER);
		
		try {
			final AbstractBindingWidget createTextBindingWidget0 = BindingWidgetFactory.createTextBindingWidget(tx_Street, editGeneralPage);
			createTextBindingWidget0.bindModel(InformationUtil.getChildByType(contact, acc.getCurStreet()), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			final ComboBindingWidget createComboBinding = BindingWidgetFactory.createComboBinding(combo_Country, editGeneralPage);			
			String[] s = ContactSettings.DEFAULT_ITMES_COMBO_COUNTRY_CHOOSER;
			List<String> values = new ArrayList<String>();
			
			for (int i = 0; i < s.length; i++) values.add(s[i]);

			createComboBinding.setLabelProvider(new LabelProvider());
			createComboBinding.setInput(values);
			createComboBinding.bindModel(InformationUtil.getChildByType(contact, acc.getCurCountry()), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			final AbstractBindingWidget createTextBindingWidget2 = BindingWidgetFactory.createTextBindingWidget(tx_Locality, editGeneralPage);
			createTextBindingWidget2.bindModel(InformationUtil.getChildByType(contact, acc.getCurLocality()), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			final AbstractBindingWidget createTextBindingWidget3 = BindingWidgetFactory.createTextBindingWidget(tx_Pob, editGeneralPage);
			createTextBindingWidget3.bindModel(InformationUtil.getChildByType(contact, acc.getCurPob()), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);					
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			final AbstractBindingWidget createTextBindingWidget4 = BindingWidgetFactory.createTextBindingWidget(tx_Postal, editGeneralPage);
			createTextBindingWidget4.bindModel(InformationUtil.getChildByType(contact, acc.getCurPostal()), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			final AbstractBindingWidget createTextBindingWidget5 = BindingWidgetFactory.createTextBindingWidget(tx_Region, editGeneralPage);
			createTextBindingWidget5.bindModel(InformationUtil.getChildByType(contact, acc.getCurRegion()), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);			
		} catch (Exception e) {
			// TODO: handle exception
		}	
		try {
			final AbstractBindingWidget createTextBindingWidget6 = BindingWidgetFactory.createTextBindingWidget(tx_Latitude, editGeneralPage);
			createTextBindingWidget6.bindModel(InformationUtil.getChildByType(contact, acc.getCurLatitude()), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			final AbstractBindingWidget createTextBindingWidget7 = BindingWidgetFactory.createTextBindingWidget(tx_Longitude, editGeneralPage);
			createTextBindingWidget7.bindModel(InformationUtil.getChildByType(contact, acc.getCurLongitude()), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
