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

package org.remus.infomngmnt.ui.widgets.databinding;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.nebula.widgets.calendarcombo.ISettings;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarComboModelToTarget extends UpdateValueStrategy {

	private final CalendarCombo widget;
	private final ISettings settings;

	public CalendarComboModelToTarget(CalendarCombo widget, ISettings settings) {
		this.widget = widget;
		this.settings = settings;
	}

	@Override
	public Object convert(Object value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime((Date) value);
		this.widget.setDate(calendar);
		return super.convert(value);
	}

}
