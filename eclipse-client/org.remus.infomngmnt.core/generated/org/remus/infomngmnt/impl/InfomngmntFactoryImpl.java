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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.remus.infomngmnt.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InfomngmntFactoryImpl extends EFactoryImpl implements InfomngmntFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static InfomngmntFactory init() {
		try {
			InfomngmntFactory theInfomngmntFactory = (InfomngmntFactory)EPackage.Registry.INSTANCE.getEFactory("http://remus-software.org/infomngmnt/1.0"); 
			if (theInfomngmntFactory != null) {
				return theInfomngmntFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InfomngmntFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfomngmntFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case InfomngmntPackage.INFORMATION_UNIT: return createInformationUnit();
			case InfomngmntPackage.USAGE: return createUsage();
			case InfomngmntPackage.CATEGORY: return createCategory();
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM: return createInformationUnitListItem();
			case InfomngmntPackage.APPLICATION_ROOT: return createApplicationRoot();
			case InfomngmntPackage.ANNOTATION: return createAnnotation();
			case InfomngmntPackage.LINK: return createLink();
			case InfomngmntPackage.LINK_TYPE: return createLinkType();
			case InfomngmntPackage.LINK_TYPE_COLLECTION: return createLinkTypeCollection();
			case InfomngmntPackage.STRING_TO_LINK_TYPE_MAP: return (EObject)createStringToLinkTypeMap();
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS: return createRecentlyUsedKeywords();
			case InfomngmntPackage.NEW_ELEMENT_RULES: return createNewElementRules();
			case InfomngmntPackage.RULE_VALUE: return createRuleValue();
			case InfomngmntPackage.AVAILABLE_RULE_DEFINITIONS: return createAvailableRuleDefinitions();
			case InfomngmntPackage.REMUS_TRANSFER_TYPE: return createRemusTransferType();
			case InfomngmntPackage.RULE_ACTION: return createRuleAction();
			case InfomngmntPackage.RULE_RESULT: return createRuleResult();
			case InfomngmntPackage.REMOTE_REPOSITORY: return createRemoteRepository();
			case InfomngmntPackage.REMOTE_OBJECT: return createRemoteObject();
			case InfomngmntPackage.REMOTE_CONTAINER: return createRemoteContainer();
			case InfomngmntPackage.REPOSITORY_COLLECTION: return createRepositoryCollection();
			case InfomngmntPackage.SYNCHRONIZATION_METADATA: return createSynchronizationMetadata();
			case InfomngmntPackage.CHANGE_SET: return createChangeSet();
			case InfomngmntPackage.CHANGE_SET_ITEM: return createChangeSetItem();
			case InfomngmntPackage.CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP: return (EObject)createCategoryToSynchronizationActionMap();
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP: return (EObject)createInformationUnitListItemToInformationUnitMap();
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP: return (EObject)createSynchronizableObjectToSynchronizationActionMap();
			case InfomngmntPackage.TAG: return createTag();
			case InfomngmntPackage.AVAILABLE_TAGS: return createAvailableTags();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case InfomngmntPackage.SYNCHRONIZATION_STATE:
				return createSynchronizationStateFromString(eDataType, initialValue);
			case InfomngmntPackage.SYNCHRONIZATION_ACTION:
				return createSynchronizationActionFromString(eDataType, initialValue);
			case InfomngmntPackage.OBJECT:
				return createObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case InfomngmntPackage.SYNCHRONIZATION_STATE:
				return convertSynchronizationStateToString(eDataType, instanceValue);
			case InfomngmntPackage.SYNCHRONIZATION_ACTION:
				return convertSynchronizationActionToString(eDataType, instanceValue);
			case InfomngmntPackage.OBJECT:
				return convertObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InformationUnit createInformationUnit() {
		InformationUnitImpl informationUnit = new InformationUnitImpl();
		return informationUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Usage createUsage() {
		UsageImpl usage = new UsageImpl();
		return usage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Category createCategory() {
		CategoryImpl category = new CategoryImpl();
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InformationUnitListItem createInformationUnitListItem() {
		InformationUnitListItemImpl informationUnitListItem = new InformationUnitListItemImpl();
		return informationUnitListItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationRoot createApplicationRoot() {
		ApplicationRootImpl applicationRoot = new ApplicationRootImpl();
		return applicationRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Annotation createAnnotation() {
		AnnotationImpl annotation = new AnnotationImpl();
		return annotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Link createLink() {
		LinkImpl link = new LinkImpl();
		return link;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkType createLinkType() {
		LinkTypeImpl linkType = new LinkTypeImpl();
		return linkType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkTypeCollection createLinkTypeCollection() {
		LinkTypeCollectionImpl linkTypeCollection = new LinkTypeCollectionImpl();
		return linkTypeCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, LinkType> createStringToLinkTypeMap() {
		StringToLinkTypeMapImpl stringToLinkTypeMap = new StringToLinkTypeMapImpl();
		return stringToLinkTypeMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RecentlyUsedKeywords createRecentlyUsedKeywords() {
		RecentlyUsedKeywordsImpl recentlyUsedKeywords = new RecentlyUsedKeywordsImpl();
		return recentlyUsedKeywords;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NewElementRules createNewElementRules() {
		NewElementRulesImpl newElementRules = new NewElementRulesImpl();
		return newElementRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleValue createRuleValue() {
		RuleValueImpl ruleValue = new RuleValueImpl();
		return ruleValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AvailableRuleDefinitions createAvailableRuleDefinitions() {
		AvailableRuleDefinitionsImpl availableRuleDefinitions = new AvailableRuleDefinitionsImpl();
		return availableRuleDefinitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemusTransferType createRemusTransferType() {
		RemusTransferTypeImpl remusTransferType = new RemusTransferTypeImpl();
		return remusTransferType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleAction createRuleAction() {
		RuleActionImpl ruleAction = new RuleActionImpl();
		return ruleAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleResult createRuleResult() {
		RuleResultImpl ruleResult = new RuleResultImpl();
		return ruleResult;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoteRepository createRemoteRepository() {
		RemoteRepositoryImpl remoteRepository = new RemoteRepositoryImpl();
		return remoteRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoteObject createRemoteObject() {
		RemoteObjectImpl remoteObject = new RemoteObjectImpl();
		return remoteObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RemoteContainer createRemoteContainer() {
		RemoteContainerImpl remoteContainer = new RemoteContainerImpl();
		return remoteContainer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RepositoryCollection createRepositoryCollection() {
		RepositoryCollectionImpl repositoryCollection = new RepositoryCollectionImpl();
		return repositoryCollection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynchronizationMetadata createSynchronizationMetadata() {
		SynchronizationMetadataImpl synchronizationMetadata = new SynchronizationMetadataImpl();
		return synchronizationMetadata;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeSet createChangeSet() {
		ChangeSetImpl changeSet = new ChangeSetImpl();
		return changeSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChangeSetItem createChangeSetItem() {
		ChangeSetItemImpl changeSetItem = new ChangeSetItemImpl();
		return changeSetItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Category, SynchronizationAction> createCategoryToSynchronizationActionMap() {
		CategoryToSynchronizationActionMapImpl categoryToSynchronizationActionMap = new CategoryToSynchronizationActionMapImpl();
		return categoryToSynchronizationActionMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<InformationUnitListItem, InformationUnit> createInformationUnitListItemToInformationUnitMap() {
		InformationUnitListItemToInformationUnitMapImpl informationUnitListItemToInformationUnitMap = new InformationUnitListItemToInformationUnitMapImpl();
		return informationUnitListItemToInformationUnitMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<SynchronizableObject, SynchronizationAction> createSynchronizableObjectToSynchronizationActionMap() {
		SynchronizableObjectToSynchronizationActionMapImpl synchronizableObjectToSynchronizationActionMap = new SynchronizableObjectToSynchronizationActionMapImpl();
		return synchronizableObjectToSynchronizationActionMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tag createTag() {
		TagImpl tag = new TagImpl();
		return tag;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AvailableTags createAvailableTags() {
		AvailableTagsImpl availableTags = new AvailableTagsImpl();
		return availableTags;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynchronizationState createSynchronizationStateFromString(EDataType eDataType, String initialValue) {
		SynchronizationState result = SynchronizationState.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSynchronizationStateToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynchronizationAction createSynchronizationActionFromString(EDataType eDataType, String initialValue) {
		SynchronizationAction result = SynchronizationAction.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSynchronizationActionToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object createObjectFromString(EDataType eDataType, String initialValue) {
		return super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertObjectToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfomngmntPackage getInfomngmntPackage() {
		return (InfomngmntPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InfomngmntPackage getPackage() {
		return InfomngmntPackage.eINSTANCE;
	}

} //InfomngmntFactoryImpl
