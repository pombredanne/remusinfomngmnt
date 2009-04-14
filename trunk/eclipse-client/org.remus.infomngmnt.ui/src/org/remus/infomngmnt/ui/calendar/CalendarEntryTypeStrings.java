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

package org.remus.infomngmnt.ui.calendar;

import java.util.Calendar;
import java.util.Date;

import org.aspencloud.calypso.util.TimeSpan;

import org.remus.infomngmnt.CalendarEntryType;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarEntryTypeStrings {

	public static String getStringByCalendarEntryType(final CalendarEntryType type) {
		switch (type) {
		case ONE_TIME:
			return "one-time";
		case WEEKLY:
			return "weekly";
		case TWO_WEEK:
			return "two-week";
		case MONTHLY:
			return "monthly";
		case ANNUAL:
			return "annual";
		default:
			return "";
		}
	}

	public static Date getNextStartDate(final Date refDate, final CalendarEntryType type,
			final Date originalDate) {
		if (originalDate.compareTo(refDate) > 0) {
			return originalDate;
		}
		Calendar instance = Calendar.getInstance();
		instance.setTime(originalDate);
		TimeSpan ts = new TimeSpan(originalDate, refDate);
		switch (type) {
		case ONE_TIME:
			return originalDate;
		case WEEKLY:
			instance.add(Calendar.WEEK_OF_MONTH, 1 + ts.getWeekDifference());
			return instance.getTime();
		case TWO_WEEK:
			instance.add(Calendar.WEEK_OF_MONTH, 2 + ts.getWeekDifference() / 2);
			return instance.getTime();
		case MONTHLY:
			instance.add(Calendar.MONTH, 1 + ts.getMonthDifference());
			return instance.getTime();
		default:
			break;
		}
		return originalDate;
	}

	public static Date getNextEndDate(final TimeSpan ts, final Date newStartDate) {
		return new TimeSpan(newStartDate, ts.getDuration()).getEndDate();
	}
}
