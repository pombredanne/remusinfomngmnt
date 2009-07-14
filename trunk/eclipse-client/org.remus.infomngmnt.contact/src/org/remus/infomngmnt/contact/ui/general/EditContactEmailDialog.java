/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.contact.ui.general;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.InformationUtil;

public class EditContactEmailDialog extends StatusDialog {

	private final InformationUnit informationUnit;
	private final EditingDomain editingDomain;
	private Composite area;
	private TableViewer tv_Email;
	private Button bt_AddNew;
	private Button bt_Change;
	private Button bt_Delete;
	private Button bt_SetStandard;
	private InformationUnit selectedEmail;

	public EditContactEmailDialog(final Shell parentShell, final InformationUnit informationUnit,
			final EditingDomain editingDomain) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.informationUnit = informationUnit;
		this.editingDomain = editingDomain;
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.CLOSE_LABEL, true);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout(2, false));
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		this.tv_Email = new TableViewer(this.area, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.FULL_SELECTION | SWT.BORDER);
		final Table table = this.tv_Email.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));
		this.tv_Email.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				return ((InformationUnit) element).getStringValue();
			}
		});
		this.tv_Email.setContentProvider(new AdapterFactoryContentProvider(EditingUtil
				.getInstance().getAdapterFactory()));
		this.bt_AddNew = new Button(this.area, SWT.PUSH);
		this.bt_AddNew.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		this.bt_AddNew.setText("Add New E-Mail");
		this.bt_SetStandard = new Button(this.area, SWT.PUSH);
		this.bt_SetStandard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		this.bt_SetStandard.setText("Set As Standard");
		this.bt_SetStandard.setEnabled(false);
		this.bt_Delete = new Button(this.area, SWT.PUSH);
		this.bt_Delete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		this.bt_Delete.setText("Delete");
		this.bt_Delete.setEnabled(false);
		this.bt_Change = new Button(this.area, SWT.PUSH);
		this.bt_Change.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		this.bt_Change.setText("Change");
		this.bt_Change.setEnabled(false);

		createListener();
		bindValuesToUi();

		return this.area;
	}

	private void bindValuesToUi() {
		this.tv_Email.setInput(InformationUtil.getChildByType(this.informationUnit,
				ContactActivator.NODE_MAILS));

	}

	private void createListener() {
		this.bt_AddNew.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InputDialog newDialog = new InputDialog(getShell(), "Add new email",
						"Enter new email adress", "", new IInputValidator() {
							public String isValid(final String newText) {
								return newText.trim().length() == 0 ? "Input required" : null;
							}
						});
				if (newDialog.open() == IDialogConstants.OK_ID) {
					InformationUnit newEmail = InfomngmntFactory.eINSTANCE.createInformationUnit();
					newEmail.setType(ContactActivator.NODE_MAIL);
					newEmail.setStringValue(newDialog.getValue());
					Command command = AddCommand.create(EditContactEmailDialog.this.editingDomain,
							InformationUtil.getChildByType(
									EditContactEmailDialog.this.informationUnit,
									ContactActivator.NODE_MAILS),
							InfomngmntPackage.Literals.INFORMATION_UNIT__CHILD_VALUES, newEmail);
					EditContactEmailDialog.this.editingDomain.getCommandStack().execute(command);
				}
			}
		});
		this.tv_Email.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					setSelection(null);
				} else {
					InformationUnit firstElement = (InformationUnit) ((IStructuredSelection) event
							.getSelection()).getFirstElement();
					setSelection(firstElement);
				}
			}
		});
		this.bt_Change.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				InputDialog newDialog = new InputDialog(getShell(), "Edit email",
						"Enter new email adress", EditContactEmailDialog.this.selectedEmail
								.getStringValue(), new IInputValidator() {
							public String isValid(final String newText) {
								return newText.trim().length() == 0 ? "Input required" : null;
							}
						});
				if (newDialog.open() == IDialogConstants.OK_ID) {
					Command command = SetCommand.create(EditContactEmailDialog.this.editingDomain,
							EditContactEmailDialog.this.selectedEmail,
							InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE, newDialog
									.getValue());
					EditContactEmailDialog.this.editingDomain.getCommandStack().execute(command);
				}
			}
		});
		this.bt_Delete.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				if (MessageDialog.openConfirm(getShell(), "Confirm deletion",
						"Selected email will be deleted. Continue?")) {
					Command command = DeleteCommand.create(
							EditContactEmailDialog.this.editingDomain,
							EditContactEmailDialog.this.selectedEmail);
					EditContactEmailDialog.this.editingDomain.getCommandStack().execute(command);
				}
			}
		});
		this.bt_SetStandard.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				Command command = SetCommand.create(EditContactEmailDialog.this.editingDomain,
						InformationUtil.getChildByType(EditContactEmailDialog.this.informationUnit,
								ContactActivator.NODE_MAIL_DEF),
						InfomngmntPackage.Literals.INFORMATION_UNIT__STRING_VALUE,
						EditContactEmailDialog.this.selectedEmail.getStringValue());
				EditContactEmailDialog.this.editingDomain.getCommandStack().execute(command);
			}
		});

	}

	private void setSelection(final InformationUnit firstElement) {
		this.selectedEmail = firstElement;
		if (this.selectedEmail != null) {
			this.bt_SetStandard.setEnabled(true);
			this.bt_Change.setEnabled(true);
			this.bt_Delete.setEnabled(true);
		}

	}

	@Override
	protected Point getInitialSize() {
		return new Point(400, 300);
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Edit E-Mail Adresses");
	}
}
