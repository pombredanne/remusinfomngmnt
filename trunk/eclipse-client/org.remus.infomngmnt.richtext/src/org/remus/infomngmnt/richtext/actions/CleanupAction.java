/*******************************************************************************
 * Copyright (c) 2006 Tom Seidel, Spirit Link GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.richtext.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;

/**
 * 
 * @author Tom Seidel <tom.seidel@spiritlink.de>
 * 
 */
public class CleanupAction extends Action {

	private HtmlComposer composer = null;

	public CleanupAction(final HtmlComposer composer) {
		super("", IAction.AS_CHECK_BOX);
		setToolTipText("Cleanup");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				"de.spiritlink.richhtml4eclipse",
				"tiny_mce/jscripts/tiny_mce/themes/advanced/images/cleanup.gif"));
		this.composer = composer;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		this.composer.execute(JavaScriptCommands.CLEANUP);
	}
}
