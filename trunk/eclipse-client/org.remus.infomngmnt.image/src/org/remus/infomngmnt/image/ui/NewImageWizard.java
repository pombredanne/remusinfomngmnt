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

package org.remus.infomngmnt.image.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewImageWizard extends NewInfoObjectWizard {

	/**
	 * 
	 */
	public NewImageWizard() {
		setNeedsProgressMonitor(true);
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench, final IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			this.page1 = new GeneralImagePage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralImagePage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralImagePage((Category)null);
		}

	}
	
	

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		return ImagePlugin.TYPE_ID;
	}

}
