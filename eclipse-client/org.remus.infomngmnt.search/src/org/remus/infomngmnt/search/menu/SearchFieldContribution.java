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

package org.remus.infomngmnt.search.menu;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import org.remus.infomngmnt.common.ui.swt.InputPrompter;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchFieldContribution extends WorkbenchWindowControlContribution {

	/**
	 * 
	 */
	public SearchFieldContribution() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 */
	public SearchFieldContribution(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.ControlContribution#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(final Composite parent) {
		
		Text text = new Text(parent, SWT.SINGLE | SWT.LEAD | SWT.BORDER | SWT.SEARCH);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
//		text.setText("Search");
		InputPrompter.addPrompt(text, "Enter your searchterm");
		return text;
	}

	@Override
	protected int computeWidth(final Control control) {
		return 300;
	}


}
