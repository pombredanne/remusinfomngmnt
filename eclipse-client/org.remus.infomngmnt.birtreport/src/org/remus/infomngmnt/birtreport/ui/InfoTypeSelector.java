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
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.core.extension.IInfoType;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.core.services.IInformationTypeHandler;
import org.eclipse.remus.ui.viewer.provider.InformationUnitLabelProvider;
import org.eclipse.remus.ui.viewer.provider.NavigatorDecoratingLabelProvider;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.birtreport.messages.Messages;
import org.remus.infomngmnt.birtreport.parameter.AbstractParameterControl;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InfoTypeSelector extends AbstractParameterControl {

	public static final String INFO_TYPE = "infotype"; //$NON-NLS-1$
	public static final String DIALOG_TITLE = "dialogtitle"; //$NON-NLS-1$
	public static final String DIALOG_MESSAGE = "dialogmessage"; //$NON-NLS-1$

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
		IInformationTypeHandler informationTypeHandler = ReportActivator.getDefault()
				.getServiceTracker().getService(IInformationTypeHandler.class);
		IInfoType infoTypeByType = informationTypeHandler.getInfoTypeByType(this.options
				.get(INFO_TYPE));
		if (infoTypeByType == null) {
			throw new IllegalArgumentException(Messages.InfoTypeSelector_InfoTypeNotInstalled);
		}
		this.pathText = new Text(parent, SWT.READ_ONLY | SWT.BORDER);
		final GridData gd_feedText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.pathText.setLayoutData(gd_feedText);

		final Button browseButton = new Button(parent, SWT.NONE);
		browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		browseButton.setText(Messages.InfoTypeSelector_Browse);
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Set<? extends EObject> allItemsByType = InformationUtil
						.getAllItemsByType(InfoTypeSelector.this.options.get(INFO_TYPE));
				IEditingHandler editService = ReportActivator.getDefault().getServiceTracker()
						.getService(IEditingHandler.class);
				NavigatorDecoratingLabelProvider labelProvider = new NavigatorDecoratingLabelProvider(
						new InformationUnitLabelProvider(editService.getAdapterFactory())) {
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
				dialog.setEmptySelectionMessage(Messages.InfoTypeSelector_SelectionRequired);
				dialog.setMultipleSelection(false);
				if (InfoTypeSelector.this.options.get(DIALOG_TITLE) != null) {
					dialog.setTitle(InfoTypeSelector.this.options.get(DIALOG_TITLE));
				} else {
					dialog.setTitle(Messages.InfoTypeSelector_SelectItem);
				}
				if (InfoTypeSelector.this.options.get(DIALOG_MESSAGE) != null) {
					dialog.setMessage(InfoTypeSelector.this.options.get(DIALOG_MESSAGE));
				} else {
					dialog.setMessage(Messages.InfoTypeSelector_SelectItemFromList);
				}
				dialog.setIgnoreCase(true);
				if (dialog.open() == IDialogConstants.OK_ID) {
					InfoTypeSelector.this.selectedInfoType = ((InformationUnitListItem) dialog
							.getFirstResult());
					InfoTypeSelector.this.pathText.setText(InformationUtil
							.getFullReadablePath(InfoTypeSelector.this.selectedInfoType));
				}
				ReportActivator.getDefault().getServiceTracker().ungetService(editService);
			}
		});
		ReportActivator.getDefault().getServiceTracker().ungetService(informationTypeHandler);

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
