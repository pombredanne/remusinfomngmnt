/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.services;

import org.osgi.framework.ServiceReference;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ServiceWrapper<T> {

	private final ServiceReference ref;
	private final Class serviceClass;

	public ServiceWrapper(final ServiceReference ref, final Class serviceClass) {
		this.ref = ref;
		this.serviceClass = serviceClass;
	}

	public T get() {
		return null;
	}

	public void unget() {

	}
}
