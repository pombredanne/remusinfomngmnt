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

package org.remus.infomngmnt.core.remote;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RemoteException extends CoreException {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 3229381779848854312L;

	/**
	 * @param status
	 */
	public RemoteException(final IStatus status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

}
