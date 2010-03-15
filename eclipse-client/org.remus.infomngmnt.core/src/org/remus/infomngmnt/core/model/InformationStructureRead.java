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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.DynamicStructure;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructure;
import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.InformationStructureType;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.core.util.StringUtils;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationStructureRead {

	private final InformationUnit unit;
	private final String type;

	public static final String ATTRIBUTE_ACCESSOR = "@"; //$NON-NLS-1$

	/**
	 * A string list where all node ids are stored which are children or
	 * subchildren of a dynamic node.
	 */
	private List<String> multipleNodeIds;

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

		if (node.split("/").length > 1) {
			return (InformationUnit) getObjectByPath(node.split("/"), null, 0);
		}
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

	public EStructuralFeature getFeatureByNodeId(final String nodeId) {
		if (nodeId.startsWith(ATTRIBUTE_ACCESSOR)) {
			EList<EAttribute> eAttributes = this.unit.eClass().getEAllAttributes();
			for (EAttribute eAttribute : eAttributes) {
				if (nodeId.equals(StringUtils.join(ATTRIBUTE_ACCESSOR, eAttribute.getName()))) {
					return eAttribute;
				}
			}
		}
		InformationStructure itemByNodeAndTypeId = getItemByNodeAndTypeId(nodeId, this.type);
		if (itemByNodeAndTypeId != null) {
			InformationStructureType structureType = itemByNodeAndTypeId.getType();
			switch (structureType) {
			case STRING:
				return InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE;
			case BINARY:
				return InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE;
			case BOOLEAN:
				return InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE;
			case DATETIME:
				return InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE;
			case DOUBLE:
				return InfomngmntPackage.Literals.INFORMATION_UNIT__DOUBLE_VALUE;
			case LONG:
				return InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE;
			default:
				throw new IllegalArgumentException("Type was found, but datatype was not set");
			}
		}
		throw new IllegalArgumentException();
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
		if (node.split("/").length > 1) {
			return getValueByPath(node.split("/"), null, 0);
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
		case DATETIME:
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
		if (attToGet != null && childByNodeId != null) {
			return childByNodeId.eGet(attToGet);
		}
		return null;
	}

	private Object getValueByPath(final String[] split, final InformationStructure parentElement,
			final int i) {
		InformationStructure itemByNodeAndTypeId = getItemByNodeAndTypeId(split[i], this.type);
		if (i == 0 && itemByNodeAndTypeId != null) {
			return getValueByPath(split, itemByNodeAndTypeId, i + 1);
		} else if (i == 0 && itemByNodeAndTypeId == null) {
			return null;
		} else if (parentElement instanceof DynamicStructure && itemByNodeAndTypeId == null) {
			EList<InformationUnit> dynamicList = getDynamicList(((DynamicStructure) parentElement)
					.getId());
			for (InformationUnit informationUnit : dynamicList) {
				String type2 = informationUnit.getType();
				EStructuralFeature featureByNodeId = getFeatureByNodeId(type2);
				if (featureByNodeId != null) {
					Object eGet = informationUnit.eGet(featureByNodeId);
					if (eGet != null && eGet.toString().equals(split[i])) {
						String[] newPath = new String[split.length - 1 - i];
						System.arraycopy(split, i + 1, newPath, 0, split.length - 1 - i);
						InformationStructureRead newRead = InformationStructureRead.newSession(
								informationUnit, this.type);
						return newRead.getValueByNodeId(org.apache.commons.lang.StringUtils.join(
								newPath, "/"));
					}
				}
			}
		} else if (parentElement instanceof InformationStructure && itemByNodeAndTypeId != null) {
			return getValueByPath(split, itemByNodeAndTypeId, i + 1);
		}
		return null;
	}

	private Object getObjectByPath(final String[] split, final InformationStructure parentElement,
			final int i) {
		InformationStructure itemByNodeAndTypeId = getItemByNodeAndTypeId(split[i], this.type);
		if (i == 0 && itemByNodeAndTypeId != null) {
			return getObjectByPath(split, itemByNodeAndTypeId, i + 1);
		} else if (i == 0 && itemByNodeAndTypeId == null) {
			return null;
		} else if (parentElement instanceof DynamicStructure && itemByNodeAndTypeId == null) {
			EList<InformationUnit> dynamicList = getDynamicList(((DynamicStructure) parentElement)
					.getId());
			for (InformationUnit informationUnit : dynamicList) {
				String type2 = informationUnit.getType();
				EStructuralFeature featureByNodeId = getFeatureByNodeId(type2);
				if (featureByNodeId != null) {
					Object eGet = informationUnit.eGet(featureByNodeId);
					if (eGet != null && eGet.toString().equals(split[i])) {
						String[] newPath = new String[split.length - 1 - i];
						System.arraycopy(split, i + 1, newPath, 0, split.length - 1 - i);
						InformationStructureRead newRead = InformationStructureRead.newSession(
								informationUnit, this.type);
						return newRead.getChildByNodeId(org.apache.commons.lang.StringUtils.join(
								newPath, "/"));
					}
				}
			}
		} else if (parentElement instanceof InformationStructure && itemByNodeAndTypeId != null) {
			return getObjectByPath(split, itemByNodeAndTypeId, i + 1);
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

	public static InformationStructure getItemByNodeAndTypeId(final String nodeId,
			final String typeId) {
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

	public Map<String, Object> getContentsAsStrucuturedMap() {
		Map<String, Object> returnValue = new HashMap<String, Object>();
		InformationStructureDefinition structureDefinition = getStructureDefinition(this.type);
		returnValue.put(this.type, getValueByNodeId(this.type));
		EList<InformationStructureItem> structureItems = structureDefinition.getStructureItems();
		for (InformationStructureItem informationStructureItem : structureItems) {
			addValues(informationStructureItem, returnValue, this);
		}
		returnValue.put("__label", this.unit.getLabel());
		returnValue.put("__id", this.unit.getId());
		returnValue.put("__description", this.unit.getDescription());
		returnValue.put("__keywords", this.unit.getKeywords());
		returnValue.put("__datecreated", this.unit.getCreationDate());
		return returnValue;

	}

	public Map<String, List<Map<String, Object>>> getDynamicContentAsStructuredMap() {
		Map<String, List<Map<String, Object>>> returnValue = new HashMap<String, List<Map<String, Object>>>();
		InformationStructureDefinition structureDefinition = getStructureDefinition(this.type);
		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject arg0) {
				return arg0.eClass() == InfomngmntPackage.Literals.DYNAMIC_STRUCTURE;
			}
		}));
		IQueryResult execute = select.execute();
		Set<? extends EObject> eObjects = execute.getEObjects();
		for (EObject eObject : eObjects) {
			DynamicStructure structure = (DynamicStructure) eObject;
			EList<InformationUnit> dynamicList = getDynamicList(structure.getId());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for (InformationUnit informationUnit : dynamicList) {
				InformationStructureRead infoRead = InformationStructureRead.newSession(
						informationUnit, this.type);
				Map<String, Object> dynamicItemValues = new HashMap<String, Object>();
				EList<InformationStructureItem> structureItems = structure.getStructureItems();
				for (InformationStructureItem informationStructureItem : structureItems) {
					addValues(informationStructureItem, dynamicItemValues, infoRead);
				}
				list.add(dynamicItemValues);
			}
			returnValue.put(structure.getId(), list);
		}
		return returnValue;

	}

	private void addValues(final InformationStructureItem structure, final Map<String, Object> map,
			final InformationStructureRead read) {
		if (structure.eClass() != InfomngmntPackage.Literals.DYNAMIC_STRUCTURE) {
			Object valueByNodeId = read.getValueByNodeId(structure.getId());
			map.put(structure.getId(), valueByNodeId);
		}
		EList<InformationStructureItem> structureItems = structure.getStructureItems();
		for (InformationStructureItem informationStructureItem : structureItems) {
			addValues(informationStructureItem, map, read);
		}

	}

	private static InformationStructureDefinition getStructureDefinition(final String type) {
		IInformationTypeHandler service = InfomngmntEditPlugin.getPlugin().getService(
				IInformationTypeHandler.class);
		IInfoType infoTypeByType = service.getInfoTypeByType(type);
		InformationStructureDefinition structureDefinition = infoTypeByType
				.getStructureDefinition();
		InfomngmntEditPlugin.getPlugin().getServiceTracker().ungetService(service);
		return structureDefinition;
	}

	/**
	 * Returns the name of the dynamic node where the given node id is
	 * contained.
	 * 
	 * @param nodeId
	 * @return the node id of the dynamic node, else <code>null</code>
	 */
	public String getInContainedDynamicNode(final String nodeId) {
		InformationStructure itemByNodeAndTypeId = getItemByNodeAndTypeId(nodeId, this.type);
		while (itemByNodeAndTypeId instanceof InformationStructureItem
				&& itemByNodeAndTypeId.eContainer() != null) {
			if (itemByNodeAndTypeId.eClass() == InfomngmntPackage.Literals.DYNAMIC_STRUCTURE) {
				return ((InformationStructureItem) itemByNodeAndTypeId).getId();
			}
			itemByNodeAndTypeId = (InformationStructure) itemByNodeAndTypeId.eContainer();
			if (!(itemByNodeAndTypeId instanceof InformationStructureItem)) {
				break;
			}
		}
		return null;

	}

	public List<String> getNodeIdsWithBinaryReferences(final String nodeId) {
		InformationStructure searchScope;
		if (nodeId == null) {
			searchScope = getStructureDefinition(this.type);
		} else {
			searchScope = getItemByNodeAndTypeId(nodeId, this.type);
		}
		SELECT select = new SELECT(
				new FROM(searchScope),
				new WHERE(
						new EObjectAttributeValueCondition(
								InfomngmntPackage.Literals.INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES,
								new Condition() {
									@Override
									public boolean isSatisfied(final Object arg0) {
										return (Boolean) arg0;
									}
								})));
		IQueryResult execute = select.execute();
		Set<? extends EObject> eObjects = execute.getEObjects();
		List<String> returnValue = new ArrayList<String>();
		for (EObject eObject : eObjects) {
			if (eObject instanceof InformationStructureDefinition) {
				returnValue.add(this.type);
			} else if (eObject instanceof InformationStructureItem) {

				returnValue.add(((InformationStructureItem) eObject).getId());

			}
		}
		return returnValue;
	}

	/**
	 * Searches for all binary references within the given node.
	 * 
	 * @return
	 */
	public List<BinaryReference> getBinaryReferences() {
		return getBinaryReferences(null, true);
	}

	/**
	 * Searches through the structure definition for nodes that can have binary
	 * references and returns the value of the node, if set and leaves the nodes
	 * that <i>can</i> have binary nodes.
	 * 
	 * @param nodeId
	 *            the scope to search. If null, all possible binary references
	 *            within the StructureDefinition will be checked.
	 * @param checkForDynamics
	 *            additional search for dynamic references. It's recommended
	 *            always to use <code>true</code> since the given nodeId could
	 *            be present multiple times in the given information unit.
	 * @return a list of all set binary references within the node which id
	 *         equals the parameter nodeId.
	 */
	public List<BinaryReference> getBinaryReferences(final String nodeId,
			final boolean checkForDynamics) {
		List<String> nodeIdsWithBinaryReferences = getNodeIdsWithBinaryReferences(nodeId);
		List<BinaryReference> returnValue = new ArrayList<BinaryReference>();
		for (String string : nodeIdsWithBinaryReferences) {
			String inContainedDynamicNode = getInContainedDynamicNode(string);
			if (checkForDynamics && inContainedDynamicNode != null) {
				EList<InformationUnit> dynamicList = getDynamicList(inContainedDynamicNode);
				for (InformationUnit informationUnit : dynamicList) {
					InformationStructureRead dynamicRead = InformationStructureRead.newSession(
							informationUnit, this.type);
					returnValue.addAll(dynamicRead.getBinaryReferences(inContainedDynamicNode,
							false));
				}
			} else {
				InformationUnit childByNodeId = getChildByNodeId(string);
				if (childByNodeId != null && childByNodeId.getBinaryReferences() != null) {
					returnValue.add(childByNodeId.getBinaryReferences());
				}
			}
		}
		return returnValue;
	}

}
