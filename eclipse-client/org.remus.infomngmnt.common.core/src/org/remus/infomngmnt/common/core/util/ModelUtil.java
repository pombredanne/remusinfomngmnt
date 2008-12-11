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

package org.remus.infomngmnt.common.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ModelUtil {
	public static List getFeatureList(EList<? extends EObject> list, EAttribute attribute) {
		List returnValue = new ArrayList();
		for (EObject object : list) {
			Object get = object.eGet(attribute);
			if (get != null) {
				returnValue.add(get);
			}
		}
		return returnValue;
	}

	public static Map<String, ? extends EObject> buildMap(EList<? extends EObject> list, EAttribute decisionAtt) {
		Map<String, EObject> returnValue = new HashMap<String, EObject>();
		for (EObject object : list) {
			Object get = object.eGet(decisionAtt);
			if (get != null && get instanceof String) {
				returnValue.put((String)get, object);
			}
		}
		return returnValue;
	}
}
