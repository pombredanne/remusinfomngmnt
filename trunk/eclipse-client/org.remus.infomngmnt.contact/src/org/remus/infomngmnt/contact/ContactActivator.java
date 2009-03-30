package org.remus.infomngmnt.contact;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ContactActivator extends AbstractUIPlugin {

	public static String PASSWORD_INFO_ID = "CONTACT";
	
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.contact";

	// The shared instance
	private static ContactActivator plugin;
	
	/**
	 * The constructor
	 */
	public ContactActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ContactActivator getDefault() {
		return plugin;
	}

}
