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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.util.IdFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CopyInformationUnitObject implements Command {

	private final String oldId;
	private final InformationUnitListItem newItem;
	private final EditingDomain domain;
	private final EObject owner;
	private final List<IPath> createResources;
	private final IApplicationModel service;

	public CopyInformationUnitObject(final String oldId, final InformationUnitListItem newItem,
			final EditingDomain domain, final EObject newOwner) {
		this.oldId = oldId;
		this.newItem = newItem;
		this.domain = domain;
		this.owner = newOwner;
		this.createResources = new ArrayList<IPath>();
		this.service = InfomngmntEditPlugin.getPlugin().getServiceTracker().getService(
				IApplicationModel.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#canExecute()
	 */
	public boolean canExecute() {
		InformationUnitListItem itemById = this.service.getItemById(this.oldId, null);
		return itemById != null;
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
		// TODO Auto-generated method stub
		return null;
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
		this.createResources.clear();
		InformationUnitListItem itemById = this.service.getItemById(this.oldId, null);
		InformationUnit adapter = (InformationUnit) itemById.getAdapter(InformationUnit.class);
		InformationUnit copy = (InformationUnit) EcoreUtil.copy(adapter);

		IPath sourceProjectPath = ((IResource) adapter.getAdapter(IFile.class)).getProject()
				.getFullPath();
		List<InformationUnit> nodesWithBinaryreferences = getBinaryReferences(adapter);
		for (InformationUnit node : nodesWithBinaryreferences) {
			BinaryReference binaryReference = node.getBinaryReferences();
			IPath append = new Path("").append(sourceProjectPath)
					.append(ResourceUtil.BINARY_FOLDER).append(
							binaryReference.getProjectRelativePath());
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(append);
			if (file.exists()) {
				String newId = IdFactory.createId();
				IPath targetPath = new Path(new Path(this.newItem.getWorkspacePath()).segment(0))
						.append(ResourceUtil.BINARY_FOLDER).append(newId).addFileExtension(
								file.getFileExtension());
				try {
					IFile file2 = ResourcesPlugin.getWorkspace().getRoot().getFile(targetPath);
					InputStream contents = file.getContents();
					file2.create(contents, true, new NullProgressMonitor());
					this.createResources.add(targetPath);
					List<InformationUnit> nodesWithBinaryreferences2 = getBinaryReferences(copy);
					for (InformationUnit node2 : nodesWithBinaryreferences2) {
						BinaryReference binaryReference2 = node2.getBinaryReferences();
						if (binaryReference2.getId().equals(binaryReference.getId())) {
							binaryReference2.setProjectRelativePath(targetPath.lastSegment());
						}
					}
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		copy.setId(this.newItem.getId());
		InfomngmntEditPlugin.getPlugin().getEditService().saveObjectToResource(
				ResourcesPlugin.getWorkspace().getRoot().getFile(
						new Path(this.newItem.getWorkspacePath())), copy);
		this.createResources.add(new Path(this.newItem.getWorkspacePath()));

	}

	private List<InformationUnit> getBinaryReferences(final InformationUnit adapter) {
		List<InformationUnit> returnValue = new ArrayList<InformationUnit>();
		if (adapter.getBinaryReferences() != null) {
			returnValue.add(adapter);
		}
		EList<InformationUnit> childValues = adapter.getChildValues();
		for (InformationUnit informationUnit : childValues) {
			returnValue.addAll(getBinaryReferences(informationUnit));
		}

		return returnValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
	 */
	public Collection<?> getAffectedObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getDescription()
	 */
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getLabel()
	 */
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getResult()
	 */
	public Collection<?> getResult() {
		// TODO Auto-generated method stub
		return null;
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
		for (IPath path : this.createResources) {
			try {
				ResourcesPlugin.getWorkspace().getRoot().getFile(path).delete(true,
						new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
