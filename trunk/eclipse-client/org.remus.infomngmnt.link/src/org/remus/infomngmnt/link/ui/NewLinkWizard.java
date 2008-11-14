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

package org.remus.infomngmnt.link.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.link.LinkRepresentation;
import org.remus.infomngmnt.link.preferences.LinkPreferenceInitializer;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewLinkWizard extends NewInfoObjectWizard {

	private LinkWizardPage page2;

	@Override
	protected InformationUnit createNewInformationUnit() {
		IPreferenceStore store = LinkActivator.getDefault().getPreferenceStore();
		InformationUnit returnValue = super.createNewInformationUnit();
		returnValue.setType("LINK");
		InformationUnit screenShot = InfomngmntFactory.eINSTANCE.createInformationUnit();
		screenShot.setType(LinkRepresentation.SCREENSHOT_TYPE);
		screenShot.setBoolValue(store.getBoolean(LinkPreferenceInitializer.MAKE_SCREENSHOT));
		InformationUnit indexTarget = InfomngmntFactory.eINSTANCE.createInformationUnit();
		indexTarget.setType(LinkRepresentation.INDEXWEBPAGE_TYPE);
		indexTarget.setBoolValue(store.getBoolean(LinkPreferenceInitializer.INDEX_DOCUMENT));
		InformationUnit linkContent = InfomngmntFactory.eINSTANCE.createInformationUnit();
		linkContent.setType(LinkRepresentation.INDEXWEBPAGECONTENT_TYPE);
		returnValue.getChildValues().add(screenShot);
		returnValue.getChildValues().add(indexTarget);
		returnValue.getChildValues().add(linkContent);
		return returnValue;
	}
	@Override
	public void addPages() {
		super.addPages();
		addPage(this.page2 = new LinkWizardPage(this.newElement));
	}

	@Override
	public boolean performFinish() {
		return super.performFinish();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralLinkPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralLinkPage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralLinkPage((Category)null);
		}

	}



	@Override
	public boolean canFinish() {
		return this.page1.isPageComplete();
	}

}
