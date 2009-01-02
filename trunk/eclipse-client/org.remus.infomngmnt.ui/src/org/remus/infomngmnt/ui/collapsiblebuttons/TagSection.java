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

package org.remus.infomngmnt.ui.collapsiblebuttons;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.ui.extension.CollapsibleButtonBar;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TagSection extends CollapsibleButtonBar {

	@Override
	public void createControl(final Composite parent) {
		setControl(new Composite(parent,SWT.NULL));
	}

}
