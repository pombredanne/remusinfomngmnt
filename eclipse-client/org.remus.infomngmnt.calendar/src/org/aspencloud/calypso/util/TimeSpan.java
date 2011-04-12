/****************************************************************************
 * Copyright (c) 2005-2006 Jeremy Dowdall
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Jeremy Dowdall <jeremyd@aspencloud.com> - initial API and implementation
 *****************************************************************************/
package org.aspencloud.calypso.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.aspencloud.calypso.ui.calendar.model.PropertyChangeObject;

/**
 * A TimeSpan is a length of time between a start date, and an end date. It is
 * made up of many convenience methods that are useful in working with durations
 * of time.
 */
public class TimeSpan extends PropertyChangeObject {

	public static final Calendar tmpcal1 = Calendar.getInstance();
	public static final Calendar tmpcal2 = Calendar.getInstance();

	public static final long SECOND = 1000;
	public static final long MINUTE = 1000 * 60;
	public static final long HOUR = 1000 * 60 * 60;
	public static final long DAY = 1000 * 60 * 60 * 24;
	public static final long WEEK = 1000 * 60 * 60 * 24 * 7;

	public static final String PROP_START_TIME = "starttime"; //$NON-NLS-1$
	public static final String PROP_END_TIME = "endtime"; //$NON-NLS-1$

	public static TimeSpan createAllDay(final Date date) {
		tmpcal1.setTime(date);
		tmpcal1.set(Calendar.HOUR_OF_DAY, 0);
		tmpcal1.set(Calendar.MINUTE, 0);
		tmpcal1.set(Calendar.SECOND, 0);
		tmpcal1.set(Calendar.MILLISECOND, 0);
		tmpcal2.setTime(tmpcal1.getTime());
		tmpcal2.add(Calendar.DATE, 1);
		return new TimeSpan(tmpcal1.getTime(), tmpcal2.getTime());
	}

	public static TimeSpan createAllMonth(final Date date) {
		tmpcal1.setTime(date);
		tmpcal1.set(Calendar.DAY_OF_MONTH, 1);
		tmpcal1.set(Calendar.HOUR_OF_DAY, 0);
		tmpcal1.set(Calendar.MINUTE, 0);
		tmpcal1.set(Calendar.SECOND, 0);
		tmpcal1.set(Calendar.MILLISECOND, 0);
		tmpcal2.setTime(tmpcal1.getTime());
		tmpcal2.add(Calendar.MONTH, 1);
		return new TimeSpan(tmpcal1.getTime(), tmpcal2.getTime());
	}

	public static TimeSpan createAllWeek(final Date date) {
		tmpcal1.setTime(date);
		tmpcal1.set(Calendar.DAY_OF_WEEK, tmpcal1.getFirstDayOfWeek());
		tmpcal1.set(Calendar.HOUR_OF_DAY, 0);
		tmpcal1.set(Calendar.MINUTE, 0);
		tmpcal1.set(Calendar.SECOND, 0);
		tmpcal1.set(Calendar.MILLISECOND, 0);
		tmpcal2.setTime(tmpcal1.getTime());
		tmpcal2.add(Calendar.WEEK_OF_MONTH, 1);
		return new TimeSpan(tmpcal1.getTime(), tmpcal2.getTime());
	}

	public static long getTimeOfDay(final Date date) {
		tmpcal1.setTime(date);
		return (tmpcal1.get(Calendar.HOUR_OF_DAY) * HOUR)
				+ (tmpcal1.get(Calendar.MINUTE) * MINUTE)
				+ (tmpcal1.get(Calendar.SECOND) * SECOND)
				+ tmpcal1.get(Calendar.MILLISECOND);
	}

	protected Date start = new Date(0);
	protected Date end = new Date(0);

	public TimeSpan() {
	}

	public TimeSpan(final Date start, final Date end) {
		if (start != null) {
			this.start = start;
		}
		if (end != null) {
			this.end = end;
		}
	}

	public TimeSpan(final Date start, final long duration) {
		if (start != null) {
			this.start = start;
			setDurationFromStart(duration);
		}
	}

