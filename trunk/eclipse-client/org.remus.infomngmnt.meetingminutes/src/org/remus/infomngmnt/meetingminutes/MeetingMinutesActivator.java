package org.remus.infomngmnt.meetingminutes;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class MeetingMinutesActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.meetingminutes";

	// The shared instance
	private static MeetingMinutesActivator plugin;

	public static final String INFO_TYPE_ID = "MEETING"; //$NON-NLS-1$

	public static final String NODE_NAME_DATETIME = "meetingtime"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTENDEES = "attendees"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTENDEE = "attendee"; //$NON-NLS-1$

	public static final String NODE_NAME_MODERATOR = "moderator"; //$NON-NLS-1$

	public static final String NODE_NAME_PLACE = "place"; //$NON-NLS-1$

	public static final String NODE_NAME_LOG = "log"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTACHMENTS = "attachments"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTACHMENT = "attachment"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTACHMENT_LABEL = "attachmentLabel"; //$NON-NLS-1$

	public static final String NODE_NAME_DECISIONS = "decisions"; //$NON-NLS-1$

	public static final String NODE_NAME_TODOS = "todos"; //$NON-NLS-1$

	private RemusServiceTracker serviceTracker;

	/**
	 * The constructor
	 */
	public MeetingMinutesActivator() {
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

	public RemusServiceTracker getServiceTracker() {
		return this.serviceTracker;
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
	public static MeetingMinutesActivator getDefault() {
		return plugin;
	}

}
