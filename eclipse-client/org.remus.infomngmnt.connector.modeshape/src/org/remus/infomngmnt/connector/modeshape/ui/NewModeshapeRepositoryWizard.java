/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.connector.modeshape.ui;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.ui.remote.NewRepositoryWizard;
import org.eclipse.swt.widgets.Composite;
import org.remus.infomngmnt.connector.modeshape.Messages;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NewModeshapeRepositoryWizard extends NewRepositoryWizard {

	private ModeshapeConnectionWizardPage page1;

	/**
	 * 
	 */
	public NewModeshapeRepositoryWizard() {
		setWindowTitle(Messages.NewModeshapeRepositoryWizard_NewModeShapeConnector);
	}

	@Override
	public void addPages() {
		addPage(page1);
	}

	@Override
	public boolean performFinish() {
		if (repository.getUrl().endsWith("/")) { //$NON-NLS-1$
			repository.setUrl(StringUtils.stripEnd(repository.getUrl(), "/")); //$NON-NLS-1$
		}
		return super.performFinish();
	};

	@Override
	public IWizardPage getNextPage(final IWizardPage page) {
		IWizardPage nextPage = super.getNextPage(page);
		if (nextPage == page1) {
			page1.setRemoteObject(getRepository());
		}
		return nextPage;
	}

	@Override
	public void init(final IStructuredSelection selection) {
		super.init(selection);
		page1 = new ModeshapeConnectionWizardPage();
	}

	@Override
	public IWizardPage getStartingPage() {
		IWizardPage startingPage = super.getStartingPage();
		if (startingPage == page1) {
			page1.setRemoteObject(getRepository());
		}
		return startingPage;
	}

	@Override
	public void createPageControls(final Composite pageContainer) {
		// do nothing. no precreation.
	}

	@Override
	protected void configureRepository(final RemoteRepository newRemoteRepositry) {

	}

}
