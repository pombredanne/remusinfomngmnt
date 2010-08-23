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

package org.remus.infomngmnt.image.tray;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.remus.ui.desktop.extension.IToolbarItemProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import org.remus.infomngmnt.image.screenshot.ScreenshotCreationWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CreateScreenShotAction implements IToolbarItemProvider {

	/**
	 * 
	 */
	public CreateScreenShotAction() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.ui.extension.IToolbarItemProvider#createToolItem
	 * (org.eclipse.swt.widgets.ToolBar)
	 */
	public ToolItem createToolItem(final ToolBar parent) {
		ToolItem toolItem = new ToolItem(parent, SWT.PUSH);
		toolItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(final Event event) {
				WizardDialog wz = new WizardDialog(parent.getShell(),
						new ScreenshotCreationWizard());
				wz.open();
			}
		});
		return toolItem;
	}

}
