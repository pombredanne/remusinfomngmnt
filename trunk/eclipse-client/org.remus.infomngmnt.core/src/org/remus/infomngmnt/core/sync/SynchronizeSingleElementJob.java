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

package org.remus.infomngmnt.core.sync;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;

import org.remus.infomngmnt.ChangeSet;
import org.remus.infomngmnt.ChangeSetItem;
import org.remus.infomngmnt.InformationUnitListItem;
import org.remus.infomngmnt.core.model.StatusCreator;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizeSingleElementJob extends Job {

	private final InformationUnitListItem item;
	private ChangeSet createChangeSet;

	public SynchronizeSingleElementJob(final InformationUnitListItem item) {
		super("Synchronize single item");
		this.item = item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public IStatus run(final IProgressMonitor monitor) {
		ChangeSetManager manager = new ChangeSetManager();
		try {
			this.createChangeSet = manager.syncSingleInformationUnit(this.item, monitor);
			if (this.createChangeSet != null && this.createChangeSet.getChangeSetItems().size() > 0) {
				ChangeSetItem changeSetItem = this.createChangeSet.getChangeSetItems().get(0);
				DiffModel createDiffModel = manager.createDiffModel(changeSetItem,
						this.createChangeSet.getTargetCategory());
				manager.prepareSyncActions(createDiffModel.getOwnedElements(), changeSetItem,
						this.createChangeSet.getTargetCategory());
				manager.updateFromRemote(changeSetItem);

				ChangeSetExecutor executor = new ChangeSetExecutor();
				executor.synchronize(createDiffModel.getOwnedElements(), changeSetItem, monitor,
						this.createChangeSet.getTargetCategory());
			}
		} catch (CoreException e) {
			return e.getStatus();
		} catch (Exception e) {
			return StatusCreator.newStatus("Error synchronizing single information unit", e);
		}

		return Status.OK_STATUS;
	}

	/**
	 * @return the createChangeSet
	 */
	public ChangeSet getCreateChangeSet() {
		return this.createChangeSet;
	}

}
