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

package org.remus.infomngmnt.connector.youtube;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import org.remus.infomngmnt.common.core.streams.StreamUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SiteInspector {
	public static final String REGEXP_STRING = "(\"fmt_url_map\": \")(.+)(\")";

	public static Map<String, String> getUrlMap(final IFile file) throws CoreException {
		InputStream contents = file.getContents();
		String convertStreamToString = StreamUtil.convertStreamToString(contents);

		Pattern compile = Pattern.compile(REGEXP_STRING);
		Matcher matcher = compile.matcher(convertStreamToString);
		Map<String, String> returnValue = new HashMap<String, String>();
		if (matcher.find()) {
			String escapeJavaScript;
			try {
				escapeJavaScript = URLDecoder.decode(matcher.group(2), "UTF-8");
				String[] split = StringUtils.split(escapeJavaScript, ",");
				for (String string : split) {
					String[] split2 = StringUtils.split(string, "|");
					if (split2.length == 2) {
						String data = split2[1];
						if (data.endsWith("\"")) {
							data = data.substring(0, data.length() - 1);
						}
						returnValue.put(split2[0], data);
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	public static String getId(final String url) {
		int lastIndexOf = url.lastIndexOf("v=");
		return url.substring(lastIndexOf + 2);
	}

}
