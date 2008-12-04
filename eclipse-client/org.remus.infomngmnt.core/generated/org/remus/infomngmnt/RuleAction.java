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
 * A representation of the model object '<em><b>Rule Action</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.RuleAction#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.RuleAction#getInfoTypeId <em>Info Type Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.RuleAction#getRuleValue <em>Rule Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getRuleAction()
 * @model
 * @generated
 */
public interface RuleAction extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRuleAction_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.RuleAction#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Info Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Info Type Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info Type Id</em>' attribute.
	 * @see #setInfoTypeId(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRuleAction_InfoTypeId()
	 * @model required="true"
	 * @generated
	 */
	String getInfoTypeId();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.RuleAction#getInfoTypeId <em>Info Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Info Type Id</em>' attribute.
	 * @see #getInfoTypeId()
	 * @generated
	 */
	void setInfoTypeId(String value);

	/**
	 * Returns the value of the '<em><b>Rule Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Value</em>' containment reference.
	 * @see #setRuleValue(RuleValue)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRuleAction_RuleValue()
	 * @model containment="true"
	 * @generated
	 */
	RuleValue getRuleValue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.RuleAction#getRuleValue <em>Rule Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Value</em>' containment reference.
	 * @see #getRuleValue()
	 * @generated
	 */
	void setRuleValue(RuleValue value);

} // RuleAction
