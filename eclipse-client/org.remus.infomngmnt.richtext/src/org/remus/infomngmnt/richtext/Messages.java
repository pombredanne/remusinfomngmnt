package org.remus.infomngmnt.richtext;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.richtext.messages"; //$NON-NLS-1$
	public static String RichTextEditPage_Font;
	public static String RichTextEditPage_Format;
	public static String RichTextEditPage_General;
	public static String RichTextEditPage_PresaveFormatting;
	public static String RichTextEditPage_Size;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
