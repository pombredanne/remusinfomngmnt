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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.graphics.ImageData;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.image.ui.NewImageWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ScreenshotCreationWizard extends Wizard {

	private ScreenshotCreationPage page1;

	@Override
	public boolean performFinish() {
		final ImageData imageData = ScreenshotCreationWizard.this.page1.createImage()
				.getImageData();
		getShell().getDisplay().asyncExec(new Runnable() {

			public void run() {
				try {
					NewImageWizard newImageWizard = new NewImageWizard();
					newImageWizard.init(UIUtil.getPrimaryWindow().getWorkbench(),
							new StructuredSelection(new Object[0]));
					newImageWizard.setDefaults(imageData, InfomngmntFactory.eINSTANCE
							.createRuleValue(), null);
					WizardDialog wizard = new WizardDialog(UIUtil.getPrimaryWindow().getShell(),
							newImageWizard);
					wizard.open();
				} catch (CoreException e) {
					ErrorDialog.openError(UIUtil.getDisplay().getActiveShell(),
							"Error creating new information unit",
							"Error occured while executing your request.", e.getStatus());
				}
			}

		});
		return true;

	}

	@Override
	public void addPages() {
		setWindowTitle("Capture desktop");
		addPage(this.page1 = new ScreenshotCreationPage());

	}

}
