/*******************************************************************************
 * Copyright (c) 2009 Jan Hartwig, FEB Radebeul
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Jan Hartwig - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.contact.wizard;

/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.contact.core.ContactUtil;
import org.remus.infomngmnt.contact.messages.Messages;

public class NewContactWizard extends NewInfoObjectWizard implements INewWizard {

	public NewContactWizard() {
		setWindowTitle(Messages.NewContactWizard_CreateNewContact);
	}

	@Override
	protected String getInfoTypeId() {
		return ContactActivator.TYPE_ID;
	}

	@Override
	public boolean performFinish() {
		this.newElement.setLabel(ContactUtil.getFormattedName(this.newElement));
		return super.performFinish();
	}

	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralContactPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralContactPage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralContactPage((Category) null);
		}
		setCategoryToPage();
	}

}
