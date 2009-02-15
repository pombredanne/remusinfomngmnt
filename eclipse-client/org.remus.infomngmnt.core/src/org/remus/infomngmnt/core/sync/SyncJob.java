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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.core.progress.CancelableJob;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncJob extends CancelableJob {

	private final EList<DiffElement> ownedElements;
	private final ChangeSetExecutor changeSetExecutor;

	public SyncJob(final EList<DiffElement> ownedElements, final ChangeSetExecutor changeSetExecutor) {
		super("Synchronization");
		this.ownedElements = ownedElements;
		this.changeSetExecutor = changeSetExecutor;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.core.progress.CancelableJob#runCancelable(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus runCancelable(final IProgressMonitor monitor) {
		monitor.beginTask(
				NLS.bind("Checking out to {0}", this.changeSetExecutor.getChangeSet().getTargetCategory().getLabel()), IProgressMonitor.UNKNOWN);
		this.changeSetExecutor.performCheckout(this.ownedElements, monitor);
		return Status.OK_STATUS;
	}

}
