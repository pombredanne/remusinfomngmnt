package org.remus.infomngmnt.birtreport.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.birtreport.messages.messages"; //$NON-NLS-1$
	public static String CategorySelectorControl_Browse;
	public static String CategorySelectorControl_NoParentSelected;
	public static String CategorySelectorControl_SelectCategory;
	public static String GeneralReportPage_Browse;
	public static String GeneralReportPage_CreateFromFile;
	public static String GeneralReportPage_CreateFromReportTemplate;
	public static String GeneralReportPage_File;
	public static String GeneralReportPage_NameFile;
	public static String GeneralReportPage_NewReport;
	public static String GeneralReportPage_NoPathSpecified;
	public static String GeneralReportPage_NotAValidFile;
	public static String GeneralReportPage_NoTemplateSelected;
	public static String GeneralReportPage_Reports;
	public static String GeneralReportPage_WizardSubtitle;
	public static String InfoTypeSelector_Browse;
	public static String InfoTypeSelector_InfoTypeNotInstalled;
	public static String InfoTypeSelector_SelectionRequired;
	public static String InfoTypeSelector_SelectItem;
	public static String InfoTypeSelector_SelectItemFromList;
	public static String NewReportWizard_NewReport;
	public static String ParameterSelectionPage_ReportParameterSelection;
	public static String ParameterSelectionPage_TemplateNeedsParameter;
	public static String ReportParameterDialog_AddEditParameter;
	public static String ReportParameterDialog_ParameterName;
	public static String ReportParameterDialog_ParameterNameMandatory;
	public static String ReportParameterDialog_ParameterValue;
	public static String ReportParameterPage_Add;
	public static String ReportParameterPage_Delete;
	public static String ReportParameterPage_Edit;
	public static String ReportParameterPage_ParameterName;
	public static String ReportParameterPage_ParameterValue;
	public static String ReportParameterPage_ReportParameters;
	public static String ReportTemplateManager_Uncategorized;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
