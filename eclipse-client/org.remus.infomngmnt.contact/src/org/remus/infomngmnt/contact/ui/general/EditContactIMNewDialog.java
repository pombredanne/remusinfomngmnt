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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.contact.core.ContactSettings;

public class EditContactIMNewDialog extends TitleAreaDialog {
	private InformationUnit informationUnit;
	private FormToolkit toolkit;
	private EditGeneralPage editGeneralPage;
	private Button bt_Ok;
	private Composite area;
	private Combo combo_Protocol;
	private Text tx_Address;

	public EditContactIMNewDialog(FormToolkit toolkit, Shell parentShell, InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
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
		final GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		this.area.setLayout(gridLayout);
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label lb_Protocol = new Label(area, SWT.NONE);
		lb_Protocol.setText("Protocol:");

		combo_Protocol = new Combo(area, SWT.NONE);
		final GridData gd_combo_Protocol = new GridData(SWT.FILL, SWT.CENTER, true, false);
		combo_Protocol.setLayoutData(gd_combo_Protocol);

		final Label lb_Address = new Label(area, SWT.NONE);
		lb_Address.setText("Address:");

		tx_Address = new Text(area, SWT.BORDER);
		final GridData gd_tx_Address = new GridData(SWT.FILL, SWT.CENTER, true, false);
		tx_Address.setLayoutData(gd_tx_Address);
		
		createListener();
		createTextValueBindings();
		
		setContactProportiesFromActivatorToGenerationDialog();
		
		return this.area;
	}
	private void setContactProportiesFromActivatorToGenerationDialog() {
		// TODO Auto-generated method stub
		
	}
	private void createTextValueBindings() {
		// TODO change
		combo_Protocol.setItems(ContactSettings.DEFAULT_ITMES_COMBO_IM_ADDRESS_CHOOSER);
		
	}
	private void createListener() {
		// TODO Auto-generated method stub
		
	}
}
