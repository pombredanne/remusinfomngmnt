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

import static org.remus.infomngmnt.InfomngmntPackage.ADAPTER;

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
import org.remus.infomngmnt.DynamicStructure;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationStructure;
import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.InformationStructureItem;
import org.remus.infomngmnt.InformationStructureType;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.NewElementRules;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationCollection;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.RecentlyUsedKeywords;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RemusTransferType;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.RuleAction;
import org.remus.infomngmnt.RuleResult;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.Tag;
import org.remus.infomngmnt.Usage;
import org.remus.infomngmnt.core.extension.TransferWrapper;
import org.remus.infomngmnt.core.remote.IRepository;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class InfomngmntPackageImpl extends EPackageImpl implements InfomngmntPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass informationUnitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass binaryReferenceEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass usageEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass categoryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass abstractInformationUnitEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass informationUnitListItemEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass applicationRootEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass annotationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass linkEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass adapterEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass recentlyUsedKeywordsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass newElementRulesEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ruleValueEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass availableRuleDefinitionsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass remusTransferTypeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ruleActionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ruleResultEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass remoteRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass remoteObjectEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass remoteContainerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass repositoryCollectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass synchronizationMetadataEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass changeSetEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass changeSetItemEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass categoryToSynchronizationActionMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass informationUnitListItemToInformationUnitMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass synchronizableObjectToSynchronizationActionMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tagEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass availableTagsEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass synchronizableObjectEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass calendarEntryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass notificationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass notificationCollectionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stringToStringMapEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass informationStructureDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass informationStructureItemEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass informationStructureEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass dynamicStructureEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum synchronizationStateEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum synchronizationActionEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum calendarEntryTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum notificationImportanceEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum severityEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum informationStructureTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType objectEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType iRepositoryEDataType = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType transferWrapperEDataType = null;

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
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link InfomngmntPackage#eINSTANCE}
	 * when that field is accessed. Clients should not invoke it directly.
	 * Instead, they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static InfomngmntPackage init() {
		if (isInited) {
			return (InfomngmntPackage) EPackage.Registry.INSTANCE
					.getEPackage(InfomngmntPackage.eNS_URI);
		}

		// Obtain or create and register package
		InfomngmntPackageImpl theInfomngmntPackage = (InfomngmntPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof InfomngmntPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new InfomngmntPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theInfomngmntPackage.createPackageContents();

		// Initialize created meta-data
		theInfomngmntPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInfomngmntPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(InfomngmntPackage.eNS_URI, theInfomngmntPackage);
		return theInfomngmntPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInformationUnit() {
		return this.informationUnitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_StringValue() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_LongValue() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_BoolValue() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_BinaryValue() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_DateValue() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_DoubleValue() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationUnit_ChildValues() {
		return (EReference) this.informationUnitEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationUnit_References() {
		return (EReference) this.informationUnitEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationUnit_Links() {
		return (EReference) this.informationUnitEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_CreationDate() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationUnit_UsageData() {
		return (EReference) this.informationUnitEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_Description() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnit_Keywords() {
		return (EAttribute) this.informationUnitEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationUnit_CalendarEntry() {
		return (EReference) this.informationUnitEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationUnit_BinaryReferences() {
		return (EReference) this.informationUnitEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBinaryReference() {
		return this.binaryReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBinaryReference_Id() {
		return (EAttribute) this.binaryReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBinaryReference_ProjectRelativePath() {
		return (EAttribute) this.binaryReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBinaryReference_Dirty() {
		return (EAttribute) this.binaryReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getUsage() {
		return this.usageEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getUsage_LastAccess() {
		return (EAttribute) this.usageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getUsage_AccessCount() {
		return (EAttribute) this.usageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCategory() {
		return this.categoryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCategory_Id() {
		return (EAttribute) this.categoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCategory_Label() {
		return (EAttribute) this.categoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCategory_Children() {
		return (EReference) this.categoryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCategory_InformationUnit() {
		return (EReference) this.categoryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCategory_Description() {
		return (EAttribute) this.categoryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAbstractInformationUnit() {
		return this.abstractInformationUnitEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAbstractInformationUnit_Id() {
		return (EAttribute) this.abstractInformationUnitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAbstractInformationUnit_Label() {
		return (EAttribute) this.abstractInformationUnitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAbstractInformationUnit_Type() {
		return (EAttribute) this.abstractInformationUnitEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInformationUnitListItem() {
		return this.informationUnitListItemEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationUnitListItem_WorkspacePath() {
		return (EAttribute) this.informationUnitListItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getApplicationRoot() {
		return this.applicationRootEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getApplicationRoot_RootCategories() {
		return (EReference) this.applicationRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getApplicationRoot_AvailableTags() {
		return (EReference) this.applicationRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAnnotation() {
		return this.annotationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getAnnotation_Description() {
		return (EAttribute) this.annotationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getLink() {
		return this.linkEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getLink_Target() {
		return (EReference) this.linkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAdapter() {
		return this.adapterEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRecentlyUsedKeywords() {
		return this.recentlyUsedKeywordsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRecentlyUsedKeywords_Maxlength() {
		return (EAttribute) this.recentlyUsedKeywordsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRecentlyUsedKeywords_Keywords() {
		return (EAttribute) this.recentlyUsedKeywordsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNewElementRules() {
		return this.newElementRulesEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getNewElementRules_TransferTypes() {
		return (EReference) this.newElementRulesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNewElementRules_Name() {
		return (EAttribute) this.newElementRulesEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNewElementRules_Deletable() {
		return (EAttribute) this.newElementRulesEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRuleValue() {
		return this.ruleValueEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAvailableRuleDefinitions() {
		return this.availableRuleDefinitionsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAvailableRuleDefinitions_NewElementRules() {
		return (EReference) this.availableRuleDefinitionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRemusTransferType() {
		return this.remusTransferTypeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemusTransferType_Name() {
		return (EAttribute) this.remusTransferTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemusTransferType_Id() {
		return (EAttribute) this.remusTransferTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRemusTransferType_Actions() {
		return (EReference) this.remusTransferTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRuleAction() {
		return this.ruleActionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRuleAction_Name() {
		return (EAttribute) this.ruleActionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRuleAction_InfoTypeId() {
		return (EAttribute) this.ruleActionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRuleAction_RuleValue() {
		return (EReference) this.ruleActionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRuleAction_GroovyMatcher() {
		return (EAttribute) this.ruleActionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRuleAction_PostProcessingInstructions() {
		return (EAttribute) this.ruleActionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRuleResult() {
		return this.ruleResultEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRuleResult_Value() {
		return (EAttribute) this.ruleResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRuleResult_Actions() {
		return (EReference) this.ruleResultEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRuleResult_Description() {
		return (EAttribute) this.ruleResultEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRuleResult_TransferType() {
		return (EAttribute) this.ruleResultEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRemoteRepository() {
		return this.remoteRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRemoteRepository_Options() {
		return (EReference) this.remoteRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRemoteObject() {
		return this.remoteObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemoteObject_PossibleInfoTypeId() {
		return (EAttribute) this.remoteObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemoteObject_Id() {
		return (EAttribute) this.remoteObjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemoteObject_Url() {
		return (EAttribute) this.remoteObjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemoteObject_Name() {
		return (EAttribute) this.remoteObjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemoteObject_RepositoryTypeId() {
		return (EAttribute) this.remoteObjectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemoteObject_RepositoryTypeObjectId() {
		return (EAttribute) this.remoteObjectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemoteObject_WrappedObject() {
		return (EAttribute) this.remoteObjectEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRemoteObject_Hash() {
		return (EAttribute) this.remoteObjectEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRemoteContainer() {
		return this.remoteContainerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRemoteContainer_Children() {
		return (EReference) this.remoteContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRemoteContainer_ExclusionChildren() {
		return (EReference) this.remoteContainerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRepositoryCollection() {
		return this.repositoryCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getRepositoryCollection_Repositories() {
		return (EReference) this.repositoryCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSynchronizationMetadata() {
		return this.synchronizationMetadataEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_RepositoryId() {
		return (EAttribute) this.synchronizationMetadataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_Url() {
		return (EAttribute) this.synchronizationMetadataEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_Readonly() {
		return (EAttribute) this.synchronizationMetadataEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_LastSynchronisation() {
		return (EAttribute) this.synchronizationMetadataEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_Hash() {
		return (EAttribute) this.synchronizationMetadataEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_SyncState() {
		return (EAttribute) this.synchronizationMetadataEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSynchronizationMetadata_CurrentlySyncing() {
		return (EAttribute) this.synchronizationMetadataEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getChangeSet() {
		return this.changeSetEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSet_TargetCategory() {
		return (EReference) this.changeSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSet_ChangeSetItems() {
		return (EReference) this.changeSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSet_Repository() {
		return (EReference) this.changeSetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getChangeSetItem() {
		return this.changeSetItemEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSetItem_RemoteConvertedContainer() {
		return (EReference) this.changeSetItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSetItem_RemoteOriginalObject() {
		return (EReference) this.changeSetItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSetItem_LocalContainer() {
		return (EReference) this.changeSetItemEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSetItem_SyncCategoryActionMap() {
		return (EReference) this.changeSetItemEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSetItem_SyncObjectActionMap() {
		return (EReference) this.changeSetItemEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangeSetItem_RemoteFullObjectMap() {
		return (EReference) this.changeSetItemEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCategoryToSynchronizationActionMap() {
		return this.categoryToSynchronizationActionMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getCategoryToSynchronizationActionMap_Key() {
		return (EReference) this.categoryToSynchronizationActionMapEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCategoryToSynchronizationActionMap_Value() {
		return (EAttribute) this.categoryToSynchronizationActionMapEClass.getEStructuralFeatures()
				.get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInformationUnitListItemToInformationUnitMap() {
		return this.informationUnitListItemToInformationUnitMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationUnitListItemToInformationUnitMap_Key() {
		return (EReference) this.informationUnitListItemToInformationUnitMapEClass
				.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationUnitListItemToInformationUnitMap_Value() {
		return (EReference) this.informationUnitListItemToInformationUnitMapEClass
				.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSynchronizableObjectToSynchronizationActionMap() {
		return this.synchronizableObjectToSynchronizationActionMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getSynchronizableObjectToSynchronizationActionMap_Key() {
		return (EReference) this.synchronizableObjectToSynchronizationActionMapEClass
				.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getSynchronizableObjectToSynchronizationActionMap_Value() {
		return (EAttribute) this.synchronizableObjectToSynchronizationActionMapEClass
				.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTag() {
		return this.tagEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTag_InfoUnits() {
		return (EAttribute) this.tagEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTag_Name() {
		return (EAttribute) this.tagEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAvailableTags() {
		return this.availableTagsEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAvailableTags_Tags() {
		return (EReference) this.availableTagsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getSynchronizableObject() {
		return this.synchronizableObjectEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getSynchronizableObject_SynchronizationMetaData() {
		return (EReference) this.synchronizableObjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getCalendarEntry() {
		return this.calendarEntryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCalendarEntry_Id() {
		return (EAttribute) this.calendarEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCalendarEntry_Start() {
		return (EAttribute) this.calendarEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCalendarEntry_End() {
		return (EAttribute) this.calendarEntryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCalendarEntry_EntryType() {
		return (EAttribute) this.calendarEntryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCalendarEntry_Reminder() {
		return (EAttribute) this.calendarEntryEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getCalendarEntry_Title() {
		return (EAttribute) this.calendarEntryEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNotification() {
		return this.notificationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_TimeStamp() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_Importance() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_Severity() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_Noticed() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_Message() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_Details() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getNotification_Children() {
		return (EReference) this.notificationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_AffectedInfoUnitIds() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_Source() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNotification_Image() {
		return (EAttribute) this.notificationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNotificationCollection() {
		return this.notificationCollectionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getNotificationCollection_Notifcations() {
		return (EReference) this.notificationCollectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getStringToStringMap() {
		return this.stringToStringMapEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getStringToStringMap_Key() {
		return (EAttribute) this.stringToStringMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getStringToStringMap_Value() {
		return (EAttribute) this.stringToStringMapEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInformationStructureDefinition() {
		return this.informationStructureDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationStructureDefinition_StructurePool() {
		return (EReference) this.informationStructureDefinitionEClass.getEStructuralFeatures().get(
				0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInformationStructureItem() {
		return this.informationStructureItemEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationStructureItem_Id() {
		return (EAttribute) this.informationStructureItemEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationStructureItem_CreateAlways() {
		return (EAttribute) this.informationStructureItemEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getInformationStructure() {
		return this.informationStructureEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationStructure_Type() {
		return (EAttribute) this.informationStructureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationStructure_StructureItems() {
		return (EReference) this.informationStructureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getInformationStructure_ReferencedStructureItems() {
		return (EReference) this.informationStructureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationStructure_CanHaveBinaryReferences() {
		return (EAttribute) this.informationStructureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getInformationStructure_Label() {
		return (EAttribute) this.informationStructureEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDynamicStructure() {
		return this.dynamicStructureEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDynamicStructure_LowerBound() {
		return (EAttribute) this.dynamicStructureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDynamicStructure_UpperBound() {
		return (EAttribute) this.dynamicStructureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getSynchronizationState() {
		return this.synchronizationStateEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getSynchronizationAction() {
		return this.synchronizationActionEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getCalendarEntryType() {
		return this.calendarEntryTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getNotificationImportance() {
		return this.notificationImportanceEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getSeverity() {
		return this.severityEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EEnum getInformationStructureType() {
		return this.informationStructureTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getObject() {
		return this.objectEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getIRepository() {
		return this.iRepositoryEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EDataType getTransferWrapper() {
		return this.transferWrapperEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public InfomngmntFactory getInfomngmntFactory() {
		return (InfomngmntFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (this.isCreated) {
			return;
		}
		this.isCreated = true;

		// Create classes and their features
		this.informationUnitEClass = createEClass(INFORMATION_UNIT);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__STRING_VALUE);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__LONG_VALUE);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__BOOL_VALUE);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__BINARY_VALUE);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__DATE_VALUE);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__DOUBLE_VALUE);
		createEReference(this.informationUnitEClass, INFORMATION_UNIT__CHILD_VALUES);
		createEReference(this.informationUnitEClass, INFORMATION_UNIT__REFERENCES);
		createEReference(this.informationUnitEClass, INFORMATION_UNIT__LINKS);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__CREATION_DATE);
		createEReference(this.informationUnitEClass, INFORMATION_UNIT__USAGE_DATA);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__DESCRIPTION);
		createEAttribute(this.informationUnitEClass, INFORMATION_UNIT__KEYWORDS);
		createEReference(this.informationUnitEClass, INFORMATION_UNIT__CALENDAR_ENTRY);
		createEReference(this.informationUnitEClass, INFORMATION_UNIT__BINARY_REFERENCES);

		this.binaryReferenceEClass = createEClass(BINARY_REFERENCE);
		createEAttribute(this.binaryReferenceEClass, BINARY_REFERENCE__ID);
		createEAttribute(this.binaryReferenceEClass, BINARY_REFERENCE__PROJECT_RELATIVE_PATH);
		createEAttribute(this.binaryReferenceEClass, BINARY_REFERENCE__DIRTY);

		this.usageEClass = createEClass(USAGE);
		createEAttribute(this.usageEClass, USAGE__LAST_ACCESS);
		createEAttribute(this.usageEClass, USAGE__ACCESS_COUNT);

		this.categoryEClass = createEClass(CATEGORY);
		createEAttribute(this.categoryEClass, CATEGORY__ID);
		createEAttribute(this.categoryEClass, CATEGORY__LABEL);
		createEReference(this.categoryEClass, CATEGORY__CHILDREN);
		createEReference(this.categoryEClass, CATEGORY__INFORMATION_UNIT);
		createEAttribute(this.categoryEClass, CATEGORY__DESCRIPTION);

		this.abstractInformationUnitEClass = createEClass(ABSTRACT_INFORMATION_UNIT);
		createEAttribute(this.abstractInformationUnitEClass, ABSTRACT_INFORMATION_UNIT__ID);
		createEAttribute(this.abstractInformationUnitEClass, ABSTRACT_INFORMATION_UNIT__LABEL);
		createEAttribute(this.abstractInformationUnitEClass, ABSTRACT_INFORMATION_UNIT__TYPE);

		this.informationUnitListItemEClass = createEClass(INFORMATION_UNIT_LIST_ITEM);
		createEAttribute(this.informationUnitListItemEClass,
				INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH);

		this.applicationRootEClass = createEClass(APPLICATION_ROOT);
		createEReference(this.applicationRootEClass, APPLICATION_ROOT__ROOT_CATEGORIES);
		createEReference(this.applicationRootEClass, APPLICATION_ROOT__AVAILABLE_TAGS);

		this.annotationEClass = createEClass(ANNOTATION);
		createEAttribute(this.annotationEClass, ANNOTATION__DESCRIPTION);

		this.linkEClass = createEClass(LINK);
		createEReference(this.linkEClass, LINK__TARGET);

		this.adapterEClass = createEClass(ADAPTER);

		this.recentlyUsedKeywordsEClass = createEClass(RECENTLY_USED_KEYWORDS);
		createEAttribute(this.recentlyUsedKeywordsEClass, RECENTLY_USED_KEYWORDS__MAXLENGTH);
		createEAttribute(this.recentlyUsedKeywordsEClass, RECENTLY_USED_KEYWORDS__KEYWORDS);

		this.newElementRulesEClass = createEClass(NEW_ELEMENT_RULES);
		createEReference(this.newElementRulesEClass, NEW_ELEMENT_RULES__TRANSFER_TYPES);
		createEAttribute(this.newElementRulesEClass, NEW_ELEMENT_RULES__NAME);
		createEAttribute(this.newElementRulesEClass, NEW_ELEMENT_RULES__DELETABLE);

		this.ruleValueEClass = createEClass(RULE_VALUE);

		this.availableRuleDefinitionsEClass = createEClass(AVAILABLE_RULE_DEFINITIONS);
		createEReference(this.availableRuleDefinitionsEClass,
				AVAILABLE_RULE_DEFINITIONS__NEW_ELEMENT_RULES);

		this.remusTransferTypeEClass = createEClass(REMUS_TRANSFER_TYPE);
		createEAttribute(this.remusTransferTypeEClass, REMUS_TRANSFER_TYPE__NAME);
		createEAttribute(this.remusTransferTypeEClass, REMUS_TRANSFER_TYPE__ID);
		createEReference(this.remusTransferTypeEClass, REMUS_TRANSFER_TYPE__ACTIONS);

		this.ruleActionEClass = createEClass(RULE_ACTION);
		createEAttribute(this.ruleActionEClass, RULE_ACTION__NAME);
		createEAttribute(this.ruleActionEClass, RULE_ACTION__INFO_TYPE_ID);
		createEReference(this.ruleActionEClass, RULE_ACTION__RULE_VALUE);
		createEAttribute(this.ruleActionEClass, RULE_ACTION__GROOVY_MATCHER);
		createEAttribute(this.ruleActionEClass, RULE_ACTION__POST_PROCESSING_INSTRUCTIONS);

		this.ruleResultEClass = createEClass(RULE_RESULT);
		createEAttribute(this.ruleResultEClass, RULE_RESULT__VALUE);
		createEReference(this.ruleResultEClass, RULE_RESULT__ACTIONS);
		createEAttribute(this.ruleResultEClass, RULE_RESULT__DESCRIPTION);
		createEAttribute(this.ruleResultEClass, RULE_RESULT__TRANSFER_TYPE);

		this.remoteRepositoryEClass = createEClass(REMOTE_REPOSITORY);
		createEReference(this.remoteRepositoryEClass, REMOTE_REPOSITORY__OPTIONS);

		this.remoteObjectEClass = createEClass(REMOTE_OBJECT);
		createEAttribute(this.remoteObjectEClass, REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID);
		createEAttribute(this.remoteObjectEClass, REMOTE_OBJECT__ID);
		createEAttribute(this.remoteObjectEClass, REMOTE_OBJECT__URL);
		createEAttribute(this.remoteObjectEClass, REMOTE_OBJECT__NAME);
		createEAttribute(this.remoteObjectEClass, REMOTE_OBJECT__REPOSITORY_TYPE_ID);
		createEAttribute(this.remoteObjectEClass, REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID);
		createEAttribute(this.remoteObjectEClass, REMOTE_OBJECT__WRAPPED_OBJECT);
		createEAttribute(this.remoteObjectEClass, REMOTE_OBJECT__HASH);

		this.remoteContainerEClass = createEClass(REMOTE_CONTAINER);
		createEReference(this.remoteContainerEClass, REMOTE_CONTAINER__CHILDREN);
		createEReference(this.remoteContainerEClass, REMOTE_CONTAINER__EXCLUSION_CHILDREN);

		this.repositoryCollectionEClass = createEClass(REPOSITORY_COLLECTION);
		createEReference(this.repositoryCollectionEClass, REPOSITORY_COLLECTION__REPOSITORIES);

		this.synchronizationMetadataEClass = createEClass(SYNCHRONIZATION_METADATA);
		createEAttribute(this.synchronizationMetadataEClass,
				SYNCHRONIZATION_METADATA__REPOSITORY_ID);
		createEAttribute(this.synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__URL);
		createEAttribute(this.synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__READONLY);
		createEAttribute(this.synchronizationMetadataEClass,
				SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION);
		createEAttribute(this.synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__HASH);
		createEAttribute(this.synchronizationMetadataEClass, SYNCHRONIZATION_METADATA__SYNC_STATE);
		createEAttribute(this.synchronizationMetadataEClass,
				SYNCHRONIZATION_METADATA__CURRENTLY_SYNCING);

		this.changeSetEClass = createEClass(CHANGE_SET);
		createEReference(this.changeSetEClass, CHANGE_SET__TARGET_CATEGORY);
		createEReference(this.changeSetEClass, CHANGE_SET__CHANGE_SET_ITEMS);
		createEReference(this.changeSetEClass, CHANGE_SET__REPOSITORY);

		this.changeSetItemEClass = createEClass(CHANGE_SET_ITEM);
		createEReference(this.changeSetItemEClass, CHANGE_SET_ITEM__REMOTE_CONVERTED_CONTAINER);
		createEReference(this.changeSetItemEClass, CHANGE_SET_ITEM__REMOTE_ORIGINAL_OBJECT);
		createEReference(this.changeSetItemEClass, CHANGE_SET_ITEM__LOCAL_CONTAINER);
		createEReference(this.changeSetItemEClass, CHANGE_SET_ITEM__SYNC_CATEGORY_ACTION_MAP);
		createEReference(this.changeSetItemEClass, CHANGE_SET_ITEM__SYNC_OBJECT_ACTION_MAP);
		createEReference(this.changeSetItemEClass, CHANGE_SET_ITEM__REMOTE_FULL_OBJECT_MAP);

		this.categoryToSynchronizationActionMapEClass = createEClass(CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP);
		createEReference(this.categoryToSynchronizationActionMapEClass,
				CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP__KEY);
		createEAttribute(this.categoryToSynchronizationActionMapEClass,
				CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP__VALUE);

		this.informationUnitListItemToInformationUnitMapEClass = createEClass(INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP);
		createEReference(this.informationUnitListItemToInformationUnitMapEClass,
				INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP__KEY);
		createEReference(this.informationUnitListItemToInformationUnitMapEClass,
				INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP__VALUE);

		this.synchronizableObjectToSynchronizationActionMapEClass = createEClass(SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP);
		createEReference(this.synchronizableObjectToSynchronizationActionMapEClass,
				SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP__KEY);
		createEAttribute(this.synchronizableObjectToSynchronizationActionMapEClass,
				SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP__VALUE);

		this.tagEClass = createEClass(TAG);
		createEAttribute(this.tagEClass, TAG__NAME);
		createEAttribute(this.tagEClass, TAG__INFO_UNITS);

		this.availableTagsEClass = createEClass(AVAILABLE_TAGS);
		createEReference(this.availableTagsEClass, AVAILABLE_TAGS__TAGS);

		this.synchronizableObjectEClass = createEClass(SYNCHRONIZABLE_OBJECT);
		createEReference(this.synchronizableObjectEClass,
				SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);

		this.calendarEntryEClass = createEClass(CALENDAR_ENTRY);
		createEAttribute(this.calendarEntryEClass, CALENDAR_ENTRY__ID);
		createEAttribute(this.calendarEntryEClass, CALENDAR_ENTRY__START);
		createEAttribute(this.calendarEntryEClass, CALENDAR_ENTRY__END);
		createEAttribute(this.calendarEntryEClass, CALENDAR_ENTRY__ENTRY_TYPE);
		createEAttribute(this.calendarEntryEClass, CALENDAR_ENTRY__REMINDER);
		createEAttribute(this.calendarEntryEClass, CALENDAR_ENTRY__TITLE);

		this.notificationEClass = createEClass(NOTIFICATION);
		createEAttribute(this.notificationEClass, NOTIFICATION__TIME_STAMP);
		createEAttribute(this.notificationEClass, NOTIFICATION__IMPORTANCE);
		createEAttribute(this.notificationEClass, NOTIFICATION__SEVERITY);
		createEAttribute(this.notificationEClass, NOTIFICATION__NOTICED);
		createEAttribute(this.notificationEClass, NOTIFICATION__MESSAGE);
		createEAttribute(this.notificationEClass, NOTIFICATION__DETAILS);
		createEReference(this.notificationEClass, NOTIFICATION__CHILDREN);
		createEAttribute(this.notificationEClass, NOTIFICATION__AFFECTED_INFO_UNIT_IDS);
		createEAttribute(this.notificationEClass, NOTIFICATION__SOURCE);
		createEAttribute(this.notificationEClass, NOTIFICATION__IMAGE);

		this.notificationCollectionEClass = createEClass(NOTIFICATION_COLLECTION);
		createEReference(this.notificationCollectionEClass, NOTIFICATION_COLLECTION__NOTIFCATIONS);

		this.stringToStringMapEClass = createEClass(STRING_TO_STRING_MAP);
		createEAttribute(this.stringToStringMapEClass, STRING_TO_STRING_MAP__KEY);
		createEAttribute(this.stringToStringMapEClass, STRING_TO_STRING_MAP__VALUE);

		this.informationStructureDefinitionEClass = createEClass(INFORMATION_STRUCTURE_DEFINITION);
		createEReference(this.informationStructureDefinitionEClass,
				INFORMATION_STRUCTURE_DEFINITION__STRUCTURE_POOL);

		this.informationStructureItemEClass = createEClass(INFORMATION_STRUCTURE_ITEM);
		createEAttribute(this.informationStructureItemEClass, INFORMATION_STRUCTURE_ITEM__ID);
		createEAttribute(this.informationStructureItemEClass,
				INFORMATION_STRUCTURE_ITEM__CREATE_ALWAYS);

		this.informationStructureEClass = createEClass(INFORMATION_STRUCTURE);
		createEAttribute(this.informationStructureEClass, INFORMATION_STRUCTURE__TYPE);
		createEReference(this.informationStructureEClass, INFORMATION_STRUCTURE__STRUCTURE_ITEMS);
		createEReference(this.informationStructureEClass,
				INFORMATION_STRUCTURE__REFERENCED_STRUCTURE_ITEMS);
		createEAttribute(this.informationStructureEClass,
				INFORMATION_STRUCTURE__CAN_HAVE_BINARY_REFERENCES);
		createEAttribute(this.informationStructureEClass, INFORMATION_STRUCTURE__LABEL);

		this.dynamicStructureEClass = createEClass(DYNAMIC_STRUCTURE);
		createEAttribute(this.dynamicStructureEClass, DYNAMIC_STRUCTURE__LOWER_BOUND);
		createEAttribute(this.dynamicStructureEClass, DYNAMIC_STRUCTURE__UPPER_BOUND);

		// Create enums
		this.synchronizationStateEEnum = createEEnum(SYNCHRONIZATION_STATE);
		this.synchronizationActionEEnum = createEEnum(SYNCHRONIZATION_ACTION);
		this.calendarEntryTypeEEnum = createEEnum(CALENDAR_ENTRY_TYPE);
		this.notificationImportanceEEnum = createEEnum(NOTIFICATION_IMPORTANCE);
		this.severityEEnum = createEEnum(SEVERITY);
		this.informationStructureTypeEEnum = createEEnum(INFORMATION_STRUCTURE_TYPE);

		// Create data types
		this.objectEDataType = createEDataType(OBJECT);
		this.iRepositoryEDataType = createEDataType(IREPOSITORY);
		this.transferWrapperEDataType = createEDataType(TRANSFER_WRAPPER);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
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
		if (this.isInitialized) {
			return;
		}
		this.isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		this.informationUnitEClass.getESuperTypes().add(getAbstractInformationUnit());
		this.categoryEClass.getESuperTypes().add(getSynchronizableObject());
		this.abstractInformationUnitEClass.getESuperTypes().add(getAdapter());
		this.informationUnitListItemEClass.getESuperTypes().add(getAbstractInformationUnit());
		this.informationUnitListItemEClass.getESuperTypes().add(getSynchronizableObject());
		this.ruleValueEClass.getESuperTypes().add(getInformationUnit());
		this.remoteRepositoryEClass.getESuperTypes().add(getRemoteContainer());
		this.remoteObjectEClass.getESuperTypes().add(getAdapter());
		this.remoteContainerEClass.getESuperTypes().add(getRemoteObject());
		this.synchronizationMetadataEClass.getESuperTypes().add(getAdapter());
		this.changeSetEClass.getESuperTypes().add(getAdapter());
		this.changeSetItemEClass.getESuperTypes().add(getAdapter());
		this.tagEClass.getESuperTypes().add(getAdapter());
		this.synchronizableObjectEClass.getESuperTypes().add(getAdapter());
		this.notificationEClass.getESuperTypes().add(getAdapter());
		this.informationStructureDefinitionEClass.getESuperTypes().add(getInformationStructure());
		this.informationStructureItemEClass.getESuperTypes().add(getInformationStructure());
		this.dynamicStructureEClass.getESuperTypes().add(getInformationStructureItem());

		// Initialize classes and features; add operations and parameters
		initEClass(this.informationUnitEClass, InformationUnit.class, "InformationUnit",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInformationUnit_StringValue(), this.ecorePackage.getEString(),
				"stringValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_LongValue(), this.ecorePackage.getELong(), "longValue",
				null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_BoolValue(), this.ecorePackage.getEBoolean(),
				"boolValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_BinaryValue(), this.ecorePackage.getEByteArray(),
				"binaryValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_DateValue(), this.ecorePackage.getEDate(), "dateValue",
				null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_DoubleValue(), this.ecorePackage.getEDouble(),
				"doubleValue", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_ChildValues(), getInformationUnit(), null, "childValues",
				null, 0, -1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getInformationUnit_References(), getInformationUnit(), null, "references",
				null, 0, -1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getInformationUnit_Links(), getLink(), null, "links", null, 0, -1,
				InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_CreationDate(), this.ecorePackage.getEDate(),
				"creationDate", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_UsageData(), getUsage(), null, "usageData", null, 0, 1,
				InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_Description(), this.ecorePackage.getEString(),
				"description", null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationUnit_Keywords(), this.ecorePackage.getEString(), "keywords",
				null, 0, 1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_CalendarEntry(), getCalendarEntry(), null,
				"calendarEntry", null, 0, -1, InformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnit_BinaryReferences(), getBinaryReference(), null,
				"binaryReferences", null, 0, -1, InformationUnit.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.binaryReferenceEClass, BinaryReference.class, "BinaryReference",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBinaryReference_Id(), this.ecorePackage.getEString(), "id", null, 1, 1,
				BinaryReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinaryReference_ProjectRelativePath(), this.ecorePackage.getEString(),
				"projectRelativePath", "", 1, 1, BinaryReference.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getBinaryReference_Dirty(), this.ecorePackage.getEBoolean(), "dirty", null,
				0, 1, BinaryReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.usageEClass, Usage.class, "Usage", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUsage_LastAccess(), this.ecorePackage.getEDate(), "lastAccess", null, 0,
				1, Usage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUsage_AccessCount(), this.ecorePackage.getEInt(), "accessCount", null, 0,
				1, Usage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.categoryEClass, Category.class, "Category", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCategory_Id(), this.ecorePackage.getEString(), "id", null, 1, 1,
				Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategory_Label(), this.ecorePackage.getEString(), "label", null, 0, 1,
				Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCategory_Children(), getCategory(), null, "children", null, 0, -1,
				Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCategory_InformationUnit(), getInformationUnitListItem(), null,
				"informationUnit", null, 0, -1, Category.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCategory_Description(), this.ecorePackage.getEString(), "description",
				null, 0, 1, Category.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.abstractInformationUnitEClass, AbstractInformationUnit.class,
				"AbstractInformationUnit", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAbstractInformationUnit_Id(), this.ecorePackage.getEString(), "id", null,
				1, 1, AbstractInformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractInformationUnit_Label(), this.ecorePackage.getEString(), "label",
				null, 0, 1, AbstractInformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAbstractInformationUnit_Type(), this.ecorePackage.getEString(), "type",
				null, 0, 1, AbstractInformationUnit.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.informationUnitListItemEClass, InformationUnitListItem.class,
				"InformationUnitListItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInformationUnitListItem_WorkspacePath(), this.ecorePackage.getEString(),
				"workspacePath", null, 0, 1, InformationUnitListItem.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(this.applicationRootEClass, ApplicationRoot.class, "ApplicationRoot",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplicationRoot_RootCategories(), getCategory(), null, "rootCategories",
				null, 0, -1, ApplicationRoot.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getApplicationRoot_AvailableTags(), getAvailableTags(), null,
				"availableTags", null, 1, 1, ApplicationRoot.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(this.annotationEClass, Annotation.class, "Annotation", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAnnotation_Description(), this.ecorePackage.getEString(), "description",
				null, 0, 1, Annotation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.linkEClass, Link.class, "Link", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLink_Target(), getInformationUnit(), null, "target", null, 1, 1,
				Link.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.adapterEClass, Adapter.class, "Adapter", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(this.recentlyUsedKeywordsEClass, RecentlyUsedKeywords.class,
				"RecentlyUsedKeywords", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRecentlyUsedKeywords_Maxlength(), this.ecorePackage.getEInt(),
				"maxlength", "100", 0, 1, RecentlyUsedKeywords.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRecentlyUsedKeywords_Keywords(), this.ecorePackage.getEString(),
				"keywords", null, 0, -1, RecentlyUsedKeywords.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.newElementRulesEClass, NewElementRules.class, "NewElementRules",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNewElementRules_TransferTypes(), getRemusTransferType(), null,
				"transferTypes", null, 0, -1, NewElementRules.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getNewElementRules_Name(), this.ecorePackage.getEString(), "name", null, 1,
				1, NewElementRules.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNewElementRules_Deletable(), this.ecorePackage.getEBoolean(),
				"deletable", null, 0, 1, NewElementRules.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.ruleValueEClass, RuleValue.class, "RuleValue", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(this.availableRuleDefinitionsEClass, AvailableRuleDefinitions.class,
				"AvailableRuleDefinitions", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAvailableRuleDefinitions_NewElementRules(), getNewElementRules(), null,
				"newElementRules", null, 0, -1, AvailableRuleDefinitions.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.remusTransferTypeEClass, RemusTransferType.class, "RemusTransferType",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRemusTransferType_Name(), this.ecorePackage.getEString(), "name", null,
				0, 1, RemusTransferType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemusTransferType_Id(), this.ecorePackage.getEString(), "id", null, 1, 1,
				RemusTransferType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRemusTransferType_Actions(), getRuleAction(), null, "actions", null, 0,
				-1, RemusTransferType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(this.ruleActionEClass, RuleAction.class, "RuleAction", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleAction_Name(), this.ecorePackage.getEString(), "name", null, 0, 1,
				RuleAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleAction_InfoTypeId(), this.ecorePackage.getEString(), "infoTypeId",
				null, 1, 1, RuleAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleAction_RuleValue(), getRuleValue(), null, "ruleValue", null, 0, 1,
				RuleAction.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleAction_GroovyMatcher(), this.ecorePackage.getEString(),
				"groovyMatcher", "return true", 0, 1, RuleAction.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getRuleAction_PostProcessingInstructions(), this.ecorePackage.getEString(),
				"postProcessingInstructions", null, 0, 1, RuleAction.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(this.ruleResultEClass, RuleResult.class, "RuleResult", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleResult_Value(), getObject(), "value", null, 1, 1, RuleResult.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEReference(getRuleResult_Actions(), getRuleAction(), null, "actions", null, 1, -1,
				RuleResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleResult_Description(), this.ecorePackage.getEString(), "description",
				null, 0, 1, RuleResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleResult_TransferType(), getTransferWrapper(), "transferType", null, 0,
				1, RuleResult.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.remoteRepositoryEClass, RemoteRepository.class, "RemoteRepository",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRemoteRepository_Options(), getStringToStringMap(), null, "options",
				null, 0, -1, RemoteRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		addEOperation(this.remoteRepositoryEClass, getIRepository(), "getRepositoryImplementation",
				0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(this.remoteObjectEClass, RemoteObject.class, "RemoteObject", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRemoteObject_PossibleInfoTypeId(), this.ecorePackage.getEString(),
				"possibleInfoTypeId", null, 0, -1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_Id(), this.ecorePackage.getEString(), "id", null, 0, 1,
				RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_Url(), this.ecorePackage.getEString(), "url", null, 1, 1,
				RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_Name(), this.ecorePackage.getEString(), "name", null, 0, 1,
				RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_RepositoryTypeId(), this.ecorePackage.getEString(),
				"repositoryTypeId", null, 1, 1, RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_RepositoryTypeObjectId(), this.ecorePackage.getEString(),
				"repositoryTypeObjectId", null, 1, 1, RemoteObject.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getRemoteObject_WrappedObject(), getObject(), "wrappedObject", null, 0, 1,
				RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRemoteObject_Hash(), this.ecorePackage.getEString(), "hash", null, 0, 1,
				RemoteObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.remoteContainerEClass, RemoteContainer.class, "RemoteContainer",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRemoteContainer_Children(), getRemoteObject(), null, "children", null, 0,
				-1, RemoteContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getRemoteContainer_ExclusionChildren(), getRemoteObject(), null,
				"exclusionChildren", null, 0, -1, RemoteContainer.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.repositoryCollectionEClass, RepositoryCollection.class,
				"RepositoryCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRepositoryCollection_Repositories(), getRemoteRepository(), null,
				"repositories", null, 0, -1, RepositoryCollection.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.synchronizationMetadataEClass, SynchronizationMetadata.class,
				"SynchronizationMetadata", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSynchronizationMetadata_RepositoryId(), this.ecorePackage.getEString(),
				"repositoryId", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_Url(), this.ecorePackage.getEString(), "url",
				null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_Readonly(), this.ecorePackage.getEBoolean(),
				"readonly", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_LastSynchronisation(), this.ecorePackage
				.getEDate(), "lastSynchronisation", null, 0, 1, SynchronizationMetadata.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_Hash(), this.ecorePackage.getEString(), "hash",
				null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_SyncState(), getSynchronizationState(),
				"syncState", null, 0, 1, SynchronizationMetadata.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getSynchronizationMetadata_CurrentlySyncing(), this.ecorePackage
				.getEBoolean(), "currentlySyncing", "false", 0, 1, SynchronizationMetadata.class,
				IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(this.changeSetEClass, ChangeSet.class, "ChangeSet", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChangeSet_TargetCategory(), getCategory(), null, "targetCategory", null,
				1, 1, ChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSet_ChangeSetItems(), getChangeSetItem(), null, "changeSetItems",
				null, 0, -1, ChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getChangeSet_Repository(), getRemoteRepository(), null, "repository", null,
				1, 1, ChangeSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.changeSetItemEClass, ChangeSetItem.class, "ChangeSetItem", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChangeSetItem_RemoteConvertedContainer(), getCategory(), null,
				"remoteConvertedContainer", null, 0, 1, ChangeSetItem.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSetItem_RemoteOriginalObject(), getRemoteContainer(), null,
				"remoteOriginalObject", null, 0, 1, ChangeSetItem.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSetItem_LocalContainer(), getCategory(), null, "localContainer",
				null, 0, 1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getChangeSetItem_SyncCategoryActionMap(),
				getCategoryToSynchronizationActionMap(), null, "syncCategoryActionMap", null, 0,
				-1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangeSetItem_SyncObjectActionMap(),
				getSynchronizableObjectToSynchronizationActionMap(), null, "syncObjectActionMap",
				null, 0, -1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getChangeSetItem_RemoteFullObjectMap(),
				getInformationUnitListItemToInformationUnitMap(), null, "remoteFullObjectMap",
				null, 0, -1, ChangeSetItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(this.categoryToSynchronizationActionMapEClass, Map.Entry.class,
				"CategoryToSynchronizationActionMap", !IS_ABSTRACT, !IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCategoryToSynchronizationActionMap_Key(), getCategory(), null, "key",
				null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getCategoryToSynchronizationActionMap_Value(), getSynchronizationAction(),
				"value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.informationUnitListItemToInformationUnitMapEClass, Map.Entry.class,
				"InformationUnitListItemToInformationUnitMap", !IS_ABSTRACT, !IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInformationUnitListItemToInformationUnitMap_Key(),
				getInformationUnitListItem(), null, "key", null, 0, 1, Map.Entry.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationUnitListItemToInformationUnitMap_Value(),
				getInformationUnit(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.synchronizableObjectToSynchronizationActionMapEClass, Map.Entry.class,
				"SynchronizableObjectToSynchronizationActionMap", !IS_ABSTRACT, !IS_INTERFACE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSynchronizableObjectToSynchronizationActionMap_Key(),
				getSynchronizableObject(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSynchronizableObjectToSynchronizationActionMap_Value(),
				getSynchronizationAction(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(this.tagEClass, Tag.class, "Tag", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTag_Name(), this.ecorePackage.getEString(), "name", null, 0, 1,
				Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTag_InfoUnits(), this.ecorePackage.getEString(), "infoUnits", null, 0,
				-1, Tag.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.availableTagsEClass, AvailableTags.class, "AvailableTags", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAvailableTags_Tags(), getTag(), null, "tags", null, 0, -1,
				AvailableTags.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(this.synchronizableObjectEClass, SynchronizableObject.class,
				"SynchronizableObject", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSynchronizableObject_SynchronizationMetaData(),
				getSynchronizationMetadata(), null, "synchronizationMetaData", null, 0, 1,
				SynchronizableObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(this.calendarEntryEClass, CalendarEntry.class, "CalendarEntry", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCalendarEntry_Id(), this.ecorePackage.getEString(), "id", null, 1, 1,
				CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_Start(), this.ecorePackage.getEDate(), "start", null, 1, 1,
				CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_End(), this.ecorePackage.getEDate(), "end", null, 1, 1,
				CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_EntryType(), getCalendarEntryType(), "entryType", null, 0,
				1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_Reminder(), this.ecorePackage.getEInt(), "reminder", null,
				0, 1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendarEntry_Title(), this.ecorePackage.getEString(), "title", null, 0,
				1, CalendarEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.notificationEClass, Notification.class, "Notification", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNotification_TimeStamp(), this.ecorePackage.getEDate(), "timeStamp",
				null, 0, 1, Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNotification_Importance(), getNotificationImportance(), "importance",
				null, 0, 1, Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNotification_Severity(), getSeverity(), "severity", null, 0, 1,
				Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNotification_Noticed(), this.ecorePackage.getEBoolean(), "noticed", null,
				0, 1, Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNotification_Message(), this.ecorePackage.getEString(), "message", null,
				0, 1, Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNotification_Details(), this.ecorePackage.getEString(), "details", null,
				0, 1, Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNotification_Children(), getNotification(), null, "children", null, 0,
				-1, Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNotification_AffectedInfoUnitIds(), this.ecorePackage.getEString(),
				"affectedInfoUnitIds", null, 0, -1, Notification.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getNotification_Source(), this.ecorePackage.getEString(), "source", null, 0,
				1, Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNotification_Image(), getObject(), "image", null, 0, 1,
				Notification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.notificationCollectionEClass, NotificationCollection.class,
				"NotificationCollection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNotificationCollection_Notifcations(), getNotification(), null,
				"notifcations", null, 0, -1, NotificationCollection.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.stringToStringMapEClass, Map.Entry.class, "StringToStringMap",
				!IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringToStringMap_Key(), this.ecorePackage.getEString(), "key", null, 0,
				1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStringToStringMap_Value(), this.ecorePackage.getEString(), "value", null,
				0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.informationStructureDefinitionEClass, InformationStructureDefinition.class,
				"InformationStructureDefinition", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInformationStructureDefinition_StructurePool(),
				getInformationStructureItem(), null, "structurePool", null, 0, -1,
				InformationStructureDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(this.informationStructureItemEClass, InformationStructureItem.class,
				"InformationStructureItem", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInformationStructureItem_Id(), this.ecorePackage.getEString(), "id",
				null, 1, 1, InformationStructureItem.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationStructureItem_CreateAlways(), this.ecorePackage.getEBoolean(),
				"createAlways", null, 0, 1, InformationStructureItem.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		initEClass(this.informationStructureEClass, InformationStructure.class,
				"InformationStructure", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInformationStructure_Type(), getInformationStructureType(), "type", null,
				0, 1, InformationStructure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationStructure_StructureItems(), getInformationStructureItem(),
				null, "structureItems", null, 0, -1, InformationStructure.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getInformationStructure_ReferencedStructureItems(),
				getInformationStructureItem(), null, "referencedStructureItems", null, 0, -1,
				InformationStructure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getInformationStructure_CanHaveBinaryReferences(), this.ecorePackage
				.getEBoolean(), "canHaveBinaryReferences", null, 0, 1, InformationStructure.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getInformationStructure_Label(), this.ecorePackage.getEString(), "label",
				null, 0, 1, InformationStructure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(this.dynamicStructureEClass, DynamicStructure.class, "DynamicStructure",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDynamicStructure_LowerBound(), this.ecorePackage.getEInt(), "lowerBound",
				null, 1, 1, DynamicStructure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDynamicStructure_UpperBound(), this.ecorePackage.getEInt(), "upperBound",
				null, 1, 1, DynamicStructure.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(this.synchronizationStateEEnum, SynchronizationState.class,
				"SynchronizationState");
		addEEnumLiteral(this.synchronizationStateEEnum, SynchronizationState.LOCAL_DELETED);
		addEEnumLiteral(this.synchronizationStateEEnum, SynchronizationState.TARGET_DELETED);
		addEEnumLiteral(this.synchronizationStateEEnum, SynchronizationState.NOT_ADDED);
		addEEnumLiteral(this.synchronizationStateEEnum, SynchronizationState.LOCAL_EDITED);
		addEEnumLiteral(this.synchronizationStateEEnum, SynchronizationState.TARGET_EDITED);
		addEEnumLiteral(this.synchronizationStateEEnum, SynchronizationState.IN_SYNC);
		addEEnumLiteral(this.synchronizationStateEEnum, SynchronizationState.IGNORED);

		initEEnum(this.synchronizationActionEEnum, SynchronizationAction.class,
				"SynchronizationAction");
		addEEnumLiteral(this.synchronizationActionEEnum, SynchronizationAction.REPLACE_LOCAL);
		addEEnumLiteral(this.synchronizationActionEEnum, SynchronizationAction.REPLACE_REMOTE);
		addEEnumLiteral(this.synchronizationActionEEnum, SynchronizationAction.DELETE_LOCAL);
		addEEnumLiteral(this.synchronizationActionEEnum, SynchronizationAction.DELETE_REMOTE);
		addEEnumLiteral(this.synchronizationActionEEnum, SynchronizationAction.ADD_LOCAL);
		addEEnumLiteral(this.synchronizationActionEEnum, SynchronizationAction.ADD_REMOTE);
		addEEnumLiteral(this.synchronizationActionEEnum, SynchronizationAction.RESOLVE_CONFLICT);

		initEEnum(this.calendarEntryTypeEEnum, CalendarEntryType.class, "CalendarEntryType");
		addEEnumLiteral(this.calendarEntryTypeEEnum, CalendarEntryType.ONE_TIME);
		addEEnumLiteral(this.calendarEntryTypeEEnum, CalendarEntryType.WEEKLY);
		addEEnumLiteral(this.calendarEntryTypeEEnum, CalendarEntryType.TWO_WEEK);
		addEEnumLiteral(this.calendarEntryTypeEEnum, CalendarEntryType.MONTHLY);
		addEEnumLiteral(this.calendarEntryTypeEEnum, CalendarEntryType.ANNUAL);

		initEEnum(this.notificationImportanceEEnum, NotificationImportance.class,
				"NotificationImportance");
		addEEnumLiteral(this.notificationImportanceEEnum, NotificationImportance.NONE);
		addEEnumLiteral(this.notificationImportanceEEnum, NotificationImportance.LOW);
		addEEnumLiteral(this.notificationImportanceEEnum, NotificationImportance.MEDIUM);
		addEEnumLiteral(this.notificationImportanceEEnum, NotificationImportance.HIGH);

		initEEnum(this.severityEEnum, Severity.class, "Severity");
		addEEnumLiteral(this.severityEEnum, Severity.OK);
		addEEnumLiteral(this.severityEEnum, Severity.INFO);
		addEEnumLiteral(this.severityEEnum, Severity.WARNING);
		addEEnumLiteral(this.severityEEnum, Severity.ERROR);

		initEEnum(this.informationStructureTypeEEnum, InformationStructureType.class,
				"InformationStructureType");
		addEEnumLiteral(this.informationStructureTypeEEnum, InformationStructureType.NONE);
		addEEnumLiteral(this.informationStructureTypeEEnum, InformationStructureType.STRING);
		addEEnumLiteral(this.informationStructureTypeEEnum, InformationStructureType.DATE);
		addEEnumLiteral(this.informationStructureTypeEEnum, InformationStructureType.LONG);
		addEEnumLiteral(this.informationStructureTypeEEnum, InformationStructureType.BOOLEAN);
		addEEnumLiteral(this.informationStructureTypeEEnum, InformationStructureType.BINARY);
		addEEnumLiteral(this.informationStructureTypeEEnum, InformationStructureType.DOUBLE);

		// Initialize data types
		initEDataType(this.objectEDataType, Object.class, "Object", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);
		initEDataType(this.iRepositoryEDataType, IRepository.class, "IRepository",
				!IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(this.transferWrapperEDataType, TransferWrapper.class, "TransferWrapper",
				!IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for
	 * <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";
		addAnnotation(getInformationUnit_BinaryValue(), source, new String[] { "name", "value",
				"kind", "element" });
	}

} // InfomngmntPackageImpl
