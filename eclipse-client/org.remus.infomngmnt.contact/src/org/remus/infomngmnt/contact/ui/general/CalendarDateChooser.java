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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Shell;

public class CalendarDateChooser extends TitleAreaDialog {

	private Button bt_Ok;
	private Composite area;
	private DateTime calendar;
	public String selectedDate;
	private String currentDate;	
	
	public CalendarDateChooser(Shell parentShell, String currentDate) {
		super(parentShell);
		this.currentDate = currentDate;
	}
	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		this.bt_Ok = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		this.bt_Ok.setEnabled(true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}
	@Override
	public boolean close() {
		return super.close();
	}
	@Override
	protected void okPressed(){
		if (calendar != null) {
			selectedDate = (calendar.getMonth() + 1) + "/" + calendar.getDay() + "/" + calendar.getYear();			
		}
		super.okPressed();
	}
	@Override
	protected Control createDialogArea(final Composite parent) {
		this.area = new Composite((Composite) super.createDialogArea(parent), SWT.NONE);
		this.area.setLayout(new GridLayout());
		this.area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		calendar = new DateTime (area, SWT.CALENDAR | SWT.BORDER);
		
		String[] splittedDate = currentDate.split("/");
		calendar.setMonth(Integer.valueOf(splittedDate[0])-1);
		calendar.setDay(Integer.valueOf(splittedDate[1]));
		calendar.setYear(Integer.valueOf(splittedDate[2]));
		
		return area;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
}
