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
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.core.util.ModelUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteSynchronizedCategoryCommand implements Command {

	private final Category category;
	private final EditingDomain domain;

	private final List<InformationUnitListItem> affectedChildren;
	private final CompoundCommand cc;

	public DeleteSynchronizedCategoryCommand(final Category category, final EditingDomain domain) {
		this.category = category;
		this.domain = domain;
		CompoundCommand deleteCommand = new CompoundCommand();
		if (category.eContainer() != null
				&& ((Category) category.eContainer()).getSynchronizationMetaData() != null
				&& category.getSynchronizationMetaData() != null
				&& category.getSynchronizationMetaData().getSyncState() != SynchronizationState.NOT_ADDED) {
			deleteCommand.append(SetCommand.create(domain, category.getSynchronizationMetaData(),
					InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__SYNC_STATE,
					SynchronizationState.LOCAL_DELETED));
			List<Object> allChildren = ModelUtil.getAllChildren(category,
					InfomngmntPackage.Literals.CATEGORY);
			for (Object object : allChildren) {
				deleteCommand.append(new DeleteSynchronizedCategoryCommand((Category) object,
						domain));
			}
		} else {
			deleteCommand.append(new DeleteCommand(domain, Collections.singleton(category)));
		}

		this.affectedChildren = ModelUtil.getAllChildren(category,
				InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM);
		this.cc = new CompoundCommand();
		if (this.affectedChildren.size() > 0) {
			this.cc.append(CommandFactory.DELETE_INFOUNIT(this.affectedChildren, domain));
		}
		this.cc.append(deleteCommand);
	}

	public boolean canExecute() {
		return this.cc.canExecute();
	}

	public boolean canUndo() {
		return this.cc.canUndo();
	}

	public Command chain(final Command command) {
		return this.cc.chain(command);
	}

	public void dispose() {
		this.cc.dispose();
	}

	public void execute() {
		this.cc.execute();
	}

	public Collection<?> getAffectedObjects() {
		return this.cc.getAffectedObjects();
	}

	public String getDescription() {
		return this.cc.getDescription();
	}

	public String getLabel() {
		return this.cc.getLabel();
	}

	public Collection<?> getResult() {
		return this.cc.getResult();
	}

	public void redo() {
		this.cc.redo();
	}

	public void undo() {
		this.cc.undo();
	}

}
