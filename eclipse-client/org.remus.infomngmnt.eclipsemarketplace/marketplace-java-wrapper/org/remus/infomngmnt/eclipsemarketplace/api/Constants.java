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

	public static final String API_ENDPOINT = "http://marketplace.eclipse.org/"; //$NON-NLS-1$

	public static final String USER_AGENT_HEADER = "HTTP_USER_AGENT"; //$NON-NLS-1$

	public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows; U; Windows NT 6.0; de; rv:1.9.0.5) Gecko/2008120122 Firefox/3.0.5"; //$NON-NLS-1$

	public static final String XML = "xml"; //$NON-NLS-1$

	public static final String API = "api/p"; //$NON-NLS-1$

	public static final String TAXONOMY = "taxonomy/"; //$NON-NLS-1$
	public static final String TERM = "term/"; //$NON-NLS-1$
	public static final String NODE = "node/"; //$NON-NLS-1$
	// ///////// XML Node-Names

	public static final String MARKETPLACE_NODE = "marketplace"; //$NON-NLS-1$

	public static final String MARKET_NODE = "market"; //$NON-NLS-1$

	public static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

	public static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$

	public static final String CATEGORY_NODE = "category"; //$NON-NLS-1$

	public static final String COUNT_ATTRIBUTE = "count"; //$NON-NLS-1$

	public static final String NODE_NODE = "node"; //$NON-NLS-1$

	public static final String TITLE_NODE = "title"; //$NON-NLS-1$

	public static final String NID_NODE = "nid"; //$NON-NLS-1$

	public static final String FAVORITED_NODE = "favorited"; //$NON-NLS-1$

	public static final String TYPE_NODE = "type"; //$NON-NLS-1$

	public static final String OWNER_NODE = "owner"; //$NON-NLS-1$

	public static final String BODY_NODE = "body"; //$NON-NLS-1$

	public static final String CREATED_NODE = "created"; //$NON-NLS-1$

	public static final String CHANGED_NODE = "changed"; //$NON-NLS-1$

	public static final String FOUNDATION_NODE = "foundationmember"; //$NON-NLS-1$

	public static final String URL_NODE = "url"; //$NON-NLS-1$

	public static final String IMAGE_NODE = "image"; //$NON-NLS-1$

	public static final String VERSION_NODE = "version"; //$NON-NLS-1$

	public static final String LICENSE_NODE = "license"; //$NON-NLS-1$

	public static final String COMPANYNAME_NODE = "companyname"; //$NON-NLS-1$

	public static final String STATUS_NODE = "status"; //$NON-NLS-1$

	public static final String ECLIPSEVERSION_NODE = "eclipseversion"; //$NON-NLS-1$

	public static final String SUPPORTURL_NODE = "supporturl"; //$NON-NLS-1$

	public static final String UPDATEURL_NODE = "updateurl"; //$NON-NLS-1$

}
