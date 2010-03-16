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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.search.SearchActivator;
import org.remus.infomngmnt.search.analyzer.IAnalyzer;
import org.remus.infomngmnt.search.analyzer.ISecondaryAnalyzer;
import org.remus.infomngmnt.search.service.IIndexService;
import org.remus.infomngmnt.search.service.ISearchAnalyzerService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class IndexExtensionService extends PluginRegistryDynamic implements IIndexService {

	private ISearchAnalyzerService service;

	public static final String EXTENSION_POINT = SearchActivator.PLUGIN_ID + ".indexer"; //$NON-NLS-1$

	public static final String NODENAME_INDEX = "index"; //$NON-NLS-1$
	public static final String NODENAME_SECONDARY_INDEX = "secondaryIndex"; //$NON-NLS-1$
	public static final String TYPEID_ATT = "typeId"; //$NON-NLS-1$
	public static final String NODE_ATT = "node"; //$NON-NLS-1$
	public static final String ANALYZER_ATT = "analyzer"; //$NON-NLS-1$
	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	public IndexExtensionService() {
		super(EXTENSION_POINT);
	}

	private Map<String, Map<String, String>> indexer;
	private Map<String, Map<String, List<ISecondaryAnalyzer>>> secondaryIndexer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic#init()
	 */
	@Override
	protected void init() {
		this.indexer = new HashMap<String, Map<String, String>>();
		this.secondaryIndexer = new HashMap<String, Map<String, List<ISecondaryAnalyzer>>>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(NODENAME_INDEX)) {
				String typeId = configurationElement.getAttribute(TYPEID_ATT);
				String node = configurationElement.getAttribute(NODE_ATT);
				String attribute = configurationElement.getAttribute(ANALYZER_ATT);

				Map<String, String> map = this.indexer.get(typeId);
				if (map == null) {
					map = new HashMap<String, String>();
					this.indexer.put(typeId, map);
				}
				map.put(node, attribute);

			}
			if (configurationElement.getName().equals(NODENAME_SECONDARY_INDEX)) {
				String typeId = configurationElement.getAttribute(TYPEID_ATT);
				String node = configurationElement.getAttribute(NODE_ATT);
				try {
					ISecondaryAnalyzer attribute = (ISecondaryAnalyzer) configurationElement
							.createExecutableExtension(CLASS_ATT);

					Map<String, List<ISecondaryAnalyzer>> map = this.secondaryIndexer.get(typeId);
					if (map == null) {
						map = new HashMap<String, List<ISecondaryAnalyzer>>();
						this.secondaryIndexer.put(typeId, map);
					}
					List<ISecondaryAnalyzer> list = map.get(node);
					if (list == null) {
						list = new ArrayList<ISecondaryAnalyzer>();
						map.put(node, list);
					}
					list.add(attribute);
				} catch (Exception e) {
					// do nothing
				}

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.IIndexService#getAnalyzerByTypeAndNodeId
	 * (java.lang.String, java.lang.String)
	 */
	public IAnalyzer getAnalyzerByTypeAndNodeId(final String typeId, final String nodeId) {
		checkForInitialization();
		Map<String, String> map = this.indexer.get(typeId);
		if (map != null) {
			String string = map.get(nodeId);
			ISearchAnalyzerService service2 = SearchActivator.getDefault().getServiceTracker()
					.getService(ISearchAnalyzerService.class);
			return service2.getAnalyerById(string);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.remus.infomngmnt.search.service.IIndexService#
	 * getSecondaryAnalyerByTypeAndNodeId(java.lang.String, java.lang.String)
	 */
	public ISecondaryAnalyzer[] getSecondaryAnalyerByTypeAndNodeId(final String typeId,
			final String nodeId) {
		checkForInitialization();
		Map<String, List<ISecondaryAnalyzer>> map = this.secondaryIndexer.get(typeId);
		if (map != null) {
			List<ISecondaryAnalyzer> string = map.get(nodeId);
			if (string != null) {
				return string.toArray(new ISecondaryAnalyzer[string.size()]);
			}
		}
		return new ISecondaryAnalyzer[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.search.service.IIndexService#setAnalyzerService(
	 * org.remus.infomngmnt.search.service.ISearchAnalyzerService)
	 */
	public void setAnalyzerService(final ISearchAnalyzerService service) {
		this.service = service;

	}

}
