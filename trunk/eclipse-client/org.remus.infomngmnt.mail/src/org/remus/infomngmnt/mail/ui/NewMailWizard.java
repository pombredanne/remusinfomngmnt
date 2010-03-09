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

package org.remus.infomngmnt.mail.ui;

import org.remus.infomngmnt.mail.MailActivator;
import org.remus.infomngmnt.mail.internal.ResourceManager;
import org.remus.infomngmnt.ui.newwizards.NewInfoObjectWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewMailWizard extends NewInfoObjectWizard {

	public NewMailWizard() {
		super();
		setWindowTitle("New Mail/Message");
	}

	@Override
	protected String getInfoTypeId() {
		return MailActivator.INFO_TYPE_ID;
	}

	@Override
	public void addPages() {
		super.addPages();
		this.page1.setTitle("New Mail/Message");
		this.page1.setMessage("This wizard enables you to create new mail or message");
		this.page1.setImageDescriptor(ResourceManager.getPluginImageDescriptor(MailActivator
				.getDefault(), "icons/iconexperience/mail_wizard.png"));
	}

}
