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

package org.aspencloud.calypso.ui.calendar.activitiesCalendar;

import java.util.Date;

import org.aspencloud.calypso.ui.calendar.activities.ActivityPart;
import org.aspencloud.calypso.ui.calendar.commands.CreateTaskCommand;
import org.aspencloud.calypso.ui.calendar.commands.EditTaskCommand;
import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.remus.infomngmnt.calendar.model.Task;


public class ActivitiesCalendarLayoutEditPolicy extends XYLayoutEditPolicy {

	
	@Override
	protected Command getAddCommand(Request generic) {
		// TODO Auto-generated method stub
		return super.getAddCommand(generic);
	}

	@Override
	protected Command createAddCommand(EditPart childEditPart, Object constraint) {
		CreateTaskCommand create = new CreateTaskCommand(1);
		ActivitiesCalendarPart part = (ActivitiesCalendarPart) getHost();
		
		Point p1 = ((Rectangle) constraint).getLeft();
		Point p2 = ((Rectangle) constraint).getRight();
		Date date1 = part.getDateFromPoint(p1, true);
		Date date2 = part.getDateFromPoint(p2, false);
		if((date1 == null) || (date2 == null)) return null;
		if(date2.after(date1)) {
			create.setStart(date1);
			create.setEnd(date2);
		} else {
			create.setStart(date2);
			create.setEnd(date1);
		}
		return create;
	}
	
	@Override
	protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
		return null;
	}
	
	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {

		EditTaskCommand cmd = new EditTaskCommand();
		ActivitiesCalendarPart part = (ActivitiesCalendarPart) getHost();
		
		cmd.setTask((Task) ((ActivityPart) child).getModel());
		Point p1 = ((Rectangle) constraint).getLeft().getCopy();
		p1.x += 10;
		TimeSpan ts = new TimeSpan();
		ts.setStartDate(part.getDateFromPoint(p1, true));
		cmd.setNewStart(ts.getStartDate());
		Point p2 = ((Rectangle) constraint).getRight().getCopy();
		p2.x -= 10;
		ts.setEndDate(part.getDateFromPoint(p2, false));
		cmd.setNewEnd(ts.getEndDate());
		
		return cmd;
	}
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		ActivitiesCalendarPart part = (ActivitiesCalendarPart) getHost();
		
		Date date;
		TimeSpan ts = new TimeSpan();
		Point p1 = request.getLocation().getCopy();
		date = part.getDateFromPoint(p1, true);
		if(date != null) {
			ts.setAllDay(date);
			Date start = ts.getStartDate();
		
			Dimension size = request.getSize();
			if(size != null) {
				Point p2 = p1.getCopy();
				p2 = p2.translate(size);
				p2.x -= (int) part.getDayWidth();
				date = part.getDateFromPoint(p2, false);
				if(date != null) {
					ts.setEndDate(date);
				} else {
					return null;
				}
			}
			Date end = ts.getEndDate();

			CreateTaskCommand create = new CreateTaskCommand(1);
			create.setStart(start);
			create.setEnd(end);
			return create;
		}
		return null;
	}
	
	@Override
	protected Command getMoveChildrenCommand(Request request) {
		return super.getMoveChildrenCommand(request);
	}

	@Override
	protected Command getDeleteDependantCommand(Request request) {
		return null;
	}
	
	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
//		TaskLayoutPolicy policy = new TaskLayoutPolicy();
		EditPolicy policy = child.getEditPolicy(EditPolicy.LAYOUT_ROLE);
		if(policy == null) {
			policy = new ResizableEditPolicy();
			((ResizableEditPolicy) policy).setResizeDirections(
					PositionConstants.EAST |
					PositionConstants.WEST |
					PositionConstants.NORTH |
					PositionConstants.SOUTH
					);
		}
		return policy;
	}
}