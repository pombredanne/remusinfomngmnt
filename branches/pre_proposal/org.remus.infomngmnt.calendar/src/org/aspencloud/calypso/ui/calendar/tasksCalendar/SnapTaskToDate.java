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

import java.util.Calendar;
import java.util.Date;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.DropRequest;

public class SnapTaskToDate extends SnapToHelper {

	private TasksCalendarPart container;

	
	public SnapTaskToDate(TasksCalendarPart container) {
		this.container = container;
	}

	public Date snapDate(Date date) {
		if(date != null) {
			long resolution = container.getSnapToDateResolution();
			Calendar cal = TasksCalendarPart.tmpcal;
			cal.setTime(date);
			long time = (cal.get(Calendar.HOUR_OF_DAY) * 1000*60*60) +
						(cal.get(Calendar.MINUTE) * 1000*60) +
						(cal.get(Calendar.SECOND) * 1000) +
						(cal.get(Calendar.MILLISECOND));
			long mod = time % resolution;
			if(mod >= (resolution/2)) {
				return new Date(date.getTime() + (resolution - mod));
			} else {
				return new Date(date.getTime() - (mod));
			}
		}
		return null;
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
			Date date1 = container.getDateFromPoint(p1);
			Date date2 = container.getDateFromPoint(p2);
			if((date1 != null) && (date2 != null)) {
				int leftCorrection = container.getPointFromDate(date1).x - base.x + 1;
				correction.preciseX += leftCorrection;
				if((snapOrientation & HORIZONTAL) == 0) {
					correction.preciseWidth -= leftCorrection;
				}

				correction.preciseWidth += container.getPointFromDate(date2).x - (base.x + base.width) + container.getDayWidth();
				 
				snapOrientation &= ~(EAST | WEST | HORIZONTAL);
			}
			
		}
		
		if((snapOrientation & (WEST | HORIZONTAL)) != 0) {
			if(request instanceof DropRequest) {
				Date date = container.getDateFromPoint(((DropRequest) request).getLocation());
				if(date != null) {
					int leftCorrection = container.getPointFromDate(date).x - base.x + 1;
					correction.preciseX += leftCorrection;
					if((snapOrientation & HORIZONTAL) == 0) {
						correction.preciseWidth -= leftCorrection;
					}
		
					snapOrientation &= ~(WEST | HORIZONTAL);
				}
			}
		}
		
		if((snapOrientation & SOUTH) != 0) {
			Date date = container.getDateFromPoint(base.getBottomLeft());
			if(date != null) {
				date = snapDate(date);
				correction.preciseHeight += container.getPointFromDate(date).y - (base.y + base.height) + 1;
				snapOrientation &= ~SOUTH;
			}
		}
		
		if((snapOrientation & (NORTH | VERTICAL)) != 0) {
			Date date = container.getDateFromPoint(base.getTopLeft());
			if(date != null) {
				date = snapDate(date);
	
				int topCorrection = container.getPointFromDate(date).y - base.y + 1;
				correction.preciseY += topCorrection;
				if((snapOrientation & VERTICAL) == 0) {
					correction.preciseHeight -= topCorrection - 1;
				}

				snapOrientation &= ~(NORTH | VERTICAL);
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
