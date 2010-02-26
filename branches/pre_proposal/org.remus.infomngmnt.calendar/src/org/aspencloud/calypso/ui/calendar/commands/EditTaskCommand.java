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
		if (this.newStart != null && this.newStart.compareTo(this.task.getStart().getDate()) != 0) {
			this.task.getStart().setDate(this.newStart);
		}
		if (this.newEnd != null && this.newEnd.compareTo(this.task.getEnd().getDate()) != 0) {
			this.task.getEnd().setDate(this.newEnd);
		}
	}

	public void setTask(final Task task) {
		this.task = task;
	}

	public void setNewStart(final Date newStart) {
		this.newStart = newStart;
	}

	public void setNewEnd(final Date newEnd) {
		this.newEnd = newEnd;
	}
}