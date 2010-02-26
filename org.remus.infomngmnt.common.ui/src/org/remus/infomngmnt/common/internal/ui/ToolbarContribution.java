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

package org.remus.infomngmnt.common.internal.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.common.ui.extension.IToolbarContribution;
import org.remus.infomngmnt.common.ui.extension.IToolbarItemProvider;
import org.remus.infomngmnt.common.ui.service.ToolbarContributionExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ToolbarContribution implements IToolbarContribution {

	public ToolbarContribution(IConfigurationElement configurationElement,
			String id, String label, String imagePath) {
		super();
		this.configurationElement = configurationElement;
		this.id = id;
		this.label = label;
		this.imagePath = imagePath;
	}

	private final String imagePath;

	private final String id;

	private Image image;

	private final String label;

	private final IConfigurationElement configurationElement;

	private IToolbarItemProvider contribution;

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.extension.IToolbarContribution#getId()
	 */
	public String getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.extension.IToolbarContribution#getImage()
	 */
	public Image getImage() {
		if (this.image == null) {
			ImageDescriptor imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
					this.configurationElement.getContributor().getName(), this.imagePath);
			if (imageDescriptor != null) {
				this.image = imageDescriptor.createImage();
			}
		}
		return this.image;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.extension.IToolbarContribution#getName()
	 */
	public String getName() {
		return this.label;
	}

	/* (non-Javadoc)
	 * @see org.remus.infomngmnt.common.ui.extension.IToolbarContribution#getToolItemProvider()
	 */
	public IToolbarItemProvider getToolItemProvider() {
		if (this.contribution == null) {
			try {
				this.contribution = (IToolbarItemProvider) this.configurationElement.createExecutableExtension(ToolbarContributionExtensionService.CLASS_ATT);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return this.contribution;
	}

}
