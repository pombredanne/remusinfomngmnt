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

package org.aspencloud.calypso.ui.calendar.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aspencloud.calypso.ui.calendar.commands.DeleteTaskCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import org.remus.infomngmnt.calendar.model.Task;

public class TaskComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(final GroupRequest deleteRequest) {
		List list = new ArrayList();
		for (Iterator i = deleteRequest.getEditParts().iterator(); i.hasNext();) {
			Object o = ((EditPart) i.next()).getModel();
			if (o instanceof Task) {
				if (!((Task) o).isReadonly()) {
					list.add(o);
				}
			}
		}
		if (!list.isEmpty()) {
			return new DeleteTaskCommand(list);
		}
		return null;
	}

}
