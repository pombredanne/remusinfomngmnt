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
 * A representation of the model object '<em><b>Synchronizable Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.SynchronizableObject#getSynchronizationMetaData <em>Synchronization Meta Data</em>}</li>
 *   <li>{@link org.remus.infomngmnt.SynchronizableObject#getMarkedAsDeleteItems <em>Marked As Delete Items</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizableObject()
 * @model abstract="true"
 * @generated
 */
public interface SynchronizableObject extends Adapter {
	/**
	 * Returns the value of the '<em><b>Synchronization Meta Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Synchronization Meta Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Synchronization Meta Data</em>' containment reference.
	 * @see #setSynchronizationMetaData(SynchronizationMetadata)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizableObject_SynchronizationMetaData()
	 * @model containment="true"
	 * @generated
	 */
	SynchronizationMetadata getSynchronizationMetaData();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.SynchronizableObject#getSynchronizationMetaData <em>Synchronization Meta Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Synchronization Meta Data</em>' containment reference.
	 * @see #getSynchronizationMetaData()
	 * @generated
	 */
	void setSynchronizationMetaData(SynchronizationMetadata value);

	/**
	 * Returns the value of the '<em><b>Marked As Delete Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.SynchronizableObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Marked As Delete Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Marked As Delete Items</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizableObject_MarkedAsDeleteItems()
	 * @model containment="true"
	 * @generated
	 */
	EList<SynchronizableObject> getMarkedAsDeleteItems();

} // SynchronizableObject
