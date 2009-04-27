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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InformationUnit;

public class EditContactEmailNewDialog extends TitleAreaDialog {

	private InformationUnit informationUnit;
	private FormToolkit toolkit;
	private EditGeneralPage editGeneralPage;
	private Button bt_Ok;
	private Composite area;
	private Text tx_Email;

	public EditContactEmailNewDialog(FormToolkit toolkit, Shell parentShell, InformationUnit informationUnit, EditGeneralPage editGeneralPage) {
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
		this.area.setLayout(gridLayout);
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label lb_Email = new Label(area, SWT.NONE);
		final GridData gd_lb_Email = new GridData();
		lb_Email.setLayoutData(gd_lb_Email);
		lb_Email.setText("New E-Mail");

		tx_Email = new Text(area, SWT.BORDER);
		final GridData gd_tx_Email = new GridData(SWT.FILL, SWT.CENTER, true, false);
		tx_Email.setLayoutData(gd_tx_Email);
		tx_Email.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				validateEmail();				
			}
		});		
		return this.area;		
	}
		private void validateEmail() {
			if (tx_Email.getText().contains("@")) {
				bt_Ok.setEnabled(true);
			}else bt_Ok.setEnabled(false);
		}
}
