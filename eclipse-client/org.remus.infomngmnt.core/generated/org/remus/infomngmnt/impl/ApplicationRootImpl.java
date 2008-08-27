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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Application Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.ApplicationRootImpl#getRootCategories <em>Root Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicationRootImpl extends EObjectImpl implements ApplicationRoot {
	/**
	 * The cached value of the '{@link #getRootCategories() <em>Root Categories</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<Category> rootCategories;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.APPLICATION_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Category> getRootCategories() {
		if (rootCategories == null) {
			rootCategories = new EObjectResolvingEList<Category>(Category.class, this, InfomngmntPackage.APPLICATION_ROOT__ROOT_CATEGORIES);
		}
		return rootCategories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.APPLICATION_ROOT__ROOT_CATEGORIES:
				return getRootCategories();
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
			case InfomngmntPackage.APPLICATION_ROOT__ROOT_CATEGORIES:
				getRootCategories().clear();
				getRootCategories().addAll((Collection<? extends Category>)newValue);
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
			case InfomngmntPackage.APPLICATION_ROOT__ROOT_CATEGORIES:
				getRootCategories().clear();
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
			case InfomngmntPackage.APPLICATION_ROOT__ROOT_CATEGORIES:
				return rootCategories != null && !rootCategories.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ApplicationRootImpl
