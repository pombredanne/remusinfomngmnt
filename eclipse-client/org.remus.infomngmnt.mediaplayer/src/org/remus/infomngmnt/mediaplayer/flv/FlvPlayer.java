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

package org.remus.infomngmnt.mediaplayer.flv;

import java.util.Map;

import org.eclipse.core.runtime.IPath;

import org.remus.infomngmnt.jslib.extension.CheckResourceReferenceJob;
import org.remus.infomngmnt.mediaplayer.extension.AbstractMediaPlayer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FlvPlayer extends AbstractMediaPlayer {

	public static final String REF_RES_1 = "org.remus.infomngmnt.mediaplayer.swfobjects"; //$NON-NLS-1$
	public static final String REF_RES_2 = "org.remus.infomngmnt.jslib.browserReferenceResource"; //$NON-NLS-1$

	/**
	 * 
	 */
	public FlvPlayer() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.mediaplayer.extension.IMediaPlayer#buildHtml(org
	 * .eclipse.core.runtime.IPath, int, int, java.util.Map)
	 */
	public String buildHtml(final IPath mediaFilePath, final int widht, final int height,
			final Map<String, String> options) {
		String htmlString = "<div id=\"flvplayer\"></div>\r\n"
				+ "<script type=\"text/javascript\">\r\n" + "	var so = new SWFObject(\""
				+ CheckResourceReferenceJob.map.get(REF_RES_2) + "\", \"swfplayer\", \"" + widht
				+ "\", \"" + height + "\", \"9\", \"#000000\");\r\n"
				+ "	so.addVariable(\"flv\", \"" + mediaFilePath.toOSString() + "\");\r\n"
				+ "	so.addVariable(\"jpg\",\"imagen.jpg\");\r\n"
				+ "	//so.addVariable(\"autoplay\",\"true\");\r\n"
				+ "	so.addParam(\"allowFullScreen\",\"true\");\r\n"
				+ "	so.write(\"flvplayer\");\r\n" + "</script>";
		return htmlString;
	}

	@Override
	public String buildHeaderScript() {
		return "<script src=\"" + CheckResourceReferenceJob.map.get(REF_RES_1)
				+ "\" type=\"text/javascript\"></script>";
	}
}
