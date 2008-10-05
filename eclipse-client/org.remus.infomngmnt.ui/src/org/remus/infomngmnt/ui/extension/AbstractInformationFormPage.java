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

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
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
	protected AdapterFactoryEditingDomain editingDomain;
	protected EMFDataBindingContext dataBindingContext;

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

	/**
	 * Binds a text control to a text control. The value to the model
	 * object is set
	 * @param control
	 * @param attribute
	 */
	protected void addDirtyOnTextModifyListener(Control control, EAttribute attribute) {

		IObservableValue mObs = EMFEditObservables.observeValue(Realm.getDefault(), this.editingDomain, getModelObject(), attribute);
		ISWTObservableValue uObs = SWTObservables.observeDelayedValue(100, SWTObservables.observeText(control, SWT.Modify));
		this.dataBindingContext.bindValue(uObs, mObs,null,null);
	}

	protected InformationUnit getModelObject() {
		return this.modelObject;
	}

	public void setModelObject(InformationUnit modelObject) {
		this.modelObject = modelObject;
	}

	public void setEditingDomain(AdapterFactoryEditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	public void setBindingContext(EMFDataBindingContext ctx) {
		this.dataBindingContext = ctx;

	}

}
