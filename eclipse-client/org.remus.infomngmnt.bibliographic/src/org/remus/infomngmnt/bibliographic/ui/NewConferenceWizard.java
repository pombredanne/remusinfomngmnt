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

import org.eclipse.remus.common.ui.image.ResourceManager;
import org.eclipse.remus.ui.newwizards.NewInfoObjectWizard;

import org.remus.infomngmnt.bibliographic.BibliographicActivator;

/**
 * New Wizard for information unit "Conference"
 * 
 * @author Andreas Deinlein <dev@deasw.com>
 * 
 */
public class NewConferenceWizard extends NewInfoObjectWizard {

	public NewConferenceWizard() {
		super();
		setWindowTitle("New Conference");
	}

	@Override
	protected String getInfoTypeId() {
		return BibliographicActivator.CONFERENCE_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New Conference");
		this.page1
				.setMessage("This wizard enables you to create a new information unit for a \"Conference\"");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(
				BibliographicActivator.getDefault(), "icons/iconexperience/conference_new_wizard.png"));
	}

}
