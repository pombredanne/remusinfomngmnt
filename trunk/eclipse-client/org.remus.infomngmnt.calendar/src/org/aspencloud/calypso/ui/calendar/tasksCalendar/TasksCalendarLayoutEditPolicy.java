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

package org.aspencloud.calypso.ui.calendar.tasksCalendar;

import java.util.Date;

import org.aspencloud.calypso.ui.calendar.commands.CreateTaskCommand;
import org.aspencloud.calypso.ui.calendar.commands.EditTaskCommand;
import org.aspencloud.calypso.ui.calendar.tasks.TaskPart;
import org.aspencloud.calypso.util.CTimeSpan;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import org.remus.infomngmnt.calendar.model.Task;

public class TasksCalendarLayoutEditPolicy extends XYLayoutEditPolicy {

	@Override
	protected Command getAddCommand(final Request generic) {
		// TODO Auto-generated method stub
		return super.getAddCommand(generic);
	}

	@Override
	protected Command createAddCommand(final EditPart childEditPart, final Object constraint) {
		CreateTaskCommand create = new CreateTaskCommand(1);
		TasksCalendarPart part = (TasksCalendarPart) getHost();

		Point p1 = ((Rectangle) constraint).getTopLeft();
		Point p2 = ((Rectangle) constraint).getBottomLeft();
		Date date1 = part.getDateFromPoint(p1);
		Date date2 = part.getDateFromPoint(p2);
		if ((date1 == null) || (date2 == null)) {
			return null;
		}
		if (date2.after(date1)) {
			create.setStart(date1);
			create.setEnd(date2);
		} else {
			create.setStart(date2);
			create.setEnd(date1);
		}
		return create;
	}

	@Override
	protected Command createChangeConstraintCommand(final EditPart child, final Object constraint) {
		return null;
	}

	@Override
	protected Command createChangeConstraintCommand(final ChangeBoundsRequest request,
			final EditPart child, final Object constraint) {

		EditTaskCommand cmd = new EditTaskCommand();
		TasksCalendarPart part = (TasksCalendarPart) getHost();

		Task task = (Task) ((TaskPart) child).getModel();
		cmd.setTask(task);
		if (!task.isReadonly()) {
			if (((request.getResizeDirection() & PositionConstants.NORTH) != 0)
					|| ((request.getResizeDirection() & PositionConstants.SOUTH) != 0)
					|| !CTimeSpan.isTaskInstantaneous(task)) {
				Date start = part.getDateFromPoint(((Rectangle) constraint).getTopLeft());
				cmd.setNewStart(start);
				Date end = part.getDateFromPoint(((Rectangle) constraint).getBottomLeft());
				cmd.setNewEnd(end);
			} else {
				Date date = part.getDateFromPoint(((Rectangle) constraint).getLeft());
				cmd.setNewStart(date);
				cmd.setNewEnd(date);
			}
			return cmd;
		}
		return UnexecutableCommand.INSTANCE;

	}

	@Override
	protected Command getCreateCommand(final CreateRequest request) {
		TasksCalendarPart part = (TasksCalendarPart) getHost();
		TasksCalendarFigure fig = (TasksCalendarFigure) part.getFigure();

		Point p1 = request.getLocation().getCopy();
		fig.getParent().translateToRelative(p1);
		Dimension d = request.getSize();
		Point p2 = (d != null) ? p1.getTranslated(d) : p1.getCopy();
		int occurrences = 1 + (int) ((p2.x - p1.x) / fig.getDayWidth());
		p2.x = p1.x;
		Date date1 = part.getDateFromPoint(p1);
		Date date2 = part.getDateFromPoint(p2);

		if ((date1 == null) || (date2 == null)) {
			return null;
		}

		CreateTaskCommand create = new CreateTaskCommand(occurrences);

		// if(date1.equals(date2)) {
		// if(fig.getNumRowLabels() > 0) {
		// p1.y += fig.getTaskArea().height / (fig.getNumRowLabels()-1);
		// date2 = part.getDateFromPoint(p1);
		// if(date2 == null) {
		// date2 = new Date(date1.getTime() +
		// (2*part.getSnapToDateResolution()));
		// }
		// } else {
		// date2.setTime(date1.getTime() + (2*part.getSnapToDateResolution()));
		// }
		// }
		if (date1.before(date2)) {
			create.setStart(date1);
			create.setEnd(date2);
		} else {
			create.setStart(date2);
			create.setEnd(date1);
		}

		return create;
	}

	@Override
	protected Command getMoveChildrenCommand(final Request request) {
		return super.getMoveChildrenCommand(request);
	}

	@Override
	protected Command getDeleteDependantCommand(final Request request) {
		return null;
	}

	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		// TaskLayoutPolicy policy = new TaskLayoutPolicy();
		EditPolicy policy = child.getEditPolicy(EditPolicy.LAYOUT_ROLE);
		if (policy == null) {
			policy = new ResizableEditPolicy();
			((ResizableEditPolicy) policy).setResizeDirections(PositionConstants.EAST
					| PositionConstants.WEST | PositionConstants.NORTH | PositionConstants.SOUTH);
		}
		return policy;
	}
}