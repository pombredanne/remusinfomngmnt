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

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.jobs.Job;

import org.remus.infomngmnt.SynchronizableObject;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractSynchronizationJob extends Job {

	public AbstractSynchronizationJob(final String name) {
		super(name);
	}

	public void doPrepare() {
		setRule(new SyncSchedulingRule(getAffectedObjects()));
	}

	protected List<? extends SynchronizableObject> getAffectedObjects() {
		return Collections.<SynchronizableObject> emptyList();
	}

}
