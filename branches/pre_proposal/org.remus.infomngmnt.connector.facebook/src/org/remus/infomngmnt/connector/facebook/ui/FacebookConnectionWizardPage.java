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
package org.remus.infomngmnt.connector.facebook.ui;

import java.net.URL;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class FacebookConnectionWizardPage extends WizardPage {

	private final URL buildAuthenticationUrl;
	private Browser browser;

	/**
	 * Create the wizard
	 * 
	 * @param buildAuthenticationUrl
	 */
	public FacebookConnectionWizardPage(final URL buildAuthenticationUrl) {
		super("wizardPage");
		this.buildAuthenticationUrl = buildAuthenticationUrl;
		setTitle("Grant RIM Access to your Flickr-Account");
		setDescription("Follow the instructions. If you have granted access you can finish this wizard");
	}

	/**
	 * Create contents of the wizard
	 * 
	 * @param parent
	 */
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout());

		this.browser = new Browser(container, SWT.BORDER);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.minimumWidth = 600;
		layoutData.minimumHeight = 600;
		this.browser.setLayoutData(layoutData);
		this.browser.setUrl(this.buildAuthenticationUrl.toString());

		setControl(container);

	}

	public final Browser getBrowser() {
		return this.browser;
	}

}
