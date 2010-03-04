package org.remus.infomngmnt.search.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.search.ui.context.SearchContextProvider;
import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class SearchUIActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.search.ui";

	private RemusServiceTracker remusServiceTracker;

	private SearchContextProvider searchContext;

	// The shared instance
	private static SearchUIActivator plugin;

	/**
	 * The constructor
	 */
	public SearchUIActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		this.searchContext = new SearchContextProvider();
		this.remusServiceTracker = new RemusServiceTracker(getBundle());
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static SearchUIActivator getDefault() {
		return plugin;
	}

	/**
	 * @return the remusServiceTracker
	 */
	public final RemusServiceTracker getServiceTracker() {
		return this.remusServiceTracker;
	}

	public SearchContextProvider getSearchContext() {
		return this.searchContext;
	}

	public void setSearchContext(final SearchContextProvider searchContext) {
		this.searchContext = searchContext;
	}

}
