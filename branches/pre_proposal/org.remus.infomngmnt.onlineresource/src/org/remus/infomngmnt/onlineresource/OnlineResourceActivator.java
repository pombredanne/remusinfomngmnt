package org.remus.infomngmnt.onlineresource;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class OnlineResourceActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.onlineresource";

	// The shared instance
	private static OnlineResourceActivator plugin;

	public static final String TYPE_ID = "ONLINERESOURCE"; //$NON-NLS-1$

	public static final String NODE_NAME_CREATED = "created"; //$NON-NLS-1$

	public static final String NODE_NAME_URL = "url"; //$NON-NLS-1$

	public static final String NODE_NAME_EMBED = "embed"; //$NON-NLS-1$

	public static final String NODE_NAME_THUMBNAIL = "thumbnailImage"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTACHMENT = "attachment"; //$NON-NLS-1$

	public static final String NODE_NAME_FORMAT = "format"; //$NON-NLS-1$

	/**
	 * The constructor
	 */
	public OnlineResourceActivator() {
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
	public static OnlineResourceActivator getDefault() {
		return plugin;
	}

}
