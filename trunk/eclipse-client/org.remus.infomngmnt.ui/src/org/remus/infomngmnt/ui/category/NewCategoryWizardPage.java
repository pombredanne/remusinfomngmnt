/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.category;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * The new category wizard provides the ability for the user to create new
 * category under a given category.
 * 
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class NewCategoryWizardPage extends WizardPage {

	private Text newCategoriyName;
	private Text parentCategoryText;
	private final Category selection;
	private String newCategoryValue;
	private String parentCategoryValue;

	/**
	 * Create the wizard
	 * 
	 * @param selection
	 */
	public NewCategoryWizardPage(final Category selection) {
		super("wizardPage");
		this.selection = selection;
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
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
						EditingUtil.getInstance().getAdapterFactory());
				AdapterFactoryLabelProvider adapterFactoryLabelProvider = new AdapterFactoryLabelProvider(
						EditingUtil.getInstance().getAdapterFactory());
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
						adapterFactoryLabelProvider, adapterFactoryContentProvider);
				dialog.setAllowMultiple(false);
				dialog.setDoubleClickSelects(true);
				dialog.setTitle("Select a category");
				dialog.setMessage("Select a cateogry");
				dialog.addFilter(new ViewerFilter() {
					@Override
					public boolean select(final Viewer viewer, final Object parentElement,
							final Object element) {
						return element instanceof Category;
					}
				});
				dialog.setValidator(new ISelectionStatusValidator() {
					public IStatus validate(final Object[] selection) {
						if (selection.length == 0) {
							return StatusCreator.newStatus("No parent category selected...");
						}
						return StatusCreator.newStatus(IStatus.OK, "", null);
					}
				});
				dialog.setInput(ApplicationModelPool.getInstance().getModel());
				dialog.setInitialSelection(NewCategoryWizardPage.this.selection);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					Category selectedCategory = (Category) result[0];
					NewCategoryWizardPage.this.parentCategoryText.setText(CategoryUtil
							.categoryToString(selectedCategory));
				}
			}

		});

		final Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setText("Name");

		this.newCategoriyName = new Text(container, SWT.BORDER);
		this.newCategoriyName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		this.newCategoriyName.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				validatePage();
			}
		});
		setPageComplete(false);
		setControl(container);

	}

	/**
	 * Validates the wizard-page. The page is complete if the Parent-Category
	 * path is a valid path to categories
	 * 
	 * @see CategoryUtil#categoryToString(Category)
	 */
	protected void validatePage() {
		if (CategoryUtil.isCategoryPathStringValid(this.parentCategoryText.getText()).getSeverity() == IStatus.OK
				&& CategoryUtil.isCategoryNameValid(this.newCategoriyName.getText()).getSeverity() == IStatus.OK
				&& CategoryUtil.isCategoryStructureValid(this.parentCategoryText.getText(),
						this.newCategoriyName.getText()).getSeverity() == IStatus.OK) {
			setPageComplete(true);
			this.newCategoryValue = this.newCategoriyName.getText();
			this.parentCategoryValue = this.parentCategoryText.getText();
		} else {
			setPageComplete(false);
		}

	}

	public String getNewCategoryValue() {
		return this.newCategoryValue;
	}

	public String getParentCategoryValue() {
		return this.parentCategoryValue;
	}

}
