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

package org.remus.infomngmnt.ui.widgets.databinding;

import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.widgets.Control;

import org.remus.infomngmnt.ui.databinding.AbstractBindingWidget;
import org.remus.infomngmnt.ui.databinding.IEMFEditBindingProvider;
import org.remus.infomngmnt.ui.widgets.DateCombo;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AdditionalBindingWidgetFactory {

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
