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

package org.aspencloud.calypso.ui.calendar.commands;

import java.util.Date;

import org.eclipse.gef.commands.Command;
import org.remus.infomngmnt.calendar.model.Task;


public class EditTaskCommand extends Command {

	private Task task;
	private Date newStart = null;
	private Date newEnd = null;
	
	@Override
	public void execute() {
		if(newStart != null) {
			task.getStart().setDate(newStart);
		}
		if(newEnd != null) {
			task.getEnd().setDate(newEnd);
		}
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public void setNewStart(Date newStart) {
		this.newStart = newStart;
	}
	
	public void setNewEnd(Date newEnd) {
		this.newEnd = newEnd;
	}
}