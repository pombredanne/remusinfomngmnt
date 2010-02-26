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

package org.aspencloud.calypso.ui.calendar.filters;


import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.remus.infomngmnt.calendar.model.Task;


public class AllDayMultiDayTaskFilter extends ViewerFilter {

	private static TimeSpan ts = new TimeSpan();

	private boolean pass;
	
	public AllDayMultiDayTaskFilter(boolean pass) {
		this.pass = pass;
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof Task) {
			Task task = (Task) element;
			if(task.getStart() != null && task.getEnd() != null) {
				ts.setTimeSpan(task.getStart().getDate(), task.getEnd().getDate());
				if(!ts.isMultiDay() && !ts.isAllDay()) {
					return pass;
				} else {
					return !pass;
				}
			}
		}
		return false;
	}

}
