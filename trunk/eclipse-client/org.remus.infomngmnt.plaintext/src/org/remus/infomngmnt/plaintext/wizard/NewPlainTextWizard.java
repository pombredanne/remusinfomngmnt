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
		page1.setTitle("New unformatted text");
		page1.setMessage("This wizard enables you to create new unformatted text units");
		page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				Activator.getDefault(), "icons/iconexperience/new_wizard.png"));
	}

	// protected void setDefaults(final Object value, final RuleValue ruleValue,
	// final TransferWrapper transferType) throws CoreException {
	// this.newElement.setStringValue(String.valueOf(value));
	// }

}
