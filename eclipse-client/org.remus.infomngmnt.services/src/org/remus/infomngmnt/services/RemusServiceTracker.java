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

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemusServiceTracker {

	private final Bundle bundle;

	private volatile Map<Class<?>, ServiceReference> references;

	public RemusServiceTracker(final Bundle bundle) {
		this.bundle = bundle;
		this.references = new HashMap<Class<?>, ServiceReference>();
	}

	public <T> T getService(final Class<T> serviceClass) {
		ServiceReference serviceReference = this.bundle.getBundleContext().getServiceReference(
				serviceClass.getName());
		if (serviceReference != null) {
			Object service = this.bundle.getBundleContext().getService(serviceReference);

			return (T) service;
		}
		return null;
	}

	public void ungetService(final Object obj) {
		ServiceReference serviceReference = this.references.get(obj);
		if (serviceReference != null) {
			// this.bundle.getBundleContext().ungetService(serviceReference);
		}
	}

}
