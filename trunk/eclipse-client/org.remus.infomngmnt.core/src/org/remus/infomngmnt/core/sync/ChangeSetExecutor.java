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

import java.util.Collections;
import java.util.List;

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.metamodel.AddModelElement;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.match.service.MatchService;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
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

	private static final String COMPARE_FOLDER = "compare";
	private ChangeSet changeSet;
	private IPath remotePath;
	private IPath localPath;
	private DiffModel diffModel;

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


	public void prepareDiff(final Category pLocalTargetCateogory) {
		Category localTargetCategory = InfomngmntFactory.eINSTANCE.createCategory();
		Category tmpRemoteRootCategory = CategoryUtil.copyBlankObject(localTargetCategory);
		this.changeSet.setTargetCategory(pLocalTargetCateogory);

		EList<ChangeSetItem> changeSetItems = this.changeSet.getChangeSetItems();
		for (ChangeSetItem changeSetItem2 : changeSetItems) {
			tmpRemoteRootCategory.getChildren().add((Category) EcoreUtil.copy(changeSetItem2.getRemoteConvertedContainer()));
		}

		this.remotePath = InfomngmntEditPlugin.getPlugin().getStateLocation()
		.append(COMPARE_FOLDER).append(new UniversalUniqueIdentifier().toString()).addFileExtension("xml");

		this.localPath = InfomngmntEditPlugin.getPlugin().getStateLocation()
		.append(COMPARE_FOLDER).append(new UniversalUniqueIdentifier().toString()).addFileExtension("xml");

		EditingUtil.getInstance().saveObjectToResource(
				tmpRemoteRootCategory, 
				this.remotePath.toOSString());

		EditingUtil.getInstance().saveObjectToResource(
				localTargetCategory, 
				this.localPath.toOSString());
	}

	public DiffModel makeDiff() {
		MatchModel match;
		try {
			EObject localObject = EditingUtil.getInstance().getObjectFromFileUri(URI.createFileURI(this.localPath.toOSString()), InfomngmntPackage.Literals.CATEGORY, null);
			EObject remoteObject = EditingUtil.getInstance().getObjectFromFileUri(URI.createFileURI(this.remotePath.toOSString()), InfomngmntPackage.Literals.CATEGORY, null);
			match = MatchService.doMatch(localObject ,remoteObject, Collections.<String, Object> emptyMap());
			this.diffModel = DiffService.doDiff(match, false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.diffModel;
	}

}
