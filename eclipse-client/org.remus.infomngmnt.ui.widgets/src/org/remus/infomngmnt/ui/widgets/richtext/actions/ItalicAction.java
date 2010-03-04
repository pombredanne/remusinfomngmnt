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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.richhtml4eclipse.widgets.AllActionConstants;
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
public class ItalicAction extends Action implements Listener {
	private HtmlComposer composer = null;

	public ItalicAction(final HtmlComposer composer) {
		super("Italic", IAction.AS_CHECK_BOX);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				"de.spiritlink.richhtml4eclipse",
				"tiny_mce/jscripts/tiny_mce/themes/advanced/images/italic.gif"));
		this.composer = composer;
		this.composer.addListener(EventConstants.ITALIC, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		this.composer.execute(JavaScriptCommands.ITALIC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.
	 * Event)
	 */
	public void handleEvent(final Event event) {
		Properties props = (Properties) event.data;
		if (ComposerStatus.SELECTED.equals(props.getProperty(PropertyConstants.STATUS))) {
			// current selection/cursor is bold --> set the action checked
			setChecked(true);
		} else if (ComposerStatus.NORMAL.equals(props.getProperty(PropertyConstants.STATUS))) {
			setChecked(false);
		} else if (event.type == EventConstants.ALL
				&& AllActionConstants.RESET_ALL
						.equals(props.getProperty(PropertyConstants.COMMAND))) {
			// callback if the cursor changed, reset the state.
			setChecked(false);
		}
	}

}