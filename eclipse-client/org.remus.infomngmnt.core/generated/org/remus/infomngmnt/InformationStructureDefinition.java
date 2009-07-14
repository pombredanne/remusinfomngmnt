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
 * A representation of the model object '<em><b>Information Structure Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.InformationStructureDefinition#getStructurePool <em>Structure Pool</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructureDefinition()
 * @model
 * @generated
 */
public interface InformationStructureDefinition extends InformationStructure {

	/**
	 * Returns the value of the '<em><b>Structure Pool</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.InformationStructureItem}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Structure Pool</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structure Pool</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getInformationStructureDefinition_StructurePool()
	 * @model containment="true"
	 * @generated
	 */
	EList<InformationStructureItem> getStructurePool();

} // InformationStructureDefinition
