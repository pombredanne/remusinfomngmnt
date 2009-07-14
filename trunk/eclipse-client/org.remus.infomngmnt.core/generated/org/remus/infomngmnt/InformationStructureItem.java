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
 * A representation of the model object '<em><b>Information Structure Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.InformationStructureItem#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationStructureItem#isCreateAlways <em>Create Always</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructureItem()
 * @model
 * @generated
 */
public interface InformationStructureItem extends InformationStructure {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructureItem_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationStructureItem#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Create Always</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Create Always</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Create Always</em>' attribute.
	 * @see #setCreateAlways(boolean)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructureItem_CreateAlways()
	 * @model
	 * @generated
	 */
	boolean isCreateAlways();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationStructureItem#isCreateAlways <em>Create Always</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Create Always</em>' attribute.
	 * @see #isCreateAlways()
	 * @generated
	 */
	void setCreateAlways(boolean value);

} // InformationStructureItem
