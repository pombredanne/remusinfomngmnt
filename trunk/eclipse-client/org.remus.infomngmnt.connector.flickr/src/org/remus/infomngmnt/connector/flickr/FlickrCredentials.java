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

package org.remus.infomngmnt.connector.flickr;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.equinox.security.storage.StorageException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.REST;
import com.aetrion.flickr.RequestContext;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.people.User;

import org.remus.infomngmnt.core.security.CredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FlickrCredentials extends CredentialProvider {

	public static final String REAL_NAME = "realName"; //$NON-NLS-1$

	public static final String INTERNAL_ID = "internalId"; //$NON-NLS-1$

	private Flickr flickr;

	public FlickrCredentials() {
		super();
		setGroup("flickr");
	}

	public String getRealName() {
		try {
			return getNode().get(REAL_NAME, "");
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public void setRealName(final String realname) {
		try {
			String oldValue = getUserName();
			getNode().put(REAL_NAME, realname, true);
			firePropertyChange(REAL_NAME, oldValue, realname);
		} catch (StorageException e) {
			throw new SecurityException(e);
		}
	}

	public String getInternalId() {
		try {
			return getNode().get(INTERNAL_ID, "");
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

	/**
	 * @return the flickr
	 */
	public Flickr getFlickr() {
		if (this.flickr == null) {
			try {
				this.flickr = new Flickr(FlickrPlugin.API_KEY, FlickrPlugin.SHARED_SECRET,
						new REST());
				User user = new User();
				user.setId(getInternalId());
				user.setUsername(getUserName());
				RequestContext requestContext = RequestContext.getRequestContext();
				Auth auth = new Auth();
				auth.setPermission(Permission.DELETE);
				auth.setToken(getPassword());
				requestContext.setAuth(auth);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.flickr;
	}
}
