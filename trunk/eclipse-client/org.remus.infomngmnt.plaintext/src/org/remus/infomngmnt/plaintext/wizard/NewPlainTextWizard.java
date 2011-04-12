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

import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;
import org.remus.infomngmnt.plaintext.Activator;
import org.remus.infomngmnt.plaintext.messages.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewPlainTextWizard extends NewInfoObjectWizard {

	public NewPlainTextWizard() {
		super();
		setWindowTitle(Messages.NewPlainTextWizard_Title);
	}

	@Override
	protected String getInfoTypeId() {
		return Activator.INFO_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		page1.setTitle(Messages.NewPlainTextWizard_Title);
		page1.setMessage(Messages.NewPlainTextWizard_Subtitle);
		page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				Activator.getDefault(), "icons/iconexperience/new_wizard.png")); //$NON-NLS-1$
	}

	// protected void setDefaults(final Object value, final RuleValue ruleValue,
	// final TransferWrapper transferType) throws CoreException {
	// this.newElement.setStringValue(String.valueOf(value));
	// }

}
