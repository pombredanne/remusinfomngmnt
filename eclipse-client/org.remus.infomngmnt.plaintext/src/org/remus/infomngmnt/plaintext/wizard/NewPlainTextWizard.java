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

package org.remus.infomngmnt.plaintext.wizard;

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.CategoryUtil;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewPlainTextWizard extends Wizard implements INewWizard {

	private NewPlainTextWizardPage page1;

	/**
	 * 
	 */
	public NewPlainTextWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addPages() {
		addPage(this.page1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		String parentCategoryValue = this.page1.getCategoryString();
		Category findCategory = CategoryUtil.findCategory(parentCategoryValue, true);
		InformationUnitListItem createInformationUnitListItem = InfomngmntFactory.eINSTANCE.createInformationUnitListItem();
		String string = new UniversalUniqueIdentifier().toString();
		createInformationUnitListItem.setId(string);
		createInformationUnitListItem.setLabel(this.page1.getNameString());
		createInformationUnitListItem.setType("PLAINTEXT");
		findCategory.getInformationUnit().add(createInformationUnitListItem);
		IFile newFile = CategoryUtil.getProjectByCategory(findCategory).getFile(string + ".info");
		InformationUnit objectFromFile = EditingUtil.getInstance().getObjectFromFile(newFile, InfomngmntPackage.eINSTANCE.getInformationUnit(), true);
		objectFromFile.setId(string);
		objectFromFile.setLabel(this.page1.getNameString());
		objectFromFile.setType("PLAINTEXT");
		createInformationUnitListItem.setWorkspacePath(newFile.getFullPath().toOSString());
		EditingUtil.getInstance().saveObjectToResource(objectFromFile);
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new InformationEditorInput(createInformationUnitListItem), InformationEditor.ID);
		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new NewPlainTextWizardPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new NewPlainTextWizardPage((InformationUnitListItem) firstElement);
		} else  {
			this.page1 = new NewPlainTextWizardPage();
		}

	}
}