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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.AttributeChange;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectReferenceValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.path.Path2ObjectMapper;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationUtil {

	public static InformationUnit getChildByType(final InformationUnit unit, final String type) {
		InformationUnit returnValue = null;
		if (unit != null) {
			EList<InformationUnit> childValues = unit.getChildValues();
			for (InformationUnit informationUnit : childValues) {
				if (informationUnit.getType().equals(type)) {
					returnValue = informationUnit;
					break;
				}
			}
		}
		return returnValue;
	}

	public static IFile getFirstBinaryReferenceFile(final InformationUnit unit) {
		IFile infoFile = (IFile) unit.getAdapter(IFile.class);
		if (infoFile != null && unit.getBinaryReferences().size() > 0) {
			return infoFile.getProject().getFolder(ResourceUtil.BINARY_FOLDER).getFile(
					unit.getBinaryReferences().get(0).getProjectRelativePath());
		}
		return null;
	}

	public static List<DiffElement> computeDiffs(final InformationUnit obj1,
			final InformationUnit obj2) {
		// Matching model elements
		try {
			MatchModel match = MatchService.doMatch(obj1, obj2, Collections
					.<String, Object> emptyMap());
			// Computing differences
			DiffModel diff = DiffService.doDiff(match, false);

			// Merges all differences from model1 to model2
			return new ArrayList<DiffElement>(diff.getOwnedElements());
		} catch (InterruptedException e) {
			return Collections.EMPTY_LIST;
		}
	}

	public static AttributeChange getAttributeChange(final InformationUnit obj1,
			final InformationUnit obj2, final EAttribute attribute) {
		List<DiffElement> computeDiffs = computeDiffs(obj1, obj2);
		return getAttributeChange(computeDiffs, attribute);
	}

	public static AttributeChange getAttributeChange(final List<DiffElement> elements,
			final EAttribute attributeToCompare) {
		for (DiffElement diffElement : elements) {
			if (diffElement instanceof DiffGroup) {
				EList<DiffElement> subDiffElements = diffElement.getSubDiffElements();
				AttributeChange attributeChange = getAttributeChange(subDiffElements,
						attributeToCompare);
				if (attributeChange != null) {
					return attributeChange;
				}

			}
			if (diffElement instanceof AttributeChange) {
				EAttribute attribute = ((AttributeChange) diffElement).getAttribute();
				if (attribute == attributeToCompare) {
					return (AttributeChange) diffElement;
				}
			}
		}
		return null;
	}

	public static Set<? extends EObject> getAllItemsByType(final String type) {
		EObjectAttributeValueCondition condition1 = new EObjectAttributeValueCondition(
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE, new Condition() {
					@Override
					public boolean isSatisfied(final Object object) {
						return type.toLowerCase().equals(object.toString().toLowerCase());
					}

				});
		EObjectReferenceValueCondition condition2 = new EObjectReferenceValueCondition(
				InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA,
				new EObjectCondition() {
					@Override
					public boolean isSatisfied(final EObject object) {
						return object == null
								|| ((SynchronizationMetadata) object).getSyncState() != SynchronizationState.LOCAL_DELETED;
					}
				});
		EObjectCondition condition3 = new EObjectCondition() {

			@Override
			public boolean isSatisfied(final EObject eObject) {
				return ((SynchronizableObject) eObject).getSynchronizationMetaData() == null;
			}

		};
		SELECT select = new SELECT(new FROM(ApplicationModelPool.getInstance().getModel()
				.getRootCategories()), new WHERE(condition1.AND(condition3.OR(condition2))));
		return select.execute().getEObjects();

	}

	public static InformationUnit createNew(final String type, final String stringValue) {
		InformationUnit unit = createNew(type);
		unit.setStringValue(stringValue);
		return unit;
	}

	public static InformationUnit createNew(final String type) {
		InformationUnit unit = InfomngmntFactory.eINSTANCE.createInformationUnit();
		unit.setType(type);
		return unit;
	}

	public static InformationUnit createNew(final String type, final long longValue) {
		InformationUnit unit = createNew(type);
		unit.setLongValue(longValue);
		return unit;
	}

	public static InformationUnit createNew(final String type, final Date dateValue) {
		InformationUnit unit = createNew(type);
		unit.setDateValue(dateValue);
		return unit;
	}

	public static Object preset(final InformationUnit unit, final Object value,
			final String finalExpression, final String... childExpressions) {
		StringWriter sw = new StringWriter();
		for (String string : childExpressions) {
			sw.append("childValues[@type=\'").append(string).append("\'/");
		}
		sw.append(finalExpression);
		return preset(unit, sw.toString(), value);
	}

	public static Object preset(final InformationUnit unit, final String expression,
			final Object value) {
		return new Path2ObjectMapper(expression, unit, value).getObjectForPath(true, true);
	}

	public static String getFullReadablePath(final InformationUnitListItem item) {
		String text = CategoryUtil.categoryToString((Category) item.eContainer()) + "/"
				+ item.getLabel();
		return text;
	}

}
