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

package org.remus.infomngmnt.core.services;

import java.awt.geom.Point2D;
import java.util.Map;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IGeoData {

	public static final String KEY_POST_OFFICE_BOX = "KEY_POST_OFFICE_BOX"; //$NON-NLS-1$
	public static final String KEY_LOCALITY = "KEY_LOCALITY"; //$NON-NLS-1$
	public static final String KEY_POST_CODE = "KEY_POST_CODE"; //$NON-NLS-1$
	public static final String KEY_REGION = "KEY_REGION"; //$NON-NLS-1$
	public static final String KEY_STREET = "KEY_STREET"; //$NON-NLS-1$

	String canRetreiveGeoData();

	Point2D getCoordinates(Map<String, Object> values) throws Exception;

	String getApiKey();

}
