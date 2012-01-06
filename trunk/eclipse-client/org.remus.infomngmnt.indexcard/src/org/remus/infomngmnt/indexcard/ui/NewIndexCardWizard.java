/*******************************************************************************
 * Copyright (c) 2010 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein
 *******************************************************************************/
package org.remus.infomngmnt.indexcard.ui;

import org.eclipse.remus.common.ui.image.ResourceManager;
import org.remus.infomngmnt.indexcard.Activator;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;

public class NewIndexCardWizard extends NewInfoObjectWizard {

	public NewIndexCardWizard() {
		super();
		setWindowTitle("New index card");
	}

	@Override
	protected String getInfoTypeId() {
		return Activator.INFO_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New index card");
		this.page1.setMessage("This wizard enables you to create new index card units");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(Activator
				.getDefault(), "icons/iconexperience/new_wizard.png"));
	}

}
