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

package org.remus.infomngmnt.common.ui.databinding;

import java.util.Map;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Button;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RadioButtonGroupEnumBinding {

	private final Map<Button, Enumerator> buttonsMap;
	private final EMFDataBindingContext ctx;

	public RadioButtonGroupEnumBinding(
			Map<Button, Enumerator> buttons,
			EMFDataBindingContext ctx) {
		this.buttonsMap = buttons;
		this.ctx = ctx;

	}

	public void bind(IObservableValue observableValue) {
		for (final Map.Entry<Button, Enumerator> element : this.buttonsMap.entrySet()) {
			Button button = element.getKey();
			ISWTObservableValue observeSelection = SWTObservables.observeSelection(button);
			this.ctx.bindValue(observableValue, observeSelection, new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return super.convert(value);
				}
			}, new UpdateValueStrategy() {
				@Override
				public Object convert(Object value) {
					return element.getValue();
				}
			});

		}
	}

	public void setDefault(Enumerator value) {
		for (final Map.Entry<Button, Enumerator> element : this.buttonsMap.entrySet()) {
			if (element.getValue().equals(value)) {
				element.getKey().setSelection(true);
			} else {
				element.getKey().setSelection(false);
			}
		}
	}

}
