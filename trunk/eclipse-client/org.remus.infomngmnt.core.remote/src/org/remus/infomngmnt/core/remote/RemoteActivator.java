package org.remus.infomngmnt.core.remote;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class RemoteActivator extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.core.remote";

	// The shared instance
	private static RemoteActivator plugin;

	private RemusServiceTracker remusServiceTracker;

	/**
	 * The constructor
	 */
	public RemoteActivator() {
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
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static RemoteActivator getDefault() {
		return plugin;
	}

}
