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

package org.remus.infomngmnt.connector.facebook;

import org.remus.infomngmnt.core.security.CredentialProvider;

import com.google.code.facebookapi.FacebookJaxbRestClient;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FacebookCredentialProvider extends CredentialProvider {

	/**
	 * 
	 */
	public FacebookCredentialProvider() {
		super();
		setGroup("facebook");
	}

	public FacebookJaxbRestClient getFacebook() {
		FacebookJaxbRestClient facebookJsonRestClient = new FacebookJaxbRestClient(
				FacebookActivator.API_KEY, getPassword(), getUserName());
		facebookJsonRestClient.setIsDesktop(true);
		return facebookJsonRestClient;
	}

}
