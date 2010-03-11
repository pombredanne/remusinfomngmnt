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

package org.remus.infomngmnt.connector.rss.jobs;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.connector.rss.RssActivator;
import org.remus.infomngmnt.core.commands.CommandFactory;
import org.remus.infomngmnt.core.edit.DisposableEditingDomain;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteOldEntriesJob extends AbstractJob {

	private final RemusServiceTracker remusServiceTracker;

	/**
	 * 
	 */
	public DeleteOldEntriesJob() {
		this.remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(RssActivator.PLUGIN_ID));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.jobs.AbstractJob#run(org.eclipse.core.runtime
	 * .IProgressMonitor)
	 */
	@Override
	public List<Notification> run(final IProgressMonitor monitor) {
		EList<RemoteRepository> repositories = this.remusServiceTracker.getService(
				IRepositoryService.class).getRepositories().getRepositories();
		IApplicationModel applicationModel = this.remusServiceTracker
				.getService(IApplicationModel.class);
		IEditingHandler editService = this.remusServiceTracker.getService(IEditingHandler.class);
		for (final RemoteRepository remoteRepository : repositories) {
			if (RssActivator.REPOSITORY_ID.equals(remoteRepository.getRepositoryTypeId())) {
				final int parseInt = Integer.parseInt(remoteRepository.getOptions().get(
						RssActivator.REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY));
				SELECT select = new SELECT(
						new FROM(applicationModel.getModel().getRootCategories()), new WHERE(
								new EObjectCondition() {
									@Override
									public boolean isSatisfied(final EObject arg0) {
										return arg0.eClass() == InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM
												&& ((InformationUnitListItem) arg0)
														.getSynchronizationMetaData() != null
												&& remoteRepository.getId().equals(
														((InformationUnitListItem) arg0)
																.getSynchronizationMetaData()
																.getRepositoryId())
												&& ((InformationUnit) ((InformationUnitListItem) arg0)
														.getAdapter(InformationUnit.class)) != null
												&& ((InformationUnit) ((InformationUnitListItem) arg0)
														.getAdapter(InformationUnit.class))
														.getCreationDate().getTime()
														+ (Long.valueOf(parseInt) * 1000L * 60L * 60L * 24L) < System
														.currentTimeMillis();
									}
								}));
				IQueryResult execute = select.execute();
				Set<? extends EObject> eObjects = execute.getEObjects();
				CompoundCommand compoundCommand = new CompoundCommand();
				DisposableEditingDomain domain = editService.createNewEditingDomain();
				for (EObject eObject : eObjects) {
					compoundCommand.append(CommandFactory.DELETE_INFOUNIT(Collections
							.singletonList((InformationUnitListItem) eObject), domain));
				}
				if (compoundCommand.canExecute()) {
					domain.getCommandStack().execute(compoundCommand);
				}
				domain.getCommandStack().flush();
				domain.dispose();
			}
		}
		return null;
	}

}
