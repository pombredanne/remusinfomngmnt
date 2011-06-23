package org.remus.infomngmnt.connector.dropbox;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.connector.dropbox.messages"; //$NON-NLS-1$
	public static String DropboxConnectionWizardPage_RepoName;
	public static String DropboxConnectionWizardPage_WizardMessage;
	public static String DropboxConnectionWizardPage_WizardTitle;
	public static String DropboxConnector_ErrorAddingCategory;
	public static String DropboxConnector_ErrorAddingElement;
	public static String DropboxConnector_ErrorBuildingSingleCategory;
	public static String DropboxConnector_ErrorComittingCategory;
	public static String DropboxConnector_ErrorCommittingElement;
	public static String DropboxConnector_ErrorConnecting;
	public static String DropboxConnector_ErrorDeleing;
	public static String DropboxConnector_ErrorLoadingSubfolders;
	public static String DropboxConnector_ErrorResolving;
	public static String DropboxConnector_ErrorResolvingBinaryReference;
	public static String NewDropboxRepositoryWizard_Error;
	public static String NewDropboxRepositoryWizard_ErrorConnecting;
	public static String NewDropboxRepositoryWizard_ErrorCreating;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
