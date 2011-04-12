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
package org.remus.infomngmnt.connector.flickr.ui;

import java.net.URL;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.remus.infomngmnt.connector.flickr.messages.Messages;

public class FlickConnectionWizardPage extends WizardPage {

	private final URL buildAuthenticationUrl;

	/**
	 * Create the wizard
	 * 
	 * @param buildAuthenticationUrl
	 */
	public FlickConnectionWizardPage(final URL buildAuthenticationUrl) {
		super("wizardPage"); //$NON-NLS-1$
		this.buildAuthenticationUrl = buildAuthenticationUrl;
		setTitle(Messages.FlickConnectionWizardPage_GrantAccess);
		setDescription(Messages.FlickConnectionWizardPage_FollowInstructions);
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());

		final Browser browser = new Browser(container, SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.minimumWidth = 600;
		layoutData.minimumHeight = 600;
		browser.setLayoutData(layoutData);
		browser.setUrl(this.buildAuthenticationUrl.toString());

		setControl(container);

	}

}
