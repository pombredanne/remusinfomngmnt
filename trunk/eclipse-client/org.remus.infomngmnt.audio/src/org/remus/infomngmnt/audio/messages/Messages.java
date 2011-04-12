package org.remus.infomngmnt.audio.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.audio.messages.messages"; //$NON-NLS-1$
	public static String AudioEditPage_AudioName;
	public static String AudioEditPage_ChangeFile;
	public static String AudioEditPage_General;
	public static String AudioEditPage_OpenWithExternal;
	public static String AudioEditPage_SupportedAudios;
	public static String AudioRepresentation_ErrorReadingLocation;
	public static String GeneralAudioPage_AudioFiles;
	public static String GeneralAudioPage_Browse;
	public static String GeneralAudioPage_File;
	public static String GeneralAudioPage_MediaType;
	public static String GeneralAudioPage_NameFile;
	public static String GeneralAudioPage_NewAudion;
	public static String GeneralAudioPage_WizardSubtitle;
	public static String NewAudioWizard_NewAudio;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
