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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.remus.Category;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.Notification;
import org.eclipse.remus.RemoteRepository;
import org.eclipse.remus.core.jobs.AbstractJob;
import org.eclipse.remus.core.remote.services.IRepositoryService;
import org.eclipse.remus.core.remote.services.ISynchronizationManager;
import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.services.RemusServiceTracker;

import org.remus.infomngmnt.connector.rss.RssActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RefreshRssJob extends AbstractJob {

	private final Map<String, Date> lastRefresh;
	private final RemusServiceTracker remusServiceTracker;

	/**
	 * 
	 */
	public RefreshRssJob() {
		this.lastRefresh = new HashMap<String, Date>();
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
		Notification returnValue = InfomngmntFactory.eINSTANCE.createNotification();
		for (final RemoteRepository remoteRepository : repositories) {
			if (RssActivator.REPOSITORY_ID.equals(remoteRepository.getRepositoryTypeId())) {
				boolean refresh = false;
				if (this.lastRefresh.get(remoteRepository.getId()) != null) {
					Date date = this.lastRefresh.get(remoteRepository.getId());
					int parseInt = Integer.parseInt(remoteRepository.getOptions().get(
							RssActivator.REPOSITORY_OPTIONS_REFRESH_INTERVAL));
					if (date.getTime() + (parseInt * 60 * 1000) <= System.currentTimeMillis()) {
						refresh = true;
					}
				} else {
					refresh = true;
				}
				if (refresh) {
					IApplicationModel applicationModel = this.remusServiceTracker
							.getService(IApplicationModel.class);
					ISynchronizationManager syncService = this.remusServiceTracker
							.getService(ISynchronizationManager.class);
					SELECT select = new SELECT(new FROM(applicationModel.getModel()
							.getRootCategories()), new WHERE(new EObjectCondition() {
						@Override
						public boolean isSatisfied(final EObject arg0) {
							return arg0.eClass() == InfomngmntPackage.Literals.CATEGORY
									&& ((Category) arg0).getSynchronizationMetaData() != null
									&& remoteRepository.getId().equals(
											((Category) arg0).getSynchronizationMetaData()
													.getRepositoryId());
						}
					}));
					IQueryResult execute = select.execute();
					Set<? extends EObject> eObjects = execute.getEObjects();
					for (final EObject eObject : eObjects) {
						syncService.scheduleSynchronization((Category) eObject);
					}
					this.lastRefresh.put(remoteRepository.getId(), new Date());
				}
			}
		}
		return returnValue.getChildren();
	}

	@Override
	public int getInterval() {
		return 1;
	}

}
