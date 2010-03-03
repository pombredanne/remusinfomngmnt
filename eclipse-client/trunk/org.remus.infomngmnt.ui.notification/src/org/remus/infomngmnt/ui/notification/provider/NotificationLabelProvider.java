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

package org.remus.infomngmnt.ui.notification.provider;

import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.common.ui.image.ResourceManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationLabelProvider extends LabelProvider implements IFontProvider {

	private final Viewer viewer;

	public NotificationLabelProvider(final Viewer viewer) {
		this.viewer = viewer;

	}

	@Override
	public Image getImage(final Object element) {
		Object image = ((Notification) element).getImage();
		if (image instanceof Image) {
			return (Image) image;
		}
		return null;
	}

	@Override
	public String getText(final Object element) {
		return ((Notification) element).getMessage();
	}

	public Font getFont(final Object object) {
		if (!((Notification) object).isNoticed()) {
			return ResourceManager.getBoldFont(this.viewer.getControl().getFont());
		}
		return null;
	}

}
