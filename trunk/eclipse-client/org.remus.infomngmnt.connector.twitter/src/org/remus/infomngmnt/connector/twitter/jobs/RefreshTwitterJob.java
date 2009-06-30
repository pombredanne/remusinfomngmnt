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

package org.remus.infomngmnt.connector.twitter.jobs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationState;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.TwitterRepository;
import org.remus.infomngmnt.connector.twitter.preferences.TwitterPreferenceInitializer;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.model.ApplicationModelPool;
import org.remus.infomngmnt.core.model.InformationUtil;
import org.remus.infomngmnt.core.sync.SynchronizeSingleElementJob;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RefreshTwitterJob extends AbstractJob {

	/**
	 * 
	 */
	public RefreshTwitterJob() {
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
		List<Notification> returnValue = new ArrayList<Notification>();
		/*
		 * We need the date of the last sync to see if we can add new
		 * notifications
		 */
		Date lastExecution = getLastExecution();
		EObjectAttributeValueCondition typeCondition = new EObjectAttributeValueCondition(
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE, new Condition() {
					@Override
					public boolean isSatisfied(final Object object) {
						return object.equals(TwitterActivator.INFOTYPE_ID);
					}
				});

		EObjectCondition isSynchronizableCondition = new EObjectCondition() {
			@Override
			public boolean isSatisfied(final EObject eObject) {
				return ((SynchronizableObject) eObject).getSynchronizationMetaData() != null
						&& ((SynchronizableObject) eObject).getSynchronizationMetaData()
								.getSyncState() == SynchronizationState.IN_SYNC
						&& ((SynchronizableObject) eObject).getSynchronizationMetaData().getUrl()
								.endsWith("/" + TwitterRepository.ID_FRIENDS);
			}
		};
		SELECT select = new SELECT(new FROM(ApplicationModelPool.getInstance().getModel()
				.getRootCategories()), new WHERE(typeCondition.AND(isSynchronizableCondition)));
		Set<? extends EObject> eObjects = select.execute().getEObjects();
		for (EObject eObject : eObjects) {
			SynchronizeSingleElementJob singleElementJob = new SynchronizeSingleElementJob(
					(InformationUnitListItem) eObject);
			singleElementJob.run(monitor);
			ChangeSet changeSet = singleElementJob.getCreateChangeSet();
			if (changeSet != null && changeSet.getChangeSetItems().size() > 0) {
				Set<SynchronizableObject> keySet = changeSet.getChangeSetItems().get(0)
						.getSyncObjectActionMap().keySet();
				for (SynchronizableObject synchronizableObject : keySet) {
					InformationUnitListItem item = (InformationUnitListItem) synchronizableObject;
					InformationUnit adapter = (InformationUnit) item
							.getAdapter(InformationUnit.class);
					if (adapter != null) {
						EList<InformationUnit> childValues = InformationUtil.getChildByType(
								adapter, TwitterActivator.MESSAGES_ID).getChildValues();
						for (InformationUnit informationUnit : childValues) {
							InformationUnit date = InformationUtil.getChildByType(informationUnit,
									TwitterActivator.MESSAGE_DATE_TYPE);
							if (date != null && date.getDateValue() != null
									&& date.getDateValue().after(lastExecution)) {
								Notification notification = InfomngmntFactory.eINSTANCE
										.createNotification();

								notification.setImage(ResourceManager.getPluginImage(
										TwitterActivator.getDefault(), "/icons/twitter.png"));

								notification.setTimeStamp(new Date());
								notification.setMessage(NLS
										.bind("Updated \"{0}\"", item.getLabel()));
								notification.setDetails(InformationUtil.getChildByType(
										informationUnit, TwitterActivator.MESSAGE_CONTENT_TYPE)
										.getStringValue());
								notification.getAffectedInfoUnitIds().add(item.getId());
								notification.setImportance(NotificationImportance.MEDIUM);
								notification.setSeverity(Severity.OK);
								returnValue.add(notification);
							} else {
								break;
							}
						}
					}
				}

			}

		}
		return returnValue;
	}

	@Override
	public int getInterval() {
		return TwitterActivator.getDefault().getPreferenceStore().getInt(
				TwitterPreferenceInitializer.RELOAD_ALL_FRIENDS_FEED);
	}

}