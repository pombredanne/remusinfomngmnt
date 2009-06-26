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

package org.remus.infomngmnt.core.sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.commands.DeleteBinaryReferenceCommand;
import org.remus.infomngmnt.core.commands.DeleteInformationUnitCommand;
import org.remus.infomngmnt.core.extension.AbstractExtensionRepository;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.IdFactory;
import org.remus.infomngmnt.core.model.StatusCreator;
import org.remus.infomngmnt.core.path.Path2ObjectMapper;
import org.remus.infomngmnt.core.remote.IChangeSetDefinition;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ChangeSetExecutor {

	private static final String COMPARE_FOLDER = "compare";
	private ChangeSet changeSet;
	private IPath remotePath;
	private IPath localPath;
	private DiffModel diffModel;

	public void checkout(final ChangeSet changeset, final IProgressMonitor monitor) {
		EList<ChangeSetItem> changeSetItems = changeset.getChangeSetItems();
		for (ChangeSetItem changeSetItem : changeSetItems) {
			Category remoteConvertedContainer = changeSetItem.getRemoteConvertedContainer();
			Category newLocalCateogries = removeInfoUnits((Category) EcoreUtil
					.copy(remoteConvertedContainer));
			changeset.getTargetCategory().getChildren().add(newLocalCateogries);
			List<InformationUnitListItem> allChildren = ModelUtil
					.getAllChildren(remoteConvertedContainer,
							InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM);
			for (InformationUnitListItem informationUnitListItem : allChildren) {
				monitor.beginTask(NLS.bind("Checkout of element {0}", informationUnitListItem
						.getLabel()), allChildren.size());
				try {
					addLocalInfoUnit(informationUnitListItem, changeSetItem, monitor);
				} catch (Exception e) {
					InfomngmntEditPlugin.getPlugin().getLog().log(
							StatusCreator.newStatus("Error checking out online-element", e));
				}
			}
		}

	}

	private Category removeInfoUnits(final Category remoteConvertedContainer) {
		InformationUnitListItem[] allInfoUnitItems = CategoryUtil
				.getAllInfoUnitItems(remoteConvertedContainer);
		for (InformationUnitListItem informationUnitListItem : allInfoUnitItems) {
			((Category) informationUnitListItem.eContainer()).getInformationUnit().remove(
					informationUnitListItem);
		}
		return remoteConvertedContainer;
	}

	public ChangeSet getChangeSet() {
		return this.changeSet;
	}

	public void setChangeSet(final ChangeSet changeSet) {
		this.changeSet = changeSet;
	}

	public void prepareDiff(final Category pLocalTargetCateogory) {
		Category localTargetCategory = InfomngmntFactory.eINSTANCE.createCategory();
		Category tmpRemoteRootCategory = CategoryUtil.copyBlankObject(localTargetCategory);
		this.changeSet.setTargetCategory(pLocalTargetCateogory);

		EList<ChangeSetItem> changeSetItems = this.changeSet.getChangeSetItems();
		for (ChangeSetItem changeSetItem2 : changeSetItems) {
			tmpRemoteRootCategory.getChildren().add(
					(Category) EcoreUtil.copy(changeSetItem2.getRemoteConvertedContainer()));
		}

		this.remotePath = InfomngmntEditPlugin.getPlugin().getStateLocation()
				.append(COMPARE_FOLDER).append(IdFactory.createNewId(null)).addFileExtension("xml");

		this.localPath = InfomngmntEditPlugin.getPlugin().getStateLocation().append(COMPARE_FOLDER)
				.append(IdFactory.createNewId(null)).addFileExtension("xml");

		EditingUtil.getInstance().saveObjectToResource(tmpRemoteRootCategory,
				this.remotePath.toOSString());

		EditingUtil.getInstance().saveObjectToResource(localTargetCategory,
				this.localPath.toOSString());
	}

	public DiffModel makeDiff() {
		MatchModel match;
		try {
			EObject localObject = EditingUtil.getInstance().getObjectFromFileUri(
					URI.createFileURI(this.localPath.toOSString()),
					InfomngmntPackage.Literals.CATEGORY, null);
			EObject remoteObject = EditingUtil.getInstance().getObjectFromFileUri(
					URI.createFileURI(this.remotePath.toOSString()),
					InfomngmntPackage.Literals.CATEGORY, null);
			match = MatchService.doMatch(localObject, remoteObject, Collections
					.<String, Object> emptyMap());
			this.diffModel = DiffService.doDiff(match, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.diffModel;
	}

	public void synchronize(final EList<DiffElement> diffElements, final ChangeSetItem item,
			final IProgressMonitor monitor, final Category targetCategory) throws CoreException {
		Set<Category> keySet = item.getSyncCategoryActionMap().keySet();
		MultiStatus status = new MultiStatus(CorePlugin.PLUGIN_ID, 500,
				"Error while synchronizing", null);
		for (Category category : keySet) {
			SynchronizationAction synchronizationAction = item.getSyncCategoryActionMap().get(
					category);
			switch (synchronizationAction) {
			case REPLACE_LOCAL:
				try {
					replaceLocalCategory(category);
				} catch (Exception e) {
					append2Status(status, e);
				}
				break;
			case ADD_LOCAL:
				try {
					addLocalCategory(category, targetCategory, monitor, item);
				} catch (Exception e) {
					append2Status(status, e);
				}
				break;
			case DELETE_LOCAL:
				try {
					deleteLocalCategory(category);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case DELETE_REMOTE:
				// TODO
				break;
			case REPLACE_REMOTE:
				// TODO
				break;
			case ADD_REMOTE:
				// TODO
				break;
			default:
				break;
			}
		}
		Set<SynchronizableObject> keySet2 = item.getSyncObjectActionMap().keySet();
		for (SynchronizableObject synchronizableObject : keySet2) {
			if (synchronizableObject instanceof InformationUnitListItem) {
				try {
					InformationUnitListItem itemById = ApplicationModelPool.getInstance()
							.getItemById(((InformationUnitListItem) synchronizableObject).getId(),
									monitor);
					if (itemById != null) {
						itemById.getSynchronizationMetaData().setCurrentlySyncing(true);
					}
					SynchronizationAction synchronizationAction = item.getSyncObjectActionMap()
							.get(synchronizableObject);
					switch (synchronizationAction) {
					case REPLACE_LOCAL:
						try {
							replaceLocalInfoUnit((InformationUnitListItem) synchronizableObject,
									item, monitor);
						} catch (Exception e) {
							append2Status(status, e);
						}
						break;
					case ADD_LOCAL:
						try {
							addLocalInfoUnit((InformationUnitListItem) synchronizableObject, item,
									monitor);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case DELETE_LOCAL:
						try {
							deleteLocalInforUnit((InformationUnitListItem) synchronizableObject,
									item, monitor);
						} catch (Exception e) {
							append2Status(status, e);
						}
						break;
					case DELETE_REMOTE:
						try {
							deleteRemoteInfoUnit((InformationUnitListItem) synchronizableObject,
									monitor);
						} catch (Exception e) {
							append2Status(status, e);
						}
						break;
					case REPLACE_REMOTE:
						try {
							replaceRemoteInfoUnit((InformationUnitListItem) synchronizableObject,
									monitor);
						} catch (Exception e) {
							append2Status(status, e);
						}
						break;
					case ADD_REMOTE:
						try {
							addRemoteInfoUnit((InformationUnitListItem) synchronizableObject,
									monitor);
						} catch (Exception e) {
							append2Status(status, e);
						}
						break;
					default:
						break;
					}
				} finally {
					InformationUnitListItem itemById = ApplicationModelPool.getInstance()
							.getItemById(((InformationUnitListItem) synchronizableObject).getId(),
									monitor);
					if (itemById != null) {
						itemById.getSynchronizationMetaData().setCurrentlySyncing(false);
					}
				}
			}
		}
		if (status.getChildren().length > 0) {
			throw new ChangeSetException(status);
		}
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().flush();
	}

	private void append2Status(final MultiStatus status, final Exception e) {
		if (e instanceof CoreException) {
			status.add(((CoreException) e).getStatus());
		} else {
			status.add(StatusCreator.newStatus("Error synchronizing", e));
		}
	}

	private void replaceRemoteInfoUnit(final InformationUnitListItem synchronizableObject,
			final IProgressMonitor monitor) throws CoreException {
		InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
				synchronizableObject.getId(), monitor);
		if (itemById == null) {
			throw new ChangeSetException(
					StatusCreator
							.newStatus("Invalid state. A information unit synchronize was requested, but the local item was not found."));
		}
		RemoteRepository remoteRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(
				synchronizableObject.getSynchronizationMetaData().getRepositoryId());
		AbstractExtensionRepository itemByRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryExtensionService.class).getItemByRepository(remoteRepository);
		String newHash = itemByRepository.commit(itemById, monitor);
		itemById.getSynchronizationMetaData().setLastSynchronisation(new Date());
		itemById.getSynchronizationMetaData().setHash(newHash);
		itemById.getSynchronizationMetaData().setSyncState(SynchronizationState.IN_SYNC);

	}

	private void deleteRemoteInfoUnit(final InformationUnitListItem synchronizableObject,
			final IProgressMonitor monitor) throws CoreException {
		InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
				synchronizableObject.getId(), monitor);
		if (itemById == null) {
			throw new ChangeSetException(
					StatusCreator
							.newStatus("Invalid state. A information unit synchronize was requested, but the local item was not found."));
		}
		RemoteRepository remoteRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(
				synchronizableObject.getSynchronizationMetaData().getRepositoryId());
		AbstractExtensionRepository itemByRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryExtensionService.class).getItemByRepository(remoteRepository);
		itemByRepository.deleteFromRepository(itemById, monitor);
		EditingDomain createNewEditingDomain = EditingUtil.getInstance().createNewEditingDomain();

		Command create = DeleteCommand.create(createNewEditingDomain, Collections
				.singletonList(itemById));
		createNewEditingDomain.getCommandStack().execute(create);
		createNewEditingDomain.getCommandStack().flush();

	}

	private void addRemoteInfoUnit(final InformationUnitListItem synchronizableObject,
			final IProgressMonitor monitor) throws CoreException {
		InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
				synchronizableObject.getId(), monitor);
		if (itemById == null) {
			throw new ChangeSetException(
					StatusCreator
							.newStatus("Invalid state. A information unit synchronize was requested, but the local item was not found."));
		}
		RemoteRepository remoteRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(
				synchronizableObject.getSynchronizationMetaData().getRepositoryId());
		AbstractExtensionRepository itemByRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryExtensionService.class).getItemByRepository(remoteRepository);
		RemoteObject addToRepository = itemByRepository.addToRepository(synchronizableObject,
				monitor);
		SynchronizationMetadata synchronizationMetaData = itemById.getSynchronizationMetaData();
		synchronizationMetaData.setHash(addToRepository.getHash());
		synchronizationMetaData.setUrl(addToRepository.getUrl());
		synchronizationMetaData.setRepositoryId(remoteRepository.getId());
		synchronizationMetaData.setLastSynchronisation(new Date());
		synchronizationMetaData.setSyncState(SynchronizationState.IN_SYNC);
	}

	private void deleteLocalInforUnit(final InformationUnitListItem synchronizableObject,
			final ChangeSetItem item, final IProgressMonitor monitor) throws ChangeSetException {
		InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
				synchronizableObject.getId(), monitor);
		if (itemById == null) {
			throw new ChangeSetException(
					StatusCreator
							.newStatus("Invalid state. A information unit synchronize was requested, but the local item was not found."));
		}
		EditingDomain editingDomain = EditingUtil.getInstance().createNewEditingDomain();
		Command deleteInfounit = CommandFactory.DELETE_INFOUNIT_WITHOUT_SYNC_CHECK(Collections
				.<InformationUnitListItem> singletonList(itemById), editingDomain);
		editingDomain.getCommandStack().execute(deleteInfounit);
		editingDomain.getCommandStack().flush();
	}

	private void addLocalInfoUnit(final InformationUnitListItem synchronizableObject,
			final ChangeSetItem item, final IProgressMonitor monitor) throws CoreException {

		SynchronizationMetadata synchronizationMetadata = synchronizableObject
				.getSynchronizationMetaData();
		RemoteRepository remoteRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(
				synchronizationMetadata.getRepositoryId());
		AbstractExtensionRepository itemByRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryExtensionService.class).getItemByRepository(remoteRepository);

		/*
		 * Get the full object from repository
		 */
		InformationUnit newRemoteInformationUnit = SyncUtil.getFullObjectFromChangeSet(item,
				synchronizableObject, itemByRepository, monitor);
		newRemoteInformationUnit.setLabel(synchronizableObject.getLabel());
		newRemoteInformationUnit.setId(synchronizableObject.getId());

		Category eContainer = (Category) synchronizableObject.eContainer();
		Category parentCategory = CategoryUtil.getCategoryById(eContainer.getId());

		EditingDomain editingDomain = EditingUtil.getInstance().createNewEditingDomain();
		CompoundCommand createInfotype = CommandFactory.CREATE_INFOTYPE(newRemoteInformationUnit,
				parentCategory);
		IFile[] binaryReferences = itemByRepository.getBinaryReferences(synchronizableObject,
				monitor);
		for (IFile iFile : binaryReferences) {
			Command addFileCommand = CommandFactory.addFileToInfoUnit(iFile,
					newRemoteInformationUnit, editingDomain);
			createInfotype.append(addFileCommand);
		}
		editingDomain.getCommandStack().execute(createInfotype);
		InformationUnitListItem localListItem = (InformationUnitListItem) newRemoteInformationUnit
				.getAdapter(InformationUnitListItem.class);

		InformationUnit newInformationUnit = (InformationUnit) localListItem
				.getAdapter(InformationUnit.class);
		if (newInformationUnit != null) {
			itemByRepository.proceedLocalInformationUnitAfterSync(newInformationUnit);
		}
		localListItem.setSynchronizationMetaData(synchronizableObject.getSynchronizationMetaData());
	}

	private void replaceLocalInfoUnit(final InformationUnitListItem synchronizableObject,
			final ChangeSetItem changeSetItem, final IProgressMonitor monitor) throws CoreException {
		InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
				synchronizableObject.getId(), monitor);
		if (itemById == null) {
			throw new ChangeSetException(
					StatusCreator
							.newStatus("Invalid state. A information unit synchronize was requested, but the local item was not found."));
		}

		EditingDomain editingDomain = EditingUtil.getInstance().createNewEditingDomain();

		/*
		 * Replacing is a bit difficult. We cannot delete the old and creating a
		 * new because all other data which is not regarding to repository data
		 * will be lost. So we have to ask the extension-point which data is
		 * relevant. This data has to be set to to the old/and new
		 * Information-unit.
		 */
		SynchronizationMetadata synchronizationMetadata = synchronizableObject
				.getSynchronizationMetaData();
		RemoteRepository remoteRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositoryById(
				synchronizationMetadata.getRepositoryId());
		AbstractExtensionRepository itemByRepository = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryExtensionService.class).getItemByRepository(remoteRepository);
		IChangeSetDefinition changeSetDefinitionForType = itemByRepository
				.getChangeSetDefinitionForType(synchronizableObject.getType());
		/*
		 * Get the full object from repository
		 */
		InformationUnit newRemoteInformationUnit = SyncUtil.getFullObjectFromChangeSet(
				changeSetItem, synchronizableObject, itemByRepository, monitor);

		newRemoteInformationUnit.setLabel(synchronizableObject.getLabel());
		newRemoteInformationUnit.setId(synchronizableObject.getId());
		InformationUnit adapter = (InformationUnit) itemById.getAdapter(InformationUnit.class);
		if (changeSetDefinitionForType != null) {
			List<String> relevantObjectPaths = changeSetDefinitionForType.getRelevantObjectPaths();
			for (String string : relevantObjectPaths) {
				Object object = Path2ObjectMapper.getObjectByExpression(string,
						newRemoteInformationUnit);
				Path2ObjectMapper path2ObjectMapper = new Path2ObjectMapper(string, adapter, object);
				path2ObjectMapper.getObjectForPath(true, true);
			}
			EditingUtil.getInstance().saveObjectToResource(adapter);
			IFile file = (IFile) adapter.getAdapter(IFile.class);
			if (file != null && file.exists()) {
				file.getProject().build(IncrementalProjectBuilder.INCREMENTAL_BUILD, monitor);
			}
			/*
			 * Binary references are replaced ALWAYS if repository supports
			 * this.
			 */
			if (itemByRepository.hasBinaryReferences()) {
				List<BinaryReference> binaryReferences2 = new ArrayList<BinaryReference>(adapter
						.getBinaryReferences());
				for (BinaryReference binaryReference : binaryReferences2) {
					DeleteBinaryReferenceCommand command = new DeleteBinaryReferenceCommand(
							binaryReference, editingDomain);
					editingDomain.getCommandStack().execute(command);
				}
				IFile[] binaryReferences = itemByRepository.getBinaryReferences(
						synchronizableObject, monitor);
				for (IFile iFile : binaryReferences) {
					Command addFileCommand = CommandFactory.addFileToInfoUnit(iFile, adapter,
							editingDomain);
					editingDomain.getCommandStack().execute(addFileCommand);
				}
			}
		} else {
			Category parentCategory = (Category) itemById.eContainer();
			CompoundCommand cc = new CompoundCommand();
			cc.append(new DeleteInformationUnitCommand(Collections
					.<InformationUnitListItem> singletonList(itemById), editingDomain, true));
			CompoundCommand command = CommandFactory.CREATE_INFOTYPE_FROM_EXISTING_LISTITEM(
					newRemoteInformationUnit, parentCategory, monitor, itemById);

			IFile[] binaryReferences = itemByRepository.getBinaryReferences(synchronizableObject,
					monitor);
			for (IFile iFile : binaryReferences) {
				Command addFileCommand = CommandFactory.addFileToInfoUnit(iFile,
						newRemoteInformationUnit, editingDomain);
				command.append(addFileCommand);
			}
			cc.append(command);
			editingDomain.getCommandStack().execute(cc);
		}
		itemById = ApplicationModelPool.getInstance().getItemById(synchronizableObject.getId(),
				monitor);
		InformationUnit newInformationUnit = (InformationUnit) itemById
				.getAdapter(InformationUnit.class);
		if (newInformationUnit != null) {
			itemByRepository.proceedLocalInformationUnitAfterSync(newInformationUnit);
		}
		itemById.setSynchronizationMetaData(synchronizableObject.getSynchronizationMetaData());
		editingDomain.getCommandStack().flush();
	}

	private void addLocalCategory(final Category category, final Category localParentCategory,
			final IProgressMonitor monitor, final ChangeSetItem changeSetItem) {
		Category newLocalCateogries = removeInfoUnits((Category) EcoreUtil.copy(category));
		localParentCategory.getChildren().add(newLocalCateogries);
		List<InformationUnitListItem> allChildren = ModelUtil.getAllChildren(category,
				InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM);
		for (InformationUnitListItem informationUnitListItem : allChildren) {
			monitor.beginTask(NLS.bind("Checkout of element {0}", informationUnitListItem
					.getLabel()), allChildren.size());
			try {
				addLocalInfoUnit(informationUnitListItem, changeSetItem, monitor);
			} catch (Exception e) {
				InfomngmntEditPlugin.getPlugin().getLog().log(
						StatusCreator.newStatus("Error checking out online-element", e));
			}
		}
	}

	private void deleteLocalCategory(final Category category) {
		Category localCategory = CategoryUtil.getCategoryById(category.getId());
		EditingDomain editingDomain = EditingUtil.getInstance().createNewEditingDomain();
		Command deleteCategory = CommandFactory.DELETE_CATEGORY(localCategory, editingDomain);
		editingDomain.getCommandStack().execute(deleteCategory);
		editingDomain.getCommandStack().flush();
	}

	private void replaceLocalCategory(final Category category) throws ChangeSetException {
		Category localCategory = CategoryUtil.getCategoryById(category.getId());
		if (localCategory == null) {
			throw new ChangeSetException(
					StatusCreator
							.newStatus("Invalid state. A category synchronize was requested, but the category was not found."));
		}
		localCategory.setSynchronizationMetaData((SynchronizationMetadata) EcoreUtil.copy(category
				.getSynchronizationMetaData()));
		localCategory.setLabel(category.getLabel());
	}

}
