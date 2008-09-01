package org.remus.infomngmnt.plaintext.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewPlainTextWizardPage extends WizardPage {

	private Text text_4;
	private Text text_3;
	private Text text_2;
	private Text text_1;
	private Text text;
	private InformationUnitListItem selection;
	private Category category;
	private Button browserButton;
	private Button browseButton;
	private Button categoryButton;
	private Button informationunitButton;
	/**
	 * Create the wizard
	 */
	public NewPlainTextWizardPage() {
		super("wizardPage");
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	public NewPlainTextWizardPage(InformationUnitListItem selection) {
		super("wizardPage");
		this.selection = selection;
	}
	public NewPlainTextWizardPage(Category category) {
		super("wizardPage");
		this.category = category;

	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());


		final Group parentElementGroup = new Group(container, SWT.NONE);
		parentElementGroup.setText("Parent Element");
		final GridData gd_parentElementGroup = new GridData(SWT.FILL, SWT.CENTER, true, false);
		parentElementGroup.setLayoutData(gd_parentElementGroup);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		parentElementGroup.setLayout(gridLayout);
		this.categoryButton = new Button(parentElementGroup, SWT.RADIO);
		this.categoryButton.setText("Category");
		this.categoryButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				NewPlainTextWizardPage.this.text.setEnabled(((Button) event.widget).getSelection());
				NewPlainTextWizardPage.this.browserButton.setEnabled(((Button) event.widget).getSelection());
			}
		});

		this.text = new Text(parentElementGroup, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.browserButton = new Button(parentElementGroup, SWT.NONE);
		this.browserButton.setText("B&rowse...");
		this.browserButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(EditingUtil.getInstance().getAdapterFactory());
				AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory());
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),adapterFactoryLabelProvider,adapterFactoryContentProvider);
				dialog.setAllowMultiple(false);
				dialog.setDoubleClickSelects(true);
				dialog.setValidator(new ISelectionStatusValidator() {
					public IStatus validate(Object[] pselection) {
						if (pselection.length == 0) {
							return StatusCreator.newStatus("No parent category selected...");
						}
						return StatusCreator.newStatus(IStatus.OK, "",null);
					}
				});
				dialog.setInput(ApplicationModelPool.getInstance().getModel());
				dialog.setInitialSelection(NewPlainTextWizardPage.this.category);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					NewPlainTextWizardPage.this.category = (Category) result[0];
					NewPlainTextWizardPage.this.text.setText(CategoryUtil.categoryToString(NewPlainTextWizardPage.this.category));
				}
			}
		});

		this.informationunitButton = new Button(parentElementGroup, SWT.RADIO);
		this.informationunitButton.setText("Information-Unit");
		this.informationunitButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				NewPlainTextWizardPage.this.text_1.setEnabled(((Button) event.widget).getSelection());
				NewPlainTextWizardPage.this.browseButton.setEnabled(((Button) event.widget).getSelection());
			}
		});
		this.text_1 = new Text(parentElementGroup, SWT.BORDER);
		this.text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		this.browseButton = new Button(parentElementGroup, SWT.NONE);
		this.browseButton.setText("Br&owse...");

		final Group propertiesGroup = new Group(container, SWT.NONE);
		propertiesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		propertiesGroup.setText("Properties");
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		propertiesGroup.setLayout(gridLayout_1);

		final Label nameLabel = new Label(propertiesGroup, SWT.NONE);
		nameLabel.setText("Name");

		this.text_2 = new Text(propertiesGroup, SWT.BORDER);
		this.text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label keywordsLabel = new Label(propertiesGroup, SWT.NONE);
		keywordsLabel.setText("Keywords");

		this.text_3 = new Text(propertiesGroup, SWT.BORDER);
		this.text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		final Label descriptionLabel = new Label(propertiesGroup, SWT.NONE);
		descriptionLabel.setText("Description");

		this.text_4 = new Text(propertiesGroup, SWT.WRAP | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		this.text_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		presetValues();
		setPageComplete(false);
		setControl(container);
	}

	private void presetValues() {
		if (this.category != null) {
			this.categoryButton.setSelection(true);
			this.text.setText(CategoryUtil.categoryToString(this.category));
		} else if (this.selection != null) {
			this.informationunitButton.setSelection(true);
		} else {
			this.categoryButton.setSelection(true);
		}

	}

}
