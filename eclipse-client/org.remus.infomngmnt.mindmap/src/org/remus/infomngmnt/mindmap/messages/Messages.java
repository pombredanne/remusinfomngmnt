package org.remus.infomngmnt.mindmap.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.mindmap.messages.messages"; //$NON-NLS-1$
	public static String MindMapEditPage_General;
	public static String MindMapEditPage_OpenEditor;
	public static String NewMindmapWizard_Subtitle;
	public static String NewMindmapWizard_Title;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
