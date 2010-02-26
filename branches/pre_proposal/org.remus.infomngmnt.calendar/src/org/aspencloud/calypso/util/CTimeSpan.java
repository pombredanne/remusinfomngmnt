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

package org.aspencloud.calypso.util;

import org.remus.infomngmnt.calendar.model.CEvent;
import org.remus.infomngmnt.calendar.model.Task;



/**
 * Extends TimeSpan to add two Constructors for use specifically with Calypso Models
 * <p>Adds no new functionality</p>
 */
public class CTimeSpan extends TimeSpan {

	public static boolean isTaskInstantaneous(Task task) {
		return task.getStart().getDate().equals(task.getEnd().getDate());
	}

	
	public CTimeSpan() {
		super();
	}
	public CTimeSpan(Task task) {
		this(task.getStart(), task.getEnd());
	}
	public CTimeSpan(CEvent start, CEvent end) {
		setTimeSpan(start, end);
	}
	
	
	public void setTimeSpan(Task task) {
		setTimeSpan(task.getStart(), task.getEnd());
	}
	
	public void setTimeSpan(CEvent start, CEvent end) {
		if((start != null) && (start.getDate() != null)) this.start.setTime(start.getDate().getTime());
		if((end != null) && (end.getDate() != null)) this.end.setTime(end.getDate().getTime());
	}
}
