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
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.IdFactory;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CopyInformationUnitCommand implements Command {

	public CopyInformationUnitCommand(final InformationUnitListItem affectedObject,
			final String targetPath) {
		this.affectedObject = affectedObject;
		this.targetPath = targetPath;
		this.affectedFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(affectedObject.getWorkspacePath()));
		this.targetFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(IPath.SEPARATOR + targetPath));
		this.sourcePath = affectedObject.getWorkspacePath();

		this.fullObject = (InformationUnit) affectedObject.getAdapter(InformationUnit.class);
		this.sourceBinaryReferences = new ArrayList<IFile>();
		this.targetBinaryReferences = new ArrayList<IFile>();
		this.sourceBinaryReferenceIds = new ArrayList<String>();
		InformationStructureRead read = InformationStructureRead.newSession(this.fullObject);
		List<BinaryReference> binaryReferences = read.getBinaryReferences();
		for (BinaryReference binaryReference : binaryReferences) {
			binaryReference.getProjectRelativePath();
			IFile sourceFile = ResourcesPlugin.getWorkspace().getRoot().getProject(
					new Path(this.sourcePath).segment(0)).getFolder(ResourceUtil.BINARY_FOLDER)
					.getFile(binaryReference.getProjectRelativePath());
			this.sourceBinaryReferences.add(sourceFile);
			this.sourceBinaryReferenceIds.add(binaryReference.getId());
			this.targetBinaryReferences.add(ResourcesPlugin.getWorkspace().getRoot().getProject(
					new Path(this.targetPath).segment(0)).getFolder(ResourceUtil.BINARY_FOLDER)
					.getFile(IdFactory.createId() + "." + sourceFile.getFileExtension()));
		}
	}

	private final InformationUnitListItem affectedObject;
	private final IFile affectedFile;
	private final IFile targetFile;
	private final String targetPath;
	private final String sourcePath;

	private final List<IFile> sourceBinaryReferences;
	private final List<String> sourceBinaryReferenceIds;
	private final List<IFile> targetBinaryReferences;
	private final InformationUnit fullObject;

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
			InformationUnit copy = (InformationUnit) EcoreUtil.copy(this.fullObject);
			copy.setId(IdFactory.createNewId(monitor));
			List<Object> allChildren = ModelUtil.getAllChildren(copy,
					InfomngmntPackage.Literals.BINARY_REFERENCE);
			for (int i = 0, n = this.sourceBinaryReferences.size(); i < n; i++) {
				this.sourceBinaryReferences.get(i).copy(
						this.targetBinaryReferences.get(i).getFullPath(), true, monitor);
				for (Object object : allChildren) {
					if (object instanceof BinaryReference) {
						if (((BinaryReference) object).getId().equals(
								this.sourceBinaryReferenceIds.get(i))) {
							((BinaryReference) object)
									.setProjectRelativePath(this.targetBinaryReferences.get(i)
											.getProjectRelativePath().toString());
						}

					}
				}
			}
			InfomngmntEditPlugin.getPlugin().getEditService().saveObjectToResource(this.targetFile,
					copy);
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
			this.targetFile.delete(true, monitor);
			for (int i = 0, n = this.targetBinaryReferences.size(); i < n; i++) {
				this.targetBinaryReferences.get(i).delete(true, monitor);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
