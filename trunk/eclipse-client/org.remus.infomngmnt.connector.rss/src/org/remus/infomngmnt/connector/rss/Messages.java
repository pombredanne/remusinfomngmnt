package org.remus.infomngmnt.connector.rss;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.connector.rss.messages"; //$NON-NLS-1$
	public static String RssConnectionWizardPage_Authentication;
	public static String RssConnectionWizardPage_DeleteFeedOlderThan;
	public static String RssConnectionWizardPage_EnterUrl;
	public static String RssConnectionWizardPage_ErrorValidating;
	public static String RssConnectionWizardPage_Name;
	public static String RssConnectionWizardPage_ObtainTitle;
	public static String RssConnectionWizardPage_Password;
	public static String RssConnectionWizardPage_Properties;
	public static String RssConnectionWizardPage_RefreshInterval;
	public static String RssConnectionWizardPage_RSSConnector;
	public static String RssConnectionWizardPage_URL;
	public static String RssConnectionWizardPage_UseAuthentication;
	public static String RssConnectionWizardPage_Username;
	public static String RssConnectionWizardPage_Validate;
	public static String RssConnector_ErrorInitECF;
	public static String RssConnector_ErrorLoading;
	public static String RssConnector_NotSupported;
	public static String RssRepositoryWizard_RSSAtomFeed;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
