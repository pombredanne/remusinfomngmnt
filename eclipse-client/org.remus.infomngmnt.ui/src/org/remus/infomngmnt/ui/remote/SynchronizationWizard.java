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

package org.remus.infomngmnt.ui.remote;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.AddModelElement;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChange;
import org.eclipse.emf.compare.diff.metamodel.RemoteMoveModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.extension.ISaveParticipant;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.services.ISaveParticipantExtensionService;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizationWizard extends Wizard {
	
	private ChangeSetWizardPage page1;
	private ChangeSet changeSet;
	
	public static final int CHECKOUTMODE = 1; 
	public static final int SHAREMODE = 2; 
	public static final int SYNCMODE = 3; 
	private final int mode;

	public SynchronizationWizard(final int mode) {
		this.mode = mode;
		setNeedsProgressMonitor(true);
	}
	
	@Override
	public void addPages() {
		addPage(this.page1 = new ChangeSetWizardPage(this.changeSet));
		
	}
	
	public void init(final ChangeSet changeSet) {
		this.changeSet = changeSet;
	}
	
	@Override
	public void createPageControls(final Composite pageContainer) {
		// TODO Auto-generated method stub
		//super.createPageControls(pageContainer);
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		DiffModel diffModel = this.page1.getDiffModel();
		EList<DiffElement> ownedElements = diffModel.getOwnedElements();
		performDiff(ownedElements);
		return false;
	}

	private void performDiff(final EList<DiffElement> ownedElements) {
		for (DiffElement diffElement : ownedElements) {
			if (diffElement instanceof AddModelElement) {
				EObject rightElement = ((AddModelElement) diffElement).getRightElement();
				System.out.println(EcoreUtil.equals(this.changeSet.getChangeSetItems().get(0).getRemoteConvertedContainer(), rightElement));
				if (this.changeSet.getChangeSetItems().get(0).getRemoteConvertedContainer().getId().equals(((Category) rightElement).getId())) {
					System.out.println("TEST");
					Category remoteConvertedContainer = this.changeSet.getChangeSetItems().get(0).getRemoteConvertedContainer();
					this.changeSet.getTargetCategory().getChildren().add(remoteConvertedContainer);
					EList<InformationUnitListItem> informationUnit = remoteConvertedContainer.getInformationUnit();
					for (InformationUnitListItem informationUnitListItem : informationUnit) {
						InformationUnit informationUnit2 = this.changeSet.getChangeSetItems().get(0).getRemoteFullObjectMap().get(informationUnitListItem);
						IFile newFile = CategoryUtil.getProjectByCategory(remoteConvertedContainer).getFile(informationUnitListItem.getId() + ".info");
						EditingUtil.getInstance().saveObjectToResource(newFile, informationUnit2);
						informationUnitListItem.setWorkspacePath(newFile.getFullPath().toOSString());
						UIPlugin.getDefault().getService(ISaveParticipantExtensionService.class)
						.fireEvent(ISaveParticipant.CREATED, informationUnit2);
						
					}
				}
			}
			if (diffElement instanceof RemoteMoveModelElement) {
				
			}
			if (diffElement instanceof ModelElementChange) {
				
			}
			performDiff(diffElement.getSubDiffElements());
		}
		
	}
	
	

}
