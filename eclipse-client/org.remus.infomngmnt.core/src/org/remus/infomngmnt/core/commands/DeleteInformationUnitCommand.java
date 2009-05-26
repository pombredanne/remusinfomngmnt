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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ItemProvider;

import org.remus.infomngmnt.BinaryReference;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.EditingUtil;
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

	private EditingDomain referenceDomain;

	private Map<EObject, Collection<EStructuralFeature.Setting>> usages;

	private static class InfoUnit2PathMapper {
		public InfoUnit2PathMapper(final IPath fullPath, final InformationUnit unit) {
			this.fullPath = fullPath;
			this.unit = unit;
		}

		private final IPath fullPath;
		private final InformationUnit unit;

		public IPath getFullPath() {
			return this.fullPath;
		}

		public InformationUnit getUnit() {
			return this.unit;
		}

	}

	public DeleteInformationUnitCommand(final List<InformationUnitListItem> items,
			final EditingDomain domain) {
		this.domain = domain;
		this.map = new HashMap<InformationUnitListItem, InfoUnit2PathMapper>();
		this.delegateCommand = new CompoundCommand();
		this.delegateCommand.append(new DeleteCommand(domain, items));
		for (InformationUnitListItem informationUnitListItem : items) {
			IPath pathInWorkspace = new Path(informationUnitListItem.getWorkspacePath());
			InformationUnit adapter = (InformationUnit) informationUnitListItem
					.getAdapter(InformationUnit.class);
			this.map
					.put(informationUnitListItem, new InfoUnit2PathMapper(pathInWorkspace, adapter));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#canExecute()
	 */
	public boolean canExecute() {
		return this.delegateCommand.canExecute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#canUndo()
	 */
	public boolean canUndo() {
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
		return this.delegateCommand.chain(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#dispose()
	 */
	public void dispose() {
		this.delegateCommand.dispose();
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

		this.delegateCommand.execute();
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
		this.referenceDomain = EditingUtil.getInstance().createNewEditingDomain();
		List<InformationUnit> relevantObjects = new ArrayList<InformationUnit>();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			IReferencedUnitStore service = InfomngmntEditPlugin.getPlugin().getService(
					IReferencedUnitStore.class);
			String[] referencedInfoUnitIds = service.getReferencedInfoUnitIds(infoUnit2PathMapper
					.getUnit().getId());
			for (String string : referencedInfoUnitIds) {
				InformationUnitListItem itemById = ApplicationModelPool.getInstance().getItemById(
						string, new NullProgressMonitor());
				InformationUnit adapter = (InformationUnit) itemById
						.getAdapter(InformationUnit.class);
				this.referenceDomain.getResourceSet().getResources().add(adapter.eResource());
			}
			this.referenceDomain.getResourceSet().getResources().add(
					infoUnit2PathMapper.getUnit().eResource());
			relevantObjects.add(infoUnit2PathMapper.getUnit());
		}
		this.usages = EcoreUtil.UsageCrossReferencer.findAll(relevantObjects, this.referenceDomain
				.getResourceSet());
		for (Map.Entry<EObject, Collection<EStructuralFeature.Setting>> entry : this.usages
				.entrySet()) {
			EObject eObject = entry.getKey();
			Collection<EStructuralFeature.Setting> settings = entry.getValue();
			for (EStructuralFeature.Setting setting : settings) {
				EObject referencingEObject = setting.getEObject();
				if (!relevantObjects.contains(referencingEObject)) {
					EStructuralFeature eStructuralFeature = setting.getEStructuralFeature();
					if (eStructuralFeature.isChangeable()) {
						if (eStructuralFeature.isMany()) {
							this.referenceDomain.getCommandStack().execute(
									RemoveCommand.create(this.referenceDomain, referencingEObject,
											eStructuralFeature, eObject));
						} else {

							/*
							 * We have here an excpetion.
							 */
							if (eStructuralFeature == InfomngmntPackage.Literals.LINK__TARGET) {
								EObject eContainer = referencingEObject.eContainer();
								ItemProvider itemProvider = new ItemProvider(
										(Collection<?>) eContainer
												.eGet(InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS));
								itemProvider.getChildren().remove(referencingEObject);
								Command command = SetCommand.create(this.referenceDomain,
										eContainer,
										InfomngmntPackage.Literals.INFORMATION_UNIT__LINKS,
										itemProvider.getChildren());
								this.referenceDomain.getCommandStack().execute(command);
								EditingUtil.getInstance().saveObjectToResource(eContainer);

							} else {
								this.referenceDomain.getCommandStack().execute(
										org.eclipse.emf.edit.command.SetCommand.create(
												this.referenceDomain, referencingEObject,
												eStructuralFeature, null));
							}

						}
					}
				}
				if (referencingEObject.eResource() != null) {
					EditingUtil.getInstance().saveObjectToResource(referencingEObject);
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
					EditingUtil.getInstance().saveObjectToResource((EObject) object);
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
		return this.delegateCommand.getAffectedObjects();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getDescription()
	 */
	public String getDescription() {
		return this.delegateCommand.getDescription();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getLabel()
	 */
	public String getLabel() {
		return this.delegateCommand.getLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#getResult()
	 */
	public Collection<?> getResult() {
		return this.delegateCommand.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		preExecute();
		this.delegateCommand.redo();
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
		this.delegateCommand.undo();
		Collection<InfoUnit2PathMapper> values = this.map.values();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(
					infoUnit2PathMapper.getFullPath());
			EditingUtil.getInstance().saveObjectToResource(file, infoUnit2PathMapper.getUnit());
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
