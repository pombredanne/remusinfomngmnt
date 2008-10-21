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
package org.remus.infomngmnt.search.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.remus.infomngmnt.search.Search;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.SearchResult;
import org.remus.infomngmnt.search.SearchScope;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Search</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchImpl#getSearchString <em>Search String</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchImpl#getDateStart <em>Date Start</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchImpl#getEndDate <em>End Date</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchImpl#getInfoType <em>Info Type</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchImpl#getScope <em>Scope</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchImpl#getResult <em>Result</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchImpl#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SearchImpl extends EObjectImpl implements Search {
	/**
	 * The default value of the '{@link #getSearchString() <em>Search String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSearchString()
	 * @generated
	 * @ordered
	 */
	protected static final String SEARCH_STRING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSearchString() <em>Search String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSearchString()
	 * @generated
	 * @ordered
	 */
	protected String searchString = SEARCH_STRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getDateStart() <em>Date Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateStart()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_START_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDateStart() <em>Date Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDateStart()
	 * @generated
	 * @ordered
	 */
	protected Date dateStart = DATE_START_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date END_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEndDate() <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndDate()
	 * @generated
	 * @ordered
	 */
	protected Date endDate = END_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInfoType() <em>Info Type</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoType()
	 * @generated
	 * @ordered
	 */
	protected EList<String> infoType;

	/**
	 * The default value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected static final SearchScope SCOPE_EDEFAULT = SearchScope.ALL;

	/**
	 * The cached value of the '{@link #getScope() <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScope()
	 * @generated
	 * @ordered
	 */
	protected SearchScope scope = SCOPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getResult() <em>Result</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResult()
	 * @generated
	 * @ordered
	 */
	protected EList<SearchResult> result;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SearchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SearchPackage.Literals.SEARCH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSearchString() {
		return searchString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSearchString(String newSearchString) {
		String oldSearchString = searchString;
		searchString = newSearchString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH__SEARCH_STRING, oldSearchString, searchString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDateStart() {
		return dateStart;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateStart(Date newDateStart) {
		Date oldDateStart = dateStart;
		dateStart = newDateStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH__DATE_START, oldDateStart, dateStart));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndDate(Date newEndDate) {
		Date oldEndDate = endDate;
		endDate = newEndDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH__END_DATE, oldEndDate, endDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getInfoType() {
		if (infoType == null) {
			infoType = new EDataTypeUniqueEList<String>(String.class, this, SearchPackage.SEARCH__INFO_TYPE);
		}
		return infoType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchScope getScope() {
		return scope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScope(SearchScope newScope) {
		SearchScope oldScope = scope;
		scope = newScope == null ? SCOPE_EDEFAULT : newScope;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH__SCOPE, oldScope, scope));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SearchResult> getResult() {
		if (result == null) {
			result = new EObjectContainmentEList<SearchResult>(SearchResult.class, this, SearchPackage.SEARCH__RESULT);
		}
		return result;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SearchPackage.SEARCH__RESULT:
				return ((InternalEList<?>)getResult()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SearchPackage.SEARCH__SEARCH_STRING:
				return getSearchString();
			case SearchPackage.SEARCH__DATE_START:
				return getDateStart();
			case SearchPackage.SEARCH__END_DATE:
				return getEndDate();
			case SearchPackage.SEARCH__INFO_TYPE:
				return getInfoType();
			case SearchPackage.SEARCH__SCOPE:
				return getScope();
			case SearchPackage.SEARCH__RESULT:
				return getResult();
			case SearchPackage.SEARCH__ID:
				return getId();
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
			case SearchPackage.SEARCH__SEARCH_STRING:
				setSearchString((String)newValue);
				return;
			case SearchPackage.SEARCH__DATE_START:
				setDateStart((Date)newValue);
				return;
			case SearchPackage.SEARCH__END_DATE:
				setEndDate((Date)newValue);
				return;
			case SearchPackage.SEARCH__INFO_TYPE:
				getInfoType().clear();
				getInfoType().addAll((Collection<? extends String>)newValue);
				return;
			case SearchPackage.SEARCH__SCOPE:
				setScope((SearchScope)newValue);
				return;
			case SearchPackage.SEARCH__RESULT:
				getResult().clear();
				getResult().addAll((Collection<? extends SearchResult>)newValue);
				return;
			case SearchPackage.SEARCH__ID:
				setId((String)newValue);
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
			case SearchPackage.SEARCH__SEARCH_STRING:
				setSearchString(SEARCH_STRING_EDEFAULT);
				return;
			case SearchPackage.SEARCH__DATE_START:
				setDateStart(DATE_START_EDEFAULT);
				return;
			case SearchPackage.SEARCH__END_DATE:
				setEndDate(END_DATE_EDEFAULT);
				return;
			case SearchPackage.SEARCH__INFO_TYPE:
				getInfoType().clear();
				return;
			case SearchPackage.SEARCH__SCOPE:
				setScope(SCOPE_EDEFAULT);
				return;
			case SearchPackage.SEARCH__RESULT:
				getResult().clear();
				return;
			case SearchPackage.SEARCH__ID:
				setId(ID_EDEFAULT);
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
			case SearchPackage.SEARCH__SEARCH_STRING:
				return SEARCH_STRING_EDEFAULT == null ? searchString != null : !SEARCH_STRING_EDEFAULT.equals(searchString);
			case SearchPackage.SEARCH__DATE_START:
				return DATE_START_EDEFAULT == null ? dateStart != null : !DATE_START_EDEFAULT.equals(dateStart);
			case SearchPackage.SEARCH__END_DATE:
				return END_DATE_EDEFAULT == null ? endDate != null : !END_DATE_EDEFAULT.equals(endDate);
			case SearchPackage.SEARCH__INFO_TYPE:
				return infoType != null && !infoType.isEmpty();
			case SearchPackage.SEARCH__SCOPE:
				return scope != SCOPE_EDEFAULT;
			case SearchPackage.SEARCH__RESULT:
				return result != null && !result.isEmpty();
			case SearchPackage.SEARCH__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		result.append(" (searchString: ");
		result.append(searchString);
		result.append(", dateStart: ");
		result.append(dateStart);
		result.append(", endDate: ");
		result.append(endDate);
		result.append(", infoType: ");
		result.append(infoType);
		result.append(", scope: ");
		result.append(scope);
		result.append(", id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //SearchImpl
