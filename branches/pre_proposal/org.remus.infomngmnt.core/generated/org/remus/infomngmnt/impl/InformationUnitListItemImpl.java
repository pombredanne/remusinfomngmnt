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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Information Unit List Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitListItemImpl#getSynchronizationMetaData <em>Synchronization Meta Data</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitListItemImpl#getWorkspacePath <em>Workspace Path</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.InformationUnitListItemImpl#isUnread <em>Unread</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InformationUnitListItemImpl extends AbstractInformationUnitImpl implements InformationUnitListItem {
	/**
	 * The cached value of the '{@link #getSynchronizationMetaData() <em>Synchronization Meta Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSynchronizationMetaData()
	 * @generated
	 * @ordered
	 */
	protected SynchronizationMetadata synchronizationMetaData;

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
	 * The default value of the '{@link #isUnread() <em>Unread</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnread()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UNREAD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUnread() <em>Unread</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUnread()
	 * @generated
	 * @ordered
	 */
	protected boolean unread = UNREAD_EDEFAULT;

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
	public boolean isUnread() {
		return unread;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUnread(boolean newUnread) {
		boolean oldUnread = unread;
		unread = newUnread;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__UNREAD, oldUnread, unread));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynchronizationMetadata getSynchronizationMetaData() {
		return synchronizationMetaData;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSynchronizationMetaData(SynchronizationMetadata newSynchronizationMetaData, NotificationChain msgs) {
		SynchronizationMetadata oldSynchronizationMetaData = synchronizationMetaData;
		synchronizationMetaData = newSynchronizationMetaData;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA, oldSynchronizationMetaData, newSynchronizationMetaData);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSynchronizationMetaData(SynchronizationMetadata newSynchronizationMetaData) {
		if (newSynchronizationMetaData != synchronizationMetaData) {
			NotificationChain msgs = null;
			if (synchronizationMetaData != null)
				msgs = ((InternalEObject)synchronizationMetaData).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA, null, msgs);
			if (newSynchronizationMetaData != null)
				msgs = ((InternalEObject)newSynchronizationMetaData).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA, null, msgs);
			msgs = basicSetSynchronizationMetaData(newSynchronizationMetaData, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA, newSynchronizationMetaData, newSynchronizationMetaData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA:
				return basicSetSynchronizationMetaData(null, msgs);
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
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA:
				return getSynchronizationMetaData();
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH:
				return getWorkspacePath();
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__UNREAD:
				return isUnread();
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
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA:
				setSynchronizationMetaData((SynchronizationMetadata)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH:
				setWorkspacePath((String)newValue);
				return;
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__UNREAD:
				setUnread((Boolean)newValue);
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
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA:
				setSynchronizationMetaData((SynchronizationMetadata)null);
				return;
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH:
				setWorkspacePath(WORKSPACE_PATH_EDEFAULT);
				return;
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__UNREAD:
				setUnread(UNREAD_EDEFAULT);
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
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA:
				return synchronizationMetaData != null;
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH:
				return WORKSPACE_PATH_EDEFAULT == null ? workspacePath != null : !WORKSPACE_PATH_EDEFAULT.equals(workspacePath);
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__UNREAD:
				return unread != UNREAD_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == SynchronizableObject.class) {
			switch (derivedFeatureID) {
				case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA: return InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == SynchronizableObject.class) {
			switch (baseFeatureID) {
				case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA: return InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM__SYNCHRONIZATION_META_DATA;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(", unread: ");
		result.append(unread);
		result.append(')');
		return result.toString();
	}

} //InformationUnitListItemImpl
