package org.remus.infomngmnt.link.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.remus.InformationUnit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;

import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.link.internal.ResourceManager;
import org.remus.infomngmnt.link.messsage.Messages;
import org.remus.infomngmnt.link.preferences.LinkPreferenceInitializer;
import org.remus.infomngmnt.link.webshot.WebshotUtil;

public class LinkWizardPage extends WizardPage {

	private final InformationUnit newElement;

	public LinkWizardPage(final InformationUnit newElement) {
		super("wizardPage"); //$NON-NLS-1$
		this.newElement = newElement;

	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());
		final IPreferenceStore store = LinkActivator.getDefault().getPreferenceStore();
		//

		setTitle(Messages.LinkWizardPage_Title);
		setMessage(Messages.LinkWizardPage_Subtitle);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(LinkActivator.getDefault(),
				"icons/iconexperience/link_wizard_title.png")); //$NON-NLS-1$

		setControl(container);

		final Button makeContentSearchableButton = new Button(container, SWT.CHECK);
		makeContentSearchableButton.setText(Messages.LinkWizardPage_MakeContentSearchable);
		makeContentSearchableButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				store.setValue(LinkPreferenceInitializer.INDEX_DOCUMENT, ((Button) event.widget)
						.getSelection());

			}

		});
		makeContentSearchableButton.setSelection(store
				.getBoolean(LinkPreferenceInitializer.INDEX_DOCUMENT));

		final Label theContentWillLabel = new Label(container, SWT.NONE);
		theContentWillLabel
				.setText(Messages.LinkWizardPage_MakeContentSearchableDescription);

		final Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Button makeScreenhotOfButton = new Button(container, SWT.CHECK);
		makeScreenhotOfButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		makeScreenhotOfButton.setText(Messages.LinkWizardPage_MakeScreenshot);
		makeScreenhotOfButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(final Event event) {
				store.setValue(LinkPreferenceInitializer.MAKE_SCREENSHOT, ((Button) event.widget)
						.getSelection());

			}

		});
		makeScreenhotOfButton.setSelection(store
				.getBoolean(LinkPreferenceInitializer.MAKE_SCREENSHOT));
		makeScreenhotOfButton.setEnabled(WebshotUtil.isWebShotToolingEnabled());
		final Link eclipseorgLink = new Link(container, SWT.NONE);
		eclipseorgLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		eclipseorgLink.setText(Messages.LinkWizardPage_WhyDisable);
		eclipseorgLink.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Program.launch(LinkActivator.getDefault().getPreferenceStore().getString(
						LinkPreferenceInitializer.URL_WEBSHOTHELP));
			}
		});
	}

}
