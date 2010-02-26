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
package org.remus.infomngmnt.password;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
/**
 * @author Jan Hartwig <jhartwig@feb-radebeul.de>
 * 
 */
public class PasswordPlugin extends AbstractUIPlugin {

	public static String PASSWORD_INFO_ID = "PASSWORD";
	public static String NODE_URL = "url";
	public static String NODE_USERNAME = "username";

	// The plug-in ID
	public static final String PLUGIN_ID = "org.remus.infomngmnt.password";

	// The shared instance
	private static PasswordPlugin plugin;

	/**
	 * The constructor
	 */
	public PasswordPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static PasswordPlugin getDefault() {
		return plugin;
	}

}
