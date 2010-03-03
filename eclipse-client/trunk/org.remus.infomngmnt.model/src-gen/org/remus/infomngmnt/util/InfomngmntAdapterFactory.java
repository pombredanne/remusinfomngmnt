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
			public Adapter caseV__________InformationUnit__________V(V__________InformationUnit__________V object) {
				return createV__________InformationUnit__________VAdapter();
			}
			@Override
			public Adapter caseAdapter(org.remus.infomngmnt.Adapter object) {
				return createAdapterAdapter();
			}
			@Override
			public Adapter caseAbstractInformationUnit(AbstractInformationUnit object) {
				return createAbstractInformationUnitAdapter();
			}
			@Override
			public Adapter caseInformationUnit(InformationUnit object) {
				return createInformationUnitAdapter();
			}
			@Override
			public Adapter caseBinaryReference(BinaryReference object) {
				return createBinaryReferenceAdapter();
			}
			@Override
			public Adapter caseLink(Link object) {
				return createLinkAdapter();
			}
			@Override
			public Adapter caseComment(Comment object) {
				return createCommentAdapter();
			}
			@Override
			public Adapter caseCalendarEntry(CalendarEntry object) {
				return createCalendarEntryAdapter();
			}
			@Override
			public Adapter caseV__________NavigationObjects__________V(V__________NavigationObjects__________V object) {
				return createV__________NavigationObjects__________VAdapter();
			}
			@Override
			public Adapter caseApplicationRoot(ApplicationRoot object) {
				return createApplicationRootAdapter();
			}
			@Override
			public Adapter caseCategory(Category object) {
				return createCategoryAdapter();
			}
			@Override
			public Adapter caseInformationUnitListItem(InformationUnitListItem object) {
				return createInformationUnitListItemAdapter();
			}
			@Override
			public Adapter caseV__________Semantics__________V(V__________Semantics__________V object) {
				return createV__________Semantics__________VAdapter();
			}
			@Override
			public Adapter caseRecentlyUsedKeywords(RecentlyUsedKeywords object) {
				return createRecentlyUsedKeywordsAdapter();
			}
			@Override
			public Adapter caseAvailableTags(AvailableTags object) {
				return createAvailableTagsAdapter();
			}
			@Override
			public Adapter caseTag(Tag object) {
				return createTagAdapter();
			}
			@Override
			public Adapter caseV__________Synchronization__________V(V__________Synchronization__________V object) {
				return createV__________Synchronization__________VAdapter();
			}
			@Override
			public Adapter caseRepositoryCollection(RepositoryCollection object) {
				return createRepositoryCollectionAdapter();
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
			public Adapter caseSynchronizableObject(SynchronizableObject object) {
				return createSynchronizableObjectAdapter();
			}
			@Override
			public Adapter caseV__________InformationStructureDefinition__________V(V__________InformationStructureDefinition__________V object) {
				return createV__________InformationStructureDefinition__________VAdapter();
			}
			@Override
			public Adapter caseInformationStructureDefinition(InformationStructureDefinition object) {
				return createInformationStructureDefinitionAdapter();
			}
			@Override
			public Adapter caseInformationStructureItem(InformationStructureItem object) {
				return createInformationStructureItemAdapter();
			}
			@Override
			public Adapter caseInformationStructure(InformationStructure object) {
				return createInformationStructureAdapter();
			}
			@Override
			public Adapter caseDynamicStructure(DynamicStructure object) {
				return createDynamicStructureAdapter();
			}
			@Override
			public Adapter caseV__________DesktopNotifications_________V(V__________DesktopNotifications_________V object) {
				return createV__________DesktopNotifications_________VAdapter();
			}
			@Override
			public Adapter caseNotification(Notification object) {
				return createNotificationAdapter();
			}
			@Override
			public Adapter caseNotificationCollection(NotificationCollection object) {
				return createNotificationCollectionAdapter();
			}
			@Override
			public Adapter caseV__________Other__________V(V__________Other__________V object) {
				return createV__________Other__________VAdapter();
			}
			@Override
			public Adapter caseStringToStringMap(Map.Entry<String, String> object) {
				return createStringToStringMapAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.V__________InformationUnit__________V <em>VInformation Unit V</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.V__________InformationUnit__________V
	 * @generated
	 */
	public Adapter createV__________InformationUnit__________VAdapter() {
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
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.BinaryReference <em>Binary Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.BinaryReference
	 * @generated
	 */
	public Adapter createBinaryReferenceAdapter() {
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
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.Comment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.Comment
	 * @generated
	 */
	public Adapter createCommentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.CalendarEntry <em>Calendar Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.CalendarEntry
	 * @generated
	 */
	public Adapter createCalendarEntryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.V__________NavigationObjects__________V <em>VNavigation Objects V</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.V__________NavigationObjects__________V
	 * @generated
	 */
	public Adapter createV__________NavigationObjects__________VAdapter() {
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
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.V__________Semantics__________V <em>VSemantics V</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.V__________Semantics__________V
	 * @generated
	 */
	public Adapter createV__________Semantics__________VAdapter() {
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
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.V__________Synchronization__________V <em>VSynchronization V</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.V__________Synchronization__________V
	 * @generated
	 */
	public Adapter createV__________Synchronization__________VAdapter() {
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
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.V__________InformationStructureDefinition__________V <em>VInformation Structure Definition V</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.V__________InformationStructureDefinition__________V
	 * @generated
	 */
	public Adapter createV__________InformationStructureDefinition__________VAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.InformationStructureDefinition <em>Information Structure Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.InformationStructureDefinition
	 * @generated
	 */
	public Adapter createInformationStructureDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.InformationStructureItem <em>Information Structure Item</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.InformationStructureItem
	 * @generated
	 */
	public Adapter createInformationStructureItemAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.InformationStructure <em>Information Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.InformationStructure
	 * @generated
	 */
	public Adapter createInformationStructureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.DynamicStructure <em>Dynamic Structure</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.DynamicStructure
	 * @generated
	 */
	public Adapter createDynamicStructureAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.V__________DesktopNotifications_________V <em>VDesktop Notifications V</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.V__________DesktopNotifications_________V
	 * @generated
	 */
	public Adapter createV__________DesktopNotifications_________VAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.Notification <em>Notification</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.Notification
	 * @generated
	 */
	public Adapter createNotificationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.NotificationCollection <em>Notification Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.NotificationCollection
	 * @generated
	 */
	public Adapter createNotificationCollectionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.remus.infomngmnt.V__________Other__________V <em>VOther V</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.remus.infomngmnt.V__________Other__________V
	 * @generated
	 */
	public Adapter createV__________Other__________VAdapter() {
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
