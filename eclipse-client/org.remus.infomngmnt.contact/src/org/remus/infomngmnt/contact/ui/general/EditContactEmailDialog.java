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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.model.InformationUtil;

public class EditContactEmailDialog extends TitleAreaDialog {

	private InformationUnit informationUnit;
	private FormToolkit toolkit;
	private EditGeneralPage editGeneralPage;
	private Button bt_Ok;
	private Composite area;
	private TableViewer tv_Email;
	private Button bt_AddNew;
	private Button bt_Change;
	private Button bt_Delete;
	private Button bt_SetStandard;
	private Shell parentShell;
	private String selectedEmail;
	private List<String> listEmails = new ArrayList<String>(1);
	protected int iSelectedEmail;

	public EditContactEmailDialog(FormToolkit toolkit, Shell parentShell, InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.parentShell = parentShell;
		this.informationUnit = informationUnit;
		this.toolkit = toolkit;
		this.editGeneralPage = editGeneralPage;
	}
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		this.bt_Ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		if (InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_DEF).getStringValue().length() > 1) {
			bt_Ok.setEnabled(true);
		}
		else this.bt_Ok.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	@Override
	public boolean close() {
		return super.close();
	}
	@Override
	protected void okPressed(){
		deleteContactActivatorListEmails();
		setListEmailsToContactActivator();
		super.okPressed();
	}
	private void deleteContactActivatorListEmails() {
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_1).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_2).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_3).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_4).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_5).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_6).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_7).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_8).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_9).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_10).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_DEF).setStringValue("");			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group_Properties = new Group(this.area, SWT.NONE);
		final GridData gd_group_Properties = new GridData(SWT.FILL, SWT.TOP, true, true);
		gd_group_Properties.widthHint = 487;
		group_Properties.setLayoutData(gd_group_Properties);

		final GridLayout gl_ProportiesGroup = new GridLayout();
		gl_ProportiesGroup.numColumns = 2;
		group_Properties.setLayout(gl_ProportiesGroup);
		
		final Composite comp_Email_Table = toolkit.createComposite(group_Properties);
		comp_Email_Table.setLayout(new GridLayout());
		comp_Email_Table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		final Composite comp_Email_Buttons = toolkit.createComposite(group_Properties);
		comp_Email_Buttons.setLayout(new GridLayout());
		final GridData gd_comp_Email_Buttons = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd_comp_Email_Buttons.widthHint = 82;
		comp_Email_Buttons.setLayoutData(gd_comp_Email_Buttons);
		
		this.tv_Email = new TableViewer(comp_Email_Table, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		final Table table = tv_Email.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		this.tv_Email.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.tv_Email.setLabelProvider(new LabelProvider());
		this.tv_Email.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		bt_AddNew = toolkit.createButton(comp_Email_Buttons, "Add New E-Mail", SWT.BORDER);
		final GridData gd_bt_AddNew = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_bt_AddNew.widthHint = 78;
		bt_AddNew.setLayoutData(gd_bt_AddNew);
		bt_Change = toolkit.createButton(comp_Email_Buttons, "Change", SWT.BORDER);
		bt_Change.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bt_Change.setEnabled(false);
		bt_Delete = toolkit.createButton(comp_Email_Buttons, "Delete", SWT.BORDER);
		bt_Delete.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bt_Delete.setEnabled(false);
		bt_SetStandard = toolkit.createButton(comp_Email_Buttons, "Set As Standard", SWT.BORDER);
		bt_SetStandard.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		bt_SetStandard.setEnabled(false);
		
		createListener();
		createTextValueBindings();
		
		setContactActivatorEmailsToListEmails();
		try {
			
			List<String> curList = removeNullValuesFromList(listEmails);
			listEmails = curList;
			this.tv_Email.setInput(listEmails);
			this.tv_Email.refresh();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		selectStandardMail();
		
		validatePage();
		
		return this.area;		
	}
	private void selectStandardMail() {
		String s = null;
		try {
			s = InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_DEF).getStringValue();			
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (s != null) {
			int n = -1;
			for (int i = 0; i < listEmails.size(); i++) {
				if (s.equals(listEmails.get(i))) {
					n = i;
				}
			}
			tv_Email.getTable().setSelection(n);
			selectedEmail = s;
		}
		
	}
	private void setContactActivatorEmailsToListEmails() {
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_1).getStringValue());			
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_2).getStringValue());		
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_3).getStringValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_4).getStringValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_5).getStringValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_6).getStringValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_7).getStringValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_8).getStringValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_9).getStringValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			listEmails.add(InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_10).getStringValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void setListEmailsToContactActivator(){
		List<String> curList = removeNullValuesFromList(listEmails);
		listEmails.clear();
		listEmails.addAll(curList);
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_1).setStringValue(listEmails.get(0));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_2).setStringValue(listEmails.get(1));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_3).setStringValue(listEmails.get(2));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_4).setStringValue(listEmails.get(3));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_5).setStringValue(listEmails.get(4));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_6).setStringValue(listEmails.get(5));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_7).setStringValue(listEmails.get(6));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_8).setStringValue(listEmails.get(7));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_9).setStringValue(listEmails.get(8));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_10).setStringValue(listEmails.get(9));
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_DEF).setStringValue(selectedEmail);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void createTextValueBindings() {
		
	}
	private void createListener() {
		bt_AddNew.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				iSelectedEmail = tv_Email.getTable().getSelectionIndex();
				EditContactEmailChangeDialog ecend = new EditContactEmailChangeDialog(toolkit, parentShell, informationUnit, editGeneralPage, "New E-Mail", null);
				ecend.setAddNewWizard(true);
				ecend.open();
				listEmails.add(ecend.getCurText());
				List<String> curList = removeNullValuesFromList(listEmails);
				if (curList.size()==1) {
					selectedEmail = curList.get(0);
//					InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_DEF).setStringValue();
					bt_Ok.setEnabled(true);
				}
				tv_Email.setInput(curList);
				tv_Email.refresh();
			}
		});
		this.tv_Email.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					selectedEmail = null;
				}
				Object firstElement = ((IStructuredSelection) event.getSelection()).getFirstElement();
				try {
					selectedEmail = firstElement.toString();
					iSelectedEmail = tv_Email.getTable().getSelectionIndex();
				} catch (Exception e) {
					// TODO: handle exception
				}
				validatePage();
			}
		});
		bt_Change.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				iSelectedEmail = tv_Email.getTable().getSelectionIndex();
				EditContactEmailChangeDialog ecend = new EditContactEmailChangeDialog(toolkit, parentShell, informationUnit, editGeneralPage, "Change Selected E-Mail", selectedEmail);
				ecend.setChangeWizard(true);
				ecend.open();
				listEmails.remove(iSelectedEmail);
				listEmails.add(iSelectedEmail, ecend.getCurText());
				List<String> curList = removeNullValuesFromList(listEmails);
				tv_Email.setInput(curList);
				tv_Email.getTable().setSelection(iSelectedEmail);
				tv_Email.refresh();
			}
		});
		bt_Delete.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				iSelectedEmail = tv_Email.getTable().getSelectionIndex();
				selectedEmail = tv_Email.getTable().getItem(iSelectedEmail).getText();
				EditContactEmailChangeDialog ecend = new EditContactEmailChangeDialog(toolkit, parentShell, informationUnit, editGeneralPage, "Delete Selected E-Mail?", selectedEmail);
				ecend.setDeletionWizard(true);
				ecend.open();
				if(ecend.isDeletionSelected){
					listEmails.remove(iSelectedEmail);
					List<String> curList = removeNullValuesFromList(listEmails);
					listEmails = curList;
					tv_Email.refresh();
				}
			}
		});
		bt_SetStandard.addMouseListener(new MouseAdapter() {
			public void mouseUp(final MouseEvent e) {
				InformationUtil.getChildByType(informationUnit, ContactActivator.NODE_MAIL_DEF).setStringValue(selectedEmail);
				bt_Ok.setEnabled(true);
			}
		});
	}
	private void validatePage() {
		if (selectedEmail != null) {
			bt_SetStandard.setEnabled(true);
			bt_Change.setEnabled(true);
			bt_Delete.setEnabled(true);
		}
	}
	private List<String> removeNullValuesFromList(
			List<String> listEmails) {
		List<String> curList = new ArrayList<String>(1);
		for (int i = 0; i < listEmails.size(); i++) {
			if (listEmails.get(i) != null && listEmails.get(i) != "") {
				curList.add(listEmails.get(i));
			}	
		}
		return curList;
	}
}
