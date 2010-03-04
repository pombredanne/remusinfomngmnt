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
package org.remus.infomngmnt;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.ApplicationRoot#getRootCategories <em>Root Categories</em>}</li>
 *   <li>{@link org.remus.infomngmnt.ApplicationRoot#getAvailableTags <em>Available Tags</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getApplicationRoot()
 * @model
 * @generated
 */
public interface ApplicationRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Root Categories</b></em>' reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Categories</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Categories</em>' reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getApplicationRoot_RootCategories()
	 * @model
	 * @generated
	 */
	EList<Category> getRootCategories();

	/**
	 * Returns the value of the '<em><b>Available Tags</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Available Tags</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Available Tags</em>' reference.
	 * @see #setAvailableTags(AvailableTags)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getApplicationRoot_AvailableTags()
	 * @model required="true"
	 * @generated
	 */
	AvailableTags getAvailableTags();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.ApplicationRoot#getAvailableTags <em>Available Tags</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Available Tags</em>' reference.
	 * @see #getAvailableTags()
	 * @generated
	 */
	void setAvailableTags(AvailableTags value);

} // ApplicationRoot
