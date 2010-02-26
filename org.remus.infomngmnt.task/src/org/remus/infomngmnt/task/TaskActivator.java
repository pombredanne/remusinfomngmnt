package org.remus.infomngmnt.task;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TaskActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.task";

	// The shared instance
	private static TaskActivator plugin;

	public static final String NODE_NAME_STATUS = "status"; //$NON-NLS-1$

	public static final String NODE_NAME_STARTED = "started"; //$NON-NLS-1$

	public static final String NODE_NAME_COMPLETED_PERCENTAGE = "completedpercentage"; //$NON-NLS-1$

	public static final String NODE_NAME_ASIGNEE = "asignee"; //$NON-NLS-1$

	public static final String NODE_NAME_PRIORITY = "priority"; //$NON-NLS-1$

	public static final String NODE_NAME_DUE_DATE = "duedata"; //$NON-NLS-1$

	public static final String NODE_NAME_NOTIFY = "notify"; //$NON-NLS-1$

	public static final String NODE_NAME_MINUTES_BEFORE_DUE = "minutesbeforedue"; //$NON-NLS-1$

	public static final String NODE_NAME_ESTIMATED_EFFORTS = "estimated"; //$NON-NLS-1$

	public static final String NODE_NAME_WORKED_UNITS = "workunits"; //$NON-NLS-1$

	public static final String NODE_NAME_WORKED_UNIT = "workunit"; //$NON-NLS-1$

	public static final String NODE_NAME_WORKED_UNIT_STARTED = "workunitstart"; //$NON-NLS-1$

	public static final String NODE_NAME_WORKED_UNIT_END = "workunitend"; //$NON-NLS-1$

	public static final String NODE_NAME_WORKED_UNIT_DESCRIPTION = "description"; //$NON-NLS-1$

	public static final String NODE_NAME_COMPLETED = "completed"; //$NON-NLS-1$

	public static final String INFO_TYPE_ID = "TASK";

	/**
	 * The constructor
	 */
	public TaskActivator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
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
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static TaskActivator getDefault() {
		return plugin;
	}

}
