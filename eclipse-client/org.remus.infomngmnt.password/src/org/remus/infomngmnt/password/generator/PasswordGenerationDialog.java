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
package org.remus.infomngmnt.password.generator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.password.PasswordPlugin;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
public class PasswordGenerationDialog extends TitleAreaDialog {

	private final InformationUnit password;

	private Composite area;
	private Group group_Passwords;
	private Text tx_additionalCharacters;
	private Combo comboDefaultPasswordLength;
	private Label lb_PasswordLength;
	private TableViewer tv_GeneratedPasswords;
	private Spinner sp_PasswordLength;
	private Button checkWide;
	private Button checkSmall;
	private Button checkNumber;
	private Button checkAdditional;
	private Button radioDefaultPassword;
	private Button radioUserDefinedPassword;
	private Button bt_GeneratePasswords;
	private Button bt_SaveProporties;

	private String selectedPassword;
	private int currentPasswordLength;
	private List<String> generatedPasswords;

	public PasswordGenerationDialog(final Shell parentShell, final InformationUnit password,
			final FormToolkit toolkit) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.password = password;
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		this.bt_SaveProporties = createButton(parent, IDialogConstants.OK_ID,
				"Apply Selected Password", true);
		this.bt_SaveProporties.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed() {
		PasswordGenerationDialog.this.password
				.setStringValue(PasswordGenerationDialog.this.selectedPassword);
		setPasswortProportiesFromPasswortGenerationDialogToActivator();
		super.okPressed();
	}

	@Override
	public boolean close() {
		setPasswortProportiesFromPasswortGenerationDialogToActivator();
		return super.close();
	}

	@Override
	protected void cancelPressed() {
		setPasswortProportiesFromPasswortGenerationDialogToActivator();
		super.cancelPressed();
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Group Create Properties
		setGroupCreateProportiesUI();
		setCreateProportiesButtonListener();

		// Group Generated Passwords
		setGroupGeneratePasswordsUI();

		if (!PasswordPlugin.getDefault().getDialogSettings()
				.getBoolean(PGSettings.AC_USER_SETTINGS)) {
			setPasswortProportiesFromActivatorToDefault();
			setPasswortProportiesFromActivatorToPasswordGenerationDialog();
		} else {
			setPasswortProportiesFromActivatorToPasswordGenerationDialog();
		}

		return this.area;
	}

