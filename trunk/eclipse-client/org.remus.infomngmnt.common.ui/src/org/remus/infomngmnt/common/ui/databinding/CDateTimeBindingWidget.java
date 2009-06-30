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

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.databinding.swt.ISWTObservable;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CDateTimeBindingWidget extends AbstractBindingWidget {

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
			final UpdateValueStrategy target2Model, final UpdateValueStrategy model2target) {

		IObservableValue observableValue = new CDateTimeObservableValue().observe((CDateTime) getWrappedControl());
		IObservableValue emfObserveValue = EMFEditObservables.observeValue(getEditingDomain(),
				object, feature);
		setBinding(getBindingContext().bindValue(observableValue, emfObserveValue, target2Model,
				model2target));

	}

}
