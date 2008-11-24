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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RecentlyUsedKeywords;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Recently Used Keywords</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl#getMaxlength <em>Maxlength</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RecentlyUsedKeywordsImpl#getKeywords <em>Keywords</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RecentlyUsedKeywordsImpl extends EObjectImpl implements RecentlyUsedKeywords {
	/**
	 * The default value of the '{@link #getMaxlength() <em>Maxlength</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxlength()
	 * @generated
	 * @ordered
	 */
	protected static final int MAXLENGTH_EDEFAULT = 100;

	/**
	 * The cached value of the '{@link #getMaxlength() <em>Maxlength</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxlength()
	 * @generated
	 * @ordered
	 */
	protected int maxlength = MAXLENGTH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getKeywords() <em>Keywords</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeywords()
	 * @generated
	 * @ordered
	 */
	protected EList<String> keywords;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RecentlyUsedKeywordsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.RECENTLY_USED_KEYWORDS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxlength() {
		return maxlength;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxlength(int newMaxlength) {
		int oldMaxlength = maxlength;
		maxlength = newMaxlength;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.RECENTLY_USED_KEYWORDS__MAXLENGTH, oldMaxlength, maxlength));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getKeywords() {
		if (keywords == null) {
			keywords = new EDataTypeUniqueEList<String>(String.class, this, InfomngmntPackage.RECENTLY_USED_KEYWORDS__KEYWORDS);
		}
		return keywords;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS__MAXLENGTH:
				return new Integer(getMaxlength());
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS__KEYWORDS:
				return getKeywords();
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
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS__MAXLENGTH:
				setMaxlength(((Integer)newValue).intValue());
				return;
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS__KEYWORDS:
				getKeywords().clear();
				getKeywords().addAll((Collection<? extends String>)newValue);
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
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS__MAXLENGTH:
				setMaxlength(MAXLENGTH_EDEFAULT);
				return;
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS__KEYWORDS:
				getKeywords().clear();
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
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS__MAXLENGTH:
				return maxlength != MAXLENGTH_EDEFAULT;
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS__KEYWORDS:
				return keywords != null && !keywords.isEmpty();
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
		result.append(" (maxlength: ");
		result.append(maxlength);
		result.append(", keywords: ");
		result.append(keywords);
		result.append(')');
		return result.toString();
	}

} //RecentlyUsedKeywordsImpl
