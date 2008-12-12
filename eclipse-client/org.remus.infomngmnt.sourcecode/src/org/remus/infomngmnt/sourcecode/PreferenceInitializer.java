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

package org.remus.infomngmnt.sourcecode;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {


	public static final String LINE_NUMBERS = "LINE_NUMBERS"; //$NON-NLS-1$
	public static final String COLOR_SCHEME = "COLOR_SCHEME"; //$NON-NLS-1$
	private final IPreferenceStore store;
	/**
	 * 
	 */
	public PreferenceInitializer() {
		this.store = SourceCodePlugin.getDefault().getPreferenceStore();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(LINE_NUMBERS, false);
		this.store.setDefault(COLOR_SCHEME, "default"); //$NON-NLS-1$

	}

}
