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
	public static final String URL_WEBSHOTHELP = "URL_WEBSHOTHELP"; //$NON-NLS-1$
	public static final String LIST_RENDERER = "LIST_RENDERER"; //$NON-NLS-1$
	public static final String LIST_RENDERER_URL = "LIST_RENDERER_URL"; //$NON-NLS-1$
	public static final String LIST_RENDERER_ARGUMENTS = "LIST_RENDERER_ARGUMENTS"; //$NON-NLS-1$
	public static final String RENDERER_SELECTED = "RENDERER_SELECTED"; //$NON-NLS-1$

	/**
	 * 
	 */
	public LinkPreferenceInitializer() {
		store = LinkActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		store.setDefault(INDEX_DOCUMENT, true);
		store.setDefault(MAKE_SCREENSHOT, true);
		store.setDefault(URL_WEBSHOTHELP,
				"http://remus-software.org/faq/10-why-is-webshotting-diabled"); //$NON-NLS-1$
		store.setDefault(LIST_RENDERER,
				"None,IECapt (Win),CutyCapt (Linux),Webkit2png (Mac)"); //$NON-NLS-1$
		store.setDefault(
				LIST_RENDERER_URL,
				",http://iecapt.sourceforge.net/,http://cutycapt.sourceforge.net/,http://www.paulhammond.org/webkit2png/"); //$NON-NLS-1$
		store.setDefault(
				LIST_RENDERER_ARGUMENTS,
				"|{LOC},--url=\"{URL}\",--out=\"{OUT}\",--silent|{LOC},--url={URL},--out={OUT}|python,{LOC},-o,{OUT},-F,{URL}"); //$NON-NLS-1$
		store.setDefault(RENDERER_SELECTED, 0);

		//this.store.setDefault(SCREENSHOT_CMD, "C:\\Downloads\\IECapt.exe --url=\"{URL}\" --out=\"{OUT}\" --silent"); //$NON-NLS-1$
	}

}
