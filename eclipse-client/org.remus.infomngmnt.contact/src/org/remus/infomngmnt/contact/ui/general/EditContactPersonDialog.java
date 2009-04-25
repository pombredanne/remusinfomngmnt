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
import org.eclipse.swt.widgets.Label;
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

public class EditContactPersonDialog extends TitleAreaDialog {

	private final InformationUnit contact;
	private Button bt_Ok;
	private Composite area;
	private final Composite body;
	private final FormToolkit toolkit;
	private AbstractBindingWidget createTextBindingWidget;
	private Text tx_FormattedName;
	private Text tx_TitleAfterName;
	private Text tx_LastName;
	private Text tx_AdditionalName;
	private Text tx_FirstName;
	private EditGeneralPage editGeneralPage;
	private Combo cb_Title;
	private Button ck_Automation;
	private Combo cb_FormattedName;
	private ArrayList<String> valuesFormatted;
	private String currentFormattedName = "";
	
	public EditContactPersonDialog(Composite body, FormToolkit toolkit, Shell parentShell, InformationUnit contact, EditGeneralPage editGeneralPage) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.contact = contact;
		this.body = body;
		this.toolkit = toolkit;
		this.editGeneralPage = editGeneralPage;
	}
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		this.bt_Ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.bt_Ok.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	@Override
	public boolean close() {
		setDialogUserSettingssetContactProportiesFromGenerationDialogToActivator();
		return super.close();
	}
	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group_Properties = new Group(this.area, SWT.NONE);
		group_Properties.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_ProportiesGroup = new GridLayout();
		gl_ProportiesGroup.numColumns = 3;
		group_Properties.setLayout(gl_ProportiesGroup);

		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		final Label lb_Title = toolkit.createLabel(group_Properties, "Title:");
		cb_Title = new Combo(group_Properties, SWT.DROP_DOWN | SWT.READ_ONLY);
		cb_Title.setLayoutData(gd_text);

		final Label lb_FirstName = toolkit.createLabel(group_Properties, "First Name");
		tx_FirstName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_FirstName.setLayoutData(gd_text);

		final Label lb_AdditionalName = toolkit.createLabel(group_Properties, "Additional Name");
		tx_AdditionalName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_AdditionalName.setLayoutData(gd_text);
		
		final Label lb_LastName = toolkit.createLabel(group_Properties, "Last Name");
		tx_LastName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_LastName.setLayoutData(gd_text);
		
		final Label lb_TitleAfterName = toolkit.createLabel(group_Properties, "Title After Name");
		tx_TitleAfterName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_TitleAfterName.setLayoutData(gd_text);
		
		final Label lb_FormattedName = toolkit.createLabel(group_Properties, "Formatted Name:");
		cb_FormattedName = new Combo(group_Properties, SWT.DROP_DOWN | SWT.READ_ONLY);
		cb_FormattedName.setItems(ContactSettings.DEFAULT_ITEMS_COMBO_NAME_FORMATTED);
		
		tx_FormattedName = toolkit.createText(group_Properties, null, SWT.BORDER);
		tx_FormattedName.setEnabled(false);
		tx_FormattedName.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		
		ck_Automation = toolkit.createButton(group_Properties, "namen automatisch auswerten", SWT.CHECK);
		ck_Automation.setSelection(true);
		ck_Automation.setEnabled(false);
		
		createTextValueBindings();
		addListener();
		
		if (!ContactActivator.getDefault().getDialogSettings()
				.getBoolean(ContactSettings.AC_USER_SETTINGS)) {
			setContactProportiesFromActivatorToDefault();
			setContactProportiesFromActivatorToGenerationDialog();
		} else {
			setContactProportiesFromActivatorToGenerationDialog();
		}

		validatePage();
		
		return this.area;
	}
	private void setContactProportiesFromActivatorToDefault() {
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_USER_SETTINGS, false);
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_COMBO_NAME_FORMATTED_INDEX, 0);
	}
	private void setContactProportiesFromActivatorToGenerationDialog() {

//		try {
//			cb_FormattedName.select(ContactActivator.getDefault().getDialogSettings().getInt(ContactSettings.AC_COMBO_NAME_FORMATTED_INDEX));
//		} catch (Exception e) {
//			// TODO: log4j
//			e.printStackTrace();
//		}
	}
	private void setDialogUserSettingssetContactProportiesFromGenerationDialogToActivator() {
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_COMBO_NAME_FORMATTED_INDEX, cb_FormattedName.getSelectionIndex());
		ContactActivator.getDefault().getDialogSettings().put(ContactSettings.AC_USER_SETTINGS, true);
	}
	private void addListener() {
		this.ck_Automation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				validatePage();
			}
		});
		
		tx_LastName.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();				
			}
		});
		tx_FirstName.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();				
			}			
		});
		tx_AdditionalName.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();				
			}			
		});
		tx_TitleAfterName.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validatePage();				
			}			
		});
		cb_FormattedName.addModifyListener(new ModifyListener(){
				public void modifyText(ModifyEvent e) {
					validatePage();
				}
		});
	}
	private void createTextValueBindings() {
		
		createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(tx_FirstName, editGeneralPage);
		createTextBindingWidget.bindModel(InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_NAME_FIRST), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(tx_FormattedName, editGeneralPage);
		createTextBindingWidget.bindModel(InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_NAME_FORMATTED), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);

		createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(tx_TitleAfterName, editGeneralPage);
		createTextBindingWidget.bindModel(InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_NAME_TITLE_AFTER), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);		

		createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(tx_AdditionalName, editGeneralPage);
		createTextBindingWidget.bindModel(InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_NAME_ADDITIONAL), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(tx_LastName, editGeneralPage);
		createTextBindingWidget.bindModel(InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_NAME_LAST), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
		ComboBindingWidget createComboBinding = BindingWidgetFactory.createComboBinding(cb_Title, editGeneralPage);
		List<String> values = new ArrayList<String>();
		values.add("");
		values.add("Dr.");
		values.add("Prof.");
		values.add("Female");
		values.add("Miss");
		values.add("Male");
		createComboBinding.setLabelProvider(new LabelProvider());
		createComboBinding.setInput(values);
		createComboBinding.bindModel(InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_NAME_TITLE), InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		
	}
	private void validatePage() {
		
		String completeName = cb_Title.getText()+" "+tx_FirstName.getText()+" "+tx_AdditionalName.getText()+" "+tx_LastName.getText()+" "+tx_TitleAfterName.getText();
		if (cb_FormattedName.getSelectionIndex() == 0) {
			currentFormattedName = tx_FirstName.getText()+" "+tx_LastName.getText();
		}else if (cb_FormattedName.getSelectionIndex() == 1) {
			currentFormattedName = completeName;
		}else if (cb_FormattedName.getSelectionIndex() == 2) {
			currentFormattedName = tx_LastName.getText()+" , "+tx_FirstName.getText();
		}else if (cb_FormattedName.getSelectionIndex() == 3) {
			try {
				String str = InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_ORGANISATION).getStringValue();
				currentFormattedName = str;				
			} catch (Exception e) {
				// TODO: handle exception
				currentFormattedName = "";
			}
		}
		
		InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_NAME_COMPLETE).setStringValue(completeName);
		InformationUtil.getChildByType(contact, ContactActivator.NODE_NAME_PERS_NAME_FORMATTED).setStringValue(currentFormattedName);
		tx_FormattedName.setText(currentFormattedName);
		try {
			if (bt_Ok != null){
				if(tx_FirstName.getText().length() >= 1 || tx_LastName.getText().length() >= 1)
					bt_Ok.setEnabled(true);
				else bt_Ok.setEnabled(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
