/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.eclipsemarketplace.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Some simple utility functions for analyzing responses from the API
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MarketPlaceUtils {

	public static final String UTF_8 = "UTF-8"; //$NON-NLS-1$

	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator"); //$NON-NLS-1$

	/**
	 * Check to see if the input is <code>null</code> or blank
	 * 
	 * @param input
	 *            Input
	 * @return <code>true</code> if input is null or blank, <code>false</code>
	 *         otherwise
	 */
	public static boolean checkNullOrBlank(final String input) {
		return (input == null || "".equals(input)); //$NON-NLS-1$
	}

	/**
	 * Encode input using UTF-8
	 * 
	 * @param input
	 *            Input
	 * @return Input encoded using UTF-8 or <code>null</code> if input was null
	 */
	public static String encodeUTF8(final String input) {
		return encodeUTF8(input, false);
	}

	/**
	 * Encod input as UTF-8 while converting %20 (space) to a space
	 * 
	 * @param input
	 *            Input
	 * @param keepSpaces
	 *            <code>true</code> if spaces should be preserved,
	 *            <code>false</code> otherwise
	 * @return Input encoded using UTF-8 or <code>null</code> if input was null
	 */
	public static String encodeUTF8(final String input, final boolean keepSpaces) {
		if (input == null) {
			return null;
		}

		String encodedInput;

		try {
			encodedInput = URLEncoder.encode(input, UTF_8);
		} catch (UnsupportedEncodingException e) {
			return input;
		}

		if (keepSpaces) {
			encodedInput = encodedInput.replaceAll("[+]", " "); //$NON-NLS-1$ //$NON-NLS-2$
		}

		return encodedInput;
	}

	public static boolean convertNumberToBoolean(final String str) {
		if ("1".equals(str)) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	/**
	 * Parses a string to and tries to create a date by the pattern
	 * <code>EEE MMM dd HH:mm:ss Z yyyy</code>
	 * 
	 * @param the
	 *            string to parse
	 * @return date the parsed date, <code>null</code> if date was not parsable.
	 */
	public static Date convertStringToDate(final String date) {
		String pattern = "EEE MMM dd HH:mm:ss Z yyyy"; //$NON-NLS-1$
		Locale locale = Locale.US;
		try {
			return new SimpleDateFormat(pattern, locale).parse(date);
		} catch (ParseException e) {

		}
		return null;
	}

}
