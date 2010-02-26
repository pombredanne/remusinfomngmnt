/*******************************************************************************
 * Copyright (c) Emil Crumhorn - Hexapixel.com - emil.crumhorn@gmail.com
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    emil.crumhorn@gmail.com - initial API and implementation
 *******************************************************************************/ 

package org.eclipse.nebula.widgets.calendarcombo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateHelper {

	private static final long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;
	
	public static long daysBetween(Calendar start, Calendar end, Locale locale) {		
		// create copies
		GregorianCalendar startDate = new GregorianCalendar(locale);
		GregorianCalendar endDate = new GregorianCalendar(locale);

		// switch calendars to pure Julian mode for correct day-between calculation, from the Java API:
		// - To obtain a pure Julian calendar, set the change date to Date(Long.MAX_VALUE).
		startDate.setGregorianChange(new Date(Long.MAX_VALUE));
		endDate.setGregorianChange(new Date(Long.MAX_VALUE));

		// set them
		startDate.setTime(start.getTime());
		endDate.setTime(end.getTime());

		// force times to be exactly the same
		startDate.set(Calendar.HOUR_OF_DAY, 12);
		endDate.set(Calendar.HOUR_OF_DAY, 12);
		startDate.set(Calendar.MINUTE, 0);
		endDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		endDate.set(Calendar.SECOND, 0);
		startDate.set(Calendar.MILLISECOND, 0);
		endDate.set(Calendar.MILLISECOND, 0);
	
		// now we should be able to do a "safe" millisecond/day caluclation to get the number of days
		long endMilli = endDate.getTimeInMillis();
		long startMilli = startDate.getTimeInMillis();

		// calculate # of days, finally
		long diff = (endMilli - startMilli) / MILLISECONDS_IN_DAY;
		
		return diff;
	}

	public static long daysBetween(Date start, Date end, Locale locale) {
		Calendar dEnd = Calendar.getInstance(locale);
		Calendar dStart = Calendar.getInstance(locale);
		dEnd.setTime(end);
		dStart.setTime(start);
		return daysBetween(dStart, dEnd, locale);
	}
	
    public static boolean isToday(Date date, Locale locale) {
        Calendar cal = Calendar.getInstance(locale);
        cal.setTime(date);

        return isToday(cal, locale);
    }

    public static boolean isToday(Calendar cal, Locale locale) {
        Calendar today = Calendar.getInstance(locale);

        if (today.get(Calendar.YEAR) == cal.get(Calendar.YEAR)) {
            if (today.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR)) {
                return true;
            }
        }

        return false;
    }

    public static String getDate(Calendar cal, String dateFormat) {
        Calendar toUse = (Calendar) cal.clone();
        toUse.add(Calendar.MONTH, -1);

        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        df.setLenient(true);
        return df.format(cal.getTime());
    }
    
    public static boolean sameDate(Date date1, Date date2, Locale locale) {
        Calendar cal1 = Calendar.getInstance(locale);
        Calendar cal2 = Calendar.getInstance(locale);

        cal1.setTime(date1);
        cal2.setTime(date2);

        return sameDate(cal1, cal2);
    }

    public static boolean sameDate(Calendar cal1, Calendar cal2) {
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                return true;
            }
        }

        return false;
    }
    
    public static Date getDate(String str, String dateFormat) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        df.setLenient(false);
        
        return df.parse(str);
    }
}
