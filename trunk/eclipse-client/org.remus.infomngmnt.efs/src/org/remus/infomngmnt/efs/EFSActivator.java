package org.remus.infomngmnt.efs;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class EFSActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.efs";

	// The shared instance
	private static EFSActivator plugin;

	/**
	 * The constructor
	 */
	public EFSActivator() {
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
	public static EFSActivator getDefault() {
		return plugin;
	}

	public <T> T getService(final Class<T> serviceClass) {
		ServiceReference serviceReference = getBundle().getBundleContext().getServiceReference(
				serviceClass.getName());
		if (serviceReference != null) {
			Object service = getBundle().getBundleContext().getService(serviceReference);
			return (T) service;
		}
		return null;
	}

}
