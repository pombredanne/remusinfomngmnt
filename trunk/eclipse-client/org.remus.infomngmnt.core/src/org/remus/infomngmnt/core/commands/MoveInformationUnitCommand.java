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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MoveInformationUnitCommand implements Command {

	public MoveInformationUnitCommand(final InformationUnitListItem affectedObject,
			final String targetPath) {
		this.targetPath = targetPath;
		this.affectedFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(affectedObject.getWorkspacePath()));

		this.sourcePath = affectedObject.getWorkspacePath();
		this.targetFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(IPath.SEPARATOR + targetPath));

		InformationUnit fullObject = (InformationUnit) affectedObject
				.getAdapter(InformationUnit.class);
		EList<BinaryReference> binaryReferences = fullObject.getBinaryReferences();
		this.sourceBinaryReferences = new ArrayList<IFile>();
		this.targetBinaryReferences = new ArrayList<IFile>();
		for (BinaryReference binaryReference : binaryReferences) {
			binaryReference.getProjectRelativePath();
			this.sourceBinaryReferences.add(ResourcesPlugin.getWorkspace().getRoot().getProject(
					new Path(this.sourcePath).segment(0)).getFolder(ResourceUtil.BINARY_FOLDER)
					.getFile(binaryReference.getProjectRelativePath()));
			this.targetBinaryReferences.add(ResourcesPlugin.getWorkspace().getRoot().getProject(
					new Path(this.targetPath).segment(0)).getFolder(ResourceUtil.BINARY_FOLDER)
					.getFile(binaryReference.getProjectRelativePath()));
		}
	}

	private final IFile affectedFile;
	private final IFile targetFile;
	private final String targetPath;
	private final String sourcePath;

	private final List<IFile> sourceBinaryReferences;
	private final List<IFile> targetBinaryReferences;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#canExecute()
	 */
	public boolean canExecute() {
		return this.affectedFile.exists()
				&& !this.targetFile.exists()
				&& ResourcesPlugin.getWorkspace().getRoot().getProject(
						new Path(this.targetPath).segment(0)).exists();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#canUndo()
	 */
	public boolean canUndo() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command
	 * .Command)
	 */
	public Command chain(final Command command) {
		CompoundCommand compoundCommand = new CompoundCommand();
		compoundCommand.append(this);
		compoundCommand.append(command);
		return compoundCommand;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
		try {
			NullProgressMonitor monitor = new NullProgressMonitor();
			if (this.affectedFile.getLocationURI().getScheme().equals(
					this.targetFile.getLocationURI().getScheme())) {
				this.affectedFile.move(new Path(IPath.SEPARATOR + this.targetPath), true, monitor);
				for (int i = 0, n = this.sourceBinaryReferences.size(); i < n; i++) {
					this.sourceBinaryReferences.get(i).move(
							this.targetBinaryReferences.get(i).getFullPath(), true, monitor);
				}
			} else {
				this.targetFile.create(this.affectedFile.getContents(), true, monitor);
				this.affectedFile.delete(true, monitor);
				for (int i = 0, n = this.sourceBinaryReferences.size(); i < n; i++) {
					this.targetBinaryReferences.get(i).create(
							this.sourceBinaryReferences.get(i).getContents(), true, monitor);
					this.sourceBinaryReferences.get(i).delete(true, monitor);
				}
			}

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
	 */
	public Collection<?> getAffectedObjects() {
		return Collections.EMPTY_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getDescription()
	 */
	public String getDescription() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getLabel()
	 */
	public String getLabel() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getResult()
	 */
	public Collection<?> getResult() {
		return Collections.EMPTY_LIST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		execute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#undo()
	 */
	public void undo() {
		try {
			NullProgressMonitor monitor = new NullProgressMonitor();
			if (this.affectedFile.getLocationURI().getScheme().equals(
					this.targetFile.getLocationURI().getScheme())) {
				this.targetFile.move(new Path(this.sourcePath), true, monitor);
				for (int i = 0, n = this.targetBinaryReferences.size(); i < n; i++) {
					this.targetBinaryReferences.get(i).move(
							this.sourceBinaryReferences.get(i).getFullPath(), true, monitor);
				}
			} else {
				this.affectedFile.create(this.targetFile.getContents(), true, monitor);
				this.targetFile.delete(true, monitor);
				for (int i = 0, n = this.targetBinaryReferences.size(); i < n; i++) {
					this.sourceBinaryReferences.get(i).create(
							this.targetBinaryReferences.get(i).getContents(), true, monitor);
					this.targetBinaryReferences.get(i).delete(true, monitor);
				}

			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
