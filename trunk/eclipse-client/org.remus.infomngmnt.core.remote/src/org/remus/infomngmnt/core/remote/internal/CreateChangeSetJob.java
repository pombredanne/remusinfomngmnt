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

import java.util.Collections;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.remote.RemoteActivator;
import org.remus.infomngmnt.core.remote.sync.SyncSchedulingRule;
import org.remus.infomngmnt.model.remote.IChangeHandler;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CreateChangeSetJob extends Job {

	private final Category category;
	private final boolean failOnError;
	private final IChangeHandler changeSetManager;
	private ChangeSet changeSet;
	private final InformationUnitListItem listItem;

	private CreateChangeSetJob(final IChangeHandler changeHandler, final Category category,
			final InformationUnitListItem listItem, final boolean failOnError) {
		super(NLS.bind("Creating changeset for element \'\'{0}\'\'", category.getLabel()));
		this.changeSetManager = changeHandler;
		this.category = category;
		this.listItem = listItem;
		this.failOnError = failOnError;
		if (category != null) {
			setRule(new SyncSchedulingRule(Collections.singletonList(category)));
		} else {
			setRule(new SyncSchedulingRule(Collections.singletonList(listItem)));
		}
		setSystem(true);
	}

	public CreateChangeSetJob(final IChangeHandler changeHandler,
			final InformationUnitListItem item, final boolean failOnError) {
		this(changeHandler, null, item, failOnError);
	}

	public CreateChangeSetJob(final IChangeHandler changeHandler, final Category category,
			final boolean failOnError) {
		this(changeHandler, category, null, failOnError);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		try {
			if (this.category != null) {
				this.changeSet = this.changeSetManager.createChangeSet(this.category, monitor);
			} else if (this.listItem != null) {
				this.changeSet = this.changeSetManager.syncSingleInformationUnit(this.listItem,
						monitor);
			}
		} catch (Exception e) {
			if (this.failOnError) {
				return StatusCreator.newStatus("Error while computing change-set", e);
			}
		} finally {
			RemoteActivator.getDefault().getServiceTracker().ungetService(this.changeSetManager);
		}
		return Status.OK_STATUS;
	}

	/**
	 * @return the changeSet
	 */
	public final ChangeSet getChangeSet() {
		return this.changeSet;
	}

}
