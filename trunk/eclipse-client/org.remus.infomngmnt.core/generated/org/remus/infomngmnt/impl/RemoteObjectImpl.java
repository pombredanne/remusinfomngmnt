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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.RemoteObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Remote Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getPossibleInfoTypeId <em>Possible Info Type Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getRepositoryTypeId <em>Repository Type Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getRepositoryTypeObjectId <em>Repository Type Object Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getWrappedObject <em>Wrapped Object</em>}</li>
 *   <li>{@link org.remus.infomngmnt.impl.RemoteObjectImpl#getHash <em>Hash</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RemoteObjectImpl extends AdapterImpl implements RemoteObject {
	/**
	 * The cached value of the '{@link #getPossibleInfoTypeId() <em>Possible Info Type Id</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPossibleInfoTypeId()
	 * @generated
	 * @ordered
	 */
	protected EList<String> possibleInfoTypeId;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;
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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRepositoryTypeId() <em>Repository Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryTypeId()
	 * @generated
	 * @ordered
	 */
	protected static final String REPOSITORY_TYPE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepositoryTypeId() <em>Repository Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryTypeId()
	 * @generated
	 * @ordered
	 */
	protected String repositoryTypeId = REPOSITORY_TYPE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getRepositoryTypeObjectId() <em>Repository Type Object Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryTypeObjectId()
	 * @generated
	 * @ordered
	 */
	protected static final String REPOSITORY_TYPE_OBJECT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRepositoryTypeObjectId() <em>Repository Type Object Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepositoryTypeObjectId()
	 * @generated
	 * @ordered
	 */
	protected String repositoryTypeObjectId = REPOSITORY_TYPE_OBJECT_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getWrappedObject() <em>Wrapped Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWrappedObject()
	 * @generated
	 * @ordered
	 */
	protected static final Object WRAPPED_OBJECT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWrappedObject() <em>Wrapped Object</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWrappedObject()
	 * @generated
	 * @ordered
	 */
	protected Object wrappedObject = WRAPPED_OBJECT_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RemoteObjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InfomngmntPackage.Literals.REMOTE_OBJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getPossibleInfoTypeId() {
		if (possibleInfoTypeId == null) {
			possibleInfoTypeId = new EDataTypeUniqueEList<String>(String.class, this, InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID);
		}
		return possibleInfoTypeId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.REMOTE_OBJECT__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.REMOTE_OBJECT__URL, oldUrl, url));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.REMOTE_OBJECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRepositoryTypeId() {
		return repositoryTypeId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepositoryTypeId(String newRepositoryTypeId) {
		String oldRepositoryTypeId = repositoryTypeId;
		repositoryTypeId = newRepositoryTypeId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_ID, oldRepositoryTypeId, repositoryTypeId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRepositoryTypeObjectId() {
		return repositoryTypeObjectId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepositoryTypeObjectId(String newRepositoryTypeObjectId) {
		String oldRepositoryTypeObjectId = repositoryTypeObjectId;
		repositoryTypeObjectId = newRepositoryTypeObjectId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID, oldRepositoryTypeObjectId, repositoryTypeObjectId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getWrappedObject() {
		return wrappedObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWrappedObject(Object newWrappedObject) {
		Object oldWrappedObject = wrappedObject;
		wrappedObject = newWrappedObject;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.REMOTE_OBJECT__WRAPPED_OBJECT, oldWrappedObject, wrappedObject));
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
			eNotify(new ENotificationImpl(this, Notification.SET, InfomngmntPackage.REMOTE_OBJECT__HASH, oldHash, hash));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
				return getPossibleInfoTypeId();
			case InfomngmntPackage.REMOTE_OBJECT__ID:
				return getId();
			case InfomngmntPackage.REMOTE_OBJECT__URL:
				return getUrl();
			case InfomngmntPackage.REMOTE_OBJECT__NAME:
				return getName();
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_ID:
				return getRepositoryTypeId();
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID:
				return getRepositoryTypeObjectId();
			case InfomngmntPackage.REMOTE_OBJECT__WRAPPED_OBJECT:
				return getWrappedObject();
			case InfomngmntPackage.REMOTE_OBJECT__HASH:
				return getHash();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
				getPossibleInfoTypeId().clear();
				getPossibleInfoTypeId().addAll((Collection<? extends String>)newValue);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__ID:
				setId((String)newValue);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__URL:
				setUrl((String)newValue);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__NAME:
				setName((String)newValue);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_ID:
				setRepositoryTypeId((String)newValue);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID:
				setRepositoryTypeObjectId((String)newValue);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__WRAPPED_OBJECT:
				setWrappedObject(newValue);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__HASH:
				setHash((String)newValue);
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
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
				getPossibleInfoTypeId().clear();
				return;
			case InfomngmntPackage.REMOTE_OBJECT__ID:
				setId(ID_EDEFAULT);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__URL:
				setUrl(URL_EDEFAULT);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_ID:
				setRepositoryTypeId(REPOSITORY_TYPE_ID_EDEFAULT);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID:
				setRepositoryTypeObjectId(REPOSITORY_TYPE_OBJECT_ID_EDEFAULT);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__WRAPPED_OBJECT:
				setWrappedObject(WRAPPED_OBJECT_EDEFAULT);
				return;
			case InfomngmntPackage.REMOTE_OBJECT__HASH:
				setHash(HASH_EDEFAULT);
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
			case InfomngmntPackage.REMOTE_OBJECT__POSSIBLE_INFO_TYPE_ID:
				return possibleInfoTypeId != null && !possibleInfoTypeId.isEmpty();
			case InfomngmntPackage.REMOTE_OBJECT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case InfomngmntPackage.REMOTE_OBJECT__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
			case InfomngmntPackage.REMOTE_OBJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_ID:
				return REPOSITORY_TYPE_ID_EDEFAULT == null ? repositoryTypeId != null : !REPOSITORY_TYPE_ID_EDEFAULT.equals(repositoryTypeId);
			case InfomngmntPackage.REMOTE_OBJECT__REPOSITORY_TYPE_OBJECT_ID:
				return REPOSITORY_TYPE_OBJECT_ID_EDEFAULT == null ? repositoryTypeObjectId != null : !REPOSITORY_TYPE_OBJECT_ID_EDEFAULT.equals(repositoryTypeObjectId);
			case InfomngmntPackage.REMOTE_OBJECT__WRAPPED_OBJECT:
				return WRAPPED_OBJECT_EDEFAULT == null ? wrappedObject != null : !WRAPPED_OBJECT_EDEFAULT.equals(wrappedObject);
			case InfomngmntPackage.REMOTE_OBJECT__HASH:
				return HASH_EDEFAULT == null ? hash != null : !HASH_EDEFAULT.equals(hash);
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
		result.append(" (possibleInfoTypeId: ");
		result.append(possibleInfoTypeId);
		result.append(", id: ");
		result.append(id);
		result.append(", url: ");
		result.append(url);
		result.append(", name: ");
		result.append(name);
		result.append(", repositoryTypeId: ");
		result.append(repositoryTypeId);
		result.append(", repositoryTypeObjectId: ");
		result.append(repositoryTypeObjectId);
		result.append(", wrappedObject: ");
		result.append(wrappedObject);
		result.append(", hash: ");
		result.append(hash);
		result.append(')');
		return result.toString();
	}

} //RemoteObjectImpl
