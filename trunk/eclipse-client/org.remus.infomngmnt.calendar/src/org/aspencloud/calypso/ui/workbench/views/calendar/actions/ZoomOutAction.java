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
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.remus.calendartest.ccalendar.CCalendar;

public class ZoomOutAction extends Action {
	public static final String ID = "org.aspencloud.calypso.ui.views.Calendar.stuff.actions.ZoomOutAction";

	private final CCalendar calendar;
	
	public ZoomOutAction(final CCalendar calendar) {
		super("Zoom Out");
		this.calendar = calendar;
		setToolTipText("Zoom Out");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_ELEMENT));
		setId(ID);
//		setChecked(true);
//		run();
	}

	@Override
	public void run() {
		this.calendar.zoom(.5, .5);
	}
}
