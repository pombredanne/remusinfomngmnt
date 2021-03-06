/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.aspencloud.calypso.ui.calendar.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PropertyChangeObject {
	
	protected transient PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		listeners.addPropertyChangeListener(pcl);
	}
	
	protected void firePropertyChange(String propName, Object old, Object newValue) {
		listeners.firePropertyChange(propName, old, newValue);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		listeners.removePropertyChangeListener(pcl);
	}

}
