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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.editor.FormPage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.ui.editors.InformationEditor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractInformationFormPage extends FormPage {

	private InformationUnit modelObject = null;

	public AbstractInformationFormPage() {
		super(null, null, null);
	}

	private boolean dirty = false;

	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}

	protected abstract String getString();

	@Override
	public boolean isDirty() {
		return this.dirty;
	}

	public void setDirty(boolean dirty) {
		if (dirty != this.dirty) {
			this.dirty = dirty;
			if (dirty) {
				((InformationEditor) getEditor()).setDirty(dirty);
			}
		}
	}

	protected void addDirtyOnModifyListener(Control control) {
		control.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				setDirty(true);

			}

		});
	}

	protected InformationUnit getModelObject() {
		return this.modelObject;
	}

	public void setModelObject(InformationUnit modelObject) {
		this.modelObject = modelObject;
	}

}
