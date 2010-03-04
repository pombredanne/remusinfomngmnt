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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change Set Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetItemImpl#getRemoteConvertedContainer <em>Remote Converted Container</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetItemImpl#getRemoteOriginalObject <em>Remote Original Object</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetItemImpl#getLocalContainer <em>Local Container</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetItemImpl#getSyncCategoryActionMap <em>Sync Category Action Map</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetItemImpl#getSyncObjectActionMap <em>Sync Object Action Map</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetItemImpl#getRemoteFullObjectMap <em>Remote Full Object Map</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChangeSetItemImpl extends AdapterImpl implements ChangeSetItem {
	/**
	 * The cached value of the '{@link #getRemoteConvertedContainer() <em>Remote Converted Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteConvertedContainer()
	 * @generated
	 * @ordered
	 */
	protected Category remoteConvertedContainer;

	/**
	 * The cached value of the '{@link #getRemoteOriginalObject() <em>Remote Original Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteOriginalObject()
	 * @generated
	 * @ordered
	 */
	protected RemoteContainer remoteOriginalObject;

	/**
	 * The cached value of the '{@link #getLocalContainer() <em>Local Container</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocalContainer()
	 * @generated
	 * @ordered
	 */
	protected Category localContainer;

	/**
	 * The cached value of the '{@link #getSyncCategoryActionMap() <em>Sync Category Action Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncCategoryActionMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<Category, SynchronizationAction> syncCategoryActionMap;

	/**
	 * The cached value of the '{@link #getSyncObjectActionMap() <em>Sync Object Action Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncObjectActionMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<SynchronizableObject, SynchronizationAction> syncObjectActionMap;

	/**
	 * The cached value of the '{@link #getRemoteFullObjectMap() <em>Remote Full Object Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRemoteFullObjectMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<InformationUnitListItem, InformationUnit> remoteFullObjectMap;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeSetItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.CHANGE_SET_ITEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category getRemoteConvertedContainer() {
		return remoteConvertedContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRemoteConvertedContainer(Category newRemoteConvertedContainer, NotificationChain msgs) {
		Category oldRemoteConvertedContainer = remoteConvertedContainer;
		remoteConvertedContainer = newRemoteConvertedContainer;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER, oldRemoteConvertedContainer, newRemoteConvertedContainer);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoteConvertedContainer(Category newRemoteConvertedContainer) {
		if (newRemoteConvertedContainer != remoteConvertedContainer) {
			NotificationChain msgs = null;
			if (remoteConvertedContainer != null)
				msgs = ((InternalEObject)remoteConvertedContainer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER, null, msgs);
			if (newRemoteConvertedContainer != null)
				msgs = ((InternalEObject)newRemoteConvertedContainer).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER, null, msgs);
			msgs = basicSetRemoteConvertedContainer(newRemoteConvertedContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER, newRemoteConvertedContainer, newRemoteConvertedContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoteContainer getRemoteOriginalObject() {
		return remoteOriginalObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRemoteOriginalObject(RemoteContainer newRemoteOriginalObject, NotificationChain msgs) {
		RemoteContainer oldRemoteOriginalObject = remoteOriginalObject;
		remoteOriginalObject = newRemoteOriginalObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT, oldRemoteOriginalObject, newRemoteOriginalObject);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRemoteOriginalObject(RemoteContainer newRemoteOriginalObject) {
		if (newRemoteOriginalObject != remoteOriginalObject) {
			NotificationChain msgs = null;
			if (remoteOriginalObject != null)
				msgs = ((InternalEObject)remoteOriginalObject).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT, null, msgs);
			if (newRemoteOriginalObject != null)
				msgs = ((InternalEObject)newRemoteOriginalObject).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT, null, msgs);
			msgs = basicSetRemoteOriginalObject(newRemoteOriginalObject, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT, newRemoteOriginalObject, newRemoteOriginalObject));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category getLocalContainer() {
		if (localContainer != null && localContainer.eIsProxy()) {
			InternalEObject oldLocalContainer = (InternalEObject)localContainer;
			localContainer = (Category)eResolveProxy(oldLocalContainer);
			if (localContainer != oldLocalContainer) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InfomngmntPackage.CHANGE_SET_ITEM__LOCAL_CONTAINER, oldLocalContainer, localContainer));
			}
		}
		return localContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category basicGetLocalContainer() {
		return localContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocalContainer(Category newLocalContainer) {
		Category oldLocalContainer = localContainer;
		localContainer = newLocalContainer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CHANGE_SET_ITEM__LOCAL_CONTAINER, oldLocalContainer, localContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<Category, SynchronizationAction> getSyncCategoryActionMap() {
		if (syncCategoryActionMap == null) {
			syncCategoryActionMap = new EcoreEMap<Category,SynchronizationAction>(InfomngmntPackage.Literals.CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP, CategoryToSynchronizationActionMapImpl.class, this, InfomngmntPackage.CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP);
		}
		return syncCategoryActionMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<SynchronizableObject, SynchronizationAction> getSyncObjectActionMap() {
		if (syncObjectActionMap == null) {
			syncObjectActionMap = new EcoreEMap<SynchronizableObject,SynchronizationAction>(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP, SynchronizableObjectToSynchronizationActionMapImpl.class, this, InfomngmntPackage.CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP);
		}
		return syncObjectActionMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<InformationUnitListItem, InformationUnit> getRemoteFullObjectMap() {
		if (remoteFullObjectMap == null) {
			remoteFullObjectMap = new EcoreEMap<InformationUnitListItem,InformationUnit>(InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP, InformationUnitListItemToInformationUnitMapImpl.class, this, InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP);
		}
		return remoteFullObjectMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER:
				return basicSetRemoteConvertedContainer(null, msgs);
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT:
				return basicSetRemoteOriginalObject(null, msgs);
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP:
				return ((InternalEList<?>)getSyncCategoryActionMap()).basicRemove(otherEnd, msgs);
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP:
				return ((InternalEList<?>)getSyncObjectActionMap()).basicRemove(otherEnd, msgs);
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP:
				return ((InternalEList<?>)getRemoteFullObjectMap()).basicRemove(otherEnd, msgs);
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
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER:
				return getRemoteConvertedContainer();
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT:
				return getRemoteOriginalObject();
			case InfomngmntPackage.CHANGE_SET_ITEM__LOCAL_CONTAINER:
				if (resolve) return getLocalContainer();
				return basicGetLocalContainer();
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP:
				if (coreType) return getSyncCategoryActionMap();
				else return getSyncCategoryActionMap().map();
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP:
				if (coreType) return getSyncObjectActionMap();
				else return getSyncObjectActionMap().map();
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP:
				if (coreType) return getRemoteFullObjectMap();
				else return getRemoteFullObjectMap().map();
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
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER:
				setRemoteConvertedContainer((Category)newValue);
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT:
				setRemoteOriginalObject((RemoteContainer)newValue);
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__LOCAL_CONTAINER:
				setLocalContainer((Category)newValue);
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP:
				((EStructuralFeature.Setting)getSyncCategoryActionMap()).set(newValue);
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP:
				((EStructuralFeature.Setting)getSyncObjectActionMap()).set(newValue);
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP:
				((EStructuralFeature.Setting)getRemoteFullObjectMap()).set(newValue);
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
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER:
				setRemoteConvertedContainer((Category)null);
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT:
				setRemoteOriginalObject((RemoteContainer)null);
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__LOCAL_CONTAINER:
				setLocalContainer((Category)null);
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP:
				getSyncCategoryActionMap().clear();
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP:
				getSyncObjectActionMap().clear();
				return;
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP:
				getRemoteFullObjectMap().clear();
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
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER:
				return remoteConvertedContainer != null;
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT:
				return remoteOriginalObject != null;
			case InfomngmntPackage.CHANGE_SET_ITEM__LOCAL_CONTAINER:
				return localContainer != null;
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP:
				return syncCategoryActionMap != null && !syncCategoryActionMap.isEmpty();
			case InfomngmntPackage.CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP:
				return syncObjectActionMap != null && !syncObjectActionMap.isEmpty();
			case InfomngmntPackage.CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP:
				return remoteFullObjectMap != null && !remoteFullObjectMap.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ChangeSetItemImpl
