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

package org.remus.infomngmnt.birtreport.ui;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.birtreport.parameter.AbstractParameterControl;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.ui.provider.NavigatorDecoratingLabelProvider;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InfoTypeSelector extends AbstractParameterControl {

	public static final String INFO_TYPE = "infotype";
	public static final String DIALOG_TITLE = "dialogtitle";
	public static final String DIALOG_MESSAGE = "dialogmessage";

	private Text pathText;

	protected InformationUnitListItem selectedInfoType;

	/**
	 * 
	 */
	public InfoTypeSelector() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.birtreport.parameter.AbstractParameterControl#
	 * createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(
				this.options.get(INFO_TYPE));
		if (infoTypeByType == null) {
			throw new IllegalArgumentException("Infotype not installed");
		}
		this.pathText = new Text(parent, SWT.READ_ONLY | SWT.BORDER);
		final GridData gd_feedText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.pathText.setLayoutData(gd_feedText);

		final Button browseButton = new Button(parent, SWT.NONE);
		browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		browseButton.setText("Browse...");
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Set<? extends EObject> allItemsByType = InformationUtil
						.getAllItemsByType(InfoTypeSelector.this.options.get(INFO_TYPE));
				NavigatorDecoratingLabelProvider labelProvider = new NavigatorDecoratingLabelProvider(
						new AdapterFactoryLabelProvider(EditingUtil.getInstance()
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
				ElementListSelectionDialog dialog = new ElementListSelectionDialog(parent
						.getShell(), labelProvider);
				dialog.setAllowDuplicates(false);
				dialog.setElements(allItemsByType.toArray());
				dialog.setEmptySelectionMessage("Selection is required");
				dialog.setMultipleSelection(false);
				if (InfoTypeSelector.this.options.get(DIALOG_TITLE) != null) {
					dialog.setTitle(InfoTypeSelector.this.options.get(DIALOG_TITLE));
				} else {
					dialog.setTitle("Select a info item");
				}
				if (InfoTypeSelector.this.options.get(DIALOG_MESSAGE) != null) {
					dialog.setMessage(InfoTypeSelector.this.options.get(DIALOG_MESSAGE));
				} else {
					dialog.setMessage("Please select a info item from the list");
				}
				dialog.setIgnoreCase(true);
				if (dialog.open() == IDialogConstants.OK_ID) {
					InfoTypeSelector.this.selectedInfoType = ((InformationUnitListItem) dialog
							.getFirstResult());
					InfoTypeSelector.this.pathText.setText(InformationUtil
							.getFullReadablePath(InfoTypeSelector.this.selectedInfoType));
				}
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.birtreport.parameter.AbstractParameterControl#
	 * getParameterValue()
	 */
	@Override
	public String getParameterValue() {
		return InformationUtil.getFullReadablePath(this.selectedInfoType);
	}

}
