/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LoadingBarMessageProvider {

	public static final String LOADINGBAR_EVENT_ID = "LOADINGBAR_EVENT_ID"; //$NON-NLS-1$

	private static LoadingBarMessageProvider INSTANCE;

	private final Map<String, List<PropertyChangeListener>> listenerMap;

	public static LoadingBarMessageProvider getInstance() {
		if (LoadingBarMessageProvider.INSTANCE == null) {
			synchronized (LoadingBarMessageProvider.class) {
				if (LoadingBarMessageProvider.INSTANCE == null) {
					LoadingBarMessageProvider.INSTANCE = new LoadingBarMessageProvider();
				}
			}
		}
		return LoadingBarMessageProvider.INSTANCE;
	}

	private LoadingBarMessageProvider() {
		this.listenerMap = new HashMap<String, List<PropertyChangeListener>>();
	}

	public void addPropertyChangeListener(String id, PropertyChangeListener listener) {
		if (this.listenerMap.get(id) == null) {
			this.listenerMap.put(id, new ArrayList<PropertyChangeListener>());
		}
		this.listenerMap.get(id).add(listener);
	}

	public void firePropertyChange(String id, String message) {
		List<PropertyChangeListener> list = this.listenerMap.get(id);
		if (list != null) {
			PropertyChangeEvent event = new PropertyChangeEvent(id,LOADINGBAR_EVENT_ID,null,message);
			for (PropertyChangeListener propertyChangeListener : list) {
				propertyChangeListener.propertyChange(event);
			}
		}
	}

	public void removePropertyChangeListener(String id, PropertyChangeListener listener) {
		if (this.listenerMap.get(id) != null) {
			this.listenerMap.get(id).remove(listener);
		}
	}

}
