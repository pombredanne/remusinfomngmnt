package org.remus.infomngmnt.connector.googlecalendar;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.connector.googlecalendar.messages"; //$NON-NLS-1$
	public static String GoogleCalendarConnectionWizardPage_APIUrl;
	public static String GoogleCalendarConnectionWizardPage_Credentials;
	public static String GoogleCalendarConnectionWizardPage_EnterCredentials;
	public static String GoogleCalendarConnectionWizardPage_ErrorValidatingSettings;
	public static String GoogleCalendarConnectionWizardPage_EventsFuture;
	public static String GoogleCalendarConnectionWizardPage_EventsPast;
	public static String GoogleCalendarConnectionWizardPage_GoogleCalendarConenctor;
	public static String GoogleCalendarConnectionWizardPage_Month;
	public static String GoogleCalendarConnectionWizardPage_Name;
	public static String GoogleCalendarConnectionWizardPage_Password;
	public static String GoogleCalendarConnectionWizardPage_SyncTimespan;
	public static String GoogleCalendarConnectionWizardPage_Username;
	public static String GoogleCalendarConnectionWizardPage_ValidatingCredentials;
	public static String GoogleCalendarConnector_ErrorAdding;
	public static String GoogleCalendarConnector_ErrorCreatingGroup;
	public static String GoogleCalendarConnector_ErrorDeleting;
	public static String GoogleCalendarConnector_ErrorResolving;
	public static String GoogleCalendarConnector_ErrorUpdatingCalendarEvent;
	public static String GoogleCalendarConnector_ErrorUpdatingCategory;
	public static String GoogleCalendarConnector_ErrorValidatingCredentials;
	public static String NewGoogleCalendarRepositoryWizard_NewGoogleCalendarConnector;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
