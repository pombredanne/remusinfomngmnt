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

package org.remus.infomngmnt.core.remote.sync;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SyncStateParticipantNotfier {

	private static Collection<String> cleanElements;

	public static synchronized void notifyClean(final String id) {
		getList().add(id);
	}

	private static Collection<String> getList() {
		if (cleanElements == null) {
			cleanElements = Collections.synchronizedCollection(new ArrayList<String>());
		}
		return cleanElements;
	}

	public static boolean isCleanAndClear(final String id) {
		if (getList().contains(id)) {
			getList().remove(id);
			return true;
		}
		return false;

	}

}
