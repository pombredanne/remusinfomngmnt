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
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.AbstractHyperlink;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
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
	private Text tx_BlogFeed;
	private Text tx_Homepage;
	private Button bt_EditEmail;
	private Text tx_Email;
	private final FormToolkit toolkit;
	private final Shell shell;
	private final InformationUnit informationUnit;
	private final EditGeneralPage editGeneralPage;
	private Hyperlink hl_Email;
	private AbstractHyperlink tl_Homepage;

	public ContactGeneralSection(final Composite body, final FormToolkit toolkit,
			final Shell shell, final InformationUnit informationUnit,
			final EditGeneralPage editGeneralPage) {

		this.toolkit = toolkit;
		this.shell = shell;
		this.informationUnit = informationUnit;
		this.editGeneralPage = editGeneralPage;

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

		createGroupPerson(compositeGeneral);
		createGroupPhoneNumbers(compositeGeneral);
		createGroupAddress(compositeGeneral);
		createGroupInternet(compositeGeneral);
		// createGrouAddressImage(compositeGeneral);
	}

	// private void createGrouAddressImage(Composite compositeGeneral) {
	// final Group group_Person = new Group(compositeGeneral, SWT.NONE);
	// final GridData gd_Person = new GridData();
	// gd_Person.grabExcessVerticalSpace = true;
	// gd_Person.verticalAlignment = GridData.FILL;
	// gd_Person.grabExcessHorizontalSpace = true;
	// gd_Person.horizontalAlignment = GridData.FILL;
	// group_Person.setLayoutData(gd_Person);
	// final GridLayout gl_PersonGroup = new GridLayout();
	// gl_PersonGroup.numColumns = 3;
	// group_Person.setLayout(gl_PersonGroup);
	//
	// this.lb_Image = toolkit.createLabel(group_Person, null, SWT.BORDER);
	// GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, false, true);
	// gd_text.verticalSpan = 4;
	// gd_text.verticalAlignment = GridData.FILL;
	// this.lb_Image.setLayoutData(gd_text);
	//		
	//		
	// //final static String GOOGLE_MAPS_API_KEY_REMUS =
	// "ABQIAAAA2Iee3-LfY8cYhYcH3i65vhS5WyVyGNFqhgudXfVE-5tAedqW1BS5qANHYqlX1vUb1nlffnYhHBmFCQ";
	//		
	// //gmaps.getImageFromGMaps();
	//		
	// //InformationUnit rawData =
	//			
	//			
	//			
	// //InformationUtil.getChildByType(informationUnit,
	// ContactActivator.NODE_NAME_RAWDATA_IMAGE);
	// // if (rawData != null && rawData.getBinaryValue() != null) {
	// // ByteArrayInputStream bais = new
	// ByteArrayInputStream(rawData.getBinaryValue());
	// // ImageData imageData = new ImageData(bais);
	// // Image image = new Image(null, imageData);
	// // this.lb_Image.setImage(image);
	// // }
	// }

	private void createGroupPerson(final Composite compositeGeneral) {

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

		this.lb_Image = this.toolkit.createLabel(group_Person, "double click me ...", SWT.BORDER);
		this.lb_Image.setSize(100, 200);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, false, true);
		gd_text.verticalSpan = 4;
		gd_text.verticalAlignment = GridData.FILL;
		this.lb_Image.setLayoutData(gd_text);

		this.bt_EditName = this.toolkit.createButton(group_Person, "Edit Name...", SWT.NONE);
		this.tx_EditName = this.toolkit.createText(group_Person, null, SWT.BORDER);
		this.tx_EditName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		this.tx_EditName.setEditable(false);
		this.tx_EditName.setEnabled(false);

		this.toolkit.createLabel(group_Person, "Role:");
		this.tx_Role = this.toolkit.createText(group_Person, null, SWT.BORDER);
		this.tx_Role.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_Person, "Organisation:");
		this.tx_Organisation = this.toolkit.createText(group_Person, null, SWT.BORDER);
		this.tx_Organisation.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		this.toolkit.createLabel(group_Person, "Formatted Name:");
		this.tx_FormattedName = this.toolkit.createText(group_Person, null, SWT.BORDER);
		this.tx_FormattedName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		this.tx_FormattedName.setEditable(false);
		this.tx_FormattedName.setEnabled(false);

		createListenerGroupPerson(compositeGeneral, this.toolkit, this.shell, this.informationUnit,
				this.editGeneralPage);
		createTextValueBindingsGroupPerson(this.informationUnit, this.editGeneralPage);

		InformationUnit rawData = InformationUtil.getChildByType(this.informationUnit,
				ContactActivator.NODE_NAME_RAWDATA_IMAGE);
		if (rawData != null && rawData.getBinaryValue() != null) {
			ByteArrayInputStream bais = new ByteArrayInputStream(rawData.getBinaryValue());
			ImageData imageData = new ImageData(bais);
			Image image = new Image(null, imageData);
			this.lb_Image.setImage(image);
		}
	}

	private void createTextValueBindingsGroupPerson(final InformationUnit informationUnit,
			final EditGeneralPage editGeneralPage) {

		TextBindingWidget createTextBindingWidget1 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_EditName, editGeneralPage);
		createTextBindingWidget1.bindModel(InformationUtil.getChildByType(informationUnit,
				ContactActivator.NODE_NAME_PERS_NAME_COMPLETE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget2 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Role, editGeneralPage);
		createTextBindingWidget2.bindModel(InformationUtil.getChildByType(informationUnit,
				ContactActivator.NODE_NAME_PERS_ROLE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget3 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Organisation, editGeneralPage);
		createTextBindingWidget3.bindModel(InformationUtil.getChildByType(informationUnit,
				ContactActivator.NODE_NAME_PERS_ORGANISATION),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		TextBindingWidget createTextBindingWidget4 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_FormattedName, editGeneralPage);
		createTextBindingWidget4.bindModel(InformationUtil.getChildByType(informationUnit,
				ContactActivator.NODE_NAME_PERS_NAME_FORMATTED),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
	}

	private void createListenerGroupPerson(final Composite compositeGeneral,
			final FormToolkit toolkit, final Shell shell, final InformationUnit informationUnit,
			final EditGeneralPage editGeneralPage) {
		this.lb_Image.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(final MouseEvent e) {

				Image image = ImageManipulation.selectImageFromDialog(shell, informationUnit,
						ContactActivator.NODE_NAME_RAWDATA_IMAGE,
						(AdapterFactoryEditingDomain) editGeneralPage.getEditingDomain(),
						ContactGeneralSection.this.lb_Image.getSize().x,
						ContactGeneralSection.this.lb_Image.getSize().y);
				if (image != null) {
					ContactGeneralSection.this.lb_Image.setImage(image);
				}
			}

			public void mouseDown(final MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseUp(final MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		this.bt_EditName.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				EditContactPersonDialog ecd = new EditContactPersonDialog(toolkit, shell,
						informationUnit, editGeneralPage);
				ecd.open();
			}
		});
	}

	private void createGroupPhoneNumbers(final Composite compositeGeneral) {

		final Group group_PhoneNumbers = new Group(compositeGeneral, SWT.NONE);
		final GridData gd_PhoneNumbers = new GridData();
		gd_PhoneNumbers.grabExcessVerticalSpace = true;
		gd_PhoneNumbers.verticalAlignment = GridData.FILL;
		gd_PhoneNumbers.grabExcessHorizontalSpace = true;
		gd_PhoneNumbers.horizontalAlignment = GridData.FILL;
		group_PhoneNumbers.setLayoutData(gd_PhoneNumbers);

		final GridLayout gl_PhoneNumbersGroup = new GridLayout();
		gl_PhoneNumbersGroup.numColumns = 1;
		group_PhoneNumbers.setLayout(gl_PhoneNumbersGroup);

		for (int i = 0; i < 3; i++) {
			new PhoneNumbersComposite(group_PhoneNumbers, SWT.NONE, this.toolkit, i,
					this.informationUnit, this.editGeneralPage);
		}
	}

	private void createGroupAddress(final Composite compositeGeneral) {
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

		this.combo_AddressChooser = new Combo(group_Address, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd_AddressChooser = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		this.combo_AddressChooser.setLayoutData(gd_AddressChooser);
		this.combo_AddressChooser.setItems(ContactSettings.DEFAULT_ITMES_COMBO_ADDRESS_CHOOSER);
		// cb_AddressChooser.setText(ContactActivator.getDefault().NODE_ADDRESS_CURRENT_SEL);

		this.tx_Address = this.toolkit.createText(group_Address, null, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL | SWT.H_SCROLL);
		GridData gd_Address = new GridData();
		gd_Address.grabExcessVerticalSpace = true;
		gd_Address.verticalAlignment = GridData.FILL;
		gd_Address.grabExcessHorizontalSpace = true;
		gd_Address.horizontalAlignment = GridData.FILL;
		gd_Address.heightHint = 90;
		this.tx_Address.setEditable(false);
		this.tx_Address.setLayoutData(gd_Address);

		this.bt_EditAddress = this.toolkit
				.createButton(group_Address, "Edit Address ...", SWT.NONE);
		GridData gd_EditAddress = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		this.bt_EditAddress.setLayoutData(gd_EditAddress);

		createListenerGroupAddress(compositeGeneral, this.toolkit, this.shell,
				this.informationUnit, this.editGeneralPage);

		if (!ContactActivator.getDefault().getDialogSettings().getBoolean(
				ContactSettings.AC_USER_SETTINGS)) {
			setContactProportiesFromActivatorToDefault();
			setContactProportiesFromActivatorToGenerationDialog();
		} else {
			setContactProportiesFromActivatorToGenerationDialog();
		}
	}

	private void setContactProportiesFromActivatorToGenerationDialog() {
		try {
			this.combo_AddressChooser.select(ContactActivator.getDefault().getDialogSettings()
					.getInt(ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}
	}

	private void setContactProportiesFromActivatorToDefault() {
		ContactActivator.getDefault().getDialogSettings().put(
				ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX, 0);
	}

	private void setContactProportiesFromGenerationDialogToActivator() {
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_USER_SETTINGS,
				true);
		ContactActivator.getDefault().getDialogSettings().put(
				ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX,
				this.combo_AddressChooser.getSelectionIndex());

	}

	private void createListenerGroupAddress(final Composite compositeGeneral,
			final FormToolkit toolkit, final Shell shell, final InformationUnit informationUnit,
			final EditGeneralPage editGeneralPage) {

		this.bt_EditAddress.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				setContactProportiesFromGenerationDialogToActivator();
				EditContactAddressDialog ecd = new EditContactAddressDialog(toolkit, shell,
						informationUnit, editGeneralPage);
				ecd.open();
				setTextForAddressBox(informationUnit);
				setContactProportiesFromActivatorToGenerationDialog();
			}
		});
		this.combo_AddressChooser.addModifyListener(new ModifyListener() {
			public void modifyText(final ModifyEvent e) {
				ContactActivator.getDefault().getDialogSettings().put(
						ContactSettings.AC_COMBO_DEFAULT_ADDRESS_INDEX,
						ContactGeneralSection.this.combo_AddressChooser.getSelectionIndex());
				setTextForAddressBox(informationUnit);
			}
		});
	}

	private void setTextForAddressBox(final InformationUnit informationUnit) {
		AddressComboChooser acc = new AddressComboChooser(this.combo_AddressChooser,
				ContactSettings.DEFAULT_ITMES_COMBO_ADDRESS_CHOOSER);

		String street = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurStreet())
					.getStringValue() != null) {
				street = InformationUtil.getChildByType(informationUnit, acc.getCurStreet())
						.getStringValue();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String postal = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurPostal())
					.getStringValue() != null) {
				postal = InformationUtil.getChildByType(informationUnit, acc.getCurPostal())
						.getStringValue();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String region = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurRegion())
					.getStringValue() != null) {
				region = InformationUtil.getChildByType(informationUnit, acc.getCurRegion())
						.getStringValue();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String locality = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurLocality())
					.getStringValue() != null) {
				locality = InformationUtil.getChildByType(informationUnit, acc.getCurLocality())
						.getStringValue();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String country = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurCountry())
					.getStringValue() != null) {
				country = InformationUtil.getChildByType(informationUnit, acc.getCurCountry())
						.getStringValue();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String pob = "";
		try {
			if (InformationUtil.getChildByType(informationUnit, acc.getCurPob()).getStringValue() != null) {
				pob = InformationUtil.getChildByType(informationUnit, acc.getCurPob())
						.getStringValue();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		this.tx_Address.setText(street + "\n" + postal + "\n" + locality + "\n" + region + "\n"
				+ country + "\n" + pob);
	}

	private void createGroupInternet(final Composite compositeGeneral) {
		final Group group_Internet = new Group(compositeGeneral, SWT.NONE);
		group_Internet.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridData gd_SpanHorizontal2 = new GridData(SWT.END, SWT.CENTER, false, false);

		final GridLayout gl_Internet = new GridLayout();
		gl_Internet.numColumns = 3;
		group_Internet.setLayout(gl_Internet);

		this.hl_Email = this.toolkit.createHyperlink(group_Internet, "E-Mail:", SWT.NONE);

		this.tx_Email = this.toolkit.createText(group_Internet, null, SWT.BORDER);
		this.tx_Email.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.tx_Email.setEditable(false);
		this.tx_Email.setEnabled(false);

		this.bt_EditEmail = this.toolkit.createButton(group_Internet, "Edit", SWT.NONE);
		this.bt_EditEmail.setLayoutData(gd_SpanHorizontal2);

		createSeparator(group_Internet, true, 3);

		this.tl_Homepage = this.toolkit.createHyperlink(group_Internet, "Homepage:", SWT.NONE);

		this.tx_Homepage = this.toolkit.createText(group_Internet, null, SWT.BORDER);
		this.tx_Homepage.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));

		this.toolkit.createLabel(group_Internet, "Blog Feed:");
		this.tx_BlogFeed = this.toolkit.createText(group_Internet, null, SWT.BORDER);
		this.tx_BlogFeed.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));

		this.toolkit.createLabel(group_Internet, "IM-Address:");

		new InstantMessagingComposite(group_Internet, SWT.NONE, this.toolkit, 1,
				this.informationUnit, this.editGeneralPage);

		createListener();
		createTextValueBindingsGroupInternet();

		setContactProportiesFromActivatorToGenerationDialog();
	}

	private void createTextValueBindingsGroupInternet() {
		TextBindingWidget createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Email, this.editGeneralPage);
		createTextBindingWidget.bindModel(InformationUtil.getChildByType(this.informationUnit,
				ContactActivator.NODE_MAIL_DEF),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		TextBindingWidget createTextBindingWidget1 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_Homepage, this.editGeneralPage);
		createTextBindingWidget1.bindModel(InformationUtil.getChildByType(this.informationUnit,
				ContactActivator.NODE_FRONTPAGE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		TextBindingWidget createTextBindingWidget2 = BindingWidgetFactory.createTextBindingWidget(
				this.tx_BlogFeed, this.editGeneralPage);
		createTextBindingWidget2.bindModel(InformationUtil.getChildByType(this.informationUnit,
				ContactActivator.NODE_BLOG_FEED),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
	}

	private void createListener() {
		this.bt_EditEmail.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				EditContactEmailDialog ecd = new EditContactEmailDialog(
						ContactGeneralSection.this.shell,
						ContactGeneralSection.this.informationUnit,
						ContactGeneralSection.this.editGeneralPage.getEditingDomain());
				ecd.open();
			}
		});
		this.hl_Email.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				try {
					Program.launch("mailto:" + ContactGeneralSection.this.tx_Email.getText());
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		this.tl_Homepage.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				try {
					Program.launch(ContactGeneralSection.this.tx_Homepage.getText());
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
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
