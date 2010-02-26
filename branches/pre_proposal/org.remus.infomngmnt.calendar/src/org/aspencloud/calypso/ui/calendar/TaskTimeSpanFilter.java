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

package org.aspencloud.calypso.ui.calendar;


import org.aspencloud.calypso.util.CTimeSpan;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.remus.infomngmnt.calendar.model.EndEvent;
import org.remus.infomngmnt.calendar.model.StartEvent;
import org.remus.infomngmnt.calendar.model.Task;

public class TaskTimeSpanFilter extends ViewerFilter {

	private CTimeSpan ts;
	private boolean allowNonTasks = false;
	
	public TaskTimeSpanFilter() {
	}
	
	public CTimeSpan getTimeSpan() {
		return this.ts;
	}
	
	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if(this.ts == null) {
			return true;
		}
		if(!(element instanceof Task)) {
			return this.allowNonTasks;
		}
		
		Task task = (Task) element;
		StartEvent start = task.getStart();
		EndEvent end = task.getEnd();
		if((start != null) && (end != null)) {
			return this.ts.overlaps(start.getDate(), end.getDate());
		}
		return false;
	}
	
	public void setAllowNonTasks(final boolean tasksOnly) {
		this.allowNonTasks = tasksOnly;
	}
	public void setTimeSpan(final CTimeSpan ts) {
		this.ts = ts;
	}
}