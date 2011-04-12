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
import org.eclipse.remus.Category;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.core.commands.CommandFactory;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.eclipse.ui.IWorkbench;
import org.remus.infomngmnt.audio.AudioActivator;
import org.remus.infomngmnt.audio.messages.Messages;

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
		setWindowTitle(Messages.NewAudioWizard_NewAudio);
		serviceTracker = new RemusServiceTracker(
				Platform.getBundle(AudioActivator.PLUGIN_ID));
		editService = serviceTracker.getService(IEditingHandler.class);

	}

	@Override
	public void dispose() {
		serviceTracker.ungetService(editService);
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench workbench,
			final IStructuredSelection selection) {
		Object firstElement = selection.getFirstElement();
		if (firstElement instanceof Category) {
			page1 = new GeneralAudioPage((Category) firstElement);
		} else if (firstElement instanceof InformationUnitListItem) {
			page1 = new GeneralAudioPage((InformationUnitListItem) firstElement);
		} else {
			page1 = new GeneralAudioPage((Category) null);
		}
		setCategoryToPage();
		setFilesToPage();

	}

	@Override
	protected Command getAdditionalCommands() {
		IFile tmpFile = ((GeneralAudioPage) page1).getFiles()[0];
		if (tmpFile != null) {
			return CommandFactory.addFileToInfoUnit(tmpFile, newElement,
					editService.getNavigationEditingDomain());
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

}
