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
 *   <li>{@link org.remus.infomngmnt.Link#getTarget <em>Target</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Link#getLinktype <em>Linktype</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getLink()
 * @model
 * @generated
 */
public interface Link extends EObject {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(InformationUnit)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getLink_Target()
	 * @model required="true"
	 * @generated
	 */
	InformationUnit getTarget();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Link#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(InformationUnit value);

	/**
	 * Returns the value of the '<em><b>Linktype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Linktype</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Linktype</em>' reference.
	 * @see #setLinktype(LinkType)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getLink_Linktype()
	 * @model required="true"
	 * @generated
	 */
	LinkType getLinktype();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Link#getLinktype <em>Linktype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Linktype</em>' reference.
	 * @see #getLinktype()
	 * @generated
	 */
	void setLinktype(LinkType value);

} // Link
