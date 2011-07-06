/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.eclipse.remus.application.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.remus.core.remote.sync.SynchronizeItemsWithCommitJob;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SynchronizeAllHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		new Job("Synchronize all items") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SynchronizeItemsWithCommitJob synchronizeItemsWithCommitJob = new SynchronizeItemsWithCommitJob();
				synchronizeItemsWithCommitJob.beforeRun(monitor);
				synchronizeItemsWithCommitJob.run(monitor);
				synchronizeItemsWithCommitJob.afterRun(monitor);
				return Status.OK_STATUS;
			}
		}.schedule();
		return null;
	}

}
