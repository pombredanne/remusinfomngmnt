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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aspencloud.calypso.util.CalypsoEdit;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.remus.infomngmnt.calendar.messages.Messages;
import org.remus.infomngmnt.calendar.model.Task;

public class DeleteTaskCommand extends Command {
	private final List tasks;
	
	public DeleteTaskCommand(final List tasks) {
		this.tasks = (tasks != null) ? tasks : new ArrayList();
	}
	
	@Override
	public void execute() {
		Shell shell = new Shell(Display.getCurrent().getActiveShell());
		MessageBox msg = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO);
		msg.setText(Messages.DeleteTaskCommand_Delete);
		msg.setMessage(Messages.DeleteTaskCommand_ConfirmDelete);
		int rval = msg.open();
		if(rval == SWT.NO) {
			return;
		}

		List list = new ArrayList();
		for(Iterator i = this.tasks.iterator(); i.hasNext(); ) {
			Task task = (Task) i.next();
			list.add(task);
		}

		if(!list.isEmpty()) {
			CalypsoEdit.delete(list);
		}
	}
}
