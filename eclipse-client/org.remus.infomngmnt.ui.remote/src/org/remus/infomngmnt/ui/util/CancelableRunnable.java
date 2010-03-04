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

package org.remus.infomngmnt.ui.util;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.operation.IRunnableWithProgress;

import org.remus.infomngmnt.common.core.operation.CancelableJob;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class CancelableRunnable implements IRunnableWithProgress {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public final void run(final IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		CancelableJob job = new CancelableJob("Run") {
			@Override
			protected IStatus runCancelable(final IProgressMonitor monitor) {
				return runCancelableRunnable(monitor);
			}
		};
		IStatus run = job.run(monitor);
		if (run.getSeverity() == IStatus.CANCEL) {
			throw new InterruptedException();
		} else if (run.getException() != null) {
			throw new InvocationTargetException(run.getException());
		} else if (run.getSeverity() == IStatus.ERROR) {
			throw new InvocationTargetException(new CoreException(run));
		}
	}
	protected abstract IStatus runCancelableRunnable(final IProgressMonitor monitor);

}
