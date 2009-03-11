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

import java.util.Collection;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.edit.EMFEditObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Combo;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ComboBindingWidget extends AbstractBindingWidget {

	private WritableList input;

	@SuppressWarnings("unchecked")
	public void setInput(final Collection input) {
		this.input = new WritableList(null);
		input.addAll(input);
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
			final UpdateValueStrategy target2Model, final UpdateValueStrategy model2target) {
		ComboViewer ccViewer = new ComboViewer((Combo) getWrappedControl());
		if (this.input == null) {
			throw new IllegalStateException("Input is not set. Set the input via #setInput()");
		}
		ccViewer.setContentProvider(new ObservableListContentProvider());
		ccViewer.setLabelProvider(new LabelProvider());
		ccViewer.setInput(this.input);
		ISWTObservableValue swtType = SWTObservables.observeText(getWrappedControl());
		IObservableValue emfType = EMFEditObservables.observeValue(getEditingDomain(), object,
				feature);
		setBinding(getBindingContext().bindValue(swtType, emfType, model2target, target2Model));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.common.ui.databinding.AbstractBindingWidget#
	 * updateReadOnly()
	 */
	@Override
	protected void updateReadOnly() {
		getWrappedControl().setEnabled(isReadonly());
	}

}
