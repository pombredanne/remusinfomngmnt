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

import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.core.model.IdFactory;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CreateBinaryReferenceCommand extends CompoundCommand implements
		ICompoundableCreateCommand {

	private final IFile tmpFile;
	private final InformationUnit targetInfoUnit;
	private final EditingDomain editingDomain;
	private IFile targetFile;
	private final IPath path;
	private IProject targetProject;

	public CreateBinaryReferenceCommand(final IFile tmpFile, final InformationUnit targetInfoUnit,
			final EditingDomain editingDomain) {
		this.tmpFile = tmpFile;
		this.targetInfoUnit = targetInfoUnit;
		this.editingDomain = editingDomain;

		BinaryReference binaryReference = InfomngmntFactory.eINSTANCE.createBinaryReference();
		String id = IdFactory.createId();
		binaryReference.setId(id);
		this.path = new Path(id).addFileExtension(tmpFile.getFileExtension());
		binaryReference.setProjectRelativePath(this.path.toString());
		append(new CreateChildCommand(editingDomain, targetInfoUnit,
				InfomngmntPackage.Literals.INFORMATION_UNIT__BINARY_REFERENCES, binaryReference,
				Collections.EMPTY_LIST));
	}

	@Override
	public void execute() {
		super.execute();
		postExecute();
	}

	private void postExecute() {
		if (this.targetFile == null) {
			if (this.targetProject != null) {
				this.targetFile = this.targetProject.getFolder(ResourceUtil.BINARY_FOLDER).getFile(
						this.path);
			} else {
				IFile infoFile = (IFile) this.targetInfoUnit.getAdapter(IFile.class);
				this.targetFile = infoFile.getProject().getFolder(ResourceUtil.BINARY_FOLDER)
						.getFile(this.path);
			}
		}
		try {
			this.tmpFile.copy(this.targetFile.getFullPath(), true, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void undo() {
		super.undo();
		try {
			this.targetFile.delete(false, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void redo() {
		super.redo();
		postExecute();
	}

	@Override
	public void dispose() {
		try {
			this.tmpFile.delete(false, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.dispose();
	}

	public void setTarget(final IFile file) {
		this.targetProject = file.getProject();
	}

}
