package org.remus.infomngmnt.link.delicious;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.link.delicious.messages"; //$NON-NLS-1$
	public static String DelicicousRepository_ErrorConnecting;
	public static String DelicicousRepository_ErrorGettingChildren;
	public static String DeliciousConnectionWizardPage_APIUrl;
	public static String DeliciousConnectionWizardPage_Credentials;
	public static String DeliciousConnectionWizardPage_ErrorValidatingRepo;
	public static String DeliciousConnectionWizardPage_ErrorValidatingSettings;
	public static String DeliciousConnectionWizardPage_Name;
	public static String DeliciousConnectionWizardPage_Password;
	public static String DeliciousConnectionWizardPage_Subtitle;
	public static String DeliciousConnectionWizardPage_Title;
	public static String DeliciousConnectionWizardPage_Username;
	public static String DeliciousConnectionWizardPage_ValidateCredentials;
	public static String DeliciousPreferencePage_AOIUrl;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
