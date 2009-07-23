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
package org.remus.infomngmnt.connector.rss;

import org.eclipse.equinox.security.storage.StorageException;

import org.remus.infomngmnt.core.security.CredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RssCredentialProvider extends CredentialProvider {

	public static final String URL = "url"; //$NON-NLS-1$

	/**
	 * 
	 */
	public RssCredentialProvider() {
		super();
		setGroup("rss");
	}

	public String getUrl() {
		try {
			return getNode().get(URL, "");
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setUrl(final String url) {
		try {
			String oldValue = getUserName();
			getNode().put(URL, url, true);
			firePropertyChange(URL, oldValue, url);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

}
