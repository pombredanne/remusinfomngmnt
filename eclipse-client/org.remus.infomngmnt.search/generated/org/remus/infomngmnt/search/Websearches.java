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
package org.remus.infomngmnt.search;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Websearches</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.search.Websearches#getWebsearch <em>Websearch</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.search.SearchPackage#getWebsearches()
 * @model
 * @generated
 */
public interface Websearches extends EObject {
	/**
	 * Returns the value of the '<em><b>Websearch</b></em>' reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.search.Websearch}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Websearch</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Websearch</em>' reference list.
	 * @see org.remus.infomngmnt.search.SearchPackage#getWebsearches_Websearch()
	 * @model
	 * @generated
	 */
	EList<Websearch> getWebsearch();

} // Websearches
