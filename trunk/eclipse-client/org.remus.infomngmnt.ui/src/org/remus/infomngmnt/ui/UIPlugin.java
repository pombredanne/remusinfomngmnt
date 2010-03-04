package org.remus.infomngmnt.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.common.ui.image.CommonImageRegistry;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class UIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.ui";

	private static final int NOTIFICATION_DELAY = 5000;

	// The shared instance
	private static UIPlugin plugin;

	// private NotificationPopupManager popupManager;

	private RemusServiceTracker serviceTracker;

	private IEditingHandler editService;

	private IApplicationModel applicationService;

	/**
	 * The constructor
	 */
	public UIPlugin() {
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
		/*
		 * This is a bit ugly. Problem is that if the first access on the
		 * image-registry comes from a non-ui thread the initialization fails.
		 * 
		 * This is not good, especially because in this image-registry there are
		 * decorator-icons which are loaded very early and in a non-ui thread.
		 */
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				CommonImageRegistry.getInstance();
			}
		});
		this.serviceTracker = new RemusServiceTracker(getBundle());
		this.editService = this.serviceTracker.getService(IEditingHandler.class);
		this.applicationService = this.serviceTracker.getService(IApplicationModel.class);
		plugin = this;
		// FIXME
		// this.popupManager = new NotificationPopupManager();
		// this.popupManager.startNotification(NOTIFICATION_DELAY);
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
	public static UIPlugin getDefault() {
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
		return this.editService;
	}

	/**
	 * @return the applicationService
	 */
	public final IApplicationModel getApplicationService() {
		return this.applicationService;
	}

}
