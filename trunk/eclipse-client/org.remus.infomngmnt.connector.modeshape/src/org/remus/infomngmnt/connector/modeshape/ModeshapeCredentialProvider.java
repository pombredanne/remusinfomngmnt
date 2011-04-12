/*******************************************************************************
 * Copyright (c) 2011 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/
package org.remus.infomngmnt.connector.modeshape;

import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.remus.core.remote.security.CredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ModeshapeCredentialProvider extends CredentialProvider {

	public static final String URL = "url"; //$NON-NLS-1$

	public static final String WORKSPACE = "workspace"; //$NON-NLS-1$

	public static String USESEPARATEWORKSPACE = "useSeparateWorkspace"; //$NON-NLS-1$

	/**
	 * 
	 */
	public ModeshapeCredentialProvider() {
		super();
		setGroup("modeshape"); //$NON-NLS-1$
	}

	public String getUrl() {
		try {
			return getNode().get(URL, ""); //$NON-NLS-1$
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setWorkspace(final String workspace) {
		try {
			String oldValue = getWorkspace();
			getNode().put(WORKSPACE, workspace, true);
			firePropertyChange(WORKSPACE, oldValue, workspace);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public String getWorkspace() {
		try {
			return getNode().get(WORKSPACE, ""); //$NON-NLS-1$
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setUrl(final String url) {
		try {
			String oldValue = getUrl();
			getNode().put(URL, url, true);
			firePropertyChange(URL, oldValue, url);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public boolean isUseSeparateWorkspace() {
		try {
			return getNode().getBoolean(USESEPARATEWORKSPACE, false);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setUseSeparateWorkspace(final boolean flag) {
		try {
			boolean oldValue = isUseSeparateWorkspace();
			getNode().putBoolean(USESEPARATEWORKSPACE, flag, true);
			firePropertyChange(URL, oldValue, flag);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

}
