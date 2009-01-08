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

import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Change Set Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.ChangeSetItem#getRemoteConvertedContainer <em>Remote Converted Container</em>}</li>
 *   <li>{@link org.remus.infomngmnt.ChangeSetItem#getRemoteOriginalObject <em>Remote Original Object</em>}</li>
 *   <li>{@link org.remus.infomngmnt.ChangeSetItem#getLocalContainer <em>Local Container</em>}</li>
 *   <li>{@link org.remus.infomngmnt.ChangeSetItem#getSyncCategoryActionMap <em>Sync Category Action Map</em>}</li>
 *   <li>{@link org.remus.infomngmnt.ChangeSetItem#getSyncInformationUnitActionMap <em>Sync Information Unit Action Map</em>}</li>
 *   <li>{@link org.remus.infomngmnt.ChangeSetItem#getRemoteFullObjectMap <em>Remote Full Object Map</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSetItem()
 * @model
 * @generated
 */
public interface ChangeSetItem extends Adapter {
	/**
	 * Returns the value of the '<em><b>Remote Converted Container</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote Converted Container</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote Converted Container</em>' containment reference.
	 * @see #setRemoteConvertedContainer(Category)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSetItem_RemoteConvertedContainer()
	 * @model containment="true"
	 * @generated
	 */
	Category getRemoteConvertedContainer();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.ChangeSetItem#getRemoteConvertedContainer <em>Remote Converted Container</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote Converted Container</em>' containment reference.
	 * @see #getRemoteConvertedContainer()
	 * @generated
	 */
	void setRemoteConvertedContainer(Category value);

	/**
	 * Returns the value of the '<em><b>Remote Original Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote Original Object</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote Original Object</em>' containment reference.
	 * @see #setRemoteOriginalObject(RemoteContainer)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSetItem_RemoteOriginalObject()
	 * @model containment="true"
	 * @generated
	 */
	RemoteContainer getRemoteOriginalObject();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.ChangeSetItem#getRemoteOriginalObject <em>Remote Original Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Remote Original Object</em>' containment reference.
	 * @see #getRemoteOriginalObject()
	 * @generated
	 */
	void setRemoteOriginalObject(RemoteContainer value);

	/**
	 * Returns the value of the '<em><b>Local Container</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Local Container</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Local Container</em>' reference.
	 * @see #setLocalContainer(Category)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSetItem_LocalContainer()
	 * @model
	 * @generated
	 */
	Category getLocalContainer();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.ChangeSetItem#getLocalContainer <em>Local Container</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Local Container</em>' reference.
	 * @see #getLocalContainer()
	 * @generated
	 */
	void setLocalContainer(Category value);

	/**
	 * Returns the value of the '<em><b>Sync Category Action Map</b></em>' map.
	 * The key is of type {@link org.remus.infomngmnt.Category},
	 * and the value is of type {@link org.remus.infomngmnt.SynchronizationAction},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sync Category Action Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Category Action Map</em>' map.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSetItem_SyncCategoryActionMap()
	 * @model mapType="org.remus.infomngmnt.CategoryToSynchronizationActionMap<org.remus.infomngmnt.Category, org.remus.infomngmnt.SynchronizationAction>"
	 * @generated
	 */
	EMap<Category, SynchronizationAction> getSyncCategoryActionMap();

	/**
	 * Returns the value of the '<em><b>Sync Information Unit Action Map</b></em>' map.
	 * The key is of type {@link org.remus.infomngmnt.InformationUnitListItem},
	 * and the value is of type {@link org.remus.infomngmnt.SynchronizationAction},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sync Information Unit Action Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Information Unit Action Map</em>' map.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSetItem_SyncInformationUnitActionMap()
	 * @model mapType="org.remus.infomngmnt.InformationUnitListItemToSynchronizationActionMap<org.remus.infomngmnt.InformationUnitListItem, org.remus.infomngmnt.SynchronizationAction>"
	 * @generated
	 */
	EMap<InformationUnitListItem, SynchronizationAction> getSyncInformationUnitActionMap();

	/**
	 * Returns the value of the '<em><b>Remote Full Object Map</b></em>' map.
	 * The key is of type {@link org.remus.infomngmnt.InformationUnitListItem},
	 * and the value is of type {@link org.remus.infomngmnt.InformationUnit},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Remote Full Object Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Remote Full Object Map</em>' map.
	 * @see org.remus.infomngmnt.InfomngmntPackage#getChangeSetItem_RemoteFullObjectMap()
	 * @model mapType="org.remus.infomngmnt.InformationUnitListItemToInformationUnitMap<org.remus.infomngmnt.InformationUnitListItem, org.remus.infomngmnt.InformationUnit>"
	 * @generated
	 */
	EMap<InformationUnitListItem, InformationUnit> getRemoteFullObjectMap();

} // ChangeSetItem
