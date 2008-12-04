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

package org.remus.infomngmnt.ui.extension;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.RuleValue;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractCreationPreferencePage {

	public static final String NODENAME_PREDEFINED_CATEGORY = "NODENAME_PREDEFINED_CATEGORY"; //$NON-NLS-1$
	public static final String NODENAME_PREDEFINED_NAME = "NODENAME_PREDEFINED_NAME"; //$NON-NLS-1$
	public static final String NODENAME_PREDEFINED_KEYWORDS = "NODENAME_PREDEFINED_KEYWORDS"; //$NON-NLS-1$
	public static final String NODENAME_PREDEFINED_DESCRIPTION = "NODENAME_PREDEFINED_DESCRIPTION"; //$NON-NLS-1$

	protected RuleValue value;
	protected EditingDomain domain;
	private Control control;
	protected Text predefCategoryText;
	protected Text predefNameText;

	public void setValues(
			RuleValue value,
			EditingDomain domain) {
		this.value = value;
		this.domain = domain;
	}

	public void applyValues() {

	}

	public void bindValuesToUi() {
		// does nothing by default
	}

	protected Composite createDefaultPreferences(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Label label = new Label(composite, SWT.NONE);
		label.setText("Predefined Category");
		this.predefCategoryText = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		this.predefCategoryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Label nameLabel = new Label(composite, SWT.NONE);
		nameLabel.setText("Predefined Name");
		this.predefNameText = new Text(composite, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		this.predefNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		return composite;
	}

	public void createPreferencePage(Composite parent) {
		setControl(createDefaultPreferences(parent));
	}

	public Control getControl() {
		if (this.control == null) {
			throw new IllegalArgumentException("Control must be set.");
		}
		return this.control;
	}

	protected void setControl(Control control) {
		this.control = control;
	}


}
