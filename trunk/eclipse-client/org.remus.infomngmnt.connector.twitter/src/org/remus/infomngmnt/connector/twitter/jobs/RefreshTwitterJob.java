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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.query.conditions.Condition;
import org.eclipse.emf.query.conditions.eobjects.EObjectCondition;
import org.eclipse.emf.query.conditions.eobjects.structuralfeatures.EObjectAttributeValueCondition;
import org.eclipse.emf.query.statements.FROM;
import org.eclipse.emf.query.statements.SELECT;
import org.eclipse.emf.query.statements.WHERE;
import org.eclipse.remus.InfomngmntPackage;
import org.eclipse.remus.InformationUnitListItem;
import org.eclipse.remus.Notification;
import org.eclipse.remus.core.jobs.AbstractJob;
import org.eclipse.remus.core.remote.services.ISynchronizationManager;
import org.eclipse.remus.core.services.IApplicationModel;
import org.eclipse.remus.core.services.IEditingHandler;
import org.eclipse.remus.services.RemusServiceTracker;

import org.remus.infomngmnt.connector.twitter.TwitterActivator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class RefreshTwitterJob extends AbstractJob {

	private final RemusServiceTracker remusServiceTracker;
	protected IEditingHandler service;
	protected IApplicationModel applicationModel;
	protected ISynchronizationManager synchronizationManager;

	/**
	 * 
	 */
	public RefreshTwitterJob() {
		this.remusServiceTracker = new RemusServiceTracker(Platform
				.getBundle(TwitterActivator.PLUGIN_ID));
		this.service = this.remusServiceTracker.getService(IEditingHandler.class);
		this.applicationModel = this.remusServiceTracker.getService(IApplicationModel.class);
		this.synchronizationManager = this.remusServiceTracker
				.getService(ISynchronizationManager.class);
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
				return isTwitterElementSatisfied(eObject);
			}
		};
		SELECT select = new SELECT(new FROM(this.applicationModel.getModel().getRootCategories()),
				new WHERE(typeCondition.AND(isSynchronizableCondition)));
		Set<? extends EObject> eObjects = select.execute().getEObjects();
		for (final EObject eObject : eObjects) {
			this.synchronizationManager.scheduleSynchronization((InformationUnitListItem) eObject);
		}
		return null;
	}

	protected abstract boolean isTwitterElementSatisfied(EObject object);

}
