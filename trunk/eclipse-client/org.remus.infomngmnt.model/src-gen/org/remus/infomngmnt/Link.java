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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.Link#getLocalInformationUnit <em>Local Information Unit</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Link#getRemoteUrl <em>Remote Url</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Link#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getLink()
 * @model
 * @generated
 */
public interface Link extends EObject {
	/**
	 * Returns the value of the '<em><b>Local Information Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Information Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Information Unit</em>' attribute.
	 * @see #setLocalInformationUnit(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getLink_LocalInformationUnit()
	 * @model required="true"
	 * @generated
	 */
	String getLocalInformationUnit();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Link#getLocalInformationUnit <em>Local Information Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Information Unit</em>' attribute.
	 * @see #getLocalInformationUnit()
	 * @generated
	 */
	void setLocalInformationUnit(String value);

	/**
	 * Returns the value of the '<em><b>Remote Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote Url</em>' attribute.
	 * @see #setRemoteUrl(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getLink_RemoteUrl()
	 * @model
	 * @generated
	 */
	String getRemoteUrl();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Link#getRemoteUrl <em>Remote Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote Url</em>' attribute.
	 * @see #getRemoteUrl()
	 * @generated
	 */
	void setRemoteUrl(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getLink_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Link#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

} // Link
