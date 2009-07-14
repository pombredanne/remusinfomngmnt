/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
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
 * A composite consisting out of a decorated text control and a browse button
 * which opens a dialog with a tree of selectable categories.
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CategoryBrowser extends Composite {

	private final Text text;
	private final Button browseButton;
	private Category category;
	private String categoryString;

	public CategoryBrowser(final Composite parent, final int style, final Category category) {
		this(parent, style);
		this.category = category;
	}

	public CategoryBrowser(final Composite parent, final int style) {
		super(parent, SWT.NONE);
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		setLayout(gridLayout);
		this.text = new Text(this, style);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		new CategorySmartField(this.text);

		this.browseButton = new Button(this, SWT.NONE);
		this.browseButton.setText("Browse...");
		this.browseButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		this.browseButton.addListener(SWT.Selection, new Listener() {
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
					public IStatus validate(final Object[] pselection) {
						if (pselection.length == 0) {
							return StatusCreator.newStatus("No parent category selected...");
						}
						return StatusCreator.newStatus(IStatus.OK, "", null);
					}
				});
				dialog.setInput(ApplicationModelPool.getInstance().getModel());
				dialog.setInitialSelection(CategoryBrowser.this.category);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					CategoryBrowser.this.category = (Category) result[0];
					CategoryBrowser.this.text.setText(CategoryUtil
							.categoryToString(CategoryBrowser.this.category));
				}
			}
		});
	}

	public IStatus isValid() {
		if (this.text != null) {
			this.categoryString = this.text.getText();
			IStatus categoryPathStringValid = CategoryUtil.isCategoryPathStringValid(this.text
					.getText());
			return categoryPathStringValid;
		}
		// if (this.text != null) {
		// IStatus categoryNameValid =
		// CategoryUtil.isCategoryNameValid(this.text.getText());
		// return categoryNameValid;
		// }
		// This should not happen.
		return null;

	}

	protected void presetValues() {
		if (this.category != null) {
			this.text.setText(CategoryUtil.categoryToString(this.category));
		}
	}

	/**
	 * @return the text
	 */
	public Text getText() {
		return this.text;
	}

}
