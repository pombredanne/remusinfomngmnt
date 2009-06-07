/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *     Tom Seidel - Added compatibility to RIM-Notification Model
 *******************************************************************************/

package org.remus.infomngmnt.ui.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Rob Elves
 * @author Tom Seidel
 */
public class NotificationPopupManager implements IPropertyChangeListener {

	private static final long DELAY_OPEN = 5 * 1000;

	private static final boolean runSystem = true;

	private NotificationPopup popup;

	private final Set<Notification> notifications = new HashSet<Notification>();

	private final Set<Notification> currentlyNotifying = Collections
			.synchronizedSet(this.notifications);

	private final WeakHashMap<Object, Object> cancelledTokens = new WeakHashMap<Object, Object>();

	private final Adapter notifyChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			if (msg.getEventType() == org.eclipse.emf.common.notify.Notification.ADD) {
				NotificationPopupManager.this.currentlyNotifying.add((Notification) msg
						.getNewValue());
			}

		};
	};

	private final Job openJob = new Job("open notificationpopup job") {
		@Override
		protected IStatus run(final IProgressMonitor monitor) {
			try {

				if (Platform.isRunning() && PlatformUI.getWorkbench() != null
						&& PlatformUI.getWorkbench().getDisplay() != null
						&& !PlatformUI.getWorkbench().getDisplay().isDisposed()) {
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

						public void run() {

							if (NotificationPopupManager.this.popup != null
									&& NotificationPopupManager.this.popup.getReturnCode() == Window.CANCEL) {
								List<Notification> notifications = NotificationPopupManager.this.popup
										.getNotifications();
								for (Notification notification : notifications) {
									if (notification.getTimeStamp() != null) {
										NotificationPopupManager.this.cancelledTokens.put(
												notification.getTimeStamp(), null);
									}
								}
							}

							for (Iterator<Notification> it = NotificationPopupManager.this.currentlyNotifying
									.iterator(); it.hasNext();) {
								Notification notification = it.next();
								if (notification.getTimeStamp() != null
										&& NotificationPopupManager.this.cancelledTokens
												.containsKey(notification.getTimeStamp())) {
									it.remove();
								}
							}

							synchronized (NotificationPopupManager.class) {
								if (NotificationPopupManager.this.currentlyNotifying.size() > 0) {
									// popup.close();
									showPopup();
								}
							}
						}
					});
				}
			} finally {
				if (NotificationPopupManager.this.popup != null) {
					schedule(NotificationPopupManager.this.popup.getDelayClose() / 2);
				} else {
					schedule(DELAY_OPEN);
				}
			}

			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			return Status.OK_STATUS;
		}

	};

	private INotificationManagerManager service;

	public NotificationPopupManager() {
	}

	public void showPopup() {
		if (this.popup != null) {
			this.popup.close();
		}

		Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay());
		this.popup = new NotificationPopup(shell);
		this.popup.setFadingEnabled(true);
		List<Notification> toDisplay = new ArrayList<Notification>(this.currentlyNotifying);
		Collections.sort(toDisplay, new Comparator<Notification>() {
			public int compare(final Notification arg0, final Notification arg1) {
				if (arg0.getTimeStamp() == null && arg1.getTimeStamp() == null) {
					return 0;
				}
				if (arg0.getTimeStamp() != null) {
					return -1;
				}

				return arg0.getTimeStamp().compareTo(arg1.getTimeStamp());
			}
		});
		this.popup.setContents(toDisplay);
		cleanNotified();
		this.popup.setBlockOnOpen(false);
		this.popup.open();
	}

	private void cleanNotified() {
		this.currentlyNotifying.clear();
	}

	public void startNotification(final long initialStartupTime) {
		this.service = UIPlugin.getDefault().getService(INotificationManagerManager.class);
		this.service.getAllNotifications().eAdapters().add(this.notifyChangeAdapter);
		if (true) {
			if (!this.openJob.cancel()) {
				try {
					this.openJob.join();
				} catch (InterruptedException e) {
					// ignore
				}
			}
			this.openJob.setSystem(runSystem);
			this.openJob.schedule(initialStartupTime);
		}
	}

	public void stopNotification() {
		this.openJob.cancel();
		this.service.getAllNotifications().eAdapters().remove(this.notifyChangeAdapter);
		// closeJob.cancel();
		// if (popup != null) {
		// popup.close();
		// }
	}

	/**
	 * public for testing purposes
	 */
	public Set<Notification> getNotifications() {
		synchronized (NotificationPopupManager.class) {
			return this.currentlyNotifying;
		}
	}

	public void propertyChange(final PropertyChangeEvent event) {
		// if
		// (event.getProperty().equals(ITasksUiPreferenceConstants.NOTIFICATIONS_ENABLED))
		// {
		// Object newValue = event.getNewValue();
		// if (!(newValue instanceof Boolean)) {
		// // default if no preference value
		// startNotification(0);
		// } else if ((Boolean) newValue == true) {
		// startNotification(0);
		// } else {
		// stopNotification();
		// }
		// }
	}
}
