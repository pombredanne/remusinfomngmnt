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

package org.remus.infomngmnt.core.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructure;
import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.InformationStructureType;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.internal.creation.InformationUnitCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationStructureRead {

	private final InformationUnit unit;
	private final String type;

	public static final String ATTRIBUTE_ACCESSOR = "@"; //$NON-NLS-1$

	public InformationStructureRead(final InformationUnit unit, final String type) {
		this.unit = unit;
		this.type = type;
	}

	public static InformationStructureRead newSession(final InformationUnit baseUnit) {
		return new InformationStructureRead(baseUnit, baseUnit.getType());
	}

	public static InformationStructureRead newSession(final InformationUnit anyChildUnit,
			final String baseTypeId) {
		return new InformationStructureRead(anyChildUnit, baseTypeId);
	}

	public InformationUnit getChildByNodeId(final String node) {
		InformationStructure itemByNodeAndTypeId = getItemByNodeAndTypeId(node, this.type);
		/*
		 * Now we search through the given base object for the id
		 */
		if (itemByNodeAndTypeId == null) {
			throw new IllegalArgumentException("node was not found in definition");
		}
		SELECT innerSelect = new SELECT(new FROM(this.unit), new WHERE(
				new EObjectAttributeValueCondition(
						InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE,
						new Condition() {
							@Override
							public boolean isSatisfied(final Object arg0) {
								return arg0 != null && arg0.toString().equals(node);
							}
						})));
		IQueryResult execute2 = innerSelect.execute();
		if (execute2.size() == 0) {
			// Node is defined, but not present. Ok, we return null.
			return null;
		}
		EObject next2 = execute2.iterator().next();
		return (InformationUnit) next2;
	}

	public InformationUnit getChildByPath(final String... path) {
		final InformationStructureDefinition structureDefinition = getStructureDefinition(this.type);
		EObjectAttributeValueCondition itemIdCondition = new EObjectAttributeValueCondition(
				InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM__ID, new Condition() {
					@Override
					public boolean isSatisfied(final Object arg0) {
						return arg0 != null && arg0.toString().equals(path[path.length - 1]);
					}
				});
		EObjectCondition pathCondition = new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject arg0) {
				return InformationUnitCreator.pathSatisfied(structureDefinition
						.getReferencedStructureItems(), path, 0)
						|| InformationUnitCreator.pathSatisfied(structureDefinition
								.getStructureItems(), path, 0);
			}
		};

		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(itemIdCondition
				.AND(pathCondition)));
		IQueryResult execute = select.execute();
		if (execute.size() != 1) {
			throw new IllegalArgumentException("Subtype not found");
		} else {
			SELECT innerSelect = new SELECT(new FROM(this.unit), new WHERE(
					new EObjectAttributeValueCondition(
							InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE,
							new Condition() {
								@Override
								public boolean isSatisfied(final Object arg0) {
									return InformationUnitCreator.pathSatisfied2(
											InformationStructureRead.this.unit, path);
								}
							})));
			IQueryResult execute2 = innerSelect.execute();
			if (execute2.size() == 0) {
				// Node is defined, but not present. Ok, we return null.
				return null;
			}
			EObject next2 = execute2.iterator().next();
			return (InformationUnit) next2;
		}
	}

	public Object getValueByPath(final String... path) {

		InformationUnit childByNodeId = getChildByPath(path);
		InformationStructure structureItem = getItemByPathAndTypeId(this.type, path);
		InformationStructureType structureType = structureItem.getType();
		EAttribute attToGet = null;
		switch (structureType) {
		case STRING:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE;
			break;
		case BINARY:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE;
			break;
		case BOOLEAN:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE;
			break;
		case DATE:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE;

			break;
		case LONG:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE;

			break;
		case DOUBLE:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__DOUBLE_VALUE;
			break;
		default:
			break;
		}
		if (attToGet != null) {
			return childByNodeId.eGet(attToGet);
		}
		return null;
	}

	public Object getValueByNodeId(final String node) {
		if (node.startsWith(ATTRIBUTE_ACCESSOR)) {
			EList<EAttribute> eAttributes = this.unit.eClass().getEAllAttributes();
			for (EAttribute eAttribute : eAttributes) {
				if (node.equals(StringUtils.join(ATTRIBUTE_ACCESSOR, eAttribute.getName()))) {
					return this.unit.eGet(eAttribute);
				}
			}
		}
		InformationUnit childByNodeId = getChildByNodeId(node);
		InformationStructure structureItem = getItemByNodeAndTypeId(node, this.type);
		InformationStructureType structureType = structureItem.getType();
		EAttribute attToGet = null;
		switch (structureType) {
		case STRING:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE;
			break;
		case BINARY:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE;
			break;
		case BOOLEAN:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE;
			break;
		case DATE:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE;

			break;
		case LONG:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE;

			break;
		case DOUBLE:
			attToGet = InfomngmntPackage.Literals.INFORMATION_UNIT__DOUBLE_VALUE;
			break;
		default:
			break;
		}
		if (attToGet != null) {
			return childByNodeId.eGet(attToGet);
		}
		return null;
	}

	public EList<InformationUnit> getDynamicList(final String node) {
		InformationStructureDefinition structureDefinition = getStructureDefinition(this.type);
		/*
		 * We check the structure definition if such a dynamic list is defined.
		 */
		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(
				new EObjectAttributeValueCondition(
						InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM__ID, new Condition() {
							@Override
							public boolean isSatisfied(final Object arg0) {
								return arg0 != null && arg0.toString().equals(node);
							}
						}).AND(new EObjectCondition() {

					@Override
					public boolean isSatisfied(final EObject arg0) {
						return arg0.eClass() == InfomngmntPackage.Literals.DYNAMIC_STRUCTURE;
					}
				})));
		IQueryResult execute = select.execute();
		if (execute.size() != 1) {
			throw new IllegalArgumentException(
					"Dynamic Node could not be found in structure definition");
		}
		return getChildByNodeId(node).getChildValues();
	}

	static InformationStructure getItemByNodeAndTypeId(final String nodeId, final String typeId) {
		InformationStructureDefinition structureDefinition = getStructureDefinition(typeId);
		if (nodeId.equals(typeId)) {
			return structureDefinition;
		}
		/*
		 * We check the structure definition if such a dynamic list is defined.
		 */
		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(
				new EObjectAttributeValueCondition(
						InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM__ID, new Condition() {
							@Override
							public boolean isSatisfied(final Object arg0) {
								return arg0 != null && arg0.toString().equals(nodeId);
							}
						})));
		IQueryResult execute = select.execute();
		if (execute.size() != 1) {
			return null;
		}
		return (InformationStructureItem) execute.getEObjects().iterator().next();
	}

	static InformationStructure getItemByPathAndTypeId(final String typeId, final String... path) {
		final InformationStructureDefinition structureDefinition = getStructureDefinition(typeId);
		EObjectAttributeValueCondition itemIdCondition = new EObjectAttributeValueCondition(
				InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM__ID, new Condition() {
					@Override
					public boolean isSatisfied(final Object arg0) {
						return arg0 != null && arg0.toString().equals(path[path.length - 1]);
					}
				});
		EObjectCondition pathCondition = new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject arg0) {
				return InformationUnitCreator.pathSatisfied(structureDefinition
						.getReferencedStructureItems(), path, 0)
						|| InformationUnitCreator.pathSatisfied(structureDefinition
								.getStructureItems(), path, 0);
			}
		};

		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(itemIdCondition
				.AND(pathCondition)));
		IQueryResult execute = select.execute();
		if (execute.size() != 1) {
			return null;
		}
		return (InformationStructureItem) execute.getEObjects().iterator().next();
	}

	private static InformationStructureDefinition getStructureDefinition(final String type) {
		IInfoType infoTypeByType = InformationExtensionManager.getInstance()
				.getInfoTypeByType(type);
		InformationStructureDefinition structureDefinition = infoTypeByType
				.getStructureDefinition();
		return structureDefinition;
	}

}
