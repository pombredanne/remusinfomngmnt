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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RemusTransferType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>New Element Rules</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.NewElementRulesImpl#getTransferTypes <em>Transfer Types</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NewElementRulesImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.NewElementRulesImpl#isDeletable <em>Deletable</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NewElementRulesImpl extends EObjectImpl implements NewElementRules {
	/**
	 * The cached value of the '{@link #getTransferTypes() <em>Transfer Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransferTypes()
	 * @generated
	 * @ordered
	 */
	protected EList<RemusTransferType> transferTypes;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isDeletable() <em>Deletable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeletable()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DELETABLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDeletable() <em>Deletable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDeletable()
	 * @generated
	 * @ordered
	 */
	protected boolean deletable = DELETABLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NewElementRulesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.NEW_ELEMENT_RULES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RemusTransferType> getTransferTypes() {
		if (transferTypes == null) {
			transferTypes = new EObjectContainmentEList<RemusTransferType>(RemusTransferType.class, this, InfomngmntPackage.NEW_ELEMENT_RULES__TRANSFER_TYPES);
		}
		return transferTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.NEW_ELEMENT_RULES__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDeletable() {
		return deletable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeletable(boolean newDeletable) {
		boolean oldDeletable = deletable;
		deletable = newDeletable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.NEW_ELEMENT_RULES__DELETABLE, oldDeletable, deletable));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.NEW_ELEMENT_RULES__TRANSFER_TYPES:
				return ((InternalEList<?>)getTransferTypes()).basicRemove(otherEnd, msgs);
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
			case InfomngmntPackage.NEW_ELEMENT_RULES__TRANSFER_TYPES:
				return getTransferTypes();
			case InfomngmntPackage.NEW_ELEMENT_RULES__NAME:
				return getName();
			case InfomngmntPackage.NEW_ELEMENT_RULES__DELETABLE:
				return isDeletable();
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
			case InfomngmntPackage.NEW_ELEMENT_RULES__TRANSFER_TYPES:
				getTransferTypes().clear();
				getTransferTypes().addAll((Collection<? extends RemusTransferType>)newValue);
				return;
			case InfomngmntPackage.NEW_ELEMENT_RULES__NAME:
				setName((String)newValue);
				return;
			case InfomngmntPackage.NEW_ELEMENT_RULES__DELETABLE:
				setDeletable((Boolean)newValue);
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
			case InfomngmntPackage.NEW_ELEMENT_RULES__TRANSFER_TYPES:
				getTransferTypes().clear();
				return;
			case InfomngmntPackage.NEW_ELEMENT_RULES__NAME:
				setName(NAME_EDEFAULT);
				return;
			case InfomngmntPackage.NEW_ELEMENT_RULES__DELETABLE:
				setDeletable(DELETABLE_EDEFAULT);
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
			case InfomngmntPackage.NEW_ELEMENT_RULES__TRANSFER_TYPES:
				return transferTypes != null && !transferTypes.isEmpty();
			case InfomngmntPackage.NEW_ELEMENT_RULES__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case InfomngmntPackage.NEW_ELEMENT_RULES__DELETABLE:
				return deletable != DELETABLE_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", deletable: ");
		result.append(deletable);
		result.append(')');
		return result.toString();
	}

} //NewElementRulesImpl
