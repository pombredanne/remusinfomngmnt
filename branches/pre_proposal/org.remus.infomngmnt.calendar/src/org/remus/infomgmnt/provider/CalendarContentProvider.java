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

package org.remus.infomgmnt.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspencloud.calypso.ui.calendar.TaskListModel;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jface.viewers.Viewer;
import org.remus.infomngmnt.calendar.model.Task;


public class CalendarContentProvider extends AbstractTaskContentProvider {

	private TaskListModel model;

	public CalendarContentProvider() {
		super();
	}

	public CalendarContentProvider(int taskTypes) {
		super(taskTypes);
	}

	@Override
	public Object[] getChildren(Object object) {
		List l = new ArrayList();
		return l.toArray();
	}
	
	@Override
	public Object[] getElements(Object element) {
		List l = new ArrayList();
		Object[] elements = 
			(element == this.model.getInput()) ? super.getElements(element) : getChildren(element);
		for(int i = 0; i < elements.length; i++) {
			l.add(elements[i]);
			if(hasChildren(elements[i])) {
				l.addAll(Arrays.asList(getElements(elements[i])));
			}
		}

		return l.toArray();
	}
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.model = (TaskListModel) oldInput;
		clearAdapters();
		if(newInput != null) {
			addAdapter((EObjectImpl) newInput);
		}
	}

	// TODO model.refresh is used for EVERY model change
	@Override
	protected void add(Task task) 			{ this.model.refresh(); }
	@Override
	protected void add(Task[] tasks) 		{ this.model.refresh(); }
	@Override
	protected void clear(Task task)			{ this.model.refresh(); }
	@Override
	protected void refresh()					{ this.model.refresh(); }
	@Override
	protected void refresh(boolean update)	{ this.model.refresh(); }
	@Override
	protected void refresh(Task task) 		{ this.model.refresh(); }
	@Override
	protected void refresh(Task[] tasks) 	{ this.model.refresh(); }
	@Override
	protected void remove(Task task) 		{ this.model.refresh(); }
	@Override
	protected void remove(Task[] tasks) 	{ this.model.refresh(); }
	@Override
	protected void update(Task task, String[] properties) 		{  }
	@Override
	protected void update(Task[] tasks, String[] properties) 	{  }
}
