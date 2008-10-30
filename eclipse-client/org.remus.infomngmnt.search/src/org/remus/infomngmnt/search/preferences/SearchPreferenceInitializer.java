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

package org.remus.infomngmnt.search.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.remus.infomngmnt.search.provider.SearchPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchPreferenceInitializer extends AbstractPreferenceInitializer {

	public static final String KEEP_X_SEARCHES_IN_HISTORY = "KEEP_X_SEARCHES_IN_HISTORY"; //$NON-NLS-1$
	private final IPreferenceStore store;

	/**
	 * 
	 */
	public SearchPreferenceInitializer() {
		this.store = SearchPlugin.getPlugin().getPreferenceStore();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(KEEP_X_SEARCHES_IN_HISTORY, 20);

	}

}
