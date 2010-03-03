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
package org.remus.infomngmnt;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.InfomngmntPackage
 * @generated
 */
public interface InfomngmntFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InfomngmntFactory eINSTANCE = org.remus.infomngmnt.impl.InfomngmntFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Information Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Information Unit</em>'.
	 * @generated
	 */
	InformationUnit createInformationUnit();

	/**
	 * Returns a new object of class '<em>Binary Reference</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Binary Reference</em>'.
	 * @generated
	 */
	BinaryReference createBinaryReference();

	/**
	 * Returns a new object of class '<em>Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Link</em>'.
	 * @generated
	 */
	Link createLink();

	/**
	 * Returns a new object of class '<em>Comment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comment</em>'.
	 * @generated
	 */
	Comment createComment();

	/**
	 * Returns a new object of class '<em>Calendar Entry</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Calendar Entry</em>'.
	 * @generated
	 */
	CalendarEntry createCalendarEntry();

	/**
	 * Returns a new object of class '<em>Application Root</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Application Root</em>'.
	 * @generated
	 */
	ApplicationRoot createApplicationRoot();

	/**
	 * Returns a new object of class '<em>Category</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Category</em>'.
	 * @generated
	 */
	Category createCategory();

	/**
	 * Returns a new object of class '<em>Information Unit List Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Information Unit List Item</em>'.
	 * @generated
	 */
	InformationUnitListItem createInformationUnitListItem();

	/**
	 * Returns a new object of class '<em>Recently Used Keywords</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Recently Used Keywords</em>'.
	 * @generated
	 */
	RecentlyUsedKeywords createRecentlyUsedKeywords();

	/**
	 * Returns a new object of class '<em>Available Tags</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Available Tags</em>'.
	 * @generated
	 */
	AvailableTags createAvailableTags();

	/**
	 * Returns a new object of class '<em>Tag</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Tag</em>'.
	 * @generated
	 */
	Tag createTag();

	/**
	 * Returns a new object of class '<em>Repository Collection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Repository Collection</em>'.
	 * @generated
	 */
	RepositoryCollection createRepositoryCollection();

	/**
	 * Returns a new object of class '<em>Remote Repository</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remote Repository</em>'.
	 * @generated
	 */
	RemoteRepository createRemoteRepository();

	/**
	 * Returns a new object of class '<em>Remote Object</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remote Object</em>'.
	 * @generated
	 */
	RemoteObject createRemoteObject();

	/**
	 * Returns a new object of class '<em>Remote Container</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Remote Container</em>'.
	 * @generated
	 */
	RemoteContainer createRemoteContainer();

	/**
	 * Returns a new object of class '<em>Synchronization Metadata</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Synchronization Metadata</em>'.
	 * @generated
	 */
	SynchronizationMetadata createSynchronizationMetadata();

	/**
	 * Returns a new object of class '<em>Change Set</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Set</em>'.
	 * @generated
	 */
	ChangeSet createChangeSet();

	/**
	 * Returns a new object of class '<em>Change Set Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Change Set Item</em>'.
	 * @generated
	 */
	ChangeSetItem createChangeSetItem();

	/**
	 * Returns a new object of class '<em>Information Structure Definition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Information Structure Definition</em>'.
	 * @generated
	 */
	InformationStructureDefinition createInformationStructureDefinition();

	/**
	 * Returns a new object of class '<em>Information Structure Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Information Structure Item</em>'.
	 * @generated
	 */
	InformationStructureItem createInformationStructureItem();

	/**
	 * Returns a new object of class '<em>Dynamic Structure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Dynamic Structure</em>'.
	 * @generated
	 */
	DynamicStructure createDynamicStructure();

	/**
	 * Returns a new object of class '<em>Notification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Notification</em>'.
	 * @generated
	 */
	Notification createNotification();

	/**
	 * Returns a new object of class '<em>Notification Collection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Notification Collection</em>'.
	 * @generated
	 */
	NotificationCollection createNotificationCollection();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	InfomngmntPackage getInfomngmntPackage();

} //InfomngmntFactory
