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
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.databinding.AbstractBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.BindingWidgetFactory;
import org.remus.infomngmnt.common.ui.databinding.ComboBindingWidget;
import org.remus.infomngmnt.common.ui.databinding.IEMFEditBindingProvider;
import org.remus.infomngmnt.common.ui.jface.BindingStatusDialog;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationUtil;

public class EditContactPersonDialog extends BindingStatusDialog {

	private final InformationUnit contact;
	private AbstractBindingWidget createTextBindingWidget;
	private Text tx_TitleAfterName;
	private Text tx_LastName;
	private Text tx_AdditionalName;
	private Text tx_FirstName;
	private Combo cb_Title;
	private final IEMFEditBindingProvider editingDomainProvider;

	public EditContactPersonDialog(final Shell parentShell, final InformationUnit contact,
			final IEMFEditBindingProvider editingDomainProvider) {
		super(parentShell);
		this.editingDomainProvider = editingDomainProvider;
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.contact = contact;

	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite comp = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridData gd_text = new GridData(SWT.FILL, SWT.TOP, true, false);
		final Label lb_Title = new Label(comp, SWT.NONE);
		lb_Title.setLayoutData(new GridData());
		lb_Title.setText("Title");
		this.cb_Title = new Combo(comp, SWT.DROP_DOWN | SWT.READ_ONLY);
		this.cb_Title.setLayoutData(new GridData());

		final Label lb_FirstName = new Label(comp, SWT.NONE);
		lb_FirstName.setLayoutData(new GridData());
		lb_FirstName.setText("First Name:");
		this.tx_FirstName = new Text(comp, SWT.BORDER);
		this.tx_FirstName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Label lb_AdditionalName = new Label(comp, SWT.NONE);
		lb_AdditionalName.setLayoutData(new GridData());
		lb_AdditionalName.setText("Additional Name:");
		this.tx_AdditionalName = new Text(comp, SWT.BORDER);
		this.tx_AdditionalName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Label lb_LastName = new Label(comp, SWT.NONE);
		lb_LastName.setLayoutData(new GridData());
		lb_LastName.setText("Last Name:");
		this.tx_LastName = new Text(comp, SWT.BORDER);
		this.tx_LastName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Label lb_TitleAfterName = new Label(comp, SWT.NONE);
		lb_TitleAfterName.setLayoutData(new GridData());
		lb_TitleAfterName.setText("Title After Name:");
		this.tx_TitleAfterName = new Text(comp, SWT.BORDER);
		this.tx_TitleAfterName.setLayoutData(gd_text);

		bindValuesToUi();
		return comp;
	}

	private void bindValuesToUi() {

		this.createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(
				this.tx_FirstName, this.editingDomainProvider);
		this.createTextBindingWidget.bindModel(InformationUtil.getChildByType(this.contact,
				ContactActivator.NODE_NAME_PERS_NAME_FIRST),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(this.createTextBindingWidget.getBinding());

		this.createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(
				this.tx_TitleAfterName, this.editingDomainProvider);
		this.createTextBindingWidget.bindModel(InformationUtil.getChildByType(this.contact,
				ContactActivator.NODE_NAME_PERS_NAME_TITLE_AFTER),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(this.createTextBindingWidget.getBinding());

		this.createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(
				this.tx_AdditionalName, this.editingDomainProvider);
		this.createTextBindingWidget.bindModel(InformationUtil.getChildByType(this.contact,
				ContactActivator.NODE_NAME_PERS_NAME_ADDITIONAL),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(this.createTextBindingWidget.getBinding());

		this.createTextBindingWidget = BindingWidgetFactory.createTextBindingWidget(
				this.tx_LastName, this.editingDomainProvider);
		this.createTextBindingWidget.bindModel(InformationUtil.getChildByType(this.contact,
				ContactActivator.NODE_NAME_PERS_NAME_LAST),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(this.createTextBindingWidget.getBinding());

		ComboBindingWidget createComboBinding = BindingWidgetFactory.createComboBinding(
				this.cb_Title, this.editingDomainProvider);
		List<String> values = new ArrayList<String>();
		values.add("");
		values.add("Dr.");
		values.add("Prof.");
		values.add("Female");
		values.add("Miss");
		values.add("Male");
		createComboBinding.setLabelProvider(new LabelProvider());
		createComboBinding.setInput(values);
		createComboBinding.bindModel(InformationUtil.getChildByType(this.contact,
				ContactActivator.NODE_NAME_PERS_NAME_TITLE),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		addBinding(createComboBinding.getBinding());

	}

	@Override
	protected Point getInitialSize() {
		return new Point(350, 280);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit Name");
	}
}
