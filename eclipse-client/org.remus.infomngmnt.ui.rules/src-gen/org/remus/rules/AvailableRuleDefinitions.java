/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.rules;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Available Rule Definitions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.rules.AvailableRuleDefinitions#getNewElementRules <em>New Element Rules</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.rules.RulesPackage#getAvailableRuleDefinitions()
 * @model
 * @generated
 */
public interface AvailableRuleDefinitions extends EObject {
	/**
	 * Returns the value of the '<em><b>New Element Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.rules.NewElementRules}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Element Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Element Rules</em>' containment reference list.
	 * @see org.remus.rules.RulesPackage#getAvailableRuleDefinitions_NewElementRules()
	 * @model containment="true"
	 * @generated
	 */
	EList<NewElementRules> getNewElementRules();

} // AvailableRuleDefinitions
