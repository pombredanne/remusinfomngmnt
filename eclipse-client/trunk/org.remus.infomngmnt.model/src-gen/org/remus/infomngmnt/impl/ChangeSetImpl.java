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

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteRepository;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Change Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetImpl#getTargetCategory <em>Target Category</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetImpl#getChangeSetItems <em>Change Set Items</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.ChangeSetImpl#getRepository <em>Repository</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChangeSetImpl extends AdapterImpl implements ChangeSet {
	/**
	 * The cached value of the '{@link #getTargetCategory() <em>Target Category</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetCategory()
	 * @generated
	 * @ordered
	 */
	protected Category targetCategory;

	/**
	 * The cached value of the '{@link #getChangeSetItems() <em>Change Set Items</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChangeSetItems()
	 * @generated
	 * @ordered
	 */
	protected EList<ChangeSetItem> changeSetItems;

	/**
	 * The cached value of the '{@link #getRepository() <em>Repository</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepository()
	 * @generated
	 * @ordered
	 */
	protected RemoteRepository repository;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChangeSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.CHANGE_SET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category getTargetCategory() {
		if (targetCategory != null && targetCategory.eIsProxy()) {
			InternalEObject oldTargetCategory = (InternalEObject)targetCategory;
			targetCategory = (Category)eResolveProxy(oldTargetCategory);
			if (targetCategory != oldTargetCategory) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InfomngmntPackage.CHANGE_SET__TARGET_CATEGORY, oldTargetCategory, targetCategory));
			}
		}
		return targetCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category basicGetTargetCategory() {
		return targetCategory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTargetCategory(Category newTargetCategory) {
		Category oldTargetCategory = targetCategory;
		targetCategory = newTargetCategory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CHANGE_SET__TARGET_CATEGORY, oldTargetCategory, targetCategory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ChangeSetItem> getChangeSetItems() {
		if (changeSetItems == null) {
			changeSetItems = new EObjectContainmentEList<ChangeSetItem>(ChangeSetItem.class, this, InfomngmntPackage.CHANGE_SET__CHANGE_SET_ITEMS);
		}
		return changeSetItems;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoteRepository getRepository() {
		if (repository != null && repository.eIsProxy()) {
			InternalEObject oldRepository = (InternalEObject)repository;
			repository = (RemoteRepository)eResolveProxy(oldRepository);
			if (repository != oldRepository) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, InfomngmntPackage.CHANGE_SET__REPOSITORY, oldRepository, repository));
			}
		}
		return repository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoteRepository basicGetRepository() {
		return repository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepository(RemoteRepository newRepository) {
		RemoteRepository oldRepository = repository;
		repository = newRepository;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.CHANGE_SET__REPOSITORY, oldRepository, repository));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.CHANGE_SET__CHANGE_SET_ITEMS:
				return ((InternalEList<?>)getChangeSetItems()).basicRemove(otherEnd, msgs);
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
			case InfomngmntPackage.CHANGE_SET__TARGET_CATEGORY:
				if (resolve) return getTargetCategory();
				return basicGetTargetCategory();
			case InfomngmntPackage.CHANGE_SET__CHANGE_SET_ITEMS:
				return getChangeSetItems();
			case InfomngmntPackage.CHANGE_SET__REPOSITORY:
				if (resolve) return getRepository();
				return basicGetRepository();
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
			case InfomngmntPackage.CHANGE_SET__TARGET_CATEGORY:
				setTargetCategory((Category)newValue);
				return;
			case InfomngmntPackage.CHANGE_SET__CHANGE_SET_ITEMS:
				getChangeSetItems().clear();
				getChangeSetItems().addAll((Collection<? extends ChangeSetItem>)newValue);
				return;
			case InfomngmntPackage.CHANGE_SET__REPOSITORY:
				setRepository((RemoteRepository)newValue);
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
			case InfomngmntPackage.CHANGE_SET__TARGET_CATEGORY:
				setTargetCategory((Category)null);
				return;
			case InfomngmntPackage.CHANGE_SET__CHANGE_SET_ITEMS:
				getChangeSetItems().clear();
				return;
			case InfomngmntPackage.CHANGE_SET__REPOSITORY:
				setRepository((RemoteRepository)null);
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
			case InfomngmntPackage.CHANGE_SET__TARGET_CATEGORY:
				return targetCategory != null;
			case InfomngmntPackage.CHANGE_SET__CHANGE_SET_ITEMS:
				return changeSetItems != null && !changeSetItems.isEmpty();
			case InfomngmntPackage.CHANGE_SET__REPOSITORY:
				return repository != null;
		}
		return super.eIsSet(featureID);
	}

} //ChangeSetImpl
