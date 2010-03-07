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

import java.util.Date;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.NotificationImportance;
import org.remus.infomngmnt.Severity;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.sync.ChangeSetExecutor;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;
import org.remus.infomngmnt.model.remote.IChangeHandler;
import org.remus.infomngmnt.util.CategoryUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class PerformUpdateJob extends Job {

	private final IChangeHandler changeSetManager;
	private final ChangeSet changeSet;
	private final Notification notification;
	private final IInformationTypeHandler informationTypeHandler;

	public PerformUpdateJob(final IChangeHandler manager, final ChangeSet changeSet) {
		super(NLS.bind("Performing update on element \'\'{0}\'\'", changeSet.getTargetCategory()
				.getLabel()));
		this.changeSetManager = manager;
		this.changeSet = changeSet;
		this.notification = InfomngmntFactory.eINSTANCE.createNotification();
		this.informationTypeHandler = RemoteActivator.getDefault().getServiceTracker().getService(
				IInformationTypeHandler.class);
		setRule(CategoryUtil.getProjectByCategory(changeSet.getTargetCategory()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		if (this.changeSet != null && this.changeSet.getChangeSetItems().size() > 0) {
			ChangeSetExecutor executor = new ChangeSetExecutor();
			try {
				ChangeSetItem changeSetItem = this.changeSet.getChangeSetItems().get(0);
				DiffModel createDiffModel = this.changeSetManager.createDiffModel(changeSetItem,
						this.changeSet.getTargetCategory());
				this.changeSetManager.prepareSyncActions(createDiffModel.getOwnedElements(),
						changeSetItem, this.changeSet.getTargetCategory());
				this.changeSetManager.updateFromRemote(changeSetItem);
				executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem, monitor,
						this.changeSet.getTargetCategory());
				Set<SynchronizableObject> keySet = changeSetItem.getSyncObjectActionMap().keySet();
				for (SynchronizableObject synchronizableObject : keySet) {
					Notification notification = InfomngmntFactory.eINSTANCE.createNotification();
					InformationUnitListItem item = (InformationUnitListItem) synchronizableObject;
					IInfoType infoType = this.informationTypeHandler.getInfoTypeByType(item
							.getType());
					if (infoType != null) {
						// FIXME
						// notification.setImage(infoType.getImage());
					}
					notification.setTimeStamp(new Date());
					SynchronizationAction synchronizationAction = changeSetItem
							.getSyncObjectActionMap().get(synchronizableObject);
					switch (synchronizationAction) {
					case ADD_LOCAL:
						notification.setMessage(NLS.bind("NEW: \"{0}\"", item.getLabel()));
						notification.getAffectedInfoUnitIds().add(item.getId());
						break;
					case REPLACE_LOCAL:
						notification.setMessage(NLS.bind("UPDATED: \"{0}\"", item.getLabel()));
						notification.getAffectedInfoUnitIds().add(item.getId());
						break;
					case DELETE_LOCAL:
						notification.setMessage(NLS.bind("DELETED: \"{0}\"", item.getLabel()));
						break;
					case REPLACE_REMOTE:
						notification.setMessage(NLS
								.bind("REMOTE UPDATED: \"{0}\"", item.getLabel()));
						notification.getAffectedInfoUnitIds().add(item.getId());
						break;
					case ADD_REMOTE:
						notification.setMessage(NLS.bind("REMOTE NEW: \"{0}\"", item.getLabel()));
						notification.getAffectedInfoUnitIds().add(item.getId());
						break;
					case DELETE_REMOTE:
						notification.setMessage(NLS
								.bind("REMOTE DELETED: \"{0}\"", item.getLabel()));
						break;
					default:
						break;
					}
					notification.setImportance(NotificationImportance.MEDIUM);
					notification.setSeverity(Severity.OK);
					this.notification.getChildren().add(notification);
				}
			} catch (Exception e) {
				// we'll ignore any exceptions here.
			} finally {
				executor.dispose();
			}
		}
		return Status.OK_STATUS;
	}

	/**
	 * @return the notification
	 */
	public final Notification getNotification() {
		return this.notification;
	}

}
