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

package org.remus.infomngmnt.eclipsemarketplace.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.ui.remote.NewRepositoryWizard;
import org.eclipse.swt.widgets.Composite;

import org.remus.infomngmnt.eclipsemarketplace.api.Constants;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewEclipseMarketPlaceWizard extends NewRepositoryWizard {

	private MarketPlaceWizardPage page1;

	/**
	 * 
	 */
	public NewEclipseMarketPlaceWizard() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addPages() {
		addPage(this.page1);
	}

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage == this.page1) {
			this.page1.setRemoteObject(getRepository());
		}
		return nextPage;
	}

	@Override
	public void init(final IStructuredSelection selection) {
		super.init(selection);
		this.page1 = new MarketPlaceWizardPage();
	}

	@Override
	public IWizardPage getStartingPage() {
		IWizardPage startingPage = super.getStartingPage();
		if (startingPage == this.page1) {
			this.page1.setRemoteObject(getRepository());
		}
		return startingPage;
	}

	@Override
	public void createPageControls(final Composite pageContainer) {
		// do nothing. no precreation.
	}

	@Override
	protected void configureRepository(final RemoteRepository newRemoteRepositry) {
		newRemoteRepositry.setUrl(Constants.API_ENDPOINT);

	}

}
