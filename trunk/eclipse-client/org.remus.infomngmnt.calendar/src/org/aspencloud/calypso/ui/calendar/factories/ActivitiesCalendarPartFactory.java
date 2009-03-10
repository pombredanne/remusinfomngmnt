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

import org.aspencloud.calypso.ui.calendar.activities.ActivityPart;
import org.aspencloud.calypso.ui.calendar.activitiesCalendar.ActivitiesCalendarPart;
import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarModel;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.remus.infomngmnt.calendar.model.Task;

public class ActivitiesCalendarPartFactory implements EditPartFactory {
	
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;
		if(model instanceof Task) {
			part = new ActivityPart();
		} else if(model instanceof TasksCalendarModel) {
			part = new ActivitiesCalendarPart();
		}
		if(part == null) {
			int i = 0;
			i++;
		}
		part.setModel(model);
		return part;
	}
}