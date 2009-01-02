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

package org.remus.infomngmnt.ui.remote;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;

import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.remote.IRepository;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.ui.extension.IRepositoryUI;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class NewRepositoryWizard extends Wizard {
	
	protected RemoteRepository repository;
	protected IRepository definingRepository;

	public NewRepositoryWizard() {
		setNeedsProgressMonitor(true);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(IRepositoryService.class).getRepositories();
		repositories.getRepositories().add(this.repository);
		this.repository.setTypeId(this.definingRepository.getId());
		EditingUtil.getInstance().saveObjectToResource(repositories);
		return true;
	}
	
	public void init(final IStructuredSelection selection) {
		if (!selection.isEmpty() && selection.getFirstElement() instanceof IRepositoryUI) {
			this.definingRepository = ((IRepositoryUI) selection.getFirstElement()).getRepository();
		}
	}

}
