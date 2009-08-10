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

package org.remus.infomngmnt.test.remote;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.edit.domain.EditingDomain;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.RemoteContainer;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.RepositoryCollection;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.remote.ICredentialProvider;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.core.sync.ChangeSetExecutor;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.test.AbstractTest;
import org.remus.infomngmnt.util.CategoryUtil;
import org.remus.infomngmnt.util.EditingUtil;
import org.remus.infomngmnt.util.IdFactory;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractRemoteTest extends AbstractTest {

	@Override
	protected void initialize() throws Exception {
		createRepository(getRepositoryTypeId());
		cleanRepository();
	}

	private void cleanRepository() throws Exception {
		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		RemoteRepository remoteRepository = repositories.getRepositories().get(0);
		assertNotNull(remoteRepository);

		final ChangeSetManager manager = new ChangeSetManager();
		ChangeSet changeSet = manager.createChangeSet(null, Collections
				.singletonList((RemoteContainer) remoteRepository), remoteRepository,
				ChangeSetManager.MODE_CHECKOUT_REPLACE, new NullProgressMonitor());
		changeSet.setTargetCategory(CategoryUtil.findCategory("Inbox", false));
		ChangeSetExecutor executor = new ChangeSetExecutor();
		executor.checkout(changeSet, new NullProgressMonitor());

		Category findCategory = CategoryUtil.findCategory("Inbox", false);
		Category targetCat = findCategory.getChildren().get(0);
		EList<Category> children = targetCat.getChildren();
		EditingDomain createNewEditingDomain = EditingUtil.getInstance().createNewEditingDomain();
		for (Category category2 : children) {
			Command command;
			if (!category2.getLabel().toLowerCase().startsWith("system")) {
				command = CommandFactory.DELETE_SYNCHRONIZABLE_CATEGORY(category2,
						createNewEditingDomain);
			} else {
				command = CommandFactory.DELETE_INFOUNIT(category2.getInformationUnit(),
						createNewEditingDomain);
			}
			createNewEditingDomain.getCommandStack().execute(command);
		}
		changeSet = manager.createChangeSet(targetCat, new NullProgressMonitor());
		ChangeSetItem changeSetItem = changeSet.getChangeSetItems().get(0);
		DiffModel createDiffModel = manager.createDiffModel(changeSetItem, targetCat);
		executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem,
				new NullProgressMonitor(), targetCat);
		Command deleteCATEGORY = CommandFactory.DELETE_CATEGORY(targetCat, createNewEditingDomain);
		createNewEditingDomain.getCommandStack().execute(deleteCATEGORY);
		createNewEditingDomain.getCommandStack().flush();

	}

	protected void createRepository(final String repositoryTypeId) {

		RepositoryCollection repositories = InfomngmntEditPlugin.getPlugin().getService(
				IRepositoryService.class).getRepositories();
		RemoteRepository repository = InfomngmntFactory.eINSTANCE.createRemoteRepository();
		repository.setId(IdFactory.createNewId(null));
		repository.setRepositoryTypeId(getRepositoryTypeId());
		repository.setUrl(repository.getRepositoryImplementation().getRepositoryUrl());
		repository.setName("TEST_" + getRepositoryTypeId());
		repositories.getRepositories().add(repository);

		ICredentialProvider credentialProvider = repository.getRepositoryImplementation()
				.getCredentialProvider();
		configureCredentials(credentialProvider);
		EditingUtil.getInstance().saveObjectToResource(repositories);

	}

	protected abstract String getRepositoryTypeId();

	protected abstract void configureCredentials(ICredentialProvider provider);

}
