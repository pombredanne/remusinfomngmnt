package org.remus.infomngmnt.video.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.video.messages.messages"; //$NON-NLS-1$
	public static String GeneralVideoPage_Browse;
	public static String GeneralVideoPage_File;
	public static String GeneralVideoPage_MediaType;
	public static String GeneralVideoPage_NameFile;
	public static String GeneralVideoPage_Subtitle;
	public static String GeneralVideoPage_Title;
	public static String GeneralVideoPage_Videos;
	public static String NewVideoWizard_Title;
	public static String VideoEditPage_ChangeVideoFile;
	public static String VideoEditPage_General;
	public static String VideoEditPage_Height;
	public static String VideoEditPage_KeepRatio;
	public static String VideoEditPage_OpenWithExternal;
	public static String VideoEditPage_px;
	public static String VideoEditPage_SupportedVideos;
	public static String VideoEditPage_VideoName;
	public static String VideoEditPage_Width;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
