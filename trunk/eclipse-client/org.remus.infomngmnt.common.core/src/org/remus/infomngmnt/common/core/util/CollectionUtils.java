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

package org.remus.infomngmnt.common.core.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CollectionUtils {
	
	public static <T> List<T> filter(final List<T> list, final CollectionFilter<T> filter) {
		List<T> arrayList = new LinkedList<T>();
		for (T t : list) {
			if (filter.select(t)) {
				arrayList.add(t);
			}
		}
		return arrayList;
	}

}
