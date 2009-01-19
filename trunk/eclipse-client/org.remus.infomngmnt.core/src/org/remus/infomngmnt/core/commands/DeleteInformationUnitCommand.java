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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.extension.ISaveParticipant;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.services.ISaveParticipantExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteInformationUnitCommand implements Command {
	
	private final Map<InformationUnitListItem, InfoUnit2PathMapper> map;
	
	private final DeleteCommand delegateCommand;

	private final EditingDomain domain;

	private final ISaveParticipantExtensionService service;
	
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

	public DeleteInformationUnitCommand(final List<InformationUnitListItem> items, final EditingDomain domain) {
		this.domain = domain;
		this.map = new HashMap<InformationUnitListItem, InfoUnit2PathMapper>();
		this.delegateCommand = new DeleteCommand(domain,items);
		for (InformationUnitListItem informationUnitListItem : items) {
			IPath pathInWorkspace = new Path(informationUnitListItem.getWorkspacePath());
			InformationUnit adapter = (InformationUnit) informationUnitListItem.getAdapter(InformationUnit.class);
			this.map.put(informationUnitListItem, new InfoUnit2PathMapper(pathInWorkspace,adapter));
		}
		this.service = InfomngmntEditPlugin.getPlugin().getService(ISaveParticipantExtensionService.class);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#canExecute()
	 */
	public boolean canExecute() {
		return this.delegateCommand.canExecute();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#canUndo()
	 */
	public boolean canUndo() {
		return this.delegateCommand.canUndo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#chain(org.eclipse.emf.common.command.Command)
	 */
	public Command chain(final Command command) {
		return this.delegateCommand.chain(command);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#dispose()
	 */
	public void dispose() {
		this.delegateCommand.dispose();
		this.map.clear();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#execute()
	 */
	public void execute() {
		this.delegateCommand.execute();
		Collection<InfoUnit2PathMapper> values = this.map.values();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			this.service.fireEvent(ISaveParticipant.BEFORE_DELETE, infoUnit2PathMapper.getUnit());
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(infoUnit2PathMapper.getFullPath());
			try {
				file.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.service.fireEvent(ISaveParticipant.DELETED, infoUnit2PathMapper.getUnit());
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#getAffectedObjects()
	 */
	public Collection<?> getAffectedObjects() {
		return this.delegateCommand.getAffectedObjects();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#getDescription()
	 */
	public String getDescription() {
		return this.delegateCommand.getDescription();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#getLabel()
	 */
	public String getLabel() {
		return this.delegateCommand.getLabel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#getResult()
	 */
	public Collection<?> getResult() {
		return this.delegateCommand.getResult();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#redo()
	 */
	public void redo() {
		this.delegateCommand.redo();
		Collection<InfoUnit2PathMapper> values = this.map.values();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			this.service.fireEvent(ISaveParticipant.BEFORE_DELETE, infoUnit2PathMapper.getUnit());
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(infoUnit2PathMapper.getFullPath());
			try {
				file.delete(true, new NullProgressMonitor());
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.service.fireEvent(ISaveParticipant.DELETED, infoUnit2PathMapper.getUnit());
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.common.command.Command#undo()
	 */
	public void undo() {
		this.delegateCommand.undo();
		Collection<InfoUnit2PathMapper> values = this.map.values();
		for (InfoUnit2PathMapper infoUnit2PathMapper : values) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(infoUnit2PathMapper.getFullPath());
			EditingUtil.getInstance().saveObjectToResource(file, infoUnit2PathMapper.getUnit());
			this.service.fireEvent(ISaveParticipant.CREATED, infoUnit2PathMapper.getUnit());
		}
	}

}
