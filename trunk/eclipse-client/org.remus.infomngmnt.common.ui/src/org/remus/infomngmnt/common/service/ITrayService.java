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
package org.remus.infomngmnt.common.service;

import org.eclipse.swt.widgets.Shell;

/**
 * <p>
 * This interface is used as as an OSGi Service definition which enables a
 * client to move the application into the tray or restore it from there.
 * </p>
 * 
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @since 1.0
 */
public interface ITrayService {

	/**
	 * Restores an application from tray. If the application is already "trayed"
	 * this method has no effect.
	 * 
	 * @param shell
	 * @since 1.0
	 */
	void restoreFromTray(Shell shell);

	void moveToTray(Shell shell);

	boolean isMinimized();

}
