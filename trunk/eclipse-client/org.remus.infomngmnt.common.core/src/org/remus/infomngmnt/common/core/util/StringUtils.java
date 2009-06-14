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

package org.remus.infomngmnt.common.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class StringUtils {

	public static String join(final String... string) {
		return org.apache.commons.lang.StringUtils.join(string);
	}

	public static String surroundMatchingInString(String sourceString, final String[] matchings,
			final String pre[], final String post[]) {
		if (matchings.length != pre.length && matchings.length != post.length) {
			throw new IllegalArgumentException("Wrong input data");
		}
		for (int i = 0, n = matchings.length; i < n; i++) {
			String patternString = matchings[i];
			if (patternString.trim().length() > 0) {
				Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(sourceString);
				int matchcount = 0;
				while (matcher.find()) {
					int shift = (pre[i].length() + post[i].length()) * matchcount;
					StringBuilder sb = new StringBuilder();
					sourceString = sb.append(sourceString.substring(0, matcher.start() + shift))
							.append(pre[i]).append(
									sourceString.substring(matcher.start() + shift, matcher.end()
											+ shift)).append(post[i]).append(
									sourceString.substring(matcher.end() + shift, sourceString
											.length())).toString();
					matchcount++;
				}
			}
		}
		return sourceString;
	}

}
