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

package org.remus.infomngmnt.ui.databinding;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Control;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractBindingWidget {

	private EditingDomain editingDomain;

	private Binding binding;

	private EMFDataBindingContext bindingContext;

	private Control wrappedControl;

	private final MultiValidator target2ModelValidators;

	private boolean mandatory;

	private boolean readonly;

	public AbstractBindingWidget() {
		this.target2ModelValidators = new MultiValidator();
	}

	/**
	 * @return the editingDomain
	 */
	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	/**
	 * @param editingDomain
	 *            the editingDomain to set
	 */
	public void setEditingDomain(final EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

	/**
	 * @return the bindingContext
	 */
	public EMFDataBindingContext getBindingContext() {
		return this.bindingContext;
	}

	/**
	 * @param bindingContext
	 *            the bindingContext to set
	 */
	public void setBindingContext(final EMFDataBindingContext bindingContext) {
		this.bindingContext = bindingContext;
	}

	/**
	 * @return the wrappedControl
	 */
	public Control getWrappedControl() {
		return this.wrappedControl;
	}

	/**
	 * @param wrappedControl
	 *            the wrappedControl to set
	 */
	public void setWrappedControl(final Control wrappedControl) {
		this.wrappedControl = wrappedControl;
	}

	public void addValidator(final IValidator validator) {
		this.target2ModelValidators.add(validator);
	}

	/**
	 * @return the target2ModelValidators
	 */
	public IValidator getTarget2ModelValidators() {
		return this.target2ModelValidators;
	}

	/**
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return this.mandatory;
	}

	/**
	 * @param mandatory
	 *            the mandatory to set
	 */
	public void setMandatory(final boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * @return the readonly
	 */
	public boolean isReadonly() {
		return this.readonly;
	}

	/**
	 * @param readonly
	 *            the readonly to set
	 */
	public void setReadonly(final boolean readonly) {
		if (readonly != this.readonly) {
			this.readonly = readonly;
			updateReadOnly();
		}
	}

	protected void updateReadOnly() {
		getWrappedControl().setEnabled(isReadonly());
	}

	public void bindModel(final EObject object, final EStructuralFeature feature) {
		bindModel(object, feature, null, null);
	}

	public abstract void bindModel(EObject object, EStructuralFeature feature,
			UpdateValueStrategy target2Model, UpdateValueStrategy model2target);

	/**
	 * @return the binding
	 */
	public Binding getBinding() {
		return this.binding;
	}

	/**
	 * @param binding
	 *            the binding to set
	 */
	protected void setBinding(final Binding binding) {
		this.binding = binding;
	}

}
