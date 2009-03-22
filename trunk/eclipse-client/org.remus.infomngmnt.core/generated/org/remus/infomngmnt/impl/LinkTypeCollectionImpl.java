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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.LinkType;
import org.remus.infomngmnt.LinkTypeCollection;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link Type Collection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.LinkTypeCollectionImpl#getAvailableLinkTypes <em>Available Link Types</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinkTypeCollectionImpl extends EObjectImpl implements LinkTypeCollection {
	/**
	 * The cached value of the '{@link #getAvailableLinkTypes() <em>Available Link Types</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAvailableLinkTypes()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, LinkType> availableLinkTypes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkTypeCollectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.LINK_TYPE_COLLECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, LinkType> getAvailableLinkTypes() {
		if (availableLinkTypes == null) {
			availableLinkTypes = new EcoreEMap<String,LinkType>(InfomngmntPackage.Literals.STRING_TO_LINK_TYPE_MAP, StringToLinkTypeMapImpl.class, this, InfomngmntPackage.LINK_TYPE_COLLECTION__AVAILABLE_LINK_TYPES);
		}
		return availableLinkTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.LINK_TYPE_COLLECTION__AVAILABLE_LINK_TYPES:
				return ((InternalEList<?>)getAvailableLinkTypes()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.LINK_TYPE_COLLECTION__AVAILABLE_LINK_TYPES:
				if (coreType) return getAvailableLinkTypes();
				else return getAvailableLinkTypes().map();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfomngmntPackage.LINK_TYPE_COLLECTION__AVAILABLE_LINK_TYPES:
				((EStructuralFeature.Setting)getAvailableLinkTypes()).set(newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case InfomngmntPackage.LINK_TYPE_COLLECTION__AVAILABLE_LINK_TYPES:
				getAvailableLinkTypes().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case InfomngmntPackage.LINK_TYPE_COLLECTION__AVAILABLE_LINK_TYPES:
				return availableLinkTypes != null && !availableLinkTypes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //LinkTypeCollectionImpl
