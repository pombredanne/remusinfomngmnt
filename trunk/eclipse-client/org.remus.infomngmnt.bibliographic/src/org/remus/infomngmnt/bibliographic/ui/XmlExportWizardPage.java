/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class XmlExportWizardPage extends WizardPage {
	
	private static final String PAGE_ID = "org.remus.infomngmnt.bibliographic.ui.xmlexportPage"; //$NON-NLS-1$

	private Button browseButton = null;
	private String exportElement = "";
	private Text destDirText = null;
	
	// Key values for the dialog settings object
	private final static String SETTINGS_SAVED = "Settings saved"; //$NON-NLS-1$
	private final static String DEST_FILE_SETTING = "Destination file setting"; //$NON-NLS-1$
	
	public XmlExportWizardPage() {
		super(PAGE_ID);
		//setPageComplete(false);
		setTitle("Das ist der Titel");
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);
		
		createExportDirectoryControl(container);

// TODO: initSettings() does not work
//		initSettings();

		Dialog.applyDialogFont(container);
		setControl(container);
		setPageComplete(validate());
	}

	
	/**
	 * Create widgets for specifying the destination directory
	 */
	private void createExportDirectoryControl(Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		new Label(parent, SWT.NONE).setText("Element for export:");
		Label l = new Label(parent, SWT.NONE);
		l.setText(exportElement);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		l.setLayoutData(gd);
		new Label(parent, SWT.NONE).setText("Destination");

		destDirText = new Text(parent, SWT.BORDER);
		destDirText.setEditable(false);
		destDirText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		destDirText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				controlChanged();
			}
		});

		browseButton = new Button(parent, SWT.PUSH);
		browseButton.setText("Browse");
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
				dialog.setText("Select File for export");
				String[] extensions = {"*.xml"};
				dialog.setFilterExtensions(extensions);
				final String open = dialog.open();
				if (open == null || open.equals("")) {
					return;
				}
				destDirText.setText(open);
				controlChanged();
			}
		});
	}
	
	/**
	 * Initializes controls with values from the Dialog Settings object
	 */
	protected void initSettings() {
		IDialogSettings settings = getDialogSettings();

		if (settings.get(SETTINGS_SAVED) == null) {
			destDirText.setText(""); //$NON-NLS-1$
		} else {
			String directory = settings.get(DEST_FILE_SETTING);
			if (directory != null) {
				destDirText.setText(settings.get(DEST_FILE_SETTING));
			}
		}
	}

	/**
	 * Saves the control values in the dialog settings to be used as defaults the next time the page is opened
	 */
	public void saveSettings() {
		IDialogSettings settings = getDialogSettings();
		settings.put(DEST_FILE_SETTING, destDirText.getText());
		settings.put(SETTINGS_SAVED, SETTINGS_SAVED);
	}
	
	/** Called to indicate that a control's value has changed */
	public void controlChanged() {
		setPageComplete(validate());
	}
	
	/** Returns true if the information entered by the user is valid */
	protected boolean validate() {
		setMessage(null);

		// Check that a destination dir has been specified
		if (destDirText.getText().equals("")) { //$NON-NLS-1$
			setMessage("Please choose an export file", IStatus.WARNING);
			return false;
		}

		return true;
	}

	public String getExportElement() {
		return exportElement;
	}

	public void setExportElement(String exportElement) {
		this.exportElement = exportElement;
	}
	
}
