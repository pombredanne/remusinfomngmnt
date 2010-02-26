package org.remus.infomngmnt.spellengine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.variables.IStringVariableManager;
import org.eclipse.core.variables.VariablesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

/**
 * Spell check engine for Java source spell checking.
 * 
 * @since 3.0
 */
public class SpellCheckEngine implements ISpellCheckEngine, IPropertyChangeListener {

	/** The dictionary location */
	public static final String DICTIONARY_LOCATION = "dictionaries/"; //$NON-NLS-1$

	/** The singleton engine instance */
	private static ISpellCheckEngine fgEngine = null;

	/**
	 * Caches the locales of installed dictionaries.
	 * 
	 * @since 3.3
	 */
	private static Set fgLocalesWithInstalledDictionaries;

	/**
	 * Returns the locales for which this spell check engine has dictionaries in
	 * certain location.
	 * 
	 * @param location
	 *            dictionaries location
	 * @return The available locales for this engine
	 */
	private static Set getLocalesWithInstalledDictionaries(final URL location) {
		String[] fileNames;
		try {
			URL url = FileLocator.toFileURL(location);
			File file = new File(url.getFile());
			if (!file.isDirectory())
				return Collections.EMPTY_SET;
			fileNames = file.list();
			if (fileNames == null)
				return Collections.EMPTY_SET;
		} catch (IOException ex) {
			SpellActivator.log(ex);
			return Collections.EMPTY_SET;
		}

		Set localesWithInstalledDictionaries = new HashSet();
		int fileNameCount = fileNames.length;
		for (int i = 0; i < fileNameCount; i++) {
			String fileName = fileNames[i];
			int localeEnd = fileName.indexOf(".dictionary"); //$NON-NLS-1$
			if (localeEnd > 1) {
				String localeName = fileName.substring(0, localeEnd);
				int languageEnd = localeName.indexOf('_');
				if (languageEnd == -1)
					localesWithInstalledDictionaries.add(new Locale(localeName));
				else if (languageEnd == 2 && localeName.length() == 5)
					localesWithInstalledDictionaries.add(new Locale(localeName.substring(0, 2),
							localeName.substring(3)));
				else if (localeName.length() > 6 && localeName.charAt(5) == '_')
					localesWithInstalledDictionaries.add(new Locale(localeName.substring(0, 2),
							localeName.substring(3, 5), localeName.substring(6)));
			}
		}

		return localesWithInstalledDictionaries;
	}

	/**
	 * Returns the locales for which this spell check engine has dictionaries.
	 * 
	 * @return The available locales for this engine
	 */
	public static Set getLocalesWithInstalledDictionaries() {
		if (fgLocalesWithInstalledDictionaries != null)
			return fgLocalesWithInstalledDictionaries;

		Enumeration locations;
		try {
			locations = getDictionaryLocations();
			if (locations == null)
				return fgLocalesWithInstalledDictionaries = Collections.EMPTY_SET;
		} catch (IOException ex) {
			SpellActivator.log(ex);
			return fgLocalesWithInstalledDictionaries = Collections.EMPTY_SET;
		}

		fgLocalesWithInstalledDictionaries = new HashSet();

		while (locations.hasMoreElements()) {
			URL location = (URL) locations.nextElement();
			Set locales = getLocalesWithInstalledDictionaries(location);
			fgLocalesWithInstalledDictionaries.addAll(locales);
		}

		return fgLocalesWithInstalledDictionaries;
	}

	/**
	 * Returns the default locale for this engine.
	 * 
	 * @return The default locale
	 */
	public static Locale getDefaultLocale() {
		return Locale.getDefault();
	}

	/**
	 * Returns the dictionary closest to the given locale.
	 * 
	 * @param locale
	 *            the locale
	 * @return the dictionary or <code>null</code> if none is suitable
	 * @since 3.3
	 */
	public ISpellDictionary findDictionary(final Locale locale) {
		ISpellDictionary dictionary = (ISpellDictionary) this.fLocaleDictionaries.get(locale);
		if (dictionary != null)
			return dictionary;

		// Try same language
		String language = locale.getLanguage();
		Iterator iter = this.fLocaleDictionaries.entrySet().iterator();
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			Locale dictLocale = (Locale) entry.getKey();
			if (dictLocale.getLanguage().equals(language))
				return (ISpellDictionary) entry.getValue();
		}

