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

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.BeansObservables;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class BeansBindingUtil {

	private static UpdateValueStrategy DEFAULT_UVSTRATEGIE = new UpdateValueStrategy();

	public static void bindText(final Text text, final Object object, final String propertyName,
			final DataBindingContext ctx) {
		IObservableValue observeValue = BeansObservables.observeValue(object, propertyName);
		ISWTObservableValue observeText = SWTObservables.observeText(text, SWT.Modify);
		ctx.bindValue(observeText, observeValue, new UpdateValueStrategy(),
				new UpdateValueStrategy());
	}

	public static void bindButton(final Button button, final Object object,
			final String propertyName, final DataBindingContext ctx) {
		IObservableValue observeValue = BeansObservables.observeValue(object, propertyName);
		ISWTObservableValue observeSelection = SWTObservables.observeSelection(button);
		ctx.bindValue(observeSelection, observeValue, null, DEFAULT_UVSTRATEGIE);
	}

}
