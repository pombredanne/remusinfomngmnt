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

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Synchronization Metadata</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.SynchronizationMetadataImpl#getRepositoryId <em>Repository Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.SynchronizationMetadataImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.SynchronizationMetadataImpl#isReadonly <em>Readonly</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.SynchronizationMetadataImpl#getLastSynchronisation <em>Last Synchronisation</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.SynchronizationMetadataImpl#getHash <em>Hash</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.SynchronizationMetadataImpl#getSyncState <em>Sync State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SynchronizationMetadataImpl extends AdapterImpl implements SynchronizationMetadata {
	/**
	 * The default value of the '{@link #getRepositoryId() <em>Repository Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryId()
	 * @generated
	 * @ordered
	 */
	protected static final String REPOSITORY_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepositoryId() <em>Repository Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryId()
	 * @generated
	 * @ordered
	 */
	protected String repositoryId = REPOSITORY_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected String url = URL_EDEFAULT;

	/**
	 * The default value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected static final boolean READONLY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isReadonly() <em>Readonly</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isReadonly()
	 * @generated
	 * @ordered
	 */
	protected boolean readonly = READONLY_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastSynchronisation() <em>Last Synchronisation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastSynchronisation()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_SYNCHRONISATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastSynchronisation() <em>Last Synchronisation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastSynchronisation()
	 * @generated
	 * @ordered
	 */
	protected Date lastSynchronisation = LAST_SYNCHRONISATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getHash() <em>Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHash()
	 * @generated
	 * @ordered
	 */
	protected static final String HASH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHash() <em>Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHash()
	 * @generated
	 * @ordered
	 */
	protected String hash = HASH_EDEFAULT;

	/**
	 * The default value of the '{@link #getSyncState() <em>Sync State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncState()
	 * @generated
	 * @ordered
	 */
	protected static final SynchronizationState SYNC_STATE_EDEFAULT = SynchronizationState.LOCAL_DELETED;

	/**
	 * The cached value of the '{@link #getSyncState() <em>Sync State</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSyncState()
	 * @generated
	 * @ordered
	 */
	protected SynchronizationState syncState = SYNC_STATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SynchronizationMetadataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRepositoryId() {
		return repositoryId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepositoryId(String newRepositoryId) {
		String oldRepositoryId = repositoryId;
		repositoryId = newRepositoryId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.SYNCHRONIZATION_METADATA__REPOSITORY_ID, oldRepositoryId, repositoryId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrl(String newUrl) {
		String oldUrl = url;
		url = newUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.SYNCHRONIZATION_METADATA__URL, oldUrl, url));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReadonly() {
		return readonly;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReadonly(boolean newReadonly) {
		boolean oldReadonly = readonly;
		readonly = newReadonly;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.SYNCHRONIZATION_METADATA__READONLY, oldReadonly, readonly));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getLastSynchronisation() {
		return lastSynchronisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastSynchronisation(Date newLastSynchronisation) {
		Date oldLastSynchronisation = lastSynchronisation;
		lastSynchronisation = newLastSynchronisation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION, oldLastSynchronisation, lastSynchronisation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHash(String newHash) {
		String oldHash = hash;
		hash = newHash;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.SYNCHRONIZATION_METADATA__HASH, oldHash, hash));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SynchronizationState getSyncState() {
		return syncState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSyncState(SynchronizationState newSyncState) {
		SynchronizationState oldSyncState = syncState;
		syncState = newSyncState == null ? SYNC_STATE_EDEFAULT : newSyncState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.SYNCHRONIZATION_METADATA__SYNC_STATE, oldSyncState, syncState));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__REPOSITORY_ID:
				return getRepositoryId();
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__URL:
				return getUrl();
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__READONLY:
				return isReadonly() ? Boolean.TRUE : Boolean.FALSE;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION:
				return getLastSynchronisation();
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__HASH:
				return getHash();
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__SYNC_STATE:
				return getSyncState();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__REPOSITORY_ID:
				setRepositoryId((String)newValue);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__URL:
				setUrl((String)newValue);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__READONLY:
				setReadonly(((Boolean)newValue).booleanValue());
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION:
				setLastSynchronisation((Date)newValue);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__HASH:
				setHash((String)newValue);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__SYNC_STATE:
				setSyncState((SynchronizationState)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__REPOSITORY_ID:
				setRepositoryId(REPOSITORY_ID_EDEFAULT);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__URL:
				setUrl(URL_EDEFAULT);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__READONLY:
				setReadonly(READONLY_EDEFAULT);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION:
				setLastSynchronisation(LAST_SYNCHRONISATION_EDEFAULT);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__HASH:
				setHash(HASH_EDEFAULT);
				return;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__SYNC_STATE:
				setSyncState(SYNC_STATE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__REPOSITORY_ID:
				return REPOSITORY_ID_EDEFAULT == null ? repositoryId != null : !REPOSITORY_ID_EDEFAULT.equals(repositoryId);
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__READONLY:
				return readonly != READONLY_EDEFAULT;
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION:
				return LAST_SYNCHRONISATION_EDEFAULT == null ? lastSynchronisation != null : !LAST_SYNCHRONISATION_EDEFAULT.equals(lastSynchronisation);
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__HASH:
				return HASH_EDEFAULT == null ? hash != null : !HASH_EDEFAULT.equals(hash);
			case InfomngmntPackage.SYNCHRONIZATION_METADATA__SYNC_STATE:
				return syncState != SYNC_STATE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (repositoryId: ");
		result.append(repositoryId);
		result.append(", url: ");
		result.append(url);
		result.append(", readonly: ");
		result.append(readonly);
		result.append(", lastSynchronisation: ");
		result.append(lastSynchronisation);
		result.append(", hash: ");
		result.append(hash);
		result.append(", syncState: ");
		result.append(syncState);
		result.append(')');
		return result.toString();
	}

} //SynchronizationMetadataImpl
