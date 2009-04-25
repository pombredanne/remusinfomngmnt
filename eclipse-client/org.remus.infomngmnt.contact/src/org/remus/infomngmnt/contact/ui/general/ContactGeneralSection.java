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
import java.io.ByteArrayInputStream;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.TextBindingWidget;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ContactSettings;
import org.remus.infomngmnt.contact.core.ImageManipulation;
import org.remus.infomngmnt.core.model.InformationUtil;

public class ContactGeneralSection {

	private Label lb_Image;
	private Button bt_EditName;
	private Text tx_EditName;
	private Text tx_Role;
	private Text tx_Organisation;
	private Text tx_FormattedName;
	
	private Button bt_EditAddress;
	private Combo combo_AddressChooser;
	private Text tx_Address;
	
	public ContactGeneralSection(Composite body, FormToolkit toolkit, Shell shell, InformationUnit informationUnit, EditGeneralPage editGeneralPage){		
		

		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("General");

		final Composite compositeGeneral = toolkit.createComposite(section_1, SWT.BORDER);
		final GridLayout gridLayoutGeneral = new GridLayout();
		gridLayoutGeneral.numColumns = 2;
		compositeGeneral.setLayout(gridLayoutGeneral);
		toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupPerson(compositeGeneral, toolkit, shell, informationUnit, editGeneralPage);
		createGroupPhoneNumbers(compositeGeneral, toolkit, informationUnit, editGeneralPage);		
		createGroupAddress(compositeGeneral, toolkit, shell, informationUnit, editGeneralPage);
		createGroupInternet(compositeGeneral, toolkit);
		createSeparator(compositeGeneral, true, 2);
		new Label(compositeGeneral, SWT.NONE);
		createGroupButtons(compositeGeneral, toolkit);
	}

	private void createGroupButtons(final Composite compositeGeneral, final FormToolkit toolkit) {
		final Composite composite_CreateDetailButtons = toolkit.createComposite(compositeGeneral,
				SWT.NONE);
		final GridLayout gl_CreateDetailButtons = new GridLayout();
		gl_CreateDetailButtons.numColumns = 5;
		composite_CreateDetailButtons.setLayoutData(new GridData(SWT.FILL, SWT.END, true, false));
		composite_CreateDetailButtons.setLayout(gl_CreateDetailButtons);

		final Label lb = new Label(composite_CreateDetailButtons, SWT.NONE);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		lb.setLayoutData(gd_text);
		final Button bt_Ok = toolkit.createButton(composite_CreateDetailButtons, "OK", SWT.NONE);
		final Button bt_Apply = toolkit.createButton(composite_CreateDetailButtons, "Apply",
				SWT.NONE);
		final Button bt_Cancel = toolkit.createButton(composite_CreateDetailButtons, "Cancel",
				SWT.NONE);
	}

	private void createGroupPerson(final Composite compositeGeneral, final FormToolkit toolkit,
			final Shell shell, final InformationUnit informationUnit, EditGeneralPage editGeneralPage) {

		final Group group_Person = new Group(compositeGeneral, SWT.NONE);
		final GridData gd_Person = new GridData();
		gd_Person.grabExcessVerticalSpace = true;
		gd_Person.verticalAlignment = GridData.FILL;
		gd_Person.grabExcessHorizontalSpace = true;
		gd_Person.horizontalAlignment = GridData.FILL;
		group_Person.setLayoutData(gd_Person);
		final GridLayout gl_PersonGroup = new GridLayout();
		gl_PersonGroup.numColumns = 3;
		group_Person.setLayout(gl_PersonGroup);

		this.lb_Image = toolkit.createLabel(group_Person, "double click me ...", SWT.BORDER);
		this.lb_Image.setSize(100, 200);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, false, true);
		gd_text.verticalSpan = 4;
		gd_text.verticalAlignment = GridData.FILL;
		this.lb_Image.setLayoutData(gd_text);

