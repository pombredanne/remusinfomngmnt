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

package org.remus.infomngmnt.util;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.EObjectTypeRelationCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.core.util.CollectionUtils;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * Categories are a major element within the Information Management. There are
 * several operation that can be executed on categories. This class holds some
 * helper methods for validating/converting categories.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class CategoryUtil {

	/**
	 * Converts a category and its path to the root category to a human readable
	 * string, separated by '/'.
	 * 
	 * <pre>
	 * Root
	 * 	Child1
	 * 		Child12
	 * </pre>
	 * 
	 * is converted to Root/Child1/Child12
	 * 
	 * @param category
	 *            the category to convert to
	 * @return a string representation of the category and its parent categories
	 * @see EObject#eContainer()
	 */
	public static String categoryToString(final Category category) {
		StringWriter sw = new StringWriter();
		if (category.eContainer() != null && category.eContainer() instanceof Category) {
			sw.append(categoryToString((Category) category.eContainer()));
			sw.append("/");
		}
		sw.append(category.getLabel());
		return sw.toString();
	}

	public static IStatus isCategoryPathStringValid(final String str) {
		if (str.trim().length() == 0) {
			return StatusCreator.newStatus("No parent category");
		}
		String[] split = str.split("/");
		boolean relevantProject = ResourceUtil.isRelevantProject(ResourcesPlugin.getWorkspace()
				.getRoot().getProject(split[0]));
		if (!relevantProject) {
			return StatusCreator.newStatus("The given root-category was not found");
		}
		return Status.OK_STATUS;
	}

	public static IStatus isCategoryNameValid(final String str) {
		if (str.trim().length() == 0) {
			return StatusCreator.newStatus("At least 1 character is mandatory");
		}
		if (str.indexOf("/") != -1) {
			return StatusCreator.newStatus("The character \'/\' is not allowed");
		}
		return Status.OK_STATUS;
	}

	public static Category findCategory(final String str, final boolean createMissingFragments) {
		IApplicationModel appService = InfomngmntEditPlugin.getPlugin().getServiceTracker()
				.getService(IApplicationModel.class);
		String[] split = str.split("/");
		Category parentCategory = null;
		for (int i = 0, n = split.length; i < n; i++) {
			if (i == 0) {
				EList<Category> rootCategories = appService.getModel().getRootCategories();
				for (Category category : rootCategories) {
					if (category.getLabel() != null && category.getLabel().equals(split[i])) {
						parentCategory = category;
					}
				}
			} else {
				boolean found = false;
				if (parentCategory != null) {
					EList<Category> children = parentCategory.getChildren();
					for (Category category : children) {
						if (category.getLabel().equals(split[i])) {
							parentCategory = category;
							found = true;
							break;
						}
					}
				}

				if (!found && createMissingFragments) {
					Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
					createCategory.setId(IdFactory.createNewId(null));
					createCategory.setLabel(split[i]);
					parentCategory.getChildren().add(createCategory);
					parentCategory = createCategory;
				} else if (!found && !createMissingFragments) {
					return null;
				}

			}
		}
		return parentCategory;
	}

	/**
	 * Checks if the new category is valid to the given path. If another
	 * category <b>or</b> an information unit with the same name is present as a
	 * child of the parentCategory (category will be resolved with
	 * {@link CategoryUtil#findCategory(String, boolean)} this method returns an
	 * Error status. This is important to keep the unique identification of an
	 * element with a slash-separted path.
	 * 
	 * @param parentCategoryPath
	 *            the path of the parentCategory
	 * @param newCategoryName
	 *            the new category name
	 * @return
	 */
	public static IStatus isCategoryStructureValid(final String parentCategoryPath,
			final String newCategoryName) {
		if (findCategory(parentCategoryPath + "/" + newCategoryName, false) != null) {
			return StatusCreator.newStatus("Category already exisits");
		}
		Category findCategory = findCategory(parentCategoryPath, false);
		if (findCategory != null) {
			EList<InformationUnitListItem> informationUnit = findCategory.getInformationUnit();
			for (InformationUnitListItem informationUnitListItem : informationUnit) {
				if (informationUnitListItem.getLabel().equals(newCategoryName)) {
					return StatusCreator
							.newStatus("An information item with this name alreasy exists.");
				}
			}
		}
		return Status.OK_STATUS;
	}

	public static IProject getProjectByCategory(final Category category) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(category.eResource().getURI().toPlatformString(true))).getProject();
	}

	public static Category[] findCatetegories(final String startString, final boolean caseSensitive) {
		return findCatetegories(startString, caseSensitive, null);
	}

	public static Category[] findCatetegories(final String startString,
			final boolean caseSensitive, final CollectionFilter<Category> filter) {
		IApplicationModel appService = InfomngmntEditPlugin.getPlugin().getServiceTracker()
				.getService(IApplicationModel.class);
		ApplicationRoot model = appService.getModel();
		EObjectCondition condition = new EObjectTypeRelationCondition(
				InfomngmntPackage.Literals.CATEGORY);
		EObjectCondition valueCondition = new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject object) {
				if (caseSensitive) {
					return categoryToString((Category) object).startsWith(startString);
				}
				return categoryToString((Category) object).toLowerCase().startsWith(
						startString.toLowerCase());

			}
		};
		SELECT select = new SELECT(new FROM(model.getRootCategories()), new WHERE(condition
				.AND(valueCondition)));
		IQueryResult execute = select.execute();
		Set<? extends EObject> objects = execute.getEObjects();
		List<Category> returnValue = new LinkedList<Category>();
		for (EObject object : objects) {
			returnValue.add((Category) object);
		}
		Collections.sort(returnValue, new Comparator<Category>() {
			public int compare(final Category arg0, final Category arg1) {
				if (caseSensitive) {
					return categoryToString(arg0).compareTo(categoryToString(arg1));
				}
				return categoryToString(arg0).compareToIgnoreCase(categoryToString(arg1));
			}
		});
		if (filter != null) {
			returnValue = CollectionUtils.filter(returnValue, filter);
		}
		return returnValue.toArray(new Category[returnValue.size()]);
	}

	public static boolean isItemParentOfCategory(final EObject possibleChild,
			final Category possibleParent) {
		if (possibleChild == possibleParent) {
			return true;
		}
		if (possibleChild.eContainer() != null) {
			return isItemParentOfCategory(possibleChild.eContainer(), possibleParent);
		}
		return false;
	}

	public static Category copyBlankObject(final Category cat) {
		Category newCategory = InfomngmntFactory.eINSTANCE.createCategory();
		ModelUtil.copyObject(cat, newCategory, new EStructuralFeature[] {
				InfomngmntPackage.Literals.CATEGORY__ID,
				InfomngmntPackage.Literals.CATEGORY__DESCRIPTION,
				InfomngmntPackage.Literals.CATEGORY__LABEL }, new EStructuralFeature[0]);
		return newCategory;
	}

	public static InformationUnitListItem[] getAllInfoUnitItems(final Category cat) {

		SELECT select = new SELECT(new FROM(cat), new WHERE(new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject eObject) {
				return eObject instanceof InformationUnitListItem;
			}
		}));
		IQueryResult execute = select.execute();
		Set<? extends EObject> eObjects = execute.getEObjects();
		InformationUnitListItem[] returnValue = new InformationUnitListItem[eObjects.size()];
		int i = 0;
		for (EObject informationUnitListItem : eObjects) {
			returnValue[i++] = (InformationUnitListItem) informationUnitListItem;
		}
		return returnValue;
	}

	public static EObject[] getAllChildren(final EObject parent, final EClass clazz) {

		SELECT select = new SELECT(new FROM(parent), new WHERE(new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject eObject) {
				return eObject.eClass() == clazz;
			}
		}));
		IQueryResult execute = select.execute();
		Set<? extends EObject> eObjects = execute.getEObjects();
		EObject[] returnValue = new EObject[eObjects.size()];
		int i = 0;
		for (EObject informationUnitListItem : eObjects) {
			returnValue[i++] = informationUnitListItem;
		}
		return returnValue;
	}

	public static Category getCategoryById(final String id) {
		IApplicationModel appService = InfomngmntEditPlugin.getPlugin().getServiceTracker()
				.getService(IApplicationModel.class);
		EObjectCondition typeRelationCondition = new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject eObject) {
				return eObject.eClass() == InfomngmntPackage.Literals.CATEGORY;
			}
		};
		EObjectCondition valueCondition = new EObjectAttributeValueCondition(
				InfomngmntPackage.Literals.CATEGORY__ID, new Condition() {
					@Override
					public boolean isSatisfied(final Object object) {
						return id.equals(object);
					}
				});
		SELECT select = new SELECT(new FROM(appService.getModel().getRootCategories()), new WHERE(
				typeRelationCondition.AND(valueCondition)));
		IQueryResult execute = select.execute();
		Set<? extends EObject> eObjects = execute.getEObjects();
		if (eObjects.size() > 0) {
			Iterator<? extends EObject> iterator = eObjects.iterator();
			while (iterator.hasNext()) {
				EObject eObject = iterator.next();
				return ((Category) eObject);
			}
		}
		return null;
	}

	public static EObject[] getAllAdpatableChildren(final EObject parent, final Class adaptable) {

		SELECT select = new SELECT(new FROM(parent), new WHERE(new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject eObject) {
				return eObject instanceof IAdaptable
						&& ((IAdaptable) eObject).getAdapter(adaptable) != null;
			}
		}));
		IQueryResult execute = select.execute();
		Set<? extends EObject> eObjects = execute.getEObjects();
		EObject[] returnValue = new EObject[eObjects.size()];
		int i = 0;
		for (EObject informationUnitListItem : eObjects) {
			returnValue[i++] = informationUnitListItem;
		}
		return returnValue;
	}

	public static void recursivelySortId(final Category category) {
		EList<InformationUnitListItem> informationUnit = category.getInformationUnit();
		ECollections.sort(informationUnit, new Comparator<InformationUnitListItem>() {
			public int compare(final InformationUnitListItem arg0,
					final InformationUnitListItem arg1) {
				return arg0.getId().compareTo(arg1.getId());
			}
		});
		EList<Category> children = category.getChildren();
		for (Category category2 : children) {
			recursivelySortId(category2);
		}
	}

}
