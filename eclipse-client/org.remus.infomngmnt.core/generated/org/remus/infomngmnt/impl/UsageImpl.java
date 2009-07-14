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

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.Usage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Usage</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.UsageImpl#getLastAccess <em>Last Access</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.UsageImpl#getAccessCount <em>Access Count</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UsageImpl extends EObjectImpl implements Usage {
	/**
	 * The default value of the '{@link #getLastAccess() <em>Last Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastAccess()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_ACCESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastAccess() <em>Last Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastAccess()
	 * @generated
	 * @ordered
	 */
	protected Date lastAccess = LAST_ACCESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getAccessCount() <em>Access Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessCount()
	 * @generated
	 * @ordered
	 */
	protected static final int ACCESS_COUNT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getAccessCount() <em>Access Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAccessCount()
	 * @generated
	 * @ordered
	 */
	protected int accessCount = ACCESS_COUNT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UsageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.USAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getLastAccess() {
		return lastAccess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastAccess(Date newLastAccess) {
		Date oldLastAccess = lastAccess;
		lastAccess = newLastAccess;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.USAGE__LAST_ACCESS, oldLastAccess, lastAccess));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getAccessCount() {
		return accessCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAccessCount(int newAccessCount) {
		int oldAccessCount = accessCount;
		accessCount = newAccessCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.USAGE__ACCESS_COUNT, oldAccessCount, accessCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.USAGE__LAST_ACCESS:
				return getLastAccess();
			case InfomngmntPackage.USAGE__ACCESS_COUNT:
				return getAccessCount();
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
			case InfomngmntPackage.USAGE__LAST_ACCESS:
				setLastAccess((Date)newValue);
				return;
			case InfomngmntPackage.USAGE__ACCESS_COUNT:
				setAccessCount((Integer)newValue);
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
			case InfomngmntPackage.USAGE__LAST_ACCESS:
				setLastAccess(LAST_ACCESS_EDEFAULT);
				return;
			case InfomngmntPackage.USAGE__ACCESS_COUNT:
				setAccessCount(ACCESS_COUNT_EDEFAULT);
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
			case InfomngmntPackage.USAGE__LAST_ACCESS:
				return LAST_ACCESS_EDEFAULT == null ? lastAccess != null : !LAST_ACCESS_EDEFAULT.equals(lastAccess);
			case InfomngmntPackage.USAGE__ACCESS_COUNT:
				return accessCount != ACCESS_COUNT_EDEFAULT;
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
		result.append(" (lastAccess: ");
		result.append(lastAccess);
		result.append(", accessCount: ");
		result.append(accessCount);
		result.append(')');
		return result.toString();
	}

} //UsageImpl
