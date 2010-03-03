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

package org.remus.infomngmnt.core.remote.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.util.ModelUtil;
import org.remus.infomngmnt.core.remote.services.ISynchronizationManager;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.model.remote.IChangeHandler;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizationManager implements ISynchronizationManager {

	private INotificationManagerManager notificationManager;
	private IChangeHandler changeHandler;

	private final List<SynchronizableObject> scheduledElements = Collections
			.synchronizedList(new ArrayList<SynchronizableObject>());

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.core.services.ISynchronizationManager#
	 * scheduleSynchronization(org.remus.infomngmnt.Category)
	 */
	public synchronized void scheduleSynchronization(final Category category) {
		if (!this.scheduledElements.contains(category)
				&& !ModelUtil.containsParent(this.scheduledElements, category)) {
			this.scheduledElements.add(category);
			CreateChangeSetJob createChangeSetJob = new CreateChangeSetJob(this.changeHandler,
					category, false);
			createChangeSetJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(final IJobChangeEvent event) {
					CreateChangeSetJob job = (CreateChangeSetJob) event.getJob();
					if (job.getChangeSet() != null) {
						PerformUpdateJob updateJob = new PerformUpdateJob(
								SynchronizationManager.this.changeHandler, job.getChangeSet());
						updateJob.addJobChangeListener(new JobChangeAdapter() {
							@Override
							public void done(final IJobChangeEvent event) {
								SynchronizationManager.this.scheduledElements.remove(category);
								Notification notification = ((PerformUpdateJob) event.getJob())
										.getNotification();
								SynchronizationManager.this.notificationManager
										.addNotification(notification.getChildren());
							}
						});
						updateJob.schedule();
					} else {
						SynchronizationManager.this.scheduledElements.remove(category);
					}
				}
			});
			createChangeSetJob.schedule();
		}
	}

	public void scheduleSynchronization(final InformationUnitListItem singleItem) {
		if (!this.scheduledElements.contains(singleItem)
				&& !ModelUtil.containsParent(this.scheduledElements, singleItem)) {
			this.scheduledElements.add(singleItem);
			CreateChangeSetJob createChangeSetJob = new CreateChangeSetJob(this.changeHandler,
					singleItem, false);
			createChangeSetJob.addJobChangeListener(new JobChangeAdapter() {
				@Override
				public void done(final IJobChangeEvent event) {
					CreateChangeSetJob job = (CreateChangeSetJob) event.getJob();
					if (job.getChangeSet() != null) {
						PerformUpdateJob updateJob = new PerformUpdateJob(
								SynchronizationManager.this.changeHandler, job.getChangeSet());
						updateJob.addJobChangeListener(new JobChangeAdapter() {
							@Override
							public void done(final IJobChangeEvent event) {
								SynchronizationManager.this.scheduledElements.remove(singleItem);
								Notification notification = ((PerformUpdateJob) event.getJob())
										.getNotification();
								SynchronizationManager.this.notificationManager
										.addNotification(notification.getChildren());
							}
						});
						updateJob.schedule();

					} else {
						SynchronizationManager.this.scheduledElements.remove(singleItem);
					}
				}
			});
			createChangeSetJob.schedule();
		}

	}

	public void setNotificationManager(final INotificationManagerManager manger) {
		this.notificationManager = manger;

	}

	public void setChangeHandler(final IChangeHandler manger) {
		this.changeHandler = manger;
	}

}
