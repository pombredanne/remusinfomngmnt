package org.remus.infomngmnt.search;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.search.internal.FavoriteHandleTracker;
import org.remus.infomngmnt.search.internal.LuceneSearchCustomizeTracker;
import org.remus.infomngmnt.search.service.IFavoriteSearchHandler;
import org.remus.infomngmnt.search.service.ILuceneCustomizer;
import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class SearchActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.search";

	// The shared instance
	private static SearchActivator plugin;

	private RemusServiceTracker remusServiceTracker;

	private LuceneSearchCustomizeTracker tracker;

	/**
	 * The constructor
	 */
	public SearchActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		this.remusServiceTracker = new RemusServiceTracker(context.getBundle());
		this.tracker = new LuceneSearchCustomizeTracker(context);
		this.tracker.open();
		this.favoriteHandleTracker = new FavoriteHandleTracker(context);
		this.favoriteHandleTracker.open();

		plugin = this;
	}

	/**
	 * @return the remusServiceTracker
	 */
	public final RemusServiceTracker getServiceTracker() {
		return this.remusServiceTracker;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		this.tracker.close();
		this.favoriteHandleTracker.close();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static SearchActivator getDefault() {
		return plugin;
	}

	private FavoriteHandleTracker favoriteHandleTracker;

	private volatile SearchHistory searchHistory;

	public ILuceneCustomizer getLuceneCustomizationService() {
		return (ILuceneCustomizer) this.tracker.getService();
	}

	public IFavoriteSearchHandler getFavoriteTrackerService() {
		return (IFavoriteSearchHandler) this.favoriteHandleTracker.getService();
	}

	public SearchHistory getSearchHistory() {
		if (this.searchHistory == null) {
			this.searchHistory = SearchFactory.eINSTANCE.createSearchHistory();
		}
		return this.searchHistory;
	}

}