	public TimeSpan(final long start, final long end) {
		setStart(start);
		setEnd(end);
	}

	public TimeSpan(final TimeSpan ts) {
		if (ts != null) {
			start = ts.getStartDate();
			end = ts.getEndDate();
		}
	}

	/**
	 * Determines whether or not this TimeSpan contains the given date<br>
	 * <b>NOTE: Starts are INclusive, while Ends are EXclusive</b><br>
	 * <code>(this.start <= date < this.end)</code>
	 * 
	 * @param date
	 *            the date being tested
	 * @return true if the given date is contained, false otherwise
	 */
	public boolean contains(final Date date) {
		return ((start.before(date) || start.equals(date)) && end.after(date));
	}

	/**
	 * Determines whether or not this TimeSpan contains the given date<br>
	 * <b>NOTE: Starts are INclusive, while Ends are EXclusive</b><br>
	 * <code>this.start <= date < this.end</code>
	 * 
	 * @param date
	 *            a long integer representation of the date being tested
	 * @return true if the given date is contained, false otherwise
	 */
	public boolean contains(final long date) {
		return contains(new Date(date));
	}

	/**
	 * Determines whether or not this TimeSpan contains the start and end of the
	 * given TimeSpan<br>
	 * <b>NOTE: Starts are INclusive, while Ends are EXclusive</b><br>
	 * <code>(this.start <= ts.start < this.end) && (this.start <= ts.end < this.end)</code>
	 * 
	 * @param TimeSpan
	 *            to be tested
	 * @return true if the given TimeSpan is fully contained, false otherwise
	 */
	public boolean contains(final TimeSpan ts) {
		return (contains(ts.getStartDate())) && contains(ts.getEndDate());
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public boolean containsProper(final Date date) {
		return (start.before(date) && end.after(date));
	}

	/**
	 * @deprecated
	 */
	@Deprecated
	public boolean containsProper(final TimeSpan ts) {
		return (containsProper(new Date(ts.getStart())) && containsProper(new Date(
				ts.getEnd())));
	}

	public long getDuration() {
		return (end.getTime() - start.getTime());
	}

	public long getEnd() {
		return end.getTime();
	}

	public Date getEndDate() {
		return end;
	}

	public long getEndTimeOfDay() {
		return getTimeOfDay(end);
	}

	public long getStart() {
		return start.getTime();
	}

	public Date getStartDate() {
		return start;
	}

	public long getStartTimeOfDay() {
		return getTimeOfDay(start);
	}

	public boolean isAllDay() {
		tmpcal1.setTime(start);
		tmpcal2.setTime(end);
		return ((tmpcal1.get(Calendar.HOUR_OF_DAY) == 0)
				&& (tmpcal1.get(Calendar.MINUTE) == 0)
				&& (tmpcal1.get(Calendar.SECOND) == 0)
				&& (tmpcal1.get(Calendar.MILLISECOND) == 0)
				&& (tmpcal2.get(Calendar.HOUR_OF_DAY) == 0)
				&& (tmpcal2.get(Calendar.MINUTE) == 0)
				&& (tmpcal2.get(Calendar.SECOND) == 0)
				&& (tmpcal2.get(Calendar.MILLISECOND) == 0)
				&& (tmpcal1.get(Calendar.YEAR) == tmpcal2.get(Calendar.YEAR)) && ((tmpcal1
				.get(Calendar.DATE) + 1) == tmpcal2.get(Calendar.DATE)));
	}

	public boolean isDuration(final long d) {
		return (d == getDuration());
	}

	public boolean isInstant() {
		return start.equals(end);
	}

	public boolean isMultiDay() {
		tmpcal1.setTime(start);
		tmpcal2.setTime(end);
		return ((tmpcal1.get(Calendar.YEAR) != tmpcal2.get(Calendar.YEAR)) || (tmpcal1
				.get(Calendar.DATE) != tmpcal2.get(Calendar.DATE)));
	}

	/**
	 * TimeSpan overlaps ts (either ts.start | ts.end occur between this.start &
	 * this.end), (this reverse is implied).
	 */
	public boolean overlaps(final Date start, final Date end) {
		return this.start.before(end) && this.end.after(start);
	}

	public boolean overlaps(final TimeSpan ts) {
		return overlaps(ts.getStartDate(), ts.getEndDate());
	}

	public void setAllDay(final Date date) {
		if (date != null) {
			tmpcal1.setTime(date);
			tmpcal1.set(Calendar.HOUR_OF_DAY, 0);
			tmpcal1.set(Calendar.MINUTE, 0);
			tmpcal1.set(Calendar.SECOND, 0);
			tmpcal1.set(Calendar.MILLISECOND, 0);
			setStartDate(tmpcal1.getTime());
			tmpcal1.add(Calendar.DATE, 1);
			setEndDate(tmpcal1.getTime());
		}
	}

	public void setDurationFromEnd(final long duration) {
		setStart(end.getTime() - duration);
	}

	public void setDurationFromStart(final long duration) {
		setEnd(start.getTime() + duration);
	}

	public void setEnd(final long arg) {
		long oldValue = end.getTime();
		end.setTime(arg);
		firePropertyChange(PROP_END_TIME, oldValue, arg);
	}

	public void setEndDate(final Date date) {
		setEnd(date.getTime());
	}

	public void setStart(final long arg) {
		long oldValue = start.getTime();
		start.setTime(arg);
		firePropertyChange(PROP_START_TIME, oldValue, arg);
	}

	public void setStartDate(final Date date) {
		setStart(date.getTime());
	}

	public void setTimeSpan(final Date start, final Date end) {
		setStart(start.getTime());
		setEnd(end.getTime());
	}

	public void setTimeSpan(final Date start, final long duration) {
		this.start = start;
		setDurationFromStart(duration);
	}

	public void setTimeSpan(final long start, final long end) {
		setStart(start);
		setEnd(end);
	}

	public static boolean isInstantaneous(final TimeSpan ts) {
		return ts.getStartDate().equals(ts.getEndDate());
	}

	/**
	 * Calculates the absolute amount of month in a timespan.
	 * 
	 * <pre>
	 * 	Examples:
	 * 		12.01.2009 15:14 to 12.02.2009 15:13 will return 0
	 * 		14.12.2008 10:00 to 09.02.2009 10:00 will return 1
	 * </pre>
	 * 
	 * @return
	 */
	public int getMonthDifference() {

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(start);
		int yearDifference = endCalendar.get(Calendar.YEAR)
				- startCalendar.get(Calendar.YEAR);

		calendar.roll(Calendar.YEAR, yearDifference);
		// the difference is less than one year.
		if (calendar.getTime().compareTo(end) > 0) {
			yearDifference = yearDifference - 1;
			calendar.roll(Calendar.YEAR, -1);
		}
		for (int i = 1, n = 12; i < n; i++) {
			calendar.roll(Calendar.MONTH, 1);
			if (calendar.get(Calendar.MONTH % 11) == 0) {
				calendar.roll(Calendar.YEAR, 1);
			}
			if (calendar.getTime().compareTo(end) > 0) {
				return yearDifference * 12 + i - 1;
			}
		}
		return 0;
	}

	public int getWeekDifference() {
		return (int) (getDuration() / (1000 * 60 * 60 * 24 * 7));
	}

	public static void main(final String[] args) {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.YEAR, 2001);
		start.set(Calendar.MONTH, 11);
		start.set(Calendar.DAY_OF_MONTH, 15);
		start.set(Calendar.HOUR, 1);
		start.set(Calendar.MINUTE, 12);

		Calendar end = Calendar.getInstance();
		end.set(Calendar.YEAR, 2002);
		end.set(Calendar.MONTH, 4);
		end.set(Calendar.DAY_OF_MONTH, 15);
		end.set(Calendar.HOUR, 1);
		end.set(Calendar.MINUTE, 11);

		TimeSpan ts = new TimeSpan(start.getTime(), end.getTime());
		System.out.println(ts.getMonthDifference());
	}
}