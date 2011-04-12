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

package org.remus.infomngmnt.connector.youtube.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.remus.infomngmnt.connector.youtube.YoutubeActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public static final String GDATA_SERVER_URL = "GDATA_SERVER_URL"; //$NON-NLS-1$

	public static final String PLAYLIST_URL = "PLAYLIST_URL"; //$NON-NLS-1$

	public static final String FAVORITES_URL = "FAVORITES_URL"; //$NON-NLS-1$

	public static final String HIGH_DEFINITION_DOWNLOAD_URL = "HIGH_DEFINITION_DOWNLOAD_URL"; //$NON-NLS-1$

	public static final String HIGH_QUALITY_DOWNLOAD_URL = "HIGH_QUALITY_DOWNLOAD_URL"; //$NON-NLS-1$

	public static final String NORMAL_DOWNLOAD_URL = "NORMAL_DOWNLOAD_URL"; //$NON-NLS-1$

	public static final String VIDEO_HTML_URL = "VIDEO_HTML_URL"; //$NON-NLS-1$

	public static final String ALWAYS_HD = "ALWAYS_HD"; //$NON-NLS-1$

	private final IPreferenceStore store;

	/**
	 * 
	 */
	public PreferenceInitializer() {
		store = YoutubeActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		store.setDefault(GDATA_SERVER_URL, "http://gdata.youtube.com"); //$NON-NLS-1$
		store.setDefault(VIDEO_HTML_URL, "http://www.youtube.com/watch?v={0}"); //$NON-NLS-1$
		store.setDefault(FAVORITES_URL,
				"http://gdata.youtube.com/feeds/api/users/{0}/favorites"); //$NON-NLS-1$
		store.setDefault(PLAYLIST_URL,
				"http://gdata.youtube.com/feeds/api/users/{0}/playlists"); //$NON-NLS-1$
		store.setDefault(HIGH_DEFINITION_DOWNLOAD_URL,
				"http://www.youtube.com/get_video?video_id={0}&t={1}&fmt=22"); //$NON-NLS-1$
		store.setDefault(HIGH_QUALITY_DOWNLOAD_URL,
				"http://www.youtube.com/get_video?video_id={0}&t={1}&fmt=18"); //$NON-NLS-1$
		store.setDefault(NORMAL_DOWNLOAD_URL,
				"http://www.youtube.com/get_video?video_id={0}&t={1}"); //$NON-NLS-1$
		store.setDefault(ALWAYS_HD, true);

	}

}
