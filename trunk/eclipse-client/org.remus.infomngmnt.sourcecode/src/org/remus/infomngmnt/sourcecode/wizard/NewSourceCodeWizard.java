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

package org.remus.infomngmnt.sourcecode.wizard;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.sourcecode.Messages;
import org.remus.infomngmnt.sourcecode.SourceCodePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewSourceCodeWizard extends NewInfoObjectWizard {

	/**
	 * 
	 */
	public NewSourceCodeWizard() {
		setWindowTitle(Messages.NewSourceCodeWizard_Title);
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
			this.page1 = new GeneralSourcePage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralSourcePage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralSourcePage((Category) null);
		}
		setCategoryToPage();

	}

	@Override
	protected String getInfoTypeId() {
		return SourceCodePlugin.TYPE_ID;
	}

}
