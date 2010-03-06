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

package org.remus.infomngmnt.birtreport.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import org.remus.infomngmnt.birtreport.parameter.AbstractParameterControl;
import org.remus.infomngmnt.ui.widgets.DateCombo;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DateParameterControl extends AbstractParameterControl {

	public static final String DEFAULT_VALUE = "default"; //$NON-NLS-1$

	public static final String INPUT_DATE_PATTERN = "pattern"; //$NON-NLS-1$

	public static final String OUTPUT_DATE_PATTERN = "outputpatter"; //$NON-NLS-1$

	private Date date2Set;

	/**
	 * 
	 */
	public DateParameterControl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.birtreport.parameter.AbstractParameterControl#
	 * createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(final Composite parent) {
		DateCombo dateCombo = new DateCombo(parent, SWT.FLAT | SWT.BORDER, false);
		dateCombo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		dateCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		if (this.options.get(DEFAULT_VALUE) != null) {
			try {
				if (this.options.get(INPUT_DATE_PATTERN) != null) {
					this.date2Set = new SimpleDateFormat().parse(this.options.get(DEFAULT_VALUE));
				} else {
					this.date2Set = SimpleDateFormat.getDateInstance().parse(
							this.options.get(DEFAULT_VALUE));
				}
				dateCombo.setSelection(new StructuredSelection(this.date2Set));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dateCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(final SelectionChangedEvent event) {
				DateParameterControl.this.date2Set = (Date) ((IStructuredSelection) event
						.getSelection()).getFirstElement();
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.birtreport.parameter.AbstractParameterControl#
	 * getParameterValue()
	 */
	@Override
	public String getParameterValue() {
		if (this.date2Set != null) {
			if (this.options.get(OUTPUT_DATE_PATTERN) != null) {
				return new SimpleDateFormat(this.options.get(OUTPUT_DATE_PATTERN))
						.format(this.date2Set);
			} else {
				return SimpleDateFormat.getDateInstance().format(this.date2Set);
			}
		}
		return null;
	}

}
