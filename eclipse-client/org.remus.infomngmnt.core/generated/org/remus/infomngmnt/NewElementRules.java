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
 * A representation of the model object '<em><b>New Element Rules</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.NewElementRules#getTransferTypes <em>Transfer Types</em>}</li>
 *   <li>{@link org.remus.infomngmnt.NewElementRules#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.NewElementRules#isDeletable <em>Deletable</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getNewElementRules()
 * @model
 * @generated
 */
public interface NewElementRules extends EObject {
	/**
	 * Returns the value of the '<em><b>Transfer Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.RemusTransferType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transfer Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transfer Types</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNewElementRules_TransferTypes()
	 * @model containment="true"
	 * @generated
	 */
	EList<RemusTransferType> getTransferTypes();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNewElementRules_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.NewElementRules#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Deletable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deletable</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deletable</em>' attribute.
	 * @see #setDeletable(boolean)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getNewElementRules_Deletable()
	 * @model
	 * @generated
	 */
	boolean isDeletable();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.NewElementRules#isDeletable <em>Deletable</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deletable</em>' attribute.
	 * @see #isDeletable()
	 * @generated
	 */
	void setDeletable(boolean value);

} // NewElementRules
