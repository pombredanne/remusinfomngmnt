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

import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.InfomngmntPackage
 * @generated
 */
public class InfomngmntAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static InfomngmntPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InfomngmntAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = InfomngmntPackage.eINSTANCE;
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
	protected InfomngmntSwitch<Adapter> modelSwitch =
		new InfomngmntSwitch<Adapter>() {
			@Override
			public Adapter caseInformationUnit(InformationUnit object) {
				return createInformationUnitAdapter();
			}
			@Override
			public Adapter caseUsage(Usage object) {
				return createUsageAdapter();
			}
			@Override
			public Adapter caseCategory(Category object) {
				return createCategoryAdapter();
			}
			@Override
			public Adapter caseAbstractInformationUnit(AbstractInformationUnit object) {
				return createAbstractInformationUnitAdapter();
			}
			@Override
			public Adapter caseInformationUnitListItem(InformationUnitListItem object) {
				return createInformationUnitListItemAdapter();
			}
			@Override
			public Adapter caseApplicationRoot(ApplicationRoot object) {
				return createApplicationRootAdapter();
			}
			@Override
			public Adapter caseAnnotation(Annotation object) {
				return createAnnotationAdapter();
			}
			@Override
			public Adapter caseLink(Link object) {
				return createLinkAdapter();
			}
			@Override
			public Adapter caseLinkType(LinkType object) {
				return createLinkTypeAdapter();
			}
			@Override
			public Adapter caseAdapter(org.remus.infomngmnt.Adapter object) {
				return createAdapterAdapter();
			}
			@Override
			public Adapter caseLinkTypeCollection(LinkTypeCollection object) {
				return createLinkTypeCollectionAdapter();
			}
			@Override
			public Adapter caseStringToLinkTypeMap(Map.Entry<String, LinkType> object) {
				return createStringToLinkTypeMapAdapter();
			}
			@Override
			public Adapter caseRecentlyUsedKeywords(RecentlyUsedKeywords object) {
				return createRecentlyUsedKeywordsAdapter();
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
			public Adapter caseRemoteRepository(RemoteRepository object) {
				return createRemoteRepositoryAdapter();
			}
			@Override
			public Adapter caseRemoteObject(RemoteObject object) {
				return createRemoteObjectAdapter();
			}
			@Override
			public Adapter caseRemoteContainer(RemoteContainer object) {
				return createRemoteContainerAdapter();
			}
			@Override
			public Adapter caseRepositoryCollection(RepositoryCollection object) {
				return createRepositoryCollectionAdapter();
			}
			@Override
			public Adapter caseSynchronizationMetadata(SynchronizationMetadata object) {
				return createSynchronizationMetadataAdapter();
			}
			@Override
			public Adapter caseChangeSet(ChangeSet object) {
				return createChangeSetAdapter();
			}
			@Override
			public Adapter caseChangeSetItem(ChangeSetItem object) {
				return createChangeSetItemAdapter();
			}
			@Override
			public Adapter caseCategoryToSynchronizationActionMap(Map.Entry<Category, SynchronizationAction> object) {
				return createCategoryToSynchronizationActionMapAdapter();
			}
			@Override
			public Adapter caseInformationUnitListItemToInformationUnitMap(Map.Entry<InformationUnitListItem, InformationUnit> object) {
				return createInformationUnitListItemToInformationUnitMapAdapter();
			}
			@Override
			public Adapter caseSynchronizableObjectToSynchronizationActionMap(Map.Entry<SynchronizableObject, SynchronizationAction> object) {
				return createSynchronizableObjectToSynchronizationActionMapAdapter();
			}
			@Override
			public Adapter caseTag(Tag object) {
				return createTagAdapter();
			}
			@Override
			public Adapter caseAvailableTags(AvailableTags object) {
				return createAvailableTagsAdapter();
			}
			@Override
			public Adapter caseSynchronizableObject(SynchronizableObject object) {
				return createSynchronizableObjectAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.InformationUnit <em>Information Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.InformationUnit
	 * @generated
	 */
	public Adapter createInformationUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.Usage <em>Usage</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.Usage
	 * @generated
	 */
	public Adapter createUsageAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.Category <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.Category
	 * @generated
	 */
	public Adapter createCategoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.AbstractInformationUnit <em>Abstract Information Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.AbstractInformationUnit
	 * @generated
	 */
	public Adapter createAbstractInformationUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.InformationUnitListItem <em>Information Unit List Item</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.InformationUnitListItem
	 * @generated
	 */
	public Adapter createInformationUnitListItemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.ApplicationRoot <em>Application Root</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.ApplicationRoot
	 * @generated
	 */
	public Adapter createApplicationRootAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.Annotation
	 * @generated
	 */
	public Adapter createAnnotationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.Link <em>Link</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.Link
	 * @generated
	 */
	public Adapter createLinkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.LinkType <em>Link Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.LinkType
	 * @generated
	 */
	public Adapter createLinkTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.Adapter <em>Adapter</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.Adapter
	 * @generated
	 */
	public Adapter createAdapterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.LinkTypeCollection <em>Link Type Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.LinkTypeCollection
	 * @generated
	 */
	public Adapter createLinkTypeCollectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>String To Link Type Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createStringToLinkTypeMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RecentlyUsedKeywords <em>Recently Used Keywords</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RecentlyUsedKeywords
	 * @generated
	 */
	public Adapter createRecentlyUsedKeywordsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.NewElementRules <em>New Element Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.NewElementRules
	 * @generated
	 */
	public Adapter createNewElementRulesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RuleValue <em>Rule Value</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RuleValue
	 * @generated
	 */
	public Adapter createRuleValueAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.AvailableRuleDefinitions <em>Available Rule Definitions</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.AvailableRuleDefinitions
	 * @generated
	 */
	public Adapter createAvailableRuleDefinitionsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RemusTransferType <em>Remus Transfer Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RemusTransferType
	 * @generated
	 */
	public Adapter createRemusTransferTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RuleAction <em>Rule Action</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RuleAction
	 * @generated
	 */
	public Adapter createRuleActionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RuleResult <em>Rule Result</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RuleResult
	 * @generated
	 */
	public Adapter createRuleResultAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RemoteRepository <em>Remote Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RemoteRepository
	 * @generated
	 */
	public Adapter createRemoteRepositoryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RemoteObject <em>Remote Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RemoteObject
	 * @generated
	 */
	public Adapter createRemoteObjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RemoteContainer <em>Remote Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RemoteContainer
	 * @generated
	 */
	public Adapter createRemoteContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.RepositoryCollection <em>Repository Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.RepositoryCollection
	 * @generated
	 */
	public Adapter createRepositoryCollectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.SynchronizationMetadata <em>Synchronization Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.SynchronizationMetadata
	 * @generated
	 */
	public Adapter createSynchronizationMetadataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.ChangeSet <em>Change Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.ChangeSet
	 * @generated
	 */
	public Adapter createChangeSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.ChangeSetItem <em>Change Set Item</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.ChangeSetItem
	 * @generated
	 */
	public Adapter createChangeSetItemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Category To Synchronization Action Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createCategoryToSynchronizationActionMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Information Unit List Item To Information Unit Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createInformationUnitListItemToInformationUnitMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.util.Map.Entry <em>Synchronizable Object To Synchronization Action Map</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see java.util.Map.Entry
	 * @generated
	 */
	public Adapter createSynchronizableObjectToSynchronizationActionMapAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.Tag <em>Tag</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.Tag
	 * @generated
	 */
	public Adapter createTagAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.AvailableTags <em>Available Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.AvailableTags
	 * @generated
	 */
	public Adapter createAvailableTagsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.SynchronizableObject <em>Synchronizable Object</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.SynchronizableObject
	 * @generated
	 */
	public Adapter createSynchronizableObjectAdapter() {
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

} //InfomngmntAdapterFactory
