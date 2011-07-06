/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.connector.rss;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore preferenceStore;

	public static final String FIRST_START = "FIRST_START"; //$NON-NLS-1$

	/**
	 * 
	 */
	public PreferenceInitializer() {
		preferenceStore = RssActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		preferenceStore.setDefault(FIRST_START, true);

	}

}
