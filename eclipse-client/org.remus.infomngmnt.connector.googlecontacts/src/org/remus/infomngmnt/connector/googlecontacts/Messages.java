package org.remus.infomngmnt.connector.googlecontacts;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.connector.googlecontacts.messages"; //$NON-NLS-1$
	public static String ContactsRepository_ErrorAddingContact;
	public static String ContactsRepository_ErrorCreatingGroup;
	public static String ContactsRepository_ErrorDeletingRemote;
	public static String ContactsRepository_ErrorResolving;
	public static String ContactsRepository_ErrorUpdatingCategory;
	public static String ContactsRepository_ErrorUpdatingContact;
	public static String ContactsRepository_ErrorValidatingCredentials;
	public static String GoogleContactsConnectionWizardPage_APIUrl;
	public static String GoogleContactsConnectionWizardPage_Credentials;
	public static String GoogleContactsConnectionWizardPage_EnterCredentials;
	public static String GoogleContactsConnectionWizardPage_ErrorValidatingSettings;
	public static String GoogleContactsConnectionWizardPage_GoogleContactConnector;
	public static String GoogleContactsConnectionWizardPage_Name;
	public static String GoogleContactsConnectionWizardPage_Password;
	public static String GoogleContactsConnectionWizardPage_Username;
	public static String GoogleContactsConnectionWizardPage_ValidateCredentials;
	public static String NewGoogleContactsRepositoryWizard_NewConnector;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
