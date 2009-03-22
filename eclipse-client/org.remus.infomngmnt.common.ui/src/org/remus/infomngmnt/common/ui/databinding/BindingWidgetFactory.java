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

import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.common.ui.richtext.RichTextWidget;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class BindingWidgetFactory {

	/**
	 * Returns a binding control for a wrapped text widget. The widget can be
	 * bound to a {@link EStructuralFeature} and is listen for Modify events.
	 * 
	 * @param text
	 *            the wrapped text widget.
	 * @param ctx
	 *            the DatabindingContext
	 * @param domain
	 *            the EditingDomain
	 * @return a Binding-Control
	 */
	public static TextBindingWidget createTextBinding(final Text text,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		TextBindingWidget textWidget = new TextBindingWidget();
		initControl(textWidget, ctx, domain, text);
		return textWidget;
	}

	public static TextBindingWidget createTextBindingWidget(final Text text,
			final IEMFEditBindingProvider provider) {
		return createTextBinding(text, provider.getDatabindingContext(), provider
				.getEditingDomain());
	}

	public static ComboBindingWidget createComboBinding(final Combo combo,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		ComboBindingWidget bindingWidget = new ComboBindingWidget();
		initControl(bindingWidget, ctx, domain, combo);
		return bindingWidget;
	}

	public static ComboBindingWidget createComboBinding(final Combo combo,
			final IEMFEditBindingProvider provider) {
		return createComboBinding(combo, provider.getDatabindingContext(), provider
				.getEditingDomain());
	}

	public static StyledTextBindingWidget createStyledText(final StyledText text,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		StyledTextBindingWidget bindingWidget = new StyledTextBindingWidget();
		initControl(bindingWidget, ctx, domain, text);
		return bindingWidget;
	}

	public static StyledTextBindingWidget createStyledText(final StyledText text,
			final IEMFEditBindingProvider provider) {
		return createStyledText(text, provider.getDatabindingContext(), provider.getEditingDomain());

	}

	public static SpinnerBindingWidget createSpinner(final Spinner spinner,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		SpinnerBindingWidget bindingWidget = new SpinnerBindingWidget();
		initControl(bindingWidget, ctx, domain, spinner);
		return bindingWidget;
	}

	public static RichTextBindingWidget createRichText(final RichTextWidget control,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		RichTextBindingWidget bindingWidget = new RichTextBindingWidget();
		initControl(bindingWidget, ctx, domain, control);
		return bindingWidget;
	}

	public static RichTextBindingWidget createRichText(final RichTextWidget control,
			final IEMFEditBindingProvider provider) {
		return createRichText(control, provider.getDatabindingContext(), provider
				.getEditingDomain());
	}

	private static void initControl(final AbstractBindingWidget widget,
			final EMFDataBindingContext ctx, final EditingDomain domain, final Control control) {
		widget.setBindingContext(ctx);
		widget.setEditingDomain(domain);
		widget.setWrappedControl(control);
	}

}
