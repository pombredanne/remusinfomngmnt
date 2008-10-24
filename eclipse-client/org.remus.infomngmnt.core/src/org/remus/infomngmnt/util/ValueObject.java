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

package org.remus.infomngmnt.util;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ValueObject<T> {

	private T object;

	/**
	 * @param obj
	 */
	public ValueObject(T obj) {
		this.object = obj;
	}

	public ValueObject() {

	}

	public T getObject() {
		return this.object;
	}

	public void setObject(T object) {
		this.object = object;
	}

}
