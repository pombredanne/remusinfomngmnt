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
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.nebula.widgets.calendarcombo.DefaultSettings;
import org.eclipse.nebula.widgets.calendarcombo.ISettings;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarComboBindingWidget extends AbstractBindingWidget {
	private final ISettings settings = new DefaultSettings();

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
		// from-date
		IObservableValue mObsFrom = EMFEditObservables.observeValue(getEditingDomain(), object,
				feature);
		ISWTObservableValue uObsFrom = SWTObservables
				.observeText(((CalendarCombo) getWrappedControl()).getCombo());
		setBinding(getBindingContext().bindValue(uObsFrom, mObsFrom,
				new CalendarComboTargetToModel((CalendarCombo) getWrappedControl(), this.settings),
				new CalendarComboModelToTarget((CalendarCombo) getWrappedControl(), this.settings)));

	}

}
