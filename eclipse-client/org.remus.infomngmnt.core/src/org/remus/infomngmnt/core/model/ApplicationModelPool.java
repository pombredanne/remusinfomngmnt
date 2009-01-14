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
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;

import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.AvailableTags;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ApplicationModelPool {

	private final AvailableInformationCache cache;

	private final class AdapterImplExtension extends EContentAdapter {
		private final Category category;

		AdapterImplExtension(final Category category) {
			this.category = category;
		}

		@Override
		public void notifyChanged(final Notification msg) {
			if (msg.getNotifier() instanceof ResourceImpl) {
				return;
			}
			try {
				this.category.eResource().save(null);
			} catch (IOException e) {
				// do nothing
			}
			super.notifyChanged(msg);
		}
	}
	private final class AdapterTagImplExtension extends EContentAdapter {
		
		
		AdapterTagImplExtension() {
			
		}
		
		@Override
		public void notifyChanged(final Notification msg) {
			if (msg.getNotifier() instanceof ResourceImpl) {
				return;
			}
			try {
				ApplicationModelPool
					.getInstance().getModel()
					.getAvailableTags().eResource().save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				// do nothing
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
		AvailableTags objectFromUri = EditingUtil.getInstance().getObjectFromFileUri(URI.createFileURI(InfomngmntEditPlugin.getPlugin().getStateLocation().append("tags.xml").toOSString()),
				InfomngmntPackage.Literals.AVAILABLE_TAGS, EditingUtil.getInstance().getNavigationEditingDomain());
		EditingUtil.getInstance().getNavigationEditingDomain().getResourceSet().getResources().add(objectFromUri.eResource());
		this.model.setAvailableTags(objectFromUri);
		this.model.getAvailableTags().eAdapters().add(new AdapterTagImplExtension());
	}

	public ApplicationRoot getModel() {
		return this.model;
	}

	public void addListenerToCategory(final Category category) {
		EditingUtil.getInstance().getNavigationEditingDomain().getResourceSet().getResources().add(category.eResource());
		category.eAdapters().add(new AdapterImplExtension(category));
	}

	public Map<String, InformationUnitListItem> getAllItems(
			final IProgressMonitor monitor) {
		return this.cache.getAllItems(monitor);
	}

	public InformationUnitListItem getItemById(final String id,
			final IProgressMonitor monitor) {
		return this.cache.getItemById(id, monitor);
	}



}
