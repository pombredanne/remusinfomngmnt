/*******************************************************************************
 * Copyright (c) 2007 Tom Seidel, Spirit Link GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * 
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.common.ui.richtext.actions;

import java.util.Properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import de.spiritlink.richhtml4eclipse.util.ColorConverter;
import de.spiritlink.richhtml4eclipse.widgets.ComposerStatus;
import de.spiritlink.richhtml4eclipse.widgets.EventConstants;
import de.spiritlink.richhtml4eclipse.widgets.HtmlComposer;
import de.spiritlink.richhtml4eclipse.widgets.JavaScriptCommands;
import de.spiritlink.richhtml4eclipse.widgets.PropertyConstants;

/**
 * @author Tom Seidel <tom.seidel@spiritlink.de>
 * 
 */
public class ForegroundAction extends Action implements Listener {

	private HtmlComposer composer = null;

	public ForegroundAction(final HtmlComposer composer) {
		super("", IAction.AS_PUSH_BUTTON); //$NON-NLS-1$
		setToolTipText("Set foreground color");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				"de.spiritlink.richhtml4eclipse", //$NON-NLS-1$
				"tiny_mce/jscripts/tiny_mce/themes/advanced/images/forecolor.gif")); //$NON-NLS-1$
		this.composer = composer;
		this.composer.addListener(EventConstants.FORECOLOR, this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		ColorDialog colorDialog = new ColorDialog(this.composer.getShell());
		if (colorDialog.open() != null) {
			this.composer.execute(JavaScriptCommands.FORECOLOR(colorDialog.getRGB()));
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
		if (event.type != EventConstants.ALL) {
			Properties evtProps = (Properties) event.data;
			if (evtProps.getProperty(PropertyConstants.VALUE).equals(ComposerStatus.UNDEFINED)) {
				setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
						"de.spiritlink.richhtml4eclipse", //$NON-NLS-1$
						"tiny_mce/jscripts/tiny_mce/themes/advanced/images/forecolor.gif")); //$NON-NLS-1$
			} else {
				Image image = new Image(Display.getDefault(), 16, 16);
				GC gc = new GC(image);
				gc.setBackground(new Color(null, ColorConverter.convertHexToRgb(evtProps
						.getProperty(PropertyConstants.VALUE))));
				gc.fillRectangle(0, 0, 16, 16);
				gc.dispose();
				setImageDescriptor(ImageDescriptor.createFromImage(image));
			}
		}

	}

}
