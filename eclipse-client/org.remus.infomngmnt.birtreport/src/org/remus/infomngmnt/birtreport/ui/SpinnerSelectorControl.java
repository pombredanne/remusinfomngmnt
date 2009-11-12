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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Spinner;

import org.remus.infomngmnt.birtreport.parameter.AbstractParameterControl;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SpinnerSelectorControl extends AbstractParameterControl {

	public static final String MIN_VALUE = "min"; //$NON-NLS-1$

	public static final String MAX_VALUE = "max"; //$NON-NLS-1$

	public static final String INCREMENT_VALUE = "increment"; //$NON-NLS-1$

	public static final String DEFAULT_VALUE = "default"; //$NON-NLS-1$

	private Spinner spinner;

	private int selection;

	/**
	 * 
	 */
	public SpinnerSelectorControl() {
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
		this.spinner = new Spinner(parent, SWT.BORDER);
		int min = 0;
		try {
			min = Integer.parseInt(this.options.get(MIN_VALUE));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.spinner.setMinimum(min);
		int increment = 1;
		try {
			increment = Integer.parseInt(this.options.get(INCREMENT_VALUE));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.spinner.setIncrement(increment);

		int defaultValue = 1;
		try {
			defaultValue = Integer.parseInt(this.options.get(DEFAULT_VALUE));
			this.spinner.setSelection(defaultValue);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int max = Spinner.LIMIT;
		try {
			max = Integer.parseInt(this.options.get(MAX_VALUE));
			this.spinner.setMaximum(max);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.spinner.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));
		this.spinner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(final SelectionEvent e) {
				SpinnerSelectorControl.this.selection = ((Spinner) e.widget).getSelection();
			}
		});
		this.selection = this.spinner.getSelection();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.birtreport.parameter.AbstractParameterControl#
	 * getParameterValue()
	 */
	@Override
	public String getParameterValue() {
		return String.valueOf(this.selection);
	}
}
