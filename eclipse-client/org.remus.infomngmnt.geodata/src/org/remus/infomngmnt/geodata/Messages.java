package org.remus.infomngmnt.geodata;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.geodata.messages"; //$NON-NLS-1$
	public static String GeoDataEditorPreferencePage_GetMapsKey;
	public static String GeoDataEditorPreferencePage_GetMapsKeyLink;
	public static String GeoDataEditorPreferencePage_GetMapsKeyUrl;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
