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

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.remus.BinaryReference;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.core.commands.DeleteBinaryReferenceCommand;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.core.model.InformationStructureRead;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.ui.editors.editpage.AbstractInformationFormPage;
import org.eclipse.remus.ui.operation.LoadFileToTmpFromPathRunnable;
import org.eclipse.remus.ui.progress.CancelableRunnable;
import org.eclipse.remus.util.InformationUtil;
import org.eclipse.remus.util.StatusCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import org.remus.infomngmnt.mail.MailActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AttachmentsEditPage extends AbstractInformationFormPage {

	private TableViewer attachmentsTableViewer;
	private TableViewer embeddedTableViewer;
	private final LabelProvider embeddedLabelProvider = new LabelProvider() {
		@Override
		public String getText(final Object element) {
			InformationUnit element2 = (InformationUnit) element;
			InformationStructureRead read = InformationStructureRead.newSession(element2,
					MailActivator.INFO_TYPE_ID);
			List<BinaryReference> binaryReferences = read.getBinaryReferences(element2.getType(),
					false);
			if (binaryReferences.size() == 0 || binaryReferences.get(0) == null) {
				return "No file";
			}
			return binaryReferences.get(0).getProjectRelativePath();
		}

		@Override
		public Image getImage(final Object element) {
			InformationUnit element2 = (InformationUnit) element;
			InformationStructureRead read = InformationStructureRead.newSession(element2,
					MailActivator.INFO_TYPE_ID);
			List<BinaryReference> binaryReferences = read.getBinaryReferences(element2.getType(),
					false);
			if (binaryReferences.size() > 0 && binaryReferences.get(0) != null) {
				String projectRelativePath = binaryReferences.get(0).getProjectRelativePath();
				String fileExtension = new Path(projectRelativePath).getFileExtension();
				if (fileExtension == null) {
					fileExtension = "";
				}
				Program findProgram = Program.findProgram(fileExtension);
				if (findProgram != null) {
					ImageData imageData = findProgram.getImageData();
					return ImageDescriptor.createFromImageData(imageData).createImage();
				}
			}
			return super.getImage(element);
		}
	};
	private final LabelProvider attachmentLabelProvider = new LabelProvider() {
		@Override
		public String getText(final Object element) {
			InformationStructureRead read = InformationStructureRead.newSession(
					(InformationUnit) element, MailActivator.INFO_TYPE_ID);
			return (String) read.getValueByNodeId(MailActivator.NODE_NAME_ATTACHMENT_LABEL);
		}

		@Override
		public Image getImage(final Object element) {
			InformationUnit element2 = (InformationUnit) element;
			InformationStructureRead read = InformationStructureRead.newSession(element2,
					MailActivator.INFO_TYPE_ID);
			List<BinaryReference> binaryReferences = read.getBinaryReferences(element2.getType(),
					false);
			if (binaryReferences.size() > 0 && binaryReferences.get(0) != null) {
				String projectRelativePath = binaryReferences.get(0).getProjectRelativePath();
				String fileExtension = new Path(projectRelativePath).getFileExtension();
				if (fileExtension == null) {
					fileExtension = "";
				}
				Program findProgram = Program.findProgram(fileExtension);
				if (findProgram != null) {
					ImageData imageData = findProgram.getImageData();
					return ImageDescriptor.createFromImageData(imageData).createImage();
				}
			}
			return super.getImage(element);
		}
	};
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
		generalSection.setText("Attachments");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.MULTI);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 100).grab(true, false).span(1, 3).applyTo(
				createTable);
		this.attachmentsTableViewer = new TableViewer(createTable);
		Button addbutton = toolkit.createButton(client, "Add", SWT.FLAT);
		addbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.OPEN);
				final String open = fileDialog.open();
				if (open != null) {
					ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
					try {
						pmd.run(true, true, new CancelableRunnable() {
							@Override
							protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
								LoadFileToTmpFromPathRunnable loadFile = new LoadFileToTmpFromPathRunnable();
								loadFile.setFilePath(open);
								try {
									loadFile.run(monitor);
								} catch (InvocationTargetException e) {
									return StatusCreator.newStatus("Error uploading file", e);
								} catch (InterruptedException e) {
									return Status.CANCEL_STATUS;
								}
								IFile tmpFile = loadFile.getTmpFile();
								InformationStructureEdit edit = InformationStructureEdit
										.newSession(MailActivator.INFO_TYPE_ID, getEditingDomain());
								InformationUnit createSubType = edit.createSubType(
										MailActivator.NODE_NAME_ATTACHMENT, null);
								edit.setValue(createSubType,
										MailActivator.NODE_NAME_ATTACHMENT_LABEL, new Path(open)
												.lastSegment());
								edit.addDynamicNode(getModelObject(), createSubType,
										getEditingDomain(), Collections.singletonMap(
												MailActivator.NODE_NAME_ATTACHMENT, tmpFile));
								return Status.OK_STATUS;
							}
						});
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		final Button deleteButton = toolkit.createButton(client, "Delete", SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				CompoundCommand cc = new CompoundCommand();
				List list = ((IStructuredSelection) AttachmentsEditPage.this.attachmentsTableViewer
						.getSelection()).toList();
				for (Object object : list) {
					InformationStructureRead read = InformationStructureRead.newSession(
							(InformationUnit) object, MailActivator.INFO_TYPE_ID);
					List<BinaryReference> binaryReferences = read.getBinaryReferences();
					for (BinaryReference binaryReference : binaryReferences) {
						cc.append(new DeleteBinaryReferenceCommand(binaryReference,
								getEditingDomain()));
					}
				}
				Command deleteCmd = DeleteCommand.create(getEditingDomain(),
						((IStructuredSelection) AttachmentsEditPage.this.attachmentsTableViewer
								.getSelection()).toList());
				cc.append(deleteCmd);
				cc.setLabel("Delete attachment");
				getEditingDomain().getCommandStack().execute(cc);
			}
		});
		this.attachmentsTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				deleteButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
		this.attachmentsTableViewer.addOpenListener(new IOpenListener() {
			public void open(final OpenEvent event) {
				if (MessageDialog.openConfirm(getSite().getShell(), "Confirm open attachments",
						"Don't open attachments from senders you don't trust. Continue?")) {
					List list = ((IStructuredSelection) event.getSelection()).toList();
					for (Object object : list) {
						InformationStructureRead read = InformationStructureRead.newSession(
								(InformationUnit) object, MailActivator.INFO_TYPE_ID);
						InformationUnit childByNodeId = read
								.getChildByNodeId(MailActivator.NODE_NAME_ATTACHMENT);
						IFile firstBinaryReferenceFile = InformationUtil
								.getBinaryReferenceFile(childByNodeId);
						Program.launch(firstBinaryReferenceFile.getLocation().toOSString());
					}
				}
			}
		});
		this.attachmentsTableViewer.setContentProvider(new AdapterFactoryContentProvider(
				this.editingHandler.getAdapterFactory()));
		this.attachmentsTableViewer.setLabelProvider(this.attachmentLabelProvider);
		this.attachmentsTableViewer.setSelection(StructuredSelection.EMPTY);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(addbutton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(deleteButton);

	}

	private void doCreateCCSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText("Embedded");

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.MULTI);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 100).grab(true, false).span(1, 2).applyTo(
				createTable);
		this.embeddedTableViewer = new TableViewer(createTable);
		Button addbutton = toolkit.createButton(client, "Add", SWT.FLAT);
		addbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				FileDialog fileDialog = new FileDialog(getSite().getShell(), SWT.OPEN);
				final String open = fileDialog.open();
				if (open != null) {
					ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
					try {
						pmd.run(true, true, new CancelableRunnable() {
							@Override
							protected IStatus runCancelableRunnable(final IProgressMonitor monitor) {
								LoadFileToTmpFromPathRunnable loadFile = new LoadFileToTmpFromPathRunnable();
								loadFile.setFilePath(open);
								try {
									loadFile.run(monitor);
								} catch (InvocationTargetException e) {
									return StatusCreator.newStatus("Error uploading file", e);
								} catch (InterruptedException e) {
									return Status.CANCEL_STATUS;
								}
								IFile tmpFile = loadFile.getTmpFile();
								InformationStructureEdit edit = InformationStructureEdit
										.newSession(MailActivator.INFO_TYPE_ID, getEditingDomain());
								InformationUnit createSubType = edit.createSubType(
										MailActivator.NODE_NAME_EMBEDDED, null);
								edit.addDynamicNode(getModelObject(), createSubType,
										getEditingDomain(), Collections.singletonMap(
												MailActivator.NODE_NAME_EMBEDDED, tmpFile));
								return Status.OK_STATUS;
							}
						});
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});
		final Button deleteButton = toolkit.createButton(client, "Delete", SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				CompoundCommand cc = new CompoundCommand();
				List list = ((IStructuredSelection) AttachmentsEditPage.this.embeddedTableViewer
						.getSelection()).toList();
				for (Object object : list) {
					InformationStructureRead read = InformationStructureRead.newSession(
							(InformationUnit) object, MailActivator.INFO_TYPE_ID);
					List<BinaryReference> binaryReferences = read.getBinaryReferences(
							((InformationUnit) object).getType(), false);
					for (BinaryReference binaryReference : binaryReferences) {
						cc.append(new DeleteBinaryReferenceCommand(binaryReference,
								getEditingDomain()));
					}
				}
				Command deleteCmd = DeleteCommand.create(getEditingDomain(),
						((IStructuredSelection) AttachmentsEditPage.this.embeddedTableViewer
								.getSelection()).toList());
				cc.append(deleteCmd);
				cc.setLabel("Delete embedded data");
				getEditingDomain().getCommandStack().execute(cc);
			}
		});
		this.embeddedTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				deleteButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
		this.embeddedTableViewer.setContentProvider(new AdapterFactoryContentProvider(
				this.editingHandler.getAdapterFactory()));

		this.embeddedTableViewer.setLabelProvider(this.embeddedLabelProvider);
		this.embeddedTableViewer.setSelection(StructuredSelection.EMPTY);
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
		InformationUnit attachments = read.getChildByNodeId(MailActivator.NODE_NAME_ATTACHMENTS);
		InformationUnit embedded = read.getChildByNodeId(MailActivator.NODE_NAME_EMBEDDEDS);
		this.attachmentsTableViewer.setInput(attachments);
		this.embeddedTableViewer.setInput(embedded);
		super.bindValuesToUi();
	}

}
