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

package org.remus.infomngmnt.contact.ui.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.contact.ContactActivator;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.util.InformationUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CheckBirthdayJob extends AbstractJob {

	private final List<String> alreadyShown = new ArrayList<String>();

	/**
	 * 
	 */
	public CheckBirthdayJob() {
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
	public List<Notification> run(final IProgressMonitor monitor) {
		Set<? extends EObject> allItems = InformationUtil
				.getAllItemsByType(ContactActivator.TYPE_ID);
		List<Notification> returnValue = new ArrayList<Notification>();
		for (EObject eObject : allItems) {
			if (eObject instanceof InformationUnitListItem) {
				InformationUnit adapter = (InformationUnit) ((InformationUnitListItem) eObject)
						.getAdapter(InformationUnit.class);
				if (adapter != null) {
					InformationStructureRead read = InformationStructureRead.newSession(adapter);
					Date birthDay = (Date) read
							.getValueByNodeId(ContactActivator.NODE_DETAILS_BIRTHDAY);
					if (birthDay != null) {
						Calendar instance = Calendar.getInstance();
						instance.setTime(birthDay);

						Calendar today = Calendar.getInstance();
						today.setTime(new Date());
						if (instance.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
								&& instance.get(Calendar.MONTH) == today.get(Calendar.MONTH)
								&& !this.alreadyShown.contains(adapter.getId())) {

							Notification createNotification = InfomngmntFactory.eINSTANCE
									.createNotification();
							createNotification.setImportance(NotificationImportance.MEDIUM);
							// createNotification.setImage(InformationExtensionManager.getInstance()
							// .getInfoTypeByType(ContactActivator.TYPE_ID).getImage());
							createNotification.setSeverity(Severity.INFO);
							createNotification.setMessage(NLS.bind(
									"Contact \"{0}\" needs your attention", adapter.getLabel()));
							createNotification.setDetails("Reason: Contact has birthday");
							createNotification.getAffectedInfoUnitIds().add(adapter.getId());
							returnValue.add(createNotification);

							this.alreadyShown.add(adapter.getId());
						}
					}
				}
			}
		}
		return returnValue;
	}
}
