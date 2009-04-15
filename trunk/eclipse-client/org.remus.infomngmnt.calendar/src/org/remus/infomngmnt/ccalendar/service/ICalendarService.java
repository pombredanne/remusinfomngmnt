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

package org.remus.infomngmnt.ccalendar.service;

import java.util.Date;

import org.aspencloud.calypso.ui.workbench.views.calendar.actions.CreateAction;
import org.aspencloud.calypso.util.TimeSpan;

import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;

/**
 * <p>
 * Interface which will be accessed via OSGi-Service Registry where other
 * components can provide logic or ui for the creation/updating and deletion of
 * tasks.
 * </p>
 * <p>
 * The calendar component will ask always {@link #getTasksForTimespan(TimeSpan)}
 * if an event, see {@link IDirtyTimespanListener} was thrown that timespan is
 * within the visible area of the component. The other methods are for
 * implementing logic when it comes to user-interaction within this component.
 * </p>
 * <p>
 * <b> You have to provide an implementation if you want to use the calendar
 * component! </b>
 * <p>
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 * @noextend This interface is not intended to be extended by clients.
 */
public interface ICalendarService {

	/**
	 * Called after the user has dragged with the New-creation Tool, see
	 * {@link CreateAction} a new rectangle.
	 * 
	 * @param startingTime
	 *            the drag start time
	 * @param endDate
	 *            the drag end time
	 * @return returns the new task. This is just for furhter usage, at the
	 *         moment the returnvalue is not evaluated.
	 */
	Task createTask(Date startingTime, Date endDate);

	/**
	 * Called if the user deletes a selected task in the component-
	 * 
	 * @param task
	 *            the task to delete.
	 */
	void deleteTask(Task task);

	/**
	 * Updates a task. Typically if the user has moved the task or changed with
	 * drag-n-drop the start or end-date.
	 * 
	 * @param task
	 *            the task that changed.
	 */
	void updateTask(Task task);

	/**
	 * This method is used to get the initial input based on a given timespan
	 * which is visible in the control. On changing the visible {@link TimeSpan}
	 * this method has to return the associated tasks.
	 * 
	 * @param timespan
	 *            the relevent timespan
	 * @return a list of tasks which will be displayed in the ui.
	 */
	Tasklist getTasksForTimespan(TimeSpan timespan);

}
