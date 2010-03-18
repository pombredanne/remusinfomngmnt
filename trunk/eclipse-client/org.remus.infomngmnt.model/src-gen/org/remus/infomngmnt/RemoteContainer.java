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
 * A representation of the model object '<em><b>Remote Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.RemoteContainer#getChildren <em>Children</em>}</li>
 *   <li>{@link org.remus.infomngmnt.RemoteContainer#getExclusionChildren <em>Exclusion Children</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getRemoteContainer()
 * @model
 * @generated
 */
public interface RemoteContainer extends RemoteObject {
	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.RemoteObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRemoteContainer_Children()
	 * @model containment="true" transient="true"
	 * @generated
	 */
	EList<RemoteObject> getChildren();

	/**
	 * Returns the value of the '<em><b>Exclusion Children</b></em>' reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.RemoteObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exclusion Children</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exclusion Children</em>' reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRemoteContainer_ExclusionChildren()
	 * @model transient="true"
	 * @generated
	 */
	EList<RemoteObject> getExclusionChildren();

} // RemoteContainer
