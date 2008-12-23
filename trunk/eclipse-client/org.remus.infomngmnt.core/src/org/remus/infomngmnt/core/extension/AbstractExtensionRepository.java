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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.remus.infomngmnt.core.remote.AbstractRepository;
import org.remus.infomngmnt.core.remote.ICredentialProvider;
import org.remus.infomngmnt.core.services.IRepositoryExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public abstract class AbstractExtensionRepository extends AbstractRepository {

	private IConfigurationElement element;
	
	private String contributor;
	
	private String imagePath;
	
	
	@Override
	public Image getImage() {
		if (super.getImage() == null && this.imagePath != null) {
			ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(this.contributor, this.imagePath);
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
				ICredentialProvider createExecutableExtension = (ICredentialProvider) this.element.createExecutableExtension(IRepositoryExtensionService.CREDENTIALPROVIDER_ATT);
				setCredentialProvider(createExecutableExtension);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
			}
		}
		return super.getCredentialProvider();
	}
	
	


	public void setContributor(final String contributor) {
		this.contributor = contributor;
	}


	public void setImagePath(final String imagePath) {
		this.imagePath = imagePath;
	}
	
}
