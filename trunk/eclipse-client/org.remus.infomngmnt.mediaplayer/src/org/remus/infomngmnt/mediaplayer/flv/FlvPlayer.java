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
import org.eclipse.remus.js.extension.CheckResourceReferenceJob;
import org.remus.infomngmnt.mediaplayer.extension.AbstractMediaPlayer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FlvPlayer extends AbstractMediaPlayer {

	public static final String REF_RES_1 = "org_remus_infomngmnt_mediaplayer_playerFlashSWF"; //$NON-NLS-1$
	public static final String REF_RES_2 = "org_remus_infomngmnt_mediaplayer_swfobjects"; //$NON-NLS-1$

	protected String playerType = "flv"; //$NON-NLS-1$

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
	public String buildHtml(final IPath mediaFilePath, final int widht,
			final int height, final Map<String, String> options) {
		String htmlString = "<div id=\"gsplayer\" style=\"margin-top:10px;text-align: center\">\r\n<script type=\"text/javascript\">\r\n" //$NON-NLS-1$
				+ "	var html = swf(\"gsplayer\",\"" //$NON-NLS-1$
				+ CheckResourceReferenceJob.map.get(REF_RES_1)
				+ "\",\"" //$NON-NLS-1$
				+ (widht == 0 ? 480 : widht)
				+ "\",\"" //$NON-NLS-1$
				+ (height == 0 ? 300 : height)
				+ "\",{path:\"" //$NON-NLS-1$
				+ mediaFilePath.toOSString().replaceAll("\\\\", "\\\\\\\\") //$NON-NLS-1$ //$NON-NLS-2$
				+ "\",type:\"" //$NON-NLS-1$
				+ playerType
				+ "\",fullscreen:'true'},{allowfullscreen:\"true\",allowScriptAccess:\"sameDomain\"});\r\n" //$NON-NLS-1$
				+ "\r\ndocument.write(html);\r\n" + "</script></div>"; //$NON-NLS-1$ //$NON-NLS-2$
		return htmlString;
	}

	@Override
	public String buildHeaderScript() {
		return "<script src=\"" + CheckResourceReferenceJob.map.get(REF_RES_2) //$NON-NLS-1$
				+ "\" type=\"text/javascript\"></script>"; //$NON-NLS-1$
	}
}
