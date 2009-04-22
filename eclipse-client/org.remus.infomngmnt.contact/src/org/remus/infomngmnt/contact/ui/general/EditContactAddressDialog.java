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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.remus.infomngmnt.InformationUnit;

public class EditContactAddressDialog extends TitleAreaDialog {

	private InformationUnit contact;
	private Composite body;
	private EditGeneralPage editGeneralPage;
	private FormToolkit toolkit;
	private Button bt_Ok;
	private Composite area;

	public EditContactAddressDialog(Composite body, FormToolkit toolkit, Shell parentShell, InformationUnit contact, EditGeneralPage editGeneralPage) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.contact = contact;
		this.body = body;
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
		//setContactProportiesFromGenerationDialogToActivator();
		return super.close();
	}
	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group_Properties = new Group(this.area, SWT.NONE);
		group_Properties.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_ProportiesGroup = new GridLayout();
		gl_ProportiesGroup.numColumns = 3;
		group_Properties.setLayout(gl_ProportiesGroup);

		GridData gd_text = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gd_text.horizontalSpan = 2;
		
		final Combo cb_Combo;
		
		final Label lb_Title = toolkit.createLabel(group_Properties, "Title:");
		
		return this.area;		
	}
}
