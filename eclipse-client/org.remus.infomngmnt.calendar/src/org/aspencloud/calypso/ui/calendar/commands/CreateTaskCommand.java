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

import org.aspencloud.calypso.util.CalypsoFactory;
import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.gef.commands.Command;
import org.remus.infomngmnt.calendar.messages.Messages;

public class CreateTaskCommand extends Command {

	private Date end;
	private Date start;
	private int occ;
	
	public CreateTaskCommand(final int occurrences) {
		setOccurrences(occurrences);
	}
	
	@Override
	public void execute() {
		for(int i = 0; i < this.occ; i++) {
			CalypsoFactory.createTask(Messages.CreateTaskCommand_NewTask, this.start, this.end);
			this.start = new Date(this.start.getTime() + TimeSpan.DAY);
			this.end = new Date(this.end.getTime() + TimeSpan.DAY);
		}
	}

	public void setEnd(final Date end) {
		this.end = end;
	}
	
	public void setOccurrences(final int occurrences) {
		this.occ = occurrences;
	}
	
	public void setStart(final Date start) {
		this.start = start;
	}
}