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
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.common.ui.richtext.RichTextWidget;
import org.remus.infomngmnt.common.ui.swt.DateCombo;

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

	public static TextBindingWidget createTextBinding(final Text text,
			final IEMFEditBindingProvider provider) {
		return createTextBinding(text, provider.getDatabindingContext(), provider
				.getEditingDomain());
	}
	
	public static LabelBindingWidget createLabelBinding(final Label label,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		LabelBindingWidget labelWidget = new LabelBindingWidget();
		initControl(labelWidget, ctx, domain, label);
		return labelWidget;
	}
	
	public static LabelBindingWidget createTextBinding(final Label label,
			final IEMFEditBindingProvider provider) {
		return createLabelBinding(label, provider.getDatabindingContext(), provider
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

	public static SpinnerSliderBindingWidget createSpinner(final Spinner spinner,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		SpinnerSliderBindingWidget bindingWidget = new SpinnerSliderBindingWidget();
		initControl(bindingWidget, ctx, domain, spinner);
		return bindingWidget;
	}

	public static SpinnerSliderBindingWidget createSpinner(final Slider slider,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		SpinnerSliderBindingWidget bindingWidget = new SpinnerSliderBindingWidget();
		initControl(bindingWidget, ctx, domain, slider);
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

	public static CalendarComboBindingWidget createDateCombo(final CalendarCombo control,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		CalendarComboBindingWidget bindingWidget = new CalendarComboBindingWidget();
		initControl(bindingWidget, ctx, domain, control);
		return bindingWidget;
	}

	public static CDateTimeBindingWidget createCDateTime(final CDateTime control,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		CDateTimeBindingWidget bindingWidget = new CDateTimeBindingWidget();
		initControl(bindingWidget, ctx, domain, control);
		return bindingWidget;
	}

	public static DatePickerBindingWidget createDateComboBinding(final DateCombo combo,
			final IEMFEditBindingProvider provider) {
		return createDateComboBinding(combo, provider.getDatabindingContext(), provider
				.getEditingDomain());

	}

	public static CheckBoxBindingWidget createCheckboxBinding(final Button control,
			final IEMFEditBindingProvider provider) {
		return createCheckboxBinding(control, provider.getDatabindingContext(), provider
				.getEditingDomain());

	}

	public static CheckBoxBindingWidget createCheckboxBinding(final Button control,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		CheckBoxBindingWidget bindingWidget = new CheckBoxBindingWidget();
		initControl(bindingWidget, ctx, domain, control);
		return bindingWidget;
	}

	public static DatePickerBindingWidget createDateComboBinding(final DateCombo combo,
			final EMFDataBindingContext ctx, final EditingDomain domain) {
		DatePickerBindingWidget bindingWidget = new DatePickerBindingWidget();
		initControl(bindingWidget, ctx, domain, combo);
		return bindingWidget;
	}

	private static void initControl(final AbstractBindingWidget widget,
			final EMFDataBindingContext ctx, final EditingDomain domain, final Control control) {
		widget.setBindingContext(ctx);
		widget.setEditingDomain(domain);
		widget.setWrappedControl(control);
	}

}
