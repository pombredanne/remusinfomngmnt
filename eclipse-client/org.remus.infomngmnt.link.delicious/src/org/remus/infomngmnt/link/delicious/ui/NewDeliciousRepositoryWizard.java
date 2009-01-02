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

package org.remus.infomngmnt.link.delicious.ui;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.widgets.Composite;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.link.delicious.Activator;
import org.remus.infomngmnt.link.delicious.pref.PreferenceInitializer;
import org.remus.infomngmnt.ui.remote.NewRepositoryWizard;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewDeliciousRepositoryWizard extends NewRepositoryWizard {

	private DeliciousConnectionWizardPage page1;

	/**
	 * 
	 */
	public NewDeliciousRepositoryWizard() {
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
		this.page1 = new DeliciousConnectionWizardPage();
	}
	
	@Override
	public IWizardPage getStartingPage() {
		IWizardPage startingPage = super.getStartingPage();
		if (startingPage == this.page1) {
			this.page1.setRemoteObject(getRepository());
			this.page1.setDefiningRepository(this.definingRepository);
		}
		return startingPage;
	}
	
	@Override
	public void createPageControls(final Composite pageContainer) {
		// do nothing. no precreation.
	}
	
	
	private RemoteRepository getRepository() {
		if (this.repository == null) {
			this.repository = InfomngmntFactory.eINSTANCE.createRemoteRepository();
			this.repository.setUrl(Activator.getDefault().getPreferenceStore().getString(PreferenceInitializer.API_URL));
		}
		return this.repository;
	}

}
