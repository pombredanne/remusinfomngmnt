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

package org.remus.infomngmnt.ui.trayaction;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import org.remus.infomngmnt.common.ui.extension.IToolbarItemProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class OpenElementToolItem implements IToolbarItemProvider {

	/**
	 * 
	 */
	public OpenElementToolItem() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.extension.IToolbarItemProvider#createToolItem(org.eclipse.swt.widgets.ToolBar)
	 */
	public ToolItem createToolItem(final ToolBar parent) {
		ToolItem toolItem = new ToolItem(parent, SWT.PUSH);
		toolItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				MessageDialog.openInformation(parent.getShell(), "HALLO", "HALLO TOOLITEM");
			}
		});
		return toolItem;
	}

}
