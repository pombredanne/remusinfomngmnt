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
package org.remus.infomngmnt.util;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.*;
import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Adapter;
import org.remus.infomngmnt.Annotation;
import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.AvailableRuleDefinitions;
import org.remus.infomngmnt.AvailableTags;
import org.remus.infomngmnt.CalenderEntry;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
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
import org.remus.infomngmnt.Tag;
import org.remus.infomngmnt.Usage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance
 * hierarchy. It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the
 * result of the switch. <!-- end-user-doc -->
 * @see org.remus.infomngmnt.InfomngmntPackage
 * @generated
 */
public class InfomngmntSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static InfomngmntPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public InfomngmntSwitch() {
		if (modelPackage == null) {
			modelPackage = InfomngmntPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case InfomngmntPackage.INFORMATION_UNIT: {
				InformationUnit informationUnit = (InformationUnit)theEObject;
				T result = caseInformationUnit(informationUnit);
				if (result == null) result = caseAbstractInformationUnit(informationUnit);
				if (result == null) result = caseAdapter(informationUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.USAGE: {
				Usage usage = (Usage)theEObject;
				T result = caseUsage(usage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.CATEGORY: {
				Category category = (Category)theEObject;
				T result = caseCategory(category);
				if (result == null) result = caseSynchronizableObject(category);
				if (result == null) result = caseAdapter(category);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.ABSTRACT_INFORMATION_UNIT: {
				AbstractInformationUnit abstractInformationUnit = (AbstractInformationUnit)theEObject;
				T result = caseAbstractInformationUnit(abstractInformationUnit);
				if (result == null) result = caseAdapter(abstractInformationUnit);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM: {
				InformationUnitListItem informationUnitListItem = (InformationUnitListItem)theEObject;
				T result = caseInformationUnitListItem(informationUnitListItem);
				if (result == null) result = caseAbstractInformationUnit(informationUnitListItem);
				if (result == null) result = caseSynchronizableObject(informationUnitListItem);
				if (result == null) result = caseAdapter(informationUnitListItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.APPLICATION_ROOT: {
				ApplicationRoot applicationRoot = (ApplicationRoot)theEObject;
				T result = caseApplicationRoot(applicationRoot);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.ANNOTATION: {
				Annotation annotation = (Annotation)theEObject;
				T result = caseAnnotation(annotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.LINK: {
				Link link = (Link)theEObject;
				T result = caseLink(link);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.ADAPTER: {
				Adapter adapter = (Adapter)theEObject;
				T result = caseAdapter(adapter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.RECENTLY_USED_KEYWORDS: {
				RecentlyUsedKeywords recentlyUsedKeywords = (RecentlyUsedKeywords)theEObject;
				T result = caseRecentlyUsedKeywords(recentlyUsedKeywords);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.NEW_ELEMENT_RULES: {
				NewElementRules newElementRules = (NewElementRules)theEObject;
				T result = caseNewElementRules(newElementRules);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.RULE_VALUE: {
				RuleValue ruleValue = (RuleValue)theEObject;
				T result = caseRuleValue(ruleValue);
				if (result == null) result = caseInformationUnit(ruleValue);
				if (result == null) result = caseAbstractInformationUnit(ruleValue);
				if (result == null) result = caseAdapter(ruleValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.AVAILABLE_RULE_DEFINITIONS: {
				AvailableRuleDefinitions availableRuleDefinitions = (AvailableRuleDefinitions)theEObject;
				T result = caseAvailableRuleDefinitions(availableRuleDefinitions);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.REMUS_TRANSFER_TYPE: {
				RemusTransferType remusTransferType = (RemusTransferType)theEObject;
				T result = caseRemusTransferType(remusTransferType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.RULE_ACTION: {
				RuleAction ruleAction = (RuleAction)theEObject;
				T result = caseRuleAction(ruleAction);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.RULE_RESULT: {
				RuleResult ruleResult = (RuleResult)theEObject;
				T result = caseRuleResult(ruleResult);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.REMOTE_REPOSITORY: {
				RemoteRepository remoteRepository = (RemoteRepository)theEObject;
				T result = caseRemoteRepository(remoteRepository);
				if (result == null) result = caseRemoteContainer(remoteRepository);
				if (result == null) result = caseRemoteObject(remoteRepository);
				if (result == null) result = caseAdapter(remoteRepository);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.REMOTE_OBJECT: {
				RemoteObject remoteObject = (RemoteObject)theEObject;
				T result = caseRemoteObject(remoteObject);
				if (result == null) result = caseAdapter(remoteObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.REMOTE_CONTAINER: {
				RemoteContainer remoteContainer = (RemoteContainer)theEObject;
				T result = caseRemoteContainer(remoteContainer);
				if (result == null) result = caseRemoteObject(remoteContainer);
				if (result == null) result = caseAdapter(remoteContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.REPOSITORY_COLLECTION: {
				RepositoryCollection repositoryCollection = (RepositoryCollection)theEObject;
				T result = caseRepositoryCollection(repositoryCollection);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.SYNCHRONIZATION_METADATA: {
				SynchronizationMetadata synchronizationMetadata = (SynchronizationMetadata)theEObject;
				T result = caseSynchronizationMetadata(synchronizationMetadata);
				if (result == null) result = caseAdapter(synchronizationMetadata);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.CHANGE_SET: {
				ChangeSet changeSet = (ChangeSet)theEObject;
				T result = caseChangeSet(changeSet);
				if (result == null) result = caseAdapter(changeSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.CHANGE_SET_ITEM: {
				ChangeSetItem changeSetItem = (ChangeSetItem)theEObject;
				T result = caseChangeSetItem(changeSetItem);
				if (result == null) result = caseAdapter(changeSetItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.CATEGORY_TO_SYNCHRONIZATION_ACTION_MAP: {
				@SuppressWarnings("unchecked") Map.Entry<Category, SynchronizationAction> categoryToSynchronizationActionMap = (Map.Entry<Category, SynchronizationAction>)theEObject;
				T result = caseCategoryToSynchronizationActionMap(categoryToSynchronizationActionMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.INFORMATION_UNIT_LIST_ITEM_TO_INFORMATION_UNIT_MAP: {
				@SuppressWarnings("unchecked") Map.Entry<InformationUnitListItem, InformationUnit> informationUnitListItemToInformationUnitMap = (Map.Entry<InformationUnitListItem, InformationUnit>)theEObject;
				T result = caseInformationUnitListItemToInformationUnitMap(informationUnitListItemToInformationUnitMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT_TO_SYNCHRONIZATION_ACTION_MAP: {
				@SuppressWarnings("unchecked") Map.Entry<SynchronizableObject, SynchronizationAction> synchronizableObjectToSynchronizationActionMap = (Map.Entry<SynchronizableObject, SynchronizationAction>)theEObject;
				T result = caseSynchronizableObjectToSynchronizationActionMap(synchronizableObjectToSynchronizationActionMap);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.TAG: {
				Tag tag = (Tag)theEObject;
				T result = caseTag(tag);
				if (result == null) result = caseAdapter(tag);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.AVAILABLE_TAGS: {
				AvailableTags availableTags = (AvailableTags)theEObject;
				T result = caseAvailableTags(availableTags);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.SYNCHRONIZABLE_OBJECT: {
				SynchronizableObject synchronizableObject = (SynchronizableObject)theEObject;
				T result = caseSynchronizableObject(synchronizableObject);
				if (result == null) result = caseAdapter(synchronizableObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case InfomngmntPackage.CALENDER_ENTRY: {
				CalenderEntry calenderEntry = (CalenderEntry)theEObject;
				T result = caseCalenderEntry(calenderEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Information Unit</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Information Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInformationUnit(InformationUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Usage</em>'.
	 * <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Usage</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUsage(Usage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Category</em>'.
	 * <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Category</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCategory(Category object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Information Unit</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Information Unit</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractInformationUnit(AbstractInformationUnit object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Information Unit List Item</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Information Unit List Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInformationUnitListItem(InformationUnitListItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Application Root</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Application Root</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseApplicationRoot(ApplicationRoot object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotation</em>'.
	 * <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnnotation(Annotation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '
	 * <em>Link</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '
	 *         <em>Link</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLink(Link object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Adapter</em>'.
	 * <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Adapter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAdapter(Adapter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Recently Used Keywords</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Recently Used Keywords</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRecentlyUsedKeywords(RecentlyUsedKeywords object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>New Element Rules</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>New Element Rules</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNewElementRules(NewElementRules object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Value</em>'.
	 * <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleValue(RuleValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Available Rule Definitions</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Available Rule Definitions</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAvailableRuleDefinitions(AvailableRuleDefinitions object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remus Transfer Type</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remus Transfer Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRemusTransferType(RemusTransferType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Action</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Action</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleAction(RuleAction object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Result</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuleResult(RuleResult object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remote Repository</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remote Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRemoteRepository(RemoteRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remote Object</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remote Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRemoteObject(RemoteObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Remote Container</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Remote Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRemoteContainer(RemoteContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Repository Collection</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Repository Collection</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRepositoryCollection(RepositoryCollection object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Synchronization Metadata</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Synchronization Metadata</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSynchronizationMetadata(SynchronizationMetadata object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Change Set</em>'.
	 * <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Change Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangeSet(ChangeSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Change Set Item</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Change Set Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseChangeSetItem(ChangeSetItem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Category To Synchronization Action Map</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null; returning a non-null result will
	 * terminate the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Category To Synchronization Action Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCategoryToSynchronizationActionMap(
			Map.Entry<Category, SynchronizationAction> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '
	 * <em>Information Unit List Item To Information Unit Map</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null
	 * result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '
	 *         <em>Information Unit List Item To Information Unit Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInformationUnitListItemToInformationUnitMap(
			Map.Entry<InformationUnitListItem, InformationUnit> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '
	 * <em>Synchronizable Object To Synchronization Action Map</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null
	 * result will terminate the switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '
	 *         <em>Synchronizable Object To Synchronization Action Map</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSynchronizableObjectToSynchronizationActionMap(
			Map.Entry<SynchronizableObject, SynchronizationAction> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '
	 * <em>Tag</em>'. <!-- begin-user-doc --> This implementation returns null;
	 * returning a non-null result will terminate the switch. <!-- end-user-doc
	 * -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '
	 *         <em>Tag</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTag(Tag object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Available Tags</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Available Tags</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAvailableTags(AvailableTags object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Synchronizable Object</em>'.
	 * <!-- begin-user-doc --> This
	 * implementation returns null; returning a non-null result will terminate
	 * the switch. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Synchronizable Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSynchronizableObject(SynchronizableObject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Calender Entry</em>'.
	 * <!-- begin-user-doc --> This implementation
	 * returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Calender Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCalenderEntry(CalenderEntry object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc --> This implementation returns
	 * null; returning a non-null result will terminate the switch, but this is
	 * the last case anyway. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} // InfomngmntSwitch
