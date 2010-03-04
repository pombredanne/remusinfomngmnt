/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.search.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.search.SearchActivator;
import org.remus.infomngmnt.search.analyzer.IAnalyzer;
import org.remus.infomngmnt.search.service.ISearchAnalyzerService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class AnalyzerExtensionService extends PluginRegistryDynamic implements
		ISearchAnalyzerService {

	public static final String EXTENSION_POINT = SearchActivator.PLUGIN_ID + ".analyzer"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	public AnalyzerExtensionService() {
		super(EXTENSION_POINT);

	}

	private Map<String, IAnalyzer> items;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic#init()
	 */
	@Override
	protected void init() {
		this.items = new HashMap<String, IAnalyzer>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (IConfigurationElement iConfigurationElement : configurationElements) {
			String attribute = iConfigurationElement.getAttribute(ID_ATT);
			try {
				IAnalyzer analyzer = (IAnalyzer) iConfigurationElement
						.createExecutableExtension(CLASS_ATT);
				this.items.put(attribute, analyzer);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public IAnalyzer getAnalyerById(final String id) {
		return this.items.get(id);
	}

}
