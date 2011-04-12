package org.remus.infomngmnt.eclipsemarketplace.connector;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.remus.infomngmnt.eclipsemarketplace.connector.messages"; //$NON-NLS-1$
	public static String MarketplaceConnector_ContactingMarketForElement;
	public static String MarketplaceConnector_DeletionNotSupported;
	public static String MarketplaceConnector_ErrorElements;
	public static String MarketplaceConnector_ErrorInitECF;
	public static String MarketplaceConnector_ErrorMarkets;
	public static String MarketPlaceWizardPage_APIUrl;
	public static String MarketPlaceWizardPage_EclipseMarketplace;
	public static String MarketPlaceWizardPage_Name;
	public static String MarketPlaceWizardPage_WizardSubtitle;
	public static String MarketPlaceWizardPage_WizardTitle;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
