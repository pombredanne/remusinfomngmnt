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

package org.remus.infomngmnt.ui.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIPreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore store;
	public static final String TRAY_ON_MINIMIZE = "TRAY_ON_MINIMZIE"; //$NON-NLS-1$
	public static final String TRAY_ON_CLOSE = "TRAY_ON_CLOSE"; //$NON-NLS-1$
	public static final String TRAY_ON_START = "TRAY_ON_START"; //$NON-NLS-1$
	/**
	 * 
	 */
	public UIPreferenceInitializer() {
		this.store = UIPlugin.getDefault().getPreferenceStore();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(TRAY_ON_MINIMIZE, true);
		this.store.setDefault(TRAY_ON_CLOSE, false);
		this.store.setDefault(TRAY_ON_START, false);

	}

}
