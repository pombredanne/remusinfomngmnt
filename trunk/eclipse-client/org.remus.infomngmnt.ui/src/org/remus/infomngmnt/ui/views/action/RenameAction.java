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

package org.remus.infomngmnt.ui.views.action;

import java.util.Collection;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.action.CommandActionHandler;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RenameAction extends CommandActionHandler {

	public RenameAction() {
		super(null);
		setText(WorkbenchMessages.Workbench_rename);
		setToolTipText(WorkbenchMessages.Workbench_renameToolTip);
		setActionDefinitionId("org.eclipse.ui.edit.rename");
	}

	/**
	 * @since 2.1.0
	 */
	public void setActiveWorkbenchPart(IWorkbenchPart workbenchPart)
	{
		if (workbenchPart instanceof IEditingDomainProvider)
		{
			this.domain = ((IEditingDomainProvider)workbenchPart).getEditingDomain();
		}
	}

	@Override
	public Command createCommand(Collection<?> selection) {
		if (selection.size() == 1) {
			for (Object object : selection) {
				if (object instanceof Category)
					return new RenameCommand(
							this.domain,(EObject)object,InfomngmntPackage.Literals.CATEGORY__LABEL);
				else if (object instanceof InformationUnitListItem) {
					return new RenameCommand(
							this.domain,(EObject)object,InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__LABEL);
				}
			}
		}
		return super.createCommand(selection);
	}

	@Override
	public boolean updateSelection(IStructuredSelection selection) {
		return super.updateSelection(selection);
	}

	@Override
	public void run() {
		boolean closeAllEditors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().saveAllEditors(true);
		if (closeAllEditors &&
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getDirtyEditors().length == 0) {
			super.run();
		}
	}

	/**
	 * @author Tom Seidel <tom.seidel@remus-software.org>
	 */
	class RenameCommand extends SetCommand {

		public RenameCommand(EditingDomain domain, EObject owner,
				EStructuralFeature feature) {
			super(domain, owner, feature, null);
			setLabel("Rename");
		}

		@Override
		public boolean doCanUndo() {
			return super.doCanUndo() && this.value != null;
		}

		@Override
		public void doExecute() {
			RenameDialog dialog = new RenameDialog((String)this.owner.eGet(this.feature));
			if (dialog.open() == IDialogConstants.OK_ID) {
				this.value = dialog.getNewName();
				renameInfoUnit((String) this.value);
				super.doExecute();
			}
		}

		@Override
		public void doUndo() {
			super.doUndo();
			renameInfoUnit((String) this.oldValue);
		}
		@Override
		public void doRedo() {
			renameInfoUnit((String) this.value);
			super.doRedo();
		}

		private void renameInfoUnit(String pValue) {
			if (this.owner instanceof InformationUnitListItem) {
				((InformationUnitListItem) this.owner).getWorkspacePath();
				InformationUnit objectFromFile = EditingUtil.getInstance().getObjectFromFile(
						ResourcesPlugin.getWorkspace().getRoot().getFile(
								new Path(((InformationUnitListItem) this.owner).getWorkspacePath())),
								InfomngmntPackage.Literals.INFORMATION_UNIT);
				objectFromFile.setLabel(pValue);
				EditingUtil.getInstance().saveObjectToResource(objectFromFile);
			}
		}
	}


}
