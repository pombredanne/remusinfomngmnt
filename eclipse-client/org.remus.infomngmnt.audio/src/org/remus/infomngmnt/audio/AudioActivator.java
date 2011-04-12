package org.remus.infomngmnt.audio;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class AudioActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.audio"; //$NON-NLS-1$

	// The shared instance
	private static AudioActivator plugin;

	public static String TYPE_ID = "AUDIO"; //$NON-NLS-1$

	public static final String NODE_NAME_MEDIATYPE = "mediatype"; //$NON-NLS-1$

	public static final String NODE_NAME_CUEPOINTS = "cuepoints"; //$NON-NLS-1$

	public static final String NODE_NAME_CUEPOINT = "cuepoint"; // $NON //$NON-NLS-1$

	/**
	 * The constructor
	 */
	public AudioActivator() {
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
	public static AudioActivator getDefault() {
		return plugin;
	}

}
