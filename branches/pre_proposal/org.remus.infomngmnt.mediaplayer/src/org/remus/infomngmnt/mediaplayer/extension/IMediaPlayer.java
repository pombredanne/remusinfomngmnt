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

package org.remus.infomngmnt.mediaplayer.extension;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IMediaPlayer {

	String getId();

	String getName();

	List<String> getSupportedMediaTypes();

	String buildHtml(IPath mediaFilePath, int widht, int height, Map<String, String> options);

	String buildHeaderScript();

}
