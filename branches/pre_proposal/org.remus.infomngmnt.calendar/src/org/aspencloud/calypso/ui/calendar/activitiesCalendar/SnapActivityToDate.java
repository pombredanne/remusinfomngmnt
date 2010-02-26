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

import org.aspencloud.calypso.ui.calendar.tasksCalendar.TasksCalendarFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.DropRequest;

public class SnapActivityToDate extends SnapToHelper {

	private ActivitiesCalendarPart container;

	
	public SnapActivityToDate(ActivitiesCalendarPart container) {
		this.container = container;
	}

	@Override
	public int snapRectangle(Request request, int snapOrientation,
			PrecisionRectangle baseRect, PrecisionRectangle result) {
		
		PrecisionRectangle base = baseRect.getPreciseCopy();
		makeRelative(container.getContentPane(), base);
		PrecisionRectangle correction = new PrecisionRectangle();
		makeRelative(container.getContentPane(), correction);
		
		
		if((snapOrientation & EAST) != 0) {
			Point p1 = base.getLeft();
			Point p2 = base.getRight();
			Date date1 = container.getDateFromPoint(p1, true);
			Date date2 = container.getDateFromPoint(p2, false);
			if(date2 == null) {
				Point p = ((TasksCalendarFigure) container.getFigure()).getTaskArea().getRight();
				p.x -= ((TasksCalendarFigure) container.getFigure()).getTaskArea().getLeft().x;
				date2 = container.getDateFromPoint(p, false);
			}
			if((date1 != null) && (date2 != null)) {
				correction.preciseWidth += container.getPointFromDate(date2, false).x - (base.x + base.width) + container.getDayWidth();
				snapOrientation &= ~(EAST);
			}
		}
		
		if((snapOrientation & (WEST | HORIZONTAL)) != 0) {
			if(request instanceof DropRequest) {
				Point drop = ((DropRequest) request).getLocation();
				Date date = container.getDateFromPoint(drop, false);
				
				if(date == null) {
					int width = ((TasksCalendarFigure) container.getFigure()).getTaskArea().width;
					Point p = (drop.x > width/2) ? 
							((TasksCalendarFigure) container.getFigure()).getTaskArea().getRight() :
								((TasksCalendarFigure) container.getFigure()).getTaskArea().getLeft();
					if(drop.x > width/2) {
						p.x -= ((TasksCalendarFigure) container.getFigure()).getTaskArea().getLeft().x;
					}
					date = container.getDateFromPoint(p, false);
				}
				if(date != null) {
					int leftCorrection = container.getPointFromDate(date, false).x - base.x + 1;
					correction.preciseX += leftCorrection;
					if((snapOrientation & HORIZONTAL) == 0) {
						correction.preciseWidth -= leftCorrection;
					}
		
					snapOrientation &= ~(WEST | HORIZONTAL);
				}
			}
		}
		
		correction.updateInts();
		makeAbsolute(container.getContentPane(), correction);
		result.preciseX += correction.preciseX;
		result.preciseY += correction.preciseY;
		result.preciseWidth += correction.preciseWidth;
		result.preciseHeight += correction.preciseHeight;
		result.updateInts();

		return snapOrientation;
	}

}
