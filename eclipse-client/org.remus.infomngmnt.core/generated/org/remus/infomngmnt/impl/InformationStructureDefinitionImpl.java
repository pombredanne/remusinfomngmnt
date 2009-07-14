/**
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *      Tom Seidel - initial API and implementation
 * 
 *
 * $Id$
 */
package org.remus.infomngmnt.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.InformationStructureItem;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Information Structure Definition</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.InformationStructureDefinitionImpl#getStructurePool <em>Structure Pool</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InformationStructureDefinitionImpl extends InformationStructureImpl implements
		InformationStructureDefinition {
	/**
	 * The cached value of the '{@link #getStructurePool() <em>Structure Pool</em>}' containment reference list.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @see #getStructurePool()
	 * @generated
	 * @ordered
	 */
	protected EList<InformationStructureItem> structurePool;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected InformationStructureDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.INFORMATION_STRUCTURE_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InformationStructureItem> getStructurePool() {
		if (structurePool == null) {
			structurePool = new EObjectContainmentEList<InformationStructureItem>(InformationStructureItem.class, this, InfomngmntPackage.INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL);
		}
		return structurePool;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL:
				return ((InternalEList<?>)getStructurePool()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL:
				return getStructurePool();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL:
				getStructurePool().clear();
				getStructurePool().addAll((Collection<? extends InformationStructureItem>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL:
				getStructurePool().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL:
				return structurePool != null && !structurePool.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // InformationStructureDefinitionImpl
