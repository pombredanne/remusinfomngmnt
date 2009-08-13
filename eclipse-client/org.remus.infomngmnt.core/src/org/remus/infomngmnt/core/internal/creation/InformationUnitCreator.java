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

package org.remus.infomngmnt.core.internal.creation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructure;
import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.InformationStructureType;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * Class for building information units and fragments based on the
 * {@link InformationStructureDefinition}
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @since 1.0
 */
public class InformationUnitCreator {

	public static final String ATTRIBUTE_ACCESSOR = "@"; //$NON-NLS-1$

	public InformationUnit createNewObject(final String type) {
		InformationStructureDefinition structureDefinition = getStructureDefinition(type);
		InformationUnit newUnit = InfomngmntFactory.eINSTANCE.createInformationUnit();
		newUnit.setType(type);
		newUnit.setCreationDate(new Date());
		EList<InformationStructureItem> structureItems = structureDefinition.getStructureItems();
		for (InformationStructureItem InformationStructureItem : structureItems) {
			fillStructureItem(newUnit, InformationStructureItem, false);
		}
		EList<InformationStructureItem> referencedStructureItems = structureDefinition
				.getReferencedStructureItems();
		for (InformationStructureItem InformationStructureItem : referencedStructureItems) {
			fillStructureItem(newUnit, InformationStructureItem, false);
		}
		return newUnit;

	}

	private void fillStructureItem(final InformationUnit newUnit,
			final InformationStructureItem InformationStructureItem,
			final boolean ignoreCreateAlways) {
		if (ignoreCreateAlways || InformationStructureItem.isCreateAlways()) {
			InformationUnit newSubNode = InfomngmntFactory.eINSTANCE.createInformationUnit();
			newSubNode.setType(InformationStructureItem.getId());
			EList<InformationStructureItem> structureItems = InformationStructureItem
					.getStructureItems();
			for (InformationStructureItem subInformationStructureItem : structureItems) {
				fillStructureItem(newSubNode, subInformationStructureItem, ignoreCreateAlways);
			}
			EList<InformationStructureItem> referencedStructureItems = InformationStructureItem
					.getReferencedStructureItems();
			for (InformationStructureItem subInformationStructureItem : referencedStructureItems) {
				fillStructureItem(newSubNode, subInformationStructureItem, ignoreCreateAlways);
			}
			newUnit.getChildValues().add(newSubNode);
		}
	}

