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

package org.remus.infomngmnt.core.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.StrictCompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.command.PasteFromClipboardCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.IdFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PasteInformationUnitCommand extends PasteFromClipboardCommand {

	private Map<String, InformationUnitListItem> oldNewIdMap;

	public PasteInformationUnitCommand(final EditingDomain domain, final Object owner,
			final Object feature, final int index, final boolean optimize) {
		super(domain, owner, feature, index, optimize);
	}

	public PasteInformationUnitCommand(final EditingDomain domain, final Object owner,
			final Object feature, final int index) {
		super(domain, owner, feature, index);
	}

	@Override
	protected boolean prepare() {
		this.oldNewIdMap = new HashMap<String, InformationUnitListItem>();
		// Create a strict compound command to do a copy and then add the result
		//
		this.command = new StrictCompoundCommand();

		// Create a command to copy the clipboard.
		//
		final Command copyCommand = CopyCommand.create(this.domain, this.domain.getClipboard());
		this.command.append(copyCommand);

		// Create a proxy that will create an add command.
		//
		this.command.append(new CommandWrapper() {
			protected Collection<Object> original;
			protected Collection<Object> copy;

			@Override
			protected Command createCommand() {
				this.original = PasteInformationUnitCommand.this.domain.getClipboard();
				this.copy = new ArrayList<Object>(copyCommand.getResult());

				// Use the original to do the add, but only if it's of the same
				// type as the copy.
				// This ensures that if there is conversion being done as part
				// of the copy,
				// as would be the case for a cross domain copy in the mapping
				// framework,
				// that we do actually use the converted instance.
				//
				if (this.original.size() == this.copy.size()) {
					for (Iterator<Object> i = this.original.iterator(), j = this.copy.iterator(); i
							.hasNext();) {
						Object originalObject = i.next();
						Object copyObject = j.next();
						if (originalObject.getClass() != copyObject.getClass()) {
							this.original = null;
							break;

						}
						if (originalObject instanceof Category) {
							recursivelyIdSet((Category) copyObject,
									PasteInformationUnitCommand.this.owner);
						}
						if (originalObject instanceof InformationUnitListItem) {
							prepareInfoUnit((InformationUnitListItem) copyObject,
									PasteInformationUnitCommand.this.owner);
						}
					}
				}

				CompoundCommand cc = new CompoundCommand();
				Command addCommand = AddCommand.create(PasteInformationUnitCommand.this.domain,
						PasteInformationUnitCommand.this.owner,
						PasteInformationUnitCommand.this.feature, this.copy,
						PasteInformationUnitCommand.this.index);

				cc.append(addCommand);
				Set<String> keySet = PasteInformationUnitCommand.this.oldNewIdMap.keySet();
				for (String string : keySet) {
					String oldId = string;
					InformationUnitListItem newItem = PasteInformationUnitCommand.this.oldNewIdMap
							.get(oldId);
					cc.append(new CopyInformationUnitObject(oldId, newItem,
							PasteInformationUnitCommand.this.domain,
							(EObject) PasteInformationUnitCommand.this.owner));
				}

				return cc;
			}

			@Override
			public void execute() {
				if (this.original != null) {
					PasteInformationUnitCommand.this.domain.setClipboard(this.copy);
				}
				super.execute();
			}

			@Override
			public void undo() {
				super.undo();
				if (this.original != null) {
					PasteInformationUnitCommand.this.domain.setClipboard(this.original);
				}
			}

			@Override
			public void redo() {
				if (this.original != null) {
					PasteInformationUnitCommand.this.domain.setClipboard(this.copy);
				}
				super.redo();
			}
		});

		boolean result;
		if (this.optimize) {
			// This will determine canExecute as efficiently as possible.
			//
			result = optimizedCanExecute();
		} else {
			// This will actually execute the copy command in order to check if
			// the add can execute.
			//
			result = this.command.canExecute();
		}

		return result;
	}

	private void recursivelyIdSet(final Category cat, final Object owner) {
		cat.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
		if (owner instanceof SynchronizableObject
				&& ((SynchronizableObject) owner).getSynchronizationMetaData() != null) {
			SynchronizationMetadata copy = (SynchronizationMetadata) EcoreUtil
					.copy(((SynchronizableObject) owner).getSynchronizationMetaData());
			copy.eUnset(InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH);
			copy.eUnset(InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__CURRENTLY_SYNCING);
			copy.eUnset(InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION);
			copy.setSyncState(SynchronizationState.NOT_ADDED);
			cat.eSet(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA,
					copy);
		}
		String newId = IdFactory.createNewId(null);
		cat.eSet(InfomngmntPackage.Literals.CATEGORY__ID, newId);
		EList<Category> children = cat.getChildren();
		for (Category category : children) {
			recursivelyIdSet(category, owner);
		}
		EList<InformationUnitListItem> informationUnit = cat.getInformationUnit();
		for (InformationUnitListItem informationUnitListItem : informationUnit) {
			prepareInfoUnit(informationUnitListItem, owner);
		}
	}

	private void prepareInfoUnit(final InformationUnitListItem informationUnitListItem,
			final Object owner) {
		informationUnitListItem
				.eUnset(InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA);
		if (owner instanceof SynchronizableObject
				&& ((SynchronizableObject) owner).getSynchronizationMetaData() != null) {
			SynchronizationMetadata copy = (SynchronizationMetadata) EcoreUtil
					.copy(((SynchronizableObject) owner).getSynchronizationMetaData());
			copy.eUnset(InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__HASH);
			copy.eUnset(InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__CURRENTLY_SYNCING);
			copy.eUnset(InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__LAST_SYNCHRONISATION);
			copy.setSyncState(SynchronizationState.NOT_ADDED);
			informationUnitListItem.eSet(
					InfomngmntPackage.Literals.SYNCHRONIZABLE_OBJECT__SYNCHRONIZATION_META_DATA,
					copy);
		}
		String newId = IdFactory.createNewId(null);
		if (owner instanceof Category) {
			IProject project = CategoryUtil.getProjectByCategory((Category) owner);
			IPath addFileExtension = new Path("/").append(project.getName()).append(newId)
					.addFileExtension(ResourceUtil.FILE_EXTENSION);
			informationUnitListItem.eSet(
					InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM__WORKSPACE_PATH,
					addFileExtension.toOSString());
		}
		this.oldNewIdMap.put(informationUnitListItem.getId(), informationUnitListItem);
		informationUnitListItem.eSet(InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__ID,
				newId);
	}

}
