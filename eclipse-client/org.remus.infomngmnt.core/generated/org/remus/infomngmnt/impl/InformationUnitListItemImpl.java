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
import org.remus.infomngmnt.InformationUnitListItem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Information Unit List Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitListItemImpl#getWorkspacePath <em>Workspace Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InformationUnitListItemImpl extends AbstractInformationUnitImpl implements InformationUnitListItem {
	/**
	 * The default value of the '{@link #getWorkspacePath() <em>Workspace Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkspacePath()
	 * @generated
	 * @ordered
	 */
	protected static final String WORKSPACE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWorkspacePath() <em>Workspace Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWorkspacePath()
	 * @generated
	 * @ordered
	 */
	protected String workspacePath = WORKSPACE_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InformationUnitListItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWorkspacePath() {
		return workspacePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWorkspacePath(String newWorkspacePath) {
		String oldWorkspacePath = workspacePath;
		workspacePath = newWorkspacePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH, oldWorkspacePath, workspacePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH:
				return getWorkspacePath();
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
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH:
				setWorkspacePath((String)newValue);
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
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH:
				setWorkspacePath(WORKSPACE_PATH_EDEFAULT);
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
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH:
				return WORKSPACE_PATH_EDEFAULT == null ? workspacePath != null : !WORKSPACE_PATH_EDEFAULT.equals(workspacePath);
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
		result.append(" (workspacePath: ");
		result.append(workspacePath);
		result.append(')');
		return result.toString();
	}

} //InformationUnitListItemImpl
