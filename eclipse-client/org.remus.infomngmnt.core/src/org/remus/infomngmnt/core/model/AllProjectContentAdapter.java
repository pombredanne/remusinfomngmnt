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

package org.remus.infomngmnt.core.model;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EContentAdapter;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AllProjectContentAdapter extends AdapterImpl {
	private final IApplicationModel service;

	public AllProjectContentAdapter() {
		this.service = InfomngmntEditPlugin.getPlugin().getServiceTracker().getService(
				IApplicationModel.class);
	}

	public void initialize() {
		this.service.getModel().eAdapters().add(this);
		EList<Category> rootCategories = this.service.getModel().getRootCategories();
		for (Category category : rootCategories) {
			category.eAdapters().add(this.categoryAdapter);
		}
	}

	public void dispose() {
		this.service.getModel().eAdapters().remove(this);
		EList<Category> rootCategories = this.service.getModel().getRootCategories();
		for (Category category : rootCategories) {
			category.eAdapters().remove(this.categoryAdapter);
		}
		InfomngmntEditPlugin.getPlugin().getServiceTracker().ungetService(this.service);
	}

	private final EContentAdapter categoryAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged(final Notification notification) {
			notifyProjectChange(notification);
			super.notifyChanged(notification);

		};
	};

	@Override
	public void notifyChanged(final Notification msg) {
		if (msg.getFeature() == InfomngmntPackage.Literals.APPLICATION_ROOT__ROOT_CATEGORIES) {
			switch (msg.getEventType()) {
			case Notification.ADD:
				Category cat = (Category) msg.getNewValue();
				cat.eAdapters().add(this.categoryAdapter);
				break;
			case Notification.REMOVE:
				Category oldCat = (Category) msg.getOldValue();
				oldCat.eAdapters().remove(this.categoryAdapter);
				break;
			default:
				break;
			}
		}
		super.notifyChanged(msg);
	}

	protected abstract void notifyProjectChange(Notification notification);

}
