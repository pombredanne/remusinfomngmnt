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

package org.remus.infomngmnt.contact.wizard;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.common.ui.wizards.WizardValidatingUtil;
import org.eclipse.remus.ui.newwizards.GeneralPage;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.contact.ContactActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralContactPage extends GeneralPage {

	private Text firstName;
	private Text lastName;

	public GeneralContactPage(final Category category) {
		super(category);
	}

	public GeneralContactPage(final InformationUnitListItem selection) {
		super(selection);
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		setTitle("New Contact");
		setMessage("This wizard enables you to create a new contact.");
		// setImageDescriptor(ResourceManager.getPluginImageDescriptor(ImagePlugin.getDefault(),
		// "icons/iconexperience/photo_wizard_title.png"));

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(2, false));

		new Label(group, SWT.NONE).setText("Firstname");
		this.firstName = new Text(group, SWT.BORDER);
		this.firstName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(group, SWT.NONE).setText("Lastname");
		this.lastName = new Text(group, SWT.BORDER);
		this.lastName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		initValidation();
		setControl(container);
	}

	@Override
	protected void initDatabinding() {
		super.initDatabinding();
		IObservableValue emfFirstName = EMFObservables.observeValue(InformationUtil.getChildByType(
				this.unit, ContactActivator.NODE_NAME_PERS_NAME_FIRST),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		ISWTObservableValue swtFirstName = SWTObservables.observeText(this.firstName, SWT.Modify);
		this.ctx.bindValue(swtFirstName, emfFirstName, null, null);

		IObservableValue emfLastName = EMFObservables.observeValue(InformationUtil.getChildByType(
				this.unit, ContactActivator.NODE_NAME_PERS_NAME_LAST),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		ISWTObservableValue swtLastName = SWTObservables.observeText(this.lastName, SWT.Modify);
		this.ctx.bindValue(swtLastName, emfLastName, null, null);

	}

	@Override
	public boolean validate(final boolean showErrorMessage) {
		boolean validate = super.validate(showErrorMessage);
		if (validate) {
			if (this.firstName.getText().trim().length() == 0
					&& this.lastName.getText().trim().length() == 0) {
				if (showErrorMessage) {
					setErrorMessage("Either firstname or lastname must be set.");
				}
				return false;
			} else {
				setErrorMessage(null);
				return true;
			}
		} else {
			return validate;
		}
	}

	@Override
	protected void initValidation() {
		super.initValidation();
		WizardValidatingUtil.validateControlsOnModify(this, this.firstName, this.lastName);
	}

}
