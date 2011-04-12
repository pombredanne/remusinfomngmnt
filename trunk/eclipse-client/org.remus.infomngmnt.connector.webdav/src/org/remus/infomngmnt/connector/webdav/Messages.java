package org.remus.infomngmnt.connector.webdav;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.connector.webdav.messages"; //$NON-NLS-1$
	public static String WebdavConnectionWizardPage_Authentication;
	public static String WebdavConnectionWizardPage_ErrorValidating;
	public static String WebdavConnectionWizardPage_Name;
	public static String WebdavConnectionWizardPage_Password;
	public static String WebdavConnectionWizardPage_Url;
	public static String WebdavConnectionWizardPage_UseAuthentication;
	public static String WebdavConnectionWizardPage_Username;
	public static String WebdavConnectionWizardPage_Validate;
	public static String WebdavConnectionWizardPage_WizardSubtitle;
	public static String WebdavConnectionWizardPage_WizardTitle;
	public static String WebdavRepositoryWizard_WebDAV;
	public static String WebDAVConnector_ErrorAdding;
	public static String WebDAVConnector_ErrorBuilding;
	public static String WebDAVConnector_ErrorCommitting;
	public static String WebDAVConnector_ErrorCommittingCategory;
	public static String WebDAVConnector_ErrorDeleting;
	public static String WebDAVConnector_ErrorGettingCategory;
	public static String WebDAVConnector_ErrorResolving;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
