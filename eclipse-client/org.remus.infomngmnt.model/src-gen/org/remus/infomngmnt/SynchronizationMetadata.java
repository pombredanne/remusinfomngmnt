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

import java.util.Date;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Synchronization Metadata</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.SynchronizationMetadata#getRepositoryId <em>Repository Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.SynchronizationMetadata#getUrl <em>Url</em>}</li>
 *   <li>{@link org.remus.infomngmnt.SynchronizationMetadata#isReadonly <em>Readonly</em>}</li>
 *   <li>{@link org.remus.infomngmnt.SynchronizationMetadata#getLastSynchronisation <em>Last Synchronisation</em>}</li>
 *   <li>{@link org.remus.infomngmnt.SynchronizationMetadata#getHash <em>Hash</em>}</li>
 *   <li>{@link org.remus.infomngmnt.SynchronizationMetadata#getSyncState <em>Sync State</em>}</li>
 *   <li>{@link org.remus.infomngmnt.SynchronizationMetadata#isCurrentlySyncing <em>Currently Syncing</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationMetadata()
 * @model
 * @generated
 */
public interface SynchronizationMetadata extends Adapter {
	/**
	 * Returns the value of the '<em><b>Repository Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repository Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repository Id</em>' attribute.
	 * @see #setRepositoryId(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationMetadata_RepositoryId()
	 * @model
	 * @generated
	 */
	String getRepositoryId();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.SynchronizationMetadata#getRepositoryId <em>Repository Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Repository Id</em>' attribute.
	 * @see #getRepositoryId()
	 * @generated
	 */
	void setRepositoryId(String value);

	/**
	 * Returns the value of the '<em><b>Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Url</em>' attribute.
	 * @see #setUrl(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationMetadata_Url()
	 * @model
	 * @generated
	 */
	String getUrl();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.SynchronizationMetadata#getUrl <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Url</em>' attribute.
	 * @see #getUrl()
	 * @generated
	 */
	void setUrl(String value);

	/**
	 * Returns the value of the '<em><b>Readonly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Readonly</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Readonly</em>' attribute.
	 * @see #setReadonly(boolean)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationMetadata_Readonly()
	 * @model
	 * @generated
	 */
	boolean isReadonly();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.SynchronizationMetadata#isReadonly <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Readonly</em>' attribute.
	 * @see #isReadonly()
	 * @generated
	 */
	void setReadonly(boolean value);

	/**
	 * Returns the value of the '<em><b>Last Synchronisation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Synchronisation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Synchronisation</em>' attribute.
	 * @see #setLastSynchronisation(Date)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationMetadata_LastSynchronisation()
	 * @model
	 * @generated
	 */
	Date getLastSynchronisation();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.SynchronizationMetadata#getLastSynchronisation <em>Last Synchronisation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Synchronisation</em>' attribute.
	 * @see #getLastSynchronisation()
	 * @generated
	 */
	void setLastSynchronisation(Date value);

	/**
	 * Returns the value of the '<em><b>Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hash</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hash</em>' attribute.
	 * @see #setHash(String)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationMetadata_Hash()
	 * @model
	 * @generated
	 */
	String getHash();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.SynchronizationMetadata#getHash <em>Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hash</em>' attribute.
	 * @see #getHash()
	 * @generated
	 */
	void setHash(String value);

	/**
	 * Returns the value of the '<em><b>Sync State</b></em>' attribute.
	 * The literals are from the enumeration {@link org.remus.infomngmnt.SynchronizationState}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sync State</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync State</em>' attribute.
	 * @see org.remus.infomngmnt.SynchronizationState
	 * @see #setSyncState(SynchronizationState)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationMetadata_SyncState()
	 * @model
	 * @generated
	 */
	SynchronizationState getSyncState();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.SynchronizationMetadata#getSyncState <em>Sync State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sync State</em>' attribute.
	 * @see org.remus.infomngmnt.SynchronizationState
	 * @see #getSyncState()
	 * @generated
	 */
	void setSyncState(SynchronizationState value);

	/**
	 * Returns the value of the '<em><b>Currently Syncing</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Currently Syncing</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Currently Syncing</em>' attribute.
	 * @see #setCurrentlySyncing(boolean)
	 * @see org.remus.infomngmnt.InfomngmntPackage#getSynchronizationMetadata_CurrentlySyncing()
	 * @model default="false" transient="true"
	 * @generated
	 */
	boolean isCurrentlySyncing();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.SynchronizationMetadata#isCurrentlySyncing <em>Currently Syncing</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Currently Syncing</em>' attribute.
	 * @see #isCurrentlySyncing()
	 * @generated
	 */
	void setCurrentlySyncing(boolean value);

} // SynchronizationMetadata
