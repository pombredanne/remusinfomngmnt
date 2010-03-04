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

package org.remus.infomngmnt.ui.notification;

import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.common.ui.image.ResourceManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationUtil {

	public static Image getImageBySeverity(final Severity severity) {
		switch (severity) {
		case OK:
			return ResourceManager.getPluginImage(NotificationActivator.getDefault(),
					"icons/16/ok.png");
		case INFO:
			return ResourceManager.getPluginImage(NotificationActivator.getDefault(),
					"icons/16/information.png");
		case WARNING:
			return ResourceManager.getPluginImage(NotificationActivator.getDefault(),
					"icons/16/sign_warning.png");
		case ERROR:
			return ResourceManager.getPluginImage(NotificationActivator.getDefault(),
					"icons/16/error.png");
		default:
			break;
		}
		return null;
	}

}
