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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.search.SearchFactory
 * @model kind="package"
 * @generated
 */
public interface SearchPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "search";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://remus-software.org/search";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "search";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SearchPackage eINSTANCE = org.remus.infomngmnt.search.impl.SearchPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.search.impl.SearchImpl <em>Search</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.search.impl.SearchImpl
	 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSearch()
	 * @generated
	 */
	int SEARCH = 0;

	/**
	 * The feature id for the '<em><b>Search String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__SEARCH_STRING = 0;

	/**
	 * The feature id for the '<em><b>Date Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__DATE_START = 1;

	/**
	 * The feature id for the '<em><b>End Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__END_DATE = 2;

	/**
	 * The feature id for the '<em><b>Info Type</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__INFO_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__SCOPE = 4;

	/**
	 * The feature id for the '<em><b>Result</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__RESULT = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__ID = 6;

	/**
	 * The feature id for the '<em><b>Id Search</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__ID_SEARCH = 7;

	/**
	 * The feature id for the '<em><b>Projects</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH__PROJECTS = 8;

	/**
	 * The number of structural features of the '<em>Search</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.search.impl.SearchResultImpl <em>Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.search.impl.SearchResultImpl
	 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSearchResult()
	 * @generated
	 */
	int SEARCH_RESULT = 1;

	/**
	 * The feature id for the '<em><b>Info Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__INFO_ID = 0;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__TITLE = 1;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__TEXT = 2;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__PATH = 3;

	/**
	 * The feature id for the '<em><b>Info Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__INFO_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__DATE = 5;

	/**
	 * The feature id for the '<em><b>Keywords</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__KEYWORDS = 6;

	/**
	 * The feature id for the '<em><b>Highlight Attributes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES = 7;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__QUERY = 8;

	/**
	 * The feature id for the '<em><b>Secondary Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT__SECONDARY_RESULTS = 9;

	/**
	 * The number of structural features of the '<em>Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_RESULT_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.search.impl.SavedSearchesImpl <em>Saved Searches</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.search.impl.SavedSearchesImpl
	 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSavedSearches()
	 * @generated
	 */
	int SAVED_SEARCHES = 2;

	/**
	 * The feature id for the '<em><b>Searches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAVED_SEARCHES__SEARCHES = 0;

	/**
	 * The number of structural features of the '<em>Saved Searches</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SAVED_SEARCHES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.search.impl.LatestSearchStringsImpl <em>Latest Search Strings</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.search.impl.LatestSearchStringsImpl
	 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getLatestSearchStrings()
	 * @generated
	 */
	int LATEST_SEARCH_STRINGS = 3;

	/**
	 * The feature id for the '<em><b>Strings</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LATEST_SEARCH_STRINGS__STRINGS = 0;

	/**
	 * The number of structural features of the '<em>Latest Search Strings</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LATEST_SEARCH_STRINGS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.search.impl.SearchHistoryImpl <em>History</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.search.impl.SearchHistoryImpl
	 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSearchHistory()
	 * @generated
	 */
	int SEARCH_HISTORY = 4;

	/**
	 * The feature id for the '<em><b>Search</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_HISTORY__SEARCH = 0;

	/**
	 * The number of structural features of the '<em>History</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEARCH_HISTORY_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.search.impl.SecondaryGroupContainerImpl <em>Secondary Group Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.search.impl.SecondaryGroupContainerImpl
	 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSecondaryGroupContainer()
	 * @generated
	 */
	int SECONDARY_GROUP_CONTAINER = 5;

	/**
	 * The feature id for the '<em><b>Info Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__INFO_ID = SEARCH_RESULT__INFO_ID;

	/**
	 * The feature id for the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__TITLE = SEARCH_RESULT__TITLE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__TEXT = SEARCH_RESULT__TEXT;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__PATH = SEARCH_RESULT__PATH;

	/**
	 * The feature id for the '<em><b>Info Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__INFO_TYPE = SEARCH_RESULT__INFO_TYPE;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__DATE = SEARCH_RESULT__DATE;

	/**
	 * The feature id for the '<em><b>Keywords</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__KEYWORDS = SEARCH_RESULT__KEYWORDS;

	/**
	 * The feature id for the '<em><b>Highlight Attributes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__HIGHLIGHT_ATTRIBUTES = SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES;

	/**
	 * The feature id for the '<em><b>Query</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__QUERY = SEARCH_RESULT__QUERY;

	/**
	 * The feature id for the '<em><b>Secondary Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER__SECONDARY_RESULTS = SEARCH_RESULT__SECONDARY_RESULTS;

	/**
	 * The number of structural features of the '<em>Secondary Group Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECONDARY_GROUP_CONTAINER_FEATURE_COUNT = SEARCH_RESULT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.remus.infomngmnt.search.SearchScope <em>Scope</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.remus.infomngmnt.search.SearchScope
	 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSearchScope()
	 * @generated
	 */
	int SEARCH_SCOPE = 6;

	/**
	 * The meta object id for the '<em>EAttribute</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EAttribute
	 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getEAttribute()
	 * @generated
	 */
	int EATTRIBUTE = 7;


	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.search.Search <em>Search</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Search</em>'.
	 * @see org.remus.infomngmnt.search.Search
	 * @generated
	 */
	EClass getSearch();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.Search#getSearchString <em>Search String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Search String</em>'.
	 * @see org.remus.infomngmnt.search.Search#getSearchString()
	 * @see #getSearch()
	 * @generated
	 */
	EAttribute getSearch_SearchString();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.Search#getDateStart <em>Date Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date Start</em>'.
	 * @see org.remus.infomngmnt.search.Search#getDateStart()
	 * @see #getSearch()
	 * @generated
	 */
	EAttribute getSearch_DateStart();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.Search#getEndDate <em>End Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Date</em>'.
	 * @see org.remus.infomngmnt.search.Search#getEndDate()
	 * @see #getSearch()
	 * @generated
	 */
	EAttribute getSearch_EndDate();

	/**
	 * Returns the meta object for the attribute list '{@link org.remus.infomngmnt.search.Search#getInfoType <em>Info Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Info Type</em>'.
	 * @see org.remus.infomngmnt.search.Search#getInfoType()
	 * @see #getSearch()
	 * @generated
	 */
	EAttribute getSearch_InfoType();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.Search#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scope</em>'.
	 * @see org.remus.infomngmnt.search.Search#getScope()
	 * @see #getSearch()
	 * @generated
	 */
	EAttribute getSearch_Scope();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.search.Search#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Result</em>'.
	 * @see org.remus.infomngmnt.search.Search#getResult()
	 * @see #getSearch()
	 * @generated
	 */
	EReference getSearch_Result();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.Search#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.remus.infomngmnt.search.Search#getId()
	 * @see #getSearch()
	 * @generated
	 */
	EAttribute getSearch_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.Search#isIdSearch <em>Id Search</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id Search</em>'.
	 * @see org.remus.infomngmnt.search.Search#isIdSearch()
	 * @see #getSearch()
	 * @generated
	 */
	EAttribute getSearch_IdSearch();

	/**
	 * Returns the meta object for the attribute list '{@link org.remus.infomngmnt.search.Search#getProjects <em>Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Projects</em>'.
	 * @see org.remus.infomngmnt.search.Search#getProjects()
	 * @see #getSearch()
	 * @generated
	 */
	EAttribute getSearch_Projects();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.search.SearchResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Result</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult
	 * @generated
	 */
	EClass getSearchResult();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.SearchResult#getInfoId <em>Info Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Info Id</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getInfoId()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_InfoId();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.SearchResult#getTitle <em>Title</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Title</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getTitle()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_Title();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.SearchResult#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getText()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_Text();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.SearchResult#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getPath()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_Path();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.SearchResult#getInfoType <em>Info Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Info Type</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getInfoType()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_InfoType();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.SearchResult#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getDate()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_Date();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.SearchResult#getKeywords <em>Keywords</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Keywords</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getKeywords()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_Keywords();

	/**
	 * Returns the meta object for the attribute list '{@link org.remus.infomngmnt.search.SearchResult#getHighlightAttributes <em>Highlight Attributes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Highlight Attributes</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getHighlightAttributes()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_HighlightAttributes();

	/**
	 * Returns the meta object for the attribute '{@link org.remus.infomngmnt.search.SearchResult#getQuery <em>Query</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Query</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getQuery()
	 * @see #getSearchResult()
	 * @generated
	 */
	EAttribute getSearchResult_Query();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.search.SearchResult#getSecondaryResults <em>Secondary Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Secondary Results</em>'.
	 * @see org.remus.infomngmnt.search.SearchResult#getSecondaryResults()
	 * @see #getSearchResult()
	 * @generated
	 */
	EReference getSearchResult_SecondaryResults();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.search.SavedSearches <em>Saved Searches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Saved Searches</em>'.
	 * @see org.remus.infomngmnt.search.SavedSearches
	 * @generated
	 */
	EClass getSavedSearches();

	/**
	 * Returns the meta object for the containment reference list '{@link org.remus.infomngmnt.search.SavedSearches#getSearches <em>Searches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Searches</em>'.
	 * @see org.remus.infomngmnt.search.SavedSearches#getSearches()
	 * @see #getSavedSearches()
	 * @generated
	 */
	EReference getSavedSearches_Searches();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.search.LatestSearchStrings <em>Latest Search Strings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Latest Search Strings</em>'.
	 * @see org.remus.infomngmnt.search.LatestSearchStrings
	 * @generated
	 */
	EClass getLatestSearchStrings();

	/**
	 * Returns the meta object for the attribute list '{@link org.remus.infomngmnt.search.LatestSearchStrings#getStrings <em>Strings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Strings</em>'.
	 * @see org.remus.infomngmnt.search.LatestSearchStrings#getStrings()
	 * @see #getLatestSearchStrings()
	 * @generated
	 */
	EAttribute getLatestSearchStrings_Strings();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.search.SearchHistory <em>History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>History</em>'.
	 * @see org.remus.infomngmnt.search.SearchHistory
	 * @generated
	 */
	EClass getSearchHistory();

	/**
	 * Returns the meta object for the reference list '{@link org.remus.infomngmnt.search.SearchHistory#getSearch <em>Search</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Search</em>'.
	 * @see org.remus.infomngmnt.search.SearchHistory#getSearch()
	 * @see #getSearchHistory()
	 * @generated
	 */
	EReference getSearchHistory_Search();

	/**
	 * Returns the meta object for class '{@link org.remus.infomngmnt.search.SecondaryGroupContainer <em>Secondary Group Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Secondary Group Container</em>'.
	 * @see org.remus.infomngmnt.search.SecondaryGroupContainer
	 * @generated
	 */
	EClass getSecondaryGroupContainer();

	/**
	 * Returns the meta object for enum '{@link org.remus.infomngmnt.search.SearchScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Scope</em>'.
	 * @see org.remus.infomngmnt.search.SearchScope
	 * @generated
	 */
	EEnum getSearchScope();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.emf.ecore.EAttribute <em>EAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>EAttribute</em>'.
	 * @see org.eclipse.emf.ecore.EAttribute
	 * @model instanceClass="org.eclipse.emf.ecore.EAttribute"
	 * @generated
	 */
	EDataType getEAttribute();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SearchFactory getSearchFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.search.impl.SearchImpl <em>Search</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.search.impl.SearchImpl
		 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSearch()
		 * @generated
		 */
		EClass SEARCH = eINSTANCE.getSearch();

		/**
		 * The meta object literal for the '<em><b>Search String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH__SEARCH_STRING = eINSTANCE.getSearch_SearchString();

		/**
		 * The meta object literal for the '<em><b>Date Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH__DATE_START = eINSTANCE.getSearch_DateStart();

		/**
		 * The meta object literal for the '<em><b>End Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH__END_DATE = eINSTANCE.getSearch_EndDate();

		/**
		 * The meta object literal for the '<em><b>Info Type</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH__INFO_TYPE = eINSTANCE.getSearch_InfoType();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH__SCOPE = eINSTANCE.getSearch_Scope();

		/**
		 * The meta object literal for the '<em><b>Result</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEARCH__RESULT = eINSTANCE.getSearch_Result();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH__ID = eINSTANCE.getSearch_Id();

		/**
		 * The meta object literal for the '<em><b>Id Search</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH__ID_SEARCH = eINSTANCE.getSearch_IdSearch();

		/**
		 * The meta object literal for the '<em><b>Projects</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH__PROJECTS = eINSTANCE.getSearch_Projects();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.search.impl.SearchResultImpl <em>Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.search.impl.SearchResultImpl
		 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSearchResult()
		 * @generated
		 */
		EClass SEARCH_RESULT = eINSTANCE.getSearchResult();

		/**
		 * The meta object literal for the '<em><b>Info Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__INFO_ID = eINSTANCE.getSearchResult_InfoId();

		/**
		 * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__TITLE = eINSTANCE.getSearchResult_Title();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__TEXT = eINSTANCE.getSearchResult_Text();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__PATH = eINSTANCE.getSearchResult_Path();

		/**
		 * The meta object literal for the '<em><b>Info Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__INFO_TYPE = eINSTANCE.getSearchResult_InfoType();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__DATE = eINSTANCE.getSearchResult_Date();

		/**
		 * The meta object literal for the '<em><b>Keywords</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__KEYWORDS = eINSTANCE.getSearchResult_Keywords();

		/**
		 * The meta object literal for the '<em><b>Highlight Attributes</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__HIGHLIGHT_ATTRIBUTES = eINSTANCE.getSearchResult_HighlightAttributes();

		/**
		 * The meta object literal for the '<em><b>Query</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEARCH_RESULT__QUERY = eINSTANCE.getSearchResult_Query();

		/**
		 * The meta object literal for the '<em><b>Secondary Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEARCH_RESULT__SECONDARY_RESULTS = eINSTANCE.getSearchResult_SecondaryResults();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.search.impl.SavedSearchesImpl <em>Saved Searches</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.search.impl.SavedSearchesImpl
		 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSavedSearches()
		 * @generated
		 */
		EClass SAVED_SEARCHES = eINSTANCE.getSavedSearches();

		/**
		 * The meta object literal for the '<em><b>Searches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SAVED_SEARCHES__SEARCHES = eINSTANCE.getSavedSearches_Searches();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.search.impl.LatestSearchStringsImpl <em>Latest Search Strings</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.search.impl.LatestSearchStringsImpl
		 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getLatestSearchStrings()
		 * @generated
		 */
		EClass LATEST_SEARCH_STRINGS = eINSTANCE.getLatestSearchStrings();

		/**
		 * The meta object literal for the '<em><b>Strings</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LATEST_SEARCH_STRINGS__STRINGS = eINSTANCE.getLatestSearchStrings_Strings();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.search.impl.SearchHistoryImpl <em>History</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.search.impl.SearchHistoryImpl
		 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSearchHistory()
		 * @generated
		 */
		EClass SEARCH_HISTORY = eINSTANCE.getSearchHistory();

		/**
		 * The meta object literal for the '<em><b>Search</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEARCH_HISTORY__SEARCH = eINSTANCE.getSearchHistory_Search();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.search.impl.SecondaryGroupContainerImpl <em>Secondary Group Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.search.impl.SecondaryGroupContainerImpl
		 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSecondaryGroupContainer()
		 * @generated
		 */
		EClass SECONDARY_GROUP_CONTAINER = eINSTANCE.getSecondaryGroupContainer();

		/**
		 * The meta object literal for the '{@link org.remus.infomngmnt.search.SearchScope <em>Scope</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.remus.infomngmnt.search.SearchScope
		 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getSearchScope()
		 * @generated
		 */
		EEnum SEARCH_SCOPE = eINSTANCE.getSearchScope();

		/**
		 * The meta object literal for the '<em>EAttribute</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.ecore.EAttribute
		 * @see org.remus.infomngmnt.search.impl.SearchPackageImpl#getEAttribute()
		 * @generated
		 */
		EDataType EATTRIBUTE = eINSTANCE.getEAttribute();

	}

} //SearchPackage
