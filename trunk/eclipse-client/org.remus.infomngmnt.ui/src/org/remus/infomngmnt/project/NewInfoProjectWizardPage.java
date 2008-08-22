package org.remus.infomngmnt.project;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewInfoProjectWizardPage extends WizardPage {

	private Text text_2;
	private Text text_1;
	private Text text;
	/**
	 * Create the wizard
	 */
	public NewInfoProjectWizardPage() {
		super("wizardPage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		//

		final Label giveYourProjectLabel = new Label(container, SWT.WRAP);
		final GridData gd_giveYourProjectLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		giveYourProjectLabel.setLayoutData(gd_giveYourProjectLabel);
		giveYourProjectLabel.setText("Give your project a meaningful name like \"My Project X at work\", \"Holiday in France\" or\n \"Research Good Food\"");

		final Label repositoryNameLabel = new Label(container, SWT.NONE);
		repositoryNameLabel.setText("Name:");

		this.text = new Text(container, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label locationLabel = new Label(container, SWT.NONE);
		locationLabel.setText("Location");

		this.text_2 = new Text(container, SWT.READ_ONLY | SWT.BORDER);
		this.text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label descriptionLabel = new Label(container, SWT.NONE);
		descriptionLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));
		descriptionLabel.setText("Description");

		this.text_1 = new Text(container, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		this.text_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		setControl(container);

	}

}
