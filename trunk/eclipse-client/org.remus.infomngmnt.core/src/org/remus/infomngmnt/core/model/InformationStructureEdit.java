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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.internal.creation.InformationUnitCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationStructureEdit {

	private final String infoType;
	private final EditingDomain domain;
	private final InformationUnitCreator creator;

	private InformationStructureEdit(final String infoType, final EditingDomain domain) {
		this.infoType = infoType;
		this.domain = domain;
		this.creator = new InformationUnitCreator();
	}

	public static InformationStructureEdit newSession(final String infoType) {
		return new InformationStructureEdit(infoType, null);
	}

	public static InformationStructureEdit newSession(final String infoType,
			final EditingDomain domain) {
		return new InformationStructureEdit(infoType, domain);
	}

	/**
	 * Creates an information subtype based on the underlying system. This
	 * method automatically generates all required "subsubnodes" of the created
	 * subnode.
	 * 
	 * @param nodeId
	 *            the id of the new node
	 * @param value
	 *            a value of the new subnode, or <code>null</code> if no value.
	 * @return the new created subnode.
	 */
	public InformationUnit createSubType(final String nodeId, final Object value) {
		return this.creator.createSubType(this.infoType, nodeId, value);
	}

	public void setValue(final InformationUnit anyParentItem, final String nodeId,
			final Object value) {
		setValue(anyParentItem, nodeId, value, this.domain);
	}

	public void setValueByPath(final InformationUnit anyParentItem, final Object value,
			final String... path) {
		setValueByPath(anyParentItem, value, this.domain, path);
	}

	public void setValueByPath(final InformationUnit anyParentItem, final Object value,
			final EditingDomain domain2, final String... path) {
		this.creator.setValue(anyParentItem, value, this.infoType, domain2, path);

	}

	public void setObject(final InformationUnit anyParentUnit, final String nodeId,
			final InformationUnit value) {
		this.creator.setObject(anyParentUnit, nodeId, value, this.infoType, this.domain);
	}

	/**
	 * Sets a given value to the given nodeId.
	 * 
	 * @param anyParentItem
	 *            any information node that belongs to the resulting information
	 *            unit. This is needed as reference point to navigate to the
	 *            node where the value should be set.
	 * @param nodeId
	 *            the id of the node where the value should be set.
	 * @param value
	 *            the value
	 * @param editingdomain
	 *            optional: editingdomain
	 * @see InformationUnitCreator
	 * @see InformationStrucutureItem
	 */
	public void setValue(final InformationUnit anyParentItem, final String nodeId,
			final Object value, final EditingDomain editingDomain) {
		this.creator.setValue(anyParentItem, nodeId, value, this.infoType, editingDomain);
	}

	/**
	 * Creates a new basic information unit from the underlying system.
	 * 
	 * @param infoTypeId
	 *            the type of the information-type. This type must be known to
	 *            the system
	 * @return a new {@link InformationUnit}. This unit is neither bound to a
	 *         {@link Resource} or cached within an {@link EditingDomain}
	 */
	public InformationUnit newInformationUnit() {
		return this.creator.createNewObject(this.infoType);
	}

	/**
	 * @param baseObject
	 * @param dynamicNode
	 * @param editingDomain
	 */
	public void addDynamicNode(final InformationUnit baseObject, final InformationUnit dynamicNode,
			final EditingDomain editingDomain) {
		this.creator.addDynamicNode(baseObject, dynamicNode, editingDomain);
	}

}
