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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.InformationUnit;
import org.remus.infomngmnt.RemoteObject;
import org.remus.infomngmnt.RemoteRepository;
import org.remus.infomngmnt.core.remote.AbstractRepository;
import org.remus.infomngmnt.core.remote.IChangeSetDefinition;
import org.remus.infomngmnt.core.remote.ICredentialProvider;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;
import org.remus.infomngmnt.core.services.IRepositoryService;
import org.remus.infomngmnt.provider.InfomngmntEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractExtensionRepository extends AbstractRepository {

	private IConfigurationElement element;

	private String contributor;

	private String imagePath;

	private Map<String, IChangeSetDefinition> changeSetDefinition;

	private static class ChangeSetDefinitionImpl implements IChangeSetDefinition {

		public ChangeSetDefinitionImpl() {
			this.objectPaths = new ArrayList<String>();
		}

		List<String> objectPaths;

		public List<String> getRelevantObjectPaths() {
			return this.objectPaths;
		}
	}

	@Override
	public Image getImage() {
		if (super.getImage() == null && this.imagePath != null) {
			ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
					this.contributor, this.imagePath);
			if (imageDescriptor != null) {
				setImage(imageDescriptor.createImage());
			}
		}
		return super.getImage();
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
				setCredentialProvider(createExecutableExtension);

			} catch (CoreException e) {
				// TODO Auto-generated catch block
			}
		}
		return super.getCredentialProvider();
	}

	public InformationUnit getPrefetchedInformationUnit(final RemoteObject remoteObject) {
		return null;
	}

	public void setContributor(final String contributor) {
		this.contributor = contributor;
	}

	public RemoteRepository getRepositoryById(final String id) {
		return InfomngmntEditPlugin.getPlugin().getService(IRepositoryService.class)
				.getRepositoryById(id);
	}

	public void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}

	public IChangeSetDefinition getChangeSetDefinitionForType(final String type) {
		if (this.changeSetDefinition == null) {
			this.changeSetDefinition = new HashMap<String, IChangeSetDefinition>();
		}
		if (this.changeSetDefinition.get(type) == null) {
			IConfigurationElement[] children = this.element
					.getChildren(IRepositoryExtensionService.CHANGE_DEFINITION_NODE_NAME);
			for (IConfigurationElement iConfigurationElement : children) {
				if (type.equals(iConfigurationElement
						.getAttribute(IRepositoryExtensionService.INFORMATION_TYPE_ATT))) {
					ChangeSetDefinitionImpl changeSetDefinition = new ChangeSetDefinitionImpl();
					IConfigurationElement[] children2 = iConfigurationElement
							.getChildren(IRepositoryExtensionService.CHANGE_OBJECT_PATH_NODE_NAME);
					for (IConfigurationElement iConfigurationElement2 : children2) {
						changeSetDefinition.objectPaths.add(iConfigurationElement2
								.getAttribute(IRepositoryExtensionService.PATH_ATT));
					}
					this.changeSetDefinition.put(type, changeSetDefinition);
					break;
				}
			}
		}
		return this.changeSetDefinition.get(type);
	}
}
