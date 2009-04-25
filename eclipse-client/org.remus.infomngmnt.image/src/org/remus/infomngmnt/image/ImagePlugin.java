package org.remus.infomngmnt.image;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ImagePlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.image";

	// The shared instance
	private static ImagePlugin plugin;

	public static final String TYPE_ID = "IMAGE"; //$NON-NLS-1$

	public static final String NODE_NAME_EXIF = "exifdata"; //$NON-NLS-1$

	public static final String ORIGINAL_FILEPATH = "originFilePath"; //$NON-NLS-1$

	public static final String NODE_NAME_WIDTH = "width"; //$NON-NLS-1$

	public static final String NODE_NAME_HEIGHT = "height"; //$NON-NLS-1$

	public static final String NODE_NAME_LINKS = "links";

	public static final String NODE_NAME_LINK = "link";

	/**
	 * The constructor
	 */
	public ImagePlugin() {
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
	public static ImagePlugin getDefault() {
		return plugin;
	}

}
