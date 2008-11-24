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
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.Rule#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Rule#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Rule#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Rule#getStringRepresentation <em>String Representation</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Rule#getRuleValue <em>Rule Value</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Rule#getChildren <em>Children</em>}</li>
 *   <li>{@link org.remus.infomngmnt.Rule#getAction <em>Action</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getRule()
 * @model
 * @generated
 */
public interface Rule extends EObject {
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
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRule_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Rule#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRule_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Rule#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activated</em>' attribute.
	 * @see #setActivated(boolean)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRule_Activated()
	 * @model
	 * @generated
	 */
	boolean isActivated();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Rule#isActivated <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activated</em>' attribute.
	 * @see #isActivated()
	 * @generated
	 */
	void setActivated(boolean value);

	/**
	 * Returns the value of the '<em><b>String Representation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String Representation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Representation</em>' attribute.
	 * @see #setStringRepresentation(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRule_StringRepresentation()
	 * @model required="true"
	 * @generated
	 */
	String getStringRepresentation();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Rule#getStringRepresentation <em>String Representation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Representation</em>' attribute.
	 * @see #getStringRepresentation()
	 * @generated
	 */
	void setStringRepresentation(String value);

	/**
	 * Returns the value of the '<em><b>Rule Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Value</em>' reference.
	 * @see #setRuleValue(RuleValue)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRule_RuleValue()
	 * @model
	 * @generated
	 */
	RuleValue getRuleValue();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Rule#getRuleValue <em>Rule Value</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Value</em>' reference.
	 * @see #getRuleValue()
	 * @generated
	 */
	void setRuleValue(RuleValue value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Children</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRule_Children()
	 * @model containment="true"
	 * @generated
	 */
	EList<Rule> getChildren();

	/**
	 * Returns the value of the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' reference.
	 * @see #setAction(RuleAction)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getRule_Action()
	 * @model
	 * @generated
	 */
	RuleAction getAction();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.Rule#getAction <em>Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(RuleAction value);

} // Rule
