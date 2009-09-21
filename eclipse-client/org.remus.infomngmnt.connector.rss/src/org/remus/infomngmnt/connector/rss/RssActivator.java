package org.remus.infomngmnt.connector.rss;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class RssActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.connector.rss";

	public static final String REPOSITORY_OPTIONS_REFRESH_INTERVAL = "REPOSITORY_OPTIONS_REFRESH_INTERVAL";
	public static final String REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY = "REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY";
	public static final String REPOSITORY_OPTIONS_BASIC_AUTHENTICATION = "REPOSITORY_OPTIONS_BASIC_AUTHENTICATION";

	public static final String REPOSITORY_ID = "org.remus.infomngmnt.connector.rss"; //$NON-NLS-1$
	// The shared instance
	private static RssActivator plugin;

	/**
	 * The constructor
	 */
	public RssActivator() {
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
	public static RssActivator getDefault() {
		return plugin;
	}

}
