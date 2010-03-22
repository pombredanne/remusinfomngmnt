package org.remus.infomngmnt.pdf;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.pdf";

	public static final String TYPE_ID = "PDF";

	public static final String SLIDER_WIDTH = "sliderWidth";

	public static final String RENDERER = "renderer"; //$NON-NLS-1$

	public static final String TITLE = "title"; //$NON-NLS-1$

	public static final String AUTHOR = "author"; //$NON-NLS-1$

	public static final String CREATOR = "creator"; //$NON-NLS-1$

	public static final String PRODUCER = "producer"; //$NON-NLS-1$

	public static final String CREATION_DATE = "creationDate"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private RemusServiceTracker serviceTracker;

	/**
	 * The constructor
	 */
	public Activator() {
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
	public static Activator getDefault() {
		return plugin;
	}

	public RemusServiceTracker getServiceTracker() {
		return this.serviceTracker;
	}

}
