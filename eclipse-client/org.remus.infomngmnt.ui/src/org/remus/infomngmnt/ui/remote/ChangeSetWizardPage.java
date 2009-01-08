package org.remus.infomngmnt.ui.remote;

import java.util.Collections;

import org.eclipse.core.databinding.observable.value.IValueChangeListener;
import org.eclipse.core.databinding.observable.value.ValueChangeEvent;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.compare.util.AdapterUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.TreeViewer;
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
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.category.CategorySmartField;

public class ChangeSetWizardPage extends WizardPage {

	private Text parentCategoryText;
	private Category selection;
	private Tree tree;
	private DiffModel diffModel;
	private ChangeSet changeSet;
	private TreeViewer treeViewer;

	/**
	 * Create the wizard
	 * @param changeSet2 
	 */
	public ChangeSetWizardPage(final ChangeSet changeSet2) {
		super("wizardPage");
		this.changeSet = changeSet2;
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	/**
	 * Create contents of the wizard
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		
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
//		this.parentCategoryText.addListener(SWT.Modify, new Listener() {
//			public void handleEvent(final Event event) {
//				validatePage();
//			}
//		});
		
		ISWTObservableValue observeDelayedValue = SWTObservables.observeDelayedValue(500, SWTObservables.observeText(this.parentCategoryText, SWT.Modify));
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
				AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(EditingUtil.getInstance().getAdapterFactory());
				AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(EditingUtil.getInstance().getAdapterFactory());
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),adapterFactoryLabelProvider,adapterFactoryContentProvider);
				dialog.setAllowMultiple(false);
				dialog.setDoubleClickSelects(true);
				dialog.setValidator(new ISelectionStatusValidator() {
					public IStatus validate(final Object[] selection) {
						if (selection.length == 0) {
							return StatusCreator.newStatus("No parent category selected...");
						}
						return StatusCreator.newStatus(IStatus.OK, "",null);
					}
				});
				dialog.setInput(ApplicationModelPool.getInstance().getModel());
				dialog.setInitialSelection(ChangeSetWizardPage.this.selection);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					Category selectedCategory = (Category) result[0];
					ChangeSetWizardPage.this.parentCategoryText.setText(CategoryUtil.categoryToString(selectedCategory));
				}
			}

		});

		final Label changesetLabel = new Label(container, SWT.NONE);
		final GridData gd_changesetLabel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1);
		changesetLabel.setLayoutData(gd_changesetLabel);
		changesetLabel.setText("Change-Set");

		this.treeViewer = new TreeViewer(container, SWT.BORDER);
		
		this.tree = this.treeViewer.getTree();
		
		this.tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		
		this.treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(AdapterUtils.getAdapterFactory()));
		this.treeViewer.setContentProvider(new AdapterFactoryContentProvider(AdapterUtils.getAdapterFactory()));
		
		this.treeViewer.setInput(this.diffModel);
		
		setControl(container);
	}

	protected void handleCategoryStringChanged(final String newText) {
		
		Category localTargetCategory = CategoryUtil.copyBlankObject(CategoryUtil.findCategory(newText,true));
		Category tmpRemoteRootCategory = CategoryUtil.copyBlankObject(localTargetCategory);
		this.changeSet.setTargetCategory(localTargetCategory);
		
		EList<ChangeSetItem> changeSetItems = this.changeSet.getChangeSetItems();
		for (ChangeSetItem changeSetItem2 : changeSetItems) {
			tmpRemoteRootCategory.getChildren().add((Category) EcoreUtil.copy(changeSetItem2.getRemoteConvertedContainer()));
		}
		EditingUtil.getInstance().saveObjectToResource(
				tmpRemoteRootCategory, 
				UIPlugin.getDefault().getStateLocation().append("compare").append("remote.xml").toOSString());

		EditingUtil.getInstance().saveObjectToResource(
				localTargetCategory, 
				UIPlugin.getDefault().getStateLocation().append("compare").append("local.xml").toOSString());
		MatchModel match;
		try {
			match = MatchService.doMatch(localTargetCategory
					, tmpRemoteRootCategory, Collections.<String, Object> emptyMap());
			this.diffModel = DiffService.doDiff(match, false);
			this.treeViewer.setInput(this.diffModel);
			//performDiff(ownedElements, changeSetItem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Computing differences
		
	}

	protected void validatePage() {
		// TODO Auto-generated method stub
		
	}

	public void setChangeSet(final ChangeSet changeSet) {
		this.changeSet = changeSet;
	}

}
