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
package org.remus.infomngmnt.connector.rss.jobs;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.remus.Category;
import org.eclipse.remus.ChangeSet;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.Notification;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.RepositoryCollection;
import org.eclipse.remus.core.edit.DisposableEditingDomain;
import org.eclipse.remus.core.jobs.AbstractJob;
import org.eclipse.remus.core.remote.AbstractExtensionRepository;
import org.eclipse.remus.core.remote.services.IRepositoryExtensionService;
import org.eclipse.remus.core.remote.services.IRepositoryService;
import org.eclipse.remus.core.remote.sync.AbstractSynchronizationJob;
import org.eclipse.remus.core.remote.sync.ChangeSetException;
import org.eclipse.remus.core.remote.sync.ChangeSetExecutor;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.model.remote.IChangeHandler;
import org.eclipse.remus.services.RemusServiceTracker;
import org.eclipse.remus.util.CategoryUtil;
import org.eclipse.remus.util.IdFactory;
import org.remus.infomngmnt.connector.rss.Messages;
import org.remus.infomngmnt.connector.rss.PreferenceInitializer;
import org.remus.infomngmnt.connector.rss.RssActivator;
import org.remus.infomngmnt.connector.rss.RssCredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FirstStartJob extends AbstractJob {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.remus.core.jobs.AbstractJob#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public List<Notification> run(IProgressMonitor monitor) {
		if (RssActivator.getDefault().getPreferenceStore()
				.getBoolean(PreferenceInitializer.FIRST_START)) {

			RemusServiceTracker remusServiceTracker = new RemusServiceTracker(
					RssActivator.getDefault().getBundle());
			final Category findCategory = CategoryUtil.findCategory("Inbox", //$NON-NLS-1$
					false);
			IRepositoryService service = remusServiceTracker
					.getService(IRepositoryService.class);
			RepositoryCollection repositoryCollection = service
					.getRepositories();
			RemoteRepository createRemoteRepository = InfomngmntFactory.eINSTANCE
					.createRemoteRepository();

			createRemoteRepository.setId(IdFactory.createNewId(null));
			createRemoteRepository
					.setRepositoryTypeId("org.remus.infomngmnt.connector.rss"); //$NON-NLS-1$
			createRemoteRepository.setName(Messages.FirstStartJob_BlogName);
			createRemoteRepository.setUrl(Messages.FirstStartJob_BlogUrl);
			createRemoteRepository.getOptions().put(
					RssActivator.REPOSITORY_OPTIONS_REFRESH_INTERVAL, "60"); //$NON-NLS-1$
			createRemoteRepository.getOptions().put(
					RssActivator.REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY, "50"); //$NON-NLS-1$
			IEditingHandler editingHandler = remusServiceTracker
					.getService(IEditingHandler.class);
			DisposableEditingDomain domain = editingHandler
					.createNewEditingDomain();
			Command create = AddCommand
					.create(domain,
							repositoryCollection,
							InfomngmntPackage.Literals.REPOSITORY_COLLECTION__REPOSITORIES,
							createRemoteRepository);
			domain.getCommandStack().execute(create);
			domain.dispose();
			editingHandler.saveObjectToResource(createRemoteRepository);
			final IChangeHandler manager = remusServiceTracker
					.getService(IChangeHandler.class);
			IRepositoryExtensionService extensionService = remusServiceTracker
					.getService(IRepositoryExtensionService.class);
			try {
				AbstractExtensionRepository itemByRepository = extensionService
						.getItemByRepository(createRemoteRepository);
				((RssCredentialProvider) itemByRepository
						.getCredentialProvider())
						.setUrl(Messages.FirstStartJob_BlogUrl);
				List list = Collections.singletonList(createRemoteRepository);
				final ChangeSet createChangeSet = manager.createChangeSet(null,
						list, null, 2/*
									 * ChangeSetManager . MODE_CHECKOUT_REPLACE
									 */, new NullProgressMonitor());
				AbstractSynchronizationJob job2 = new AbstractSynchronizationJob(
						Messages.FirstStartJob_Checkout,
						Collections.singletonList(findCategory)) {

					@Override
					protected IStatus doRun(final IProgressMonitor monitor) {
						createChangeSet.setTargetCategory(findCategory);
						ChangeSetExecutor executor = new ChangeSetExecutor();
						executor.checkout(createChangeSet, monitor);
						return Status.OK_STATUS;
					}
				};
				job2.doPrepare();
				job2.setUser(true);
				job2.schedule(2000);
			} catch (ChangeSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				RssActivator.getDefault().getPreferenceStore()
						.setValue(PreferenceInitializer.FIRST_START, false);
			}
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public int getInterval() {
		return Integer.MAX_VALUE;
	}
}
