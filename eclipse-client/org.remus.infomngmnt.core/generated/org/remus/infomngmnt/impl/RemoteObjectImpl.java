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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remote Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getPossibleInfoTypeId <em>Possible Info Type Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class RemoteObjectImpl extends EObjectImpl implements RemoteObject {
	/**
	 * The cached value of the '{@link #getPossibleInfoTypeId() <em>Possible Info Type Id</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPossibleInfoTypeId()
	 * @generated
	 * @ordered
	 */
	protected EList<String> possibleInfoTypeId;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoteObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.REMOTE_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPossibleInfoTypeId() {
		if (possibleInfoTypeId == null) {
			possibleInfoTypeId = new EDataTypeUniqueEList<String>(String.class, this, InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID);
		}
		return possibleInfoTypeId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
				return getPossibleInfoTypeId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
				getPossibleInfoTypeId().clear();
				getPossibleInfoTypeId().addAll((Collection<? extends String>)newValue);
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
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
				getPossibleInfoTypeId().clear();
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
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
				return possibleInfoTypeId != null && !possibleInfoTypeId.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (possibleInfoTypeId: ");
		result.append(possibleInfoTypeId);
		result.append(')');
		return result.toString();
	}

} //RemoteObjectImpl
