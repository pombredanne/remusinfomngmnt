/*******************************************************************************
 * Copyright (c) 2008 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.common.core.operation;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;

import org.remus.infomngmnt.common.core.util.ValueObject;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class CancelableJob extends Job {

	public CancelableJob(final String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	@Override
	public final IStatus run(final IProgressMonitor monitor) {
		final ValueObject<IStatus> returnValue = new ValueObject<IStatus>();
		Thread runThread = new Thread() {
			@Override
			public void run() {
				returnValue.setObject(runCancelable(monitor));
			}
		};
		runThread.start();
		while (runThread.isAlive() && !monitor.isCanceled()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		return returnValue.getObject();
	}

	protected abstract IStatus runCancelable(IProgressMonitor monitor);

}
