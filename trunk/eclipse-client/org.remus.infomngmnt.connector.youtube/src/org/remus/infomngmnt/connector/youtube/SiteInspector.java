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
import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.remus.common.core.streams.StreamUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SiteInspector {
	public static final String REGEXP_STRING = "(\"fmt_url_map\": \")(.+)(\")"; //$NON-NLS-1$

	public static Map<String, String> getUrlMap(final IFile file)
			throws CoreException {
		InputStream contents = file.getContents();
		String convertStreamToString = StreamUtil
				.convertStreamToString(contents);

		Pattern compile = Pattern.compile("(fmt_url_map=)(.+)(&)"); //$NON-NLS-1$
		Matcher matcher = compile.matcher(convertStreamToString);
		Map<String, String> returnValue = new HashMap<String, String>();
		if (matcher.find()) {
			String escapeJavaScript;
			try {
				escapeJavaScript = URLDecoder.decode(matcher.group(2), "UTF-8"); //$NON-NLS-1$
				String[] split = StringUtils.split(escapeJavaScript, ","); //$NON-NLS-1$
				for (String string : split) {
					String[] split2 = StringUtils.split(string, "|"); //$NON-NLS-1$
					if (split2.length == 2) {
						String data = split2[1];
						if (data.endsWith("\"")) { //$NON-NLS-1$
							data = data.substring(0, data.length() - 1);
						}
						returnValue.put(split2[0], data);
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// RIMCONNECTORS-24 old pattern does not work everywhere.
			compile = Pattern.compile("(fmt_url_map=)(.+)(&)"); //$NON-NLS-1$
			matcher = compile.matcher(convertStreamToString);
			if (matcher.find()) {
				String escapeJavaScript;
				try {
					escapeJavaScript = URLDecoder.decode(matcher.group(2),
							"UTF-8"); //$NON-NLS-1$
					String[] split = StringUtils.split(escapeJavaScript, ","); //$NON-NLS-1$
					for (String string : split) {
						String[] split2 = StringUtils.split(string, "|"); //$NON-NLS-1$
						if (split2.length == 2) {
							String data = split2[1];
							if (data.endsWith("\"")) { //$NON-NLS-1$
								data = data.substring(0, data.length() - 1);
							}
							returnValue.put(split2[0], data);
						}
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				compile = Pattern
						.compile("(url_encoded_fmt_stream_map=)(.+)(&)"); //$NON-NLS-1$
				matcher = compile.matcher(convertStreamToString);
				if (matcher.find()) {
					String escapeJavaScript;
					try {
						escapeJavaScript = URLDecoder.decode(matcher.group(2),
								"UTF-8"); //$NON-NLS-1$
						String[] split = StringUtils.split(escapeJavaScript,
								","); //$NON-NLS-1$
						for (String string : split) {
							if (string.startsWith("url=")) {
								string = string.replaceFirst("url=", "");
								String data = URLDecoder
										.decode(string, "UTF-8");; //$NON-NLS-1$
								data = data.replaceAll(";.*$", "");

								if (data.endsWith("\"")) { //$NON-NLS-1$
									data = data.substring(0, data.length() - 1);
								}
								if (data.indexOf("&type=video/x-flv") != -1
										|| data.indexOf("&type=video/mp4") != -1) {
									if (data.indexOf("&quality=hd1080") != -1) {
										returnValue.put("22", data);
									}
									if (data.indexOf("&quality=hd720") != -1) {
										returnValue.put("21", data);
									}
									if (data.indexOf("&quality=large") != -1) {
										returnValue.put("20", data);
									}
									if (data.indexOf("&quality=medium") != -1) {
										returnValue.put("19", data);
									}
									if (data.indexOf("&quality=small") != -1) {
										returnValue.put("05", data);
									}

								}
							}

						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		TreeMap<String, String> treeMap = new TreeMap<String, String>(
				new Comparator<String>() {

					public int compare(String o1, String o2) {
						return Collator.getInstance().compare(o1, o2);
					}
				});
		treeMap.putAll(returnValue);
		return treeMap;
	}

	public static String getId(final String url) {
		int lastIndexOf = url.lastIndexOf("v="); //$NON-NLS-1$
		return url.substring(lastIndexOf + 2);
	}

}
