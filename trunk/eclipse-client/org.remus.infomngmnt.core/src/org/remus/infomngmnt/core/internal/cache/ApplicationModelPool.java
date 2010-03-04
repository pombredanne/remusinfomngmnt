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

package org.remus.infomngmnt.core.internal.cache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ItemProvider;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectReferenceValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.AvailableTags;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.extension.ISaveParticipant;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.core.services.ISaveParticipantExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.CategoryUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ApplicationModelPool implements IApplicationModel {

	private final AvailableInformationCache cache;

	private final Logger log = Logger.getLogger(ApplicationModelPool.class);

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
			} else if (msg.getEventType() == Notification.REMOVE
					&& msg.getOldValue() instanceof Category) {
				InformationUnitListItem[] allInfoUnitItems = CategoryUtil
						.getAllInfoUnitItems((Category) msg.getOldValue());
				for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
					ApplicationModelPool.this.cache.remove(informationUnitListItem.getId());
				}
			} else if (msg.getEventType() == Notification.ADD
					&& msg.getNewValue() instanceof InformationUnitListItem) {
				addItem((InformationUnitListItem) msg.getNewValue());
			} else if (msg.getEventType() == Notification.ADD
					&& msg.getNewValue() instanceof Category) {
				InformationUnitListItem[] allInfoUnitItems = CategoryUtil
						.getAllInfoUnitItems((Category) msg.getNewValue());
				for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
					addItem(informationUnitListItem);
				}
			} else if (msg.getEventType() == Notification.ADD_MANY
					&& msg.getNewValue() instanceof EList) {
				EList newItems = (EList) msg.getNewValue();
				for (Object object : newItems) {
					if (object instanceof InformationUnitListItem) {
						addItem((InformationUnitListItem) object);
					} else if (object instanceof Category) {
						InformationUnitListItem[] allInfoUnitItems = CategoryUtil
								.getAllInfoUnitItems((Category) object);
						for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
							addItem(informationUnitListItem);
						}
					}
				}
			}
			ApplicationModelPool.this.editService.saveObjectToResource(this.category);
			super.notifyChanged(msg);
		}

		/**
		 * adds an item to the cache, except, it is marked as deleted.
		 */
		private void addItem(final InformationUnitListItem item) {
			if (item.getSynchronizationMetaData() == null
					|| item.getSynchronizationMetaData().getSyncState() != SynchronizationState.LOCAL_DELETED) {
				ApplicationModelPool.this.cache.addItem(item);
			}

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
				getModel().getAvailableTags().eResource().save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				// do nothing
			}
			super.notifyChanged(msg);
		}
	}

	private static IApplicationModel INSTANCE;

	private final ApplicationRoot model;

	private final IEditingHandler editService;

	public ApplicationModelPool() {
		this.editService = InfomngmntEditPlugin.getPlugin().getEditService();
		this.log.debug("Initializing datamodel");
		this.model = InfomngmntFactory.eINSTANCE.createApplicationRoot();
		IProject[] relevantProjects = ResourceUtil.getRelevantProjects();
		this.log.debug("Found relevant projects: " + relevantProjects.length);
		for (IProject project : relevantProjects) {
			addToModel(project);
		}
		this.cache = new AvailableInformationCache(this);
		AvailableTags objectFromUri = this.editService.getObjectFromFileUri(URI
				.createFileURI(InfomngmntEditPlugin.getPlugin().getStateLocation().append(
						"tags.xml").toOSString()), InfomngmntPackage.Literals.AVAILABLE_TAGS,
				this.editService.getNavigationEditingDomain());
		this.editService.getNavigationEditingDomain().getResourceSet().getResources().add(
				objectFromUri.eResource());
		EList<Category> rootCategories = this.model.getRootCategories();
		List<Category> cat2Delete = new ArrayList<Category>();
		for (Category category : rootCategories) {
			if (category.getLabel() == null) {
				cat2Delete.add(category);
			}
		}
		this.model.getRootCategories().removeAll(cat2Delete);
		this.model.setAvailableTags(objectFromUri);
		this.model.getAvailableTags().eAdapters().add(new AdapterTagImplExtension());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.model.IApplicationModel#removeFromModel(org
	 * .eclipse.core.resources.IProject)
	 */
	public void removeFromModel(final IProject project) throws CoreException {
		Category itemByValue = (Category) ModelUtil.getItemByValue(getModel().getRootCategories(),
				InfomngmntPackage.Literals.CATEGORY__LABEL, project.getName());
		if (itemByValue != null) {
			Collection<ISaveParticipant> allItems = InfomngmntEditPlugin.getPlugin().getService(
					ISaveParticipantExtensionService.class).getAllItems();
			for (ISaveParticipant iSaveParticipant : allItems) {
				iSaveParticipant.handleClean(project);
			}
			ItemProvider provider = new ItemProvider(getModel().getRootCategories());
			provider.getChildren().remove(itemByValue);
			AdapterFactoryEditingDomain editingDomain = this.editService
					.getNavigationEditingDomain();
			Command command = SetCommand.create(editingDomain, getModel(),
					InfomngmntPackage.Literals.APPLICATION_ROOT__ROOT_CATEGORIES, provider
							.getChildren());
			editingDomain.getCommandStack().execute(command);
			// getModel().getRootCategories().remove(itemByValue);
		}
		project.close(new NullProgressMonitor());
		clearCache();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.model.IApplicationModel#addToModel(org.eclipse
	 * .core.resources.IProject)
	 */
	public void addToModel(final IProject project) {
		if (project.isOpen()) {
			this.log.debug(NLS.bind("Adding project {0} to pool", project.getName()));
			IFile file = project.getFile(new Path(ResourceUtil.SETTINGS_FOLDER + File.separator
					+ ResourceUtil.PRIMARY_CONTENT_FILE));
			AdapterFactoryEditingDomain navigationEditingDomain = this.editService
					.getNavigationEditingDomain();
			final Category category = this.editService.getObjectFromFile(file,
					InfomngmntPackage.eINSTANCE.getCategory(), navigationEditingDomain);
			if (category.getId() != null) {
				category.eResource().eAdapters().add(new AdapterImplExtension(category));
			}

			Command command = AddCommand.create(navigationEditingDomain, this.model,
					InfomngmntPackage.Literals.APPLICATION_ROOT__ROOT_CATEGORIES, category);
			navigationEditingDomain.getCommandStack().execute(command);
			this.model.getRootCategories().add(category);
			navigationEditingDomain.getResourceSet().getResources().add(category.eResource());

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.model.IApplicationModel#getModel()
	 */
	public ApplicationRoot getModel() {
		return this.model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.model.IApplicationModel#addListenerToCategory
	 * (org.remus.infomngmnt.Category)
	 */
	public void addListenerToCategory(final Category category) {
		this.editService.getNavigationEditingDomain().getResourceSet().getResources().add(
				category.eResource());
		category.eAdapters().add(new AdapterImplExtension(category));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.model.IApplicationModel#getAllItems(org.eclipse
	 * .core.runtime.IProgressMonitor)
	 */
	public Map<String, InformationUnitListItem> getAllItems(final IProgressMonitor monitor) {
		return this.cache.getAllItems(monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.model.IApplicationModel#getItemById(java.lang
	 * .String, org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InformationUnitListItem getItemById(final String id, final IProgressMonitor monitor) {
		return this.cache.getItemById(id, monitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.core.model.IApplicationModel#
	 * getItemByIdLocalDeletedIncluded(java.lang.String,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public InformationUnitListItem getItemByIdLocalDeletedIncluded(final String id,
			final IProgressMonitor monitor) {
		InformationUnitListItem itemById = this.cache.getItemById(id, monitor);
		if (itemById == null) {
			EObjectReferenceValueCondition condition = new EObjectReferenceValueCondition(
					InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA,
					new EObjectCondition() {
						@Override
						public boolean isSatisfied(final EObject object) {
							return object == null
									|| ((SynchronizationMetadata) object).getSyncState() == SynchronizationState.LOCAL_DELETED;
						}
					});
			EObjectCondition condition2 = new EObjectCondition() {

				@Override
				public boolean isSatisfied(final EObject eObject) {
					return eObject instanceof InformationUnitListItem;
				}

			};
			SELECT select = new SELECT(new FROM(getModel().getRootCategories()), new WHERE(
					condition2.AND(condition)));
			Set<? extends EObject> eObjects = select.execute().getEObjects();
			if (eObjects.size() == 1) {
				itemById = (InformationUnitListItem) eObjects.iterator().next();
			}
		}
		return itemById;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.model.IApplicationModel#clearCache()
	 */
	public void clearCache() {
		this.cache.clear();
	}

	public void setEditService(final IEditingHandler service) {
		// TODO Auto-generated method stub

	}

}
