package org.remus.infomngmnt.ui.notification;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class NotificationActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.ui.notification";

	// The shared instance
	private static NotificationActivator plugin;

	private RemusServiceTracker serviceTracker;

	/**
	 * The constructor
	 */
	public NotificationActivator() {
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
		this.serviceTracker = new RemusServiceTracker(getBundle());
		plugin = this;
		new NotificationPopupManager().startNotification(0);
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
	public static NotificationActivator getDefault() {
		return plugin;
	}

	/**
	 * @return the serviceTracker
	 */
	public final RemusServiceTracker getServiceTracker() {
		return this.serviceTracker;
	}

}
