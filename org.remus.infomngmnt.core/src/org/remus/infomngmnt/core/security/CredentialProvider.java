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

package org.remus.infomngmnt.core.security;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;

import org.remus.infomngmnt.core.remote.ICredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class CredentialProvider implements ICredentialProvider {

	public static final String USER_NAME = "userName";

	public static final String PASSWORD = "password"; //$NON-NLS-1$

	public static final String GROUP = "group"; //$NON-NLS-1$

	public static final String IDENTIFIER = "identifier"; //$NON-NLS-1$

	protected final ISecurePreferences preferences;

	protected transient PropertyChangeSupport listeners = new PropertyChangeSupport(this);

	private String identifier;

	private String group;

	public CredentialProvider() {
		this.preferences = SecurePreferencesFactory.getDefault();
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(final PropertyChangeEvent arg0) {
				try {
					CredentialProvider.this.preferences.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
	}

	protected ISecurePreferences getNode() {
		if (this.identifier == null) {
			throw new IllegalArgumentException("Identifier not set.");
		}
		if (this.group != null) {
			return this.preferences.node(this.group).node(this.identifier);
		}
		return this.preferences.node(this.identifier);
	}

	public String getUserName() {
		try {
			return getNode().get(USER_NAME, "");
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setUserName(final String username) {
		try {
			String oldValue = getUserName();
			getNode().put(USER_NAME, username, true);
			firePropertyChange(USER_NAME, oldValue, username);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public String getPassword() {
		try {
			return getNode().get(PASSWORD, "");
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setPassword(final String password) {
		try {
			String oldValue = getPassword();
			getNode().put(PASSWORD, password, true);
			firePropertyChange(PASSWORD, oldValue, password);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setIdentifier(final String identifier) {
		String oldValue = this.identifier;
		this.identifier = identifier;
		firePropertyChange(IDENTIFIER, oldValue, identifier);
	}

	public void removeCredentials() {
		getNode().removeNode();
	}

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		this.listeners.addPropertyChangeListener(listener);

	}

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		this.listeners.removePropertyChangeListener(listener);

	}

	protected void firePropertyChange(final String prop, final Object old, final Object newValue) {
		if (this.listeners.hasListeners(prop)) {
			this.listeners.firePropertyChange(prop, old, newValue);
		}
	}

	public void setGroup(final String group) {
		String oldValue = this.group;
		this.group = group;
		firePropertyChange(GROUP, oldValue, group);
	}

	public void delete() {
		this.preferences.node(this.identifier).removeNode();
	}
}
