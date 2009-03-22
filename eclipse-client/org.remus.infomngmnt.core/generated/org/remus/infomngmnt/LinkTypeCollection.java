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

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link Type Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.LinkTypeCollection#getAvailableLinkTypes <em>Available Link Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getLinkTypeCollection()
 * @model
 * @generated
 */
public interface LinkTypeCollection extends EObject {
	/**
	 * Returns the value of the '<em><b>Available Link Types</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link org.remus.infomngmnt.LinkType},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Available Link Types</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Available Link Types</em>' map.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getLinkTypeCollection_AvailableLinkTypes()
	 * @model mapType="org.remus.infomngmnt.StringToLinkTypeMap<org.eclipse.emf.ecore.EString, org.remus.infomngmnt.LinkType>"
	 * @generated
	 */
	EMap<String, LinkType> getAvailableLinkTypes();

} // LinkTypeCollection
