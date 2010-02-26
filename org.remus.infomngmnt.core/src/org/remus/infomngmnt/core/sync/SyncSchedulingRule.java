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

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.common.core.util.ModelUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncSchedulingRule implements ISchedulingRule {

	private final List<? extends SynchronizableObject> affectedItems;

	public SyncSchedulingRule(final List<? extends SynchronizableObject> affectedItems) {
		this.affectedItems = affectedItems;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.ISchedulingRule#contains(org.eclipse.core
	 * .runtime.jobs.ISchedulingRule)
	 */
	public boolean contains(final ISchedulingRule rule) {
		return rule == this || rule instanceof IResource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.jobs.ISchedulingRule#isConflicting(org.eclipse
	 * .core.runtime.jobs.ISchedulingRule)
	 */
	public boolean isConflicting(final ISchedulingRule rule) {
		boolean conflicting = false;
		if (rule instanceof SyncSchedulingRule) {
			List<? extends SynchronizableObject> affectedItems = ((SyncSchedulingRule) rule).affectedItems;
			for (SynchronizableObject synchronizableObject : affectedItems) {
				if (ModelUtil.containsParent(this.affectedItems, synchronizableObject)
						|| this.affectedItems.contains(synchronizableObject)) {
					conflicting = true;
					break;
				}
			}
			if (!conflicting) {
				for (SynchronizableObject synchronizableObject : this.affectedItems) {
					if (ModelUtil.containsParent(affectedItems, synchronizableObject)) {
						conflicting = true;
						break;
					}
				}
			}
		}
		return rule == this || conflicting;
	}
}
