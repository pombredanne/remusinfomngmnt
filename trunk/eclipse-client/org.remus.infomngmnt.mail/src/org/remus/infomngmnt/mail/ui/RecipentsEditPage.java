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

package org.remus.infomngmnt.mail.ui;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.contact.shared.MailPersonDialog;
import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.mail.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RecipentsEditPage extends AbstractInformationFormPage {

	private TableViewer recipientsTableViewer;
	private TableViewer ccTableViewer;
	private IEditingHandler editingHandler;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.extension.AbstractInformationFormPage#renderPage
	 * (org.eclipse.ui.forms.IManagedForm)
	 */
	@Override
	protected void renderPage(final IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		Composite body = form.getBody();
		body.setLayout(new GridLayout());
		toolkit.paintBordersFor(body);
		this.editingHandler = MailActivator.getDefault().getServiceTracker().getService(
				IEditingHandler.class);
		doCreateNamesSection(body, toolkit);
		doCreateCCSection(body, toolkit);

	}

	private void doCreateNamesSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(Messages.RecipentsEditPage_Recipients);

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.BORDER);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 100).grab(true, false).span(1, 3).applyTo(
				createTable);
		this.recipientsTableViewer = new TableViewer(createTable);
		Button addbutton = toolkit.createButton(client, Messages.RecipentsEditPage_Add, SWT.FLAT);
		addbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				MailPersonDialog mailPersonDialog = new MailPersonDialog(getSite().getShell(), null);
				if (mailPersonDialog.open() == IDialogConstants.OK_ID) {
					InformationStructureEdit edit = InformationStructureEdit
							.newSession(MailActivator.INFO_TYPE_ID);
					InformationUnit createSubType = edit.createSubType(
							MailActivator.NODE_NAME_RECIPIENT, mailPersonDialog.getContent());
					edit.addDynamicNode(getModelObject(), createSubType, getEditingDomain());
				}
			}
		});
		final Button editButton = toolkit.createButton(client, Messages.RecipentsEditPage_Edit, SWT.FLAT);
		editButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationUnit firstElement = (InformationUnit) ((IStructuredSelection) RecipentsEditPage.this.recipientsTableViewer
						.getSelection()).getFirstElement();
				InformationStructureRead read = InformationStructureRead.newSession(firstElement,
						MailActivator.INFO_TYPE_ID);
				MailPersonDialog mailPersonDialog = new MailPersonDialog(getSite().getShell(),
						(String) read.getValueByNodeId(MailActivator.NODE_NAME_RECIPIENT));
				if (mailPersonDialog.open() == IDialogConstants.OK_ID) {
					InformationStructureEdit edit = InformationStructureEdit.newSession(
							MailActivator.INFO_TYPE_ID, getEditingDomain());
					edit.setValue(firstElement, MailActivator.NODE_NAME_RECIPIENT, mailPersonDialog
							.getContent());
				}
			}
		});
		final Button deleteButton = toolkit.createButton(client, Messages.RecipentsEditPage_Delete, SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Command deleteCmd = DeleteCommand.create(getEditingDomain(),
						((IStructuredSelection) RecipentsEditPage.this.recipientsTableViewer
								.getSelection()).toList());
				getEditingDomain().getCommandStack().execute(deleteCmd);
			}
		});
		this.recipientsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				editButton.setEnabled(!event.getSelection().isEmpty());
				deleteButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
		this.recipientsTableViewer.setContentProvider(new AdapterFactoryContentProvider(
				this.editingHandler.getAdapterFactory()));
		this.recipientsTableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				InformationStructureRead read = InformationStructureRead.newSession(
						(InformationUnit) element, MailActivator.INFO_TYPE_ID);
				return (String) read.getValueByNodeId(MailActivator.NODE_NAME_RECIPIENT);
			}
		});
		this.recipientsTableViewer.setSelection(StructuredSelection.EMPTY);
		GridDataFactory.fillDefaults().hint(80, SWT.DEFAULT).align(SWT.FILL, SWT.TOP).applyTo(
				editButton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(addbutton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(deleteButton);

	}

	private void doCreateCCSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(Messages.RecipentsEditPage_CCs);

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.BORDER);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 100).grab(true, false).span(1, 3).applyTo(
				createTable);
		this.ccTableViewer = new TableViewer(createTable);
		Button addbutton = toolkit.createButton(client, Messages.RecipentsEditPage_Add, SWT.FLAT);
		addbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				MailPersonDialog mailPersonDialog = new MailPersonDialog(getSite().getShell(), null);
				if (mailPersonDialog.open() == IDialogConstants.OK_ID) {
					InformationStructureEdit edit = InformationStructureEdit
							.newSession(MailActivator.INFO_TYPE_ID);
					InformationUnit createSubType = edit.createSubType(MailActivator.NODE_NAME_CC,
							mailPersonDialog.getContent());
					edit.addDynamicNode(getModelObject(), createSubType, getEditingDomain());
				}
			}
		});
		final Button editButton = toolkit.createButton(client, Messages.RecipentsEditPage_Edit, SWT.FLAT);
		editButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationUnit firstElement = (InformationUnit) ((IStructuredSelection) RecipentsEditPage.this.ccTableViewer
						.getSelection()).getFirstElement();
				InformationStructureRead read = InformationStructureRead.newSession(firstElement,
						MailActivator.INFO_TYPE_ID);
				MailPersonDialog mailPersonDialog = new MailPersonDialog(getSite().getShell(),
						(String) read.getValueByNodeId(MailActivator.NODE_NAME_CC));
				if (mailPersonDialog.open() == IDialogConstants.OK_ID) {
					InformationStructureEdit edit = InformationStructureEdit.newSession(
							MailActivator.INFO_TYPE_ID, getEditingDomain());
					edit.setValue(firstElement, MailActivator.NODE_NAME_CC, mailPersonDialog
							.getContent());
				}
			}
		});
		final Button deleteButton = toolkit.createButton(client, Messages.RecipentsEditPage_Delete, SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Command deleteCmd = DeleteCommand
						.create(getEditingDomain(),
								((IStructuredSelection) RecipentsEditPage.this.ccTableViewer
										.getSelection()).toList());
				getEditingDomain().getCommandStack().execute(deleteCmd);
			}
		});
		this.ccTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				editButton.setEnabled(!event.getSelection().isEmpty());
				deleteButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
		this.ccTableViewer.setContentProvider(new AdapterFactoryContentProvider(this.editingHandler
				.getAdapterFactory()));
		this.ccTableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				InformationStructureRead read = InformationStructureRead.newSession(
						(InformationUnit) element, MailActivator.INFO_TYPE_ID);
				return (String) read.getValueByNodeId(MailActivator.NODE_NAME_CC);
			}
		});
		this.ccTableViewer.setSelection(StructuredSelection.EMPTY);
		GridDataFactory.fillDefaults().hint(80, SWT.DEFAULT).align(SWT.FILL, SWT.TOP).applyTo(
				editButton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(addbutton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(deleteButton);

	}

	@Override
	public void dispose() {
		MailActivator.getDefault().getServiceTracker().ungetService(this.editingHandler);
		super.dispose();
	}

	@Override
	public void bindValuesToUi() {
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
		InformationUnit recipients = read.getChildByNodeId(MailActivator.NODE_NAME_RECIPIENTS);
		InformationUnit ccs = read.getChildByNodeId(MailActivator.NODE_NAME_CCS);
		this.recipientsTableViewer.setInput(recipients);
		this.ccTableViewer.setInput(ccs);
		super.bindValuesToUi();
	}
}
