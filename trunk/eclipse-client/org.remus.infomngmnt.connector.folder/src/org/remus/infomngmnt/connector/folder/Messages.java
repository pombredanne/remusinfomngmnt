package org.remus.infomngmnt.connector.folder;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.connector.folder.messages"; //$NON-NLS-1$
	public static String FolderConnectionWizardPage_Browse;
	public static String FolderConnectionWizardPage_ConnectorName;
	public static String FolderConnectionWizardPage_Name;
	public static String FolderConnectionWizardPage_NoValidFolder;
	public static String FolderConnectionWizardPage_Path;
	public static String FolderConnectionWizardPage_SelectSharedFolder;
	public static String FolderConnectionWizardPage_WizardSubtitle;
	public static String FolderConnector_ErrorAddingElement;
	public static String FolderConnector_ErrorCommittingCategory;
	public static String FolderConnector_ErrorCommittingElement;
	public static String FolderConnector_ErrorDeletingItem;
	public static String FolderConnector_NoValidCategory;
	public static String FolderConnector_RemoteObjectNotFound;
	public static String NewFolderRepositoryWizard_Local;
	public static String NewFolderRepositoryWizard_LocalFolderRepository;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
