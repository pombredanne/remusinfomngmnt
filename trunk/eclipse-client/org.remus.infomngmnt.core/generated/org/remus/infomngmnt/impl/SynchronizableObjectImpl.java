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
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Synchronizable Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.SynchronizableObjectImpl#getSynchronizationMetaData <em>Synchronization Meta Data</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.SynchronizableObjectImpl#getMarkedAsDeleteItems <em>Marked As Delete Items</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SynchronizableObjectImpl extends AdapterImpl implements SynchronizableObject {
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
	 * The cached value of the '{@link #getMarkedAsDeleteItems() <em>Marked As Delete Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMarkedAsDeleteItems()
	 * @generated
	 * @ordered
	 */
	protected EList<SynchronizableObject> markedAsDeleteItems;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SynchronizableObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA, oldSynchronizationMetaData, newSynchronizationMetaData);
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
				msgs = ((InternalEObject)synchronizationMetaData).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA, null, msgs);
			if (newSynchronizationMetaData != null)
				msgs = ((InternalEObject)newSynchronizationMetaData).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA, null, msgs);
			msgs = basicSetSynchronizationMetaData(newSynchronizationMetaData, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA, newSynchronizationMetaData, newSynchronizationMetaData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SynchronizableObject> getMarkedAsDeleteItems() {
		if (markedAsDeleteItems == null) {
			markedAsDeleteItems = new EObjectContainmentEList<SynchronizableObject>(SynchronizableObject.class, this, InfomngmntPackage.SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS);
		}
		return markedAsDeleteItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA:
				return basicSetSynchronizationMetaData(null, msgs);
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS:
				return ((InternalEList<?>)getMarkedAsDeleteItems()).basicRemove(otherEnd, msgs);
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
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA:
				return getSynchronizationMetaData();
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS:
				return getMarkedAsDeleteItems();
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
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA:
				setSynchronizationMetaData((SynchronizationMetadata)newValue);
				return;
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS:
				getMarkedAsDeleteItems().clear();
				getMarkedAsDeleteItems().addAll((Collection<? extends SynchronizableObject>)newValue);
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
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA:
				setSynchronizationMetaData((SynchronizationMetadata)null);
				return;
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS:
				getMarkedAsDeleteItems().clear();
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
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA:
				return synchronizationMetaData != null;
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS:
				return markedAsDeleteItems != null && !markedAsDeleteItems.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SynchronizableObjectImpl
