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

package org.remus.infomngmnt.connector.googlecalendar;

import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.remus.core.remote.security.CredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class GoogleCalendarCredentialProvider extends CredentialProvider {

	public static final String START_TIME = "startTime"; //$NON-NLS-1$

	public static final String END_TIME = "endTime"; //$NON-NLS-1$

	/**
	 * 
	 */
	public GoogleCalendarCredentialProvider() {
		super();
		setGroup("google-calendar"); //$NON-NLS-1$
	}

	public int getStartTime() {
		try {
			return getNode().getInt(START_TIME, 6);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setStartTime(final int time) {
		try {
			int oldValue = getStartTime();
			getNode().putInt(START_TIME, time, true);
			firePropertyChange(START_TIME, oldValue, time);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public int getEndTime() {
		try {
			return getNode().getInt(END_TIME, 6);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setEndTime(final int time) {
		try {
			int oldValue = getStartTime();
			getNode().putInt(END_TIME, time, true);
			firePropertyChange(END_TIME, oldValue, time);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

}
