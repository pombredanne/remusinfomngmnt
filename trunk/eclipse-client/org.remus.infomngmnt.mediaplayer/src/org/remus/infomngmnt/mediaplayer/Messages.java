package org.remus.infomngmnt.mediaplayer;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.mediaplayer.messages"; //$NON-NLS-1$
	public static String MediaPlayerExtensionService_NoPlayerInstalled;
	public static String MediaPlayerExtensionService_Unknown;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
