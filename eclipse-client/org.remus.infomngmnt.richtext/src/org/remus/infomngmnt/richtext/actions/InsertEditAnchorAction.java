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

import java.util.Properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.richhtml4eclipse.widgets.AllActionConstants;
import de.spiritlink.richhtml4eclipse.widgets.EventConstants;
import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;
import de.spiritlink.richhtml4eclipse.widgets.PropertyConstants;

import org.remus.infomngmnt.richtext.dialogs.AnchorDialog;

/**
 * 
 * @author Tom Seidel <tom.seidel@spiritlink.de>
 * 
 */
public class InsertEditAnchorAction extends Action implements Listener {

	private HtmlComposer composer = null;

	private String name = null;

	public InsertEditAnchorAction(final HtmlComposer composer) {
		super("", IAction.AS_CHECK_BOX);
		setToolTipText("Insert/Edit anchor");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				"de.spiritlink.richhtml4eclipse",
				"tiny_mce/jscripts/tiny_mce/themes/advanced/images/anchor.gif"));
		this.composer = composer;
		this.composer.addListener(EventConstants.ANCHOR, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		AnchorDialog dialog = new AnchorDialog(this.composer.getShell(), this.name);
		if (dialog.open() == IDialogConstants.OK_ID) {
			this.composer.execute(JavaScriptCommands.INSERT_ANCHOR(dialog.getName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
	 * Event)
	 */
	public void handleEvent(final Event event) {
		Properties evtProps = (Properties) event.data;
		if (event.type != EventConstants.ALL) {
			setChecked(true);
			this.name = evtProps.getProperty(PropertyConstants.NAME);
		} else if (evtProps.getProperty(PropertyConstants.COMMAND).equals(
				AllActionConstants.RESET_ALL)) {
			setChecked(false);
			this.name = ""; //$NON-NLS-1$
		}
	}

}
