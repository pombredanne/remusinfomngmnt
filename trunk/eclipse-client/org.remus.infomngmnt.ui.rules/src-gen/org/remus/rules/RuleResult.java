/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.rules;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.ui.rules.transfer.TransferWrapper;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Result</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.rules.RuleResult#getValue <em>Value</em>}</li>
 *   <li>{@link org.remus.rules.RuleResult#getActions <em>Actions</em>}</li>
 *   <li>{@link org.remus.rules.RuleResult#getDescription <em>Description</em>}</li>
 *   <li>{@link org.remus.rules.RuleResult#getTransferType <em>Transfer Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.rules.RulesPackage#getRuleResult()
 * @model
 * @generated
 */
public interface RuleResult extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(Object)
	 * @see org.remus.rules.RulesPackage#getRuleResult_Value()
	 * @model required="true"
	 * @generated
	 */
	Object getValue();

	/**
	 * Sets the value of the '{@link org.remus.rules.RuleResult#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Object value);

	/**
	 * Returns the value of the '<em><b>Actions</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.rules.RuleAction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Actions</em>' containment reference list.
	 * @see org.remus.rules.RulesPackage#getRuleResult_Actions()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<RuleAction> getActions();

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.remus.rules.RulesPackage#getRuleResult_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.remus.rules.RuleResult#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Transfer Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transfer Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transfer Type</em>' attribute.
	 * @see #setTransferType(TransferWrapper)
	 * @see org.remus.rules.RulesPackage#getRuleResult_TransferType()
	 * @model dataType="org.remus.rules.TransferWrapper" transient="true"
	 * @generated
	 */
	TransferWrapper getTransferType();

	/**
	 * Sets the value of the '{@link org.remus.rules.RuleResult#getTransferType <em>Transfer Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transfer Type</em>' attribute.
	 * @see #getTransferType()
	 * @generated
	 */
	void setTransferType(TransferWrapper value);

} // RuleResult
