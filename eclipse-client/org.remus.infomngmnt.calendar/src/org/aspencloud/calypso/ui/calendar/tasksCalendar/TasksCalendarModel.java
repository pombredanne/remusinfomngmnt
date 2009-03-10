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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.aspencloud.calypso.ui.calendar.TaskListModel;
import org.aspencloud.calypso.ui.calendar.filters.AllDayMultiDayTaskFilter;
import org.aspencloud.calypso.ui.calendar.filters.CalendarFilter;
import org.aspencloud.calypso.util.TimeSpan;


public class TasksCalendarModel extends TaskListModel {

	public static final String PROP_DAY = "day";
	public static final String PROP_ROW_LABELS = "rowLabels";
	public static final String PROP_NUM_DAYS = "numDays";
	
	public static final int INTERVAL_START_OF_DAY = 0; 
	public static final int INTERVAL_START_OF_WORK_DAY = 1; 
	public static final int INTERVAL_END_OF_WORK_DAY = 2; 
	public static final int INTERVAL_END_OF_DAY = 3; 
	
	public static final int TYPE_DAY = 1;
	public static final int TYPE_WEEK = 7;
	
	private static Calendar tmpcal = Calendar.getInstance(Locale.getDefault());
	
	private long startOfDay = 0;//1000*60*60*18;
//	private long startWorkDay = startOfDay;
	private long endOfDay = 1000*60*60*24;
//	private long endWorkDay = endOfDay;

	private String[] rowLabels = new String[0];
//	private AllDayMultiDayTaskFilter mdtFilter;
	private CalendarFilter calFilter;
	private Calendar[] days = new Calendar[0];
	private TimeSpan[] dayTimeSpans = new TimeSpan[0];

	
	public TasksCalendarModel() {
		this(true);
	}
	public TasksCalendarModel(boolean allowAllDayAndMultiDayTasks) {
		super(null);
		filters.add(new AllDayMultiDayTaskFilter(allowAllDayAndMultiDayTasks));
		filters.add(calFilter = new CalendarFilter());
	}

	public String[] getRowLabels() {
		return rowLabels;
	}

	public int getNumRowLabels() {
		return rowLabels.length;
	}
	
	public void setNumRowLabels(int numRows) {
		rowLabels = new String[numRows];
		updateRowLabels();
	}
	
	public int getNumDays() {
		return days.length;
	}
	
	public Calendar[] getDays() {
		return days;
	}
	
	public TimeSpan[] getDayTimeSpans() {
		return dayTimeSpans;
	}
	
	public long[] getDayIntervals() {
		return new long[] { startOfDay, 0, 0, endOfDay }; 
	}
	
	/**
	 * array of four (4) longs representing the time 
	 * from the beginning of the day to the following:
	 * 1 - start of displayed day
	 * 2 - start of displayed work day
	 * 3 - end of displayed work day
	 * 4 - end of displayed day
	 * (all numbers are absolute)
	 * @param intervals
	 */
	public void setDayIntervals(long[] intervals) {
		if(intervals.length != 4) return;
		startOfDay = intervals[0];
		endOfDay = intervals[3];

		updateRowLabels();
		updateCalendarFilter();
	}

	public int[] getDay(int day) {
		int[] a = new int[2];
		if((day >= 0) && (day < days.length)) {
			a[0] = (days[day]).get(Calendar.YEAR);
			a[1] = (days[day]).get(Calendar.DAY_OF_YEAR);
		}
		return a;
	}
	
	public void setNumDays(int numDays) {
		if((days.length != numDays) && (numDays > 0)) {

			Calendar[] old = new Calendar[days.length];
			for(int i = 0; i < old.length; i++) {
				old[i] = days[i];
			}

			days = new Calendar[numDays];
			for(int i = 0; i < numDays; i++) {
				days[i] = Calendar.getInstance(Locale.getDefault());
			}

//			reloadChildren();
			refresh();

			firePropertyChange(PROP_NUM_DAYS, old, days);
		}
	}

	public void setDay(int day, int dayOfYear, int year) {
		if((day >= 0) && (day < days.length)) {
			days[day].set(Calendar.DAY_OF_YEAR, dayOfYear);
			days[day].set(Calendar.YEAR, year);
			days[day].set(Calendar.HOUR_OF_DAY, 0);
			days[day].set(Calendar.MINUTE, 0);
			days[day].set(Calendar.SECOND, 0);
			days[day].set(Calendar.MILLISECOND, 0);
			days[day].setTimeInMillis(days[day].getTimeInMillis() + startOfDay);
			
			updateCalendarFilter();
		}
	}

	private void updateCalendarFilter() {
		if(dayTimeSpans.length != days.length) {
			dayTimeSpans = new TimeSpan[days.length];
		}
		for(int i = 0; i < dayTimeSpans.length; i++) {
			dayTimeSpans[i] = new TimeSpan(days[i].getTime(), (endOfDay - startOfDay - 1));	
		}

		calFilter.setFilters(new TimeSpan[0], dayTimeSpans, false);
//		reloadChildren();
		refresh();
	}
	
	private void updateRowLabels() {
		tmpcal.set(Calendar.HOUR_OF_DAY, 0);
		tmpcal.set(Calendar.MINUTE, 0);
		tmpcal.set(Calendar.SECOND, 0);
		tmpcal.set(Calendar.MILLISECOND, 0);
		tmpcal.setTimeInMillis(tmpcal.getTimeInMillis() + startOfDay);
		
		long duration = (endOfDay - startOfDay) / (rowLabels.length - 1);

		for(int i = 0; i < rowLabels.length; i++) {
			SimpleDateFormat sdf;
			if(tmpcal.get(Calendar.HOUR_OF_DAY) == 12) {
				sdf = new SimpleDateFormat("a h");
			} else {
				sdf = new SimpleDateFormat("h");
			}
			rowLabels[i] = sdf.format(tmpcal.getTime());
			tmpcal.setTimeInMillis(tmpcal.getTimeInMillis() + duration);
		}
		
		firePropertyChange(PROP_ROW_LABELS, null, rowLabels);
	}
}
