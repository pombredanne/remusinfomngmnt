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

package org.remus.infomngmnt.connector.twitter;

import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.remus.core.remote.security.CredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TwitterCredentials extends CredentialProvider {

	public TwitterCredentials() {
		super();
		setGroup("twitter"); //$NON-NLS-1$
	}

	public static final String INTERNAL_ID = "internalId"; //$NON-NLS-1$

	public String getInternalId() {
		try {
			return getNode().get(INTERNAL_ID, ""); //$NON-NLS-1$
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setInternalId(final String internalId) {
		try {
			String oldValue = getUserName();
			getNode().put(INTERNAL_ID, internalId, true);
			firePropertyChange(INTERNAL_ID, oldValue, internalId);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

}
