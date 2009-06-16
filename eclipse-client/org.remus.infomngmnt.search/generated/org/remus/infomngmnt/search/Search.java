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
package org.remus.infomngmnt.search;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Search</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.remus.infomngmnt.search.Search#getSearchString <em>Search String</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.Search#getDateStart <em>Date Start</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.Search#getEndDate <em>End Date</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.Search#getInfoType <em>Info Type</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.Search#getScope <em>Scope</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.Search#getResult <em>Result</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.Search#getId <em>Id</em>}</li>
 *   <li>{@link org.remus.infomngmnt.search.Search#isIdSearch <em>Id Search</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.remus.infomngmnt.search.SearchPackage#getSearch()
 * @model
 * @generated
 */
public interface Search extends EObject {
	/**
	 * Returns the value of the '<em><b>Search String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Search String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Search String</em>' attribute.
	 * @see #setSearchString(String)
	 * @see org.remus.infomngmnt.search.SearchPackage#getSearch_SearchString()
	 * @model
	 * @generated
	 */
	String getSearchString();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.search.Search#getSearchString <em>Search String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Search String</em>' attribute.
	 * @see #getSearchString()
	 * @generated
	 */
	void setSearchString(String value);

	/**
	 * Returns the value of the '<em><b>Date Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Start</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Start</em>' attribute.
	 * @see #setDateStart(Date)
	 * @see org.remus.infomngmnt.search.SearchPackage#getSearch_DateStart()
	 * @model
	 * @generated
	 */
	Date getDateStart();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.search.Search#getDateStart <em>Date Start</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Start</em>' attribute.
	 * @see #getDateStart()
	 * @generated
	 */
	void setDateStart(Date value);

	/**
	 * Returns the value of the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Date</em>' attribute.
	 * @see #setEndDate(Date)
	 * @see org.remus.infomngmnt.search.SearchPackage#getSearch_EndDate()
	 * @model
	 * @generated
	 */
	Date getEndDate();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.search.Search#getEndDate <em>End Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Date</em>' attribute.
	 * @see #getEndDate()
	 * @generated
	 */
	void setEndDate(Date value);

	/**
	 * Returns the value of the '<em><b>Info Type</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Info Type</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Info Type</em>' attribute list.
	 * @see org.remus.infomngmnt.search.SearchPackage#getSearch_InfoType()
	 * @model
	 * @generated
	 */
	EList<String> getInfoType();

	/**
	 * Returns the value of the '<em><b>Scope</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * The literals are from the enumeration {@link org.remus.infomngmnt.search.SearchScope}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scope</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scope</em>' attribute.
	 * @see org.remus.infomngmnt.search.SearchScope
	 * @see #setScope(SearchScope)
	 * @see org.remus.infomngmnt.search.SearchPackage#getSearch_Scope()
	 * @model default="0"
	 * @generated
	 */
	SearchScope getScope();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.search.Search#getScope <em>Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scope</em>' attribute.
	 * @see org.remus.infomngmnt.search.SearchScope
	 * @see #getScope()
	 * @generated
	 */
	void setScope(SearchScope value);

	/**
	 * Returns the value of the '<em><b>Result</b></em>' containment reference list.
	 * The list contents are of type {@link org.remus.infomngmnt.search.SearchResult}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result</em>' containment reference list.
	 * @see org.remus.infomngmnt.search.SearchPackage#getSearch_Result()
	 * @model containment="true"
	 * @generated
	 */
	EList<SearchResult> getResult();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.remus.infomngmnt.search.SearchPackage#getSearch_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.search.Search#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Id Search</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Search</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Search</em>' attribute.
	 * @see #setIdSearch(boolean)
	 * @see org.remus.infomngmnt.search.SearchPackage#getSearch_IdSearch()
	 * @model
	 * @generated
	 */
	boolean isIdSearch();

	/**
	 * Sets the value of the '{@link org.remus.infomngmnt.search.Search#isIdSearch <em>Id Search</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Search</em>' attribute.
	 * @see #isIdSearch()
	 * @generated
	 */
	void setIdSearch(boolean value);

} // Search