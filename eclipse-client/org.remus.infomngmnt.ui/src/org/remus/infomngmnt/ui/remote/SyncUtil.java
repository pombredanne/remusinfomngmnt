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

package org.remus.infomngmnt.ui.remote;

import java.util.Set;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

import org.remus.infomngmnt.AbstractInformationUnit;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.SynchronizationAction;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncUtil {

	public static SynchronizationAction getAction(
			final EMap<SynchronizableObject, SynchronizationAction> syncActions,
			final EObject object) {
		Set<SynchronizableObject> keySet = syncActions.keySet();
		for (SynchronizableObject synchronizableObject : keySet) {
			if (synchronizableObject.eClass() == object.eClass()) {
				if (synchronizableObject instanceof Category
						&& ((Category) synchronizableObject).getId().equals(
								((Category) object).getId())) {
					return syncActions.get(synchronizableObject);
				} else if (synchronizableObject instanceof AbstractInformationUnit
						&& ((AbstractInformationUnit) synchronizableObject).getId().equals(
								((AbstractInformationUnit) object).getId())) {
					return syncActions.get(synchronizableObject);
				}
			}
		}
		return null;
	}

}
