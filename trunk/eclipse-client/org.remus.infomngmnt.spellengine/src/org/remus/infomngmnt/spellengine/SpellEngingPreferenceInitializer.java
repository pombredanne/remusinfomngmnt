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

package org.remus.infomngmnt.spellengine;

import java.util.Locale;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.texteditor.spelling.SpellingService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SpellEngingPreferenceInitializer extends AbstractPreferenceInitializer {

	private final IPreferenceStore store;

	/**
	 * 
	 */
	public SpellEngingPreferenceInitializer() {
		this.store = SpellActivator.getDefault().getPreferenceStore();
	}

	@Override
	public void initializeDefaultPreferences() {
		this.store.setDefault(SpellingService.PREFERENCE_SPELLING_ENABLED, true);
		this.store.setDefault(SpellingService.PREFERENCE_SPELLING_ENGINE,
				"org.remus.infomngmnt.spellengine.DefaultSpellingEngine");

		this.store.setDefault(PreferenceConstants.SPELLING_LOCALE, "en_US"); //$NON-NLS-1$
		String isInitializedKey = "spelling_locale_initialized"; //$NON-NLS-1$
		if (!this.store.getBoolean(isInitializedKey)) {
			this.store.setValue(isInitializedKey, true);
			Locale locale = SpellCheckEngine.getDefaultLocale();
			locale = SpellCheckEngine.findClosestLocale(locale);
			if (locale != null)
				this.store.setValue(PreferenceConstants.SPELLING_LOCALE, locale.toString());
		}
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_DIGITS, true);
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_MIXED, true);
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_SENTENCE, true);
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_UPPER, true);
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_URLS, true);
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_SINGLE_LETTERS, true);
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_AMPERSAND_IN_PROPERTIES, true);
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_NON_LETTERS, true);
		this.store.setDefault(PreferenceConstants.SPELLING_IGNORE_JAVA_STRINGS, true);
		this.store.setDefault(PreferenceConstants.SPELLING_USER_DICTIONARY, ""); //$NON-NLS-1$

		// Note: For backwards compatibility we must use the property and not
		// the workspace default
		this.store.setDefault(PreferenceConstants.SPELLING_USER_DICTIONARY_ENCODING, System
				.getProperty("file.encoding")); //$NON-NLS-1$

		this.store.setDefault(PreferenceConstants.SPELLING_PROPOSAL_THRESHOLD, 20);
		this.store.setDefault(PreferenceConstants.SPELLING_PROBLEMS_THRESHOLD, 100);
		/*
		 * XXX: This is currently disabled because the spelling engine cannot
		 * return word proposals but only correction proposals.
		 */
		this.store.setToDefault(PreferenceConstants.SPELLING_ENABLE_CONTENTASSIST);

	}

}
