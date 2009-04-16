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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ModelUtil {

	public static List getFeatureList(final EList<? extends EObject> list,
			final EAttribute attribute) {
		List returnValue = new ArrayList();
		for (EObject object : list) {
			Object get = object.eGet(attribute);
			if (get != null) {
				returnValue.add(get);
			}
		}
		return returnValue;
	}

	public static Map<String, ? extends EObject> buildMap(final EList<? extends EObject> list,
			final EAttribute decisionAtt) {
		Map<String, EObject> returnValue = new HashMap<String, EObject>();
		for (EObject object : list) {
			Object get = object.eGet(decisionAtt);
			if (get != null && get instanceof String) {
				returnValue.put((String) get, object);
			}
		}
		return returnValue;
	}

	public static void copyObject(final EObject src, final EObject target,
			final EStructuralFeature[] featuresToSet, final EStructuralFeature[] featuresToCopy) {
		if (src.getClass() == target.getClass()) {
			for (EStructuralFeature eStructuralFeature : featuresToSet) {
				Object eGet = src.eGet(eStructuralFeature);
				target.eSet(eStructuralFeature, eGet);
			}
			for (EStructuralFeature eStructuralFeature : featuresToCopy) {
				Object eGet = src.eGet(eStructuralFeature);
				if (eGet instanceof EObject) {
					target.eSet(eStructuralFeature, EcoreUtil.copy((EObject) eGet));
				}
			}
		}
	}

	public static boolean hasEqualAttribute(final List<? extends EObject> list,
			final EAttribute attribute) {
		if (list.size() <= 1) {
			return true;
		}
		Object value2Compare = null;
		for (int i = 0, n = list.size(); i < n; i++) {
			EObject obj = list.get(i);
			if (i == 0) {
				value2Compare = obj.eGet(attribute);
			} else {
				if (value2Compare != null) {
					if (!value2Compare.equals(obj.eGet(attribute))) {
						return false;
					}
				} else {
					if (obj.eGet(attribute) != null) {
						return false;
					}
				}
			}

		}
		return true;
	}

	public static boolean containsParent(final List<? extends EObject> elements2check,
			final EObject element) {
		EObject element2resolve = element;
		while (element2resolve.eContainer() != null) {
			if (elements2check.contains(element2resolve.eContainer())) {
				return true;
			} else {
				element2resolve = element2resolve.eContainer();
			}
		}
		return false;
	}

	public static <T> List<T> getAllChildren(final EObject object, final EClass clazz) {
		List<T> returnValue = new LinkedList<T>();
		TreeIterator<EObject> eAllContents = object.eAllContents();
		while (eAllContents.hasNext()) {
			EObject eObject = eAllContents.next();
			if (eObject.eClass() == clazz) {
				returnValue.add((T) eObject);
			}
		}
		return returnValue;
	}

	public static EObject getItemByValue(final EList<? extends EObject> list,
			final EStructuralFeature feature, final Object value) {
		for (EObject object : list) {
			Object eGet = object.eGet(feature);
			if (eGet.equals(value)) {
				return object;
			}
		}
		return null;
	}

	public static Collection<EObject> convertFromArray(final Object[] array) {
		Collection<EObject> returnValue = new ArrayList<EObject>();
		for (Object object : array) {
			if (object instanceof EObject) {
				returnValue.add((EObject) object);
			}
		}
		return returnValue;
	}

}
