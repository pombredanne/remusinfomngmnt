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

	private Text descriptionText;
	private Text keywordsText;
	private Text nameText;
	private Text parentInformationUnitText;
	private Text parentCategoryText;
	private InformationUnitListItem selection;
	private Category category;
	private Button browserButton;
	private Button browseButton;
	private Button categoryButton;
	private Button informationunitButton;
	private String categoryString;
	private String nameString;
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
				NewPlainTextWizardPage.this.parentCategoryText.setEnabled(((Button) event.widget).getSelection());
				NewPlainTextWizardPage.this.browserButton.setEnabled(((Button) event.widget).getSelection());
			}
		});

		this.parentCategoryText = new Text(parentElementGroup, SWT.BORDER);
		final GridData gd_parentCategoryText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.parentCategoryText.setLayoutData(gd_parentCategoryText);
		this.parentCategoryText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				validatePage();
			}
		});
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
					NewPlainTextWizardPage.this.parentCategoryText.setText(CategoryUtil.categoryToString(NewPlainTextWizardPage.this.category));
				}
			}
		});

		this.informationunitButton = new Button(parentElementGroup, SWT.RADIO);
		this.informationunitButton.setText("Information-Unit");
		this.informationunitButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				NewPlainTextWizardPage.this.parentInformationUnitText.setEnabled(((Button) event.widget).getSelection());
				NewPlainTextWizardPage.this.browseButton.setEnabled(((Button) event.widget).getSelection());
			}
		});
		this.parentInformationUnitText = new Text(parentElementGroup, SWT.BORDER);
		final GridData gd_parentInformationUnitText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.parentInformationUnitText.setLayoutData(gd_parentInformationUnitText);

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

		this.nameText = new Text(propertiesGroup, SWT.BORDER);
		final GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.nameText.setLayoutData(gd_nameText);
		this.nameText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				validatePage();
			}
		});


		final Label keywordsLabel = new Label(propertiesGroup, SWT.NONE);
		keywordsLabel.setText("Keywords");

		this.keywordsText = new Text(propertiesGroup, SWT.BORDER);
		final GridData gd_keywordsText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.keywordsText.setLayoutData(gd_keywordsText);


		final Label descriptionLabel = new Label(propertiesGroup, SWT.NONE);
		descriptionLabel.setText("Description");

		this.descriptionText = new Text(propertiesGroup, SWT.WRAP | SWT.V_SCROLL | SWT.MULTI | SWT.H_SCROLL | SWT.BORDER);
		final GridData gd_descriptionText = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.descriptionText.setLayoutData(gd_descriptionText);
		presetValues();
		setPageComplete(false);
		setControl(container);
	}

	void validatePage() {
		if(this.categoryButton.getSelection()) {
			this.categoryString = this.parentCategoryText.getText();
			IStatus categoryPathStringValid = CategoryUtil.isCategoryPathStringValid(this.parentCategoryText.getText());
			if (categoryPathStringValid.getSeverity() != IStatus.OK) {
				setErrorMessage(categoryPathStringValid.getMessage());
				setPageComplete(false);
				return;
			}
		}
		IStatus categoryNameValid = CategoryUtil.isCategoryNameValid(this.nameText.getText());
		this.nameString = this.nameText.getText();
		if (categoryNameValid.getSeverity() != IStatus.OK) {
			setErrorMessage(categoryNameValid.getMessage());
			setPageComplete(false);
			return;
		}
		setErrorMessage(null);
		setPageComplete(true);

	}

	private void presetValues() {
		if (this.category != null) {
			this.categoryButton.setSelection(true);
			this.parentCategoryText.setText(CategoryUtil.categoryToString(this.category));
		} else if (this.selection != null) {
			this.informationunitButton.setSelection(true);
		} else {
			this.categoryButton.setSelection(true);
		}

	}

	public String getCategoryString() {
		return this.categoryString;
	}

	public String getNameString() {
		return this.nameString;
	}

}
