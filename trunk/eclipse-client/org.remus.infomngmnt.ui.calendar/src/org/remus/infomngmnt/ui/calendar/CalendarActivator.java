package org.remus.infomngmnt.ui.calendar;

import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.remus.infomngmnt.ui.calendar.service.ICalendarStoreService;

/**
 * The activator class controls the plug-in life cycle
 */
public class CalendarActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.ui.calendar"; //$NON-NLS-1$

	// The shared instance
	private static CalendarActivator plugin;

	private RemusServiceTracker serviceTracker;

	private ICalendarStoreService calendarStoreService;

	private IEditingHandler editService;

	private IApplicationModel applicationService;

	/**
	 * The constructor
	 */
	public CalendarActivator() {
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
		serviceTracker = new RemusServiceTracker(getBundle());

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
		serviceTracker.ungetService(editService);
		serviceTracker.ungetService(applicationService);
		serviceTracker.ungetService(calendarStoreService);
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static CalendarActivator getDefault() {
		return plugin;
	}

	/**
	 * @return the serviceTracker
	 */
	public final RemusServiceTracker getServiceTracker() {
		return serviceTracker;
	}

	/**
	 * @return the calendarStoreService
	 */
	public final ICalendarStoreService getCalendarStoreService() {
		if (calendarStoreService == null) {
			calendarStoreService = serviceTracker
					.getService(ICalendarStoreService.class);
		}
		return calendarStoreService;
	}

	/**
	 * @return the editService
	 */
	public final IEditingHandler getEditService() {
		if (editService == null) {
			editService = serviceTracker.getService(IEditingHandler.class);
		}
		return editService;
	}

	/**
	 * @return the applicationService
	 */
	public final IApplicationModel getApplicationService() {
		if (applicationService == null) {
			applicationService = serviceTracker
					.getService(IApplicationModel.class);
		}
		return applicationService;
	}

}
