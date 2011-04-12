/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.plaintext.section;

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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.remus.infomngmnt.plaintext.Activator;
import org.remus.infomngmnt.plaintext.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InstantSectionPreferences extends AbstractTrayPreferencePage {

	private Text sourceText;
	private final RemusServiceTracker remusServiceTracker;
	private final IApplicationModel applicationModel;
	private final IEditingHandler editingHandler;
	private String selectedInfoUnitId;

	public static final String PREFERENCE_INFOUNIT_ID = "PREFERENCE_INFOUNIT_ID"; //$NON-NLS-1$
	private Listener listener;

	/**
	 * 
	 */
	public InstantSectionPreferences() {
		remusServiceTracker = new RemusServiceTracker(
				Platform.getBundle(Activator.PLUGIN_ID));
		applicationModel = remusServiceTracker
				.getService(IApplicationModel.class);
		editingHandler = remusServiceTracker.getService(IEditingHandler.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.remus.ui.desktop.extension.AbstractTrayPreferencePage#
	 * createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		Composite comp = new Composite(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		comp.setLayout(gridLayout);

		final Label sourceLabel = new Label(comp, SWT.NONE);
		sourceLabel.setText(Messages.InstantSectionPreferences_InfoUnitToCopyFrom);
		new Label(comp, SWT.NONE);

		sourceText = new Text(comp, SWT.READ_ONLY | SWT.BORDER);
		final GridData gd_feedText = new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1);
		sourceText.setLayoutData(gd_feedText);

		final Button browseButton = new Button(comp, SWT.NONE);
		browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 2, 1));
		browseButton.setText(Messages.InstantSectionPreferences_Browse);
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Set<? extends EObject> allItemsByType = InformationUtil
						.getAllItemsByType(Activator.INFO_TYPE_ID);
				NavigatorDecoratingLabelProvider labelProvider = new NavigatorDecoratingLabelProvider(
						new AdapterFactoryLabelProvider(editingHandler
								.getAdapterFactory())) {
					@Override
					public String getText(final Object element) {
						if (element instanceof InformationUnitListItem) {
							return InformationUtil
									.getFullReadablePath((InformationUnitListItem) element);
						}
						return super.getText(element);
					}
				};
				ElementListSelectionDialog dialog = new ElementListSelectionDialog(
						parent.getShell(), labelProvider);
				dialog.setAllowDuplicates(false);
				dialog.setElements(allItemsByType.toArray());
				dialog.setEmptySelectionMessage(Messages.InstantSectionPreferences_SelectionRequired);
				dialog.setMultipleSelection(false);
				dialog.setTitle(Messages.InstantSectionPreferences_SelectTextInfoUnit);
				dialog.setMessage(Messages.InstantSectionPreferences_SelectTextInfoUnit);
				dialog.setIgnoreCase(true);
				if (dialog.open() == IDialogConstants.OK_ID) {
					setSelectedInfoUnit(((InformationUnitListItem) dialog
							.getFirstResult()).getId());
				}
			}
		});
		setControl(comp);

	}

	@Override
	public void bindValuesToUi() {
		setSelectedInfoUnit(section.getPreferenceOptions().get(
				PREFERENCE_INFOUNIT_ID));
		listener = new Listener() {
			public void handleEvent(final Event event) {
				section.getPreferenceOptions().put(PREFERENCE_INFOUNIT_ID,
						String.valueOf(selectedInfoUnitId));
			}
		};
		sourceText.addListener(SWT.Modify, listener);
	}

	private void setSelectedInfoUnit(final String id) {
		selectedInfoUnitId = id;
		InformationUnitListItem itemById = applicationModel.getItemById(id,
				null);
		if (itemById != null) {
			sourceText.setText(InformationUtil.getFullReadablePath(itemById));
		}
	}

}
