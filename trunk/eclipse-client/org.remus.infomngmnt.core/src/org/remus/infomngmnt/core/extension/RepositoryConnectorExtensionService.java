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

package org.remus.infomngmnt.core.extension;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RepositoryConnectorExtensionService extends PluginRegistryDynamic implements IRepositoryExtensionService {

	
	
	private Map<String, AbstractExtensionRepository> items;
	
	public RepositoryConnectorExtensionService() {
		super(EXTENSION_POINT);
	}

	@Override
	public void init() {
		this.items = new HashMap<String, AbstractExtensionRepository>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(CONNECTOR_NODENAME)) {
				try {
					AbstractExtensionRepository repository = (AbstractExtensionRepository) configurationElement.createExecutableExtension(CLASS_ATT);
					repository.setContributor(configurationElement.getContributor().getName());
					repository.setLabel(configurationElement.getAttribute(NAME_ATT));
					repository.setImagePath(configurationElement.getAttribute(ICON_ATT));
					repository.setId(configurationElement.getAttribute(ID_ATT));
					this.items.put(configurationElement.getAttribute(ID_ATT), repository);
				} catch (InvalidRegistryObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
	}

	public AbstractExtensionRepository getItemById(final String id) {
		return this.items.get(id);
	}

	public Collection<AbstractExtensionRepository> getItems() {
		return this.items.values();
	}

	

}
