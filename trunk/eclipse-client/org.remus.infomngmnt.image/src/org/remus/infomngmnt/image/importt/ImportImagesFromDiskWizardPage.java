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

package org.remus.infomngmnt.image.importt;

import java.io.File;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.wizards.IValidatingWizard;
import org.remus.infomngmnt.common.ui.wizards.WizardValidatingUtil;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.ui.category.CategoryBrowser;
import org.remus.infomngmnt.ui.databinding.BeansBindingUtil;

public class ImportImagesFromDiskWizardPage extends WizardPage implements IValidatingWizard {

	private Text text;

	private CategoryBrowser categoryBrowser;

	private final ImportImageObject importObject;

	private final DataBindingContext ctx;

	private Button copyFolderstructureToButton;

	private final IDialogSettings dialogSettings;

	public static final String DLG_IMAGEHDIMPORT = "DLG_IMAGEHDIMPORT"; //$NON-NLS-1$

	public static final String SOURCE = "SOURCE"; //$NON-NLS-1$

	public static final String TARGET = "TARGET"; //$NON-NLS-1$

	public static final String MAPPING = "MAPPING"; //$NON-NLS-1$

	private Button importAsFlatButton;

	/**
	 * Create the wizard
	 */
	public ImportImagesFromDiskWizardPage(final ImportImageObject ioObject) {
		super("wizardPage");
		this.importObject = ioObject;
		setTitle("Import images from harddisc");
		setDescription("This wizard enables you to import directory images into the application");
		this.ctx = new DataBindingContext();
		IDialogSettings dialogSettings = ImagePlugin.getDefault().getDialogSettings();
		this.dialogSettings = UIUtil.getDialogSettings(DLG_IMAGEHDIMPORT, dialogSettings);
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);
		//

		final Label directoryLabel = new Label(container, SWT.NONE);
		directoryLabel.setText("Directory");

		this.text = new Text(container, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Button browseButton = new Button(container, SWT.NONE);
		browseButton.setText("Browse...");
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				DirectoryDialog dd = new DirectoryDialog(getShell());
				String open = dd.open();
				if (open != null) {
					ImportImagesFromDiskWizardPage.this.text.setText(open);
				}
			}
		});

		final Label categoryLabel = new Label(container, SWT.NONE);
		categoryLabel.setText("Category");

		this.categoryBrowser = new CategoryBrowser(container, SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL, SWT.CENTER, true, false);
		layoutData.horizontalSpan = 2;
		this.categoryBrowser.setLayoutData(layoutData);

		new Label(container, SWT.NONE);
		final Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		final GridLayout gridLayout_2 = new GridLayout();
		gridLayout_2.numColumns = 2;
		composite.setLayout(gridLayout_2);

		this.importAsFlatButton = new Button(composite, SWT.RADIO);
		this.importAsFlatButton.setText("Import as flat list");

		this.copyFolderstructureToButton = new Button(composite, SWT.RADIO);
		this.copyFolderstructureToButton.setSelection(true);
		final GridData gd_copyFolderstructureToButton = new GridData();
		this.copyFolderstructureToButton.setLayoutData(gd_copyFolderstructureToButton);
		this.copyFolderstructureToButton.setText("Map folderstructure to categories");

		bindValuesToUi();
		setControl(container);
	}

	private void bindValuesToUi() {
		BeansBindingUtil.bindButton(this.copyFolderstructureToButton, this.importObject,
				ImportImageObject.PROP_FOLDER_STRUCTURE, this.ctx);
		BeansBindingUtil.bindText(this.categoryBrowser.getText(), this.importObject,
				ImportImageObject.PROP_CATEGORY, this.ctx);
		BeansBindingUtil.bindText(this.text, this.importObject, ImportImageObject.PROP_DIRECTORY,
				this.ctx);

		UIUtil.setTextChecked(this.dialogSettings.get(SOURCE), this.text);
		UIUtil.setTextChecked(this.dialogSettings.get(TARGET), this.categoryBrowser.getText());
		this.importAsFlatButton.setSelection(this.dialogSettings.getBoolean(MAPPING));
		this.copyFolderstructureToButton.setSelection(!this.importAsFlatButton.getSelection());

		WizardValidatingUtil.validateControlsOnModify(this, this.text, this.categoryBrowser
				.getText(), this.importAsFlatButton, this.copyFolderstructureToButton);

		setPageComplete(validate(false));
	}

	public boolean validate(final boolean b) {
		if (!new File(this.text.getText()).exists()) {
			if (b) {
				setErrorMessage("No valid directory.");
			}
			return false;
		}
		IStatus valid = this.categoryBrowser.isValid();
		if (!valid.isOK()) {
			if (b) {
				setErrorMessage("No valid category");
			}
			return false;
		}
		setErrorMessage(null);
		return true;
	}

	@Override
	public void dispose() {
		this.dialogSettings.put(SOURCE, this.text.getText());
		this.dialogSettings.put(TARGET, this.categoryBrowser.getText().getText());
		this.dialogSettings.put(MAPPING, this.importAsFlatButton.getSelection());
		super.dispose();
	}

	/**
	 * @return the importObject
	 */
	public ImportImageObject getImportObject() {
		return this.importObject;
	}
}
