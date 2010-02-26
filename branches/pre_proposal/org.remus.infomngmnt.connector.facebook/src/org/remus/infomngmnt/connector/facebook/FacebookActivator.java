package org.remus.infomngmnt.connector.facebook;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class FacebookActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.connector.facebook";

	public static final String API_KEY = "626ad1e4223a31ad0e326b02197984bf"; //$NON-NLS-1$

	public static final String APP_SECRET = "e7e542ad231e8957f16ed2f3a0b689f6"; //$NON-NLS-1$

	public static final String STREAM_INFO_TYPE = "FBSTREAM"; //$NON-NLS-1$

	// The shared instance
	private static FacebookActivator plugin;

	/**
	 * The constructor
	 */
	public FacebookActivator() {
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
	public static FacebookActivator getDefault() {
		return plugin;
	}

}
