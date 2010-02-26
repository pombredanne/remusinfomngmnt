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


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public abstract class BaseModel {

	public static final String PROP_TIMESPAN = "timespan";
	public static final String PROP_CHILDREN = "children";
	public static final String PROP_LOCATION = "location";
	public static final String PROP_SIZE = "size";

	private BaseModel parent;
	protected TimeSpan timeSpan = new TimeSpan();
	protected List children = new ArrayList();
	protected Point location = new Point();
	protected Dimension size = new Dimension();
	protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	
	public BaseModel(BaseModel parent) {
		this.parent = parent;
	}

	
	protected void addChild(Object child) {
		if(!children.contains(child)) {
			children.add(child);
			firePropertyChange(PROP_CHILDREN, null, child);
		}
	}
	
	protected void addChildren(List children) {
		List list = new ArrayList();
		for(Iterator i = children.iterator(); i.hasNext(); ) {
			Object child = i.next();
			if(!this.children.contains(child)) {
				list.add(child);
			}
		}
		if(!list.isEmpty()) {
			this.children.addAll(list);
			firePropertyChange(PROP_CHILDREN, null, list);
		}
	}

	protected void setChildren(List children) {
		if (!children.isEmpty()) {
			List old = new ArrayList();
			old.addAll(children);
			children.clear();
			children.addAll(children);
			firePropertyChange(PROP_CHILDREN, old, this.children);
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		listeners.addPropertyChangeListener(pcl);
	}

	public void clearChildren() {
		if (!children.isEmpty()) {
			List old = new ArrayList();
			old.addAll(children);
			children.clear();
			firePropertyChange(PROP_CHILDREN, old, children);
		}
	}

	protected void firePropertyChange(String propName, Object old, Object newValue) {
		listeners.firePropertyChange(propName, old, newValue);
	}

	public List getChildren() {
		return children;
	}

	public Point getLocation() {
		return location;
	}

	public BaseModel getParent() {
		return parent;
	}

	public Dimension getSize() {
		return size;
	}

	public TimeSpan getTimeSpan() {
		return timeSpan;
	}

	protected void removeChild(Object child) {
		if (children.remove(child)) {
			firePropertyChange(PROP_CHILDREN, child, null);
		}
	}

	protected void removeChildren(List children) {
		List list = new ArrayList();
		for(Iterator i = children.iterator(); i.hasNext(); ) {
			Object child = i.next();
			if(this.children.remove(child)) {
				list.add(child);
			}
		}
		if(!list.isEmpty()) {
			firePropertyChange(PROP_CHILDREN, list, null);
		}
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		listeners.removePropertyChangeListener(pcl);
	}

	public void setLocation(Point place) {
		if ((location != null) && (location.equals(place)))
			return;
		location = place;
		firePropertyChange(PROP_LOCATION, null, place);
	}

	public void setSize(Dimension dim) {
		if ((size != null) && (size.equals(dim)))
			return;
		size = dim;
		firePropertyChange(PROP_SIZE, null, size);
	}

	public void setTimeSpan(TimeSpan ts) {
		Object old = timeSpan;
		timeSpan = ts;
		firePropertyChange(PROP_TIMESPAN, old, timeSpan);
	}

	public void setTimeSpan(Date start, long duration) {
		setTimeSpan(new TimeSpan(start, duration));
	}
}
