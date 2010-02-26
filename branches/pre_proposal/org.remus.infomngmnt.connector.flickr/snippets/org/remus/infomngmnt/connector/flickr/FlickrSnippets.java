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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.xml.sax.SAXException;

import com.aetrion.flickr.Flickr;
import com.aetrion.flickr.FlickrException;
import com.aetrion.flickr.auth.Auth;
import com.aetrion.flickr.auth.AuthInterface;
import com.aetrion.flickr.auth.Permission;
import com.aetrion.flickr.people.User;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class FlickrSnippets {

	public static void main(final String[] args) throws IOException, SAXException, FlickrException {

		Flickr flickr = new Flickr(FlickrPlugin.API_KEY);
		flickr.setSharedSecret(FlickrPlugin.SHARED_SECRET);
		AuthInterface authInterface = flickr.getAuthInterface();

		String frob = authInterface.getFrob();
		URL buildAuthenticationUrl = authInterface.buildAuthenticationUrl(Permission.DELETE, frob);
		System.out.println("Open URL in browser:");
		System.out.println(buildAuthenticationUrl);
		System.out.println("After allowing press [ENTER]:");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();

		Auth auth = authInterface.getToken(frob);
		System.out.println(auth.getToken());
		User user = auth.getUser();
		System.out.println(user.getUsername());

	}
}