	public InformationUnit createSubType(final String type, final String id, final Object value) {
		InformationStructureDefinition structureDefinition = getStructureDefinition(type);
		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(
				new EObjectAttributeValueCondition(
						InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM__ID, new Condition() {
							@Override
							public boolean isSatisfied(final Object arg0) {
								return arg0 != null && arg0.toString().equals(id);
							}
						})));
		IQueryResult execute = select.execute();
		if (execute.size() != 1) {
			throw new IllegalArgumentException("Cannot create subtype");
		} else {
			InformationStructureItem next = (InformationStructureItem) execute.getEObjects()
					.iterator().next();
			InformationUnit newInformationUnit = InfomngmntFactory.eINSTANCE
					.createInformationUnit();
			newInformationUnit.setType(id);
			EList<InformationStructureItem> structureItems = next.getStructureItems();
			for (InformationStructureItem InformationStructureItem : structureItems) {
				fillStructureItem(newInformationUnit, InformationStructureItem, false);
			}
			EList<InformationStructureItem> referencedStructureItems = next
					.getReferencedStructureItems();
			for (InformationStructureItem InformationStructureItem : referencedStructureItems) {
				fillStructureItem(newInformationUnit, InformationStructureItem, false);
			}
			if (value != null) {
				InformationStructureType structureType = next.getType();
				EAttribute attToSet = null;
				switch (structureType) {
				case STRING:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE;
					break;
				case BINARY:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE;
					break;
				case BOOLEAN:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE;
					break;
				case DATE:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE;
					break;
				case LONG:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE;
					break;
				default:
					break;
				}
				if (attToSet != null) {
					newInformationUnit.eSet(attToSet, value);
				}
			}
			return newInformationUnit;
		}
	}

	private InformationStructureDefinition getStructureDefinition(final String type) {
		IInfoType infoTypeByType = InformationExtensionManager.getInstance()
				.getInfoTypeByType(type);
		InformationStructureDefinition structureDefinition = infoTypeByType
				.getStructureDefinition();
		return structureDefinition;
	}

	public void addDynamicNode(final InformationUnit baseObject, final InformationUnit dynamicNode,
			final EditingDomain editingDomain) {
		EditingDomain domain;
		if (editingDomain == null) {
			domain = EditingUtil.getInstance().createNewEditingDomain();
		} else {
			domain = editingDomain;
		}
		Command addCommand = getAddCommand(baseObject, dynamicNode, domain);
		if (addCommand != null) {
			domain.getCommandStack().execute(addCommand);
		}
		if (domain instanceof IDisposable) {
			((IDisposable) domain).dispose();
		}

	}

	private Command getAddCommand(final InformationUnit baseObject,
			final InformationUnit dynamicNode, final EditingDomain editingDomain) {
		InformationStructureDefinition structureDefinition = getStructureDefinition(baseObject
				.getType());

		/*
		 * At first we search for the parent node where to put this dynamic
		 * node.
		 */
		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(
				new EObjectAttributeValueCondition(
						InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM__ID, new Condition() {
							@Override
							public boolean isSatisfied(final Object arg0) {
								return arg0 != null
										&& arg0.toString().equals(dynamicNode.getType());
							}
						})));
		IQueryResult execute = select.execute();
		if (execute.size() != 1) {
			throw new IllegalArgumentException(
					"Dynamic node could not be found in structure definition");
		}
		InformationStructureItem next = (InformationStructureItem) execute.getEObjects().iterator()
				.next();
		EObject eContainer = next.eContainer();
		if (eContainer instanceof InformationStructureItem) {
			final String id = ((InformationStructureItem) eContainer).getId();
			/*
			 * Now we search through the given base object for the id
			 */
			SELECT innerSelect = new SELECT(new FROM(baseObject), new WHERE(
					new EObjectAttributeValueCondition(
							InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE,
							new Condition() {
								@Override
								public boolean isSatisfied(final Object arg0) {
									return arg0 != null && arg0.toString().equals(id);
								}
							})));
			IQueryResult execute2 = innerSelect.execute();
			if (execute2.size() != 1) {
				throw new IllegalArgumentException(
						"Searching for the dynamic node was not successful");
			}
			EObject next2 = execute2.iterator().next();
			Command create = AddCommand.create(editingDomain, next2,
					InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES, Collections
							.singleton(dynamicNode));
			return create;
		}
		return null;

	}

	public void setObject(final InformationUnit anyParentUnit, final String nodeId,
			final InformationUnit value, final String infoType, final EditingDomain editingDomain) {
		if (!nodeId.equals(value.getType()) || nodeId.equals(infoType)) {
			return;
		}
		InformationStructureDefinition structureDefinition = getStructureDefinition(infoType);
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
			throw new IllegalArgumentException("Subtype not found");
		}
		SELECT innerSelect = new SELECT(new FROM(anyParentUnit), new WHERE(
				new EObjectAttributeValueCondition(
						InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE,
						new Condition() {
							@Override
							public boolean isSatisfied(final Object arg0) {
								return arg0 != null && arg0.toString().equals(nodeId);
							}
						})));
		IQueryResult execute2 = innerSelect.execute();
		EObject next2;
		if (execute2.size() == 0) {
			addDynamicNode(anyParentUnit, value, editingDomain);
		} else {
			next2 = execute2.iterator().next();
			EditingDomain domain;
			if (editingDomain == null) {
				domain = EditingUtil.getInstance().createNewEditingDomain();
			} else {
				domain = editingDomain;
			}
			InformationUnit eContainer = (InformationUnit) next2.eContainer();
			int indexOf = eContainer.getChildValues().indexOf(next2);
			Command create = SetCommand.create(domain, eContainer,
					InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES, value, indexOf);
			domain.getCommandStack().execute(create);
			if (domain instanceof IDisposable) {
				((IDisposable) domain).dispose();
			}
		}

	}

	public void setValue(final InformationUnit anyParentUnit, final String nodeId, Object value,
			final String infoTypeId, final EditingDomain editingDomain) {
		InformationStructureDefinition structureDefinition = getStructureDefinition(infoTypeId);
		InformationStructure structureItem;
		if (nodeId.startsWith(ATTRIBUTE_ACCESSOR)) {
			EList<EAttribute> eAttributes = anyParentUnit.eClass().getEAllAttributes();
			for (EAttribute eAttribute : eAttributes) {
				if (eAttribute.isChangeable() && eAttribute.getName().equals(nodeId.substring(1))) {
					anyParentUnit.eSet(eAttribute, value);
					return;
				}
			}
		}
		if (nodeId.equals(infoTypeId)) {
			structureItem = structureDefinition;
		} else {
			SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(
					new EObjectAttributeValueCondition(
							InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM__ID,
							new Condition() {
								@Override
								public boolean isSatisfied(final Object arg0) {
									return arg0 != null && arg0.toString().equals(nodeId);
								}
							})));
			IQueryResult execute = select.execute();
			if (execute.size() != 1) {
				throw new IllegalArgumentException("Subtype not found");
			} else {
				structureItem = (InformationStructureItem) execute.getEObjects().iterator().next();
			}
		}

		if (value != null) {
			InformationStructureType structureType = structureItem.getType();
			EAttribute attToSet = null;
			switch (structureType) {
			case STRING:
				attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE;
				value = value.toString();
				break;
			case BINARY:
				attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE;
				break;
			case BOOLEAN:
				attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE;
				value = Boolean.valueOf(value.toString());
				break;
			case DATE:
				attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE;

				break;
			case LONG:
				attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE;
				try {
					value = Long.valueOf(value.toString());
				} catch (NumberFormatException e) {
					// do nothing
				}
				break;
			case DOUBLE:
				attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__DOUBLE_VALUE;
				try {
					value = Double.valueOf(value.toString());
				} catch (NumberFormatException e) {
					// do nothing
				}
				break;
			default:
				break;
			}
			if (attToSet != null) {
				SELECT innerSelect = new SELECT(new FROM(anyParentUnit), new WHERE(
						new EObjectAttributeValueCondition(
								InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE,
								new Condition() {
									@Override
									public boolean isSatisfied(final Object arg0) {
										return arg0 != null && arg0.toString().equals(nodeId);
									}
								})));
				IQueryResult execute2 = innerSelect.execute();
				if (execute2.size() != 1) {
					throw new IllegalArgumentException(
							"Searching for the dynamic node was not successful");
				}
				EObject next2 = execute2.iterator().next();
				EditingDomain domain;
				if (editingDomain == null) {
					domain = EditingUtil.getInstance().createNewEditingDomain();
				} else {
					domain = editingDomain;
				}
				Command create = SetCommand.create(domain, next2, attToSet, value);
				domain.getCommandStack().execute(create);
				if (domain instanceof IDisposable) {
					((IDisposable) domain).dispose();
				}
			}
		}

	}

	public void setValue(final InformationUnit anyParentItem, Object value, final String infoType,
			final EditingDomain editingDomain, final String... path) {
		final InformationStructureDefinition structureDefinition = getStructureDefinition(infoType);
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
				return pathSatisfied(structureDefinition.getReferencedStructureItems(), path, 0)
						|| pathSatisfied(structureDefinition.getStructureItems(), path, 0);
			}
		};

		SELECT select = new SELECT(new FROM(structureDefinition), new WHERE(itemIdCondition
				.AND(pathCondition)));
		IQueryResult execute = select.execute();
		if (execute.size() != 1) {
			throw new IllegalArgumentException("Subtype not found");
		} else {
			InformationStructureItem structureItem = (InformationStructureItem) execute
					.getEObjects().iterator().next();
			if (value != null) {
				InformationStructureType structureType = structureItem.getType();
				EAttribute attToSet = null;
				switch (structureType) {
				case STRING:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE;
					value = value.toString();
					break;
				case BINARY:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_VALUE;
					break;
				case BOOLEAN:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__BOOL_VALUE;
					value = Boolean.valueOf(value.toString());
					break;
				case DATE:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__DATE_VALUE;

					break;
				case LONG:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__LONG_VALUE;
					try {
						value = Long.valueOf(value.toString());
					} catch (NumberFormatException e) {
						// do nothing
					}
					break;
				case DOUBLE:
					attToSet = InfomngmntPackage.Literals.INFORMATION_UNIT__DOUBLE_VALUE;
					try {
						value = Double.valueOf(value.toString());
					} catch (NumberFormatException e) {
						// do nothing
					}
					break;
				default:
					break;
				}
				if (attToSet != null) {
					SELECT innerSelect = new SELECT(new FROM(anyParentItem), new WHERE(
							new EObjectAttributeValueCondition(
									InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE,
									new Condition() {
										@Override
										public boolean isSatisfied(final Object arg0) {
											return arg0 != null
													&& arg0.toString()
															.equals(path[path.length - 1]);
										}
									}).AND(new EObjectCondition() {

								@Override
								public boolean isSatisfied(final EObject arg0) {
									return pathSatisfied2(arg0, path);
								}
							})));
					IQueryResult execute2 = innerSelect.execute();
					if (execute2.size() != 1) {
						throw new IllegalArgumentException(
								"Searching for the dynamic node was not successful");
					}
					EObject next2 = execute2.iterator().next();
					EditingDomain domain;
					if (editingDomain == null) {
						domain = EditingUtil.getInstance().createNewEditingDomain();
					} else {
						domain = editingDomain;
					}
					Command create = SetCommand.create(domain, next2, attToSet, value);
					domain.getCommandStack().execute(create);
					if (domain instanceof IDisposable) {
						((IDisposable) domain).dispose();
					}
				}
			}
		}

	}

	public static boolean pathSatisfied2(final EObject arg0, final String[] path) {
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(path));
		Collections.reverse(list);
		InformationUnit item = (InformationUnit) arg0;
		for (String string : list) {
			if (item != null && item.getType().equals(string)) {
				if (item.eContainer() != null && item.eContainer() instanceof InformationUnit) {
					item = (InformationUnit) item.eContainer();
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public static boolean pathSatisfied(final EList<InformationStructureItem> arg0,
			final String[] path, final int i) {
		for (InformationStructureItem item : arg0) {
			if (item.getId().equals(path[i])) {
				int j = i + 1;
				if (i == path.length - 1) {
					return true;
				} else {
					return pathSatisfied(item.getReferencedStructureItems(), path, j)
							|| pathSatisfied(item.getStructureItems(), path, j);
				}
			}
		}
		return false;
	}

	public void addDynamicNode(final InformationUnit baseObject, final InformationUnit dynamicNode,
			final EditingDomain editingDomain, final Map<String, IFile> binaryNodeIdToFileMap) {
		EditingDomain domain;
		CompoundCommand cc = new CompoundCommand();
		if (editingDomain == null) {
			domain = EditingUtil.getInstance().createNewEditingDomain();
		} else {
			domain = editingDomain;
		}
		Command addCommand = getAddCommand(baseObject, dynamicNode, domain);
		if (addCommand != null) {
			cc.append(addCommand);
		}
		Set<String> keySet = binaryNodeIdToFileMap.keySet();
		InformationStructureRead read = InformationStructureRead.newSession(dynamicNode, baseObject
				.getType());
		List<String> allowedNodes = read.getNodeIdsWithBinaryReferences(dynamicNode.getType());
		for (String string : keySet) {
			if (allowedNodes.contains(string)) {
				cc.append(CommandFactory.addFileToInfoUnit(binaryNodeIdToFileMap.get(string), read
						.getChildByNodeId(string), domain));
			}
		}
		cc.setLabel("Add item");
		domain.getCommandStack().execute(cc);
		if (domain instanceof IDisposable) {
			((IDisposable) domain).dispose();
		}

	}

}
