package org.remus.infomngmnt.password.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.password.messages.messages"; //$NON-NLS-1$
	public static String EditPasswordPage_Additional;
	public static String EditPasswordPage_General;
	public static String EditPasswordPage_Generate;
	public static String EditPasswordPage_OpenUrlInSystemBrowser;
	public static String EditPasswordPage_Password;
	public static String EditPasswordPage_Url;
	public static String EditPasswordPage_Username;
	public static String NewPasswordWizard_Subtitle;
	public static String NewPasswordWizard_Title;
	public static String PasswordGenerationDialog_AdditionalCharacters;
	public static String PasswordGenerationDialog_ApplyPassword;
	public static String PasswordGenerationDialog_DefaultLength;
	public static String PasswordGenerationDialog_Generate;
	public static String PasswordGenerationDialog_GeneratePassword;
	public static String PasswordGenerationDialog_GeneratePasswords;
	public static String PasswordGenerationDialog_Properties;
	public static String PasswordGenerationDialog_QuantityCharacters;
	public static String PasswordGenerationDialog_UserDefined;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
