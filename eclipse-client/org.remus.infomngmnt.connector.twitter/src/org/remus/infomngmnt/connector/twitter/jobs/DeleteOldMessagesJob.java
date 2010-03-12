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

import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;

import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.Notification;
import org.remus.infomngmnt.connector.twitter.TwitterActivator;
import org.remus.infomngmnt.connector.twitter.preferences.TwitterPreferenceInitializer;
import org.remus.infomngmnt.core.jobs.AbstractJob;
import org.remus.infomngmnt.core.model.InformationStructureRead;
import org.remus.infomngmnt.core.remote.sync.SyncStateParticipantNotfier;
import org.remus.infomngmnt.core.services.IApplicationModel;
import org.remus.infomngmnt.core.services.IEditingHandler;
import org.remus.infomngmnt.services.RemusServiceTracker;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class DeleteOldMessagesJob extends AbstractJob {

	private final RemusServiceTracker remusServiceTracker;
	protected IApplicationModel applicationModel;
	private final IEditingHandler editService;

	/**
	 * 
	 */
	public DeleteOldMessagesJob() {
		this.remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(TwitterActivator.PLUGIN_ID));

		this.applicationModel = this.remusServiceTracker.getService(IApplicationModel.class);
		this.editService = this.remusServiceTracker.getService(IEditingHandler.class);

	}

	@Override
	public List<Notification> run(final IProgressMonitor monitor) {
		EObjectAttributeValueCondition typeCondition = new EObjectAttributeValueCondition(
				InfomngmntPackage.Literals.ABSTRACT_INFORMATION_UNIT__TYPE, new Condition() {
					@Override
					public boolean isSatisfied(final Object object) {
						return object.equals(TwitterActivator.INFOTYPE_ID);
					}
				});

		SELECT select = new SELECT(new FROM(this.applicationModel.getModel().getRootCategories()),
				new WHERE(typeCondition));
		Set<? extends EObject> eObjects = select.execute().getEObjects();
		for (final EObject eObject : eObjects) {
			InformationUnit adapter = (InformationUnit) ((IAdaptable) eObject)
					.getAdapter(InformationUnit.class);
			if (adapter != null) {
				InformationStructureRead read = InformationStructureRead.newSession(adapter);
				int size = read.getChildByNodeId(TwitterActivator.MESSAGES_ID).getChildValues()
						.size();
				if (size > getMaxMessages()) {
					for (int i = getMaxMessages(), n = size; i < n; i++) {
						read.getChildByNodeId(TwitterActivator.MESSAGES_ID).getChildValues()
								.remove(getMaxMessages());
					}
					SyncStateParticipantNotfier.notifyClean(adapter.getId());
					this.editService.saveObjectToResource(adapter);
				}

			}

		}

		return null;
	}

	private int getMaxMessages() {
		return TwitterActivator.getDefault().getPreferenceStore().getInt(
				TwitterPreferenceInitializer.SAVED_MESSAGES);
	}

}
