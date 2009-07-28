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

package org.remus.infomngmnt.ui.notification;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationCollection;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.common.ui.UIUtil;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.ui.UIPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationTrimArea extends WorkbenchWindowControlContribution {

	private final Map<String, Notification> notifications2Show = new LinkedHashMap<String, Notification>();

	private final Adapter notificationListener = new EContentAdapter() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			super.notifyChanged(msg);
			if (msg.getEventType() == org.eclipse.emf.common.notify.Notification.SET
					&& msg.getFeature() == InfomngmntPackage.Literals.NOTIFICATION__NOTICED) {
				UIUtil.getDisplay().asyncExec(new Runnable() {
					public void run() {
						buildHyperLink();
					}
				});
			} else if (msg.getEventType() == org.eclipse.emf.common.notify.Notification.ADD
					|| msg.getEventType() == org.eclipse.emf.common.notify.Notification.ADD_MANY
					|| msg.getEventType() == org.eclipse.emf.common.notify.Notification.REMOVE
					|| msg.getEventType() == org.eclipse.emf.common.notify.Notification.REMOVE_MANY) {
				UIUtil.getDisplay().asyncExec(new Runnable() {
					public void run() {
						buildHyperLink();
					}
				});
			}
		};
	};

	private NotificationCollection allNotifications;
	private ImageHyperlink hyperLink;

	/**
	 * 
	 */
	public NotificationTrimArea() {
		// TODO Auto-generated constructor stub
	}

	protected void buildHyperLink() {
		EList<Notification> notifcations = this.allNotifications.getNotifcations();
		this.notifications2Show.clear();
		int i = 0;
		for (Notification notification : notifcations) {
			if (!notification.isNoticed()
					&& notification.getImportance() != NotificationImportance.LOW
					&& notification.getAffectedInfoUnitIds().size() > 0) {
				if (this.notifications2Show.containsKey(notification.getAffectedInfoUnitIds()
						.get(0))) {
					this.notifications2Show.get(notification.getAffectedInfoUnitIds().get(0))
							.setNoticed(true);
				}
				this.notifications2Show.put(notification.getAffectedInfoUnitIds().get(0),
						notification);
				i++;
			}
		}
		if (!this.hyperLink.isDisposed()) {
			if (i == 0) {
				this.hyperLink.setImage(NotificationUtil.getImageBySeverity(Severity.OK));
				this.hyperLink.setText("No unread notifications");
			} else {
				this.hyperLink.setImage(NotificationUtil.getImageBySeverity(Severity.INFO));
				this.hyperLink.setText(NLS.bind("{0} unread notifications", i));
			}
		}

	}

	/**
	 * @param id
	 */
	public NotificationTrimArea(final String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.action.ControlContribution#createControl(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected Control createControl(final Composite parent) {
		this.allNotifications = UIPlugin.getDefault().getService(INotificationManagerManager.class)
				.getAllNotifications();
		this.allNotifications.eAdapters().add(this.notificationListener);
		this.hyperLink = new ImageHyperlink(parent, SWT.NONE);

		this.hyperLink.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		this.hyperLink.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(final MouseEvent e) {
				((ImageHyperlink) e.widget).setUnderlined(true);
			}

			@Override
			public void mouseExit(final MouseEvent e) {
				((ImageHyperlink) e.widget).setUnderlined(false);
			}
		});
		this.hyperLink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(final HyperlinkEvent e) {
				new NotificationDialog(parent.getShell(), parent,
						NotificationTrimArea.this.notifications2Show.values()).open();
			}
		});
		buildHyperLink();
		return this.hyperLink;

	}

	@Override
	public void dispose() {
		this.allNotifications.eAdapters().remove(this.notificationListener);
		super.dispose();
	}

	@Override
	protected int computeWidth(final Control control) {
		return 150;
	}

}
