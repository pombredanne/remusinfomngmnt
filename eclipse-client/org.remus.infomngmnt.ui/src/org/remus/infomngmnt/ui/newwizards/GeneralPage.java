package org.remus.infomngmnt.ui.newwizards;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.databinding.EMFDataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
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
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.wizards.IValidatingWizard;
import org.remus.infomngmnt.common.ui.wizards.WizardValidatingUtil;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.ui.category.CategorySmartField;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GeneralPage extends WizardPage implements IInfoObjectSetter, IValidatingWizard {

	protected Text descriptionText;
	protected Text keywordsText;
	protected Text nameText;
	private Text parentCategoryText;
	private Category category;
	private Button browserButton;
	private String categoryString;
	protected InformationUnit unit;
	protected final EMFDataBindingContext ctx;

	public GeneralPage(final InformationUnitListItem selection) {
		this((Category) selection.eContainer());
	}

	public GeneralPage(final Category category) {
		super("wizardPage");
		this.category = category;
		this.ctx = new EMFDataBindingContext();

	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());

		doCreateParentElementGroup(container);
		doCreateNameElements(container);
		doCreatePropertiesGroup(container);
		initDatabinding();
		presetValues();
		initValidation();
		setControl(container);
	}

	protected void initValidation() {
		setPageComplete(validate(false));
		WizardValidatingUtil.validateControlsOnModify(this, this.nameText, this.keywordsText,
				this.descriptionText, this.parentCategoryText);
	}

	protected void doCreateNameElements(final Composite container) {
		final Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Name");

		this.nameText = new Text(container, SWT.BORDER);
		final GridData gd_nameText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.nameText.setLayoutData(gd_nameText);

	}

	protected void doCreatePropertiesGroup(final Composite container) {
		final Group propertiesGroup = new Group(container, SWT.NONE);
		propertiesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));
		propertiesGroup.setText("Properties");
		final GridLayout gridLayout_1 = new GridLayout();
		gridLayout_1.numColumns = 2;
		propertiesGroup.setLayout(gridLayout_1);

		final Label keywordsLabel = new Label(propertiesGroup, SWT.NONE);
		keywordsLabel.setText("Keywords");

		this.keywordsText = new Text(propertiesGroup, SWT.BORDER);
		final GridData gd_keywordsText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.keywordsText.setLayoutData(gd_keywordsText);

		final Label descriptionLabel = new Label(propertiesGroup, SWT.NONE);
		descriptionLabel.setText("Description");

		this.descriptionText = new Text(propertiesGroup, SWT.WRAP | SWT.V_SCROLL | SWT.MULTI
				| SWT.H_SCROLL | SWT.BORDER);
		final GridData gd_descriptionText = new GridData(SWT.FILL, SWT.FILL, true, true);
		this.descriptionText.setLayoutData(gd_descriptionText);

	}

	protected void doCreateParentElementGroup(final Composite container) {

		final Group parentElementGroup = new Group(container, SWT.NONE);
		parentElementGroup.setText("Parent Element");
		final GridData gd_parentElementGroup = new GridData(SWT.FILL, SWT.CENTER, true, false);
		parentElementGroup.setLayoutData(gd_parentElementGroup);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		parentElementGroup.setLayout(gridLayout);

		this.parentCategoryText = new Text(parentElementGroup, SWT.BORDER);
		new CategorySmartField(this.parentCategoryText);
		final GridData gd_parentCategoryText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.parentCategoryText.setLayoutData(gd_parentCategoryText);
		if (this.categoryString != null) {
			this.parentCategoryText.setText(this.categoryString);
		}

		this.browserButton = new Button(parentElementGroup, SWT.NONE);
		this.browserButton.setText("B&rowse...");
		this.browserButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(
						EditingUtil.getInstance().getAdapterFactory());
				AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(
						EditingUtil.getInstance().getAdapterFactory());
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
						adapterFactoryLabelProvider, adapterFactoryContentProvider);
				dialog.setAllowMultiple(false);
				dialog.setTitle("Select a category");
				dialog.setMessage("Select a cateogry");
				dialog.setDoubleClickSelects(true);
				dialog.addFilter(new ViewerFilter() {
					@Override
					public boolean select(final Viewer viewer, final Object parentElement,
							final Object element) {
						return element instanceof Category;
					}
				});
				dialog.setValidator(new ISelectionStatusValidator() {
					public IStatus validate(final Object[] pselection) {
						if (pselection.length == 0) {
							return StatusCreator.newStatus("No parent category selected...");
						}
						return StatusCreator.newStatus(IStatus.OK, "", null);
					}
				});
				dialog.setInput(ApplicationModelPool.getInstance().getModel());
				dialog.setInitialSelection(GeneralPage.this.category);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					GeneralPage.this.category = (Category) result[0];
					GeneralPage.this.parentCategoryText.setText(CategoryUtil
							.categoryToString(GeneralPage.this.category));
				}
			}
		});

	}

	public boolean validate(final boolean showErrorMessage) {
		if (this.parentCategoryText != null) {
			this.categoryString = this.parentCategoryText.getText();
			IStatus categoryPathStringValid = CategoryUtil
					.isCategoryPathStringValid(this.parentCategoryText.getText());
			if (categoryPathStringValid.getSeverity() != IStatus.OK) {
				if (showErrorMessage) {
					setErrorMessage(categoryPathStringValid.getMessage());
				}
				return false;
			}
		}
		if (this.nameText != null) {
			IStatus categoryNameValid = CategoryUtil.isCategoryNameValid(this.nameText.getText());
			if (categoryNameValid.getSeverity() != IStatus.OK) {
				if (showErrorMessage) {
					setErrorMessage(categoryNameValid.getMessage());
				}
				return false;
			}
		}
		setErrorMessage(null);
		return true;
	}

	protected void presetValues() {
		if (this.category != null) {
			this.parentCategoryText.setText(CategoryUtil.categoryToString(this.category));
		}
	}

	public String getCategoryString() {
		return this.categoryString;
	}

	public InformationUnit getInformationUnit() {
		return null;
	}

	public void setInformationUnit(final InformationUnit unit) {
		if (this.unit != unit) {
			this.ctx.dispose();
			this.unit = unit;
		}
	}

	protected void initDatabinding() {
		if (this.nameText != null) {
			ISWTObservableValue swtName = SWTObservables.observeText(this.nameText, SWT.Modify);
			IObservableValue emfName = EMFObservables.observeValue(this.unit,
					InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL);
			this.ctx.bindValue(swtName, emfName, null, null);
		}
		if (this.keywordsText != null) {
			ISWTObservableValue swtKeyWords = SWTObservables.observeText(this.keywordsText,
					SWT.Modify);
			IObservableValue emfKeywords = EMFObservables.observeValue(this.unit,
					InfomngmntPackage.Literals.INFORMATION_UNIT__KEYWORDS);
			this.ctx.bindValue(swtKeyWords, emfKeywords, null, null);
		}
		if (this.descriptionText != null) {
			ISWTObservableValue swtDescription = SWTObservables.observeText(this.descriptionText,
					SWT.Modify);
			IObservableValue emfDescription = EMFObservables.observeValue(this.unit,
					InfomngmntPackage.Literals.INFORMATION_UNIT__DESCRIPTION);
			this.ctx.bindValue(swtDescription, emfDescription, null, null);
		}
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public void setCategoryString(final String categoryString) {
		this.categoryString = categoryString;
	}

}