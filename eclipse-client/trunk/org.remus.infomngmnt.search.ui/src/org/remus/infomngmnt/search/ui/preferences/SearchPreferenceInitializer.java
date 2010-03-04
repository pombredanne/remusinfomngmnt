/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.search.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.remus.infomngmnt.search.ui.SearchUIActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchPreferenceInitializer extends AbstractPreferenceInitializer {

	public static final String KEEP_X_SEARCHES_IN_HISTORY = "KEEP_X_SEARCHES_IN_HISTORY"; //$NON-NLS-1$
	public static final String LOCAL_SEARCH_FOLDER = "LOCAL_SEARCH_FOLDER"; //$NON-NLS-1$#
	public static final String URL_SEARCH_SYNTAX = "URL_SEARCH_SYNTAX"; //$NON-NLS-1$#
	private final IPreferenceStore store;

	/**
	 * 
	 */
	public SearchPreferenceInitializer() {
		this.store = SearchUIActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(KEEP_X_SEARCHES_IN_HISTORY, 20);
		this.store.setDefault(LOCAL_SEARCH_FOLDER, "localsearches"); //$NON-NLS-1$
		this.store.setDefault(URL_SEARCH_SYNTAX,
				"http://remus-software.org/user-documentation/13-search/43-search-syntax"); //$NON-NLS-1$

	}

}
