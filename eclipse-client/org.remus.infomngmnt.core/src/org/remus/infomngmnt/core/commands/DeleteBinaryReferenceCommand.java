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
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteBinaryReferenceCommand extends CompoundCommand {

	private final InformationUnit infoObject;
	private final IFile oldFile;
	private final IProject project;
	private final BinaryReference oldValue;
	private final IFile cachedFile;

	public DeleteBinaryReferenceCommand(final BinaryReference reference,
			final EditingDomain editingDomain) {
		this.oldValue = reference;
		this.infoObject = (InformationUnit) this.oldValue.eContainer();
		this.project = ((IResource) this.infoObject.getAdapter(IFile.class)).getProject();
		this.oldFile = this.project.getFolder(ResourceUtil.BINARY_FOLDER).getFile(
				this.oldValue.getProjectRelativePath());
		this.cachedFile = this.project.getFolder(ResourceUtil.CMDSTACK_FOLDER).getFile(
				this.oldValue.getProjectRelativePath());
		append(DeleteCommand.create(editingDomain, Collections.singleton(this.oldValue)));
	}

	@Override
	public void execute() {
		super.execute();
		postExecute();
	}

	private void postExecute() {
		try {
			if (this.cachedFile.exists()) {
				this.cachedFile.delete(true, null);
			}
			this.oldFile.copy(this.cachedFile.getFullPath(), true, null);
			this.oldFile.delete(true, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void undo() {
		super.undo();
		postUndo();
	}

	private void postUndo() {
		try {
			if (this.oldFile.exists()) {
				this.oldFile.delete(true, null);
			}
			this.cachedFile.copy(this.oldFile.getFullPath(), true, null);
			this.cachedFile.delete(true, null);
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
		// if (this.cachedFile.exists()) {
		// try {
		// this.cachedFile.delete(true, null);
		// } catch (CoreException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		super.dispose();
	}

}
