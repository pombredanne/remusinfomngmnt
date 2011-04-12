/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.pdf.renderer;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore preferenceStore;

	public static final String PATH = "PATH"; //$NON-NLS-1$
	public static final String OPTIONS = "OPTIONS"; //$NON-NLS-1$

	/**
	 * 
	 */
	public PreferenceInitializer() {
		preferenceStore = Activator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		preferenceStore
				.setDefault(
						OPTIONS,
						"-dSAFER -dBATCH -dNOPAUSE -sDEVICE=pngalpha -r150 -dTextAlphaBits=4 -dGraphicsAlphaBits=4 -dMaxStripSize=8192"); //$NON-NLS-1$

	}
}
