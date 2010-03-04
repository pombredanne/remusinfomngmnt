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

package org.remus.infomngmnt.ui.remote.internal.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.ui.remote.service.IRepositoryExtensionService;
import org.remus.infomngmnt.ui.remote.service.IRepositoryUI;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @since 1.0
 */
public class RepositoryExtensionService extends PluginRegistryDynamic implements
		IRepositoryExtensionService {

	private Map<String, IRepositoryUI> items;

	public RepositoryExtensionService() {
		super(EXTENSION_POINT);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.remus.infomngmnt.core.extension.PluginRegistryDynamic#init()
	 */
	@Override
	public void init() {
		if (this.items == null) {
			this.items = new HashMap<String, IRepositoryUI>();
		}
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			IRepositoryUI item = new RepositoryUI(configurationElement, configurationElement
					.getAttribute(ID_ATT), configurationElement.getAttribute(REPOSITORY_ID));
			this.items.put(item.getId(), item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.service.IRepositoryExtensionService#getAllItems()
	 */
	public Collection<IRepositoryUI> getAllItems() {
		return this.items.values();
	}

	public IRepositoryUI getItemById(final String id) {
		return this.items.get(id);
	}

	public IRepositoryUI getItemByRepositoryId(final String repositoryId) {
		Collection<IRepositoryUI> allItems = getAllItems();
		for (IRepositoryUI iRepositoryUi : allItems) {
			if (iRepositoryUi.getRepositoryId().equals(repositoryId)) {
				return iRepositoryUi;
			}
		}
		return null;
	}

}
