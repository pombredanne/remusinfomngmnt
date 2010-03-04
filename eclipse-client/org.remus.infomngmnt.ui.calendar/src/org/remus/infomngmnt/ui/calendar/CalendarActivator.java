package org.remus.infomngmnt.ui.calendar;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;
import org.remus.infomngmnt.ui.calendar.service.ICalendarStoreService;

/**
 * The activator class controls the plug-in life cycle
 */
public class CalendarActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.ui.calendar";

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
		this.serviceTracker = new RemusServiceTracker(getBundle());
		this.calendarStoreService = this.serviceTracker.getService(ICalendarStoreService.class);
		this.editService = this.serviceTracker.getService(IEditingHandler.class);
		this.applicationService = this.serviceTracker.getService(IApplicationModel.class);
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
		this.serviceTracker.ungetService(this.calendarStoreService);
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
		return this.serviceTracker;
	}

	/**
	 * @return the calendarStoreService
	 */
	public final ICalendarStoreService getCalendarStoreService() {
		return this.calendarStoreService;
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
