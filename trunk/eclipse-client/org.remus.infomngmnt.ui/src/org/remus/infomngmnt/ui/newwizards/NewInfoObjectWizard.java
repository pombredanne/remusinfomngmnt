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

package org.remus.infomngmnt.ui.newwizards;

import java.util.Date;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.ui.commands.CommandFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewInfoObjectWizard extends Wizard implements INewWizard {

	protected GeneralPage page1;

	/**
	 * 
	 */
	public NewInfoObjectWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addPages() {
		addPage(this.page1);
	}

	/**
	 * Creates the new information object with the text values created
	 * from
	 * @return
	 */
	protected InformationUnit createNewInformationUnit() {
		InformationUnit newInfoObject = InfomngmntFactory.eINSTANCE.createInformationUnit();
		newInfoObject.setCreationDate(new Date());
		newInfoObject.setLabel(this.page1.getNameString());
		newInfoObject.setDescription(this.page1.getDescriptionString());
		newInfoObject.setKeywords(this.page1.getKeywordString());
		return newInfoObject;
	}

	protected Category findCategory() {
		String parentCategoryValue = this.page1.getCategoryString();
		return CategoryUtil.findCategory(parentCategoryValue, true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		InformationUnit createNewInformationUnit = createNewInformationUnit();
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack()
		.execute(CommandFactory.CREATE_INFOTYPE(createNewInformationUnit, findCategory()));
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralPage((InformationUnitListItem) firstElement);
		} else  {
			this.page1 = new GeneralPage();
		}

	}
}