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

package org.remus.infomngmnt.connector.facebook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SessionKeyInspector {

	public static String getSessionKey(final String url) {
		Pattern compile = Pattern.compile("\"session_key\":\"([^\"]+)\"");
		Matcher matcher = compile.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public static String getSecret(final String url) {
		Pattern compile = Pattern.compile("\"secret\":\"([^\"]+)\"");
		Matcher matcher = compile.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}
