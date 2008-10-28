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
package org.remus.infomngmnt.search.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.Websearch;
import org.remus.infomngmnt.search.Websearches;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Websearches</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.search.impl.WebsearchesImpl#getWebsearch <em>Websearch</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WebsearchesImpl extends EObjectImpl implements Websearches {
	/**
	 * The cached value of the '{@link #getWebsearch() <em>Websearch</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWebsearch()
	 * @generated
	 * @ordered
	 */
	protected EList<Websearch> websearch;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WebsearchesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SearchPackage.Literals.WEBSEARCHES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Websearch> getWebsearch() {
		if (websearch == null) {
			websearch = new EObjectResolvingEList<Websearch>(Websearch.class, this, SearchPackage.WEBSEARCHES__WEBSEARCH);
		}
		return websearch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SearchPackage.WEBSEARCHES__WEBSEARCH:
				return getWebsearch();
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
			case SearchPackage.WEBSEARCHES__WEBSEARCH:
				getWebsearch().clear();
				getWebsearch().addAll((Collection<? extends Websearch>)newValue);
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
			case SearchPackage.WEBSEARCHES__WEBSEARCH:
				getWebsearch().clear();
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
			case SearchPackage.WEBSEARCHES__WEBSEARCH:
				return websearch != null && !websearch.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //WebsearchesImpl
