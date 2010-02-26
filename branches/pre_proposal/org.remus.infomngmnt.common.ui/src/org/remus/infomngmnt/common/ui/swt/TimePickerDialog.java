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

package org.remus.infomngmnt.common.ui.swt;

import java.util.Date;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.nebula.widgets.cdatetime.CDT;
import org.eclipse.nebula.widgets.cdatetime.CDateTime;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TimePickerDialog extends Dialog {

	private Date selectedDate;

	protected TimePickerDialog(final Shell parentShell, final Date initialDate) {
		super(parentShell);
		this.selectedDate = initialDate;
		setShellStyle(getShellStyle() | SWT.RESIZE);

	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Select Date & Time");
	};

	@Override
	protected Control createDialogArea(final Composite parent) {
		Composite composite = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		{
			final CDateTime cdt = new CDateTime(composite, CDT.BORDER | CDT.SIMPLE
					| CDT.TIME_MEDIUM | CDT.DATE_MEDIUM | CDT.CLOCK_12_HOUR);
			cdt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			cdt.setSelection(this.selectedDate);
			cdt.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(final SelectionEvent e) {
					TimePickerDialog.this.selectedDate = cdt.getSelection();
				}
			});
			cdt.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDoubleClick(final MouseEvent e) {
					TimePickerDialog.this.selectedDate = cdt.getSelection();
					okPressed();
				}
			});
		}

		return composite;
	}

	public Date getSelectedDate() {
		return this.selectedDate;
	}
}
