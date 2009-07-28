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
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.util.DisposableEditingDomain;
import org.remus.infomngmnt.util.EditingUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteOldEntriesJob extends AbstractJob {

	/**
	 * 
	 */
	public DeleteOldEntriesJob() {
		// TODO Auto-generated constructor stub
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
		EList<RemoteRepository> repositories = UIPlugin.getDefault().getService(
				IRepositoryService.class).getRepositories().getRepositories();
		for (final RemoteRepository remoteRepository : repositories) {
			if (RssActivator.REPOSITORY_ID.equals(remoteRepository.getRepositoryTypeId())) {
				final int parseInt = Integer.parseInt(remoteRepository.getOptions().get(
						RssActivator.REPOSITORY_OPTIONS_DELETE_AFTER_X_DAY));
				SELECT select = new SELECT(new FROM(ApplicationModelPool.getInstance().getModel()
						.getRootCategories()), new WHERE(new EObjectCondition() {
					@Override
					public boolean isSatisfied(final EObject arg0) {
						return arg0.eClass() == InfomngmntPackage.Literals.INFORMATION_UNIT_LIST_ITEM
								&& ((InformationUnitListItem) arg0).getSynchronizationMetaData() != null
								&& remoteRepository.getId().equals(
										((InformationUnitListItem) arg0)
												.getSynchronizationMetaData().getRepositoryId())
								&& ((InformationUnit) ((InformationUnitListItem) arg0)
										.getAdapter(InformationUnit.class)).getCreationDate()
										.getTime()
										+ (parseInt * 1000 * 60 * 60 * 24) > System
										.currentTimeMillis();
					}
				}));
				IQueryResult execute = select.execute();
				Set<? extends EObject> eObjects = execute.getEObjects();
				CompoundCommand compoundCommand = new CompoundCommand();
				DisposableEditingDomain domain = EditingUtil.getInstance().createNewEditingDomain();
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
