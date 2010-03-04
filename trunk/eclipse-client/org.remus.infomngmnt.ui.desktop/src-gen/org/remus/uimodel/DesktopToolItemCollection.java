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
package org.remus.uimodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Desktop Tool Item Collection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.uimodel.DesktopToolItemCollection#getItems <em>Items</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.uimodel.UimodelPackage#getDesktopToolItemCollection()
 * @model
 * @generated
 */
public interface DesktopToolItemCollection extends EObject {
	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.uimodel.DesktopToolItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see org.remus.uimodel.UimodelPackage#getDesktopToolItemCollection_Items()
	 * @model containment="true"
	 * @generated
	 */
	EList<DesktopToolItem> getItems();

} // DesktopToolItemCollection
