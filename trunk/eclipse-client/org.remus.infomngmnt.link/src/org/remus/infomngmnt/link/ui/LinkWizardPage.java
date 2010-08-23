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
import org.remus.infomngmnt.link.preferences.LinkPreferenceInitializer;
import org.remus.infomngmnt.link.webshot.WebshotUtil;

public class LinkWizardPage extends WizardPage {

	private final InformationUnit newElement;

	public LinkWizardPage(final InformationUnit newElement) {
		super("wizardPage");
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

		setTitle("New Link");
		setMessage("This wizard enables you to create a new link from a url.");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(LinkActivator.getDefault(),
				"icons/iconexperience/link_wizard_title.png"));

		setControl(container);

		final Button makeContentSearchableButton = new Button(container, SWT.CHECK);
		makeContentSearchableButton.setText("Make Content searchable");
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
				.setText("The content will be indexed and is avaialable for the local search.");

		final Label label = new Label(container, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		final Button makeScreenhotOfButton = new Button(container, SWT.CHECK);
		makeScreenhotOfButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		makeScreenhotOfButton.setText("Make Screenshot of target");
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
		eclipseorgLink.setText("<a>Why is the screenshot feature disabled?</a>");
		eclipseorgLink.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Program.launch(LinkActivator.getDefault().getPreferenceStore().getString(
						LinkPreferenceInitializer.URL_WEBSHOTHELP));
			}
		});
	}

}
