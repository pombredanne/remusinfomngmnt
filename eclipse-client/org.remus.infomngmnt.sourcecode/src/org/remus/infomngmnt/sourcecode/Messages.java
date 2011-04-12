package org.remus.infomngmnt.sourcecode;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.sourcecode.messages"; //$NON-NLS-1$
	public static String EditSourcePage_General;
	public static String EditSourcePage_Name;
	public static String EditSourcePage_SourceCode;
	public static String EditSourcePage_Type;
	public static String GeneralSourcePage_NameType;
	public static String GeneralSourcePage_Subtitle;
	public static String GeneralSourcePage_Title;
	public static String GeneralSourcePage_Type;
	public static String NewSourceCodeWizard_Title;
	public static String SourceCodePlugin_Assembly;
	public static String SourceCodePlugin_CPlusPlus;
	public static String SourceCodePlugin_CSS;
	public static String SourceCodePlugin_HTML;
	public static String SourceCodePlugin_Java;
	public static String SourceCodePlugin_JavaScript;
	public static String SourceCodePlugin_JSP;
	public static String SourceCodePlugin_LISP;
	public static String SourceCodePlugin_Make;
	public static String SourceCodePlugin_Pearl;
	public static String SourceCodePlugin_PHP;
	public static String SourceCodePlugin_TeX;
	public static String SourceCodePlugin_WindowsScript;
	public static String SourceCodePlugin_XML;
	public static String SourceCodePreferencePage_ColorSheme;
	public static String SourceCodePreferencePage_ExtractLineNumber;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
