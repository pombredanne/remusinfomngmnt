/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.efs.file;

import java.io.File;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public static final String KEY_FILENAME = "KEY_FILENAME"; //$NON-NLS-1$

	public static final String LOCATION_KEY_FILE = "LOCATION_KEY_FILE"; //$NON-NLS-1$
	private final IPreferenceStore preferenceStore;

	/**
	 * 
	 */
	public PreferenceInitializer() {
		this.preferenceStore = EFSFileActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.preferenceStore.setDefault(LOCATION_KEY_FILE, System.getProperty("user.home")
				+ File.separator + ".rimsecurity");
		this.preferenceStore.setDefault(KEY_FILENAME, "key.dat");
	}
}
