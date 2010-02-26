package org.remus.infomngmnt.spellengine;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.spellengine.messages"; //$NON-NLS-1$
	public static String AddWordProposal_DisplayString;
	public static String AddWordProposal_dlgMessage;
	public static String AddWordProposal_dlgTitle;
	public static String AddWordProposal_dlgToggle;
	public static String ChangeCaseProposal_ChangeCase;
	public static String DisableSpellCheckingProposal_DisplayString;
	public static String WordCorrectionProposal_DisplayString;
	public static String WordIgnoreProposal_IgnoreMessage;
	public static String WordIgnoreProposal_IgnoreMessage1;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
