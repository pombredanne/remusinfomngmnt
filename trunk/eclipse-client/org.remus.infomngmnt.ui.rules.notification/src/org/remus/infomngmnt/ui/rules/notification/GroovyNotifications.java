/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.rules.notification;

import groovy.lang.Binding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.core.services.INotificationManagerManager;
import org.remus.infomngmnt.ui.notification.NotificationActivator;
import org.remus.infomngmnt.ui.rules.extension.IGroovyBinding;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GroovyNotifications implements IGroovyBinding {
	public static final String SEVERITY = "severityList"; //$NON-NLS-1$
	public static final String TITLE = "titleList"; //$NON-NLS-1$
	public static final String DESCRIPTION = "descriptionList"; //$NON-NLS-1$

	/**
	 * 
	 */
	public GroovyNotifications() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.rules.extension.IGroovyBinding#afterEvaluation
	 * (groovy.lang.Binding, org.remus.infomngmnt.InformationUnit)
	 */
	public Map afterEvaluation(final Binding binding, final InformationUnit createdObject) {
		List<Integer> sevs = (List<Integer>) binding.getVariable(SEVERITY);
		List<String> titles = (List<String>) binding.getVariable(TITLE);
		List<String> description = (List<String>) binding.getVariable(DESCRIPTION);
		List<Notification> notifications = new ArrayList<Notification>();
		for (int i = 0, n = sevs.size(); i < n; i++) {
			Notification createNotification = InfomngmntFactory.eINSTANCE.createNotification();
			createNotification.setSeverity(Severity.get(sevs.get(i)));
			createNotification.setMessage(titles.get(i));
			createNotification.setDetails(description.get(i));
			createNotification.setTimeStamp(new Date());
			notifications.add(createNotification);
		}
		if (notifications.size() > 0) {
			INotificationManagerManager service = NotificationActivator.getDefault()
					.getServiceTracker().getService(INotificationManagerManager.class);
			service.addNotification(notifications);

		}
		return Collections.EMPTY_MAP;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.rules.extension.IGroovyBinding#beforeEvaluation
	 * (groovy.lang.Binding)
	 */
	public void beforeEvaluation(final Binding binding) {
		binding.setVariable(SEVERITY, new ArrayList<Integer>());
		binding.setVariable(TITLE, new ArrayList<String>());
		binding.setVariable(DESCRIPTION, new ArrayList<String>());

	}
}
