package org.remus.infomngmnt.ui.remote;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.category.CategorySmartField;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.StatusCreator;

public class GeneralChangeSetProcessingDialog extends TitleAreaDialog {

	private Tree tree;
	private Text text;
	private Text parentCategoryText;
	private final Category selection;
	private final ChangeSet changeSet;
	private String categoryString;
	private final DiffModel diffModel;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public GeneralChangeSetProcessingDialog(final Shell parentShell, final ChangeSet changeSet,
			final DiffModel diffModel) {
		super(parentShell);
		this.changeSet = changeSet;
		this.diffModel = diffModel;
		this.selection = changeSet.getTargetCategory();
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);

		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		container.setLayout(gridLayout);
		//

		final Label parentCategoryLabel = new Label(container, SWT.NONE);
		parentCategoryLabel.setText("Parent Category");

		this.parentCategoryText = new Text(container, SWT.BORDER);
		new CategorySmartField(this.parentCategoryText);
		this.parentCategoryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		if (this.selection != null) {
			this.parentCategoryText.setText(CategoryUtil.categoryToString(this.selection));
		}
		this.parentCategoryText.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				validatePage();
			}
		});

		final Button browseButton = new Button(container, SWT.NONE);
		browseButton.setText("B&rowse...");
		browseButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(
						UIPlugin.getDefault().getEditService().getAdapterFactory());
				AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(
						UIPlugin.getDefault().getEditService().getAdapterFactory());
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
				dialog.setInput(UIPlugin.getDefault().getApplicationService().getModel());
				dialog.setInitialSelection(GeneralChangeSetProcessingDialog.this.selection);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					Category selectedCategory = (Category) result[0];
					GeneralChangeSetProcessingDialog.this.parentCategoryText.setText(CategoryUtil
							.categoryToString(selectedCategory));
				}
			}

		});

		final Label changesetLabel = new Label(container, SWT.NONE);
		final GridData gd_changesetLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		changesetLabel.setLayoutData(gd_changesetLabel);
		changesetLabel.setText("Change-Set");

		final TreeViewer treeViewer = new TreeViewer(container, SWT.BORDER);

		this.tree = treeViewer.getTree();
		this.tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));

		treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(AdapterUtils
				.getAdapterFactory()));
		treeViewer.setContentProvider(new AdapterFactoryContentProvider(AdapterUtils
				.getAdapterFactory()));

		treeViewer.setInput(this.diffModel);
		//
		return area;
	}

	protected void validatePage() {
		this.categoryString = this.parentCategoryText.getText();

	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

	public String getCategoryString() {
		return this.categoryString;
	}

}
