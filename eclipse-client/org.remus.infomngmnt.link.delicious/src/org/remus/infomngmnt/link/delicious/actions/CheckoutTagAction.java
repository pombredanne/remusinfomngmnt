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

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.remote.SynchronizationWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckoutTagAction extends BaseSelectionListenerAction {

	public CheckoutTagAction() {
		super("Categories");
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

		SynchronizationWizard synchronizationWizard = new SynchronizationWizard(SynchronizationWizard.CHECKOUTMODE);
		synchronizationWizard.init(createChangeSet);
		WizardDialog wz = new WizardDialog(UIUtil.getPrimaryWindow().getShell(), synchronizationWizard);
		wz.open();

		super.run();
	}


}
