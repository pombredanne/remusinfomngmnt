package org.remus.infomngmnt.ui.editor;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class EditorActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.ui.editor";

	// The shared instance
	private static EditorActivator plugin;

	private RemusServiceTracker serviceTracker;

	private IEditingHandler editService;

	private IApplicationModel applicationService;

	/**
	 * The constructor
	 */
	public EditorActivator() {
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
		this.serviceTracker.ungetService(this.editService);
		this.serviceTracker.ungetService(this.applicationService);
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EditorActivator getDefault() {
		return plugin;
	}

	/**
	 * @return the serviceTracker
	 */
	public final RemusServiceTracker getServiceTracker() {
		return this.serviceTracker;
	}

	/**
	 * @return the editService
	 */
	public final IEditingHandler getEditService() {
		if (this.editService == null) {
			this.editService = this.serviceTracker.getService(IEditingHandler.class);
		}
		return this.editService;
	}

	/**
	 * @return the applicationService
	 */
	public final IApplicationModel getApplicationService() {
		if (this.applicationService == null) {
			this.applicationService = this.serviceTracker.getService(IApplicationModel.class);
		}
		return this.applicationService;
	}

}
