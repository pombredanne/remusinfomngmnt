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

package org.remus.infomngmnt.image.screenshot;

import org.remus.infomngmnt.image.ImagePlugin;
import org.remus.infomngmnt.image.messages.Messages;
import org.remus.infomngmnt.image.ui.ImageCreationTrigger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.remus.common.ui.UIUtil;
import org.eclipse.remus.core.model.InformationStructureEdit;
import org.eclipse.remus.rules.RulesFactory;
import org.eclipse.swt.graphics.ImageData;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ScreenshotCreationWizard extends Wizard {

	private ScreenshotCreationPage2 page1;

	@Override
	public boolean performFinish() {
		final ImageData imageData = page1.createImage().getImageData();
		getShell().getDisplay().asyncExec(new Runnable() {

			public void run() {
				try {
					ImageCreationTrigger imageCreationTrigger = new ImageCreationTrigger();
					InformationStructureEdit edit = InformationStructureEdit
							.newSession(ImagePlugin.TYPE_ID);
					imageCreationTrigger.setNewInformationUnit(edit
							.newInformationUnit());
					imageCreationTrigger.setDefaults(imageData,
							RulesFactory.eINSTANCE.createRuleValue(), null);
					imageCreationTrigger.handleCreationRequest();
				} catch (CoreException e) {
					ErrorDialog.openError(UIUtil.getDisplay().getActiveShell(),
							Messages.ScreenshotCreationWizard_ErrorCreating,
							Messages.ScreenshotCreationWizard_ErrorExecutingRequest,
							e.getStatus());
				}
			}

		});
		return true;

	}

	@Override
	public void addPages() {
		setWindowTitle(Messages.ScreenshotCreationWizard_CaptureDesktop);
		addPage(page1 = new ScreenshotCreationPage2());

	}

}
