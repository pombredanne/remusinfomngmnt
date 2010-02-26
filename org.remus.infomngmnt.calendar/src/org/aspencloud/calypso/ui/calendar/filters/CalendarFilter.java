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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aspencloud.calypso.util.CTimeSpan;
import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.remus.infomngmnt.calendar.model.Task;

public class CalendarFilter extends ViewerFilter {

	private final List orList = new ArrayList();
	private final List andList = new ArrayList();
	private boolean allowNonTasks = false;


//	private class SearchCriteria {
//		protected String className;
//		protected String featureName;
//		protected Object value;
//		
//		protected SearchCriteria(String className, String featureName, Object value) {
//			this.className = className;
//			this.featureName = featureName;
//			this.value = value;
//		}
//	}
	
	public CalendarFilter() {
	}
	public CalendarFilter(final TimeSpan timeSpan) {
		this.orList.add(timeSpan);
	}
	
	public boolean selectTask(final Task task, final TimeSpan timeSpan) {
		if(task.getStart() != null && task.getEnd() != null) {
			TimeSpan ts = new CTimeSpan(task);
			return ts.overlaps(timeSpan);
		}
		return false;
	}

	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if(this.orList.isEmpty()) {
			return true;
		}
		
		if(element instanceof Task) {
			Task task = (Task) element;
			boolean result = false;
			for(Iterator iter = this.orList.iterator(); iter.hasNext(); ) {
				if(selectTask(task, (TimeSpan) iter.next())) {
					result = true;
					break;
				}
			}
			if(result == false) {
				return false;
			}
			for(Iterator iter = this.andList.iterator(); iter.hasNext(); ) {
				if(!selectTask(task, (TimeSpan) iter.next())) {
					return false;
				}
			}
			return true;
		} else {
			return this.allowNonTasks;
		}
	}

	public void allowNonTasks(final boolean allowNonTasks) {
		this.allowNonTasks = allowNonTasks;
	}
	
	public boolean allowsNonTasks() {
		return this.allowNonTasks;
	}
	
	public void clearSearch() {
		this.orList.clear();
		this.andList.clear();
	}
	
	public void addAndFilter(final TimeSpan timeSpan) {
		this.andList.add(timeSpan);
	}
	
	public void addOrFilter(final TimeSpan timeSpan) {
		this.orList.add(timeSpan);
	}

	public void setFilter(final TimeSpan timeSpan, final boolean allowNonTasks) {
		clearSearch();
		this.orList.add(timeSpan);
		this.allowNonTasks = allowNonTasks;
	}
	
	public void setFilters(final TimeSpan[] andFilters, final TimeSpan[] orFilters, final boolean allowNonTasks) {
		clearSearch();
		for(int i = 0; i < andFilters.length; i++) {
			this.andList.add(andFilters[i]);
		}
		for(int i = 0; i < orFilters.length; i++) {
			this.orList.add(orFilters[i]);
		}
		this.allowNonTasks = allowNonTasks;
	}
}
