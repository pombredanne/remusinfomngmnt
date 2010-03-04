/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.rules.util;

import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.remus.rules.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.remus.rules.RulesPackage
 * @generated
 */
public class RulesAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static RulesPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RulesAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = RulesPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RulesSwitch<Adapter> modelSwitch =
		new RulesSwitch<Adapter>() {
			@Override
			public Adapter caseV__________DesktopRuleEngine_________V(V__________DesktopRuleEngine_________V object) {
				return createV__________DesktopRuleEngine_________VAdapter();
			}
			@Override
			public Adapter caseStringToStringMap(Map.Entry<String, String> object) {
				return createStringToStringMapAdapter();
			}
			@Override
			public Adapter caseNewElementRules(NewElementRules object) {
				return createNewElementRulesAdapter();
			}
			@Override
			public Adapter caseRuleValue(RuleValue object) {
				return createRuleValueAdapter();
			}
			@Override
			public Adapter caseAvailableRuleDefinitions(AvailableRuleDefinitions object) {
				return createAvailableRuleDefinitionsAdapter();
			}
			@Override
			public Adapter caseRemusTransferType(RemusTransferType object) {
				return createRemusTransferTypeAdapter();
			}
			@Override
			public Adapter caseRuleAction(RuleAction object) {
				return createRuleActionAdapter();
			}
			@Override
			public Adapter caseRuleResult(RuleResult object) {
				return createRuleResultAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.remus.rules.V__________DesktopRuleEngine_________V <em>VDesktop Rule Engine V</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.rules.V__________DesktopRuleEngine_________V
	 * @generated
	 */
	public Adapter createV__________DesktopRuleEngine_________VAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To String Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToStringMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.rules.NewElementRules <em>New Element Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.rules.NewElementRules
	 * @generated
	 */
	public Adapter createNewElementRulesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.rules.RuleValue <em>Rule Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.rules.RuleValue
	 * @generated
	 */
	public Adapter createRuleValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.rules.AvailableRuleDefinitions <em>Available Rule Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.rules.AvailableRuleDefinitions
	 * @generated
	 */
	public Adapter createAvailableRuleDefinitionsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.rules.RemusTransferType <em>Remus Transfer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.rules.RemusTransferType
	 * @generated
	 */
	public Adapter createRemusTransferTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.rules.RuleAction <em>Rule Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.rules.RuleAction
	 * @generated
	 */
	public Adapter createRuleActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.rules.RuleResult <em>Rule Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.rules.RuleResult
	 * @generated
	 */
	public Adapter createRuleResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //RulesAdapterFactory
