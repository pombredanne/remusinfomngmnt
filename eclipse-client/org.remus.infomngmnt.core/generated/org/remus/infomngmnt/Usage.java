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

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Usage</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.Usage#getLastAccess <em>Last Access</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Usage#getAccessCount <em>Access Count</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getUsage()
 * @model
 * @generated
 */
public interface Usage extends EObject {
	/**
	 * Returns the value of the '<em><b>Last Access</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Access</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Access</em>' attribute.
	 * @see #setLastAccess(Date)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getUsage_LastAccess()
	 * @model
	 * @generated
	 */
	Date getLastAccess();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Usage#getLastAccess <em>Last Access</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Access</em>' attribute.
	 * @see #getLastAccess()
	 * @generated
	 */
	void setLastAccess(Date value);

	/**
	 * Returns the value of the '<em><b>Access Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Access Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Access Count</em>' attribute.
	 * @see #setAccessCount(int)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getUsage_AccessCount()
	 * @model
	 * @generated
	 */
	int getAccessCount();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Usage#getAccessCount <em>Access Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Access Count</em>' attribute.
	 * @see #getAccessCount()
	 * @generated
	 */
	void setAccessCount(int value);

} // Usage
