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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.core.remote.ICredentialProvider;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RepositoryConnectorExtensionService extends PluginRegistryDynamic implements IRepositoryExtensionService {



	private Map<String, IConfigurationElement> items;

	private Map<String, AbstractExtensionRepository> instances;

	public RepositoryConnectorExtensionService() {
		super(EXTENSION_POINT);
	}

	@Override
	public void init() {
		this.items = new HashMap<String, IConfigurationElement>();
		this.instances = new HashMap<String, AbstractExtensionRepository>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(CONNECTOR_NODENAME)) {
				try {
					this.items.put(configurationElement.getAttribute(ID_ATT), configurationElement);
				} catch (InvalidRegistryObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private AbstractExtensionRepository createNew(final String id) throws CoreException {
		if (this.items.get(id) != null) {
			IConfigurationElement configurationElement = this.items.get(id);
			AbstractExtensionRepository repository = (AbstractExtensionRepository) configurationElement.createExecutableExtension(CLASS_ATT);
			repository.setContributor(configurationElement.getContributor().getName());
			repository.setLabel(configurationElement.getAttribute(NAME_ATT));
			repository.setImagePath(configurationElement.getAttribute(ICON_ATT));
			repository.setId(configurationElement.getAttribute(ID_ATT));
			repository.setCredentialProvider((ICredentialProvider) configurationElement.createExecutableExtension(CREDENTIALPROVIDER_ATT));
			return repository;
		}
		return null;
	}

	public AbstractExtensionRepository getItemByRepository(final RemoteRepository repository) throws CoreException {
		if (this.instances.get(repository.getId()) == null) {


			AbstractExtensionRepository newInstance = createNew(repository.getRepositoryTypeId());
			newInstance.setLocalRepositoryId(repository.getId());
			this.instances.put(repository.getId(), newInstance);

		}
		return this.instances.get(repository.getId());
	}

	public ImageDescriptor getImageByRepositoryId(final String id) {
		if (this.items.get(id) != null) {
			IConfigurationElement iConfigurationElement = this.items.get(id);
			return AbstractUIPlugin.imageDescriptorFromPlugin(
					iConfigurationElement.getContributor().getName(), 
					iConfigurationElement.getAttribute(ICON_ATT));
		}
		return null;
	}

	public String getNameByRepositoryId(final String id) {
		if (this.items.get(id) != null) {
			IConfigurationElement iConfigurationElement = this.items.get(id);
			return iConfigurationElement.getAttribute(NAME_ATT);
		}
		return null;
	}



}
