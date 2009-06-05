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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormText;

import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationCollection;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.preference.UIPreferenceInitializer;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NotificationTraySection extends AbstractTraySection {

	private final Adapter notifyChangeAdapter = new AdapterImpl() {
		@Override
		public void notifyChanged(final org.eclipse.emf.common.notify.Notification msg) {
			buildList(NotificationTraySection.this.formText, NotificationTraySection.this.service
					.getAllNotifications());
		};
	};
	private INotificationManagerManager service;
	private FormText formText;

	@Override
	public void createDetailsPart(final Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		this.service = UIPlugin.getDefault().getService(INotificationManagerManager.class);
		this.formText = this.toolkit.createFormText(parent, false);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.widthHint = 150;
		layoutData.heightHint = 100;
		this.formText.setLayoutData(layoutData);
		List<Severity> values = Severity.VALUES;
		for (Severity severity : values) {
			this.formText.setImage(severity.getName(), NotificationUtil
					.getImageBySeverity(severity));
		}
		buildList(this.formText, this.service.getAllNotifications());
		this.service.getAllNotifications().eAdapters().add(this.notifyChangeAdapter);
	}

	private void buildList(final FormText formText2,
			final NotificationCollection notificationCollection) {
		List<Notification> arrayList = new ArrayList<Notification>(notificationCollection
				.getNotifcations());
		Collections.reverse(arrayList);
		StringWriter sw = new StringWriter();
		sw.append("<form>");
		int count = UIPlugin.getDefault().getPreferenceStore().getInt(
				UIPreferenceInitializer.AMOUNT_SHOWN_NOTIFICATIONS_TRAY);
		if (arrayList.size() == 0 || count == 0) {
			sw.append("<p>No notifications</p>");
		} else {
			for (int i = 0, n = count; i < n; i++) {
				Notification notification = arrayList.get(i);
				sw.append("<p><img href=\"").append(notification.getSeverity().getName()).append(
						"\"/><a>");
				if (notification.getImportance() == NotificationImportance.HIGH) {
					sw.append("<b>");
				}
				sw.append(StringEscapeUtils.escapeXml(notification.getMessage()));
				if (notification.getImportance() == NotificationImportance.HIGH) {
					sw.append("</b>");
				}
				sw.append("</a></p>");
			}
		}
		sw.append("</form>");
		formText2.setText(sw.toString(), true, false);

	}

	@Override
	public void dispose() {
		if (this.service != null) {
			this.service.getAllNotifications().eAdapters().remove(this.notifyChangeAdapter);
		}
		super.dispose();
	}

}
