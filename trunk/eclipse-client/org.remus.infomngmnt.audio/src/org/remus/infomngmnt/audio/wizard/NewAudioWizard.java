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
package org.remus.infomngmnt.audio.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.audio.AudioActivator;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewAudioWizard extends NewInfoObjectWizard {

	private final RemusServiceTracker serviceTracker;
	private final IEditingHandler editService;

	/**
	 * 
	 */
	public NewAudioWizard() {
		setNeedsProgressMonitor(true);
		setWindowTitle("New audio");
		this.serviceTracker = new RemusServiceTracker(Platform.getBundle(AudioActivator.PLUGIN_ID));
		this.editService = this.serviceTracker.getService(IEditingHandler.class);

	}

	@Override
	public void dispose() {
		this.serviceTracker.ungetService(this.editService);
		super.dispose();
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
			this.page1 = new GeneralAudioPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			this.page1 = new GeneralAudioPage((InformationUnitListItem) firstElement);
		} else {
			this.page1 = new GeneralAudioPage((Category) null);
		}

	}

	@Override
	protected Command getAdditionalCommands() {
		IFile tmpFile = ((GeneralAudioPage) this.page1).getTmpFile();
		if (tmpFile != null) {
			return CommandFactory.addFileToInfoUnit(tmpFile, this.newElement, this.editService
					.getNavigationEditingDomain());
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
		return AudioActivator.TYPE_ID;
	}

	@Override
	protected void performActionAfterCreation() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(
					new InformationEditorInput((IFile) this.newElement.getAdapter(IFile.class)),
					InformationEditor.ID);
		} catch (Exception e) {
			// will come soon.
		}
		// we also reveal the created list-item, that can be found in the
		// navigation
		UIUtil.selectAndReveal(this.newElement.getAdapter(InformationUnitListItem.class),
				PlatformUI.getWorkbench().getActiveWorkbenchWindow());
		UIUtil.selectAndReveal(this.newElement, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow());

	}

}
