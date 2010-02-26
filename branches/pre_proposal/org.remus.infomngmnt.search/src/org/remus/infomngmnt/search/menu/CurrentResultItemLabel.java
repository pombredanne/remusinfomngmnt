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
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CurrentResultItemLabel extends WorkbenchWindowControlContribution {

	/**
	 * 
	 */
	public CurrentResultItemLabel() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 */
	public CurrentResultItemLabel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.ControlContribution#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		label.setText("Search");

		return label;
	}



	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

}
