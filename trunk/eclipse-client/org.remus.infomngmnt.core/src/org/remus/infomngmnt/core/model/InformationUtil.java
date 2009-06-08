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

import java.util.ArrayList;
import java.util.Collections;
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
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
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
		SELECT select = new SELECT(new FROM(ApplicationModelPool.getInstance().getModel()
				.getRootCategories()), new WHERE(condition1));
		return select.execute().getEObjects();

	}

}