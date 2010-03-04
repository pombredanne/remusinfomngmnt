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
package org.remus.infomngmnt.ui.widgets.richtext.actions;

import java.util.Properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.ui.widgets.richtext.dialogs.LinkDialog;

import de.spiritlink.richhtml4eclipse.widgets.ComposerStatus;
import de.spiritlink.richhtml4eclipse.widgets.EventConstants;
import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;
import de.spiritlink.richhtml4eclipse.widgets.PropertyConstants;

/**
 * 
 * @author Tom Seidel <tom.seidel@spiritlink.de>
 * 
 */
public class InsertEditLinkAction extends Action implements Listener {

	private HtmlComposer composer = null;

	private String href = null;
	private String title = null;
	private String target = null;
	private String clazz = null;

	public InsertEditLinkAction(final HtmlComposer composer) {
		super("Insert/Edit link", IAction.AS_CHECK_BOX);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				"de.spiritlink.richhtml4eclipse",
				"tiny_mce/jscripts/tiny_mce/themes/advanced/images/link.gif"));
		this.composer = composer;
		this.composer.addListener(EventConstants.LINK, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		LinkDialog dialog = new LinkDialog(this.composer.getShell(), this.href, this.title,
				this.target, this.clazz);
		if (dialog.open() == IDialogConstants.OK_ID) {
			this.composer.execute(JavaScriptCommands.INSERT_LINK(dialog.getHref(), dialog
					.getTitel(), dialog.getTarget(), dialog.getClazz()));
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
		if (event.type != EventConstants.ALL
				&& ComposerStatus.SELECTED.equals(evtProps.getProperty(PropertyConstants.STATUS))) {

			setChecked(true);
			this.href = evtProps.getProperty("href");
			this.target = evtProps.getProperty("target");
			this.title = evtProps.getProperty("title");
			this.clazz = evtProps.getProperty("class");

		} else {
			setChecked(false);
			this.href = null;
			this.target = null;
			this.title = null;
			this.clazz = null;
		}
	}
}
