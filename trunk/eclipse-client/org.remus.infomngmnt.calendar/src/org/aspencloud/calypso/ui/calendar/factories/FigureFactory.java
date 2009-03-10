/****************************************************************************
* Copyright (c) 2005-2006 Jeremy Dowdall
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    Jeremy Dowdall <aspencloud@users.sourceforge.net> - initial API and implementation
*****************************************************************************/

package org.aspencloud.calypso.ui.calendar.factories;

import org.aspencloud.calypso.ui.calendar.activities.ActivityFigure;
import org.aspencloud.calypso.ui.calendar.tasks.TaskFigure;
import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarFigure;
import org.eclipse.draw2d.IFigure;


public class FigureFactory {

	public static IFigure createActivityFigure() {
		return new ActivityFigure();
	}

	public static IFigure createTaskFigure() {
		return new TaskFigure();
	}

	public static IFigure createTasksCalendarFigure() {
		return new TasksCalendarFigure();
	}
	
}