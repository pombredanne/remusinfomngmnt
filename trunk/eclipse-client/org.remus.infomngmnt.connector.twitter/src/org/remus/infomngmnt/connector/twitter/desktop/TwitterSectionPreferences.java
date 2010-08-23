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

package org.remus.infomngmnt.connector.twitter.desktop;

import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.desktop.extension.AbstractTrayPreferencePage;
import org.eclipse.remus.ui.viewer.provider.NavigatorDecoratingLabelProvider;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import org.remus.infomngmnt.connector.twitter.TwitterActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterSectionPreferences extends AbstractTrayPreferencePage {

	private Text nameText;
	private Text feedText;
	private Spinner spinner;
	private String selectedInfoUnitId;

	public static final String PREFERENCE_MESSAGE_COUNT = "PREFERENCE_MESSAGE_COUNT"; //$NON-NLS-1$
	public static final String PREFERENCE_INFOUNIT_ID = "PREFERENCE_INFOUNIT_ID"; //$NON-NLS-1$
	public static final String PREFERENCE_WIDTH = "PREFERENCE_WIDTH"; //$NON-NLS-1$

	public static final int WIDTH_DEFAULT = 80;

	private Spinner widthSpinner;
	private final RemusServiceTracker remusServiceTracker;
	private final IApplicationModel applicationModel;
	private final IEditingHandler editingHandler;

	/**
	 * 
	 */
	public TwitterSectionPreferences() {
		this.remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(TwitterActivator.PLUGIN_ID));
		this.applicationModel = this.remusServiceTracker.getService(IApplicationModel.class);
		this.editingHandler = this.remusServiceTracker.getService(IEditingHandler.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.common.ui.extension.AbstractTrayPreferencePage#
	 * createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		comp.setLayout(gridLayout);

		final Label nameLabel = new Label(comp, SWT.NONE);
		final GridData gd_nameLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		nameLabel.setLayoutData(gd_nameLabel);
		nameLabel.setText("Name");

		this.nameText = new Text(comp, SWT.BORDER);
		final GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		this.nameText.setLayoutData(gd_nameText);

		final Label feedLabel = new Label(comp, SWT.NONE);
		feedLabel.setText("Feed");
		new Label(comp, SWT.NONE);

		this.feedText = new Text(comp, SWT.READ_ONLY | SWT.BORDER);
		final GridData gd_feedText = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		this.feedText.setLayoutData(gd_feedText);

		final Button browseButton = new Button(comp, SWT.NONE);
		browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));
		browseButton.setText("Browse...");
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Set<? extends EObject> allItemsByType = InformationUtil
						.getAllItemsByType(TwitterActivator.INFOTYPE_ID);
				NavigatorDecoratingLabelProvider labelProvider = new NavigatorDecoratingLabelProvider(
						new AdapterFactoryLabelProvider(
								TwitterSectionPreferences.this.editingHandler.getAdapterFactory())) {
					@Override
					public String getText(final Object element) {
						if (element instanceof InformationUnitListItem) {
							return InformationUtil
									.getFullReadablePath((InformationUnitListItem) element);
						}
						return super.getText(element);
					}
				};
				ElementListSelectionDialog dialog = new ElementListSelectionDialog(parent
						.getShell(), labelProvider);
				dialog.setAllowDuplicates(false);
				dialog.setElements(allItemsByType.toArray());
				dialog.setEmptySelectionMessage("Selection is required");
				dialog.setMultipleSelection(false);
				dialog.setTitle("Select a twitter feed");
				dialog.setMessage("Please select a twitter feed from the list");
				dialog.setIgnoreCase(true);
				if (dialog.open() == IDialogConstants.OK_ID) {
					setSelectedInfoUnit(((InformationUnitListItem) dialog.getFirstResult()).getId());
				}
			}
		});
		setSelectedInfoUnit(this.section.getPreferenceOptions().get(PREFERENCE_INFOUNIT_ID));

		final Label latestMessagesLabel = new Label(comp, SWT.NONE);
		latestMessagesLabel.setText("Latest messages:");

		this.spinner = new Spinner(comp, SWT.BORDER);
		this.spinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		this.spinner.setIncrement(1);
		this.spinner.setMaximum(5);
		this.spinner.setMinimum(1);

		setControl(comp);

		final Label widthLabel = new Label(comp, SWT.NONE);
		widthLabel.setText("Width");

		this.widthSpinner = new Spinner(comp, SWT.BORDER);
		this.widthSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		this.widthSpinner.setIncrement(10);
		this.widthSpinner.setMaximum(300);
		this.widthSpinner.setMinimum(50);

	}

	@Override
	public void bindValuesToUi() {
		this.nameText.setText(this.section.getName());
		this.nameText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				TwitterSectionPreferences.this.section
						.setName(TwitterSectionPreferences.this.nameText.getText());
			}
		});

		String string = this.section.getPreferenceOptions().get(PREFERENCE_MESSAGE_COUNT);
		Integer valueOf;
		try {
			valueOf = Integer.valueOf(string);
		} catch (NumberFormatException e) {
			valueOf = WIDTH_DEFAULT;
		}
		this.spinner.setSelection(valueOf);
		this.spinner.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				TwitterSectionPreferences.this.section.getPreferenceOptions().put(
						PREFERENCE_MESSAGE_COUNT,
						String.valueOf(TwitterSectionPreferences.this.spinner.getSelection()));
			}
		});

		String width = this.section.getPreferenceOptions().get(PREFERENCE_WIDTH);
		Integer widthValue;
		try {
			widthValue = Integer.valueOf(width);
		} catch (NumberFormatException e) {
			widthValue = 80;
		}
		this.widthSpinner.setSelection(widthValue);
		this.widthSpinner.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				TwitterSectionPreferences.this.section.getPreferenceOptions().put(PREFERENCE_WIDTH,
						String.valueOf(TwitterSectionPreferences.this.widthSpinner.getSelection()));
			}
		});
		this.feedText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				TwitterSectionPreferences.this.section.getPreferenceOptions().put(
						PREFERENCE_INFOUNIT_ID,
						String.valueOf(TwitterSectionPreferences.this.selectedInfoUnitId));
			}
		});

	}

	private void setSelectedInfoUnit(final String id) {
		this.selectedInfoUnitId = id;
		InformationUnitListItem itemById = this.applicationModel.getItemById(id, null);
		if (itemById != null) {
			this.feedText.setText(InformationUtil.getFullReadablePath(itemById));
		}
	}

}
