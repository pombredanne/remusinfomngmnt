/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.connector.googlecalendar;

import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.meetingminutes.MeetingMinutesActivator;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.EventWho;
import com.google.gdata.data.extensions.Recurrence;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarConverter {

	public static final String ORGANIZER_REL = "http://schemas.google.com/g/2005#event.organizer"; //$NON-NLS-1$
	public static final String ATTENDEE_REL = "http://schemas.google.com/g/2005#event.attendee"; //$NON-NLS-1$

	public InformationUnit fromRepo(final CalendarEventEntry wrappedObject) {
		InformationStructureEdit edit = InformationStructureEdit
				.newSession(MeetingMinutesActivator.INFO_TYPE_ID);
		InformationUnit newInformationUnit = edit.newInformationUnit();
		edit.setValue(newInformationUnit, "@label", wrappedObject.getTitle().getPlainText());
		if (wrappedObject.getTextContent() != null) {
			edit.setValue(newInformationUnit, "@description", wrappedObject.getTextContent()
					.getContent().getPlainText());
		}
		List<Where> locations = wrappedObject.getLocations();
		if (locations.size() > 0) {
			Where where = locations.get(0);
			edit.setValue(newInformationUnit, MeetingMinutesActivator.NODE_NAME_PLACE, where
					.getValueString());

		}
		List<EventWho> participants = wrappedObject.getParticipants();
		for (EventWho eventWho : participants) {
			if (eventWho.getRel() != null && eventWho.getRel().endsWith("organizer")) {
				edit.setValue(newInformationUnit, MeetingMinutesActivator.NODE_NAME_MODERATOR,
						eventWho.getEmail());
			} else {
				InformationUnit attendee = edit.createSubType(
						MeetingMinutesActivator.NODE_NAME_ATTENDEE, eventWho.getEmail());
				edit.addDynamicNode(newInformationUnit, attendee, null);
			}
		}
		List<When> times = wrappedObject.getTimes();
		if (times.size() > 0) {
			Date startDate = new Date(times.get(0).getStartTime().getValue());
			Date endDate = new Date(times.get(0).getEndTime().getValue());
			edit
					.setValue(newInformationUnit, MeetingMinutesActivator.NODE_NAME_DATETIME,
							startDate);
			edit.setValue(newInformationUnit, MeetingMinutesActivator.NODE_NAME_END_DATETIME,
					endDate);
		}
		if (wrappedObject.getRecurrence() != null) {
			edit.setValue(newInformationUnit, MeetingMinutesActivator.NODE_NAME_REPEAT,
					wrappedObject.getRecurrence().getValue());
		}
		return newInformationUnit;
	}

	public CalendarEventEntry toRepo(final InformationUnitListItem element,
			final CalendarEventEntry entry) {
		InformationUnit unit = (InformationUnit) element.getAdapter(InformationUnit.class);
		InformationStructureRead read = InformationStructureRead.newSession(unit);
		entry.setTitle(TextConstruct.plainText((String) read.getValueByNodeId("@label")));
		entry.setContent(TextConstruct.plainText((String) read.getValueByNodeId("@description")));
		entry.getLocations().clear();
		entry.getLocations().add(
				new Where(null, null, (String) read
						.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_PLACE)));

		entry.getTimes().clear();
		When when = new When();
		when.setStartTime(new DateTime((Date) read
				.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_DATETIME)));
		when.setEndTime(new DateTime((Date) read
				.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_END_DATETIME)));
		entry.getTimes().add(when);
		String moderator = (String) read
				.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_MODERATOR);
		entry.getParticipants().clear();
		if (moderator != null && moderator.trim().length() > 0) {
			EventWho eventWho = new EventWho();
			eventWho.setRel(ORGANIZER_REL);
			eventWho.setEmail(moderator);
			entry.getParticipants().add(eventWho);
		}
		EList<InformationUnit> dynamicList = read
				.getDynamicList(MeetingMinutesActivator.NODE_NAME_ATTENDEES);
		for (InformationUnit informationUnit : dynamicList) {
			InformationStructureRead attendeeRead = InformationStructureRead.newSession(
					informationUnit, unit.getType());
			String attendee = (String) attendeeRead
					.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_ATTENDEE);
			EventWho eventWho = new EventWho();
			eventWho.setRel(ATTENDEE_REL);
			eventWho.setEmail(attendee);
			entry.getParticipants().add(eventWho);
		}
		String recurrence = (String) read
				.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_REPEAT);
		if (recurrence != null) {
			Recurrence recurrence2 = new Recurrence();
			recurrence2.setValue(recurrence);
			entry.setRecurrence(recurrence2);
		}
		return entry;
	}

}
