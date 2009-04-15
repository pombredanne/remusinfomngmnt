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
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.aspencloud.calypso.util.TimeSpan;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.CalendarEntryType;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.calendar.model.Task;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarEntryUtil {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

	public static String setFormTextRepresentation(final CalendarEntry entry,
			final boolean printEvents) {

		StringWriter sw = new StringWriter();
		if (entry.getReminder() >= 0) {
			sw.append("<img href=\"alarm\"/> ");
		}
		if (entry.getEntryType() != CalendarEntryType.ONE_TIME) {

			sw.append("<img href=\"refresh\"/> ");
		}

		sw.append("\u2007");
		sw.append("<a href=\"").append(entry.getId()).append("\">");
		sw.append(StringEscapeUtils.escapeXml(entry.getTitle()));
		sw.append("</a>");
		if (entry.getEntryType() != CalendarEntryType.ONE_TIME) {
			sw.append(" (");
			sw.append(StringEscapeUtils.escapeXml(CalendarEntryTypeStrings
					.getStringByCalendarEntryType(entry.getEntryType())));
			sw.append(")");
		}
		sw.append("<br />");
		if (printEvents) {
			sw.append("<b>from:</b>\u2007");
			Date nextStartDate = CalendarEntryTypeStrings.getNextStartDate(new Date(), entry
					.getEntryType(), entry.getStart());
			sw.append(sdf.format(nextStartDate));
			sw.append("<br />");

			sw.append("<b>to:</b>\u2007");
			sw.append(sdf.format(CalendarEntryTypeStrings.getNextEndDate(new TimeSpan(entry
					.getStart(), entry.getEnd()), nextStartDate)));
			sw.append("<br />");
		}
		return sw.toString();
	}

	public static String setFormTextRepresentation(final Task entry, final boolean printEvents) {
		CalendarEntry calendarEntry = convert(entry);
		return setFormTextRepresentation(calendarEntry, printEvents);
	}

	private static CalendarEntry convert(final Task entry) {
		CalendarEntry calendarEntry = InfomngmntFactory.eINSTANCE.createCalendarEntry();
		calendarEntry.setStart(entry.getStart().getDate());
		calendarEntry.setEnd(entry.getEnd().getDate());
		calendarEntry.setReminder(entry.getNotification());
		calendarEntry.setTitle(entry.getName());
		calendarEntry.setId(entry.getId().split("_")[1]);
		calendarEntry.setEntryType(CalendarEntryTypeStrings.convert(entry.getType()));
		return calendarEntry;
	}

}
