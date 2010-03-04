/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.rules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.remus.rules.RulesPackage
 * @generated
 */
public interface RulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulesFactory eINSTANCE = org.remus.rules.impl.RulesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>New Element Rules</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>New Element Rules</em>'.
	 * @generated
	 */
	NewElementRules createNewElementRules();

	/**
	 * Returns a new object of class '<em>Rule Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Value</em>'.
	 * @generated
	 */
	RuleValue createRuleValue();

	/**
	 * Returns a new object of class '<em>Available Rule Definitions</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Available Rule Definitions</em>'.
	 * @generated
	 */
	AvailableRuleDefinitions createAvailableRuleDefinitions();

	/**
	 * Returns a new object of class '<em>Remus Transfer Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remus Transfer Type</em>'.
	 * @generated
	 */
	RemusTransferType createRemusTransferType();

	/**
	 * Returns a new object of class '<em>Rule Action</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Action</em>'.
	 * @generated
	 */
	RuleAction createRuleAction();

	/**
	 * Returns a new object of class '<em>Rule Result</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Result</em>'.
	 * @generated
	 */
	RuleResult createRuleResult();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	RulesPackage getRulesPackage();

} //RulesFactory
