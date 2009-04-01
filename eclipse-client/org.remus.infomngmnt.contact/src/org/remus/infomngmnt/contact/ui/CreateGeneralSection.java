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
package org.remus.infomngmnt.contact.ui;

 /**
  * @author Jan Hartwig <jhartwig@feb-radebeul.de>
  * 
  */
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

public class CreateGeneralSection {

	public CreateGeneralSection(Composite body, FormToolkit toolkit) {
		final Section section_1 = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.TWISTIE | ExpandableComposite.EXPANDED);
		section_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		section_1.setText("General");
		
		final Composite compositeGeneral = toolkit.createComposite(section_1, SWT.NONE);
		final GridLayout gridLayoutAdditional = new GridLayout();
		gridLayoutAdditional.numColumns = 2;
		compositeGeneral.setLayout(gridLayoutAdditional);
		toolkit.paintBordersFor(compositeGeneral);
		section_1.setClient(compositeGeneral);

		createGroupPerson(compositeGeneral, toolkit);
		createGroupPhoneNumbers(compositeGeneral, toolkit);
		createGroupAddress(compositeGeneral, toolkit);
		createGroupInternet(compositeGeneral, toolkit);
	}
private void createGroupPerson(Composite compositeGeneral, FormToolkit toolkit) {
		
		final Group group_Person = new Group(compositeGeneral, SWT.NONE);
	    final GridData gd_Person= new GridData();
	    gd_Person.grabExcessVerticalSpace = true;
	    gd_Person.verticalAlignment = GridData.FILL;
	    gd_Person.grabExcessHorizontalSpace = true;
	    gd_Person.horizontalAlignment = GridData.FILL;
	    group_Person.setLayoutData(gd_Person);
		final GridLayout gl_PersonGroup = new GridLayout();
		gl_PersonGroup.numColumns = 2;
		group_Person.setLayout(gl_PersonGroup);
		
		final Button bt_EditName = toolkit.createButton(group_Person, "Edit Name...", SWT.NONE);
		String tx_EditNameValue = null;
		final Text tx_EditName = toolkit.createText(group_Person, tx_EditNameValue, SWT.BORDER);
		tx_EditName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Role = toolkit.createLabel(group_Person, "Role:");
		final Text tx_Role = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_Role.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_Organisation = toolkit.createLabel(group_Person, "Organisation:");
		final Text tx_Organisation = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_Organisation.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		final Label lb_FormattedName = toolkit.createLabel(group_Person, "Formatted Name:");
		final Text tx_FormattedName = toolkit.createText(group_Person, null, SWT.BORDER);
		tx_FormattedName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		tx_FormattedName.setEditable(false);
		tx_FormattedName.setEnabled(false);
	}
	private void createGroupPhoneNumbers(Composite compositeGeneral, FormToolkit toolkit) {
			
		final Group group_PhoneNumbers = new Group(compositeGeneral, SWT.NONE);
		group_PhoneNumbers.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, true));
	
		final GridLayout gl_PhoneNumbersGroup = new GridLayout();
		gl_PhoneNumbersGroup.numColumns = 1;
		group_PhoneNumbers.setLayout(gl_PhoneNumbersGroup);
		
		
		final Composite composite_Numbers = toolkit.createComposite(group_PhoneNumbers, SWT.BORDER);
		composite_Numbers.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		final GridLayout gl_Numbers = new GridLayout();
		gl_Numbers.numColumns = 3;
		composite_Numbers.setLayout(gl_Numbers);
		
		String[] comboValue = new String[]{"Home", "Work", "Messanger", "Voice", "Fax", "Mobile", "Video", "Mailbox","Modem", "Car","ISDN","PCS","Pager","Others..."} ;
		
		comboAndText(composite_Numbers, comboValue, 4);


		final Composite composite_CreateDeleteButtons = toolkit.createComposite(group_PhoneNumbers, SWT.NONE);
		final GridLayout gl_CreateDeleteButtons = new GridLayout();
		gl_CreateDeleteButtons.numColumns = 4;
		composite_CreateDeleteButtons.setLayoutData( new GridData(SWT.FILL, SWT.END, true, false));
		composite_CreateDeleteButtons.setLayout(gl_CreateDeleteButtons);
		
		final Label lb = new Label(composite_CreateDeleteButtons, SWT.NONE);
		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		lb.setLayoutData(gd_text);
		final Button bt_AddComboAndText = toolkit.createButton(composite_CreateDeleteButtons, "Add", SWT.NONE);
		final Button bt_DeleteComboAndText = toolkit.createButton(composite_CreateDeleteButtons, "Delete", SWT.NONE);
			
	}

	private void comboAndText(Composite composite_Numbers, String[] comboValue, int quantity) {
		for (int i = 0; i < quantity; i++) {
			final Combo cb = new Combo(composite_Numbers, SWT.DROP_DOWN | SWT.READ_ONLY);
			cb.setItems(comboValue);
			cb.setText(comboValue[i]);
			final Text tx = new Text(composite_Numbers, SWT.BORDER);
			GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
			gd_text.horizontalSpan = 2;
			tx.setLayoutData(gd_text);
		}
	}
	private void createGroupAddress(Composite compositeGeneral, FormToolkit toolkit) {
		final Group group_Address = new Group(compositeGeneral, SWT.NONE);
		final GridData gd_GroupAddress= new GridData();
		gd_GroupAddress.grabExcessVerticalSpace = true;
	    gd_GroupAddress.verticalAlignment = GridData.FILL;
	    gd_GroupAddress.grabExcessHorizontalSpace = true;
	    gd_GroupAddress.horizontalAlignment = GridData.FILL;
	    group_Address.setLayoutData(gd_GroupAddress);
		
		final GridLayout gl_Address = new GridLayout();
		gl_Address.numColumns = 1;
		group_Address.setLayout(gl_Address);

		String[] comboValue = new String[]{"Home", "Work"};
		final Combo cb_AddressChooser = new Combo(group_Address, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData gd_AddressChooser = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		cb_AddressChooser.setLayoutData(gd_AddressChooser);
		cb_AddressChooser.setItems(comboValue);
		cb_AddressChooser.setText(comboValue[0]);
		
		final StyledText stx_Address = new StyledText(group_Address, SWT.BORDER);
		GridData gd_Address = new GridData();		
		gd_Address.grabExcessVerticalSpace = true;
		gd_Address.verticalAlignment = GridData.FILL;
		gd_Address.grabExcessHorizontalSpace = true;
		gd_Address.horizontalAlignment = GridData.FILL; 
		gd_Address.heightHint = 90;
		stx_Address.setLayoutData(gd_Address);
		
		final Button bt_EditAddress = toolkit.createButton(group_Address, "Edit Address ...", SWT.NONE);
		GridData gd_EditAddress = new GridData(SWT.FILL, SWT.BEGINNING, true, true);
		bt_EditAddress.setLayoutData(gd_EditAddress);
		
	}
	private void createGroupInternet(Composite compositeGeneral, FormToolkit toolkit) {
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
		
		final Button bt_EditEmail = toolkit.createButton(group_Internet, "Edit E-Mail-Address ...", SWT.NONE);
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
		
		final Button bt_EditImAddress = toolkit.createButton(group_Internet, "Edit IM-Address ...", SWT.NONE);
		bt_EditImAddress.setLayoutData(gd_SpanHorizontal2);
	}
}
