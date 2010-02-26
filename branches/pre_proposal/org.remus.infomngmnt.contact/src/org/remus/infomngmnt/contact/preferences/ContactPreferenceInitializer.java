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

package org.remus.infomngmnt.contact.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.remus.infomngmnt.contact.ContactActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ContactPreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore store;

	public static final String SHOW_PHONE_LINKS = "SHOW_PHONE_LINKS"; //$NON-NLS-1$
	public static final String PHONE_CALL_PATTERN = "PHONE_CALL_PATTERN"; //$NON-NLS-1$
	public static final String SHOW_MAPS_IMAGE = "SHOW_MAPS_IMAGE"; //$NON-NLS-1$
	public static final String MAPS_IMAGE_WIDTH = "MAPS_IMAGE_WIDTH"; //$NON-NLS-1$
	public static final String MAPS_IMAGE_HEIGHT = "MAPS_IMAGE_HEIGHT"; //$NON-NLS-1$
	public static final String MAPS_IMAGE_ZOOMLEVEL = "MAPS_IMAGE_ZOOMLEVEL"; //$NON-NLS-1$
	public static final String FORMATTED_NAME_PATTERN = "FORMATTED_NAME_PATTERN"; //$NON-NLS-1$
	public static final String FORMATTED_ADRESS_PATTERN = "FORMATTED_ADRESS_PATTERN"; //$NON-NLS-1$

	/**
	 * 
	 */
	public ContactPreferenceInitializer() {
		this.store = ContactActivator.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#
	 * initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(SHOW_PHONE_LINKS, false);
		this.store.setDefault(FORMATTED_NAME_PATTERN, "$title $firstname $lastname");
		this.store.setDefault(FORMATTED_ADRESS_PATTERN, "$street\n\n$postal $locality");
		this.store.setDefault(MAPS_IMAGE_WIDTH, 300);
		this.store.setDefault(MAPS_IMAGE_HEIGHT, 300);
		this.store.setDefault(MAPS_IMAGE_ZOOMLEVEL, 10);
		this.store.setDefault(PHONE_CALL_PATTERN, "skype://{0}");
	}

}
