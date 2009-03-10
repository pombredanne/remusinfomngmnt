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

public class ShowGridAction extends Action {
	public static final String ID = "org.aspencloud.calypso.ui.views.Calendar.actions.ShowGridAction";

	private GraphicalViewer[] viewers;
	
	public ShowGridAction(GraphicalViewer[] viewers) {
		super("Grid", SWT.CHECK);
		this.viewers = viewers;
		setToolTipText("Toggle Grid");
//		setImageDescriptor(UiPlugin.getImageDescriptor(ImageIds.IMG_ADD));
		setId(ID);
		setChecked(true);
		run();
	}

	@Override
	public void run() {
		if(isChecked()) {
			for(int i = 0; i < viewers.length; i++) {
				((TasksCalendarFigure) ((GraphicalEditPart) viewers[i].getContents()).getFigure()).setGridVisible(true);
			}
		} else {
			for(int i = 0; i < viewers.length; i++) {
				((TasksCalendarFigure) ((GraphicalEditPart) viewers[i].getContents()).getFigure()).setGridVisible(false);
			}
		}
	}
}
