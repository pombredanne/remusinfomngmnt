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

package org.remus.infomngmnt.core.model;

import org.eclipse.core.internal.utils.UniversalUniqueIdentifier;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
@SuppressWarnings("restriction")
public class IdFactory {

	/**
	 * Creates a new unique Id for a new object that can be identified within
	 * the whole system. Therefore the cache will be asked if such an id already
	 * exists.
	 * 
	 * @param monitor
	 *            a progressmonitor if asking the cache needs a bit
	 * @return a new unique id
	 */
	public static String createNewId(final IProgressMonitor monitor) {
		String string = new UniversalUniqueIdentifier().toString();
		if (ApplicationModelPool.getInstance().getAllItems(monitor).containsKey(string)) {
			return createNewId(monitor);
		}
		return string;
	}

}
