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

package org.remus.infomngmnt.category;

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.ui.UIUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewCategoryWizard extends Wizard implements INewWizard {



	private IWorkbench workbench;
	private Category selection;
	private NewCategoryWizardPage page1;

	/**
	 * 
	 */
	public NewCategoryWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addPages() {
		addPage(this.page1 = new NewCategoryWizardPage(this.selection));
		super.addPages();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		String parentCategoryValue = this.page1.getParentCategoryValue();
		Category findCategory = CategoryUtil.findCategory(parentCategoryValue, true);
		Category createCategory = InfomngmntFactory.eINSTANCE.createCategory();
		createCategory.setId(new UniversalUniqueIdentifier().toString());
		createCategory.setLabel(this.page1.getNewCategoryValue());
		findCategory.getChildren().add(createCategory);
		UIUtil.selectAndReveal(createCategory, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		if (selection.getFirstElement() instanceof Category) {
			this.selection = (Category) selection.getFirstElement();
		}

	}

	@Override
	public boolean canFinish() {
		return this.page1.isPageComplete();
	}

}
