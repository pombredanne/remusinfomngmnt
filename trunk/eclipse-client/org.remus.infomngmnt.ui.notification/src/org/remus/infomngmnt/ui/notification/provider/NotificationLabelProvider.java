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

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.ui.infotypes.service.IInformationTypeImage;
import org.remus.infomngmnt.ui.notification.NotificationActivator;
import org.remus.infomngmnt.ui.notification.NotificationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationLabelProvider extends LabelProvider implements IFontProvider {

	private final Viewer viewer;

	private final IInformationTypeImage imageService;

	private final IApplicationModel appService;

	public NotificationLabelProvider(final Viewer viewer) {
		this.viewer = viewer;
		this.imageService = NotificationActivator.getDefault().getServiceTracker().getService(
				IInformationTypeImage.class);
		this.appService = NotificationActivator.getDefault().getServiceTracker().getService(
				IApplicationModel.class);

	}

	@Override
	public void dispose() {
		NotificationActivator.getDefault().getServiceTracker().ungetService(this.imageService);
		NotificationActivator.getDefault().getServiceTracker().ungetService(this.appService);
		super.dispose();
	}

	@Override
	public Image getImage(final Object element) {
		Notification notification = (Notification) element;
		if (notification.getAffectedInfoUnitIds().size() > 0
				&& this.appService.getItemById(notification.getAffectedInfoUnitIds().get(0), null) != null) {
			InformationUnitListItem itemById = this.appService.getItemById(notification
					.getAffectedInfoUnitIds().get(0), null);

			Image image = this.imageService.getImageByInfoType(itemById.getType());
			if (image != null) {
				return image;
			}
		}
		return NotificationUtil.getImageBySeverity(notification.getSeverity());
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
