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
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;

public class EditContactIMDialog extends TitleAreaDialog {
	private InformationUnit informationUnit;
	private FormToolkit toolkit;
	private EditGeneralPage editGeneralPage;
	private Button bt_Ok;
	private Composite area;
	private TableViewer tv_IM;
	private Button bt_AddNew;
	private Button bt_Change;
	private Button bt_Delete;
	private Button bt_SetStandard;
	private Shell parentShell;

	public EditContactIMDialog(FormToolkit toolkit, Shell parentShell, InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
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
		this.bt_Ok.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	@Override
	public boolean close() {
		//setUserSettings();
		return super.close();
	}
	@Override
	protected void okPressed(){
		super.okPressed();
	}
	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group_Properties = new Group(this.area, SWT.NONE);
		final GridData gd_group_Properties = new GridData(SWT.FILL, SWT.TOP, true, false);
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
		
		this.tv_IM = new TableViewer(comp_Email_Table, SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		this.tv_IM.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.tv_IM.setLabelProvider(new LabelProvider());
		this.tv_IM.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		bt_AddNew = toolkit.createButton(comp_Email_Buttons, "Add New IM", SWT.BORDER);
		final GridData gd_bt_AddNew = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd_bt_AddNew.widthHint = 78;
		bt_AddNew.setLayoutData(gd_bt_AddNew);
		bt_Change = toolkit.createButton(comp_Email_Buttons, "Cahange", SWT.BORDER);
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
		
		setContactProportiesFromActivatorToGenerationDialog();		
		
		return this.area;		
	}
	private void setContactProportiesFromActivatorToGenerationDialog() {
		// TODO Auto-generated method stub
		
	}
	private void createTextValueBindings() {
		// TODO Auto-generated method stub
		
	}
	private void createListener() {
		bt_AddNew.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				EditContactIMNewDialog ecend = new EditContactIMNewDialog(toolkit, parentShell, informationUnit, editGeneralPage);
				ecend.open();				
			}});		
	}
}
