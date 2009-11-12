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

package org.remus.infomngmnt.birtreport.ui;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.core.model.InformationStructureEdit;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ReportParameterPage extends AbstractInformationFormPage {

	private TableViewer parameterTableViewer;

	/**
	 * 
	 */
	public ReportParameterPage() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);

		doCreateAttendeesSection(body, toolkit);
		doCreateSemanticSection(body, toolkit);
		form.reflow(true);
	}

	private void doCreateAttendeesSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("Report-Parameters");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.BORDER
				| SWT.FULL_SELECTION);
		createTable.setHeaderVisible(true);
		createTable.setLinesVisible(true);

		TableColumn tc0 = new TableColumn(createTable, SWT.NONE);
		tc0.setWidth(200);
		tc0.setResizable(true);
		tc0.setMoveable(true);
		tc0.setText("Parameter-Name");

		TableColumn tc1 = new TableColumn(createTable, SWT.NONE);
		tc1.setWidth(200);
		tc1.setResizable(true);
		tc1.setMoveable(true);
		tc1.setText("Parameter-Value");

		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 100).grab(true, false).span(1, 3).applyTo(
				createTable);
		this.parameterTableViewer = new TableViewer(createTable);
		Button addbutton = toolkit.createButton(client, "Add", SWT.FLAT);
		addbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				ReportParameterDialog dialog = new ReportParameterDialog(getSite().getShell(),
						null, null);
				if (dialog.open() == IDialogConstants.OK_ID) {
					InformationStructureEdit edit = InformationStructureEdit
							.newSession(ReportActivator.INFOTYPE_ID);
					InformationUnit params = edit.createSubType(
							ReportActivator.NODE_NAME_PARAM_NAME, dialog.getName());
					edit.setValue(params, ReportActivator.NODE_NAME_PARAM_VALUE, dialog.getValue());
					edit.addDynamicNode(getModelObject(), params, getEditingDomain());
				}
			}
		});
		final Button editButton = toolkit.createButton(client, "Edit", SWT.FLAT);
		editButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationUnit firstElement = (InformationUnit) ((IStructuredSelection) ReportParameterPage.this.parameterTableViewer
						.getSelection()).getFirstElement();
				InformationStructureRead read = InformationStructureRead.newSession(firstElement,
						ReportActivator.INFOTYPE_ID);
				ReportParameterDialog mailPersonDialog = new ReportParameterDialog(getSite()
						.getShell(), (String) read
						.getValueByNodeId(ReportActivator.NODE_NAME_PARAM_NAME), (String) read
						.getValueByNodeId(ReportActivator.NODE_NAME_PARAM_VALUE));
				if (mailPersonDialog.open() == IDialogConstants.OK_ID) {
					InformationStructureEdit edit = InformationStructureEdit.newSession(
							ReportActivator.INFOTYPE_ID, getEditingDomain());
					edit.setValue(firstElement, ReportActivator.NODE_NAME_PARAM_NAME,
							mailPersonDialog.getName());
					edit.setValue(firstElement, ReportActivator.NODE_NAME_PARAM_VALUE,
							mailPersonDialog.getValue());
				}
			}
		});
		final Button deleteButton = toolkit.createButton(client, "Delete", SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Command deleteCmd = DeleteCommand.create(getEditingDomain(),
						((IStructuredSelection) ReportParameterPage.this.parameterTableViewer
								.getSelection()).toList());
				getEditingDomain().getCommandStack().execute(deleteCmd);
			}
		});
		this.parameterTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				editButton.setEnabled(!event.getSelection().isEmpty());
				deleteButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
		this.parameterTableViewer.setContentProvider(new AdapterFactoryContentProvider(EditingUtil
				.getInstance().getAdapterFactory()));
		this.parameterTableViewer.setLabelProvider(new ReportTableLabelProvider());
		this.parameterTableViewer.setSelection(StructuredSelection.EMPTY);
		GridDataFactory.fillDefaults().hint(80, SWT.DEFAULT).align(SWT.FILL, SWT.TOP).applyTo(
				editButton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(addbutton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(deleteButton);

	}

	@Override
	public void bindValuesToUi() {
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());

		InformationUnit parameters = read.getChildByNodeId(ReportActivator.NODE_NAME_PARAMS);
		this.parameterTableViewer.setInput(parameters);

		super.bindValuesToUi();
	}

}
