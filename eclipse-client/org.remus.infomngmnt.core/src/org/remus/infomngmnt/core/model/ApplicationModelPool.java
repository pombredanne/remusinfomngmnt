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

package org.remus.infomngmnt.core.model;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;

import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ApplicationModelPool {

	private final AvailableInformationCache cache;

	private final class AdapterImplExtension extends EContentAdapter {
		private final Category category;

		AdapterImplExtension(Category category) {
			this.category = category;
		}

		@Override
		public void notifyChanged(Notification msg) {
			if (msg.getNotifier() instanceof ResourceImpl) {
				return;
			}
			System.out.println(msg);
			try {
				this.category.eResource().save(null);
				//if (msg.getEventType() == Notification.)
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.notifyChanged(msg);
		}
	}

	private static ApplicationModelPool INSTANCE;

	public static ApplicationModelPool getInstance() {
		if (INSTANCE == null) {
			synchronized (ApplicationModelPool.class) {
				if (INSTANCE == null) {
					INSTANCE = new ApplicationModelPool();
				}
			}
		}
		return INSTANCE;
	}

	private final ApplicationRoot model;

	private ApplicationModelPool() {
		this.model = InfomngmntFactory.eINSTANCE.createApplicationRoot();
		IProject[] relevantProjects = ResourceUtil.getRelevantProjects();
		for (IProject project : relevantProjects) {
			IFile file = project.getFile(new Path(ResourceUtil.SETTINGS_FOLDER + File.separator + ResourceUtil.PRIMARY_CONTENT_FILE));
			final Category category = EditingUtil.getInstance().getObjectFromFile(
					file, InfomngmntPackage.eINSTANCE.getCategory(), EditingUtil.getInstance().getNavigationEditingDomain());
			category.eResource().eAdapters().add(new AdapterImplExtension(category));
			this.model.getRootCategories().add(category);
			EditingUtil.getInstance().getNavigationEditingDomain().getResourceSet().getResources().add(category.eResource());
		}
		this.cache = new AvailableInformationCache();
	}

	public ApplicationRoot getModel() {
		return this.model;
	}

	public void addListenerToCategory(Category category) {
		EditingUtil.getInstance().getNavigationEditingDomain().getResourceSet().getResources().add(category.eResource());
		category.eAdapters().add(new AdapterImplExtension(category));
	}

	public Map<String, InformationUnitListItem> getAllItems(
			IProgressMonitor monitor) {
		return this.cache.getAllItems(monitor);
	}

	public InformationUnitListItem getItemById(String id,
			IProgressMonitor monitor) {
		return this.cache.getItemById(id, monitor);
	}



}
