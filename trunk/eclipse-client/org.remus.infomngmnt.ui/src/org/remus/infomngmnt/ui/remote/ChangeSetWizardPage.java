package org.remus.infomngmnt.ui.remote;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.common.core.util.CollectionFilter;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.common.ui.wizards.IValidatingWizard;
import org.remus.infomngmnt.common.ui.wizards.WizardValidatingUtil;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.category.CategorySmartField;

public class ChangeSetWizardPage extends WizardPage implements IValidatingWizard {

	private Text parentCategoryText;
	private Category selection;
	private Tree tree;
	private final ChangeSet changeSet;
	private TreeViewer treeViewer;
	private Category targetCategory;

	/**
	 * Create the wizard
	 * 
	 * @param changeSet2
	 */
	public ChangeSetWizardPage(final ChangeSet changeSet) {
		super("wizardPage");
		this.changeSet = changeSet;
		setTitle("Checkout of elements");
		setDescription("Please select a category where the remote items are stored.");

	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);
		setImageDescriptor(ResourceManager.getPluginImageDescriptor(UIPlugin.getDefault(),
				"icons/iconexperience/wizards/changeset_wizard.png"));

		final Label parentCategoryLabel = new Label(container, SWT.NONE);
		parentCategoryLabel.setText("Parent Category");

		this.parentCategoryText = new Text(container, SWT.BORDER);
		CategorySmartField smartField = new CategorySmartField(this.parentCategoryText);
		smartField.setFilter(new CollectionFilter<Category>() {
			public boolean select(final Category item) {
				return item.getSynchronizationMetaData() == null;
			}
		});
		this.parentCategoryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		if (this.selection != null) {
			this.parentCategoryText.setText(CategoryUtil.categoryToString(this.selection));
		}

		ISWTObservableValue observeDelayedValue = SWTObservables.observeDelayedValue(500,
				SWTObservables.observeText(this.parentCategoryText, SWT.Modify));
		observeDelayedValue.addValueChangeListener(new IValueChangeListener() {
			public void handleValueChange(final ValueChangeEvent event) {
				String newText = (String) event.getObservableValue().getValue();
				handleCategoryStringChanged(newText);
			}

		});

		final Button browseButton = new Button(container, SWT.NONE);
		browseButton.setText("B&rowse...");
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(
						EditingUtil.getInstance().getAdapterFactory());
				AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(
						EditingUtil.getInstance().getAdapterFactory());
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
						adapterFactoryLabelProvider, adapterFactoryContentProvider);
				dialog.setAllowMultiple(false);
				dialog.setDoubleClickSelects(true);
				dialog.setValidator(new ISelectionStatusValidator() {
					public IStatus validate(final Object[] selection) {
						if (selection.length == 0) {
							return StatusCreator.newStatus("No parent category selected...");
						}
						return StatusCreator.newStatus(IStatus.OK, "", null);
					}
				});
				dialog.addFilter(new ViewerFilter() {
					@Override
					public boolean select(final Viewer viewer, final Object parentElement,
							final Object element) {
						return element instanceof Category
								&& ((SynchronizableObject) element).getSynchronizationMetaData() == null;
					}
				});
				dialog.setInput(ApplicationModelPool.getInstance().getModel());
				dialog.setInitialSelection(ChangeSetWizardPage.this.selection);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					Category selectedCategory = (Category) result[0];
					ChangeSetWizardPage.this.parentCategoryText.setText(CategoryUtil
							.categoryToString(selectedCategory));
				}
			}

		});

		final Label changesetLabel = new Label(container, SWT.NONE);
		final GridData gd_changesetLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		changesetLabel.setLayoutData(gd_changesetLabel);
		changesetLabel.setText("Preview");

		this.treeViewer = new TreeViewer(container, SWT.BORDER);

		this.tree = this.treeViewer.getTree();

		this.tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		this.treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(EditingUtil.getInstance()
				.getAdapterFactory()));
		this.treeViewer.setContentProvider(new AdapterFactoryContentProvider(EditingUtil
				.getInstance().getAdapterFactory()) {
			@Override
			public boolean hasChildren(final Object object) {
				if (object instanceof InformationUnitListItem) {
					return false;
				}
				return super.hasChildren(object);
			}

			@Override
			public Object[] getChildren(final Object object) {
				return super.getChildren(object);
			}
		});
		this.treeViewer.addFilter(new ViewerFilter() {
			@Override
			public boolean select(final Viewer viewer, final Object parentElement,
					final Object element) {
				return !(element instanceof SynchronizationMetadata);
			}
		});
		Category tmpRemoteRootCategory = InfomngmntFactory.eINSTANCE.createCategory();

		EList<ChangeSetItem> changeSetItems = this.changeSet.getChangeSetItems();
		for (ChangeSetItem changeSetItem2 : changeSetItems) {
			tmpRemoteRootCategory.getChildren().add(
					(Category) EcoreUtil.copy(changeSetItem2.getRemoteConvertedContainer()));
		}
		this.treeViewer.setInput(tmpRemoteRootCategory);

		initValidation();
		setControl(container);
	}

	protected void handleCategoryStringChanged(final String newText) {
		this.targetCategory = CategoryUtil.findCategory(newText, false);
	}

	protected void initValidation() {
		setPageComplete(validate(false));
		WizardValidatingUtil.validateControlsOnModify(this, this.parentCategoryText);
	}

	public boolean validate(final boolean showErrorMessage) {

		if (CategoryUtil.findCategory(this.parentCategoryText.getText(), false) == null
				|| CategoryUtil.findCategory(this.parentCategoryText.getText(), false)
						.getSynchronizationMetaData() != null) {
			if (showErrorMessage) {
				setErrorMessage("No valid category");
			}
			return false;
		}
		setErrorMessage(null);
		return true;
	}

	/**
	 * @return the targetCategory
	 */
	public Category getTargetCategory() {
		return this.targetCategory;
	}

}
