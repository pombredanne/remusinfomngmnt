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

import java.util.Date;

import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;
import org.remus.infomngmnt.ccalendar.service.ICalendarService;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.service.ICalendarStoreService;
import org.remus.infomngmnt.util.DisposableEditingDomain;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.IdFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CalendarServiceImpl implements ICalendarService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ccalendar.service.ICalendarService#createTask(java
	 * .sql.Date)
	 */
	public Task createTask(final Date startingTime, final Date endTime) {
		CalendarEntry calendarEntry = InfomngmntFactory.eINSTANCE.createCalendarEntry();
		calendarEntry.setId(IdFactory.createNewId(null));
		calendarEntry.setStart(startingTime);
		calendarEntry.setEnd(endTime);
		calendarEntry.setReminder(-1);
		DisposableEditingDomain editingDomain = EditingUtil.getInstance().createNewEditingDomain();
		NewCalendarEntryDialog dialog = new NewCalendarEntryDialog(UIUtil.getDisplay()
				.getActiveShell(), calendarEntry, editingDomain, null);
		dialog.open();
		editingDomain.dispose();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ccalendar.service.ICalendarService#deleteTask(org
	 * .remus.infomngmnt.calendar.model.Task)
	 */
	public void deleteTask(final Task task) {
		Job job = new Job("Deleting Task") {

			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				String id = task.getId();
				String[] split = id.split("_");
				String infoId = split[0];
				String entryId = split[1];
				InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
						infoId, monitor);
				InformationUnit adapter = (InformationUnit) itemById
						.getAdapter(InformationUnit.class);
				EObject itemByValue = ModelUtil.getItemByValue(adapter.getCalendarEntry(),
						InfomngmntPackage.Literals.CALENDAR_ENTRY__ID, entryId);
				if (itemByValue != null && itemByValue instanceof CalendarEntry) {
					adapter.getCalendarEntry().remove(itemByValue);
					EditingUtil.getInstance().saveObjectToResource(
							(IFile) adapter.getAdapter(IFile.class), adapter);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ccalendar.service.ICalendarService#updateTask(org
	 * .remus.infomngmnt.calendar.model.Task)
	 */
	public void updateTask(final Task task) {
		taskChanged(task);
	}

	public Tasklist getTasksForTimespan(final TimeSpan timespan) {
		ICalendarStoreService calendarStoreService = UIPlugin.getDefault().getService(
				ICalendarStoreService.class);
		return calendarStoreService.getItems(timespan);
	}

	public void taskChanged(final Task task) {
		Job job = new Job("Updating models") {

			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				String id = task.getId();
				String[] split = id.split("_");
				String infoId = split[0];
				String entryId = split[1];
				InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
						infoId, monitor);
				InformationUnit adapter = (InformationUnit) itemById
						.getAdapter(InformationUnit.class);
				EObject itemByValue = ModelUtil.getItemByValue(adapter.getCalendarEntry(),
						InfomngmntPackage.Literals.CALENDAR_ENTRY__ID, entryId);
				if (itemByValue != null && itemByValue instanceof CalendarEntry) {
					((CalendarEntry) itemByValue).setStart(task.getStart().getDate());
					((CalendarEntry) itemByValue).setEnd(task.getEnd().getDate());
					EditingUtil.getInstance().saveObjectToResource(
							(IFile) adapter.getAdapter(IFile.class), adapter);
				}
				return Status.OK_STATUS;
			}
		};
		job.schedule();

	}
}
