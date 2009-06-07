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

package org.remus.infomngmnt.ui.handler;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AddToSyncIgnoreHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (currentSelection instanceof IStructuredSelection) {
			CompoundCommand cc = new CompoundCommand();
			List list = ((IStructuredSelection) currentSelection).toList();
			for (Object object : list) {
				if (object instanceof SynchronizableObject
						&& ((SynchronizableObject) object).getSynchronizationMetaData() != null) {
					cc.append(SetCommand.create(EditingUtil.getInstance()
							.getNavigationEditingDomain(), ((SynchronizableObject) object)
							.getSynchronizationMetaData(),
							InfomngmntPackage.Literals.SYNCHRONIZATION_METADATA__SYNC_STATE,
							SynchronizationState.IGNORED));
				}
			}
			cc.setLabel("Ignoring items for synchronization");
			EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(cc);
		}
		return null;
	}

}
