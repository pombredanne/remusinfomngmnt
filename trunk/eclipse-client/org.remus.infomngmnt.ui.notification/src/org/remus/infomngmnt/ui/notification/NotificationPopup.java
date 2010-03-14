/*******************************************************************************
 * Copyright (c) 2004, 2008 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.notification;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.common.service.ITrayService;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.editors.EditorUtil;
import org.remus.infomngmnt.ui.popup.AbstractNotificationPopup;
import org.remus.infomngmnt.ui.widgets.ScalingHyperlink;

/**
 * @author Rob Elves
 * @author Mik Kersten
 */
public class NotificationPopup extends AbstractNotificationPopup {

	private static final String NOTIFICATIONS_HIDDEN = " more Notifications";

	private static final int NUM_NOTIFICATIONS_TO_DISPLAY = 4;

	private List<Notification> notifications;

	public NotificationPopup(final Shell parent) {
		super(parent.getDisplay());
	}

	public void setContents(final List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Notification> getNotifications() {
		return new ArrayList<Notification>(this.notifications);
	}

	@Override
	protected void createTitleArea(final Composite parent) {
		super.createTitleArea(parent);
	}

	@Override
	protected String getPopupShellTitle() {
		return "Remus Notifications";
	}

	@Override
	protected Image getPopupShellImage(final int maximumHeight) {
		return ResourceManager.getPluginImage(NotificationActivator.getDefault(),
				"icons/iconexperience/16/signal_flag_red.png");
	}

	@Override
	protected void createContentArea(final Composite parent) {
		int count = 0;
		for (final Notification notification : this.notifications) {
			Composite notificationComposite = new Composite(parent, SWT.NO_FOCUS);
			GridLayout gridLayout = new GridLayout(2, false);
			GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).applyTo(
					notificationComposite);
			notificationComposite.setLayout(gridLayout);
			notificationComposite.setBackground(parent.getBackground());

			if (count < NUM_NOTIFICATIONS_TO_DISPLAY) {

				final NotificationLink itemLink = new NotificationLink(notificationComposite,
						SWT.BEGINNING | SWT.NO_FOCUS);
				GridDataFactory.fillDefaults().grab(true, false).align(SWT.FILL, SWT.TOP).applyTo(
						itemLink);

				itemLink.setText(notification.getMessage());
				if (notification.getImage() != null && notification.getImage() instanceof Image) {
					itemLink.setImage((Image) notification.getImage());
				} else {
					itemLink.setImage(NotificationUtil.getImageBySeverity(notification
							.getSeverity()));
				}
				itemLink.setBackground(parent.getBackground());
				itemLink.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(final HyperlinkEvent e) {
						EditorUtil.openInfoUnit(notification.getAffectedInfoUnitIds().get(0));
						IWorkbenchWindow window = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow();
						if (notification.getAffectedInfoUnitIds().size() > 0) {
							if (getTrayService().isMinimized()) {
								getTrayService().restoreFromTray(window.getShell());
							}
						}
					}
				});

				String descriptionText = null;
				if (notification.getDetails() != null) {
					descriptionText = notification.getDetails();
				}
				if (descriptionText != null && !descriptionText.trim().equals("")) { //$NON-NLS-1$
					Label descriptionLabel = new Label(notificationComposite, SWT.NO_FOCUS);
					descriptionLabel.setText(descriptionText);
					descriptionLabel.setBackground(parent.getBackground());
					GridDataFactory.fillDefaults().span(2, SWT.DEFAULT).grab(true, false).align(
							SWT.FILL, SWT.TOP).applyTo(descriptionLabel);
				}
			} else {
				int numNotificationsRemain = this.notifications.size() - count;
				ScalingHyperlink remainingHyperlink = new ScalingHyperlink(notificationComposite,
						SWT.NO_FOCUS);
				remainingHyperlink.setBackground(parent.getBackground());

				remainingHyperlink.setText(numNotificationsRemain + " " + NOTIFICATIONS_HIDDEN); //$NON-NLS-1$
				GridDataFactory.fillDefaults().span(2, SWT.DEFAULT).applyTo(remainingHyperlink);
				remainingHyperlink.addHyperlinkListener(new HyperlinkAdapter() {
					@Override
					public void linkActivated(final HyperlinkEvent e) {
						// TODO open Notification-View
						// TasksUiUtil.openTasksViewInActivePerspective().setFocus();
						IWorkbenchWindow window = PlatformUI.getWorkbench()
								.getActiveWorkbenchWindow();
						if (window != null) {
							Shell windowShell = window.getShell();
							if (windowShell != null) {
								windowShell.setMaximized(true);
								windowShell.open();
							}
						}
					}
				});
				break;
			}
			count++;
		}
	}

	public ITrayService getTrayService() {
		final BundleContext bundleContext = NotificationActivator.getDefault().getBundle()
				.getBundleContext();
		final ServiceReference serviceReference = bundleContext
				.getServiceReference(ITrayService.class.getName());
		if (serviceReference != null) {
			return (ITrayService) bundleContext.getService(serviceReference);
		}
		return null;

	}
}
