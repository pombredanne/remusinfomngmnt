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

import org.eclipse.jface.action.Action;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.remus.infomngmnt.calendar.messages.Messages;
import org.remus.infomngmnt.ccalendar.CCalendar;

public class ZoomInAction extends Action {
	public static final String ID = "org.aspencloud.calypso.ui.views.Calendar.stuff.actions.ZoomInAction"; //$NON-NLS-1$

	private CCalendar calendar;

	public ZoomInAction() {
		super(Messages.ZoomInAction_ZoomIn);
		setToolTipText(Messages.ZoomInAction_ZoomIn);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				CCalendar.PLUGIN_ID, "icons/iconexperience/16/zoom_in.png")); //$NON-NLS-1$
		setId(ID);
	}

	public void setCalendar(final CCalendar calendar) {
		this.calendar = calendar;
	}

	@Override
	public void run() {
		calendar.zoom(2, 2);
	}
}
