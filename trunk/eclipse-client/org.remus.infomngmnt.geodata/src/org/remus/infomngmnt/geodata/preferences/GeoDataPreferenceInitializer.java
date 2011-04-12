/*******************************************************************************
public boolean isDeletionWizard; * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.geodata.preferences;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.remus.infomngmnt.geodata.GeoDataActivator;

public class GeoDataPreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore store;
	public static final String GOOGLE_API_KEY = "GOOGLE_API_KEY"; //$NON-NLS-1$

	public GeoDataPreferenceInitializer() {
		this.store = GeoDataActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(GOOGLE_API_KEY, ""); //$NON-NLS-1$
	}
}
