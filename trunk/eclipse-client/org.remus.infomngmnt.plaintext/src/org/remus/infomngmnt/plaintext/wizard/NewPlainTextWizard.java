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

package org.remus.infomngmnt.plaintext.wizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.plaintext.Activator;
import org.remus.infomngmnt.ui.editors.InformationEditor;
import org.remus.infomngmnt.ui.editors.InformationEditorInput;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewPlainTextWizard extends NewInfoObjectWizard {

	public NewPlainTextWizard() {
		super();
		setWindowTitle("New unformatted text");
	}

	@Override
	protected String getInfoTypeId() {
		return Activator.INFO_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New unformatted text");
		this.page1.setMessage("This wizard enables you to create new unformatted text units");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator
				.getDefault(), "icons/iconexperience/new_wizard.png"));
	}

	// protected void setDefaults(final Object value, final RuleValue ruleValue,
	// final TransferWrapper transferType) throws CoreException {
	// this.newElement.setStringValue(String.valueOf(value));
	// }

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
