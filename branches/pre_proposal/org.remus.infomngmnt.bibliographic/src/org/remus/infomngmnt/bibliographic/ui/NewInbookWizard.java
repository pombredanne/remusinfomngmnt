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
 * New Wizard for information unit "Inbook"
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 * 
 */
public class NewInbookWizard extends NewInfoObjectWizard {

	public NewInbookWizard() {
		super();
		setWindowTitle("New Inbook");
	}

	@Override
	protected String getInfoTypeId() {
		return BibliographicActivator.INBOOK_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New Inbook");
		this.page1
				.setMessage("This wizard enables you to create a new information unit for a \"Inbook\"");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				BibliographicActivator.getDefault(), "icons/iconexperience/inbook_new_wizard.png"));
	}

}
