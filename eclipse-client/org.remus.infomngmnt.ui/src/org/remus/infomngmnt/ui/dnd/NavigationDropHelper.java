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

package org.remus.infomngmnt.ui.dnd;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;

import org.remus.infomngmnt.Adapter;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.IdFactory;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NavigationDropHelper {

	public static boolean canDrop(final Collection<?> source, final Object target) {

		/*
		 * first condition: source & target must not be the same
		 */
		for (Object dragElement : source) {
			if (dragElement == target) {
				return false;
			}
		}
		/*
		 * second condition: target must not be a child of target. if a drop
		 * would be allowed there would be two objects with the same id. In
		 * general I don't understand why this is not permitted by emfs default
		 * dropadapter.
		 */
		for (Object object : source) {
			if (target instanceof EObject && object instanceof Category) {
				boolean isParent = CategoryUtil.isItemParentOfCategory((EObject) target,
						(Category) object);
				if (isParent) {
					return false;
				}
			}
		}
		/*
		 * third condition the dragged items must not be a direct child of the
		 * target.
		 */
		for (Object object : source) {
			if (object instanceof EObject && ((EObject) object).eContainer() == target) {
				return false;
			}
		}
		return true;
	}

	public static Command checkProjectRelevance(final Collection<?> source, final Object target,
			final Command originCommand, final boolean copy) {
		final Set<InformationUnitListItem> relevantItems = new LinkedHashSet<InformationUnitListItem>();

		for (Object object : source) {
			if (!((EObject) object).eResource().equals(((EObject) target).eResource())
					&& target instanceof EObject) {
				if (object instanceof InformationUnitListItem) {
					relevantItems.add((InformationUnitListItem) object);
				} else if (object instanceof Category) {
					InformationUnitListItem[] allInfoUnitItems = CategoryUtil
							.getAllInfoUnitItems((Category) object);
					relevantItems.addAll(Arrays.asList(allInfoUnitItems));
				}
			}
		}
		if (relevantItems.size() == 0) {
			// we're moving elements within a project. all fine.
			return originCommand;
		}

		CompoundCommand compoundCommand = new CompoundCommand();
		compoundCommand.append(originCommand);
		for (InformationUnitListItem informationUnitListItem : relevantItems) {
			String targetPath = calculateNewWorkspacePath(informationUnitListItem,
					(EObject) target, copy);
			Command moveResourceCommand;
			if (copy) {
				compoundCommand.append(SetCommand.create(EditingUtil.getInstance()
						.getNavigationEditingDomain(), informationUnitListItem,
						InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__ID, new Path(
								targetPath).removeFileExtension().lastSegment()));
				moveResourceCommand = CommandFactory.COPY_INFOUNIT_COMMAND(informationUnitListItem,
						targetPath);
			} else {
				moveResourceCommand = CommandFactory.MOVE_INFOUNIT_COMMAND(informationUnitListItem,
						targetPath);
			}
			Command setWorkspacePathCommand = CommandFactory.SET_WORKSPACEPATH(
					informationUnitListItem, targetPath, EditingUtil.getInstance()
							.getNavigationEditingDomain());
			compoundCommand.append(moveResourceCommand);
			compoundCommand.append(setWorkspacePathCommand);
		}
		compoundCommand.setLabel(originCommand.getLabel());
		compoundCommand.setDescription(originCommand.getLabel());
		return compoundCommand;

	}

	private static String calculateNewWorkspacePath(final InformationUnitListItem source,
			final EObject target, final boolean copy) {
		Path targetPath = new Path(target.eResource().getURI().toPlatformString(true));
		Path sourcePath = new Path(source.getWorkspacePath());
		if (copy) {
			return new Path(IPath.SEPARATOR + targetPath.segment(0)).append(
					IdFactory.createNewId(new NullProgressMonitor())).addFileExtension(
					ResourceUtil.FILE_EXTENSION).toOSString();
		}
		return new Path(IPath.SEPARATOR + targetPath.segment(0)).append(
				sourcePath.removeFirstSegments(1)).toOSString();
	}

	/**
	 * This helper method checks if the sources or targets are under
	 * sync-control. The following things have to be applied. If the source is
	 * under source control we have to mark the objects as deleted. If the
	 * target is under sync-control we have to set the items as new sync-objects
	 * that needs to be added to the target remote-repository.
	 * 
	 * @param source
	 * @param target
	 * @param command
	 * @return
	 */
	public static Command checkSyncStates(final Collection<?> source, final Object target,
			final Command command) {
		SynchronizationMetadata targetSyncData = null;
		if (target instanceof SynchronizableObject) {
			targetSyncData = (SynchronizationMetadata) ((Adapter) target)
					.getAdapter(SynchronizationMetadata.class);
		}
		CompoundCommand compoundCommand = new CompoundCommand();
		compoundCommand.append(command);
		for (Object object : source) {
			if (object instanceof SynchronizableObject) {
				SynchronizationMetadata syncMetaData = (SynchronizationMetadata) ((Adapter) object)
						.getAdapter(SynchronizationMetadata.class);
				if (syncMetaData != null) {

					/*
					 * if the source is under sync-control and is already in the
					 * repository, we have to mark it as deleted.
					 */
					if (syncMetaData.getSyncState() != SynchronizationState.NOT_ADDED
							&& ((IAdaptable) ((EObject) object).eContainer())
									.getAdapter(SynchronizationMetadata.class) != null) {
						SynchronizableObject newObject = (SynchronizableObject) EcoreUtil
								.copy((EObject) object);
						newObject.getSynchronizationMetaData().setSyncState(
								SynchronizationState.LOCAL_DELETED);
						EReference newReference = null;
						if (newObject instanceof Category) {
							((Category) newObject).setId(IdFactory.createNewId(null));
							newReference = InfomngmntPackage.Literals.CATEGORY__CHILDREN;
						} else if (newObject instanceof InformationUnitListItem) {
							((InformationUnitListItem) newObject)
									.setId(IdFactory.createNewId(null));
							newReference = InfomngmntPackage.Literals.CATEGORY__INFORMATION_UNIT;
						}
						if (newReference != null) {
							compoundCommand.append(new CreateChildCommand(EditingUtil.getInstance()
									.getNavigationEditingDomain(), ((EObject) object).eContainer(),
									newReference, newObject, Collections.EMPTY_LIST));
						}
					}
					/*
					 * The target is not under sync-control remove the
					 * synchronization metadata from source and all children
					 * that are synchronizable objects.
					 */
					if (targetSyncData == null) {
						/*
						 * if the sync-metadata of the parent object is not
						 * present the dragged object is a complete repository.
						 * In this case we have to move everything.
						 */
						if (((IAdaptable) ((EObject) object).eContainer())
								.getAdapter(SynchronizationMetadata.class) != null) {
							compoundCommand.append(CommandFactory.REMOVE_SYNCDATACOMMAND(
									(SynchronizableObject) object, EditingUtil.getInstance()
											.getNavigationEditingDomain()));
							TreeIterator<EObject> eAllContents = ((EObject) object).eAllContents();
							while (eAllContents.hasNext()) {
								EObject eObject = eAllContents.next();
								if (eObject instanceof SynchronizableObject) {
									compoundCommand.append(CommandFactory.REMOVE_SYNCDATACOMMAND(
											(SynchronizableObject) eObject, EditingUtil
													.getInstance().getNavigationEditingDomain()));
								}
							}
						}

					}
					// else if ((target instanceof Category && ((EObject)
					// object).eContainer() == target)
					// || (target instanceof InformationUnitListItem &&
					// ((EObject) object)
					// .eContainer() == ((EObject) target).eContainer())) {
					// // do nothing. Object was moved withing the same
					// // category.
					// }
					else {
						/*
						 * transfer the repository-id for the dragged object and
						 * its children.
						 */
						String repositoryId = targetSyncData.getRepositoryId();
						/*
						 * Transfer the new repository-id
						 */
						compoundCommand.append(new SetCommand(EditingUtil.getInstance()
								.getNavigationEditingDomain(), ((SynchronizableObject) object)
								.getSynchronizationMetaData(),
								InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__REPOSITORY_ID,
								repositoryId));
						/*
						 * Set the state to "not added"
						 */
						compoundCommand.append(new SetCommand(EditingUtil.getInstance()
								.getNavigationEditingDomain(), ((SynchronizableObject) object)
								.getSynchronizationMetaData(),
								InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__SYNC_STATE,
								SynchronizationState.NOT_ADDED));
						/*
						 * Remove the Hash
						 */
						compoundCommand.append(new SetCommand(EditingUtil.getInstance()
								.getNavigationEditingDomain(), ((SynchronizableObject) object)
								.getSynchronizationMetaData(),
								InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH, null));
						/*
						 * Remove the url
						 */
						compoundCommand.append(new SetCommand(EditingUtil.getInstance()
								.getNavigationEditingDomain(), ((SynchronizableObject) object)
								.getSynchronizationMetaData(),
								InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__URL, null));

						TreeIterator<EObject> eAllContents = ((EObject) object).eAllContents();
						while (eAllContents.hasNext()) {
							EObject eObject = eAllContents.next();
							if (eObject instanceof SynchronizableObject) {
								/*
								 * Transfer the new repository-id
								 */
								compoundCommand
										.append(new SetCommand(
												EditingUtil.getInstance()
														.getNavigationEditingDomain(),
												((SynchronizableObject) eObject)
														.getSynchronizationMetaData(),
												InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__REPOSITORY_ID,
												repositoryId));
								/*
								 * Set the state to "not added"
								 */
								compoundCommand
										.append(new SetCommand(
												EditingUtil.getInstance()
														.getNavigationEditingDomain(),
												((SynchronizableObject) eObject)
														.getSynchronizationMetaData(),
												InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__SYNC_STATE,
												SynchronizationState.NOT_ADDED));
								/*
								 * Remove the Hash
								 */
								compoundCommand.append(new SetCommand(EditingUtil.getInstance()
										.getNavigationEditingDomain(),
										((SynchronizableObject) eObject)
												.getSynchronizationMetaData(),
										InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH,
										null));
								/*
								 * Remove the url
								 */
								compoundCommand.append(new SetCommand(EditingUtil.getInstance()
										.getNavigationEditingDomain(),
										((SynchronizableObject) eObject)
												.getSynchronizationMetaData(),
										InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__URL,
										null));
							}
						}
					}
				} else {
					/*
					 * Check wherether target is sync-object Only if the target
					 * is under remote-control we have to add the new
					 * SynchronizationMetaData
					 */
					if (targetSyncData != null) {
						SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE
								.createSynchronizationMetadata();
						metadata.setRepositoryId(targetSyncData.getRepositoryId());
						metadata.setSyncState(SynchronizationState.NOT_ADDED);

						compoundCommand
								.append(new SetCommand(
										EditingUtil.getInstance().getNavigationEditingDomain(),
										(EObject) object,
										InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA,
										metadata));

					}
				}
			}
		}
		compoundCommand.setLabel("Drag && Drop");
		return compoundCommand;
	}

}
