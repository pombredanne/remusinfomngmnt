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
import org.eclipse.remus.CalendarEntryType;

import org.remus.infomngmnt.calendar.model.TaskType;
import org.remus.infomngmnt.ui.calendar.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarEntryTypeStrings {

	public static String getStringByCalendarEntryType(final CalendarEntryType type) {
		switch (type) {
		case ONE_TIME:
			return Messages.CalendarEntryTypeStrings_OneTime;
		case WEEKLY:
			return Messages.CalendarEntryTypeStrings_Weekly;
		case TWO_WEEK:
			return Messages.CalendarEntryTypeStrings_TwoWeek;
		case MONTHLY:
			return Messages.CalendarEntryTypeStrings_Monthly;
		case ANNUAL:
			return Messages.CalendarEntryTypeStrings_Annual;
		default:
			return ""; //$NON-NLS-1$
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

	public static CalendarEntryType convert(final TaskType type) {
		switch (type) {
		case ONE_TIME:
			return CalendarEntryType.ONE_TIME;
		case WEEKLY:
			return CalendarEntryType.WEEKLY;
		case TWO_WEEK:
			return CalendarEntryType.TWO_WEEK;
		case MONTHLY:
			return CalendarEntryType.MONTHLY;
		case ANNUAL:
			return CalendarEntryType.ANNUAL;
		default:
			return CalendarEntryType.ONE_TIME;
		}
	}

	public static TaskType convert(final CalendarEntryType type) {
		switch (type) {
		case ONE_TIME:
			return TaskType.ONE_TIME;
		case WEEKLY:
			return TaskType.WEEKLY;
		case TWO_WEEK:
			return TaskType.TWO_WEEK;
		case MONTHLY:
			return TaskType.MONTHLY;
		case ANNUAL:
			return TaskType.ANNUAL;
		default:
			return TaskType.ONE_TIME;
		}
	}

	public static Date getNextEndDate(final TimeSpan ts, final Date newStartDate) {
		return new TimeSpan(newStartDate, ts.getDuration()).getEndDate();
	}
}
