package org.remus.infomngmnt.connector.twitter;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.connector.twitter.ui.TwitterImageCache;

/**
 * The activator class controls the plug-in life cycle
 */
public class TwitterActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.connector.twitter";

	// The shared instance
	private static TwitterActivator plugin;

	public static final String REPOSITORY_OPTIONS_SEARCH_KEY = "REPOSITORY_OPTIONS_SEARCH_KEY"; //$NON-NLS-1$

	public static final String INFOTYPE_ID = "TWITTER"; //$NON-NLS-1$

	public static final String MESSAGES_ID = "messages"; //$NON-NLS-1$

	public static final String MESSAGE_TYPE = "message"; //$NON-NLS-1$

	public static final String MESSAGE_DATE_TYPE = "date"; //$NON-NLS-1$

	public static final String MESSAGE_USER_TYPE = "user"; //$NON-NLS-1$

	public static final String MESSAGE_CONTENT_TYPE = "content"; //$NON-NLS-1$

	public static final String MESSAGE_SRC_TYPE = "source"; //$NON-NLS-1$

	public static final String MESSAGE_INTERNAL_ID = "twitterid"; //$NON-NLS-1$

	public static final String REPLY_ID = "reply"; //$NON-NLS-1$

	public static final String MESSAGE_USER_ID_TYPE = "userid";

	private TwitterImageCache imageCache;

	/**
	 * The constructor
	 */
	public TwitterActivator() {
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
	public static TwitterActivator getDefault() {
		return plugin;
	}

	public TwitterImageCache getImageCache() {
		if (this.imageCache == null) {
			this.imageCache = new TwitterImageCache();
		}
		return this.imageCache;
	}

}
