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

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Change Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.ChangeSet#getTargetCategory <em>Target Category</em>}</li>
 *   <li>{@link org.remus.infomngmnt.ChangeSet#getChangeSetItems <em>Change Set Items</em>}</li>
 *   <li>{@link org.remus.infomngmnt.ChangeSet#getRepository <em>Repository</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSet()
 * @model
 * @generated
 */
public interface ChangeSet extends Adapter {
	/**
	 * Returns the value of the '<em><b>Target Category</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Category</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Category</em>' reference.
	 * @see #setTargetCategory(Category)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSet_TargetCategory()
	 * @model required="true"
	 * @generated
	 */
	Category getTargetCategory();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.ChangeSet#getTargetCategory <em>Target Category</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Category</em>' reference.
	 * @see #getTargetCategory()
	 * @generated
	 */
	void setTargetCategory(Category value);

	/**
	 * Returns the value of the '<em><b>Change Set Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.ChangeSetItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Set Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change Set Items</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSet_ChangeSetItems()
	 * @model containment="true"
	 * @generated
	 */
	EList<ChangeSetItem> getChangeSetItems();

	/**
	 * Returns the value of the '<em><b>Repository</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository</em>' reference.
	 * @see #setRepository(RemoteRepository)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSet_Repository()
	 * @model required="true"
	 * @generated
	 */
	RemoteRepository getRepository();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.ChangeSet#getRepository <em>Repository</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository</em>' reference.
	 * @see #getRepository()
	 * @generated
	 */
	void setRepository(RemoteRepository value);

} // ChangeSet
