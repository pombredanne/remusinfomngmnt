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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.remus.infomngmnt.calendar.model.Task;
import org.remus.infomngmnt.calendar.model.Tasklist;

public class TaskListModel extends BaseModel {

	public static final String PROP_TASK_TYPE = "task_type";
	public static final String PROP_EVENT_DATE = "event_date";
	
	private Tasklist input;
	private IStructuredContentProvider provider;
	protected List filters = new ArrayList();

	
	public TaskListModel(BaseModel parent) {
		super(parent);
//		provider = new TaskListContentProvider();
	}

	public void setContentProvider(IStructuredContentProvider provider) {
		this.provider = provider;
	}
	
	public Tasklist getInput() {
		return input;
	}

	public void dispose() {
		if(provider != null) provider.dispose();
	}
	
	public void add(Task task, Task parent) 	{ refresh(); }
	public void add(Task[] tasks) 				{ refresh(); }

	public void refresh() {
		clearChildren();
		
		if(input == null) return;
		
		Object[] objects = provider.getElements(input);

		// run filters
		for(Iterator i = filters.iterator(); i.hasNext(); ) {
			ViewerFilter filter = (ViewerFilter) i.next();
			objects = filter.filter(null, new Object(), objects);
		}

		// add filtered elements to children list
		addChildren(Arrays.asList(objects));
	}
	
	public void refresh(Task task) 		{ refresh(); }
	public void refresh(Task[] tasks) 	{ refresh(); }
	public void remove(Task task) 		{ refresh(); }
	public void remove(Task[] tasks) 	{ refresh(); }
	public void update(Task task) 		{ refresh(); }
	public void update(Task[] tasks) 	{ refresh(); }

	public void setInput(Tasklist input) {
		this.input = input;
		if(provider != null) provider.inputChanged(null, this, input);
		refresh();
	}
	

	public Date getDate() {
		return new Date(timeSpan.getStart());
	}
		
}
