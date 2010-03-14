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

package org.remus.infomngmnt.core.remote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.core.remote.IInteractiveCredentialProvider.LoginSate;
import org.remus.infomngmnt.core.remote.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.remote.services.IRepositoryService;
import org.remus.infomngmnt.model.remote.IChangeSetDefinition;
import org.remus.infomngmnt.model.remote.ICredentialProvider;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractExtensionRepository extends AbstractRepository {

	private IConfigurationElement element;

	private volatile Map<String, IChangeSetDefinition> changeSetDefinition;

	private static class ChangeSetDefinitionImpl implements IChangeSetDefinition {

		public ChangeSetDefinitionImpl() {
			this.objectIds = new ArrayList<String>();
			this.objectIdValues = new ArrayList<String>();
		}

		List<String> objectIds;

		List<String> objectIdValues;

		public List<String> getRelevantObjectIds() {
			return this.objectIds;
		}

		public List<String> getRelevantObjectIdValues() {
			return this.objectIdValues;
		}
	}

	public void setElement(final IConfigurationElement element) {
		this.element = element;
	}

	@Override
	public ICredentialProvider getCredentialProvider() {
		if (super.getCredentialProvider() == null) {
			try {
				ICredentialProvider createExecutableExtension = (ICredentialProvider) this.element
						.createExecutableExtension(IRepositoryExtensionService.CREDENTIALPROVIDER_ATT);
				createExecutableExtension.setIdentifier(getLocalRepositoryId());
				configureCredentialProvider(createExecutableExtension);
				setCredentialProvider(createExecutableExtension);

			} catch (CoreException e) {
				// TODO Auto-generated catch block
			}
		}
		ICredentialProvider provider = super.getCredentialProvider();
		if (provider instanceof IInteractiveCredentialProvider) {
			if (((IInteractiveCredentialProvider) provider).getInterActionResult() != LoginSate.IN_INTERACTION
					|| ((IInteractiveCredentialProvider) provider).getInterActionResult() != LoginSate.INTERACTION_SUCCESS) {
				((IInteractiveCredentialProvider) provider).startInterAction();
				while (((IInteractiveCredentialProvider) provider).getInterActionResult() != LoginSate.IN_INTERACTION) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return super.getCredentialProvider();
	}

	protected void configureCredentialProvider(final ICredentialProvider createExecutableExtension) {
		// does nothing by default

	}

	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		return null;
	}

	public RemoteRepository getRepositoryById(final String id) {
		IRepositoryService service = RemoteActivator.getDefault().getServiceTracker().getService(
				IRepositoryService.class);
		RemoteRepository repositoryById = service.getRepositoryById(id);
		RemoteActivator.getDefault().getServiceTracker().ungetService(service);
		return repositoryById;
	}

	public IChangeSetDefinition getChangeSetDefinitionForType(final String type) {
		synchronized (this) {
			if (this.changeSetDefinition == null) {
				this.changeSetDefinition = new HashMap<String, IChangeSetDefinition>();
			}
		}
		synchronized (this) {
			if (this.changeSetDefinition.get(type) == null) {
				IConfigurationElement[] children = this.element
						.getChildren(IRepositoryExtensionService.CHANGE_DEFINITION_NODE_NAME);
				for (IConfigurationElement iConfigurationElement : children) {
					if (type.equals(iConfigurationElement
							.getAttribute(IRepositoryExtensionService.INFORMATION_TYPE_ATT))) {
						ChangeSetDefinitionImpl changeSetDefinition = new ChangeSetDefinitionImpl();
						IConfigurationElement[] children3 = iConfigurationElement
								.getChildren(IRepositoryExtensionService.CHANGE_OBJECT_ID_NODE_NAME);
						for (IConfigurationElement iConfigurationElement3 : children3) {
							changeSetDefinition.objectIds.add(iConfigurationElement3
									.getAttribute(IRepositoryExtensionService.PATH_ATT));
						}
						IConfigurationElement[] children4 = iConfigurationElement
								.getChildren(IRepositoryExtensionService.CHANGE_OBJECT_ID_VALUE_NODE_NAME);
						for (IConfigurationElement iConfigurationElement3 : children4) {
							changeSetDefinition.objectIdValues.add(iConfigurationElement3
									.getAttribute(IRepositoryExtensionService.PATH_ATT));
						}
						this.changeSetDefinition.put(type, changeSetDefinition);
						break;
					}
				}
			}
		}
		return this.changeSetDefinition.get(type);
	}

	public boolean multiple() {
		return true;
	}

	public boolean onlyDownload() {
		return false;
	}

}
