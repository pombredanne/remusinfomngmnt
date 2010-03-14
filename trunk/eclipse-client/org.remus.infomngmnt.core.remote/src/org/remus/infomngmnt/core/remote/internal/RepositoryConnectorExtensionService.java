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

package org.remus.infomngmnt.core.remote.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.SynchronizableObject;
import org.remus.infomngmnt.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.core.remote.AbstractExtensionRepository;
import org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RepositoryConnectorExtensionService extends PluginRegistryDynamic implements
		IRepositoryExtensionService {

	private volatile Map<String, IConfigurationElement> items;

	private volatile Map<String, AbstractExtensionRepository> browsingInstances;

	private volatile Map<SynchronizableObject, AbstractExtensionRepository> localInstances;

	private IRepositoryService repositoryService;

	public RepositoryConnectorExtensionService() {
		super(EXTENSION_POINT);

	}

	@Override
	public void init() {
		this.items = new HashMap<String, IConfigurationElement>();
		this.browsingInstances = new HashMap<String, AbstractExtensionRepository>();
		this.localInstances = new HashMap<SynchronizableObject, AbstractExtensionRepository>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
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
			AbstractExtensionRepository repository = (AbstractExtensionRepository) configurationElement
					.createExecutableExtension(CLASS_ATT);
			repository.setLabel(configurationElement.getAttribute(NAME_ATT));
			repository.setId(configurationElement.getAttribute(ID_ATT));
			repository.setElement(configurationElement);
			return repository;
		}
		return null;
	}

	public synchronized AbstractExtensionRepository getItemByRepository(
			final RemoteRepository repository) throws CoreException {

		if (this.browsingInstances.get(repository.getId()) == null) {
			AbstractExtensionRepository newInstance = createNew(repository.getRepositoryTypeId());
			newInstance.setLocalRepositoryId(repository.getId());
			this.browsingInstances.put(repository.getId(), newInstance);
		}

		return this.browsingInstances.get(repository.getId());
	}

	public String getNameByRepositoryId(final String id) {
		if (this.items.get(id) != null) {
			IConfigurationElement iConfigurationElement = this.items.get(id);
			return iConfigurationElement.getAttribute(NAME_ATT);
		}
		return null;
	}

	public synchronized AbstractExtensionRepository getItemByLocalObject(
			final SynchronizableObject syncObject) {
		SynchronizableObject currentElement = syncObject;
		while (currentElement.eContainer() != null
				&& ((SynchronizableObject) currentElement.eContainer())
						.getSynchronizationMetaData() != null) {
			currentElement = (SynchronizableObject) currentElement.eContainer();
		}

		if (this.localInstances.get(currentElement) == null) {
			try {
				RemoteRepository repositoryById = this.repositoryService
						.getRepositoryById(currentElement.getSynchronizationMetaData()
								.getRepositoryId());
				String repositoryTypeId = repositoryById.getRepositoryTypeId();
				AbstractExtensionRepository newInstance = createNew(repositoryTypeId);
				newInstance.setLocalRepositoryId(currentElement.getSynchronizationMetaData()
						.getRepositoryId());
				this.localInstances.put(currentElement, newInstance);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.localInstances.get(currentElement);
	}

	public void setRepositoryService(final IRepositoryService service) {
		this.repositoryService = service;

	}

}
