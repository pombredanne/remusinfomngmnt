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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Information Structure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.InformationStructure#getType <em>Type</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationStructure#getStructureItems <em>Structure Items</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationStructure#getReferencedStructureItems <em>Referenced Structure Items</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationStructure#isCanHaveBinaryReferences <em>Can Have Binary References</em>}</li>
 *   <li>{@link org.remus.infomngmnt.InformationStructure#getLabel <em>Label</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructure()
 * @model abstract="true"
 * @generated
 */
public interface InformationStructure extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.remus.infomngmnt.InformationStructureType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.remus.infomngmnt.InformationStructureType
	 * @see #setType(InformationStructureType)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructure_Type()
	 * @model
	 * @generated
	 */
	InformationStructureType getType();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationStructure#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.remus.infomngmnt.InformationStructureType
	 * @see #getType()
	 * @generated
	 */
	void setType(InformationStructureType value);

	/**
	 * Returns the value of the '<em><b>Structure Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.InformationStructureItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Structure Items</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structure Items</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructure_StructureItems()
	 * @model containment="true"
	 * @generated
	 */
	EList<InformationStructureItem> getStructureItems();

	/**
	 * Returns the value of the '<em><b>Referenced Structure Items</b></em>' reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.InformationStructureItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Referenced Structure Items</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Referenced Structure Items</em>' reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructure_ReferencedStructureItems()
	 * @model
	 * @generated
	 */
	EList<InformationStructureItem> getReferencedStructureItems();

	/**
	 * Returns the value of the '<em><b>Can Have Binary References</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Can Have Binary References</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Can Have Binary References</em>' attribute.
	 * @see #setCanHaveBinaryReferences(boolean)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructure_CanHaveBinaryReferences()
	 * @model
	 * @generated
	 */
	boolean isCanHaveBinaryReferences();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationStructure#isCanHaveBinaryReferences <em>Can Have Binary References</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Can Have Binary References</em>' attribute.
	 * @see #isCanHaveBinaryReferences()
	 * @generated
	 */
	void setCanHaveBinaryReferences(boolean value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructure_Label()
	 * @model unique="false"
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.InformationStructure#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

} // InformationStructure
