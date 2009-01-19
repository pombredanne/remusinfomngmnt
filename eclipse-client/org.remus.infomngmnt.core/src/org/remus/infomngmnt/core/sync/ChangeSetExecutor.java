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

package org.remus.infomngmnt.core.sync;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.AddModelElement;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.extension.ISaveParticipant;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.services.ISaveParticipantExtensionService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ChangeSetExecutor {
	
	private ChangeSet changeSet;
	
	/**
	 * Performs a checkout.
	 * @param ownedElements
	 */
	public void performCheckout(final EList<DiffElement> ownedElements) {
		for (DiffElement diffElement : ownedElements) {
			if (diffElement instanceof AddModelElement) {
				EObject rightElement = ((AddModelElement) diffElement).getRightElement();
				EList<ChangeSetItem> changeSetItems = this.changeSet.getChangeSetItems();
				for (ChangeSetItem changeSetItem : changeSetItems) {
					if (changeSetItem.getRemoteConvertedContainer().getId().equals(((Category) rightElement).getId())) {
						Category remoteConvertedContainer = changeSetItem.getRemoteConvertedContainer();
						this.changeSet.getTargetCategory().getChildren().add(remoteConvertedContainer);
						List<InformationUnitListItem> allChildren = ModelUtil.getAllChildren(remoteConvertedContainer, InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM);
						for (InformationUnitListItem informationUnitListItem : allChildren) {
							InformationUnit informationUnit2 = changeSetItem.getRemoteFullObjectMap().get(informationUnitListItem);
							if (informationUnit2 == null) {
								InformationUnit informationUnit = this.changeSet.getRepository().getRepositoryImplementation().getFullObject(informationUnitListItem);
								informationUnit.setId(informationUnitListItem.getId());
								informationUnit.setType(informationUnitListItem.getId());
							}
							IFile newFile = CategoryUtil.getProjectByCategory(remoteConvertedContainer).getFile(informationUnitListItem.getId() + ".info");
							EditingUtil.getInstance().saveObjectToResource(newFile, informationUnit2);
							informationUnitListItem.setWorkspacePath(newFile.getFullPath().toOSString());
							InfomngmntEditPlugin.getPlugin().getService(ISaveParticipantExtensionService.class)
							.fireEvent(ISaveParticipant.CREATED, informationUnit2);
						}
					}
				}
			}
			performCheckout(diffElement.getSubDiffElements());
		}
	}

	public ChangeSet getChangeSet() {
		return this.changeSet;
	}

	public void setChangeSet(final ChangeSet changeSet) {
		this.changeSet = changeSet;
	}


}
