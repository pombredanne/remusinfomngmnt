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
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.ModelUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteCategoryCommand implements Command {
	
	private final Category category;
	private final EditingDomain domain;
	
	private final List<InformationUnitListItem> affectedChildren;
	private final CompoundCommand cc;

	public DeleteCategoryCommand(final Category category, final EditingDomain domain) {
		this.category = category;
		this.domain = domain;
		Command deleteCommand = new DeleteCommand(domain,Collections.singleton(category));
		this.affectedChildren = ModelUtil.getAllChildren(
				category, InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM);
		this.cc = new CompoundCommand();
		this.cc.append(new DeleteInformationUnitCommand(this.affectedChildren,domain));
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
