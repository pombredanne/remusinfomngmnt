package org.remus.infomngmnt.mail;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MailActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.mail";

	public static final String INFO_TYPE_ID = "MAIL"; //$NON-NLS-1$

	public static final String NODE_NAME_RECEIVED = "received"; //$NON-NLS-1$

	public static final String NODE_NAME_SENDER = "sender"; //$NON-NLS-1$

	public static final String NODE_NAME_CONTENT_TYPE = "contenttype"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTACHMENTS = "attachments"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTACHMENT = "attachment"; //$NON-NLS-1$

	public static final String NODE_NAME_ATTACHMENT_LABEL = "label"; //$NON-NLS-1$

	public static final String NODE_NAME_EMBEDDEDS = "embeddeds"; //$NON-NLS-1$

	public static final String NODE_NAME_EMBEDDED = "embedded"; //$NON-NLS-1$

	public static final String NODE_NAME_CONTENT = "content"; //$NON-NLS-1$

	public static final String NODE_NAME_CCS = "ccs"; //$NON-NLS-1$

	public static final String NODE_NAME_CC = "cc"; //$NON-NLS-1$

	public static final String NODE_NAME_RECIPIENTS = "recipients"; //$NON-NLS-1$

	public static final String NODE_NAME_RECIPIENT = "recipient"; //$NON-NLS-1$

	public static final String NODE_NAME_MORE_INFO = "moreInfo"; //$NON-NLS-1$

	// The shared instance
	private static MailActivator plugin;

	/**
	 * The constructor
	 */
	public MailActivator() {
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
	public static MailActivator getDefault() {
		return plugin;
	}

}
