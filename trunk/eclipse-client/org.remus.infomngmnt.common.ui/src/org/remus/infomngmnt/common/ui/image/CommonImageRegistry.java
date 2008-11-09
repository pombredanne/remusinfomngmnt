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

package org.remus.infomngmnt.common.ui.image;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

import org.remus.infomngmnt.common.ui.Activator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CommonImageRegistry extends ImageRegistry {

	public static final String SECTION_HORIZONTAL = "SECTION_HORIZONTAL"; //$NON-NLS-1$
	public static final String SECTION_VERTICAL = "SECTION_VERTICAL"; //$NON-NLS-1$
	public static final String NOTIFICATION_CLOSE = "NOTIFICATION_CLOSE"; //$NON-NLS-1$
	public static final String NOTIFICATION_CLOSE_HOVER = "NOTIFICATION_CLOSE_HOVER"; //$NON-NLS-1$

	private static CommonImageRegistry INSTANCE;

	public static CommonImageRegistry getInstance() {
		if (CommonImageRegistry.INSTANCE == null) {
			synchronized (CommonImageRegistry.class) {
				if (CommonImageRegistry.INSTANCE == null) {
					CommonImageRegistry.INSTANCE = new CommonImageRegistry();
				}
			}
		}
		return CommonImageRegistry.INSTANCE;
	}

	private CommonImageRegistry() {
		initialize();
	}

	private void initialize() {
		registerImage(SECTION_HORIZONTAL, "images/th_horizontal.gif");
		registerImage(SECTION_VERTICAL, "images/th_vertical.gif");
		registerImage(NOTIFICATION_CLOSE_HOVER, "images/notification/notification-close-active.gif");
		registerImage(NOTIFICATION_CLOSE, "images/notification/notification-close.gif");

	}

	private void registerImage(String key, String fileName) {
		try {
			IPath path = new Path(fileName);
			URL url = FileLocator.find(Activator.getDefault().getBundle(), path, null);
			if (url!=null) {
				ImageDescriptor desc = ImageDescriptor.createFromURL(url);
				put(key, desc);
			}
		} catch (Exception e) {
		}
	}


}
