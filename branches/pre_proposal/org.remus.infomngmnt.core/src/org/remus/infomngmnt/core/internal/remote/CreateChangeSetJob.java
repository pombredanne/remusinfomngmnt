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

package org.remus.infomngmnt.core.internal.remote;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.sync.ChangeSetManager;
import org.remus.infomngmnt.util.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CreateChangeSetJob extends Job {

	private final Category category;
	private final boolean failOnError;
	private ChangeSetManager changeSetManager;
	private ChangeSet changeSet;
	private final InformationUnitListItem listItem;

	private CreateChangeSetJob(final Category category, final InformationUnitListItem listItem,
			final boolean failOnError) {
		super(NLS.bind("Creating changeset for element \'\'{0}\'\'", category.getLabel()));
		this.category = category;
		this.listItem = listItem;
		this.failOnError = failOnError;
		setRule(ResourcesPlugin.getWorkspace().getRoot());
		setSystem(true);
	}

	public CreateChangeSetJob(final InformationUnitListItem item, final boolean failOnError) {
		this(null, item, failOnError);
	}

	public CreateChangeSetJob(final Category category, final boolean failOnError) {
		this(category, null, failOnError);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		this.changeSetManager = new ChangeSetManager();

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
		}
		return Status.OK_STATUS;
	}

	/**
	 * @return the changeSetManager
	 */
	public final ChangeSetManager getChangeSetManager() {
		return this.changeSetManager;
	}

	/**
	 * @return the changeSet
	 */
	public final ChangeSet getChangeSet() {
		return this.changeSet;
	}

}
