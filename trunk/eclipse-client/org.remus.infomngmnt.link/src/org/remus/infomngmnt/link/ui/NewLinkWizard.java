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

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.RuleValue;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.link.LinkActivator;
import org.remus.infomngmnt.ui.extension.AbstractCreationPreferencePage;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewLinkWizard extends NewInfoObjectWizard {

	private LinkWizardPage page2;

	public NewLinkWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("New link");
	}

	@Override
	public void addPages() {
		super.addPages();
		addPage(this.page2 = new LinkWizardPage(this.newElement));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralLinkPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralLinkPage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralLinkPage((Category) null);
		}
		setCategoryToPage();

	}

	@Override
	public void setDefaults(final Object value, final RuleValue ruleValue) {
		this.newElement.setStringValue(String.valueOf(value));
		InformationUnit childByType = InformationUtil.getChildByType(ruleValue,
				AbstractCreationPreferencePage.NODENAME_PREDEFINED_CATEGORY);
		if (childByType != null) {
			this.page1.setCategoryString(childByType.getStringValue());
		}
	}

	@Override
	public boolean canFinish() {
		return this.page1.isPageComplete();
	}

	@Override
	protected String getInfoTypeId() {
		return LinkActivator.LINK_INFO_ID;
	}

}
