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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.AttributeChange;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EAttribute;

import org.remus.infomngmnt.InformationUnit;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationUtil {

	public static InformationUnit getChildByType(InformationUnit unit, String type) {
		InformationUnit returnValue = null;
		EList<InformationUnit> childValues = unit.getChildValues();
		for (InformationUnit informationUnit : childValues) {
			if (informationUnit.getType().equals(type)) {
				returnValue = informationUnit;
				break;
			}
		}
		return returnValue;
	}

	public static List<DiffElement> computeDiffs(InformationUnit obj1, InformationUnit obj2) {
		// Matching model elements
		try {
			MatchModel match = MatchService.doMatch(obj1, obj2, Collections.<String, Object> emptyMap());
			// Computing differences
			DiffModel diff = DiffService.doDiff(match, false);
			// Merges all differences from model1 to model2
			return new ArrayList<DiffElement>(diff.getOwnedElements());
		} catch (InterruptedException e) {
			return Collections.EMPTY_LIST;
		}
	}

	public static AttributeChange getAttributeChange(InformationUnit obj1, InformationUnit obj2, EAttribute attribute) {
		List<DiffElement> computeDiffs = computeDiffs(obj1, obj2);
		return getAttributeChange(computeDiffs, attribute);
	}

	public static AttributeChange getAttributeChange(List<DiffElement> elements, EAttribute attributeToCompare) {
		for (DiffElement diffElement : elements) {
			if (diffElement instanceof AttributeChange) {
				EAttribute attribute = ((AttributeChange) diffElement).getAttribute();
				if (attribute == attributeToCompare) {
					return (AttributeChange) diffElement;
				}
			}
		}
		return null;
	}

}
