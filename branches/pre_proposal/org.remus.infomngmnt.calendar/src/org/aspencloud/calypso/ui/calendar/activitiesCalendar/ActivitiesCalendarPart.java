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

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.aspencloud.calypso.ui.calendar.BaseModel;
import org.aspencloud.calypso.ui.calendar.BasePart;
import org.aspencloud.calypso.ui.calendar.activities.ActivityFigure;
import org.aspencloud.calypso.ui.calendar.activities.ActivityPart;
import org.aspencloud.calypso.ui.calendar.factories.FigureFactory;
import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarFigure;
import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarModel;
import org.aspencloud.calypso.util.CTimeSpan;
import org.aspencloud.calypso.util.TimeSpan;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.SnapToHelper;
import org.remus.infomngmnt.calendar.model.Task;

public class ActivitiesCalendarPart extends BasePart {

	public static Calendar tmpcal = Calendar.getInstance(Locale.getDefault());

	private final HashMap constraints = new HashMap();

	
	@Override
	protected IFigure createFigure() {
		TasksCalendarFigure fig = (TasksCalendarFigure) FigureFactory.createTasksCalendarFigure();
//		fig.setPreferredSize(-1, 800);
		fig.setRowLabels(getTasksCalendarModel().getRowLabels());
		fig.setNumDays(getTasksCalendarModel().getNumDays());
		fig.addPropertyChangeListener(this);
		return fig;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ActivitiesCalendarLayoutEditPolicy()); //new RowEditPolicy());
	}
	
	
	@Override
	public void deactivate() {
		((TasksCalendarFigure) getFigure()).clearDayColors();
		super.deactivate();
	}

	@Override
	public Object getAdapter(final Class adapter) {
		if((adapter == SnapToHelper.class) || 
				(adapter == SnapActivityToDate.class)){
			return new SnapActivityToDate(this);
		}
		return super.getAdapter(adapter);
	}

	protected TasksCalendarModel getTasksCalendarModel() {
		return (TasksCalendarModel) getModel();
	}
	
	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		if(TasksCalendarModel.PROP_DAY.equals(prop)) {
			refreshVisuals();
		} else if(BaseModel.PROP_CHILDREN.equals(prop)) {
			refreshChildren();
			setTaskConstraints();
		} else if(BaseModel.PROP_SIZE.equals(prop) || BaseModel.PROP_LOCATION.equals(prop)) {
			getFigure().setPreferredSize(getBaseModel().getSize());
		} else if(TasksCalendarModel.PROP_NUM_DAYS.equals(prop)) {
			TasksCalendarFigure fig = (TasksCalendarFigure) getFigure();
			TasksCalendarModel model = (TasksCalendarModel) getModel();
			fig.setNumDays(model.getNumDays());
		} else if(TasksCalendarModel.PROP_ROW_LABELS.equals(prop)) {
			String[] labels = ((TasksCalendarModel) getModel()).getRowLabels();
			((TasksCalendarFigure) getFigure()).setRowLabels(labels);
			getFigure().repaint();
		} else if(TasksCalendarFigure.PROP_TASK_AREA.equals(prop) ||
				TasksCalendarFigure.PROP_DAY_WIDTH.equals(prop)) {
			setTaskConstraints();
		}
	}

	@Override
	protected List getModelChildren() {
		return ((TasksCalendarModel) getModel()).getChildren();
	}
	
	private Rectangle getConstraint(final ActivityPart part, final List requesterList) {
		if(this.constraints.containsKey(part)) {
			Object o = this.constraints.get(part);
			if((o != null) && (o instanceof Rectangle)) {
				return (Rectangle) o;
			}
		}
		
		Task task = (Task) part.getModel();

		Rectangle constraint;
		TimeSpan ts = new CTimeSpan(task);
		Point p1 = getPointFromDate(ts.getStartDate(), true);
		if(p1 == null) {
			p1 = ((TasksCalendarFigure) getFigure()).getTaskArea().getTopLeft().getCopy();
			p1.x -= Math.min(10, p1.x);
			p1.y += 1;
		}
		
		Point p2 = getPointFromDate(ts.getEndDate(), false);
		if(p2 == null) {
			p2 = ((TasksCalendarFigure) getFigure()).getTaskArea().getTopRight().getCopy();
			p2.x -= getDayWidth();
			int x = ((TasksCalendarFigure) getFigure()).getClientArea().getTopRight().x -
					((TasksCalendarFigure) getFigure()).getTaskArea().getTopRight().x;
			p2.x += Math.min(10, x);
			p2.y += 1;
		}

		p2.x += getDayWidth();
		p2.y += ((ActivityFigure) part.getFigure()).getPreferredHeight();

		constraint = new Rectangle(p1, p2);
		
		Date start = task.getStart().getDate();
		Date end = task.getEnd().getDate();
		TimeSpan ts1 = new TimeSpan(start, end);

		List overlappingParts = new ArrayList();
		for(Iterator i = getChildren().iterator(); i.hasNext(); ) {
			ActivityPart part2 = (ActivityPart) i.next();
			Task test = (Task) part2.getModel();
			if(test.equals(task)) {
				continue;
			}
			if(ts1.overlaps(test.getStart().getDate(), test.getEnd().getDate())) {
				if(start.after(test.getStart().getDate())
						||
						(start.equals(test.getStart().getDate()) && 
						 end.before(test.getEnd().getDate())) 
						||  
						(start.equals(test.getStart().getDate()) &&
						 end.equals(test.getEnd().getDate()) &&
						 !requesterList.contains(part2))) {
					
					overlappingParts.add(part2);
				}
			}
		}

		int yOffset = 0;
		for(Iterator i = overlappingParts.iterator(); i.hasNext(); ) {
			ActivityPart opart = (ActivityPart) i.next();
			if(!requesterList.contains(part)) {
				requesterList.add(part);
			}
			Rectangle opConstraint = getConstraint(opart, requesterList);
			yOffset = Math.max(yOffset, opConstraint.y + constraint.height + 1);
		}
		
		constraint.x += 1;
		constraint.width -= 3;
		constraint.y += yOffset + 1;
		this.constraints.put(part, constraint);
		return constraint;
	}
	
	private void setTaskConstraints() {
		this.constraints.clear();
		for(Iterator i = getChildren().iterator(); i.hasNext(); ) {
			ActivityPart part = (ActivityPart) i.next();
			getConstraint(part, new ArrayList());
		}

		for(Iterator i = this.constraints.keySet().iterator(); i.hasNext(); ) {
			ActivityPart part = (ActivityPart) i.next();
			setLayoutConstraint(part, part.getFigure(), this.constraints.get(part));
		}
	}

	public Date getDateFromPoint(final Point point, final boolean start) {
		if(point == null) {
			return null;
		}
		
		TasksCalendarFigure fig = (TasksCalendarFigure) getFigure();
		TasksCalendarModel model = (TasksCalendarModel) getModel();

		if(fig.getTaskArea().contains(point)) {
			int day = (int) ((point.x - fig.getTaskArea().x) / getDayWidth());
			if((day >= 0) && (day < model.getNumDays())) {
				tmpcal.set(Calendar.YEAR, model.getDay(day)[0]);
				tmpcal.set(Calendar.DAY_OF_YEAR, model.getDay(day)[1]);
				tmpcal.set(Calendar.HOUR_OF_DAY, 0);
				tmpcal.set(Calendar.MINUTE, 0);
				tmpcal.set(Calendar.SECOND, 0);
				tmpcal.set(Calendar.MILLISECOND, 0);

				tmpcal.setTimeInMillis(tmpcal.getTimeInMillis() + model.getDayIntervals()[0]);

				if(!start) {
					tmpcal.add(Calendar.DATE, 1);
				}
				
				return tmpcal.getTime();
			}
		}
		return null;
	}
	
	public Point getPointFromDate(final Date date, final boolean start) {
		if(date == null) {
			return null;
		}

		TasksCalendarFigure fig = (TasksCalendarFigure) getFigure();
		TasksCalendarModel model = (TasksCalendarModel) getModel();

		tmpcal.setTime(date);
		if(!start) {
			tmpcal.add(Calendar.DATE, -1);
		}
		
		for(int i = 0; i < model.getNumDays(); i++) {
			if((tmpcal.get(Calendar.DAY_OF_YEAR) == model.getDay(i)[1]) &&
					(tmpcal.get(Calendar.YEAR) == model.getDay(i)[0])) {

				int x = (int) (fig.getTaskArea().x + (fig.getDayWidth() * i) + 1);
				int y = fig.getTaskArea().y + 1;

				return new Point(x,y);
			}
		}
		return null;
	}

	public double getDayWidth() {
		return ((TasksCalendarFigure) getFigure()).getDayWidth();
	}
}