/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Balazs Brinkus - initial API and implementation
 *     Tasktop Technologies - improvements
 *     Willian Mitsuda - improvements
 *******************************************************************************/

package org.remus.infomngmnt.image.screenshot;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.mylyn.commons.ui.screenshots.ScreenshotViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.remus.infomngmnt.image.messages.Messages;

/**
 * NOTE: this class exposes a lot of implementation detial and is likely to
 * change.
 * 
 * A wizard page to create a screenshot from the display.
 * 
 * @author Balazs Brinkus (bug 160572)
 * @author Mik Kersten
 * @author Willian Mitsuda
 */
public class ScreenshotCreationPage2 extends WizardPage {

	public ScreenshotCreationPage2() {
		super("ScreenShotAttachment"); //$NON-NLS-1$
		setTitle(Messages.ScreenshotCreationPage2_WizardTitle);
		setDescription(Messages.ScreenshotCreationPage2_WizardSubTitle);
	}

	private ScreenshotViewer viewer;

	public void createControl(Composite parent) {
		viewer = new ScreenshotViewer(parent, SWT.BORDER | SWT.FLAT) {
			@Override
			protected void stateChanged() {
				getContainer().updateButtons();
			}
		};
		setControl(viewer.getControl());
	}

	@Override
	public boolean isPageComplete() {
		return (viewer != null) && viewer.isComplete();
	}

	public boolean isImageDirty() {
		return viewer.isDirty();
	}

	public Image createImage() {
		// NOTE: may get invoked from non UI thread
		return viewer.createImage();
	}

	public void setImageDirty(boolean imageDirty) {
		viewer.setDirty(imageDirty);
	}
}
