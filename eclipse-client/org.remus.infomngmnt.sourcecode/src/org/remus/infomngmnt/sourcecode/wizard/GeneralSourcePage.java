/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.sourcecode.wizard;

import java.util.Collection;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;
import org.remus.infomngmnt.ui.newwizards.GeneralPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralSourcePage extends GeneralPage {

	protected static final String SECTION_NAME = "SECTION_NAME";
	protected static final String SECTION_KEY = "SECTION_KEY";
	private Combo typeCombo;
	private Collection<String> values;
	private IDialogSettings settings;

	public GeneralSourcePage(final InformationUnitListItem selection) {
		super(selection);

	}

	public GeneralSourcePage(final Category category) {
		super(category);
	}

	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());

		doCreateParentElementGroup(container);
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		group.setLayout(new GridLayout(2, false));
		group.setText("Name && Type");
		doCreateNameElements(group);

		new Label(group, SWT.NONE).setText("Type");
		this.typeCombo = new Combo(group, SWT.READ_ONLY);
		this.typeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		this.values = SourceCodePlugin.getDefault().getSourceTypes().keySet();
		for (String string : this.values) {
			this.typeCombo.add(string);
		}

		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		initValidation();
		setControl(container);
	}

	@Override
	protected void initDatabinding() {
		this.settings = SourceCodePlugin.getDefault().getDialogSettings();
		super.initDatabinding();
		ISWTObservableValue swtType = SWTObservables.observeText(this.typeCombo);
		IObservableValue emfType = EMFObservables.observeValue(InformationUtil.getChildByType(
				this.unit, SourceCodePlugin.SRCTYPE_NAME),
				InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE);
		this.ctx.bindValue(swtType, emfType, new UpdateValueStrategy() {
			@Override
			public Object convert(final Object value) {
				if (value != null && value.toString().length() > 0) {
					UIUtil.getDialogSettings(SECTION_NAME, GeneralSourcePage.this.settings).put(
							SECTION_KEY, GeneralSourcePage.this.typeCombo.getSelectionIndex());
				}
				return super.convert(value);
			}
		}, null);
		try {
			this.typeCombo.select(UIUtil.getDialogSettings(SECTION_NAME, this.settings).getInt(
					SECTION_KEY));
		} catch (NumberFormatException e) {
			this.typeCombo.select(0);
		}
	}
}
