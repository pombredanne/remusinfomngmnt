/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.model.service;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.xmi.XMLResource;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ResourceConstants {

	public static Map<String, Object> SAVE_OPTIONS;
	public static final String FILEHISTORYKEEPINGSCHEME = "file"; //$NON-NLS-1$

	static {
		SAVE_OPTIONS = new HashMap<String, Object>();
		SAVE_OPTIONS.put(XMLResource.OPTION_ENCODING, "UTF-8");
		SAVE_OPTIONS.put(XMLResource.OPTION_FLUSH_THRESHOLD, 4096);
		SAVE_OPTIONS.put(XMLResource.OPTION_USE_FILE_BUFFER, true);
	}

}
