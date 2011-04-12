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

package org.remus.infomngmnt.meetingminutes.ui;

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
import org.eclipse.jface.dialogs.IDialogConstants;
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

import org.remus.infomngmnt.contact.shared.MailPersonDialog;
import org.remus.infomngmnt.meetingminutes.MeetingMinutesActivator;
import org.remus.infomngmnt.meetingminutes.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AttachmentsEditPage extends AbstractInformationFormPage {

	private TableViewer attendeesTableViewer;
	private TableViewer attachmentsTableViewer;

	private final LabelProvider attachmentLabelProvider = new LabelProvider() {
		@Override
		public String getText(final Object element) {
			InformationStructureRead read = InformationStructureRead.newSession(
					(InformationUnit) element, MeetingMinutesActivator.INFO_TYPE_ID);
			return (String) read
					.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_ATTACHMENT_LABEL);
		}

		@Override
		public Image getImage(final Object element) {
			InformationUnit element2 = (InformationUnit) element;
			InformationStructureRead read = InformationStructureRead.newSession(element2,
					MeetingMinutesActivator.INFO_TYPE_ID);
			List<BinaryReference> binaryReferences = read.getBinaryReferences(element2.getType(),
					false);
			if (binaryReferences.size() > 0 && binaryReferences.get(0) != null) {
				String projectRelativePath = binaryReferences.get(0).getProjectRelativePath();
				String fileExtension = new Path(projectRelativePath).getFileExtension();
				if (fileExtension == null) {
					fileExtension = ""; //$NON-NLS-1$
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

	/**
	 * 
	 */
	public AttachmentsEditPage() {
		// TODO Auto-generated constructor stub
	}

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
		this.editingHandler = MeetingMinutesActivator.getDefault().getServiceTracker().getService(
				IEditingHandler.class);
		doCreateAttendeesSection(body, toolkit);
		doCreateAttachementsSection(body, toolkit);

	}

	private void doCreateAttendeesSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(Messages.AttachmentsEditPage_Attendees);

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.BORDER);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 100).grab(true, false).span(1, 3).applyTo(
				createTable);
		this.attendeesTableViewer = new TableViewer(createTable);
		Button addbutton = toolkit.createButton(client, Messages.AttachmentsEditPage_Add, SWT.FLAT);
		addbutton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				MailPersonDialog mailPersonDialog = new MailPersonDialog(getSite().getShell(), null);
				if (mailPersonDialog.open() == IDialogConstants.OK_ID) {
					InformationStructureEdit edit = InformationStructureEdit
							.newSession(MeetingMinutesActivator.INFO_TYPE_ID);
					InformationUnit createSubType = edit.createSubType(
							MeetingMinutesActivator.NODE_NAME_ATTENDEE, mailPersonDialog
									.getContent());
					edit.addDynamicNode(getModelObject(), createSubType, getEditingDomain());
				}
			}
		});
		final Button editButton = toolkit.createButton(client, Messages.AttachmentsEditPage_Edit, SWT.FLAT);
		editButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InformationUnit firstElement = (InformationUnit) ((IStructuredSelection) AttachmentsEditPage.this.attendeesTableViewer
						.getSelection()).getFirstElement();
				InformationStructureRead read = InformationStructureRead.newSession(firstElement,
						MeetingMinutesActivator.INFO_TYPE_ID);
				MailPersonDialog mailPersonDialog = new MailPersonDialog(getSite().getShell(),
						(String) read.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_ATTENDEE));
				if (mailPersonDialog.open() == IDialogConstants.OK_ID) {
					InformationStructureEdit edit = InformationStructureEdit.newSession(
							MeetingMinutesActivator.INFO_TYPE_ID, getEditingDomain());
					edit.setValue(firstElement, MeetingMinutesActivator.NODE_NAME_ATTENDEE,
							mailPersonDialog.getContent());
				}
			}
		});
		final Button deleteButton = toolkit.createButton(client, Messages.AttachmentsEditPage_Delete, SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Command deleteCmd = DeleteCommand.create(getEditingDomain(),
						((IStructuredSelection) AttachmentsEditPage.this.attendeesTableViewer
								.getSelection()).toList());
				getEditingDomain().getCommandStack().execute(deleteCmd);
			}
		});
		this.attendeesTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				editButton.setEnabled(!event.getSelection().isEmpty());
				deleteButton.setEnabled(!event.getSelection().isEmpty());
			}
		});
		this.attendeesTableViewer.setContentProvider(new AdapterFactoryContentProvider(
				this.editingHandler.getAdapterFactory()));
		this.attendeesTableViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				InformationStructureRead read = InformationStructureRead.newSession(
						(InformationUnit) element, MeetingMinutesActivator.INFO_TYPE_ID);
				return (String) read.getValueByNodeId(MeetingMinutesActivator.NODE_NAME_ATTENDEE);
			}
		});
		this.attendeesTableViewer.setSelection(StructuredSelection.EMPTY);
		GridDataFactory.fillDefaults().hint(80, SWT.DEFAULT).align(SWT.FILL, SWT.TOP).applyTo(
				editButton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(addbutton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(deleteButton);

	}

	private void doCreateAttachementsSection(final Composite body, final FormToolkit toolkit) {
		final Section generalSection = toolkit.createSection(body, ExpandableComposite.TITLE_BAR
				| ExpandableComposite.EXPANDED);
		final GridData gd_generalSection = new GridData(SWT.FILL, SWT.FILL, true, false);
		generalSection.setLayoutData(gd_generalSection);
		generalSection.setText(Messages.AttachmentsEditPage_Attachments);

		final Composite client = toolkit.createComposite(generalSection, SWT.NONE);
		client.setLayout(new GridLayout(2, false));
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

		client.setLayoutData(gridData);

		generalSection.setClient(client);

		Table createTable = toolkit.createTable(client, SWT.V_SCROLL | SWT.MULTI | SWT.BORDER);
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 100).grab(true, false).span(1, 3).applyTo(
				createTable);
		this.attachmentsTableViewer = new TableViewer(createTable);
		Button addbutton = toolkit.createButton(client, Messages.AttachmentsEditPage_Add, SWT.FLAT);
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
									return StatusCreator.newStatus(Messages.AttachmentsEditPage_ErrorUploadingfile, e);
								} catch (InterruptedException e) {
									return Status.CANCEL_STATUS;
								}
								IFile tmpFile = loadFile.getTmpFile();
								InformationStructureEdit edit = InformationStructureEdit
										.newSession(MeetingMinutesActivator.INFO_TYPE_ID,
												getEditingDomain());
								InformationUnit createSubType = edit.createSubType(
										MeetingMinutesActivator.NODE_NAME_ATTACHMENT, null);
								edit.setValue(createSubType,
										MeetingMinutesActivator.NODE_NAME_ATTACHMENT_LABEL,
										new Path(open).lastSegment());
								edit.addDynamicNode(getModelObject(), createSubType,
										getEditingDomain(), Collections.singletonMap(
												MeetingMinutesActivator.NODE_NAME_ATTACHMENT,
												tmpFile));
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

		final Button deleteButton = toolkit.createButton(client, Messages.AttachmentsEditPage_Delete, SWT.FLAT);
		deleteButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				CompoundCommand cc = new CompoundCommand();
				List list = ((IStructuredSelection) AttachmentsEditPage.this.attachmentsTableViewer
						.getSelection()).toList();
				for (Object object : list) {
					InformationStructureRead read = InformationStructureRead.newSession(
							(InformationUnit) object, MeetingMinutesActivator.INFO_TYPE_ID);
					List<BinaryReference> binaryReferences = read.getBinaryReferences(
							((InformationUnit) object).getType(), false);
					for (BinaryReference binaryReference : binaryReferences) {
						cc.append(new DeleteBinaryReferenceCommand(binaryReference,
								getEditingDomain()));
					}
				}
				Command deleteCmd = DeleteCommand.create(getEditingDomain(),
						((IStructuredSelection) AttachmentsEditPage.this.attachmentsTableViewer
								.getSelection()).toList());
				cc.append(deleteCmd);
				cc.setLabel(Messages.AttachmentsEditPage_DeleteAttachments);
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
				if (MessageDialog.openConfirm(getSite().getShell(), Messages.AttachmentsEditPage_ConfirmOpenAttachments,
						Messages.AttachmentsEditPage_ConfirmOpenAttachmentsDetails)) {
					List list = ((IStructuredSelection) event.getSelection()).toList();
					for (Object object : list) {
						InformationStructureRead read = InformationStructureRead.newSession(
								(InformationUnit) object, MeetingMinutesActivator.INFO_TYPE_ID);
						InformationUnit childByNodeId = read
								.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_ATTACHMENT);
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
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).hint(80, SWT.DEFAULT).applyTo(
				addbutton);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(deleteButton);

	}

	@Override
	public void bindValuesToUi() {
		InformationStructureRead read = InformationStructureRead.newSession(getModelObject());
		InformationUnit recipients = read
				.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_ATTENDEES);

		InformationUnit attachments = read
				.getChildByNodeId(MeetingMinutesActivator.NODE_NAME_ATTACHMENTS);
		this.attendeesTableViewer.setInput(recipients);
		this.attachmentsTableViewer.setInput(attachments);

		super.bindValuesToUi();
	}

	@Override
	public void dispose() {
		MeetingMinutesActivator.getDefault().getServiceTracker().ungetService(this.editingHandler);
		super.dispose();
	}

}
