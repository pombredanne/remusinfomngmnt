package org.remus.infomngmnt.eclipsemarketplace;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MarketPlaceActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.eclipsemarketplace";

	public static final String TYPE_ID = "MARKETPLACE"; //$NON-NLS-1$

	public static final String TYPE_NODE_ID = "type"; //$NON-NLS-1$
	public static final String OWNER_NODE_ID = "owner"; //$NON-NLS-1$
	public static final String FAVORITED_NODE_ID = "favorited"; //$NON-NLS-1$
	public static final String CREATED_NODE_ID = "created"; //$NON-NLS-1$
	public static final String CHANGED_NODE_ID = "changedate"; //$NON-NLS-1$
	public static final String FOUNDATION_NODE_ID = "foundationmember"; //$NON-NLS-1$
	public static final String WEBSITE_NODE_ID = "url"; //$NON-NLS-1$
	public static final String LICENSE_NODE_ID = "license"; //$NON-NLS-1$
	public static final String VERSION_NODE_ID = "version"; //$NON-NLS-1$
	public static final String COMPANY_NODE_ID = "company"; //$NON-NLS-1$
	public static final String STATUS_NODE_ID = "status"; //$NON-NLS-1$
	public static final String ECLIPSEVERSION_NODE_ID = "eclipseversion"; //$NON-NLS-1$
	public static final String SUPPORTURL_NODE_ID = "supporturl"; //$NON-NLS-1$
	public static final String UPDATEUR_NODE_ID = "updateurl"; //$NON-NLS-1$
	public static final String DESCRIPTION_NODE_ID = "description"; //$NON-NLS-1$

	// The shared instance
	private static MarketPlaceActivator plugin;

	/**
	 * The constructor
	 */
	public MarketPlaceActivator() {
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
	public static MarketPlaceActivator getDefault() {
		return plugin;
	}

}
