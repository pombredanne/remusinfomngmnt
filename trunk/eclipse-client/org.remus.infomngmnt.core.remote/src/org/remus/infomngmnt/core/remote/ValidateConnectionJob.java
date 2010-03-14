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

package org.remus.infomngmnt.core.remote;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

import org.remus.infomngmnt.model.remote.IRepository;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ValidateConnectionJob /* extends CancelableRunnable */{

	private final IRepository implementation;

	public ValidateConnectionJob(final IRepository implementation) {
		this.implementation = implementation;
	}

	public IStatus runCancelableRunnable(final IProgressMonitor monitor) {
		monitor.beginTask("Validating connection", IProgressMonitor.UNKNOWN);
		return this.implementation.validate();
	}

}
