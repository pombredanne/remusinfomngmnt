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

package org.remus.infomngmnt.mediaplayer.avi;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.core.runtime.IPath;
import org.remus.infomngmnt.mediaplayer.extension.AbstractMediaPlayer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AviPlayer extends AbstractMediaPlayer {

	/**
	 * 
	 */
	public AviPlayer() {
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
		return "<p align=\"center\"><embed src=\"" //$NON-NLS-1$
				+ StringEscapeUtils.escapeHtml(mediaFilePath.toOSString())
				+ "\" autoplay=\"false\"\r\n" + "fullscreen=\"full\"\r\n" //$NON-NLS-1$ //$NON-NLS-2$
				+ "quitwhendone=\"true\"height=\"" + (height == 0 ? 300 : height) + "\" width=\"" //$NON-NLS-1$ //$NON-NLS-2$
				+ (widht == 0 ? 480 : widht) + "\"></p>"; //$NON-NLS-1$
	}

}
