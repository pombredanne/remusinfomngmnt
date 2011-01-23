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

package org.remus.infomngmnt.eclipsemarketplace.api;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public final class Constants {

	// ///////// General Settings

	public static final String API_ENDPOINT = "http://marketplace.eclipse.org/";

	public static final String USER_AGENT_HEADER = "HTTP_USER_AGENT";

	public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows; U; Windows NT 6.0; de; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5";

	public static final String XML = "xml";

	public static final String API = "api/p";

	public static final String TAXONOMY = "taxonomy/";
	public static final String TERM = "term/";
	public static final String NODE = "node/";
	// ///////// XML Node-Names

	public static final String MARKETPLACE_NODE = "marketplace";

	public static final String MARKET_NODE = "market";

	public static final String NAME_ATTRIBUTE = "name";

	public static final String ID_ATTRIBUTE = "id";

	public static final String CATEGORY_NODE = "category";

	public static final String COUNT_ATTRIBUTE = "count";

	public static final String NODE_NODE = "node";

	public static final String TITLE_NODE = "title";

	public static final String NID_NODE = "nid";

	public static final String FAVORITED_NODE = "favorited";

	public static final String TYPE_NODE = "type";

	public static final String OWNER_NODE = "owner";

	public static final String BODY_NODE = "body";

	public static final String CREATED_NODE = "created";

	public static final String CHANGED_NODE = "changed";

	public static final String FOUNDATION_NODE = "foundationmember";

	public static final String URL_NODE = "url";

	public static final String IMAGE_NODE = "image";

	public static final String VERSION_NODE = "version";

	public static final String LICENSE_NODE = "license"; //$NON-NLS-1$

	public static final String COMPANYNAME_NODE = "companyname"; //$NON-NLS-1$

	public static final String STATUS_NODE = "status"; //$NON-NLS-1$

	public static final String ECLIPSEVERSION_NODE = "eclipseversion"; //$NON-NLS-1$

	public static final String SUPPORTURL_NODE = "supporturl"; //$NON-NLS-1$

	public static final String UPDATEURL_NODE = "updateurl"; //$NON-NLS-1$

}
