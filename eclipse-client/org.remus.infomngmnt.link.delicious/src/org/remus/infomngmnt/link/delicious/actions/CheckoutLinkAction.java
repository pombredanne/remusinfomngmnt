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

package org.remus.infomngmnt.link.delicious.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.commands.CommandFactory;
import org.remus.infomngmnt.ui.remote.GeneralChangeSetProcessingDialog;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckoutLinkAction extends BaseSelectionListenerAction {
	public CheckoutLinkAction() {
		super("Link(s)");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FOLDER));
	}


	@Override
	public void run() {
		ChangeSet createChangeSet = InfomngmntFactory.eINSTANCE.createChangeSet();
		List list = getStructuredSelection().toList();
		String repositoryId = null;
		List<RemoteContainer> remoteContainers = new ArrayList<RemoteContainer>();
		for (Object object : list) {
			RemoteContainer remoteContainer = null;
			if (repositoryId == null) {
				repositoryId = ((RemoteObject) object).getRepositoryTypeId();
			}
			if (object instanceof RemoteContainer) {
				remoteContainer = (RemoteContainer) object;
			} else if (object instanceof RemoteObject) {
				remoteContainer = (RemoteContainer) ((RemoteObject) object).eContainer();
			}
			if (!remoteContainers.contains(remoteContainer)) {
				remoteContainers.add(remoteContainer);
			}
		}

		for (RemoteContainer remoteContainer : remoteContainers) {
			ChangeSetItem createChangeSetItem = InfomngmntFactory.eINSTANCE.createChangeSetItem();
			createChangeSetItem.setRemoteOriginalObject(remoteContainer);
			createChangeSet.getChangeSetItems().add(createChangeSetItem);
		}

		IRepository itemById = UIPlugin.getDefault().getService(IRepositoryExtensionService.class).getItemById(repositoryId);
		itemById.applyChangeSet(createChangeSet);
		
		GeneralChangeSetProcessingDialog diag = new GeneralChangeSetProcessingDialog(UIUtil.getPrimaryWindow().getShell(),createChangeSet);
		if (diag.open() == IDialogConstants.OK_ID) {
			
			Category category = CategoryUtil.findCategory(diag.getCategoryString(),true);
			createChangeSet.setTargetCategory(category);
			
			EList<ChangeSetItem> changeSetItems = createChangeSet.getChangeSetItems();
			for (ChangeSetItem changeSetItem : changeSetItems) {
				Category remoteConvertedContainer = changeSetItem.getRemoteConvertedContainer();
				if (changeSetItem.getSyncActionMap().get(remoteConvertedContainer) == SynchronizationAction.ADD_LOCAL) {
					Command createCategory = CommandFactory.CREATE_CATEGORY(
							createChangeSet.getTargetCategory(), 
							remoteConvertedContainer, EditingUtil.getInstance().getNavigationEditingDomain());
					EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack().execute(createCategory);
				}
			}
		}

//		CheckOutMultipeInfoUnitsWizard wizard = new CheckOutMultipeInfoUnitsWizard() {
//			@Override
//			protected String getInfoTypeId() {
//				return LinkActivator.LINK_INFO_ID;
//			}
//		};
//		wizard.init(UIUtil.getPrimaryWindow().getWorkbench(), getStructuredSelection());
//		WizardDialog wizardDialog = new WizardDialog(
//				UIUtil.getPrimaryWindow().getShell(),wizard);
//		wizardDialog.open();
		super.run();
	}
}
