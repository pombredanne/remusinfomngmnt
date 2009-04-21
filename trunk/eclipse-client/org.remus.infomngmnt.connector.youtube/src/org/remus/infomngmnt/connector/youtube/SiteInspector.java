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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

import org.remus.infomngmnt.common.core.streams.StreamUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SiteInspector {
	public static final String REGEXP_STRING = "(fullscreenUrl\\W=\\W.*t=)([^&]+)(.*)";

	public static String getHash(final IFile file) throws CoreException {
		InputStream contents = file.getContents();
		String convertStreamToString = StreamUtil.convertStreamToString(contents);

		Pattern compile = Pattern.compile(REGEXP_STRING);
		Matcher matcher = compile.matcher(convertStreamToString);
		if (matcher.find()) {
			return matcher.group(2);
		}
		// if (matcher.groupCount() == 3) {
		// return matcher.toMatchResult().group(2);
		// }

		return null;
	}

	public static String getId(final String url) {
		int lastIndexOf = url.lastIndexOf("v=");
		return url.substring(lastIndexOf + 2);
	}

}
