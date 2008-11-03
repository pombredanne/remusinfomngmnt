package org.remus.infomngmnt.search.provider;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import org.remus.infomngmnt.search.SearchFactory;
import org.remus.infomngmnt.search.SearchHistory;
import org.remus.infomngmnt.search.service.ILuceneCustomizer;
import org.remus.infomngmnt.search.service.LuceneSearchCustomizeTracker;

/**
 * This is the central singleton for the Search edit plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class SearchPlugin extends EMFPlugin {
	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final SearchPlugin INSTANCE = new SearchPlugin();

	/**
	 * The plugins id
	 * @generated not
	 */
	public static final String PLUGIN_ID = "org.remus.infomngmnt.search"; //$NON-NLS-1$

	/**
	 * Keep track of the singleton.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static Implementation plugin;

	/**
	 * Create the instance.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchPlugin() {
		super
		(new ResourceLocator [] {
		});
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Returns the singleton instance of the Eclipse plugin.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the singleton instance.
	 * @generated
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public static class Implementation extends EclipsePlugin {

		private ServiceTracker tracker;


		private SearchHistory searchHistory;
		/**
		 * Storage for preferences.
		 */
		private ScopedPreferenceStore preferenceStore;
		/**
		 * Creates an instance.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}

		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);
			this.tracker = new LuceneSearchCustomizeTracker(context);
			this.tracker.open();
		}

		@Override
		public void stop(BundleContext context) throws Exception {
			super.stop(context);
			this.tracker.close();
		}

		public ILuceneCustomizer getService() {
			return (ILuceneCustomizer) this.tracker.getService();
		}

		/**
		 * Returns the preference store for this UI plug-in.
		 * This preference store is used to hold persistent settings for this plug-in in
		 * the context of a workbench. Some of these settings will be user controlled,
		 * whereas others may be internal setting that are never exposed to the user.
		 * <p>
		 * If an error occurs reading the preference store, an empty preference store is
		 * quietly created, initialized with defaults, and returned.
		 * </p>
		 * <p>
		 * <strong>NOTE:</strong> As of Eclipse 3.1 this method is
		 * no longer referring to the core runtime compatibility layer and so
		 * plug-ins relying on Plugin#initializeDefaultPreferences
		 * will have to access the compatibility layer themselves.
		 * </p>
		 *
		 * @return the preference store
		 */
		public IPreferenceStore getPreferenceStore() {
			// Create the preference store lazily.
			if (this.preferenceStore == null) {
				this.preferenceStore = new ScopedPreferenceStore(new InstanceScope(),getBundle().getSymbolicName());

			}
			return this.preferenceStore;
		}

		public SearchHistory getSearchHistory() {
			if (this.searchHistory == null) {
				this.searchHistory = SearchFactory.eINSTANCE.createSearchHistory();
			}
			return this.searchHistory;
		}

	}

}
