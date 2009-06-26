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

package org.remus.infomngmnt.image.ui;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.image.comments.ShapableInfoDelegate;
import org.remus.infomngmnt.image.gef.IEditingDomainHolder;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteCommentAction extends BaseSelectionListenerAction implements
		IEditingDomainHolder {

	private final InformationUnit unit;

	private EditingDomain editingDomain;

	protected DeleteCommentAction(final InformationUnit unit) {
		super("Delete");

		this.unit = unit;

	}

	@Override
	public void run() {
		List list = getStructuredSelection().toList();
		for (Object object : list) {
			if (((EditPart) object).getModel() instanceof ShapableInfoDelegate) {
				InformationUnit origInfoObject = ((ShapableInfoDelegate) ((EditPart) object)
						.getModel()).getOrigInfoObject();
				Command removeComment = RemoveCommand.create(this.editingDomain, origInfoObject);
				this.editingDomain.getCommandStack().execute(removeComment);
			}
		}
		super.run();
	}

	@Override
	protected boolean updateSelection(final IStructuredSelection selection) {
		List list = selection.toList();
		for (Object object : list) {
			if (!(object instanceof EditPart)
					|| !(((EditPart) object).getModel() instanceof ShapableInfoDelegate)) {
				return false;
			}
		}
		return true;
	}

	public EditingDomain getEditingDomain() {
		return this.editingDomain;
	}

	public void setEditingDomain(final EditingDomain editingDomain) {
		this.editingDomain = editingDomain;
	}

}
