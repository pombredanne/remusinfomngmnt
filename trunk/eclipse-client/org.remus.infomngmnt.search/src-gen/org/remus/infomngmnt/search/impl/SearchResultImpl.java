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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.remus.infomngmnt.search.SearchPackage;
import org.remus.infomngmnt.search.SearchResult;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Result</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getInfoId <em>Info Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getTitle <em>Title</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getText <em>Text</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getPath <em>Path</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getInfoType <em>Info Type</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getDate <em>Date</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getKeywords <em>Keywords</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getHighlightAttributes <em>Highlight Attributes</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getQuery <em>Query</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.impl.SearchResultImpl#getSecondaryResults <em>Secondary Results</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SearchResultImpl extends EObjectImpl implements SearchResult {
	/**
	 * The default value of the '{@link #getInfoId() <em>Info Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoId()
	 * @generated
	 * @ordered
	 */
	protected static final String INFO_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfoId() <em>Info Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoId()
	 * @generated
	 * @ordered
	 */
	protected String infoId = INFO_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected static final String TITLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
	protected String title = TITLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected static final String TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected String text = TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getInfoType() <em>Info Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoType()
	 * @generated
	 * @ordered
	 */
	protected static final String INFO_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInfoType() <em>Info Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInfoType()
	 * @generated
	 * @ordered
	 */
	protected String infoType = INFO_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected Date date = DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getKeywords() <em>Keywords</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeywords()
	 * @generated
	 * @ordered
	 */
	protected static final String KEYWORDS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKeywords() <em>Keywords</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKeywords()
	 * @generated
	 * @ordered
	 */
	protected String keywords = KEYWORDS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getHighlightAttributes() <em>Highlight Attributes</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHighlightAttributes()
	 * @generated
	 * @ordered
	 */
	protected EList<EAttribute> highlightAttributes;

	/**
	 * The default value of the '{@link #getQuery() <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected static final String QUERY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQuery() <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuery()
	 * @generated
	 * @ordered
	 */
	protected String query = QUERY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSecondaryResults() <em>Secondary Results</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondaryResults()
	 * @generated
	 * @ordered
	 */
	protected EList<SearchResult> secondaryResults;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SearchResultImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SearchPackage.Literals.SEARCH_RESULT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInfoId() {
		return infoId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfoId(String newInfoId) {
		String oldInfoId = infoId;
		infoId = newInfoId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH_RESULT__INFO_ID, oldInfoId, infoId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH_RESULT__TITLE, oldTitle, title));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText() {
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setText(String newText) {
		String oldText = text;
		text = newText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH_RESULT__TEXT, oldText, text));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH_RESULT__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInfoType() {
		return infoType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInfoType(String newInfoType) {
		String oldInfoType = infoType;
		infoType = newInfoType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH_RESULT__INFO_TYPE, oldInfoType, infoType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDate(Date newDate) {
		Date oldDate = date;
		date = newDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH_RESULT__DATE, oldDate, date));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKeywords(String newKeywords) {
		String oldKeywords = keywords;
		keywords = newKeywords;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH_RESULT__KEYWORDS, oldKeywords, keywords));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EAttribute> getHighlightAttributes() {
		if (highlightAttributes == null) {
			highlightAttributes = new EDataTypeUniqueEList<EAttribute>(EAttribute.class, this, SearchPackage.SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES);
		}
		return highlightAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQuery(String newQuery) {
		String oldQuery = query;
		query = newQuery;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SearchPackage.SEARCH_RESULT__QUERY, oldQuery, query));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SearchResult> getSecondaryResults() {
		if (secondaryResults == null) {
			secondaryResults = new EObjectContainmentEList<SearchResult>(SearchResult.class, this, SearchPackage.SEARCH_RESULT__SECONDARY_RESULTS);
		}
		return secondaryResults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SearchPackage.SEARCH_RESULT__SECONDARY_RESULTS:
				return ((InternalEList<?>)getSecondaryResults()).basicRemove(otherEnd, msgs);
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
			case SearchPackage.SEARCH_RESULT__INFO_ID:
				return getInfoId();
			case SearchPackage.SEARCH_RESULT__TITLE:
				return getTitle();
			case SearchPackage.SEARCH_RESULT__TEXT:
				return getText();
			case SearchPackage.SEARCH_RESULT__PATH:
				return getPath();
			case SearchPackage.SEARCH_RESULT__INFO_TYPE:
				return getInfoType();
			case SearchPackage.SEARCH_RESULT__DATE:
				return getDate();
			case SearchPackage.SEARCH_RESULT__KEYWORDS:
				return getKeywords();
			case SearchPackage.SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES:
				return getHighlightAttributes();
			case SearchPackage.SEARCH_RESULT__QUERY:
				return getQuery();
			case SearchPackage.SEARCH_RESULT__SECONDARY_RESULTS:
				return getSecondaryResults();
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
			case SearchPackage.SEARCH_RESULT__INFO_ID:
				setInfoId((String)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__TITLE:
				setTitle((String)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__TEXT:
				setText((String)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__PATH:
				setPath((String)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__INFO_TYPE:
				setInfoType((String)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__DATE:
				setDate((Date)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__KEYWORDS:
				setKeywords((String)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES:
				getHighlightAttributes().clear();
				getHighlightAttributes().addAll((Collection<? extends EAttribute>)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__QUERY:
				setQuery((String)newValue);
				return;
			case SearchPackage.SEARCH_RESULT__SECONDARY_RESULTS:
				getSecondaryResults().clear();
				getSecondaryResults().addAll((Collection<? extends SearchResult>)newValue);
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
			case SearchPackage.SEARCH_RESULT__INFO_ID:
				setInfoId(INFO_ID_EDEFAULT);
				return;
			case SearchPackage.SEARCH_RESULT__TITLE:
				setTitle(TITLE_EDEFAULT);
				return;
			case SearchPackage.SEARCH_RESULT__TEXT:
				setText(TEXT_EDEFAULT);
				return;
			case SearchPackage.SEARCH_RESULT__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case SearchPackage.SEARCH_RESULT__INFO_TYPE:
				setInfoType(INFO_TYPE_EDEFAULT);
				return;
			case SearchPackage.SEARCH_RESULT__DATE:
				setDate(DATE_EDEFAULT);
				return;
			case SearchPackage.SEARCH_RESULT__KEYWORDS:
				setKeywords(KEYWORDS_EDEFAULT);
				return;
			case SearchPackage.SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES:
				getHighlightAttributes().clear();
				return;
			case SearchPackage.SEARCH_RESULT__QUERY:
				setQuery(QUERY_EDEFAULT);
				return;
			case SearchPackage.SEARCH_RESULT__SECONDARY_RESULTS:
				getSecondaryResults().clear();
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
			case SearchPackage.SEARCH_RESULT__INFO_ID:
				return INFO_ID_EDEFAULT == null ? infoId != null : !INFO_ID_EDEFAULT.equals(infoId);
			case SearchPackage.SEARCH_RESULT__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
			case SearchPackage.SEARCH_RESULT__TEXT:
				return TEXT_EDEFAULT == null ? text != null : !TEXT_EDEFAULT.equals(text);
			case SearchPackage.SEARCH_RESULT__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case SearchPackage.SEARCH_RESULT__INFO_TYPE:
				return INFO_TYPE_EDEFAULT == null ? infoType != null : !INFO_TYPE_EDEFAULT.equals(infoType);
			case SearchPackage.SEARCH_RESULT__DATE:
				return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
			case SearchPackage.SEARCH_RESULT__KEYWORDS:
				return KEYWORDS_EDEFAULT == null ? keywords != null : !KEYWORDS_EDEFAULT.equals(keywords);
			case SearchPackage.SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES:
				return highlightAttributes != null && !highlightAttributes.isEmpty();
			case SearchPackage.SEARCH_RESULT__QUERY:
				return QUERY_EDEFAULT == null ? query != null : !QUERY_EDEFAULT.equals(query);
			case SearchPackage.SEARCH_RESULT__SECONDARY_RESULTS:
				return secondaryResults != null && !secondaryResults.isEmpty();
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
		result.append(" (infoId: ");
		result.append(infoId);
		result.append(", title: ");
		result.append(title);
		result.append(", text: ");
		result.append(text);
		result.append(", path: ");
		result.append(path);
		result.append(", infoType: ");
		result.append(infoType);
		result.append(", date: ");
		result.append(date);
		result.append(", keywords: ");
		result.append(keywords);
		result.append(", highlightAttributes: ");
		result.append(highlightAttributes);
		result.append(", query: ");
		result.append(query);
		result.append(')');
		return result.toString();
	}

} //SearchResultImpl
