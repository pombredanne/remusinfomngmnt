package org.remus.infomngmnt.birtreport;

import org.eclipse.birt.report.viewer.ViewerPlugin;
import org.eclipse.birt.report.viewer.utilities.WebViewer;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ReportActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.birtreport";

	// The shared instance
	private static ReportActivator plugin;

	public static final String NODE_NAME_PARAMS = "reportparams"; //$NON-NLS-1$

	public static final String NODE_NAME_PARAM_NAME = "paramname"; //$NON-NLS-1$

	public static final String NODE_NAME_PARAM_VALUE = "paramvalue"; //$NON-NLS-1$

	public static final String INFOTYPE_ID = "REPORT"; //$NON-NLS-1$

	/**
	 * The constructor
	 */
	public ReportActivator() {
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
		WebViewer.startup(ViewerPlugin.WEBAPP_CONTEXT);
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
	public static ReportActivator getDefault() {
		return plugin;
	}

}
