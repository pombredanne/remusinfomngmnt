/*******************************************************************************
 * Copyright (c) 2009 Andreas Deinlein
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Andreas Deinlein - bibliographic extensions
 *******************************************************************************/
package org.remus.infomngmnt.bibliographic.ui;

import org.remus.infomngmnt.bibliographic.BibliographicActivator;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * New Wizard for information unit "Misc"
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 * 
 */
public class NewMiscWizard extends NewInfoObjectWizard {

	public NewMiscWizard() {
		super();
		setWindowTitle("New Misc");
	}

	@Override
	protected String getInfoTypeId() {
		return BibliographicActivator.MISC_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New Misc");
		this.page1
				.setMessage("This wizard enables you to create a new information unit for a \"Misc\"");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				BibliographicActivator.getDefault(), "icons/iconexperience/misc_new_wizard.png"));
	}

}
