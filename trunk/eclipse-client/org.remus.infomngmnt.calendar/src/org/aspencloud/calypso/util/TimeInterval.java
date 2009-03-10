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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeInterval {

	public static final int FOREVER = -1;
	private static final Calendar tmpcal = Calendar.getInstance();

	public static final int YEARS 		= 0;
	public static final int MONTHS 		= 1;
	public static final int WEEKS 		= 2;
	public static final int DAYS 		= 3;
	public static final int HOURS 		= 4;
	public static final int MINUTES		= 5;
	public static final int SECONDS		= 6;
	public static final int MILLIS 		= 7;
	
	public static final String[] FIELDS = new String[] {
		"year",
		"month",
		"week",
		"day",
		"hour",
		"minute",
		"second",
		"millisecond"
	};

	// adjustments
	public int millis;
	public int seconds;
	public int minutes;
	public int weeks;
	public int hours;
	public int days;
	public int months;
	public int years;
	// absolutes
	public int milliOfSecond;
	public int secondOfMinute;
	public int minuteOfHour;
	public int hourOfDay;
	public int dayOfWeek;
	public int dayOfMonth;
	public int dayOfYear;
	public int weekOfMonth;
	public int weekOfYear;
	public int monthOfYear;
	// recurrence
	public int periods = 0;
	private int[] skip = new int[0];
	private Date firstPeriod;

	/** 
	 * from TimeInterval to "18 minutes and 31 seconds"
	 */
	public static String format(TimeInterval interval) {
		String str = "";
		for(int i = 0; i < FIELDS.length; i++) {
			int val = interval.getField(i);
			if(val > 0) {
				if(str.length() > 0) str += " ";
				str += Integer.toString(interval.getField(i)) + " " + FIELDS[i];
				if(val > 1)  str +=  "s";
			}
		}
		return (str.length() == 0) ? "immediately" : str;
	}
	
	/** 
	 * from "18 minutes and 31 seconds" to TimeInterval
	 */
	public static TimeInterval parse(String string) {
		TimeInterval interval = new TimeInterval();
		if(string == null) return interval;
		
		ArrayList l = new ArrayList();
		Matcher matcher = Pattern.compile("\\b\\d+ \\w+\\b").matcher(string);
		while(matcher.find()) {
			l.add(matcher.group());
		}

		for(Iterator i = l.iterator(); i.hasNext(); ) {
			String[] strs = ((String) i.next()).split(" ");
			int value = Integer.parseInt(strs[0]);
			if(value > 1 && strs[1].endsWith("s")) strs[1] = strs[1].substring(0, strs[1].length()-1);
			int units = -1;
			Pattern pattern = Pattern.compile(strs[1] + ".*");
			for(int j = 0; j < TimeInterval.FIELDS.length; j++) {
				if(pattern.matcher(FIELDS[j]).matches()) {
					units = j;
					break;
				}
			}
			if(value > 0 && units >= 0) {
				interval.setField(units, value);
			}
		}
		
		return interval;
	}
	
	
	public TimeInterval() {
		reset();
	}
	public TimeInterval(Date start, Date end) {
		tmpcal.setTime(start);
		Calendar tmpcal2 = Calendar.getInstance();
		tmpcal2.setTime(end);
		
		years = tmpcal2.get(Calendar.YEAR) - tmpcal.get(Calendar.YEAR);
		months = tmpcal2.get(Calendar.MONTH) - tmpcal.get(Calendar.MONTH);
		days = tmpcal2.get(Calendar.DATE) - tmpcal.get(Calendar.DATE);
		hours = tmpcal2.get(Calendar.HOUR_OF_DAY) - tmpcal.get(Calendar.HOUR_OF_DAY);
		// TODO time hack - need to rework!
		if(hours < 0) { days--; hours += 24; }
		minutes = tmpcal2.get(Calendar.MINUTE) - tmpcal.get(Calendar.MINUTE);
		if(minutes < 0) { hours--; minutes += 60; }
		seconds = tmpcal2.get(Calendar.SECOND) - tmpcal.get(Calendar.SECOND);
		if(seconds < 0) { minutes--; seconds += 60; }
		millis = tmpcal2.get(Calendar.MILLISECOND) - tmpcal.get(Calendar.MILLISECOND);
		if(millis < 0) { seconds--; millis += 1000; }
	}
	public TimeInterval(int hours, int minutes) {
		reset();
		this.hours = hours;
		this.minutes = minutes;
	}
	public TimeInterval(int years, int months, int days) {
		reset();
		this.years = years;
		this.months = months;
		this.days = days;
	}
	public TimeInterval(int years, int months, int days, int hours, int minutes, int seconds, int millis) {
		reset();
		this.years = years;
		this.months = months;
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		this.millis = millis;
	}
	/**
	 * used by EMF model "CEvent" to deserialize
	 */
	public TimeInterval(String initialValue) {
		reset();
		
		String[] strs = initialValue.split(":");
		int val;
		int i = 0;
		for(; i < FIELDS.length && i < strs.length; i++) {
			// adjustments
			val = Integer.parseInt(strs[i]);
			setField(i, val);
		}
		for(; i < strs.length; i++) {
			try {
				val = Integer.parseInt(strs[i]);
				switch (i) {
				// absolutes
				case  7: milliOfSecond = val; break;
				case  8: secondOfMinute = val; break;
				case  9: minuteOfHour = val; break;
				case 11: hourOfDay = val; break;
				case 12: dayOfWeek = val; break;
				case 13: dayOfMonth = val; break;
				case 14: dayOfYear = val; break;
				case 15: weekOfMonth = val; break;
				case 16: weekOfYear = val; break;
				case 17: monthOfYear = val; break;
				// recurrence
				case 18: periods = val; break;
				}
			} catch (Exception e) {}
		}
	}

	
	public Date[] getAll() {
		return getAll(firstPeriod, 0);
	}
	
	public Date[] getAll(Date date, int period) {
		if(period >= 0 && period < periods && date != null) {
			period = -period;
			Date[] dates = new Date[periods - skip.length];
			for(int i = 0, d = 0; i < periods; i++) {
				boolean doit = true;
				for(int j = 0; j < skip.length; j++) {
					if(skip[j] == i) {
						doit = false;
						break;
					}
				}
				if(doit) dates[d++] = getDate(date, period++);
			}
			return dates;
		}
		return new Date[0];
	}
	
	private Date getDate(Date date, int dir) {
		tmpcal.setTime(date);

		// add adjustments
		if(years > 0) 	tmpcal.add(Calendar.YEAR, 			dir*years);
		if(months > 0) 	tmpcal.add(Calendar.MONTH, 			dir*months);
		if(days > 0) 	tmpcal.add(Calendar.DATE, 			dir*days);
		if(hours > 0) 	tmpcal.add(Calendar.HOUR_OF_DAY, 	dir*hours);
		if(minutes > 0) tmpcal.add(Calendar.MINUTE, 			dir*minutes);
		if(seconds > 0) tmpcal.add(Calendar.SECOND, 			dir*seconds);
		if(millis > 0) 	tmpcal.add(Calendar.MILLISECOND,		dir*millis);
		// set absolutes
		if(monthOfYear >= 0) 	tmpcal.set(Calendar.MONTH, 			monthOfYear);
		if(weekOfYear >= 0) 		tmpcal.set(Calendar.WEEK_OF_YEAR, 	weekOfYear);
		if(weekOfMonth >= 0) 	tmpcal.set(Calendar.WEEK_OF_MONTH, 	weekOfMonth);
		if(dayOfYear >= 0) 		tmpcal.set(Calendar.DAY_OF_YEAR, 	dayOfMonth);
		if(dayOfMonth >= 0) 		tmpcal.set(Calendar.DAY_OF_MONTH, 	dayOfMonth);
		if(dayOfWeek >= 0) 		tmpcal.set(Calendar.DAY_OF_WEEK, 	dayOfWeek);
		if(hourOfDay >= 0) 		tmpcal.set(Calendar.HOUR_OF_DAY, 	hourOfDay);
		if(minuteOfHour >= 0) 	tmpcal.set(Calendar.MINUTE, 			minuteOfHour);
		if(secondOfMinute >= 0) tmpcal.set(Calendar.SECOND, 			secondOfMinute);
		if(milliOfSecond >= 0) 	tmpcal.set(Calendar.MILLISECOND,		milliOfSecond);
		
		return tmpcal.getTime();
	}
	
	public Date getNext(Date date) {
		return getDate(date, 1);
	}

	public Date getNextPeriod(Date date) {
		return null;
	}
	
	public int getPeriod(Date date) {
		if(periods > 0 && date != null) {
//			Date d = getDate(date, 0);
//			getAll();
		}
		return -1;
	}
	
	public Date getPeriodDate(int period) {
		if(period >= 0 && period < periods && firstPeriod != null) {
			return getDate(firstPeriod, period);
		}
		return null;
	}
	
	public int getPeriods() {
		return periods;
	}
	
	public int[] getPeriodsToSkip() {
		return skip;
	}
	
	public Date getPrevious(Date date) {
		return getDate(date, -1);
	}

	public void reset() {
		millis = 0;
		seconds = 0;
		minutes = 0;
		hours = 0;
		days = 0;
		months = 0;
		years = 0;
		milliOfSecond = -1;
		secondOfMinute = -1;
		minuteOfHour = -1;
		hourOfDay = -1;
		dayOfWeek = -1;
		dayOfMonth = -1;
		dayOfYear = -1;
		weekOfMonth = -1;
		weekOfYear = -1;
		monthOfYear = -1;
		periods = 0;
	}

	/**
	 * @return the value of the field
	 */
	public int getField(int field) {
		switch(field) {
		case YEARS: 		return years;
		case MONTHS:		return months;
		case WEEKS:		return weeks;
		case DAYS:		return days;
		case HOURS:		return hours;
		case MINUTES:	return minutes;
		case SECONDS:	return seconds;
		case MILLIS:		return millis;
		default: return -1;
		}
	}

	// TODO account for all variables...
	public boolean isInstant() {
		boolean result = true;
		for(int i = 0; i < FIELDS.length; i++) {
			if(getField(i) > 0) {
				result = false;
				break;
			}
		}
		return result;
	}
	
	/**
	 * @param field the field to be set
	 * @param value the value to assign the field
	 */
	public void setField(int field, int value) {
		switch(field) {
		case YEARS: 		years = value; break;
		case MONTHS:		months = value; break;
		case WEEKS:		weeks = value; break;
		case DAYS:		days = value; break;
		case HOURS:		hours = value; break;
		case MINUTES:	minutes = value; break;
		case SECONDS:	seconds = value; break;
		case MILLIS:		millis = value; break;
		}
	}
	
	public void setPeriodDate(int period, Date date) {
		if(period >= 0 && period < periods) {
			firstPeriod = (date == null) ? date : getDate(date, -period);
		}
	}

	public void setPeriods(int repeat) {
		this.periods = (repeat < 0) ? FOREVER : repeat;
	}

	public void setPeriodsToSkip(int[] skip) {
		this.skip = skip;
	}
	
	/**
	 * used by EMF model "CEvent" to serialize
	 */
	@Override
	public String toString() {
		String separator = ":";
		StringBuffer sb = new StringBuffer();
		
		// adjustments
		for(int i = 0; i < FIELDS.length; i++) {
			sb.append(getField(i));
			sb.append(separator);
		}
		// absolutes
		sb.append(separator);
		sb.append(milliOfSecond);
		sb.append(separator);
		sb.append(secondOfMinute);
		sb.append(separator);
		sb.append(minuteOfHour);
		sb.append(separator);
		sb.append(hourOfDay);
		sb.append(separator);
		sb.append(dayOfWeek);
		sb.append(separator);
		sb.append(dayOfMonth);
		sb.append(separator);
		sb.append(dayOfYear);
		sb.append(separator);
		sb.append(weekOfMonth);
		sb.append(separator);
		sb.append(weekOfYear);
		sb.append(separator);
		sb.append(monthOfYear);
		// recurrence
		sb.append(separator);
		sb.append(periods);
		
		return 	sb.toString();
	}
}
