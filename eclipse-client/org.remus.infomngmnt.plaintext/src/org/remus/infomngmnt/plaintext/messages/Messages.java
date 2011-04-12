package org.remus.infomngmnt.plaintext.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.plaintext.messages.messages"; //$NON-NLS-1$
	public static String FormPage_General;
	public static String InstantCopySection_InfoUnitNotFound;
	public static String InstantSectionPreferences_Browse;
	public static String InstantSectionPreferences_InfoUnitToCopyFrom;
	public static String InstantSectionPreferences_SelectionRequired;
	public static String InstantSectionPreferences_SelectTextInfoUnit;
	public static String NewPlainTextWizard_Subtitle;
	public static String NewPlainTextWizard_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
