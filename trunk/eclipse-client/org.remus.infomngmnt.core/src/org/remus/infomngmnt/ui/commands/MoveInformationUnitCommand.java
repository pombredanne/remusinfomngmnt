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

package org.remus.infomngmnt.ui.commands;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;

import org.remus.infomngmnt.InformationUnitListItem;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class MoveInformationUnitCommand implements Command {

	public MoveInformationUnitCommand(InformationUnitListItem affectedObject,
			String targetPath) {
		this.affectedObject = affectedObject;
		this.targetPath = targetPath;
		this.affectedFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(affectedObject.getWorkspacePath()));
		this.affectedFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(affectedObject.getWorkspacePath()).removeFileExtension());
		this.sourcePath = affectedObject.getWorkspacePath();
		this.targetFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				new Path(IPath.SEPARATOR + targetPath));
		this.targetFolder = ResourcesPlugin.getWorkspace().getRoot().getFolder(
				new Path(IPath.SEPARATOR + targetPath).removeFileExtension());
	}

	private final IFile affectedFile;
	private final IFolder affectedFolder;
	private final IFile targetFile;
	private final IFolder targetFolder;
	private final String targetPath;
	private final String sourcePath;
	private final InformationUnitListItem affectedObject;

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#canExecute()
	 */
	public boolean canExecute() {
		return this.affectedFile.exists()
		&& !this.targetFile.exists()
		&& ResourcesPlugin.getWorkspace().getRoot().getProject(
				new Path(this.targetPath).segment(0)).exists();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#canUndo()
	 */
	public boolean canUndo() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command.Command)
	 */
	public Command chain(Command command) {
		CompoundCommand compoundCommand = new CompoundCommand();
		compoundCommand.append(this);
		compoundCommand.append(command);
		return compoundCommand;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
		try {
			NullProgressMonitor monitor = new NullProgressMonitor();
			this.affectedFile.move(new Path(IPath.SEPARATOR + this.targetPath), true, monitor);
			if (this.affectedFolder.exists()) {
				this.affectedFile.move(new Path(this.targetPath).removeFileExtension(), true, monitor);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
	 */
	public Collection<?> getAffectedObjects() {
		return Collections.EMPTY_LIST;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#getDescription()
	 */
	public String getDescription() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#getLabel()
	 */
	public String getLabel() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#getResult()
	 */
	public Collection<?> getResult() {
		return Collections.EMPTY_LIST;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		execute();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#undo()
	 */
	public void undo() {
		try {
			NullProgressMonitor monitor = new NullProgressMonitor();
			this.targetFile.move(new Path(this.sourcePath), true, monitor);
			if (this.targetFolder.exists()) {
				this.targetFolder.move(new Path(this.sourcePath).removeFileExtension(), true, monitor);
			}
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
