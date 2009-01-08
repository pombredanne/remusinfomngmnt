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
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remote Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteContainerImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteContainerImpl#getExclusionChildren <em>Exclusion Children</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RemoteContainerImpl extends RemoteObjectImpl implements RemoteContainer {
	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<RemoteObject> children;

	/**
	 * The cached value of the '{@link #getExclusionChildren() <em>Exclusion Children</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExclusionChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<RemoteObject> exclusionChildren;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoteContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.REMOTE_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RemoteObject> getChildren() {
		if (children == null) {
			children = new EObjectContainmentEList<RemoteObject>(RemoteObject.class, this, InfomngmntPackage.REMOTE_CONTAINER__CHILDREN);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RemoteObject> getExclusionChildren() {
		if (exclusionChildren == null) {
			exclusionChildren = new EObjectResolvingEList<RemoteObject>(RemoteObject.class, this, InfomngmntPackage.REMOTE_CONTAINER__EXCLUSION_CHILDREN);
		}
		return exclusionChildren;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InfomngmntPackage.REMOTE_CONTAINER__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
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
			case InfomngmntPackage.REMOTE_CONTAINER__CHILDREN:
				return getChildren();
			case InfomngmntPackage.REMOTE_CONTAINER__EXCLUSION_CHILDREN:
				return getExclusionChildren();
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
			case InfomngmntPackage.REMOTE_CONTAINER__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends RemoteObject>)newValue);
				return;
			case InfomngmntPackage.REMOTE_CONTAINER__EXCLUSION_CHILDREN:
				getExclusionChildren().clear();
				getExclusionChildren().addAll((Collection<? extends RemoteObject>)newValue);
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
			case InfomngmntPackage.REMOTE_CONTAINER__CHILDREN:
				getChildren().clear();
				return;
			case InfomngmntPackage.REMOTE_CONTAINER__EXCLUSION_CHILDREN:
				getExclusionChildren().clear();
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
			case InfomngmntPackage.REMOTE_CONTAINER__CHILDREN:
				return children != null && !children.isEmpty();
			case InfomngmntPackage.REMOTE_CONTAINER__EXCLUSION_CHILDREN:
				return exclusionChildren != null && !exclusionChildren.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RemoteContainerImpl
