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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import org.remus.infomngmnt.birtreport.parameter.AbstractParameterControl;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TextParameterControl extends AbstractParameterControl {

	public static final String DEFAULT_VALUE = "default"; //$NON-NLS-1$

	private String value;

	/**
	 * 
	 */
	public TextParameterControl() {
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
		Text text = new Text(parent, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		text.addListener(SWT.Modify, new Listener() {
			public void handleEvent(final Event event) {
				TextParameterControl.this.value = ((Text) event.widget).getText();
			}
		});
		if (this.options.get(DEFAULT_VALUE) != null) {
			text.setText(this.options.get(DEFAULT_VALUE));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.birtreport.parameter.AbstractParameterControl#
	 * getParameterValue()
	 */
	@Override
	public String getParameterValue() {
		return this.value;
	}

}
