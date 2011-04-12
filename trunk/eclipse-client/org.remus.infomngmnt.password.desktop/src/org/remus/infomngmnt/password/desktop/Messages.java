package org.remus.infomngmnt.password.desktop;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.password.desktop.messages"; //$NON-NLS-1$
	public static String PasswordTraySection_CopyPassword;
	public static String PasswordTraySection_CopyUsername;
	public static String PasswordTraySection_FillUsername;
	public static String PasswordTraySection_OpenUrlInBrowser;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
