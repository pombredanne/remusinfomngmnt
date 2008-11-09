/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.jslib;

import java.io.IOException;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TemplateLocation {

	public static final String BUNDLE_ID = "org.remus.infomngmnt.jslib"; //$NON-NLS-1$

	public static String getLoadingUrl() {
		try {
			return FileLocator.toFileURL(FileLocator.find(Platform.getBundle(BUNDLE_ID), new Path("templates/loading.html"), null)).toExternalForm();
		} catch (IOException e) {
			return null;
		}
	}

}
