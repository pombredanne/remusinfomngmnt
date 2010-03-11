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
package org.remus.infomngmnt.video.newwizard;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;
import org.remus.infomngmnt.video.VideoActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewVideoWizard extends NewInfoObjectWizard {

	private final RemusServiceTracker remusServiceTracker;
	private final IEditingHandler editingHandler;

	/**
	 * 
	 */
	public NewVideoWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("New video");
		this.remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(VideoActivator.PLUGIN_ID));
		this.editingHandler = this.remusServiceTracker.getService(IEditingHandler.class);

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
			this.page1 = new GeneralVideoPage((Category) firstElement, this.editingHandler);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralVideoPage((InformationUnitListItem) firstElement,
					this.editingHandler);
		} else {
			this.page1 = new GeneralVideoPage((Category) null, this.editingHandler);
		}

	}

	@Override
	protected Command getAdditionalCommands() {
		this.files = ((GeneralVideoPage) this.page1).getFiles();
		if (this.files != null && this.files.length > 0) {
			return CommandFactory.addFileToInfoUnit(this.files[0], this.newElement,
					this.editingHandler.getNavigationEditingDomain());
		}
		return super.getAdditionalCommands();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard#getInfoTypeId()
	 */
	@Override
	protected String getInfoTypeId() {
		return VideoActivator.TYPE_ID;
	}

}
