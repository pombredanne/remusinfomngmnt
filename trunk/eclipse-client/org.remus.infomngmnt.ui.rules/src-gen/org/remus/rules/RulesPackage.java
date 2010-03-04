/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.remus.rules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.remus.rules.RulesFactory
 * @model kind="package"
 * @generated
 */
public interface RulesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "rules";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://remus-software.org/rules/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rules";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RulesPackage eINSTANCE = org.remus.rules.impl.RulesPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.remus.rules.V__________DesktopRuleEngine_________V <em>VDesktop Rule Engine V</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.rules.V__________DesktopRuleEngine_________V
	 * @see org.remus.rules.impl.RulesPackageImpl#getV__________DesktopRuleEngine_________V()
	 * @generated
	 */
	int VDESKTOP_RULE_ENGINE_V = 0;

	/**
	 * The number of structural features of the '<em>VDesktop Rule Engine V</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VDESKTOP_RULE_ENGINE_V_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.rules.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.rules.impl.StringToStringMapImpl
	 * @see org.remus.rules.impl.RulesPackageImpl#getStringToStringMap()
	 * @generated
	 */
	int STRING_TO_STRING_MAP = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>String To String Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_TO_STRING_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.remus.rules.impl.NewElementRulesImpl <em>New Element Rules</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.rules.impl.NewElementRulesImpl
	 * @see org.remus.rules.impl.RulesPackageImpl#getNewElementRules()
	 * @generated
	 */
	int NEW_ELEMENT_RULES = 2;

	/**
	 * The feature id for the '<em><b>Transfer Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ELEMENT_RULES__TRANSFER_TYPES = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ELEMENT_RULES__NAME = 1;

	/**
	 * The feature id for the '<em><b>Deletable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ELEMENT_RULES__DELETABLE = 2;

	/**
	 * The number of structural features of the '<em>New Element Rules</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEW_ELEMENT_RULES_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.remus.rules.impl.RuleValueImpl <em>Rule Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.rules.impl.RuleValueImpl
	 * @see org.remus.rules.impl.RulesPackageImpl#getRuleValue()
	 * @generated
	 */
	int RULE_VALUE = 3;

	/**
	 * The number of structural features of the '<em>Rule Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_VALUE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.remus.rules.impl.AvailableRuleDefinitionsImpl <em>Available Rule Definitions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.rules.impl.AvailableRuleDefinitionsImpl
	 * @see org.remus.rules.impl.RulesPackageImpl#getAvailableRuleDefinitions()
	 * @generated
	 */
	int AVAILABLE_RULE_DEFINITIONS = 4;

	/**
	 * The feature id for the '<em><b>New Element Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_RULE_DEFINITIONS__NEW_ELEMENT_RULES = 0;

	/**
	 * The number of structural features of the '<em>Available Rule Definitions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AVAILABLE_RULE_DEFINITIONS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.rules.impl.RemusTransferTypeImpl <em>Remus Transfer Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.rules.impl.RemusTransferTypeImpl
	 * @see org.remus.rules.impl.RulesPackageImpl#getRemusTransferType()
	 * @generated
	 */
	int REMUS_TRANSFER_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMUS_TRANSFER_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMUS_TRANSFER_TYPE__ID = 1;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMUS_TRANSFER_TYPE__ACTIONS = 2;

	/**
	 * The number of structural features of the '<em>Remus Transfer Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REMUS_TRANSFER_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.remus.rules.impl.RuleActionImpl <em>Rule Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.rules.impl.RuleActionImpl
	 * @see org.remus.rules.impl.RulesPackageImpl#getRuleAction()
	 * @generated
	 */
	int RULE_ACTION = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Info Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__INFO_TYPE_ID = 1;

	/**
	 * The feature id for the '<em><b>Rule Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__RULE_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Groovy Matcher</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__GROOVY_MATCHER = 3;

	/**
	 * The feature id for the '<em><b>Post Processing Instructions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION__POST_PROCESSING_INSTRUCTIONS = 4;

	/**
	 * The number of structural features of the '<em>Rule Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ACTION_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.remus.rules.impl.RuleResultImpl <em>Rule Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.rules.impl.RuleResultImpl
	 * @see org.remus.rules.impl.RulesPackageImpl#getRuleResult()
	 * @generated
	 */
	int RULE_RESULT = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT__ACTIONS = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT__DESCRIPTION = 2;

	/**
	 * The feature id for the '<em><b>Transfer Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT__TRANSFER_TYPE = 3;

	/**
	 * The number of structural features of the '<em>Rule Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_RESULT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '<em>Transfer Wrapper</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.ui.rules.transfer.TransferWrapper
	 * @see org.remus.rules.impl.RulesPackageImpl#getTransferWrapper()
	 * @generated
	 */
	int TRANSFER_WRAPPER = 8;


	/**
	 * Returns the meta object for class '{@link org.remus.rules.V__________DesktopRuleEngine_________V <em>VDesktop Rule Engine V</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VDesktop Rule Engine V</em>'.
	 * @see org.remus.rules.V__________DesktopRuleEngine_________V
	 * @generated
	 */
	EClass getV__________DesktopRuleEngine_________V();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>String To String Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String To String Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getStringToStringMap();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToStringMap()
	 * @generated
	 */
	EAttribute getStringToStringMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getStringToStringMap()
	 * @generated
	 */
	EAttribute getStringToStringMap_Value();

	/**
	 * Returns the meta object for class '{@link org.remus.rules.NewElementRules <em>New Element Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>New Element Rules</em>'.
	 * @see org.remus.rules.NewElementRules
	 * @generated
	 */
	EClass getNewElementRules();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.rules.NewElementRules#getTransferTypes <em>Transfer Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transfer Types</em>'.
	 * @see org.remus.rules.NewElementRules#getTransferTypes()
	 * @see #getNewElementRules()
	 * @generated
	 */
	EReference getNewElementRules_TransferTypes();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.NewElementRules#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.rules.NewElementRules#getName()
	 * @see #getNewElementRules()
	 * @generated
	 */
	EAttribute getNewElementRules_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.NewElementRules#isDeletable <em>Deletable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deletable</em>'.
	 * @see org.remus.rules.NewElementRules#isDeletable()
	 * @see #getNewElementRules()
	 * @generated
	 */
	EAttribute getNewElementRules_Deletable();

	/**
	 * Returns the meta object for class '{@link org.remus.rules.RuleValue <em>Rule Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Value</em>'.
	 * @see org.remus.rules.RuleValue
	 * @generated
	 */
	EClass getRuleValue();

	/**
	 * Returns the meta object for class '{@link org.remus.rules.AvailableRuleDefinitions <em>Available Rule Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Available Rule Definitions</em>'.
	 * @see org.remus.rules.AvailableRuleDefinitions
	 * @generated
	 */
	EClass getAvailableRuleDefinitions();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.rules.AvailableRuleDefinitions#getNewElementRules <em>New Element Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>New Element Rules</em>'.
	 * @see org.remus.rules.AvailableRuleDefinitions#getNewElementRules()
	 * @see #getAvailableRuleDefinitions()
	 * @generated
	 */
	EReference getAvailableRuleDefinitions_NewElementRules();

	/**
	 * Returns the meta object for class '{@link org.remus.rules.RemusTransferType <em>Remus Transfer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Remus Transfer Type</em>'.
	 * @see org.remus.rules.RemusTransferType
	 * @generated
	 */
	EClass getRemusTransferType();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RemusTransferType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.rules.RemusTransferType#getName()
	 * @see #getRemusTransferType()
	 * @generated
	 */
	EAttribute getRemusTransferType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RemusTransferType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.rules.RemusTransferType#getId()
	 * @see #getRemusTransferType()
	 * @generated
	 */
	EAttribute getRemusTransferType_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.rules.RemusTransferType#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.remus.rules.RemusTransferType#getActions()
	 * @see #getRemusTransferType()
	 * @generated
	 */
	EReference getRemusTransferType_Actions();

	/**
	 * Returns the meta object for class '{@link org.remus.rules.RuleAction <em>Rule Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Action</em>'.
	 * @see org.remus.rules.RuleAction
	 * @generated
	 */
	EClass getRuleAction();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RuleAction#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.remus.rules.RuleAction#getName()
	 * @see #getRuleAction()
	 * @generated
	 */
	EAttribute getRuleAction_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RuleAction#getInfoTypeId <em>Info Type Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Info Type Id</em>'.
	 * @see org.remus.rules.RuleAction#getInfoTypeId()
	 * @see #getRuleAction()
	 * @generated
	 */
	EAttribute getRuleAction_InfoTypeId();

	/**
	 * Returns the meta object for the containment reference '{@link org.remus.rules.RuleAction#getRuleValue <em>Rule Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule Value</em>'.
	 * @see org.remus.rules.RuleAction#getRuleValue()
	 * @see #getRuleAction()
	 * @generated
	 */
	EReference getRuleAction_RuleValue();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RuleAction#getGroovyMatcher <em>Groovy Matcher</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Groovy Matcher</em>'.
	 * @see org.remus.rules.RuleAction#getGroovyMatcher()
	 * @see #getRuleAction()
	 * @generated
	 */
	EAttribute getRuleAction_GroovyMatcher();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RuleAction#getPostProcessingInstructions <em>Post Processing Instructions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Post Processing Instructions</em>'.
	 * @see org.remus.rules.RuleAction#getPostProcessingInstructions()
	 * @see #getRuleAction()
	 * @generated
	 */
	EAttribute getRuleAction_PostProcessingInstructions();

	/**
	 * Returns the meta object for class '{@link org.remus.rules.RuleResult <em>Rule Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Result</em>'.
	 * @see org.remus.rules.RuleResult
	 * @generated
	 */
	EClass getRuleResult();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RuleResult#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.remus.rules.RuleResult#getValue()
	 * @see #getRuleResult()
	 * @generated
	 */
	EAttribute getRuleResult_Value();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.rules.RuleResult#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see org.remus.rules.RuleResult#getActions()
	 * @see #getRuleResult()
	 * @generated
	 */
	EReference getRuleResult_Actions();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RuleResult#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.remus.rules.RuleResult#getDescription()
	 * @see #getRuleResult()
	 * @generated
	 */
	EAttribute getRuleResult_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.rules.RuleResult#getTransferType <em>Transfer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transfer Type</em>'.
	 * @see org.remus.rules.RuleResult#getTransferType()
	 * @see #getRuleResult()
	 * @generated
	 */
	EAttribute getRuleResult_TransferType();

	/**
	 * Returns the meta object for data type '{@link org.remus.infomngmnt.ui.rules.transfer.TransferWrapper <em>Transfer Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Transfer Wrapper</em>'.
	 * @see org.remus.infomngmnt.ui.rules.transfer.TransferWrapper
	 * @model instanceClass="org.remus.infomngmnt.ui.rules.transfer.TransferWrapper" serializeable="false"
	 * @generated
	 */
	EDataType getTransferWrapper();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RulesFactory getRulesFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.remus.rules.V__________DesktopRuleEngine_________V <em>VDesktop Rule Engine V</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.rules.V__________DesktopRuleEngine_________V
		 * @see org.remus.rules.impl.RulesPackageImpl#getV__________DesktopRuleEngine_________V()
		 * @generated
		 */
		EClass VDESKTOP_RULE_ENGINE_V = eINSTANCE.getV__________DesktopRuleEngine_________V();

		/**
		 * The meta object literal for the '{@link org.remus.rules.impl.StringToStringMapImpl <em>String To String Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.rules.impl.StringToStringMapImpl
		 * @see org.remus.rules.impl.RulesPackageImpl#getStringToStringMap()
		 * @generated
		 */
		EClass STRING_TO_STRING_MAP = eINSTANCE.getStringToStringMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_STRING_MAP__KEY = eINSTANCE.getStringToStringMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_TO_STRING_MAP__VALUE = eINSTANCE.getStringToStringMap_Value();

		/**
		 * The meta object literal for the '{@link org.remus.rules.impl.NewElementRulesImpl <em>New Element Rules</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.rules.impl.NewElementRulesImpl
		 * @see org.remus.rules.impl.RulesPackageImpl#getNewElementRules()
		 * @generated
		 */
		EClass NEW_ELEMENT_RULES = eINSTANCE.getNewElementRules();

		/**
		 * The meta object literal for the '<em><b>Transfer Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NEW_ELEMENT_RULES__TRANSFER_TYPES = eINSTANCE.getNewElementRules_TransferTypes();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEW_ELEMENT_RULES__NAME = eINSTANCE.getNewElementRules_Name();

		/**
		 * The meta object literal for the '<em><b>Deletable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEW_ELEMENT_RULES__DELETABLE = eINSTANCE.getNewElementRules_Deletable();

		/**
		 * The meta object literal for the '{@link org.remus.rules.impl.RuleValueImpl <em>Rule Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.rules.impl.RuleValueImpl
		 * @see org.remus.rules.impl.RulesPackageImpl#getRuleValue()
		 * @generated
		 */
		EClass RULE_VALUE = eINSTANCE.getRuleValue();

		/**
		 * The meta object literal for the '{@link org.remus.rules.impl.AvailableRuleDefinitionsImpl <em>Available Rule Definitions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.rules.impl.AvailableRuleDefinitionsImpl
		 * @see org.remus.rules.impl.RulesPackageImpl#getAvailableRuleDefinitions()
		 * @generated
		 */
		EClass AVAILABLE_RULE_DEFINITIONS = eINSTANCE.getAvailableRuleDefinitions();

		/**
		 * The meta object literal for the '<em><b>New Element Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AVAILABLE_RULE_DEFINITIONS__NEW_ELEMENT_RULES = eINSTANCE.getAvailableRuleDefinitions_NewElementRules();

		/**
		 * The meta object literal for the '{@link org.remus.rules.impl.RemusTransferTypeImpl <em>Remus Transfer Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.rules.impl.RemusTransferTypeImpl
		 * @see org.remus.rules.impl.RulesPackageImpl#getRemusTransferType()
		 * @generated
		 */
		EClass REMUS_TRANSFER_TYPE = eINSTANCE.getRemusTransferType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMUS_TRANSFER_TYPE__NAME = eINSTANCE.getRemusTransferType_Name();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REMUS_TRANSFER_TYPE__ID = eINSTANCE.getRemusTransferType_Id();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REMUS_TRANSFER_TYPE__ACTIONS = eINSTANCE.getRemusTransferType_Actions();

		/**
		 * The meta object literal for the '{@link org.remus.rules.impl.RuleActionImpl <em>Rule Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.rules.impl.RuleActionImpl
		 * @see org.remus.rules.impl.RulesPackageImpl#getRuleAction()
		 * @generated
		 */
		EClass RULE_ACTION = eINSTANCE.getRuleAction();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ACTION__NAME = eINSTANCE.getRuleAction_Name();

		/**
		 * The meta object literal for the '<em><b>Info Type Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ACTION__INFO_TYPE_ID = eINSTANCE.getRuleAction_InfoTypeId();

		/**
		 * The meta object literal for the '<em><b>Rule Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_ACTION__RULE_VALUE = eINSTANCE.getRuleAction_RuleValue();

		/**
		 * The meta object literal for the '<em><b>Groovy Matcher</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ACTION__GROOVY_MATCHER = eINSTANCE.getRuleAction_GroovyMatcher();

		/**
		 * The meta object literal for the '<em><b>Post Processing Instructions</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ACTION__POST_PROCESSING_INSTRUCTIONS = eINSTANCE.getRuleAction_PostProcessingInstructions();

		/**
		 * The meta object literal for the '{@link org.remus.rules.impl.RuleResultImpl <em>Rule Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.rules.impl.RuleResultImpl
		 * @see org.remus.rules.impl.RulesPackageImpl#getRuleResult()
		 * @generated
		 */
		EClass RULE_RESULT = eINSTANCE.getRuleResult();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_RESULT__VALUE = eINSTANCE.getRuleResult_Value();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_RESULT__ACTIONS = eINSTANCE.getRuleResult_Actions();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_RESULT__DESCRIPTION = eINSTANCE.getRuleResult_Description();

		/**
		 * The meta object literal for the '<em><b>Transfer Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_RESULT__TRANSFER_TYPE = eINSTANCE.getRuleResult_TransferType();

		/**
		 * The meta object literal for the '<em>Transfer Wrapper</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.ui.rules.transfer.TransferWrapper
		 * @see org.remus.rules.impl.RulesPackageImpl#getTransferWrapper()
		 * @generated
		 */
		EDataType TRANSFER_WRAPPER = eINSTANCE.getTransferWrapper();

	}

} //RulesPackage
