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
package org.remus.infomngmnt.core.model;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.remus.infomngmnt.core.CorePlugin;



/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class StatusCreator {

	public static IStatus newStatus(final String message) {
		return new Status(IStatus.ERROR,CorePlugin.PLUGIN_ID,300,message,null);
	}

	public static IStatus newStatus(final String message, final Throwable t) {
		return new Status(IStatus.ERROR,CorePlugin.PLUGIN_ID,300,message,t);
	}

	public static IStatus newStatus(final int severity, final String message, final Throwable t) {
		return new Status(severity,CorePlugin.PLUGIN_ID,300,message,t);
	}

}
