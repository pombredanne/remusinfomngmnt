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

package org.remus.infomngmnt.oda.ui.impl;

import java.util.Properties;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.value.DecoratingObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSourceWizardPage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.databinding.swt.ISWTObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.IViewerObservableValue;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.DynamicStructure;
import org.remus.infomngmnt.InformationStructureDefinition;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.oda.core.Constants;
import org.remus.infomngmnt.ui.category.CategorySmartField;
import org.remus.infomngmnt.ui.structuredefinition.StructureDefinitionLabelProvider;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemusDataSourceWizard extends DataSourceWizardPage {

	private Text text;
	private Text text_1;

	private final WritableValue rootElement = new WritableValue("", String.class);
	private final WritableValue infoType = new WritableValue("", IInfoType.class);;
	private final WritableValue baseSelection = new WritableValue("", String.class);
	private Combo combo;
	private ComboViewer comboViewer;;

	/**
	 * @param pageName
	 * @wbp.parser.constructor
	 */
	public RemusDataSourceWizard(final String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public RemusDataSourceWizard(final String pageName, final String title,
			final ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSourceWizardPage
	 * #collectCustomProperties()
	 */
	@Override
	public Properties collectCustomProperties() {
		Properties returnValue = new Properties();
		returnValue.setProperty(Constants.PROPERY_ROOT_ELEMENT, (String) this.rootElement
				.getValue());
		returnValue.setProperty(Constants.PROPERTY_INFO_TYPE,
				((IInfoType) this.infoType.getValue()).getType());
		returnValue.setProperty(Constants.PROPERTY_BASE_SELECTION, (String) this.baseSelection
				.getValue());
		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSourceWizardPage
	 * #createPageCustomControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPageCustomControl(final Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout(3, false));

		Label lblSource = new Label(group, SWT.NONE);
		lblSource.setText("Source");

		this.text = new Text(group, SWT.BORDER);
		this.text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button btnBrowse = new Button(group, SWT.NONE);
		btnBrowse.setText("Browse...");
		btnBrowse.addListener(SWT.Selection, new Listener() {
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
						return element instanceof Category;
					}
				});
				dialog.setInput(ApplicationModelPool.getInstance().getModel());
				dialog.setInitialSelection(CategoryUtil.findCategory(
						RemusDataSourceWizard.this.text.getText(), false));
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					Category selectedCategory = (Category) result[0];
					RemusDataSourceWizard.this.text.setText(CategoryUtil
							.categoryToString(selectedCategory));
				}
			}

		});

		new CategorySmartField(this.text);

		Label lblInformationtype = new Label(group, SWT.NONE);
		lblInformationtype.setText("Information-Type");

		this.combo = new Combo(group, SWT.READ_ONLY);
		this.combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		this.comboViewer = new ComboViewer(this.combo);
		this.comboViewer.setContentProvider(ArrayContentProvider.getInstance());
		this.comboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((IInfoType) element).getName();
			}
		});
		this.comboViewer.setInput(InformationExtensionManager.getInstance().getTypes());

		Label lblEntrypoint = new Label(group, SWT.NONE);
		lblEntrypoint.setText("Entry-Point");

		this.text_1 = new Text(group, SWT.BORDER);
		this.text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button button = new Button(group, SWT.NONE);
		button.setText("Browse...");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				AdapterFactoryContentProvider adapterFactoryContentProvider = new AdapterFactoryContentProvider(
						EditingUtil.getInstance().getAdapterFactory());
				ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(),
						new StructureDefinitionLabelProvider(), adapterFactoryContentProvider);
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
						return element instanceof DynamicStructure;
					}
				});
				InformationStructureDefinition structureDefinition = ((IInfoType) ((IStructuredSelection) RemusDataSourceWizard.this.comboViewer
						.getSelection()).getFirstElement()).getStructureDefinition();
				dialog.setInput(structureDefinition);
				dialog.setInitialSelection(structureDefinition.eResource().getEObject(
						RemusDataSourceWizard.this.text_1.getText()));
				if (dialog.open() == IDialogConstants.OK_ID) {
					Object[] result = dialog.getResult();
					URI uri = EcoreUtil.getURI((EObject) result[0]);
					RemusDataSourceWizard.this.text_1.setText(uri.fragment().toString());
				}
			}

		});

		bindValuesToUi();

	}

	private void bindValuesToUi() {
		DataBindingContext ctx = new DataBindingContext();
		ISWTObservableValue observeText = SWTObservables.observeText(this.text, SWT.Modify);
		ctx.bindValue(observeText, this.rootElement);
		IViewerObservableValue observeSelection = ViewersObservables
				.observeSingleSelection(this.comboViewer);
		ctx.bindValue(observeSelection, this.infoType);
		observeSelection.addChangeListener(new IChangeListener() {
			public void handleChange(final ChangeEvent event) {
				DecoratingObservableValue observable = (DecoratingObservableValue) event
						.getObservable();
				refreshBaseSelection((IInfoType) observable.getValue());

			}
		});
		ISWTObservableValue observeText2 = SWTObservables.observeText(this.text_1, SWT.Modify);
		ctx.bindValue(observeText2, this.baseSelection);

	}

	protected void refreshBaseSelection(final IInfoType infoType) {
		this.text_1.setText("");
	}

	@Override
	protected Runnable createTestConnectionRunnable(final IConnectionProfile profile) {
		// TODO Auto-generated method stub
		return super.createTestConnectionRunnable(profile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.datatools.connectivity.oda.design.ui.wizards.DataSourceWizardPage
	 * #setInitialProperties(java.util.Properties)
	 */
	@Override
	public void setInitialProperties(final Properties properties) {
		if (properties == null || properties.isEmpty()) {
			return;
		}
		String property = properties.getProperty(Constants.PROPERY_ROOT_ELEMENT);
		this.rootElement.setValue(property);
		property = properties.getProperty(Constants.PROPERTY_INFO_TYPE);
		this.infoType.setValue(InformationExtensionManager.getInstance()
				.getInfoTypeByType(property));
		property = properties.getProperty(Constants.PROPERTY_BASE_SELECTION);
		this.baseSelection.setValue(property);

	}

}
