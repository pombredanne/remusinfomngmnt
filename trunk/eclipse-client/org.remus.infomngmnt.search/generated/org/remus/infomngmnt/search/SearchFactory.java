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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.remus.infomngmnt.search.SearchPackage
 * @generated
 */
public interface SearchFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SearchFactory eINSTANCE = org.remus.infomngmnt.search.impl.SearchFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Search</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Search</em>'.
	 * @generated
	 */
	Search createSearch();

	/**
	 * Returns a new object of class '<em>Result</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Result</em>'.
	 * @generated
	 */
	SearchResult createSearchResult();

	/**
	 * Returns a new object of class '<em>Saved Searches</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Saved Searches</em>'.
	 * @generated
	 */
	SavedSearches createSavedSearches();

	/**
	 * Returns a new object of class '<em>Latest Search Strings</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Latest Search Strings</em>'.
	 * @generated
	 */
	LatestSearchStrings createLatestSearchStrings();

	/**
	 * Returns a new object of class '<em>Websearches</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Websearches</em>'.
	 * @generated
	 */
	Websearches createWebsearches();

	/**
	 * Returns a new object of class '<em>Websearch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Websearch</em>'.
	 * @generated
	 */
	Websearch createWebsearch();

	/**
	 * Returns a new object of class '<em>History</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>History</em>'.
	 * @generated
	 */
	SearchHistory createSearchHistory();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SearchPackage getSearchPackage();

} //SearchFactory