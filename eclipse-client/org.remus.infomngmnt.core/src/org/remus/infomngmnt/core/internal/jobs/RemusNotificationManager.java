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

package org.remus.infomngmnt.core.internal.jobs;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.NotificationCollection;
import org.remus.infomngmnt.core.edit.DisposableEditingDomain;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.core.services.IJobExtensionService;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemusNotificationManager implements INotificationManagerManager {

	public static final String NOTIFCIATION_PATH = "notification/noticications.xml"; //$NON-NLS-1$

	private NotificationCollection allNotifications;

	private transient boolean initialized;

	public RemusNotificationManager() {
		this.initialized = false;

	}

	protected synchronized void checkForInitialization() {
		if (!this.initialized) {
			init();
			this.initialized = true;
		}
	}

	private final Job job = new Job("Checking for jobs to run") {
		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			IJobExtensionService service = InfomngmntEditPlugin.getPlugin().getService(
					IJobExtensionService.class);
			if (service != null) {
				Collection<AbstractJob> allJobs = service.getAllJobs();
				monitor.beginTask("Initializing jobs", allJobs.size());
				for (AbstractJob abstractJob : allJobs) {
					Date now = new Date();
					Date lastExecution = abstractJob.getLastExecution();
					if (lastExecution == null
							|| (now.getTime() - lastExecution.getTime()) > (abstractJob
									.getInterval() * 1000 * 60)) {
						monitor.subTask(NLS.bind("Executing \"{0}\"", abstractJob.getName()));
						IProgressMonitor subProgressMonitor = new SubProgressMonitor(monitor,
								IProgressMonitor.UNKNOWN);
						try {
							abstractJob.beforeRun(subProgressMonitor);
							List<org.remus.infomngmnt.Notification> run = abstractJob
									.run(subProgressMonitor);
							if (run != null) {
								addNotification(run);
							}
							abstractJob.afterRun(subProgressMonitor);
							abstractJob.setLastExecution(new Date());
						} catch (Exception e) {
							// Job throw any exception. continue.
						}
					}
					monitor.worked(1);

				}
				schedule(1000 * 60);
			}

			monitor.done();
			return Status.OK_STATUS;
		};
	};

	public void addNotification(final List<org.remus.infomngmnt.Notification> run) {
		checkForInitialization();
		for (org.remus.infomngmnt.Notification notification : run) {
			// FIXME
			// if ((this.allNotifications.getNotifcations().size() - 1) ==
			// InfomngmntEditPlugin
			// .getPlugin().getPreferenceStore().getInt(
			// PreferenceInitializer.MAX_SAVED_NOTIFICATIONS)) {
			// this.allNotifications.getNotifcations().remove(
			// this.allNotifications.getNotifcations().size() - 1);
			// }
			IEditingHandler service = InfomngmntEditPlugin.getPlugin().getServiceTracker()
					.getService(IEditingHandler.class);
			DisposableEditingDomain createNewEditingDomain = service.createNewEditingDomain();
			Command create = AddCommand.create(createNewEditingDomain, this.allNotifications,
					InfomngmntPackage.Literals.NOTIFICATION_COLLECTION__NOTIFCATIONS, Collections
							.singletonList(EcoreUtil.copy(notification)), 0);
			createNewEditingDomain.getCommandStack().execute(create);
			createNewEditingDomain.dispose();
		}

	}

	public void init() {

		IPath append = InfomngmntEditPlugin.getPlugin().getStateLocation()
				.append(NOTIFCIATION_PATH);
		boolean exists = !append.toFile().exists();
		if (!exists) {
			File file = append.toFile();
			file.getParentFile().mkdirs();
		}
		this.allNotifications = InfomngmntEditPlugin.getPlugin().getEditService()
				.getObjectFromFileUri(URI.createFileURI(append.toOSString()),
						InfomngmntPackage.Literals.NOTIFICATION_COLLECTION, null);
		this.allNotifications.eAdapters().add(new EContentAdapter() {
			@Override
			public void notifyChanged(final Notification msg) {
				super.notifyChanged(msg);
				if (msg.getNotifier() instanceof ResourceImpl) {
					return;
				}
				InfomngmntEditPlugin.getPlugin().getEditService().saveObjectToResource(
						RemusNotificationManager.this.allNotifications);
			}
		});

		Job job = new Job("Initializing Job Manager") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				IJobExtensionService service = InfomngmntEditPlugin.getPlugin().getService(
						IJobExtensionService.class);
				if (service != null) {
					Collection<AbstractJob> allJobs = service.getAllJobs();
					monitor.beginTask("Initializing jobs", allJobs.size());
					for (AbstractJob abstractJob : allJobs) {
						IProgressMonitor subProgressMonitor = new SubProgressMonitor(monitor,
								IProgressMonitor.UNKNOWN);
						monitor.subTask(NLS.bind("Initializing \"{0}\"", abstractJob.getName()));
						abstractJob.initialize(subProgressMonitor);
						monitor.worked(1);
					}
				}
				RemusNotificationManager.this.job.setSystem(true);
				RemusNotificationManager.this.job.schedule();
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();
	}

	/**
	 * @return the allNotifications
	 */
	public NotificationCollection getAllNotifications() {
		checkForInitialization();
		return this.allNotifications;
	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
