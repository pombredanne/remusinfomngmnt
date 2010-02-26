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

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructureItem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Information Structure Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.InformationStructureItemImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationStructureItemImpl#isCreateAlways <em>Create Always</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InformationStructureItemImpl extends InformationStructureImpl implements InformationStructureItem {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isCreateAlways() <em>Create Always</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCreateAlways()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CREATE_ALWAYS_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCreateAlways() <em>Create Always</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCreateAlways()
	 * @generated
	 * @ordered
	 */
	protected boolean createAlways = CREATE_ALWAYS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InformationStructureItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.INFORMATION_STRUCTURE_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCreateAlways() {
		return createAlways;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreateAlways(boolean newCreateAlways) {
		boolean oldCreateAlways = createAlways;
		createAlways = newCreateAlways;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS, oldCreateAlways, createAlways));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__ID:
				return getId();
			case InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS:
				return isCreateAlways();
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
			case InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__ID:
				setId((String)newValue);
				return;
			case InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS:
				setCreateAlways((Boolean)newValue);
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
			case InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__ID:
				setId(ID_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS:
				setCreateAlways(CREATE_ALWAYS_EDEFAULT);
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
			case InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case InfomngmntPackage.INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS:
				return createAlways != CREATE_ALWAYS_EDEFAULT;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", createAlways: ");
		result.append(createAlways);
		result.append(')');
		return result.toString();
	}

} //InformationStructureItemImpl
