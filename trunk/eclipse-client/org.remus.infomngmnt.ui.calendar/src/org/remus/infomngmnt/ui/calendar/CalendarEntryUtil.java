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

import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.remus.CalendarEntry;
import org.eclipse.remus.CalendarEntryType;
import org.eclipse.remus.InfomngmntFactory;

import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.ui.calendar.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarEntryUtil {

	static DateFormat sdf = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.FULL);

	public static String setFormTextRepresentation(final CalendarEntry entry,
			final boolean printEvents) {

		StringWriter sw = new StringWriter();
		if (entry.getReminder() >= 0) {
			sw.append("<img href=\"alarm\"/> "); //$NON-NLS-1$
		}
		if (entry.getEntryType() != CalendarEntryType.ONE_TIME) {

			sw.append("<img href=\"refresh\"/> "); //$NON-NLS-1$
		}

		sw.append("\u2007"); //$NON-NLS-1$
		sw.append("<a href=\"").append(entry.getId()).append("\">"); //$NON-NLS-1$ //$NON-NLS-2$
		sw.append(StringEscapeUtils.escapeXml(entry.getTitle()));
		sw.append("</a>"); //$NON-NLS-1$
		if (entry.getEntryType() != CalendarEntryType.ONE_TIME) {
			sw.append(" ("); //$NON-NLS-1$
			sw.append(StringEscapeUtils.escapeXml(CalendarEntryTypeStrings
					.getStringByCalendarEntryType(entry.getEntryType())));
			sw.append(")"); //$NON-NLS-1$
		}
		sw.append("<br />"); //$NON-NLS-1$
		if (printEvents) {
			sw.append(Messages.CalendarEntryUtil_from);
			Date nextStartDate = CalendarEntryTypeStrings.getNextStartDate(new Date(), entry
					.getEntryType(), entry.getStart());
			sw.append(sdf.format(nextStartDate));
			sw.append("<br />"); //$NON-NLS-1$

			sw.append(Messages.CalendarEntryUtil_to);
			sw.append(sdf.format(CalendarEntryTypeStrings.getNextEndDate(new TimeSpan(entry
					.getStart(), entry.getEnd()), nextStartDate)));
			sw.append("<br />"); //$NON-NLS-1$
		}
		return sw.toString();
	}

	public static String setFormTextRepresentation(final Task entry, final boolean printEvents) {
		return setFormTextRepresentation(entry, printEvents, false);
	}

	public static String setFormTextRepresentation(final Task entry, final boolean printEvents,
			final boolean keepId) {
		CalendarEntry calendarEntry = convert(entry, keepId);
		return setFormTextRepresentation(calendarEntry, printEvents);
	}

	private static CalendarEntry convert(final Task entry, final boolean keepId) {
		CalendarEntry calendarEntry = InfomngmntFactory.eINSTANCE.createCalendarEntry();
		calendarEntry.setStart(entry.getStart().getDate());
		calendarEntry.setEnd(entry.getEnd().getDate());
		calendarEntry.setReminder(entry.getNotification());
		calendarEntry.setTitle(entry.getName());
		if (!keepId) {
			calendarEntry.setId(entry.getId().split("_")[1]); //$NON-NLS-1$
		} else {
			calendarEntry.setId(entry.getId());
		}
		calendarEntry.setEntryType(CalendarEntryTypeStrings.convert(entry.getType()));
		return calendarEntry;
	}

}
