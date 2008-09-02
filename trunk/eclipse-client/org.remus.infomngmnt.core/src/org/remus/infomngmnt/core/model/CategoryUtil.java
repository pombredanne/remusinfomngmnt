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

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnitListItem;
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
	 * 
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

	public static IStatus isCategoryPathStringValid(String str) {
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

	public static IStatus isCategoryNameValid(String str) {
		if (str.trim().length() == 0) {
			return StatusCreator.newStatus("At least 1 character is mandatory");
		}
		if (str.indexOf("/") != -1) {
			return StatusCreator.newStatus("The character \'/\' is not allowed");
		}
		return Status.OK_STATUS;
	}

	public static Category findCategory(String str, boolean createMissingFragments) {
		String[] split = str.split("/");
		Category parentCategory = null;
		for (int i = 0, n = split.length; i < n; i++) {
			if (i == 0) {
				EList<Category> rootCategories = ApplicationModelPool.getInstance().getModel().getRootCategories();
				for (Category category : rootCategories) {
					if (category.getLabel().equals(split[i])) {
						parentCategory = category;
					}
				}
			} else {
				boolean found = false;
				EList<Category> children = parentCategory.getChildren();
				for (Category category : children) {
					if (category.getLabel().equals(split[i])) {
						parentCategory = category;
						found = true;
						break;
					}
				}

				if (!found && createMissingFragments) {
					Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
					createCategory.setId(new UniversalUniqueIdentifier().toString());
					createCategory.setLabel(split[i]);
					parentCategory.getChildren().add(createCategory);
					parentCategory = createCategory;
				} else if (!found && !createMissingFragments){
					return null;
				}

			}
		}
		return parentCategory;
	}

	/**
	 * Checks if the new category is valid to the given path. If another
	 * category <b>or</b> an information unit with the same name is present
	 * as a child of the parentCategory (category will be resolved with
	 * {@link CategoryUtil#findCategory(String, boolean)} this method returns an
	 * Error status. This is important to keep the unique identification of an
	 * element with a slash-separted path.
	 * 
	 * @param parentCategoryPath the path of the parentCategory
	 * @param newCategoryName the new category name
	 * @return
	 */
	public static IStatus isCategoryStructureValid(String parentCategoryPath,
			String newCategoryName) {
		if (findCategory(parentCategoryPath + "/" + newCategoryName, false) != null) {
			return StatusCreator.newStatus("Category already exisits");
		}
		Category findCategory = findCategory(parentCategoryPath, false);
		if (findCategory != null) {
			EList<InformationUnitListItem> informationUnit = findCategory.getInformationUnit();
			for (InformationUnitListItem informationUnitListItem : informationUnit) {
				if (informationUnitListItem.getLabel().equals(newCategoryName)) {
					return StatusCreator.newStatus("An information item with this name alreasy exists.");
				}
			}
		}
		return Status.OK_STATUS;
	}

	public static IProject getProjectByCategory(Category category) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(category.eResource().getURI().toPlatformString(true))).getProject();
	}



}
