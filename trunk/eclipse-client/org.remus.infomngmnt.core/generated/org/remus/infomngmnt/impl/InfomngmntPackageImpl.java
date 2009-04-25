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
package org.remus.infomngmnt.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Adapter;
import org.remus.infomngmnt.Annotation;
import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.AvailableRuleDefinitions;
import org.remus.infomngmnt.AvailableTags;
import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.CalendarEntry;
import org.remus.infomngmnt.CalendarEntryType;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.RecentlyUsedKeywords;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RemusTransferType;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.RuleResult;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.Tag;
import org.remus.infomngmnt.Usage;
import org.remus.infomngmnt.core.remote.IRepository;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class InfomngmntPackageImpl extends EPackageImpl implements InfomngmntPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass informationUnitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binaryReferenceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass usageEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass categoryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractInformationUnitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass informationUnitListItemEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationRootEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass annotationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass linkEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass adapterEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass recentlyUsedKeywordsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass newElementRulesEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass availableRuleDefinitionsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass remusTransferTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleActionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass remoteRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass remoteObjectEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass remoteContainerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass repositoryCollectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass synchronizationMetadataEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeSetEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass changeSetItemEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass categoryToSynchronizationActionMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass informationUnitListItemToInformationUnitMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass synchronizableObjectToSynchronizationActionMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tagEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass availableTagsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EClass synchronizableObjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass calendarEntryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum synchronizationStateEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum synchronizationActionEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum calendarEntryTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType objectEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType iRepositoryEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.remus.infomngmnt.InfomngmntPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private InfomngmntPackageImpl() {
		super(eNS_URI, InfomngmntFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static InfomngmntPackage init() {
		if (isInited) return (InfomngmntPackage)EPackage.Registry.INSTANCE.getEPackage(InfomngmntPackage.eNS_URI);

		// Obtain or create and register package
		InfomngmntPackageImpl theInfomngmntPackage = (InfomngmntPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof InfomngmntPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new InfomngmntPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theInfomngmntPackage.createPackageContents();

		// Initialize created meta-data
		theInfomngmntPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInfomngmntPackage.freeze();

		return theInfomngmntPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInformationUnit() {
		return informationUnitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnit_StringValue() {
		return (EAttribute)informationUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnit_LongValue() {
		return (EAttribute)informationUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnit_BoolValue() {
		return (EAttribute)informationUnitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnit_BinaryValue() {
		return (EAttribute)informationUnitEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnit_DateValue() {
		return (EAttribute)informationUnitEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationUnit_ChildValues() {
		return (EReference)informationUnitEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationUnit_References() {
		return (EReference)informationUnitEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationUnit_Links() {
		return (EReference)informationUnitEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnit_CreationDate() {
		return (EAttribute)informationUnitEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationUnit_UsageData() {
		return (EReference)informationUnitEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnit_Description() {
		return (EAttribute)informationUnitEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnit_Keywords() {
		return (EAttribute)informationUnitEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationUnit_CalendarEntry() {
		return (EReference)informationUnitEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationUnit_BinaryReferences() {
		return (EReference)informationUnitEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinaryReference() {
		return binaryReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryReference_Id() {
		return (EAttribute)binaryReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryReference_ProjectRelativePath() {
		return (EAttribute)binaryReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinaryReference_Dirty() {
		return (EAttribute)binaryReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUsage() {
		return usageEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUsage_LastAccess() {
		return (EAttribute)usageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUsage_AccessCount() {
		return (EAttribute)usageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCategory() {
		return categoryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategory_Id() {
		return (EAttribute)categoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategory_Label() {
		return (EAttribute)categoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCategory_Children() {
		return (EReference)categoryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCategory_InformationUnit() {
		return (EReference)categoryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategory_Description() {
		return (EAttribute)categoryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAbstractInformationUnit() {
		return abstractInformationUnitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractInformationUnit_Id() {
		return (EAttribute)abstractInformationUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractInformationUnit_Label() {
		return (EAttribute)abstractInformationUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAbstractInformationUnit_Type() {
		return (EAttribute)abstractInformationUnitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInformationUnitListItem() {
		return informationUnitListItemEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInformationUnitListItem_WorkspacePath() {
		return (EAttribute)informationUnitListItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationRoot() {
		return applicationRootEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationRoot_RootCategories() {
		return (EReference)applicationRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationRoot_AvailableTags() {
		return (EReference)applicationRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAnnotation() {
		return annotationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAnnotation_Description() {
		return (EAttribute)annotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLink() {
		return linkEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLink_Target() {
		return (EReference)linkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdapter() {
		return adapterEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRecentlyUsedKeywords() {
		return recentlyUsedKeywordsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRecentlyUsedKeywords_Maxlength() {
		return (EAttribute)recentlyUsedKeywordsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRecentlyUsedKeywords_Keywords() {
		return (EAttribute)recentlyUsedKeywordsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNewElementRules() {
		return newElementRulesEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNewElementRules_TransferTypes() {
		return (EReference)newElementRulesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNewElementRules_Name() {
		return (EAttribute)newElementRulesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNewElementRules_Deletable() {
		return (EAttribute)newElementRulesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleValue() {
		return ruleValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAvailableRuleDefinitions() {
		return availableRuleDefinitionsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAvailableRuleDefinitions_NewElementRules() {
		return (EReference)availableRuleDefinitionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemusTransferType() {
		return remusTransferTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemusTransferType_Name() {
		return (EAttribute)remusTransferTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemusTransferType_Id() {
		return (EAttribute)remusTransferTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemusTransferType_Actions() {
		return (EReference)remusTransferTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleAction() {
		return ruleActionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleAction_Name() {
		return (EAttribute)ruleActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleAction_InfoTypeId() {
		return (EAttribute)ruleActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleAction_RuleValue() {
		return (EReference)ruleActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleResult() {
		return ruleResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleResult_Value() {
		return (EAttribute)ruleResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleResult_Actions() {
		return (EReference)ruleResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleResult_Description() {
		return (EAttribute)ruleResultEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoteRepository() {
		return remoteRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoteObject() {
		return remoteObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoteObject_PossibleInfoTypeId() {
		return (EAttribute)remoteObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoteObject_Id() {
		return (EAttribute)remoteObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoteObject_Url() {
		return (EAttribute)remoteObjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoteObject_Name() {
		return (EAttribute)remoteObjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoteObject_RepositoryTypeId() {
		return (EAttribute)remoteObjectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoteObject_RepositoryTypeObjectId() {
		return (EAttribute)remoteObjectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoteObject_WrappedObject() {
		return (EAttribute)remoteObjectEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRemoteObject_Hash() {
		return (EAttribute)remoteObjectEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRemoteContainer() {
		return remoteContainerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoteContainer_Children() {
		return (EReference)remoteContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRemoteContainer_ExclusionChildren() {
		return (EReference)remoteContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRepositoryCollection() {
		return repositoryCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRepositoryCollection_Repositories() {
		return (EReference)repositoryCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSynchronizationMetadata() {
		return synchronizationMetadataEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_RepositoryId() {
		return (EAttribute)synchronizationMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_Url() {
		return (EAttribute)synchronizationMetadataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_Readonly() {
		return (EAttribute)synchronizationMetadataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_LastSynchronisation() {
		return (EAttribute)synchronizationMetadataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_Hash() {
		return (EAttribute)synchronizationMetadataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_SyncState() {
		return (EAttribute)synchronizationMetadataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChangeSet() {
		return changeSetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSet_TargetCategory() {
		return (EReference)changeSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSet_ChangeSetItems() {
		return (EReference)changeSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSet_Repository() {
		return (EReference)changeSetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getChangeSetItem() {
		return changeSetItemEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSetItem_RemoteConvertedContainer() {
		return (EReference)changeSetItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSetItem_RemoteOriginalObject() {
		return (EReference)changeSetItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSetItem_LocalContainer() {
		return (EReference)changeSetItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSetItem_SyncCategoryActionMap() {
		return (EReference)changeSetItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSetItem_SyncObjectActionMap() {
		return (EReference)changeSetItemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getChangeSetItem_RemoteFullObjectMap() {
		return (EReference)changeSetItemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCategoryToSynchronizationActionMap() {
		return categoryToSynchronizationActionMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCategoryToSynchronizationActionMap_Key() {
		return (EReference)categoryToSynchronizationActionMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCategoryToSynchronizationActionMap_Value() {
		return (EAttribute)categoryToSynchronizationActionMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInformationUnitListItemToInformationUnitMap() {
		return informationUnitListItemToInformationUnitMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationUnitListItemToInformationUnitMap_Key() {
		return (EReference)informationUnitListItemToInformationUnitMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInformationUnitListItemToInformationUnitMap_Value() {
		return (EReference)informationUnitListItemToInformationUnitMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSynchronizableObjectToSynchronizationActionMap() {
		return synchronizableObjectToSynchronizationActionMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSynchronizableObjectToSynchronizationActionMap_Key() {
		return (EReference)synchronizableObjectToSynchronizationActionMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSynchronizableObjectToSynchronizationActionMap_Value() {
		return (EAttribute)synchronizableObjectToSynchronizationActionMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTag() {
		return tagEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTag_InfoUnits() {
		return (EReference)tagEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTag_Name() {
		return (EAttribute)tagEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAvailableTags() {
		return availableTagsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAvailableTags_Tags() {
		return (EReference)availableTagsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSynchronizableObject() {
		return synchronizableObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSynchronizableObject_SynchronizationMetaData() {
		return (EReference)synchronizableObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSynchronizableObject_MarkedAsDeleteItems() {
		return (EReference)synchronizableObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCalendarEntry() {
		return calendarEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendarEntry_Id() {
		return (EAttribute)calendarEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendarEntry_Start() {
		return (EAttribute)calendarEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendarEntry_End() {
		return (EAttribute)calendarEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendarEntry_EntryType() {
		return (EAttribute)calendarEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendarEntry_Reminder() {
		return (EAttribute)calendarEntryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendarEntry_Title() {
		return (EAttribute)calendarEntryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSynchronizationState() {
		return synchronizationStateEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getSynchronizationAction() {
		return synchronizationActionEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getCalendarEntryType() {
		return calendarEntryTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getObject() {
		return objectEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getIRepository() {
		return iRepositoryEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InfomngmntFactory getInfomngmntFactory() {
		return (InfomngmntFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		informationUnitEClass = createEClass(INFORMATION_UNIT);
		createEAttribute(informationUnitEClass, INFORMATION_UNIT__STRING_VALUE);
		createEAttribute(informationUnitEClass, INFORMATION_UNIT__LONG_VALUE);
		createEAttribute(informationUnitEClass, INFORMATION_UNIT__BOOL_VALUE);
		createEAttribute(informationUnitEClass, INFORMATION_UNIT__BINARY_VALUE);
		createEAttribute(informationUnitEClass, INFORMATION_UNIT__DATE_VALUE);
		createEReference(informationUnitEClass, INFORMATION_UNIT__CHILD_VALUES);
		createEReference(informationUnitEClass, INFORMATION_UNIT__REFERENCES);
		createEReference(informationUnitEClass, INFORMATION_UNIT__LINKS);
		createEAttribute(informationUnitEClass, INFORMATION_UNIT__CREATION_DATE);
		createEReference(informationUnitEClass, INFORMATION_UNIT__USAGE_DATA);
		createEAttribute(informationUnitEClass, INFORMATION_UNIT__DESCRIPTION);
		createEAttribute(informationUnitEClass, INFORMATION_UNIT__KEYWORDS);
		createEReference(informationUnitEClass, INFORMATION_UNIT__CALENDAR_ENTRY);
		createEReference(informationUnitEClass, INFORMATION_UNIT__BINARY_REFERENCES);

		binaryReferenceEClass = createEClass(BINARY_REFERENCE);
		createEAttribute(binaryReferenceEClass, BINARY_REFERENCE__ID);
		createEAttribute(binaryReferenceEClass, BINARY_REFERENCE__PROJECT_RELATIVE_PATH);
		createEAttribute(binaryReferenceEClass, BINARY_REFERENCE__DIRTY);

		usageEClass = createEClass(USAGE);
		createEAttribute(usageEClass, USAGE__LAST_ACCESS);
		createEAttribute(usageEClass, USAGE__ACCESS_COUNT);

		categoryEClass = createEClass(CATEGORY);
		createEAttribute(categoryEClass, CATEGORY__ID);
		createEAttribute(categoryEClass, CATEGORY__LABEL);
		createEReference(categoryEClass, CATEGORY__CHILDREN);
		createEReference(categoryEClass, CATEGORY__INFORMATION_UNIT);
		createEAttribute(categoryEClass, CATEGORY__DESCRIPTION);

		abstractInformationUnitEClass = createEClass(ABSTRACT_INFORMATION_UNIT);
		createEAttribute(abstractInformationUnitEClass, ABSTRACT_INFORMATION_UNIT__ID);
		createEAttribute(abstractInformationUnitEClass, ABSTRACT_INFORMATION_UNIT__LABEL);
		createEAttribute(abstractInformationUnitEClass, ABSTRACT_INFORMATION_UNIT__TYPE);

		informationUnitListItemEClass = createEClass(INFORMATION_UNIT_LIST_ITEM);
		createEAttribute(informationUnitListItemEClass, INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH);

		applicationRootEClass = createEClass(APPLICATION_ROOT);
		createEReference(applicationRootEClass, APPLICATION_ROOT__ROOT_CATEGORIES);
		createEReference(applicationRootEClass, APPLICATION_ROOT__AVAILABLE_TAGS);

		annotationEClass = createEClass(ANNOTATION);
		createEAttribute(annotationEClass, ANNOTATION__DESCRIPTION);

		linkEClass = createEClass(LINK);
		createEReference(linkEClass, LINK__TARGET);

		adapterEClass = createEClass(ADAPTER);

		recentlyUsedKeywordsEClass = createEClass(RECENTLY_USED_KEYWORDS);
		createEAttribute(recentlyUsedKeywordsEClass, RECENTLY_USED_KEYWORDS__MAXLENGTH);
		createEAttribute(recentlyUsedKeywordsEClass, RECENTLY_USED_KEYWORDS__KEYWORDS);

		newElementRulesEClass = createEClass(NEW_ELEMENT_RULES);
		createEReference(newElementRulesEClass, NEW_ELEMENT_RULES__TRANSFER_TYPES);
		createEAttribute(newElementRulesEClass, NEW_ELEMENT_RULES__NAME);
		createEAttribute(newElementRulesEClass, NEW_ELEMENT_RULES__DELETABLE);

		ruleValueEClass = createEClass(RULE_VALUE);

		availableRuleDefinitionsEClass = createEClass(AVAILABLE_RULE_DEFINITIONS);
		createEReference(availableRuleDefinitionsEClass, AVAILABLE_RULE_DEFINITIONS__NEW_ELEMENT_RULES);

		remusTransferTypeEClass = createEClass(REMUS_TRANSFER_TYPE);
		createEAttribute(remusTransferTypeEClass, REMUS_TRANSFER_TYPE__NAME);
		createEAttribute(remusTransferTypeEClass, REMUS_TRANSFER_TYPE__ID);
		createEReference(remusTransferTypeEClass, REMUS_TRANSFER_TYPE__ACTIONS);

		ruleActionEClass = createEClass(RULE_ACTION);
		createEAttribute(ruleActionEClass, RULE_ACTION__NAME);
		createEAttribute(ruleActionEClass, RULE_ACTION__INFO_TYPE_ID);
		createEReference(ruleActionEClass, RULE_ACTION__RULE_VALUE);

		ruleResultEClass = createEClass(RULE_RESULT);
		createEAttribute(ruleResultEClass, RULE_RESULT__VALUE);
		createEReference(ruleResultEClass, RULE_RESULT__ACTIONS);
		createEAttribute(ruleResultEClass, RULE_RESULT__DESCRIPTION);

		remoteRepositoryEClass = createEClass(REMOTE_REPOSITORY);

		remoteObjectEClass = createEClass(REMOTE_OBJECT);
		createEAttribute(remoteObjectEClass, REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID);
		createEAttribute(remoteObjectEClass, REMOTE_OBJECT__ID);
		createEAttribute(remoteObjectEClass, REMOTE_OBJECT__URL);
		createEAttribute(remoteObjectEClass, REMOTE_OBJECT__NAME);
		createEAttribute(remoteObjectEClass, REMOTE_OBJECT__REPOSITORY_TYPE_ID);
		createEAttribute(remoteObjectEClass, REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID);
		createEAttribute(remoteObjectEClass, REMOTE_OBJECT__WRAPPED_OBJECT);
		createEAttribute(remoteObjectEClass, REMOTE_OBJECT__HASH);

		remoteContainerEClass = createEClass(REMOTE_CONTAINER);
		createEReference(remoteContainerEClass, REMOTE_CONTAINER__CHILDREN);
		createEReference(remoteContainerEClass, REMOTE_CONTAINER__EXCLUSION_CHILDREN);

		repositoryCollectionEClass = createEClass(REPOSITORY_COLLECTION);
		createEReference(repositoryCollectionEClass, REPOSITORY_COLLECTION__REPOSITORIES);

		synchronizationMetadataEClass = createEClass(SYNCHRONIZATION_METADATA);
		createEAttribute(synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__REPOSITORY_ID);
		createEAttribute(synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__URL);
		createEAttribute(synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__READONLY);
		createEAttribute(synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION);
		createEAttribute(synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__HASH);
		createEAttribute(synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__SYNC_STATE);

		changeSetEClass = createEClass(CHANGE_SET);
		createEReference(changeSetEClass, CHANGE_SET__TARGET_CATEGORY);
		createEReference(changeSetEClass, CHANGE_SET__CHANGE_SET_ITEMS);
		createEReference(changeSetEClass, CHANGE_SET__REPOSITORY);

		changeSetItemEClass = createEClass(CHANGE_SET_ITEM);
		createEReference(changeSetItemEClass, CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER);
		createEReference(changeSetItemEClass, CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT);
		createEReference(changeSetItemEClass, CHANGE_SET_ITEM__LOCAL_CONTAINER);
		createEReference(changeSetItemEClass, CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP);
		createEReference(changeSetItemEClass, CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP);
		createEReference(changeSetItemEClass, CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP);

		categoryToSynchronizationActionMapEClass = createEClass(CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP);
		createEReference(categoryToSynchronizationActionMapEClass, CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP__KEY);
		createEAttribute(categoryToSynchronizationActionMapEClass, CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP__VALUE);

		informationUnitListItemToInformationUnitMapEClass = createEClass(INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP);
		createEReference(informationUnitListItemToInformationUnitMapEClass, INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP__KEY);
		createEReference(informationUnitListItemToInformationUnitMapEClass, INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP__VALUE);

		synchronizableObjectToSynchronizationActionMapEClass = createEClass(SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP);
		createEReference(synchronizableObjectToSynchronizationActionMapEClass, SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP__KEY);
		createEAttribute(synchronizableObjectToSynchronizationActionMapEClass, SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP__VALUE);

		tagEClass = createEClass(TAG);
		createEReference(tagEClass, TAG__INFO_UNITS);
		createEAttribute(tagEClass, TAG__NAME);

		availableTagsEClass = createEClass(AVAILABLE_TAGS);
		createEReference(availableTagsEClass, AVAILABLE_TAGS__TAGS);

		synchronizableObjectEClass = createEClass(SYNCHRONIZABLE_OBJECT);
		createEReference(synchronizableObjectEClass, SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
		createEReference(synchronizableObjectEClass, SYNCHRONIZABLE_OBJECT__MARKED_AS_DELETE_ITEMS);

		calendarEntryEClass = createEClass(CALENDAR_ENTRY);
		createEAttribute(calendarEntryEClass, CALENDAR_ENTRY__ID);
		createEAttribute(calendarEntryEClass, CALENDAR_ENTRY__START);
		createEAttribute(calendarEntryEClass, CALENDAR_ENTRY__END);
		createEAttribute(calendarEntryEClass, CALENDAR_ENTRY__ENTRY_TYPE);
		createEAttribute(calendarEntryEClass, CALENDAR_ENTRY__REMINDER);
		createEAttribute(calendarEntryEClass, CALENDAR_ENTRY__TITLE);

		// Create enums
		synchronizationStateEEnum = createEEnum(SYNCHRONIZATION_STATE);
		synchronizationActionEEnum = createEEnum(SYNCHRONIZATION_ACTION);
		calendarEntryTypeEEnum = createEEnum(CALENDAR_ENTRY_TYPE);

		// Create data types
		objectEDataType = createEDataType(OBJECT);
		iRepositoryEDataType = createEDataType(IREPOSITORY);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		informationUnitEClass.getESuperTypes().add(this.getAbstractInformationUnit());
		categoryEClass.getESuperTypes().add(this.getSynchronizableObject());
		abstractInformationUnitEClass.getESuperTypes().add(this.getAdapter());
		informationUnitListItemEClass.getESuperTypes().add(this.getAbstractInformationUnit());
		informationUnitListItemEClass.getESuperTypes().add(this.getSynchronizableObject());
		ruleValueEClass.getESuperTypes().add(this.getInformationUnit());
		remoteRepositoryEClass.getESuperTypes().add(this.getRemoteContainer());
		remoteObjectEClass.getESuperTypes().add(this.getAdapter());
		remoteContainerEClass.getESuperTypes().add(this.getRemoteObject());
		synchronizationMetadataEClass.getESuperTypes().add(this.getAdapter());
		changeSetEClass.getESuperTypes().add(this.getAdapter());
		changeSetItemEClass.getESuperTypes().add(this.getAdapter());
		tagEClass.getESuperTypes().add(this.getAdapter());
		synchronizableObjectEClass.getESuperTypes().add(this.getAdapter());

		// Initialize classes and features; add operations and parameters
		initEClass(informationUnitEClass, InformationUnit.class, "InformationUnit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInformationUnit_StringValue(), ecorePackage.getEString(), "stringValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_LongValue(), ecorePackage.getELong(), "longValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_BoolValue(), ecorePackage.getEBoolean(), "boolValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_BinaryValue(), ecorePackage.getEByteArray(), "binaryValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_DateValue(), ecorePackage.getEDate(), "dateValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_ChildValues(), this.getInformationUnit(), null, "childValues", null, 0, -1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_References(), this.getInformationUnit(), null, "references", null, 0, -1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_Links(), this.getLink(), null, "links", null, 0, -1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_CreationDate(), ecorePackage.getEDate(), "creationDate", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_UsageData(), this.getUsage(), null, "usageData", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_Description(), ecorePackage.getEString(), "description", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_Keywords(), ecorePackage.getEString(), "keywords", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_CalendarEntry(), this.getCalendarEntry(), null, "calendarEntry", null, 0, -1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_BinaryReferences(), this.getBinaryReference(), null, "binaryReferences", null, 0, -1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(binaryReferenceEClass, BinaryReference.class, "BinaryReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBinaryReference_Id(), ecorePackage.getEString(), "id", null, 1, 1, BinaryReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryReference_ProjectRelativePath(), ecorePackage.getEString(), "projectRelativePath", "", 1, 1, BinaryReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryReference_Dirty(), ecorePackage.getEBoolean(), "dirty", null, 0, 1, BinaryReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(usageEClass, Usage.class, "Usage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUsage_LastAccess(), ecorePackage.getEDate(), "lastAccess", null, 0, 1, Usage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUsage_AccessCount(), ecorePackage.getEInt(), "accessCount", null, 0, 1, Usage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(categoryEClass, Category.class, "Category", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCategory_Id(), ecorePackage.getEString(), "id", null, 1, 1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategory_Label(), ecorePackage.getEString(), "label", null, 0, 1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCategory_Children(), this.getCategory(), null, "children", null, 0, -1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCategory_InformationUnit(), this.getInformationUnitListItem(), null, "informationUnit", null, 0, -1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategory_Description(), ecorePackage.getEString(), "description", null, 0, 1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(abstractInformationUnitEClass, AbstractInformationUnit.class, "AbstractInformationUnit", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractInformationUnit_Id(), ecorePackage.getEString(), "id", null, 1, 1, AbstractInformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractInformationUnit_Label(), ecorePackage.getEString(), "label", null, 0, 1, AbstractInformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractInformationUnit_Type(), ecorePackage.getEString(), "type", null, 0, 1, AbstractInformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(informationUnitListItemEClass, InformationUnitListItem.class, "InformationUnitListItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInformationUnitListItem_WorkspacePath(), ecorePackage.getEString(), "workspacePath", null, 0, 1, InformationUnitListItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationRootEClass, ApplicationRoot.class, "ApplicationRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplicationRoot_RootCategories(), this.getCategory(), null, "rootCategories", null, 0, -1, ApplicationRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplicationRoot_AvailableTags(), this.getAvailableTags(), null, "availableTags", null, 1, 1, ApplicationRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(annotationEClass, Annotation.class, "Annotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAnnotation_Description(), ecorePackage.getEString(), "description", null, 0, 1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(linkEClass, Link.class, "Link", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLink_Target(), this.getInformationUnit(), null, "target", null, 1, 1, Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(adapterEClass, Adapter.class, "Adapter", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(recentlyUsedKeywordsEClass, RecentlyUsedKeywords.class, "RecentlyUsedKeywords", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRecentlyUsedKeywords_Maxlength(), ecorePackage.getEInt(), "maxlength", "100", 0, 1, RecentlyUsedKeywords.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRecentlyUsedKeywords_Keywords(), ecorePackage.getEString(), "keywords", null, 0, -1, RecentlyUsedKeywords.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(newElementRulesEClass, NewElementRules.class, "NewElementRules", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNewElementRules_TransferTypes(), this.getRemusTransferType(), null, "transferTypes", null, 0, -1, NewElementRules.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNewElementRules_Name(), ecorePackage.getEString(), "name", null, 1, 1, NewElementRules.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNewElementRules_Deletable(), ecorePackage.getEBoolean(), "deletable", null, 0, 1, NewElementRules.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleValueEClass, RuleValue.class, "RuleValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(availableRuleDefinitionsEClass, AvailableRuleDefinitions.class, "AvailableRuleDefinitions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAvailableRuleDefinitions_NewElementRules(), this.getNewElementRules(), null, "newElementRules", null, 0, -1, AvailableRuleDefinitions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(remusTransferTypeEClass, RemusTransferType.class, "RemusTransferType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRemusTransferType_Name(), ecorePackage.getEString(), "name", null, 0, 1, RemusTransferType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemusTransferType_Id(), ecorePackage.getEString(), "id", null, 1, 1, RemusTransferType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRemusTransferType_Actions(), this.getRuleAction(), null, "actions", null, 0, -1, RemusTransferType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleActionEClass, RuleAction.class, "RuleAction", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleAction_Name(), ecorePackage.getEString(), "name", null, 0, 1, RuleAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleAction_InfoTypeId(), ecorePackage.getEString(), "infoTypeId", null, 1, 1, RuleAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleAction_RuleValue(), this.getRuleValue(), null, "ruleValue", null, 0, 1, RuleAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleResultEClass, RuleResult.class, "RuleResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleResult_Value(), this.getObject(), "value", null, 1, 1, RuleResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleResult_Actions(), this.getRuleAction(), null, "actions", null, 1, -1, RuleResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleResult_Description(), ecorePackage.getEString(), "description", null, 0, 1, RuleResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(remoteRepositoryEClass, RemoteRepository.class, "RemoteRepository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		addEOperation(remoteRepositoryEClass, this.getIRepository(), "getRepositoryImplementation", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(remoteObjectEClass, RemoteObject.class, "RemoteObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRemoteObject_PossibleInfoTypeId(), ecorePackage.getEString(), "possibleInfoTypeId", null, 0, -1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_Id(), ecorePackage.getEString(), "id", null, 0, 1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_Url(), ecorePackage.getEString(), "url", null, 1, 1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_Name(), ecorePackage.getEString(), "name", null, 0, 1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_RepositoryTypeId(), ecorePackage.getEString(), "repositoryTypeId", null, 1, 1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_RepositoryTypeObjectId(), ecorePackage.getEString(), "repositoryTypeObjectId", null, 1, 1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_WrappedObject(), this.getObject(), "wrappedObject", null, 0, 1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_Hash(), ecorePackage.getEString(), "hash", null, 0, 1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(remoteContainerEClass, RemoteContainer.class, "RemoteContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRemoteContainer_Children(), this.getRemoteObject(), null, "children", null, 0, -1, RemoteContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRemoteContainer_ExclusionChildren(), this.getRemoteObject(), null, "exclusionChildren", null, 0, -1, RemoteContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(repositoryCollectionEClass, RepositoryCollection.class, "RepositoryCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRepositoryCollection_Repositories(), this.getRemoteRepository(), null, "repositories", null, 0, -1, RepositoryCollection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(synchronizationMetadataEClass, SynchronizationMetadata.class, "SynchronizationMetadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSynchronizationMetadata_RepositoryId(), ecorePackage.getEString(), "repositoryId", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_Url(), ecorePackage.getEString(), "url", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_Readonly(), ecorePackage.getEBoolean(), "readonly", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_LastSynchronisation(), ecorePackage.getEDate(), "lastSynchronisation", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_Hash(), ecorePackage.getEString(), "hash", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_SyncState(), this.getSynchronizationState(), "syncState", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(changeSetEClass, ChangeSet.class, "ChangeSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChangeSet_TargetCategory(), this.getCategory(), null, "targetCategory", null, 1, 1, ChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSet_ChangeSetItems(), this.getChangeSetItem(), null, "changeSetItems", null, 0, -1, ChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSet_Repository(), this.getRemoteRepository(), null, "repository", null, 1, 1, ChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(changeSetItemEClass, ChangeSetItem.class, "ChangeSetItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChangeSetItem_RemoteConvertedContainer(), this.getCategory(), null, "remoteConvertedContainer", null, 0, 1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSetItem_RemoteOriginalObject(), this.getRemoteContainer(), null, "remoteOriginalObject", null, 0, 1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSetItem_LocalContainer(), this.getCategory(), null, "localContainer", null, 0, 1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSetItem_SyncCategoryActionMap(), this.getCategoryToSynchronizationActionMap(), null, "syncCategoryActionMap", null, 0, -1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSetItem_SyncObjectActionMap(), this.getSynchronizableObjectToSynchronizationActionMap(), null, "syncObjectActionMap", null, 0, -1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSetItem_RemoteFullObjectMap(), this.getInformationUnitListItemToInformationUnitMap(), null, "remoteFullObjectMap", null, 0, -1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(categoryToSynchronizationActionMapEClass, Map.Entry.class, "CategoryToSynchronizationActionMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCategoryToSynchronizationActionMap_Key(), this.getCategory(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategoryToSynchronizationActionMap_Value(), this.getSynchronizationAction(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(informationUnitListItemToInformationUnitMapEClass, Map.Entry.class, "InformationUnitListItemToInformationUnitMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInformationUnitListItemToInformationUnitMap_Key(), this.getInformationUnitListItem(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnitListItemToInformationUnitMap_Value(), this.getInformationUnit(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(synchronizableObjectToSynchronizationActionMapEClass, Map.Entry.class, "SynchronizableObjectToSynchronizationActionMap", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSynchronizableObjectToSynchronizationActionMap_Key(), this.getSynchronizableObject(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizableObjectToSynchronizationActionMap_Value(), this.getSynchronizationAction(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tagEClass, Tag.class, "Tag", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTag_InfoUnits(), this.getInformationUnitListItem(), null, "infoUnits", null, 0, -1, Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getTag_Name(), ecorePackage.getEString(), "name", null, 0, 1, Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(availableTagsEClass, AvailableTags.class, "AvailableTags", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAvailableTags_Tags(), this.getTag(), null, "tags", null, 0, -1, AvailableTags.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(synchronizableObjectEClass, SynchronizableObject.class, "SynchronizableObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSynchronizableObject_SynchronizationMetaData(), this.getSynchronizationMetadata(), null, "synchronizationMetaData", null, 0, 1, SynchronizableObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSynchronizableObject_MarkedAsDeleteItems(), this.getSynchronizableObject(), null, "markedAsDeleteItems", null, 0, -1, SynchronizableObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(calendarEntryEClass, CalendarEntry.class, "CalendarEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCalendarEntry_Id(), ecorePackage.getEString(), "id", null, 1, 1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_Start(), ecorePackage.getEDate(), "start", null, 1, 1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_End(), ecorePackage.getEDate(), "end", null, 1, 1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_EntryType(), this.getCalendarEntryType(), "entryType", null, 0, 1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_Reminder(), ecorePackage.getEInt(), "reminder", null, 0, 1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_Title(), ecorePackage.getEString(), "title", null, 0, 1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(synchronizationStateEEnum, SynchronizationState.class, "SynchronizationState");
		addEEnumLiteral(synchronizationStateEEnum, SynchronizationState.LOCAL_DELETED);
		addEEnumLiteral(synchronizationStateEEnum, SynchronizationState.TARGET_DELETED);
		addEEnumLiteral(synchronizationStateEEnum, SynchronizationState.NOT_ADDED);
		addEEnumLiteral(synchronizationStateEEnum, SynchronizationState.LOCAL_EDITED);
		addEEnumLiteral(synchronizationStateEEnum, SynchronizationState.TARGET_EDITED);
		addEEnumLiteral(synchronizationStateEEnum, SynchronizationState.IN_SYNC);

		initEEnum(synchronizationActionEEnum, SynchronizationAction.class, "SynchronizationAction");
		addEEnumLiteral(synchronizationActionEEnum, SynchronizationAction.REPLACE_LOCAL);
		addEEnumLiteral(synchronizationActionEEnum, SynchronizationAction.REPLACE_REMOTE);
		addEEnumLiteral(synchronizationActionEEnum, SynchronizationAction.DELETE_LOCAL);
		addEEnumLiteral(synchronizationActionEEnum, SynchronizationAction.DELETE_REMOTE);
		addEEnumLiteral(synchronizationActionEEnum, SynchronizationAction.ADD_LOCAL);
		addEEnumLiteral(synchronizationActionEEnum, SynchronizationAction.ADD_REMOTE);
		addEEnumLiteral(synchronizationActionEEnum, SynchronizationAction.RESOLVE_CONFLICT);

		initEEnum(calendarEntryTypeEEnum, CalendarEntryType.class, "CalendarEntryType");
		addEEnumLiteral(calendarEntryTypeEEnum, CalendarEntryType.ONE_TIME);
		addEEnumLiteral(calendarEntryTypeEEnum, CalendarEntryType.WEEKLY);
		addEEnumLiteral(calendarEntryTypeEEnum, CalendarEntryType.TWO_WEEK);
		addEEnumLiteral(calendarEntryTypeEEnum, CalendarEntryType.MONTHLY);
		addEEnumLiteral(calendarEntryTypeEEnum, CalendarEntryType.ANNUAL);

		// Initialize data types
		initEDataType(objectEDataType, Object.class, "Object", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(iRepositoryEDataType, IRepository.class, "IRepository", !IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (getInformationUnit_BinaryValue(), 
		   source, 
		   new String[] {
			 "name", "value",
			 "kind", "element"
		   });
	}

} // InfomngmntPackageImpl
