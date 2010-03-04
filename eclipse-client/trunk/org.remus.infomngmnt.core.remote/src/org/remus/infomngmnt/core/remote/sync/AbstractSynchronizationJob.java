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

package org.remus.infomngmnt.core.remote.sync;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.core.services.IApplicationModel;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractSynchronizationJob extends Job {

	protected IApplicationModel applicationService;
	protected IRepositoryService repositoryService;
	protected IRepositoryExtensionService extensionService;

	public AbstractSynchronizationJob(final String name) {
		super(name);
		setRule(new SyncSchedulingRule(getAffectedObjects()));

	}

	public void doPrepare() {
	};

	protected abstract IStatus doRun(IProgressMonitor monitor);

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		initialize();
		IStatus doRun = doRun(monitor);
		dispose();
		return doRun;
	}

	private void dispose() {
		RemoteActivator.getDefault().getServiceTracker().ungetService(this.applicationService);
		RemoteActivator.getDefault().getServiceTracker().ungetService(this.repositoryService);
		RemoteActivator.getDefault().getServiceTracker().ungetService(this.extensionService);

	}

	private void initialize() {
		this.applicationService = RemoteActivator.getDefault().getServiceTracker().getService(
				IApplicationModel.class);
		this.repositoryService = RemoteActivator.getDefault().getServiceTracker().getService(
				IRepositoryService.class);
		this.extensionService = RemoteActivator.getDefault().getServiceTracker().getService(
				IRepositoryExtensionService.class);

	}

	protected List<? extends SynchronizableObject> getAffectedObjects() {
		return Collections.<SynchronizableObject> emptyList();
	}

}
