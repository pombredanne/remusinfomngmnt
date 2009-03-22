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

package org.aspencloud.calypso.ui.workbench.views.calendar.actions;

import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.ccalendar.CCalendar;

public class ShowGridAction extends Action {
	public static final String ID = "org.aspencloud.calypso.ui.views.Calendar.actions.ShowGridAction";

	private CCalendar calendar;

	public ShowGridAction() {
		super("Grid", SWT.CHECK);
		setToolTipText("Toggle Grid");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(CCalendar.PLUGIN_ID,
				"icons/iconexperience/16/table.png"));
		setId(ID);
		setChecked(true);
	}

	public void setViewers(final CCalendar calendar) {
		this.calendar = calendar;
	}

	@Override
	public void run() {
		GraphicalViewer[] viewers = new GraphicalViewer[] { this.calendar.getTasksViewer(),
				this.calendar.getActivitiesViewer() };
		if (isChecked()) {
			for (int i = 0; i < viewers.length; i++) {
				((TasksCalendarFigure) ((GraphicalEditPart) viewers[i].getContents()).getFigure())
						.setGridVisible(true);
			}
		} else {
			for (int i = 0; i < viewers.length; i++) {
				((TasksCalendarFigure) ((GraphicalEditPart) viewers[i].getContents()).getFigure())
						.setGridVisible(false);
			}
		}
	}
}
