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

import org.aspencloud.calypso.util.TimeSpan;

import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;

/**
 * Interface which will be accessed via OSGi-Service Registry where other
 * components can provide logic or ui for the creation/updating and deletion of
 * tasks.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ICalendarService {

	Task createTask(Date startingTime);

	void deleteTask(Task task);

	void updateTask(Task task);

	void taskChanged(Task task);

	Tasklist getTasksForTimespan(TimeSpan timespan);

}
