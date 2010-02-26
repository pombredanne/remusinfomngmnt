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

package org.remus.infomngmnt.common.ui.databinding;

import org.eclipse.core.databinding.Binding;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TextBindingWidget extends AbstractBindingWidget {

	TextBindingWidget() {
		// prevents instantations
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.databinding.AbstractBindingWidget#bindModel
	 * (org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature,
	 * org.eclipse.core.databinding.UpdateValueStrategy,
	 * org.eclipse.core.databinding.UpdateValueStrategy)
	 */
	@Override
	public void bindModel(final EObject object, final EStructuralFeature feature,
			UpdateValueStrategy target2Model, final UpdateValueStrategy model2target) {
		ISWTObservableValue textObservable = SWTObservables.observeDelayedValue(500, SWTObservables
				.observeText(getWrappedControl(), SWT.Modify));
		IObservableValue observeValue = EMFEditObservables.observeValue(getEditingDomain(), object,
				feature);
		if (target2Model == null) {
			target2Model = new EMFUpdateValueStrategy();
		}
		target2Model.setAfterConvertValidator(getTarget2ModelValidators());
		Binding bindValue = getBindingContext().bindValue(textObservable, observeValue,
				target2Model, model2target);
		setBinding(bindValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.common.ui.databinding.AbstractBindingWidget#
	 * updateReadOnly()
	 */
	@Override
	protected void updateReadOnly() {
		((Text) getWrappedControl()).setEditable(isReadonly());
	}

}
