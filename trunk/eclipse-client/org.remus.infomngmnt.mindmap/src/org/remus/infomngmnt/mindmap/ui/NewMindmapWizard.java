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

package org.remus.infomngmnt.mindmap.ui;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.xmind.ui.internal.WorkbookFactory;
import org.xmind.ui.mindmap.MindMapUI;

import org.remus.infomngmnt.common.core.util.ResourceUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.mindmap.MindmapActivator;
import org.remus.infomngmnt.services.RemusServiceTracker;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewMindmapWizard extends NewInfoObjectWizard {

	private final RemusServiceTracker remusServiceTracker;
	private final IEditingHandler service;

	public NewMindmapWizard() {
		super();
		setWindowTitle("New Mindmap");
		this.remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(MindmapActivator.PLUGIN_ID));
		this.service = this.remusServiceTracker.getService(IEditingHandler.class);
	}

	@Override
	protected Command getAdditionalCommands() {
		if (this.files != null && this.files.length > 0) {
			return CommandFactory.addFileToInfoUnit(this.files[0], this.newElement, this.service
					.getNavigationEditingDomain());
		} else {
			try {
				IFile emptyFile = ResourceUtil
						.createTempFile(MindMapUI.FILE_EXT_XMIND.substring(1));
				emptyFile.setContents(WorkbookFactory.createEmptyWorkbookStream(), true, false,
						new NullProgressMonitor());
				return CommandFactory.addFileToInfoUnit(emptyFile, this.newElement, this.service
						.getNavigationEditingDomain());
			} catch (CoreException e) {
				return null;
			}
		}
	}

	@Override
	protected String getInfoTypeId() {
		return MindmapActivator.INFO_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New Mindmap");
		this.page1.setMessage("This wizard enables you to create a new meeting protocol");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(MindmapActivator
				.getDefault(), "icons/iconexperience/new-wizard.png"));
	}

}
