/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.ui.remote;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.remote.service.IRepositoryExtensionService;

public class RemoteRepositoryDialog extends TitleAreaDialog {

	private Table table;

	/**
	 * Create the dialog
	 * 
	 * @param parentShell
	 */
	public RemoteRepositoryDialog(final Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		final FillLayout fillLayout = new FillLayout();
		fillLayout.marginWidth = 5;
		fillLayout.marginHeight = 5;
		container.setLayout(fillLayout);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));

		final TableViewer tableViewer = new TableViewer(container, SWT.BORDER);
		this.table = tableViewer.getTable();
		tableViewer.setContentProvider(new ObservableListContentProvider());

		WritableList list = new WritableList();
		list.addAll(UIPlugin.getDefault().getService(IRepositoryExtensionService.class)
				.getAllItems());
		tableViewer.setInput(list);

		//
		return area;
	}

	/**
	 * Create contents of the button bar
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(500, 375);
	}

}
