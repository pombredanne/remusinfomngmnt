package org.remus.infomngmnt.favoritesearch;

import org.eclipse.remus.search.service.IFavoriteSearchHandler;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.remus.infomngmnt.favoritesearch.service.FavSearchHandler;

/**
 * The activator class controls the plug-in life cycle
 */
public class FavoriteSearchActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.favoritesearch"; //$NON-NLS-1$

	// The shared instance
	private static FavoriteSearchActivator plugin;

	public static final String TYPE_ID = "FAVSEARCH"; //$NON-NLS-1$

	public static final String LATEST_SEARCH_TYPE = "latest"; //$NON-NLS-1$

	public static final String NEW_ELEMENTS_TYPE = "newelements"; //$NON-NLS-1$

	public static final String RESULT_NODE = "result"; //$NON-NLS-1$

	private RemusServiceTracker serviceTracker;

	/**
	 * The constructor
	 */
	public FavoriteSearchActivator() {
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
		context.registerService(IFavoriteSearchHandler.class.getName(),
				new FavSearchHandler(), null);
		this.serviceTracker = new RemusServiceTracker(getBundle());
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

	public RemusServiceTracker getServiceTracker() {
		return this.serviceTracker;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static FavoriteSearchActivator getDefault() {
		return plugin;
	}

}