		this.bt_EditName = toolkit.createButton(group_Person, "Edit Name...", SWT.NONE);
		tx_EditName = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_EditName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		tx_EditName.setEditable(false);

		final Label lb_Role = toolkit.createLabel(group_Person, "Role:");
		tx_Role = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_Role.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Organisation = toolkit.createLabel(group_Person, "Organisation:");
		tx_Organisation = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_Organisation.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_FormattedName = toolkit.createLabel(group_Person, "Formatted Name:");
		tx_FormattedName = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_FormattedName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		tx_FormattedName.setEditable(false);
		tx_FormattedName.setEnabled(false);

		createListenerGroupPerson(compositeGeneral, toolkit, shell, informationUnit, editGeneralPage);
		createTextValueBindingsGroupPerson(informationUnit, editGeneralPage);
		
		InformationUnit rawData = InformationUtil.getChildByType(informationUnit,
				ContactActivator.NODE_NAME_RAWDATA_IMAGE);
		if (rawData != null && rawData.getBinaryValue() != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(rawData.getBinaryValue());
			ImageData imageData = new ImageData(bais);
			Image image = new Image(null, imageData);
			this.lb_Image.setImage(image);
		}
	}

	private void createTextValueBindingsGroupPerson(InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
		
		TextBindingWidget createTextBindingWidget1 = BindingWidgetFactory.createTextBindingWidget(tx_EditName, editGeneralPage);
		createTextBindingWidget1.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_NAME_PERS_NAME_COMPLETE), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		TextBindingWidget createTextBindingWidget2 = BindingWidgetFactory.createTextBindingWidget(tx_Role, editGeneralPage);
		createTextBindingWidget2.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_NAME_PERS_ROLE), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		TextBindingWidget createTextBindingWidget3 = BindingWidgetFactory.createTextBindingWidget(tx_Organisation, editGeneralPage);
		createTextBindingWidget3.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_NAME_PERS_ORGANISATION), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
//		if (tx_Organisation.getText().length() >=1 && ContactSettings.AC_COMBO_NAME_FORMATTED_INDEX.equals("ganisation")) {
//			createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(tx_FormattedName, editGeneralPage);
//			createTextBindingWidget.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_NAME_PERS_ORGANISATION), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
//		}else{
		TextBindingWidget createTextBindingWidget4 = BindingWidgetFactory.createTextBindingWidget(tx_FormattedName, editGeneralPage);
		createTextBindingWidget4.bindModel(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_NAME_PERS_NAME_FORMATTED), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
