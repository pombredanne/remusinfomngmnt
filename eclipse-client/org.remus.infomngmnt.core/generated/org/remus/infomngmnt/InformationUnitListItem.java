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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Information Unit List Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.InformationUnitListItem#getWorkspacePath <em>Workspace Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnitListItem()
 * @model
 * @generated
 */
public interface InformationUnitListItem extends AbstractInformationUnit, SynchronizableObject {
	/**
	 * Returns the value of the '<em><b>Workspace Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Workspace Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Workspace Path</em>' attribute.
	 * @see #setWorkspacePath(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationUnitListItem_WorkspacePath()
	 * @model
	 * @generated
	 */
	String getWorkspacePath();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationUnitListItem#getWorkspacePath <em>Workspace Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Workspace Path</em>' attribute.
	 * @see #getWorkspacePath()
	 * @generated
	 */
	void setWorkspacePath(String value);

} // InformationUnitListItem
