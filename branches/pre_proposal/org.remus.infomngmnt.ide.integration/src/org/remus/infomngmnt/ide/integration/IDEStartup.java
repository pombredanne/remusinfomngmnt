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

package org.remus.infomngmnt.ide.integration;

import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IStartup;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class IDEStartup implements IStartup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	public void earlyStartup() {
		try {
			Bundle bundle = Platform.getBundle("org.eclipse.equinox.ds");
			if (bundle != null && bundle.getState() == Bundle.RESOLVED) {
				bundle.start();
			}
			bundle = Platform.getBundle("org.remus.infomngmnt.search.local");
			if (bundle != null && bundle.getState() == Bundle.RESOLVED) {
				bundle.start();
			}
		} catch (BundleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
