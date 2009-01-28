package org.remus.infomngmnt.link;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class LinkActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.link";
	
	
	public static final String LINK_INFO_ID = "LINK"; //$NON-NLS-1$
	public static final String NODE_INDEX = "index"; //$NON-NLS-1$
	public static final String NODE_WEBSHOTIMAGE_DATA = "webshot"; //$NON-NLS-1$

	// The shared instance
	private static LinkActivator plugin;
	
	/**
	 * The constructor
	 */
	public LinkActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
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
	public static LinkActivator getDefault() {
		return plugin;
	}

}
