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

package org.remus.infomngmnt.link.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.remus.infomngmnt.link.LinkActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LinkPreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore store;
	public static final String INDEX_DOCUMENT = "INDEX_DOCUMENT"; //$NON-NLS-1$
	public static final String MAKE_SCREENSHOT = "MAKE_SCREENSHOT"; //$NON-NLS-1$
	public static final String SCREENSHOT_CMD = "SCREENSHOT_CMD"; //$NON-NLS-1$

	/**
	 * 
	 */
	public LinkPreferenceInitializer() {
		this.store = LinkActivator.getDefault().getPreferenceStore();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(INDEX_DOCUMENT, true);
		this.store.setDefault(MAKE_SCREENSHOT, true);
		this.store.setDefault(SCREENSHOT_CMD, "C:\\Downloads\\IECapt.exe --url=\"{URL}\" --out=\"{OUT}\" --silent"); //$NON-NLS-1$
	}

}
