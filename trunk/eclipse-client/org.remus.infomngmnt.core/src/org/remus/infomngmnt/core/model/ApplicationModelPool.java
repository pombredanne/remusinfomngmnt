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
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
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
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.extension.ISaveParticipant;
import org.remus.infomngmnt.core.services.ISaveParticipantExtensionService;
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
			if (msg.getEventType() == Notification.SET
					&& msg.getFeature() == InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL) {
				ApplicationModelPool.this.cache
						.addItem((InformationUnitListItem) msg.getNotifier());
			} else if (msg.getEventType() == Notification.REMOVE
					&& msg.getOldValue() instanceof InformationUnitListItem) {
				ApplicationModelPool.this.cache
						.remove(((InformationUnitListItem) msg.getOldValue()).getId());
			} else if (msg.getEventType() == Notification.ADD
					&& msg.getNewValue() instanceof InformationUnitListItem) {
				ApplicationModelPool.this.cache
						.addItem((InformationUnitListItem) msg.getNewValue());
			} else if (msg.getEventType() == Notification.ADD
					&& msg.getNewValue() instanceof Category) {
				InformationUnitListItem[] allInfoUnitItems = CategoryUtil
						.getAllInfoUnitItems((Category) msg.getNewValue());
				for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
					ApplicationModelPool.this.cache.addItem(informationUnitListItem);
				}

			}
			EditingUtil.getInstance().saveObjectToResource(this.category);
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
				ApplicationModelPool.getInstance().getModel().getAvailableTags().eResource().save(
						Collections.EMPTY_MAP);
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
			addToModel(project);
		}
		this.cache = new AvailableInformationCache();
		AvailableTags objectFromUri = EditingUtil.getInstance().getObjectFromFileUri(
				URI.createFileURI(InfomngmntEditPlugin.getPlugin().getStateLocation().append(
						"tags.xml").toOSString()), InfomngmntPackage.Literals.AVAILABLE_TAGS,
				EditingUtil.getInstance().getNavigationEditingDomain());
		EditingUtil.getInstance().getNavigationEditingDomain().getResourceSet().getResources().add(
				objectFromUri.eResource());
		this.model.setAvailableTags(objectFromUri);
		this.model.getAvailableTags().eAdapters().add(new AdapterTagImplExtension());

	}

	public void removeFromModel(final IProject project) throws CoreException {
		Category itemByValue = (Category) ModelUtil.getItemByValue(getModel().getRootCategories(),
				InfomngmntPackage.Literals.CATEGORY__LABEL, project.getName());
		if (itemByValue != null) {
			Collection<ISaveParticipant> allItems = InfomngmntEditPlugin.getPlugin().getService(
					ISaveParticipantExtensionService.class).getAllItems();
			for (ISaveParticipant iSaveParticipant : allItems) {
				iSaveParticipant.handleClean(project);
			}
			getModel().getRootCategories().remove(itemByValue);
		}
		project.close(new NullProgressMonitor());
		clearCache();

	}

	public void addToModel(final IProject project) {
		if (project.isOpen()) {
			IFile file = project.getFile(new Path(ResourceUtil.SETTINGS_FOLDER + File.separator
					+ ResourceUtil.PRIMARY_CONTENT_FILE));
			final Category category = EditingUtil.getInstance().getObjectFromFile(file,
					InfomngmntPackage.eINSTANCE.getCategory(),
					EditingUtil.getInstance().getNavigationEditingDomain());

			category.eResource().eAdapters().add(new AdapterImplExtension(category));

			this.model.getRootCategories().add(category);
			EditingUtil.getInstance().getNavigationEditingDomain().getResourceSet().getResources()
					.add(category.eResource());

		}

	}

	public ApplicationRoot getModel() {
		return this.model;
	}

	public void addListenerToCategory(final Category category) {
		EditingUtil.getInstance().getNavigationEditingDomain().getResourceSet().getResources().add(
				category.eResource());
		category.eAdapters().add(new AdapterImplExtension(category));
	}

	public Map<String, InformationUnitListItem> getAllItems(final IProgressMonitor monitor) {
		return this.cache.getAllItems(monitor);
	}

	public InformationUnitListItem getItemById(final String id, final IProgressMonitor monitor) {
		return this.cache.getItemById(id, monitor);
	}

	public void clearCache() {
		this.cache.clear();
	}

}