//		}
	}

	private void createListenerGroupPerson(final Composite compositeGeneral, final FormToolkit toolkit,
			final Shell shell, final InformationUnit informationUnit, final EditGeneralPage editGeneralPage) {
		this.lb_Image.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(final MouseEvent e) {
				
				Image image = ImageManipulation.selectImageFromDialog(
						shell, informationUnit, ContactActivator.NODE_NAME_RAWDATA_IMAGE,
						(AdapterFactoryEditingDomain) editGeneralPage.getEditingDomain() , ContactGeneralSection.this.lb_Image.getSize().x,
						ContactGeneralSection.this.lb_Image.getSize().y);
				if(image != null) ContactGeneralSection.this.lb_Image.setImage(image);
			}

			public void mouseDown(final MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseUp(final MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		bt_EditName.addSelectionListener(new SelectionAdapter(){

			public void widgetSelected(final SelectionEvent e) {
				EditContactPersonDialog ecd = new EditContactPersonDialog(compositeGeneral, toolkit, shell,	informationUnit, editGeneralPage);
				ecd.open();
				}			
		});
	}
	
	private void createGroupPhoneNumbers(Composite compositeGeneral, FormToolkit toolkit, InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
		
		final Group group_PhoneNumbers = new Group(compositeGeneral, SWT.NONE);
		group_PhoneNumbers.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
	
		final GridLayout gl_PhoneNumbersGroup = new GridLayout();
		gl_PhoneNumbersGroup.numColumns = 1;
		group_PhoneNumbers.setLayout(gl_PhoneNumbersGroup);

		for (int i = 0; i < 3; i++) {
			new PhoneNumbersComposite(group_PhoneNumbers,SWT.NONE, toolkit, i, informationUnit, editGeneralPage);
		}
	}

	private void createGroupAddress(final Composite compositeGeneral, final FormToolkit toolkit, Shell shell, InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
		final Group group_Address = new Group(compositeGeneral, SWT.NONE);
		final GridData gd_GroupAddress = new GridData();
		gd_GroupAddress.grabExcessVerticalSpace = true;
		gd_GroupAddress.verticalAlignment = GridData.FILL;
		gd_GroupAddress.grabExcessHorizontalSpace = true;
		gd_GroupAddress.horizontalAlignment = GridData.FILL;
		group_Address.setLayoutData(gd_GroupAddress);

		final GridLayout gl_Address = new GridLayout();
		gl_Address.numColumns = 1;
		group_Address.setLayout(gl_Address);

		combo_AddressChooser = new Combo(group_Address, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd_AddressChooser = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		combo_AddressChooser.setLayoutData(gd_AddressChooser);
		combo_AddressChooser.setItems(ContactSettings.DEFAULT_ITMES_COMBO_ADDRESS_CHOOSER);
		//cb_AddressChooser.setText(ContactActivator.getDefault().NODE_ADDRESS_CURRENT_SEL);

		tx_Address = toolkit.createText(group_Address, null, SWT.BORDER | SWT.MULTI |SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd_Address = new GridData();
		gd_Address.grabExcessVerticalSpace = true;
		gd_Address.verticalAlignment = GridData.FILL;
		gd_Address.grabExcessHorizontalSpace = true;
		gd_Address.horizontalAlignment = GridData.FILL;
		gd_Address.heightHint = 90;
		tx_Address.setEditable(false);
		tx_Address.setLayoutData(gd_Address);

		bt_EditAddress = toolkit.createButton(group_Address, "Edit Address ...",
				SWT.NONE);
		GridData gd_EditAddress = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		bt_EditAddress.setLayoutData(gd_EditAddress);
		
		//createTextValueBindings();
		
		createListenerGroupAddress(compositeGeneral, toolkit, shell, informationUnit, editGeneralPage);		
		
		if (!ContactActivator.getDefault().getDialogSettings().getBoolean(ContactSettings.AC_USER_SETTINGS)) {
			setContactProportiesFromActivatorToDefault();
			setContactProportiesFromActivatorToGenerationDialog();
		} else {
			setContactProportiesFromActivatorToGenerationDialog();
		}
		
		validateAddressGroupPage();

	}

	private void setContactProportiesFromActivatorToGenerationDialog() {
		try {
			combo_AddressChooser.select(ContactActivator.getDefault().getDialogSettings().getInt(ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}
	}
	private void setContactProportiesFromActivatorToDefault() {
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX, 0);
		//ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_COMBO_NAME_FORMATTED_INDEX, 0);
		//ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_USER_SETTINGS, false);		
	}
	private void setContactProportiesFromGenerationDialogToActivator() {
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_USER_SETTINGS, true);
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX, combo_AddressChooser.getSelectionIndex());

	}
	private void validateAddressGroupPage() {
		//setContactProportiesFromGenerationDialogToActivator();
		//cb_Combo.setSelection(InformationUtil.getChildByType(contact, ContactSettings.getd);
		
	}
	private void createListenerGroupAddress(final Composite compositeGeneral, final FormToolkit toolkit,
			final Shell shell, final InformationUnit informationUnit, final EditGeneralPage editGeneralPage) {

		bt_EditAddress.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(final SelectionEvent e) {
				setContactProportiesFromGenerationDialogToActivator();
				EditContactAddressDialog ecd = new EditContactAddressDialog(toolkit, shell, informationUnit, editGeneralPage);
				ecd.open();
				setTextForAddressBox(informationUnit);
				setContactProportiesFromActivatorToGenerationDialog();
			}
		});
		combo_AddressChooser.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX, combo_AddressChooser.getSelectionIndex());
				setTextForAddressBox(informationUnit);
				//setContactProportiesFromActivatorToGenerationDialog();
			}		
		});
	}
	private void setTextForAddressBox(InformationUnit informationUnit) {		
		AddressComboChooser acc = new AddressComboChooser(combo_AddressChooser, ContactSettings.DEFAULT_ITMES_COMBO_ADDRESS_CHOOSER);
		
		String street = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurStreet()).getStringValue() != null)
				street = InformationUtil.getChildByType(informationUnit, acc.getCurStreet()).getStringValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		String postal = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurPostal()).getStringValue() != null)
				postal = InformationUtil.getChildByType(informationUnit, acc.getCurPostal()).getStringValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		String region = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurRegion()).getStringValue() != null)
				region = InformationUtil.getChildByType(informationUnit, acc.getCurRegion()).getStringValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		String locality = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurLocality()).getStringValue() != null)
				locality = InformationUtil.getChildByType(informationUnit, acc.getCurLocality()).getStringValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		String country = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurCountry()).getStringValue() != null)
				country = InformationUtil.getChildByType(informationUnit, acc.getCurCountry()).getStringValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		String pob = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurPob()).getStringValue() != null)
				pob = InformationUtil.getChildByType(informationUnit, acc.getCurPob()).getStringValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		tx_Address.setText(
				street+"\n"+
				postal+"\n"+
				locality+"\n"+
				region+"\n"+
				country+"\n"+
				pob
				);
	}
	private void createGroupInternet(final Composite compositeGeneral, final FormToolkit toolkit) {
		final Group group_Internet = new Group(compositeGeneral, SWT.NONE);
		group_Internet.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridData gd_SpanHorizontal2 = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_SpanHorizontal2.horizontalSpan = 2;

		final GridLayout gl_Internet = new GridLayout();
		gl_Internet.numColumns = 2;
		group_Internet.setLayout(gl_Internet);

		final Label lb_Email = toolkit.createLabel(group_Internet, "E-Mail:");
		final Text tx_Email = toolkit.createText(group_Internet, null, SWT.BORDER);
		tx_Email.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final Button bt_EditEmail = toolkit.createButton(group_Internet, "Edit E-Mail-Address ...",
				SWT.NONE);
		bt_EditEmail.setLayoutData(gd_SpanHorizontal2);

		final Label lb_Frontpage = toolkit.createLabel(group_Internet, "Frontpage:");
		final Text tx_Frontpage = toolkit.createText(group_Internet, null, SWT.BORDER);
		tx_Frontpage.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final Label lb_BlogFeed = toolkit.createLabel(group_Internet, "Blog Feed:");
		final Text tx_BlogFeed = toolkit.createText(group_Internet, null, SWT.BORDER);
		tx_BlogFeed.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final Label lb_ImAddress = toolkit.createLabel(group_Internet, "IM-Address:");
		final Text tx_ImAddress = toolkit.createText(group_Internet, null, SWT.BORDER);
		tx_ImAddress.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final Button bt_EditImAddress = toolkit.createButton(group_Internet, "Edit IM-Address ...",
				SWT.NONE);
		bt_EditImAddress.setLayoutData(gd_SpanHorizontal2);
	}

	private void createSeparator(final Composite compositeGeneral, final boolean isHorizontal,
			final int span) {
		final Label lb_Separator = new Label(compositeGeneral, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		if (isHorizontal) {
			gd_text.horizontalSpan = span;
		} else {
			gd_text.verticalSpan = span;
		}

		lb_Separator.setLayoutData(gd_text);
	}
}
