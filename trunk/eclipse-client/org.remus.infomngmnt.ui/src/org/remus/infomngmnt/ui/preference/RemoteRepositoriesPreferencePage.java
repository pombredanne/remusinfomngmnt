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

package org.remus.infomngmnt.ui.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemoteRepositoriesPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	public RemoteRepositoriesPreferencePage() {
		noDefaultAndApplyButton();
	}

	@Override
	protected Control createContents(final Composite parent) {
		Composite comp = new Composite(parent, 0);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		comp.setLayout(layout);
		Label descLabel = new Label(comp, 0);
		descLabel.setText("Expand the tree for editing Repository preferences.");
		return comp;
	}

	public void init(final IWorkbench iworkbench) {
	}

}
