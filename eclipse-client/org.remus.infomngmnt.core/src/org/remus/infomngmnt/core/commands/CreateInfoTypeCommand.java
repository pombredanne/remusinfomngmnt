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

package org.remus.infomngmnt.core.commands;

import java.util.Collections;

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationMetadata;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class CreateInfoTypeCommand extends CompoundCommand {

	private InformationUnit newInformationUnit;
	private final Category parentCategory;
	private final InformationUnitListItem createInformationUnitListItem;
	private final IFile newFile;

	public CreateInfoTypeCommand(final InformationUnit newInformationUnit,
			final Category parentCategory) {
		this.newInformationUnit = newInformationUnit;
		this.parentCategory = parentCategory;
		newInformationUnit.setId(new UniversalUniqueIdentifier().toString());

		this.createInformationUnitListItem = InfomngmntFactory.eINSTANCE
				.createInformationUnitListItem();
		this.newFile = CategoryUtil.getProjectByCategory(parentCategory).getFile(
				newInformationUnit.getId() + ".info");

		/*
		 * if the parent category is under remote control, we have to prepare
		 * this item for adding to the repository.
		 */
		if (parentCategory.getSynchronizationMetaData() != null) {
			SynchronizationMetadata metadata = InfomngmntFactory.eINSTANCE
					.createSynchronizationMetadata();
			metadata.setRepositoryId(parentCategory.getSynchronizationMetaData().getRepositoryId());
			metadata.setSyncState(SynchronizationState.NOT_ADDED);
			this.createInformationUnitListItem.setSynchronizationMetaData(metadata);
		}
		// transfer the needed information
		this.createInformationUnitListItem.setId(newInformationUnit.getId());
		this.createInformationUnitListItem.setLabel(newInformationUnit.getLabel());
		this.createInformationUnitListItem.setType(newInformationUnit.getType());
		this.createInformationUnitListItem
				.setWorkspacePath(this.newFile.getFullPath().toOSString());

		append(new CreateChildCommand(EditingUtil.getInstance().getNavigationEditingDomain(),
				parentCategory, InfomngmntPackage.Literals.CATEGORY__INFORMATION_UNIT,
				this.createInformationUnitListItem, Collections.EMPTY_LIST));

	}

	@Override
	public void execute() {
		super.execute();
		postExcecute();
	}

	@Override
	public void undo() {
		super.undo();
		postUndo();
	}

	@Override
	public void redo() {
		super.redo();
		postExcecute();
	}

	@Override
	public String getLabel() {
		return NLS.bind("New {0} - {1}", this.newInformationUnit.getType(), this.newInformationUnit
				.getLabel());
	}

	private void postExcecute() {
		EditingUtil.getInstance().saveObjectToResource(this.newFile, this.newInformationUnit);
	}

	private void postUndo() {
		try {
			// Save the current state.
			this.newInformationUnit = EditingUtil.getInstance().getObjectFromFile(this.newFile,
					InfomngmntPackage.Literals.INFORMATION_UNIT);
			this.newFile.delete(true, new NullProgressMonitor());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
