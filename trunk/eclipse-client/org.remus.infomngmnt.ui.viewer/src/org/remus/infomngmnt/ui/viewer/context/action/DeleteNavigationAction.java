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

package org.remus.infomngmnt.ui.viewer.context.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.ui.action.DeleteAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.commands.CommandFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteNavigationAction extends DeleteAction {

	public DeleteNavigationAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DeleteNavigationAction(final boolean removeAllReferences) {
		super(removeAllReferences);
		// TODO Auto-generated constructor stub
	}

	public DeleteNavigationAction(final EditingDomain domain, final boolean removeAllReferences) {
		super(domain, removeAllReferences);
		// TODO Auto-generated constructor stub
	}

	public DeleteNavigationAction(final EditingDomain domain) {
		super(domain);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Command createCommand(final Collection<?> selection) {
		CompoundCommand cc = new CompoundCommand();
		for (Object object : selection) {
			if (object instanceof Category) {
				//
				// cc.append(CommandFactory.DELETE_INFOUNIT(Arrays.asList(allInfoUnitItems),
				// getEditingDomain()));
				cc.append(CommandFactory.DELETE_SYNCHRONIZABLE_CATEGORY(
						(org.remus.infomngmnt.Category) object, getEditingDomain()));
			} else if (object instanceof InformationUnitListItem) {
				cc.append(CommandFactory.DELETE_INFOUNIT(Collections
						.singletonList((InformationUnitListItem) object), getEditingDomain()));
			}
		}
		return cc;
	}

	@Override
	public void run() {
		if (MessageDialog.openConfirm(Display.getDefault().getActiveShell(), "Confirm deletion",
				"Are you sure to delete the selected elements?")) {
			IRunnableWithProgress operation = new IRunnableWithProgress() {
				public void run(final IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					DeleteNavigationAction.super.run();
				}
			};
			try {
				new ProgressMonitorDialog(Display.getDefault().getActiveShell()).run(true, true,
						operation);
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
