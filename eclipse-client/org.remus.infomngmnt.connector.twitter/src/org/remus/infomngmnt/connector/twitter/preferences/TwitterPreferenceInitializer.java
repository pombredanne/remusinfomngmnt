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

package org.remus.infomngmnt.connector.twitter.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.remus.infomngmnt.connector.twitter.TwitterActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterPreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore store;

	public static final String SHOWN_MESSAGE = "SHOWN_MESSAGE"; //$NON-NLS-1$

	public static final String SAVED_MESSAGES = "SAVED_MESSAGES"; //$NON-NLS-1$

	public static final String CACHED_IMAGES = "CACHED_IMAGES"; //$NON-NLS-1$

	public static final String RELOAD_ALL_FRIENDS_FEED = "RELOAD_ALL_FRIENDS_FEED"; //$NON-NLS-1$

	public static final String RELOAD_DIRECT_MESSAGES_FEED = "RELOAD_DIRECT_MESSAGES_FEED"; //$NON-NLS-1$

	public static final String RELOAD_REPLIES_FEED = "RELOAD_REPLIES_FEED"; //$NON-NLS-1$

	public static final String RELOAD_SEARCH_FEEDS = "RELOAD_SEARCH_FEEDS"; //$NON-NLS-1$

	public static final String SEARCH_URL = "SEARCH_URL"; //$NON-NLS-1$

	/**
	 * 
	 */
	public TwitterPreferenceInitializer() {
		this.store = TwitterActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(SHOWN_MESSAGE, 30);
		this.store.setDefault(SAVED_MESSAGES, 200);
		this.store.setDefault(CACHED_IMAGES, 200);
		this.store.setDefault(RELOAD_ALL_FRIENDS_FEED, 2);
		this.store.setDefault(RELOAD_DIRECT_MESSAGES_FEED, 5);
		this.store.setDefault(RELOAD_REPLIES_FEED, 15);
		this.store.setDefault(RELOAD_SEARCH_FEEDS, 15);
		this.store.setDefault(SEARCH_URL, "http://search.twitter.com/search?q=%s");

	}

}
