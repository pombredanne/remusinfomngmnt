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

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface CollectionFilter<T> {
	
	/**
	 * Indicates wherether the given item
	 * makes it through the filter. 
	 * @param item the item to check.
	 * @return true if the item will be contained in the filtered 
	 * collection
	 */
	boolean select(T item);

}
