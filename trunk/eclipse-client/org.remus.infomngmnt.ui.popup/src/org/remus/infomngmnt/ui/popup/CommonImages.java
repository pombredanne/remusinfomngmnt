/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.popup;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommonImages {

	private final ResourceManager resourceManager;

	public static final String PLUGIN_ID = "org.remus.infomngmnt.ui.popup"; //$NON-NLS-1$

	private ImageDescriptor restore;

	private ImageDescriptor restoreHover;

	private ImageDescriptor close;

	private ImageDescriptor closeHover;

	public CommonImages(final ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
		init();
	}

	private void init() {
		this.restore = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/notification_max.gif");
		this.restoreHover = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/notification_max_active.gif");
		this.close = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/notification-close.gif");
		this.closeHover = AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID,
				"icons/notification-close-active.gif");

	}

	public Image getClose() {
		return this.resourceManager.createImage(this.close);
	}

	public Image getCloseHover() {
		return this.resourceManager.createImage(this.closeHover);
	}

	public Image getRestore() {
		return this.resourceManager.createImage(this.restore);

	}

	public Image getRestoreHover() {
		return this.resourceManager.createImage(this.restoreHover);
	}

	public void dispose() {
		this.resourceManager.destroyImage(this.restore);
		this.resourceManager.destroyImage(this.restoreHover);
		this.resourceManager.destroyImage(this.close);
		this.resourceManager.destroyImage(this.closeHover);

	}

}
