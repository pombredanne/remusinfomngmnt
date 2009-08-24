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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.IQueryResult;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.connector.rss.RssActivator;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.core.sync.AbstractSynchronizationJob;
import org.remus.infomngmnt.core.sync.SyncUtil;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RefreshRssJob extends AbstractJob {

	private final Map<String, Date> lastRefresh;

	/**
	 * 
	 */
	public RefreshRssJob() {
		this.lastRefresh = new HashMap<String, Date>();
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
					SELECT select = new SELECT(new FROM(ApplicationModelPool.getInstance()
							.getModel().getRootCategories()), new WHERE(new EObjectCondition() {
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
						AbstractSynchronizationJob job = new AbstractSynchronizationJob(NLS.bind(
								"Refreshing RSS \'\'{0}\'\'", ((Category) eObject).getLabel())) {
							@Override
							protected List<? extends SynchronizableObject> getAffectedObjects() {
								return Collections.singletonList((Category) eObject);
							}

							@Override
							protected IStatus run(final IProgressMonitor monitor) {
								try {
									Notification synchronizeCategory = SyncUtil
											.synchronizeCategory((Category) eObject, monitor);
									UIPlugin.getDefault().getService(
											INotificationManagerManager.class).addNotification(
											synchronizeCategory.getChildren());
								} catch (CoreException e) {
									// we do nothing here.
								}
								return Status.OK_STATUS;
							}
						};
						job.doPrepare();
						job.schedule();
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
