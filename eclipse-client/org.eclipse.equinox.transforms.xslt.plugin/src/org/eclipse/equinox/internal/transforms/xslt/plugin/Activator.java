/*******************************************************************************
 * Copyright (c) 2006, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.equinox.internal.transforms.xslt.plugin;

import java.net.URL;
import java.util.Properties;
import org.osgi.framework.*;

public class Activator implements BundleActivator {

	private ServiceRegistration registration;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Properties properties = new Properties();
		properties.put("equinox.transformerType", "xslt"); //$NON-NLS-1$ //$NON-NLS-2$
		registration = context.registerService(URL.class.getName(), context.getBundle().getEntry("/transform.csv"), properties); //$NON-NLS-1$
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		if (registration != null)
			registration.unregister();
	}
}
