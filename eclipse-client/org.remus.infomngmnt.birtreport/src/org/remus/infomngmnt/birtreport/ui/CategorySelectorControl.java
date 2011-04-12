package org.remus.infomngmnt.birtreport.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.remus.Category;
import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.category.CategorySmartField;
import org.eclipse.remus.ui.viewer.provider.InformationUnitLabelProvider;
import org.eclipse.remus.util.CategoryUtil;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.birtreport.messages.Messages;
import org.remus.infomngmnt.birtreport.parameter.AbstractParameterControl;

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

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CategorySelectorControl extends AbstractParameterControl {

	private Text parentCategoryText;
	private String categoryString;
	private Button browserButton;
	protected Category category;

	/**
	 * 
	 */
	public CategorySelectorControl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.birtreport.parameter.AbstractParameterControl#
	 * createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {
		this.parentCategoryText = new Text(parent, SWT.BORDER);
		new CategorySmartField(this.parentCategoryText);
		final GridData gd_parentCategoryText = new GridData(SWT.FILL, SWT.CENTER, true, false);
		this.parentCategoryText.setLayoutData(gd_parentCategoryText);
		if (this.categoryString != null) {
			this.parentCategoryText.setText(this.categoryString);
		}

		this.browserButton = new Button(parent, SWT.NONE);
		this.browserButton.setText(Messages.CategorySelectorControl_Browse);
		this.browserButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				IEditingHandler editHandler = ReportActivator.getDefault().getServiceTracker()
						.getService(IEditingHandler.class);
				IApplicationModel appService = ReportActivator.getDefault().getServiceTracker()
						.getService(IApplicationModel.class);
				AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(
						editHandler.getAdapterFactory());
				InformationUnitLabelProvider labelProvider = new InformationUnitLabelProvider(
						editHandler.getAdapterFactory());
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(parent
						.getShell(), labelProvider, adapterFactoryContentProvider);
				dialog.setAllowMultiple(false);
				dialog.setTitle(Messages.CategorySelectorControl_SelectCategory);
				dialog.setMessage(Messages.CategorySelectorControl_SelectCategory);
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
							return StatusCreator.newStatus(Messages.CategorySelectorControl_NoParentSelected);
						}
						return StatusCreator.newStatus(IStatus.OK, "", null); //$NON-NLS-1$
					}
				});
				dialog.setInput(appService.getModel());
				dialog.setInitialSelection(CategorySelectorControl.this.category);
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					CategorySelectorControl.this.category = (Category) result[0];
					CategorySelectorControl.this.parentCategoryText.setText(CategoryUtil
							.categoryToString(CategorySelectorControl.this.category));
				}
				ReportActivator.getDefault().getServiceTracker().ungetService(editHandler);
				ReportActivator.getDefault().getServiceTracker().ungetService(appService);
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.birtreport.parameter.AbstractParameterControl#
	 * getParameterValue()
	 */
	@Override
	public String getParameterValue() {
		return CategoryUtil.categoryToString(this.category);
	}

}
