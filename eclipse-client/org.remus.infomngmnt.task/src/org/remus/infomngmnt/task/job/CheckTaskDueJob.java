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

package org.remus.infomngmnt.task.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.eclipse.remus.InfomngmntFactory;
import org.eclipse.remus.InformationUnit;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.Notification;
import org.eclipse.remus.NotificationImportance;
import org.eclipse.remus.Severity;
import org.eclipse.remus.core.jobs.AbstractJob;
import org.eclipse.remus.util.InformationUtil;

import org.remus.infomngmnt.task.TaskActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckTaskDueJob extends AbstractJob {

	private final List<String> alreadyShown = new ArrayList<String>();

	/**
	 * 
	 */
	public CheckTaskDueJob() {
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
	public List<Notification> run(IProgressMonitor monitor) {
		Set<? extends EObject> allItems = InformationUtil
				.getAllItemsByType(TaskActivator.INFO_TYPE_ID);
		List<Notification> returnValue = new ArrayList<Notification>();
		for (EObject eObject : allItems) {
			if (eObject instanceof InformationUnitListItem) {
				InformationUnit adapter = (InformationUnit) ((InformationUnitListItem) eObject)
						.getAdapter(InformationUnit.class);
				if (adapter != null) {
					boolean notify = Boolean.valueOf(InformationUtil.getChildByType(adapter,
							TaskActivator.NODE_NAME_NOTIFY).getStringValue());
					boolean complete = Boolean.valueOf(InformationUtil.getChildByType(adapter,
							TaskActivator.NODE_NAME_COMPLETED).getStringValue());
					if (notify && !complete) {
						Date dueDate = InformationUtil.getChildByType(adapter,
								TaskActivator.NODE_NAME_DUE_DATE).getDateValue();
						Long longDue = InformationUtil.getChildByType(adapter,
								TaskActivator.NODE_NAME_MINUTES_BEFORE_DUE).getLongValue();
						if (dueDate != null) {
							Calendar instance = Calendar.getInstance();
							instance.setTime(dueDate);
							instance.add(Calendar.MINUTE, (int) (longDue * -1));
							if (instance.getTime().before(new Date())
									&& !this.alreadyShown.contains(adapter.getId())) {
								Notification createNotification = InfomngmntFactory.eINSTANCE
										.createNotification();
								createNotification.setImportance(NotificationImportance.MEDIUM);
								createNotification.setSeverity(Severity.INFO);
								createNotification.setMessage(NLS.bind(
										"Task \"{0}\" needs your attention", adapter.getLabel()));
								createNotification.setDetails("Task is not completed");
								createNotification.getAffectedInfoUnitIds().add(adapter.getId());
								returnValue.add(createNotification);

								this.alreadyShown.add(adapter.getId());
							}
						}
					}
				}
			}
		}
		return returnValue;
	}
}
