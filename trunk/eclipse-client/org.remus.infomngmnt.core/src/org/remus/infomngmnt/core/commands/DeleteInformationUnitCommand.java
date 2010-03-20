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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Link;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.edit.DisposableEditingDomain;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IReferencedUnitStore;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteInformationUnitCommand implements Command {

	private final Map<InformationUnitListItem, InfoUnit2PathMapper> map;

	private Map<IFile, IFile> binaries;

	private final CompoundCommand delegateCommand;

	private final EditingDomain domain;

	private DisposableEditingDomain referenceDomain;

	private Map<EObject, Collection<EStructuralFeature.Setting>> usages;

	private final IApplicationModel applicationService;

	private static class InfoUnit2PathMapper {

		public InfoUnit2PathMapper(final IPath fullPath,
				final InformationUnitListItem informationUnitListItem) {
			this.fullPath = fullPath;
			this.informationUnitListItem = informationUnitListItem;

		}

		private final InformationUnitListItem informationUnitListItem;
		private final IPath fullPath;
		private InformationUnit unit;

		public IPath getFullPath() {
			return this.fullPath;
		}

		public InformationUnit getUnit() {
			if (this.unit == null) {
				this.unit = (InformationUnit) this.informationUnitListItem
						.getAdapter(InformationUnit.class);
			}
			return this.unit;
		}

	}

	public DeleteInformationUnitCommand(final List<InformationUnitListItem> items,
			final EditingDomain domain) {
		this(items, domain, true);
	}

	public DeleteInformationUnitCommand(final List<InformationUnitListItem> items,
			final EditingDomain domain, final boolean checkForSyncState) {
		this.domain = domain;
		this.map = new HashMap<InformationUnitListItem, InfoUnit2PathMapper>();
		this.delegateCommand = new CompoundCommand();
		this.applicationService = InfomngmntEditPlugin.getPlugin().getServiceTracker().getService(
				IApplicationModel.class);
		if (checkForSyncState) {
			for (InformationUnitListItem informationUnitListItem : items) {
				if (informationUnitListItem.getSynchronizationMetaData() != null
						&& informationUnitListItem.getSynchronizationMetaData().getSyncState() != SynchronizationState.NOT_ADDED
						&& informationUnitListItem.getSynchronizationMetaData().getSyncState() != SynchronizationState.IGNORED) {
					this.delegateCommand.append(SetCommand.create(domain, informationUnitListItem
							.getSynchronizationMetaData(),
							InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__SYNC_STATE,
							SynchronizationState.LOCAL_DELETED));
				} else {
					this.delegateCommand.append(DeleteCommand.create(domain, Collections
							.singleton(informationUnitListItem)));
				}
			}
		} else {
			this.delegateCommand.append(DeleteCommand.create(domain, items));

		}
		for (InformationUnitListItem informationUnitListItem : items) {
			/*
			 * If we have a invalid datastructure here is thrown an exception
			 * and the user cannot delete invalid structures. we have to bypass
			 * this exception
			 */
			try {
				IPath pathInWorkspace = new Path(informationUnitListItem.getWorkspacePath());
				this.map.put(informationUnitListItem, new InfoUnit2PathMapper(pathInWorkspace,
						informationUnitListItem));
			} catch (Exception e) {
				// do nothing
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#canExecute()
	 */
	public boolean canExecute() {
		if (this.delegateCommand == null || this.delegateCommand.getCommandList().size() == 0) {
			return true;
		}
		return this.delegateCommand.canExecute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#canUndo()
	 */
	public boolean canUndo() {
		if (this.delegateCommand == null) {
			return true;
		}
		return this.delegateCommand.canUndo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command
	 * .Command)
	 */
	public Command chain(final Command command) {
		if (this.delegateCommand == null) {
			return null;
		}
		return this.delegateCommand.chain(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#dispose()
	 */
	public void dispose() {
		if (this.delegateCommand != null) {
			this.delegateCommand.dispose();
		}
		this.map.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
		/*
		 * At first we have to find all items that are referencing this iu:
		 */
		preExecute();
		if (this.delegateCommand != null) {
			this.delegateCommand.execute();
		}
		Collection<InfoUnit2PathMapper> values = this.map.values();
		this.binaries = new HashMap<IFile, IFile>();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {

			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
					infoUnit2PathMapper.getFullPath());
			try {
				file.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		postExecute();
	}

	private void preExecute() {
		Collection<InfoUnit2PathMapper> values = this.map.values();
		if (this.referenceDomain != null) {
			this.referenceDomain.dispose();
		}
		this.referenceDomain = InfomngmntEditPlugin.getPlugin().getEditService()
				.createNewEditingDomain();
		List<InformationUnit> relevantObjects = new ArrayList<InformationUnit>();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			IReferencedUnitStore service = InfomngmntEditPlugin.getPlugin().getService(
					IReferencedUnitStore.class);
			if (infoUnit2PathMapper.getUnit() != null) {
				String[] referencedInfoUnitIds = service
						.getReferencedInfoUnitIds(infoUnit2PathMapper.getUnit().getId());
				for (String string : referencedInfoUnitIds) {
					InformationUnitListItem itemById = this.applicationService.getItemById(string,
							new NullProgressMonitor());
					InformationUnit adapter = (InformationUnit) itemById
							.getAdapter(InformationUnit.class);
					EList<Link> links = (adapter).getLinks();
					List<Link> links2Remove = new ArrayList<Link>();
					for (Link link : links) {
						if (link.getLocalInformationUnit() != null
								&& link.getLocalInformationUnit().equals(
										infoUnit2PathMapper.getUnit().getId())) {
							links2Remove.add(link);
						}
					}
					if (links2Remove.size() > 0) {
						(adapter).getLinks().removeAll(links2Remove);
						InfomngmntEditPlugin.getPlugin().getEditService().saveObjectToResource(
								adapter);
					}
				}

			}
		}

	}

	private void postUndo() {
		while (this.referenceDomain.getCommandStack().getUndoCommand() != null) {
			Command undoCommand = this.referenceDomain.getCommandStack().getUndoCommand();
			Collection<?> affectedObjects = undoCommand.getAffectedObjects();
			this.referenceDomain.getCommandStack().undo();
			for (Object object : affectedObjects) {
				if (object instanceof EObject) {
					InfomngmntEditPlugin.getPlugin().getEditService().saveObjectToResource(
							(EObject) object);
				}
			}
		}

	}

	private void postExecute() {
		Collection<InfoUnit2PathMapper> values = this.map.values();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			InformationUnit informationUnit = infoUnit2PathMapper.getUnit();
			List<Object> allChildren = ModelUtil.getAllChildren(informationUnit,
					InfomngmntPackage.Literals.BINARY_REFERENCE);
			for (Object object : allChildren) {
				if (object instanceof BinaryReference) {
					((BinaryReference) object).getProjectRelativePath();
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getFile(
							infoUnit2PathMapper.getFullPath()).getProject();
					IFile origFile = project.getFolder(ResourceUtil.BINARY_FOLDER).getFile(
							((BinaryReference) object).getProjectRelativePath());
					IFile cachedFile = project.getFolder(ResourceUtil.CMDSTACK_FOLDER).getFile(
							((BinaryReference) object).getProjectRelativePath());
					this.binaries.put(origFile, cachedFile);
				}
			}

		}

		Set<IFile> keySet = this.binaries.keySet();
		for (IFile origFile : keySet) {
			IFile cachedFile = this.binaries.get(origFile);
			try {
				if (cachedFile.exists()) {
					cachedFile.delete(true, null);
				}
				origFile.copy(cachedFile.getFullPath(), true, null);
				origFile.delete(true, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
	 */
	public Collection<?> getAffectedObjects() {
		if (this.delegateCommand == null) {
			return Collections.EMPTY_LIST;
		}
		return this.delegateCommand.getAffectedObjects();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getDescription()
	 */
	public String getDescription() {
		if (this.delegateCommand == null) {
			return "";
		}
		return this.delegateCommand.getDescription();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getLabel()
	 */
	public String getLabel() {
		if (this.delegateCommand == null) {
			return "";
		}
		return this.delegateCommand.getLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getResult()
	 */
	public Collection<?> getResult() {
		if (this.delegateCommand == null) {
			return Collections.EMPTY_LIST;
		}
		return this.delegateCommand.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		preExecute();
		if (this.delegateCommand != null) {
			this.delegateCommand.redo();
		}
		Collection<InfoUnit2PathMapper> values = this.map.values();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
					infoUnit2PathMapper.getFullPath());
			try {
				file.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		postExecute();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#undo()
	 */
	public void undo() {
		preUndo();
		if (this.delegateCommand != null) {
			this.delegateCommand.undo();
		}
		Collection<InfoUnit2PathMapper> values = this.map.values();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
					infoUnit2PathMapper.getFullPath());
			InfomngmntEditPlugin.getPlugin().getEditService().saveObjectToResource(file,
					infoUnit2PathMapper.getUnit());
		}
		postUndo();
	}

	private void preUndo() {
		Set<IFile> keySet = this.binaries.keySet();
		for (IFile origFile : keySet) {
			IFile cachedFile = this.binaries.get(origFile);
			try {
				if (origFile.exists()) {
					origFile.delete(true, null);
				}
				cachedFile.copy(origFile.getFullPath(), true, null);
				cachedFile.delete(true, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
