package org.remus.infomngmnt.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class CorePlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.core";

	// The shared instance
	private static CorePlugin plugin;

	/**
	 * The constructor
	 */
	public CorePlugin() {
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
	public static CorePlugin getDefault() {
		return plugin;
	}

}
