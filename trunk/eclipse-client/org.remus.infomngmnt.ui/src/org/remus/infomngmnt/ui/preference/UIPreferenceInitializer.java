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
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Point;

import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIPreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore store;
	public static final String TRAY_ON_MINIMIZE = "TRAY_ON_MINIMZIE"; //$NON-NLS-1$
	public static final String TRAY_ON_CLOSE = "TRAY_ON_CLOSE"; //$NON-NLS-1$
	public static final String TRAY_ON_START = "TRAY_ON_START"; //$NON-NLS-1$
	public static final String DESKTOP_LOCATION = "DESKTOP_LOCATION"; //$NON-NLS-1$
	public static final String SHOW_WELCOME = "SHOW_WELCOME"; //$NON-NLS-1$

	public static final String FAQ_LINK = "FAQ_LINK"; //$NON-NLS-1$
	public static final String USER_DOCUMENTATION_LINK = "USER_DOCUMENTATION_LINK"; //$NON-NLS-1$
	public static final String VIDEO_LINK = "VIDEO_LINK"; //$NON-NLS-1$

	public static final String AMOUNT_SHOWN_NOTIFICATIONS_TRAY = "AMOUNT_SHOWN_NOTIFICATIONS_TRAY"; //$NON-NLS-1$

	/**
	 * 
	 */
	public UIPreferenceInitializer() {
		this.store = UIPlugin.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(TRAY_ON_MINIMIZE, true);
		this.store.setDefault(TRAY_ON_CLOSE, false);
		this.store.setDefault(TRAY_ON_START, false);
		this.store.setDefault(SHOW_WELCOME, true);
		this.store.setDefault(FAQ_LINK, "http://remus-software.org/faq");
		this.store.setDefault(USER_DOCUMENTATION_LINK,
				"http://remus-software.org/user-documentation");
		this.store.setDefault(VIDEO_LINK,
				" http://remus-software.org/media/screencasts/rim_5_minutes/rim_5_minutes.html");
		PreferenceConverter.setDefault(this.store, DESKTOP_LOCATION, new Point(10000, 10000));
		this.store.setDefault(AMOUNT_SHOWN_NOTIFICATIONS_TRAY, 3);
	}

}
