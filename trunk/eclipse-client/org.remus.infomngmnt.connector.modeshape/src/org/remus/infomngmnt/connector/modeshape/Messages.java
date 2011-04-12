package org.remus.infomngmnt.connector.modeshape;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.connector.modeshape.messages"; //$NON-NLS-1$
	public static String ModeshapeConnectionWizardPage_Authentification;
	public static String ModeshapeConnectionWizardPage_EnterUrl;
	public static String ModeshapeConnectionWizardPage_ErrorValidating;
	public static String ModeshapeConnectionWizardPage_ModeShapeConnector;
	public static String ModeshapeConnectionWizardPage_Name;
	public static String ModeshapeConnectionWizardPage_Password;
	public static String ModeshapeConnectionWizardPage_URL;
	public static String ModeshapeConnectionWizardPage_Username;
	public static String ModeshapeConnectionWizardPage_UseSeparateWorkspace;
	public static String ModeshapeConnectionWizardPage_Validate;
	public static String ModeshapeConnectionWizardPage_Workspace;
	public static String ModeshapeConnector_ErrorAdding;
	public static String ModeshapeConnector_ErrorAddingFileReference;
	public static String ModeshapeConnector_ErrorDelete;
	public static String ModeshapeConnector_ErrorLoadingTransfer;
	public static String ModeshapeConnector_ErrorPackaging;
	public static String ModeshapeConnector_ErrorPoolCategory;
	public static String ModeshapeConnector_ErrorUpdating;
	public static String ModeshapeConnector_ErrorUpdating2;
	public static String ModeshapeConnector_NotSupported;
	public static String NewModeshapeRepositoryWizard_NewModeShapeConnector;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
