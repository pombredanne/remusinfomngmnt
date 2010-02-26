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

import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.nebula.widgets.calendarcombo.CalendarCombo;
import org.eclipse.nebula.widgets.calendarcombo.DateHelper;
import org.eclipse.nebula.widgets.calendarcombo.ISettings;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarComboTargetToModel extends UpdateValueStrategy {

	private final CalendarCombo widget;
	private final ISettings settings;

	public CalendarComboTargetToModel(CalendarCombo widget, ISettings settings) {
		this.widget = widget;
		this.settings = settings;

	}

	@Override
	public Object convert(Object value) {
		try {
			return DateHelper.getDate((String)value, this.settings.getDateFormat());
		} catch (Exception e) {
			// do nothing.. date is not parsable.
		}
		return null;
	}

}
