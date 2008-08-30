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

package org.remus.infomngmnt.core.model;

import java.io.StringWriter;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * Categories are a major element within the Information
 * Management. There are several operation that can be
 * executed on categories. This class holds some helper
 * methods for validating/converting categories.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class CategoryUtil {

	/**
	 * Converts a category and its path to
	 * the root category to a human readable string,
	 * separated by '/'.
	 * <pre>
	 * Root
	 * 	Child1
	 * 		Child12
	 * </pre>
	 * is converted to Root/Child1/Child12
	 * @param category the category to convert to
	 * @return a string representation of the category and its
	 * parent categories
	 * @see EObject#eContainer()
	 */
	public static String categoryToString(Category category) {
		StringWriter sw = new StringWriter();
		if (category.eContainer() != null && category.eContainer() instanceof Category) {
			sw.append(categoryToString((Category) category.eContainer()));
			sw.append("/");
		}
		sw.append(category.getLabel());
		return sw.toString();
	}

	public static IStatus isCategoryStringValid(String str) {
		if (str.trim().length() == 0) {
			return StatusCreator.newStatus("No parent category");
		}
		String[] split = str.split("/");
		boolean relevantProject = ResourceUtil.isRelevantProject(ResourcesPlugin.getWorkspace().getRoot().getProject(split[0]));
		if (!relevantProject) {
			return StatusCreator.newStatus("The given root-category was not found");
		}
		return Status.OK_STATUS;
	}

}
