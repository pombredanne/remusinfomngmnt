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

package org.remus.infomngmnt.ui.calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TodayTrimArea extends WorkbenchWindowControlContribution {

	private TodayTrimControl control;

	/**
	 * 
	 */
	public TodayTrimArea() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id
	 */
	public TodayTrimArea(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.ControlContribution#createControl(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(final Composite parent) {

		this.control = new TodayTrimControl(parent, SWT.NONE);
		this.control.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		return this.control;

	}

	@Override
	public void dispose() {
		if (this.control != null) {
			this.control.dispose();
		}
		super.dispose();
	}

	@Override
	protected int computeWidth(final Control control) {
		return 180;
	}

}
