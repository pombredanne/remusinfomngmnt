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

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.ui.commands.CommandFactory;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class NewInfoObjectWizard extends Wizard implements INewWizard {

	protected GeneralPage page1;

	protected InformationUnit newElement;

	/**
	 * 
	 */
	public NewInfoObjectWizard() {
		this.newElement = createNewInformationUnit();
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
		IInfoType infoTypeByType = InformationExtensionManager.getInstance().getInfoTypeByType(getInfoTypeId());
		if (infoTypeByType != null) {
			return infoTypeByType.getCreationFactory().createNewObject();
		}
		return null;
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
		EditingUtil.getInstance().getNavigationEditingDomain().getCommandStack()
		.execute(CommandFactory.CREATE_INFOTYPE(this.newElement, findCategory()));
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new InformationEditorInput((IFile) this.newElement.getAdapter(IFile.class)), InformationEditor.ID);
		} catch (Exception e) {
			// will come soon.
		}
		// we also reveal the created list-item, that can be found in the navigation
		UIUtil.selectAndReveal(this.newElement.getAdapter(InformationUnitListItem.class), PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		UIUtil.selectAndReveal(this.newElement, PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		return true;
	}
	@Override
	public IWizardPage getStartingPage() {
		IWizardPage startingPage = super.getStartingPage();
		if (startingPage instanceof IInfoObjectSetter) {
			((IInfoObjectSetter) startingPage).setInformationUnit(this.newElement);
		}
		return startingPage;
	}
	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage instanceof IInfoObjectSetter) {
			((IInfoObjectSetter) nextPage).setInformationUnit(this.newElement);
		}
		return nextPage;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralPage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralPage((Category)null);
		}

	}

	protected abstract String getInfoTypeId();

	@Override
	public void createPageControls(final Composite pageContainer) {
		// do nothing
	}
}