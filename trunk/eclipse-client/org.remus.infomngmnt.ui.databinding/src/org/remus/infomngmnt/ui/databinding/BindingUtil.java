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

import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.emf.databinding.EMFUpdateValueStrategy;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.widgets.Text;

/**
 * A Utility class for binding ui widgets to an emf model
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class BindingUtil {

	/**
	 * The shortest way to create a binding.
	 * 
	 * @param text
	 * @param object
	 * @param feature
	 * @param provider
	 * @return
	 */
	public static TextBindingWidget createTextAndBind(final Text text, final EObject object,
			final EStructuralFeature feature, final IEMFEditBindingProvider provider) {
		TextBindingWidget createTextBinding = BindingWidgetFactory.createTextBinding(text, provider
				.getDatabindingContext(), provider.getEditingDomain());
		createTextBinding.bindModel(object, feature);
		return createTextBinding;
	}

	public static EMFUpdateValueStrategy createUpdateStratyWithConverter(final IConverter converter) {
		return (EMFUpdateValueStrategy) new EMFUpdateValueStrategy().setConverter(converter);
	}

}