	private void setGroupGeneratePasswordsUI() {

		this.group_Passwords = new Group(this.area, SWT.NONE);
		this.group_Passwords.setText("Generated Passwords");
		this.group_Passwords.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_PasswordGroup = new GridLayout();
		gl_PasswordGroup.numColumns = 1;
		this.group_Passwords.setLayout(gl_PasswordGroup);
		// this.group_Passwords.setEnabled(false);

		this.tv_GeneratedPasswords = new TableViewer(this.group_Passwords, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		this.tv_GeneratedPasswords.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.tv_GeneratedPasswords.setLabelProvider(new LabelProvider());
		// this.tv_GeneratedPasswords.getTable().setLinesVisible(false);
		// this.tv_GeneratedPasswords.
		this.tv_GeneratedPasswords.getTable().setLayoutData(
				new GridData(SWT.FILL, SWT.FILL, true, true));

		List<String> l = new ArrayList<String>(PGSettings.QUANTITY_PASSWORDS);
		for (int i = 1; i < PGSettings.QUANTITY_PASSWORDS; i++) {
			l.add("");
		}
		this.tv_GeneratedPasswords.setInput(l);
		this.tv_GeneratedPasswords.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(final SelectionChangedEvent event) {
				int i = PasswordGenerationDialog.this.tv_GeneratedPasswords.getTable()
						.getSelectionIndex();
				try {
					PasswordGenerationDialog.this.selectedPassword = PasswordGenerationDialog.this.generatedPasswords
							.get(i);
				} catch (Exception e) {
					// TODO: handle exception
				}
				PasswordGenerationDialog.this.bt_SaveProporties.setEnabled(true);
			}
		});
	}

	private void setGroupCreateProportiesUI() {

		final Group group_Properties = new Group(this.area, SWT.NONE);
		group_Properties.setText("Properties");
		group_Properties.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_ProportiesGroup = new GridLayout();
		gl_ProportiesGroup.numColumns = 2;
		group_Properties.setLayout(gl_ProportiesGroup);

		this.radioDefaultPassword = new Button(group_Properties, SWT.RADIO);
		this.radioDefaultPassword.setText("Default Length");

		this.comboDefaultPasswordLength = new Combo(group_Properties, SWT.DROP_DOWN | SWT.READ_ONLY);
		this.comboDefaultPasswordLength.setItems(PGSettings.ITEMS_DEFAULT_PASSWORD_LENGTH);

		this.radioUserDefinedPassword = new Button(group_Properties, SWT.RADIO);
		this.radioUserDefinedPassword.setText("User Defind");
		this.radioUserDefinedPassword.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, false,
				false));

		Composite cs_UserDefined = new Composite(group_Properties, SWT.NONE);
		final GridLayout gl_UserDefined = new GridLayout();
		gl_UserDefined.numColumns = 2;
		gl_UserDefined.marginHeight = 0;
		gl_UserDefined.marginWidth = 0;
		cs_UserDefined.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));
		cs_UserDefined.setLayout(gl_UserDefined);

		Composite cs_UserDefinedCheck = new Composite(cs_UserDefined, SWT.NONE);
		final GridLayout gl_UserDefinedCheck = new GridLayout();
		gl_UserDefinedCheck.numColumns = 3;
		GridData gd_UserDefinedCheck = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_UserDefinedCheck.horizontalSpan = 2;
		gl_UserDefinedCheck.marginHeight = 0;
		gl_UserDefinedCheck.marginWidth = 0;
		cs_UserDefinedCheck.setLayoutData(gd_UserDefinedCheck);
		cs_UserDefinedCheck.setLayout(gl_UserDefinedCheck);

		this.checkWide = new Button(cs_UserDefinedCheck, SWT.CHECK);
		this.checkWide.setText("A-Z");
		this.checkSmall = new Button(cs_UserDefinedCheck, SWT.CHECK);
		this.checkSmall.setText("a-z");
		this.checkNumber = new Button(cs_UserDefinedCheck, SWT.CHECK);
		this.checkNumber.setText("0-9");

		this.checkAdditional = new Button(cs_UserDefined, SWT.CHECK);
		this.checkAdditional.setText("Additonal Characters");

		this.tx_additionalCharacters = new Text(cs_UserDefined, SWT.NONE);
		// this.tx_additionalCharacters.setEnabled(false);
		this.tx_additionalCharacters.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

		this.lb_PasswordLength = new Label(cs_UserDefined, SWT.NONE);
		this.lb_PasswordLength.setText("Quantity Characters");
		this.sp_PasswordLength = new Spinner(cs_UserDefined, SWT.NONE);

		GridData gd_PasswordLength = new GridData(SWT.FILL, SWT.TOP, true, true);
		gd_PasswordLength.widthHint = 30;
		this.sp_PasswordLength.setLayoutData(gd_PasswordLength);
		// this.sp_PasswordLength.setSelection(8);
		this.sp_PasswordLength.setMinimum(1);
		// this.sp_PasswordLength.setEnabled(false);

		setAdditionalCheckable(false);

		new Label(group_Properties, SWT.NONE);
		this.bt_GeneratePasswords = new Button(group_Properties, SWT.NONE);
		this.bt_GeneratePasswords.setText("Generate");
		this.bt_GeneratePasswords.setLayoutData(new GridData(SWT.END, SWT.TOP, false, false));
	}

	private void setCreateProportiesButtonListener() {
		this.bt_GeneratePasswords.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				PasswordGenerationDialog.this.generatedPasswords = generatePasswords();
				PasswordGenerationDialog.this.tv_GeneratedPasswords
						.setInput(PasswordGenerationDialog.this.generatedPasswords);
				PasswordGenerationDialog.this.group_Passwords.setEnabled(true);
				PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(false);
			}

		});

		this.radioUserDefinedPassword.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(final SelectionEvent e) {
			}

			public void widgetSelected(final SelectionEvent e) {
				setAdditionalCheckable(true);
				PasswordGenerationDialog.this.comboDefaultPasswordLength.setEnabled(false);
				PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(false);
			}
		});
		this.radioDefaultPassword.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(final SelectionEvent e) {
				// Auto-generated method stub
			}

			public void widgetSelected(final SelectionEvent e) {
				// PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_USER_SETTINGS,
				// false);
				setAdditionalCheckable(false);
				PasswordGenerationDialog.this.comboDefaultPasswordLength.setEnabled(true);
				PasswordGenerationDialog.this.tx_additionalCharacters.setEnabled(false);
				PasswordGenerationDialog.this.sp_PasswordLength.setEnabled(false);
				PasswordGenerationDialog.this.lb_PasswordLength.setEnabled(false);
				PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(true);
			}
		});
		this.checkAdditional.addListener(SWT.Selection, new Listener() {
			boolean selection;

			public void handleEvent(final Event event) {
				if (PasswordGenerationDialog.this.checkAdditional.getSelection()) {
					this.selection = ((Button) event.widget).getSelection();
					PasswordGenerationDialog.this.tx_additionalCharacters
							.setEnabled(this.selection);

				} else {
					PasswordGenerationDialog.this.tx_additionalCharacters.setEnabled(false);
				}
			}
		});
		this.checkAdditional.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(final SelectionEvent e) {

				if (PasswordGenerationDialog.this.checkNumber.getSelection()
						|| PasswordGenerationDialog.this.checkSmall.getSelection()
						|| PasswordGenerationDialog.this.checkWide.getSelection()
						|| PasswordGenerationDialog.this.checkAdditional.getSelection()) {

					PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(true);

				} else if (!PasswordGenerationDialog.this.checkNumber.getSelection()
						&& !PasswordGenerationDialog.this.checkSmall.getSelection()
						&& !PasswordGenerationDialog.this.checkWide.getSelection()) {

					PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(false);
				}
			}
		});
		this.checkWide.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(final SelectionEvent e) {
				if (PasswordGenerationDialog.this.checkWide.getSelection()
						|| PasswordGenerationDialog.this.checkSmall.getSelection()
						|| PasswordGenerationDialog.this.checkNumber.getSelection()
						|| PasswordGenerationDialog.this.checkAdditional.getSelection()) {

					PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(true);

				} else if (!PasswordGenerationDialog.this.checkSmall.getSelection()
						&& !PasswordGenerationDialog.this.checkNumber.getSelection()
						&& !PasswordGenerationDialog.this.checkAdditional.getSelection()) {

					PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(false);
				}
			}
		});
		this.checkNumber.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(final SelectionEvent e) {
				if (PasswordGenerationDialog.this.checkNumber.getSelection()
						|| PasswordGenerationDialog.this.checkSmall.getSelection()
						|| PasswordGenerationDialog.this.checkWide.getSelection()
						|| PasswordGenerationDialog.this.checkAdditional.getSelection()) {

					PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(true);

				} else if (!PasswordGenerationDialog.this.checkSmall.getSelection()
						&& !PasswordGenerationDialog.this.checkWide.getSelection()
						&& !PasswordGenerationDialog.this.checkAdditional.getSelection()) {

					PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(false);
				}
			}
		});
		this.checkSmall.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(final SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			public void widgetSelected(final SelectionEvent e) {
				if (PasswordGenerationDialog.this.checkSmall.getSelection()
						|| PasswordGenerationDialog.this.checkNumber.getSelection()
						|| PasswordGenerationDialog.this.checkWide.getSelection()
						|| PasswordGenerationDialog.this.checkAdditional.getSelection()) {

					PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(true);

				} else if (!PasswordGenerationDialog.this.checkNumber.getSelection()
						&& !PasswordGenerationDialog.this.checkWide.getSelection()
						&& !PasswordGenerationDialog.this.checkAdditional.getSelection()) {

					PasswordGenerationDialog.this.bt_GeneratePasswords.setEnabled(false);
				}
			}
		});
	}

	protected List<String> generatePasswords() {

		String currentPasswordString = "";

		// default password
		if (this.radioDefaultPassword.getSelection()) {

			currentPasswordString += this.tx_additionalCharacters.getText()
					+ PGSettings.CHARACTERS_WIDE + PGSettings.CHARACTERS_SMALL
					+ PGSettings.CHARACTERS_NUMBERS;

		}
		// user generated password
		else {
			if (this.checkWide.getSelection()) {
				currentPasswordString += PGSettings.CHARACTERS_WIDE;
			}
			if (this.checkSmall.getSelection()) {
				currentPasswordString += PGSettings.CHARACTERS_SMALL;
			}
			if (this.checkNumber.getSelection()) {
				currentPasswordString += PGSettings.CHARACTERS_NUMBERS;
			}
			if (this.checkAdditional.getSelection()) {
				currentPasswordString += this.tx_additionalCharacters.getText();
			}
		}

		char[] chars = currentPasswordString.toCharArray();

		int currentPasswordLength = this.radioUserDefinedPassword.getSelection() ? Integer
				.valueOf(this.sp_PasswordLength.getText()) : Integer
				.valueOf(this.comboDefaultPasswordLength.getText());

		List<String> result = new ArrayList<String>();
		for (int j = 0; j < PGSettings.QUANTITY_PASSWORDS; j++) {
			String s = new String();

			for (int i = 0; i < currentPasswordLength; i++) {
				int r = (int) (Math.random() * chars.length);
				s += String.valueOf(chars[r]);
			}

			result.add(s);
		}
		return result;
	}

	private void setAdditionalCheckable(final boolean b) {
		this.checkAdditional.setEnabled(b);
		this.checkNumber.setEnabled(b);
		this.checkSmall.setEnabled(b);
		this.checkWide.setEnabled(b);
		this.lb_PasswordLength.setEnabled(b);
		this.sp_PasswordLength.setEnabled(b);

		// this.checkNumber.setSelection(b);
		// this.checkSmall.setSelection(b);
		// this.checkWide.setSelection(b);
	}

	private void setPasswortProportiesFromActivatorToPasswordGenerationDialog() {

		try {
			PasswordGenerationDialog.this.radioDefaultPassword.setSelection(PasswordPlugin
					.getDefault().getDialogSettings().getBoolean(
							PGSettings.AC_RADIO_DEFAULT_PASSWORD));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}
		try {
			PasswordGenerationDialog.this.comboDefaultPasswordLength.select(PasswordPlugin
					.getDefault().getDialogSettings().getInt(
							PGSettings.AC_COMBO_DEFAULT_PASSWORD_INDEX));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}

		try {
			PasswordGenerationDialog.this.radioUserDefinedPassword.setSelection(PasswordPlugin
					.getDefault().getDialogSettings().getBoolean(
							PGSettings.AC_RADIO_USER_DEFINED_PASSWORD));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}

		try {
			PasswordGenerationDialog.this.currentPasswordLength = PasswordPlugin.getDefault()
					.getDialogSettings().getInt(PGSettings.AC_CURRENT_PASSWORD_LENGTH);
		} catch (NumberFormatException e) {
			// TODO: log4j
			e.printStackTrace();
		}

		try {
			PasswordGenerationDialog.this.checkWide.setSelection(PasswordPlugin.getDefault()
					.getDialogSettings().getBoolean(PGSettings.AC_CHECK_WIDE));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}

		try {
			PasswordGenerationDialog.this.checkNumber.setSelection(PasswordPlugin.getDefault()
					.getDialogSettings().getBoolean(PGSettings.AC_CHECK_NUMBER));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}

		try {
			PasswordGenerationDialog.this.checkSmall.setSelection(PasswordPlugin.getDefault()
					.getDialogSettings().getBoolean(PGSettings.AC_CHECK_SMALL));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}

		try {
			PasswordGenerationDialog.this.checkAdditional.setSelection(PasswordPlugin.getDefault()
					.getDialogSettings().getBoolean(PGSettings.AC_CHECK_ADDITIONAL));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}

		try {
			PasswordGenerationDialog.this.tx_additionalCharacters.setText(PasswordPlugin
					.getDefault().getDialogSettings().get(PGSettings.AC_TX_ADDITIONAL_CHARACTERS));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}
		try {
			PasswordGenerationDialog.this.tx_additionalCharacters.setEnabled(PasswordPlugin
					.getDefault().getDialogSettings().getBoolean(
							PGSettings.AC_TX_ADDITIONAL_CHARACTERS_ENABLED));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}
		try {
			PasswordGenerationDialog.this.sp_PasswordLength.setSelection(PasswordPlugin
					.getDefault().getDialogSettings().getInt(PGSettings.AC_SP_PASSWORD_LENGTH));
		} catch (Exception e) {
			// TODO: log4j
			e.printStackTrace();
		}
	}

	private void setPasswortProportiesFromActivatorToDefault() {
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_USER_SETTINGS, false);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_RADIO_DEFAULT_PASSWORD,
				true);
		PasswordPlugin.getDefault().getDialogSettings().put(
				PGSettings.AC_COMBO_DEFAULT_PASSWORD_INDEX, 3);
		PasswordPlugin.getDefault().getDialogSettings().put(
				PGSettings.AC_RADIO_USER_DEFINED_PASSWORD, false);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CURRENT_PASSWORD_LENGTH,
				8);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CHECK_WIDE, false);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CHECK_NUMBER, false);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CHECK_SMALL, false);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CHECK_ADDITIONAL, false);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_TX_ADDITIONAL_CHARACTERS,
				PGSettings.AC_DEFAULT_ADDITIONAL_CHARACTERS);
		PasswordPlugin.getDefault().getDialogSettings().put(
				PGSettings.AC_TX_ADDITIONAL_CHARACTERS_ENABLED, false);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_SP_PASSWORD_LENGTH, 8);
	}

	private void setPasswortProportiesFromPasswortGenerationDialogToActivator() {
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_USER_SETTINGS, true);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_RADIO_DEFAULT_PASSWORD,
				PasswordGenerationDialog.this.radioDefaultPassword.getSelection());
		PasswordPlugin.getDefault().getDialogSettings().put(
				PGSettings.AC_RADIO_USER_DEFINED_PASSWORD,
				PasswordGenerationDialog.this.radioUserDefinedPassword.getSelection());
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CURRENT_PASSWORD_LENGTH,
				PasswordGenerationDialog.this.currentPasswordLength);
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CHECK_WIDE,
				PasswordGenerationDialog.this.checkWide.getSelection());
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CHECK_NUMBER,
				PasswordGenerationDialog.this.checkNumber.getSelection());
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CHECK_SMALL,
				PasswordGenerationDialog.this.checkSmall.getSelection());
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_CHECK_ADDITIONAL,
				PasswordGenerationDialog.this.checkAdditional.getSelection());
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_TX_ADDITIONAL_CHARACTERS,
				PasswordGenerationDialog.this.tx_additionalCharacters.getText());
		PasswordPlugin.getDefault().getDialogSettings().put(
				PGSettings.AC_TX_ADDITIONAL_CHARACTERS_ENABLED,
				PasswordGenerationDialog.this.tx_additionalCharacters.getEnabled());
		PasswordPlugin.getDefault().getDialogSettings().put(
				PGSettings.AC_COMBO_DEFAULT_PASSWORD_INDEX,
				this.comboDefaultPasswordLength.getSelectionIndex());
		PasswordPlugin.getDefault().getDialogSettings().put(PGSettings.AC_SP_PASSWORD_LENGTH,
				this.sp_PasswordLength.getSelection());
	}
}