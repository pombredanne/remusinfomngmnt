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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import org.remus.infomngmnt.common.service.ITrayService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class IDETrayServiceImpl implements ITrayService {

	private Shell shell;

	private boolean minimized;

	/** Constant for the application being run on Windows or not */
	public static final boolean IS_WINDOWS = "win32".equals(SWT.getPlatform());

	/** Constant for the application being run on Linux or not */
	public static final boolean IS_LINUX = "gtk".equals(SWT.getPlatform());

	/** Constant for the application being run on Mac or not */
	public static final boolean IS_MAC = "carbon".equals(SWT.getPlatform());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.common.service.ITrayService#isMinimized()
	 */
	public boolean isMinimized() {
		return this.minimized;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.service.ITrayService#moveToTray(org.eclipse
	 * .swt.widgets.Shell)
	 */
	public void moveToTray(final Shell shell) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.service.ITrayService#restoreFromTray(org.
	 * eclipse.swt.widgets.Shell)
	 */
	public void restoreFromTray(final Shell shell) {
		// TODO Auto-generated method stub

	}

}
