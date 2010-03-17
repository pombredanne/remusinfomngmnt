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
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.Link;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.LinkImpl#getLocalInformationUnit <em>Local Information Unit</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.LinkImpl#getRemoteUrl <em>Remote Url</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.LinkImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LinkImpl extends EObjectImpl implements Link {
	/**
	 * The default value of the '{@link #getLocalInformationUnit() <em>Local Information Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalInformationUnit()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCAL_INFORMATION_UNIT_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getLocalInformationUnit() <em>Local Information Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalInformationUnit()
	 * @generated
	 * @ordered
	 */
	protected String localInformationUnit = LOCAL_INFORMATION_UNIT_EDEFAULT;
	/**
	 * The default value of the '{@link #getRemoteUrl() <em>Remote Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String REMOTE_URL_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getRemoteUrl() <em>Remote Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteUrl()
	 * @generated
	 * @ordered
	 */
	protected String remoteUrl = REMOTE_URL_EDEFAULT;
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocalInformationUnit() {
		return localInformationUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalInformationUnit(String newLocalInformationUnit) {
		String oldLocalInformationUnit = localInformationUnit;
		localInformationUnit = newLocalInformationUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.LINK__LOCAL_INFORMATION_UNIT, oldLocalInformationUnit, localInformationUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRemoteUrl() {
		return remoteUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoteUrl(String newRemoteUrl) {
		String oldRemoteUrl = remoteUrl;
		remoteUrl = newRemoteUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.LINK__REMOTE_URL, oldRemoteUrl, remoteUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.LINK__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.LINK__LOCAL_INFORMATION_UNIT:
				return getLocalInformationUnit();
			case InfomngmntPackage.LINK__REMOTE_URL:
				return getRemoteUrl();
			case InfomngmntPackage.LINK__TYPE:
				return getType();
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
			case InfomngmntPackage.LINK__LOCAL_INFORMATION_UNIT:
				setLocalInformationUnit((String)newValue);
				return;
			case InfomngmntPackage.LINK__REMOTE_URL:
				setRemoteUrl((String)newValue);
				return;
			case InfomngmntPackage.LINK__TYPE:
				setType((String)newValue);
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
			case InfomngmntPackage.LINK__LOCAL_INFORMATION_UNIT:
				setLocalInformationUnit(LOCAL_INFORMATION_UNIT_EDEFAULT);
				return;
			case InfomngmntPackage.LINK__REMOTE_URL:
				setRemoteUrl(REMOTE_URL_EDEFAULT);
				return;
			case InfomngmntPackage.LINK__TYPE:
				setType(TYPE_EDEFAULT);
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
			case InfomngmntPackage.LINK__LOCAL_INFORMATION_UNIT:
				return LOCAL_INFORMATION_UNIT_EDEFAULT == null ? localInformationUnit != null : !LOCAL_INFORMATION_UNIT_EDEFAULT.equals(localInformationUnit);
			case InfomngmntPackage.LINK__REMOTE_URL:
				return REMOTE_URL_EDEFAULT == null ? remoteUrl != null : !REMOTE_URL_EDEFAULT.equals(remoteUrl);
			case InfomngmntPackage.LINK__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
		result.append(" (localInformationUnit: ");
		result.append(localInformationUnit);
		result.append(", remoteUrl: ");
		result.append(remoteUrl);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //LinkImpl
