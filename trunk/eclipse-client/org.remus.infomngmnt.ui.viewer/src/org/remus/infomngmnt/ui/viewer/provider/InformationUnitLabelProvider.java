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

package org.remus.infomngmnt.ui.viewer.provider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.ui.infotypes.service.IInformationTypeImage;
import org.remus.infomngmnt.ui.viewer.ViewerActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationUnitLabelProvider extends AdapterFactoryLabelProvider {

	private final IInformationTypeImage service;

	public InformationUnitLabelProvider(final AdapterFactory adapterFactory) {
		super(adapterFactory);
		this.service = ViewerActivator.getDefault().getServiceTracker().getService(
				IInformationTypeImage.class);
	}

	@Override
	public Image getImage(final Object object) {
		if (object instanceof Category) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_FOLDER);
		} else if (object instanceof InformationUnitListItem) {
			return this.service.getImageByInfoType(((InformationUnitListItem) object).getType());
		} else {
			return super.getImage(object);
		}
	}

	@Override
	public void dispose() {
		ViewerActivator.getDefault().getServiceTracker().ungetService(this.service);
		super.dispose();
	}

}
