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

package org.remus.infomngmnt.common.ui.extension;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.remus.infomngmt.common.ui.uimodel.TraySection;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractTrayPreferencePage {

	protected TraySection section;

	private Control control;

	public static final String KEY_NAME = "KEY_NAME"; //$NON-NLS-1$

	private EditingDomain editingDomain;

	public void initialize(TraySection section, EditingDomain editingDomain) {
		this.section = section;
		this.editingDomain = editingDomain;
	}

	public abstract void createControl(Composite parent);

	public Control getControl() {
		if (this.control == null) {
			throw new IllegalArgumentException("Control must be set.");
		}
		return this.control;
	}

	protected void setControl(Control control) {
		this.control = control;
	}

	public void bindValuesToUi() {
		// do nothing by default

	}

	public void performApply() {
		// does nothing by default.

	}



}