		return null;
	}

	/*
	 * @seeorg.eclipse.jdt.internal.ui.text.spelling.engine.ISpellCheckEngine#
	 * findDictionary(java.util.Locale)
	 * 
	 * @since 3.3
	 */
	public static Locale findClosestLocale(final Locale locale) {
		if (locale == null || locale.toString().length() == 0)
			return locale;

		if (getLocalesWithInstalledDictionaries().contains(locale))
			return locale;

		// Try same language
		String language = locale.getLanguage();
		Iterator iter = getLocalesWithInstalledDictionaries().iterator();
		while (iter.hasNext()) {
			Locale dictLocale = (Locale) iter.next();
			if (dictLocale.getLanguage().equals(language))
				return dictLocale;
		}

		// Try whether American English is present
		Locale defaultLocale = Locale.US;
		if (getLocalesWithInstalledDictionaries().contains(defaultLocale))
			return defaultLocale;

		return null;
	}

	/**
	 * Returns the enumeration of URLs for the dictionary locations where the
	 * Platform dictionaries are located.
	 * <p>
	 * This is in <code>org.eclipse.jdt.ui/dictionaries/</code> which can also
	 * be populated via fragments.
	 * </p>
	 * 
	 * @throws IOException
	 *             if there is an I/O error
	 * @return The dictionary locations, or <code>null</code> iff the locations
	 *         are not known
	 */
	public static Enumeration getDictionaryLocations() throws IOException {
		final SpellActivator plugin = SpellActivator.getDefault();
		if (plugin != null)
			return plugin.getBundle().getResources("/" + DICTIONARY_LOCATION); //$NON-NLS-1$
		return null;
	}

	/**
	 * Returns the singleton instance of the spell check engine.
	 * 
	 * @return The singleton instance of the spell check engine
	 */
	public static final synchronized ISpellCheckEngine getInstance() {

		if (fgEngine == null)
			fgEngine = new SpellCheckEngine();

		return fgEngine;
	}

	/**
	 * Shuts down the singleton instance of the spell check engine.
	 */
	public static final synchronized void shutdownInstance() {
		if (fgEngine != null) {
			fgEngine.shutdown();
			fgEngine = null;
		}
	}

	/** The registered locale insensitive dictionaries */
	private Set fGlobalDictionaries = new HashSet();

	/** The spell checker for fLocale */
	private ISpellChecker fChecker = null;

	/** The registered locale sensitive dictionaries */
	private Map fLocaleDictionaries = new HashMap();

	/** The user dictionary */
	private ISpellDictionary fUserDictionary = null;

	/**
	 * Creates a new spell check manager.
	 */
	private SpellCheckEngine() {

		// this.fGlobalDictionaries.add(new TaskTagDictionary());
		// this.fGlobalDictionaries.add(new HtmlTagDictionary());
		// this.fGlobalDictionaries.add(new JavaDocTagDictionary());

		try {

			Locale locale = null;
			final Enumeration locations = getDictionaryLocations();

			while (locations != null && locations.hasMoreElements()) {
				URL location = (URL) locations.nextElement();

				for (final Iterator iterator = getLocalesWithInstalledDictionaries(location)
						.iterator(); iterator.hasNext();) {

					locale = (Locale) iterator.next();
					this.fLocaleDictionaries.put(locale, new LocaleSensitiveSpellDictionary(locale,
							location));
				}
			}

		} catch (IOException exception) {
			// Do nothing
		}

		SpellActivator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
	}

	/*
	 * @seeorg.eclipse.jdt.internal.ui.text.spelling.engine.ISpellCheckEngine#
	 * getSpellChecker()
	 */
	public final synchronized ISpellChecker getSpellChecker() throws IllegalStateException {
		if (this.fGlobalDictionaries == null)
			throw new IllegalStateException("spell checker has been shut down"); //$NON-NLS-1$

		IPreferenceStore store = SpellActivator.getDefault().getPreferenceStore();
		Locale locale = getCurrentLocale(store);
		if (this.fUserDictionary == null && "".equals(locale.toString())) //$NON-NLS-1$
			return null;

		if (this.fChecker != null && this.fChecker.getLocale().equals(locale))
			return this.fChecker;

		resetSpellChecker();

		this.fChecker = new DefaultSpellChecker(store, locale);
		resetUserDictionary();

		for (Iterator iterator = this.fGlobalDictionaries.iterator(); iterator.hasNext();) {
			ISpellDictionary dictionary = (ISpellDictionary) iterator.next();
			this.fChecker.addDictionary(dictionary);
		}

		ISpellDictionary dictionary = findDictionary(this.fChecker.getLocale());
		if (dictionary != null)
			this.fChecker.addDictionary(dictionary);

		return this.fChecker;
	}

	/**
	 * Returns the current locale of the spelling preferences.
	 * 
	 * @param store
	 *            the preference store
	 * @return The current locale of the spelling preferences
	 */
	private Locale getCurrentLocale(final IPreferenceStore store) {
		return convertToLocale(store.getString(PreferenceConstants.SPELLING_LOCALE));
	}

	public static Locale convertToLocale(final String locale) {
		Locale defaultLocale = SpellCheckEngine.getDefaultLocale();
		if (locale.equals(defaultLocale.toString()))
			return defaultLocale;

		if (locale.length() >= 5)
			return new Locale(locale.substring(0, 2), locale.substring(3, 5));

		return new Locale(""); //$NON-NLS-1$
	}

	/*
	 * @see
	 * org.eclipse.jdt.ui.text.spelling.engine.ISpellCheckEngine#getLocale()
	 */
	public synchronized final Locale getLocale() {
		if (this.fChecker == null)
			return null;

		return this.fChecker.getLocale();
	}

	/*
	 * @see
	 * org.eclipse.jface.util.IPropertyChangeListener#propertyChange(org.eclipse
	 * .jface.util.PropertyChangeEvent)
	 */
	public final void propertyChange(final PropertyChangeEvent event) {
		if (event.getProperty().equals(PreferenceConstants.SPELLING_LOCALE)) {
			resetSpellChecker();
			return;
		}

		if (event.getProperty().equals(PreferenceConstants.SPELLING_USER_DICTIONARY)) {
			resetUserDictionary();
			return;
		}

		if (event.getProperty().equals(PreferenceConstants.SPELLING_USER_DICTIONARY_ENCODING)) {
			resetUserDictionary();
			return;
		}
	}

	/**
	 * Resets the current checker's user dictionary.
	 */
	private synchronized void resetUserDictionary() {
		if (this.fChecker == null)
			return;

		// Update user dictionary
		if (this.fUserDictionary != null) {
			this.fChecker.removeDictionary(this.fUserDictionary);
			this.fUserDictionary.unload();
			this.fUserDictionary = null;
		}

		IPreferenceStore store = SpellActivator.getDefault().getPreferenceStore();
		String filePath = store.getString(PreferenceConstants.SPELLING_USER_DICTIONARY);
		IStringVariableManager variableManager = VariablesPlugin.getDefault()
				.getStringVariableManager();
		try {
			filePath = variableManager.performStringSubstitution(filePath);
		} catch (CoreException e) {
			SpellActivator.log(e);
			return;
		}
		if (filePath.length() > 0) {
			try {
				File file = new File(filePath);
				if (!file.exists() && !file.createNewFile())
					return;

				final URL url = new URL("file", null, filePath); //$NON-NLS-1$
				InputStream stream = url.openStream();
				if (stream != null) {
					try {
						this.fUserDictionary = new PersistentSpellDictionary(url);
						this.fChecker.addDictionary(this.fUserDictionary);
					} finally {
						stream.close();
					}
				}
			} catch (MalformedURLException exception) {
				// Do nothing
			} catch (IOException exception) {
				// Do nothing
			}
		}
	}

	/*
	 * @seeorg.eclipse.jdt.internal.ui.text.spelling.engine.ISpellCheckEngine#
	 * registerDictionary
	 * (org.eclipse.jdt.internal.ui.text.spelling.engine.ISpellDictionary)
	 */
	public synchronized final void registerGlobalDictionary(final ISpellDictionary dictionary) {
		this.fGlobalDictionaries.add(dictionary);
		resetSpellChecker();
	}

	/*
	 * @seeorg.eclipse.jdt.internal.ui.text.spelling.engine.ISpellCheckEngine#
	 * registerDictionary(java.util.Locale,
	 * org.eclipse.jdt.internal.ui.text.spelling.engine.ISpellDictionary)
	 */
	public synchronized final void registerDictionary(final Locale locale,
			final ISpellDictionary dictionary) {
		this.fLocaleDictionaries.put(locale, dictionary);
		resetSpellChecker();
	}

	/*
	 * @see
	 * org.eclipse.jdt.internal.ui.text.spelling.engine.ISpellCheckEngine#unload
	 * ()
	 */
	public synchronized final void shutdown() {

		SpellActivator.getDefault().getPreferenceStore().removePropertyChangeListener(this);

		ISpellDictionary dictionary = null;
		for (final Iterator iterator = this.fGlobalDictionaries.iterator(); iterator.hasNext();) {
			dictionary = (ISpellDictionary) iterator.next();
			dictionary.unload();
		}
		this.fGlobalDictionaries = null;

		for (final Iterator iterator = this.fLocaleDictionaries.values().iterator(); iterator
				.hasNext();) {
			dictionary = (ISpellDictionary) iterator.next();
			dictionary.unload();
		}
		this.fLocaleDictionaries = null;

		this.fUserDictionary = null;
		this.fChecker = null;
	}

	private synchronized void resetSpellChecker() {
		if (this.fChecker != null) {
			ISpellDictionary dictionary = (ISpellDictionary) this.fLocaleDictionaries
					.get(this.fChecker.getLocale());
			if (dictionary != null)
				dictionary.unload();
		}
		this.fChecker = null;
	}

	/*
	 * @seeorg.eclipse.jdt.ui.text.spelling.engine.ISpellCheckEngine#
	 * unregisterDictionary
	 * (org.eclipse.jdt.ui.text.spelling.engine.ISpellDictionary)
	 */
	public synchronized final void unregisterDictionary(final ISpellDictionary dictionary) {
		this.fGlobalDictionaries.remove(dictionary);
		this.fLocaleDictionaries.values().remove(dictionary);
		dictionary.unload();
		resetSpellChecker();
	}
}
