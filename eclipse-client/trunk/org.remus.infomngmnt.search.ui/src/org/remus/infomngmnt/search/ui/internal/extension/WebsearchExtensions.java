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

package org.remus.infomngmnt.search.ui.internal.extension;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.search.ui.SearchUIActivator;
import org.remus.infomngmnt.search.ui.websearch.Websearch;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class WebsearchExtensions {

	private static WebsearchExtensions INSTANCE;

	public static final String EXTENSION_POINT = SearchUIActivator.PLUGIN_ID + ".websearch"; //$NON-NLS-1$

	public static final String WEBSEARCH_NODE_NAME = "websearch"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String ID_ATT = "icon"; //$NON-NLS-1$

	public static final String PATTERN_ATT = "urlpattern"; //$NON-NLS-1$

	private Collection<Websearch> searches;

	public static WebsearchExtensions getInstance() {
		if (INSTANCE == null) {
			synchronized (WebsearchExtensions.class) {
				if (INSTANCE == null) {
					INSTANCE = new WebsearchExtensions();
				}
			}
		}
		return INSTANCE;
	}

	private WebsearchExtensions() {
		init();
	}

	private void init() {
		if (this.searches == null) {
			this.searches = new ArrayList<Websearch>();
			final IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
					.getExtensionPoint(EXTENSION_POINT);
			final IConfigurationElement[] configurationElements = extensionPoint
					.getConfigurationElements();
			for (final IConfigurationElement configurationElement : configurationElements) {
				Websearch newWebsearch = new Websearch();
				newWebsearch.setId(configurationElement.getAttribute(ID_ATT));
				newWebsearch.setPattern(configurationElement.getAttribute(PATTERN_ATT));
				newWebsearch.setName(configurationElement.getAttribute(NAME_ATT));
				newWebsearch.setContributor(configurationElement.getContributor().getName());
				newWebsearch.setImagePath(configurationElement.getAttribute(ICON_ATT));
				if (newWebsearch.getImagePath() != null && newWebsearch.getImagePath().length() > 0) {
					ImageDescriptor imageDescriptorFromPlugin = AbstractUIPlugin
							.imageDescriptorFromPlugin(newWebsearch.getContributor(), newWebsearch
									.getImagePath());
					if (imageDescriptorFromPlugin != null) {
						newWebsearch.setImage(imageDescriptorFromPlugin.createImage());
					}
				}
				this.searches.add(newWebsearch);
			}
		}
	}

	public Collection<Websearch> getSearches() {
		return this.searches;
	}

}
