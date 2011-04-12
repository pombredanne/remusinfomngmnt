package org.remus.infomngmnt.connector.slideshare;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class SlideshareActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.connector.slideshare"; //$NON-NLS-1$

	public static final String REPOSITORY_OPTIONS_SEARCH_KEY = "search"; //$NON-NLS-1$

	public static final String API_KEY = "3500tot7"; //$NON-NLS-1$

	public static final String SHARED_SECRED = "Ng5EG30X"; //$NON-NLS-1$

	// The shared instance
	private static SlideshareActivator plugin;

	/**
	 * The constructor
	 */
	public SlideshareActivator() {
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
	public static SlideshareActivator getDefault() {
		return plugin;
	}

}
