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

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.core.model.EditingUtil;
import org.remus.infomngmnt.core.model.IdFactory;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.ui.extension.IRepositoryUI;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class NewRepositoryWizard extends Wizard {

	protected RemoteRepository repository;
	private String repositoryId;
	private boolean editMode;

	public NewRepositoryWizard() {
		setNeedsProgressMonitor(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		if (!this.editMode) {
			repositories.getRepositories().add(this.repository);
		}
		EditingUtil.getInstance().saveObjectToResource(repositories);
		return true;
	}

	public void init(final IStructuredSelection selection) {
		if (!selection.isEmpty() && selection.getFirstElement() instanceof IRepositoryUI) {
			this.repositoryId = ((IRepositoryUI) selection.getFirstElement()).getRepositoryId();
		} else if (!selection.isEmpty() && selection.getFirstElement() instanceof RemoteRepository) {
			this.repository = (((RemoteRepository) selection.getFirstElement()));
			this.repositoryId = this.repository.getRepositoryImplementation().getId();
			this.editMode = true;
		}
	}

	protected boolean isEditMode() {
		return this.editMode;
	}

	protected RemoteRepository getRepository() {
		if (this.repository == null) {
			this.repository = InfomngmntFactory.eINSTANCE.createRemoteRepository();
			this.repository.setId(IdFactory.createNewId(null));
			this.repository.setRepositoryTypeId(this.repositoryId);
			configureRepository(this.repository);
		}
		return this.repository;
	}

	protected abstract void configureRepository(RemoteRepository newRemoteRepositry);

}
