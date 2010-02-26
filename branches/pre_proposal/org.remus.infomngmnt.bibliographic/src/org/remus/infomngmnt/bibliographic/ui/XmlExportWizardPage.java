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
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.StatusCreator;

public class XmlExportWizardPage extends WizardPage {
	
	private static final String PAGE_ID = "org.remus.infomngmnt.bibliographic.ui.xmlexportPage"; //$NON-NLS-1$

	Category selectedCategory = null;
	
	private Text destDirText = null;
	private Button browseButton = null;
	
	private Text exportEleText = null;
	private Button exportEleButton = null;
		
	// Key values for the dialog settings object
	private final static String SETTINGS_SAVED = "Settings saved"; //$NON-NLS-1$
	private final static String DEST_FILE_SETTING = "Destination file setting"; //$NON-NLS-1$
	
	public XmlExportWizardPage() {
		super(PAGE_ID);
		setPageComplete(false);
		setTitle("BibTeXML export wizard");
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		container.setLayout(layout);		
		createExportDirectoryControl(container);
		initSettings();
		Dialog.applyDialogFont(container);
		setControl(container);
		setPageComplete(validate());
	}

	
	/**
	 * Create widgets for specifying the destination directory
	 */
	private void createExportDirectoryControl(final Composite parent) {
		parent.setLayout(new GridLayout(3, false));
		parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		new Label(parent, SWT.NONE).setText("Element for export:");
		exportEleText = new Text(parent, SWT.BORDER);
		if (selectedCategory != null)
			exportEleText.setText(CategoryUtil.categoryToString(selectedCategory));
		exportEleText.setEditable(false);
		exportEleText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		exportEleText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				controlChanged();
			}
		});
		exportEleButton = new Button(parent, SWT.PUSH);
		exportEleButton.setText("Select");
		exportEleButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(
						EditingUtil.getInstance().getAdapterFactory());
				AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(
						EditingUtil.getInstance().getAdapterFactory());
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
						adapterFactoryLabelProvider, adapterFactoryContentProvider);
				dialog.setAllowMultiple(false);
				dialog.setDoubleClickSelects(true);
				dialog.setTitle("Select a category");
				dialog.setMessage("Select a cateogry");
				dialog.addFilter(new ViewerFilter() {
					@Override
					public boolean select(final Viewer viewer, final Object parentElement,
							final Object element) {
						return element instanceof Category;
					}
				});
				dialog.setValidator(new ISelectionStatusValidator() {
					public IStatus validate(final Object[] selection) {
						if (selection.length == 0) {
							return StatusCreator.newStatus("No parent category selected...");
						}
						return StatusCreator.newStatus(IStatus.OK, "", null);
					}
				});
				dialog.setInput(ApplicationModelPool.getInstance().getModel());
				dialog.setInitialSelection(selectedCategory);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					Category selectedCategory = (Category) result[0];
					exportEleText.setText(CategoryUtil.categoryToString(selectedCategory));
				}							
			}
		});
		
		new Label(parent, SWT.NONE).setText("Destination:");
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
				dialog.setText("Select file for export");
				String[] extensions = {"*.xml"};
				dialog.setFilterExtensions(extensions);
				final String open = dialog.open();
				if (open == null || open.equals("")) {
					return;
				}
				if (!open.contains(".xml"))
					destDirText.setText(open + ".xml");
				else
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
				destDirText.setText(directory);
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

		// Check that a export element has been specified
		if (exportEleText.getText().equals("")) { //$NON-NLS-1$
			setMessage("Please choose an element for export", IStatus.WARNING);
			return false;
		}
		
		// Check that a destination dir has been specified
		if (destDirText.getText().equals("")) { //$NON-NLS-1$
			setMessage("Please choose an export file", IStatus.WARNING);
			return false;
		}

		return true;
	}

	public Category getExportElement() {
		return selectedCategory;
	}

	public void setExportElement(Category exportElement) {
		this.selectedCategory = exportElement;
	}
	
	/** Returns the directory where data files are to be saved */
	public String getDestinationFile() {
		return destDirText.getText();
	}
}
