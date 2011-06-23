package org.remus.infomngmnt.file;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.file.messages"; //$NON-NLS-1$
	public static String FileCreationTrigger_DownloadingFile;
	public static String FileCreationTrigger_ErrorDownloading;
	public static String FileEditPage_ChangeFile;
	public static String FileEditPage_Filename;
	public static String FileEditPage_General;
	public static String FileEditPage_OpenWithExternal;
	public static String GeneralFilePage_Browse;
	public static String GeneralFilePage_File;
	public static String GeneralFilePage_NameAndFile;
	public static String GeneralFilePage_WizardSubtitle;
	public static String GeneralFilePage_WizardTitle;
	public static String NewFileWizard_NewFile;
	public static String OpenFileDialog_Message;
	public static String OpenFileDialog_RefreshFiles;
	public static String OpenFileDialog_Title;
	public static String SaveFileOnDiskHandler_ErrorSaving;
	public static String SaveFileOnDiskHandler_ErrorSaving2;
	public static String SaveFileOnDiskHandler_ErrorSaving3;
	public static String SaveFileOnDiskHandler_Saving;
	public static String SaveFileOnDiskHandler_SavingFiles;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
