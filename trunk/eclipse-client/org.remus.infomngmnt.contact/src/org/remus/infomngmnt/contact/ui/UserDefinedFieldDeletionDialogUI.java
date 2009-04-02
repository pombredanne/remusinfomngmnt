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
package org.remus.infomngmnt.contact.ui;
/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.common.ui.UIUtil;

public class UserDefinedFieldDeletionDialogUI extends TitleAreaDialog {

	private final InformationUnit contact;
	private final AdapterFactoryEditingDomain editingDomain;
	private Composite area;
	private TableViewer tv_ExistingFields;
	private Button bt_Ok;

	public UserDefinedFieldDeletionDialogUI(Shell parentShell, InformationUnit contact, AdapterFactoryEditingDomain editingDomain) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		this.contact = contact;
		this.editingDomain = editingDomain;
	}
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		this.bt_Ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.bt_Ok.setEnabled(false);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void okPressed() {
		// TODO
		super.okPressed();
	}
	
	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group group_Properties = new Group(this.area, SWT.NONE);
		group_Properties.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false));

		final GridLayout gl_ProportiesGroup = new GridLayout();
		gl_ProportiesGroup.numColumns = 1;
		group_Properties.setLayout(gl_ProportiesGroup);

		final Label lb_Title = new Label(group_Properties, SWT.NONE);
		lb_Title.setText("Please choose the field for deletion:");
		
		this.tv_ExistingFields = new TableViewer(group_Properties, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		this.tv_ExistingFields.setContentProvider(UIUtil.getArrayContentProviderInstance());
		this.tv_ExistingFields.setLabelProvider(new LabelProvider());
		this.tv_ExistingFields.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// TODO
		List<String> l = new ArrayList<String>(1);
		l.add("dummy 1");
		l.add("dummy 2");
		this.tv_ExistingFields.setInput(l);
		this.tv_ExistingFields.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(final SelectionChangedEvent event) {
				bt_Ok.setEnabled(true);
			}
		});
		
		return this.area;		
	}
}
