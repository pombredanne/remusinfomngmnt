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

package org.aspencloud.calypso.ui.calendar.factories;

import org.eclipse.gef.requests.CreationFactory;

public class ModelFactory implements CreationFactory {

	public static final String TASK_TEMPLATE = "task"; //$NON-NLS-1$
	public static final String ACTIVITY_TEMPLATE = "activity"; //$NON-NLS-1$

	private final String template;

	public ModelFactory(String template) {
		this.template = template;
	}

	public Object getNewObject() {
		if (TASK_TEMPLATE.equals(template)) {
			// return CalypsoUiFactory.createTask("New CTask",
			// UiPlugin.getDefault().getWorkSpace().getOwner());
		} else if (ACTIVITY_TEMPLATE.equals(template)) {

		}
		return null;
	}

	public Object getObjectType() {
		return template;
	}

}
