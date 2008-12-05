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
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.common.ui.extension.AbstractTrayPreferencePage;
import org.remus.infomngmnt.common.ui.extension.AbstractTraySection;
import org.remus.infomngmnt.common.ui.extension.ITraySectionDefinition;
import org.remus.infomngmnt.common.ui.extension.TraySectionManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TraySectionDefinition implements ITraySectionDefinition{

	private final String label;

	private Image image;

	private final boolean multiple;

	private AbstractTraySection implementation;

	private AbstractTrayPreferencePage preferencePage;

	private final IConfigurationElement configurationElement;

	private final String id;

	public String getId() {
		return this.id;
	}

	public TraySectionDefinition(IConfigurationElement configurationElement,
			String id, String label, boolean multiple) {
		super();
		this.configurationElement = configurationElement;
		this.id = id;
		this.label = label;
		this.multiple = multiple;
	}

	public String getLabel() {
		return this.label;
	}

	public Image getImage() {
		if (this.image == null && this.configurationElement.getAttribute(TraySectionManager.ICON_ATT) != null) {
			this.image = AbstractUIPlugin.imageDescriptorFromPlugin(
					this.configurationElement.getContributor().getName(),this.configurationElement.getAttribute(TraySectionManager.ICON_ATT)).createImage();
		}
		return this.image;
	}

	public boolean isMultiple() {
		return this.multiple;
	}

	public AbstractTraySection getImplementation() {
		if (this.implementation == null) {
			try {
				this.implementation = (AbstractTraySection) this.configurationElement.createExecutableExtension(TraySectionManager.IMPLEMENTATION_ATT);
			} catch (CoreException e) {
				// TODO ErrorHandling
			}
		}
		return this.implementation;
	}

	public IConfigurationElement getConfigurationElement() {
		return this.configurationElement;
	}

	public AbstractTrayPreferencePage getPreferencePage() {
		if (this.preferencePage == null) {
			try {
				this.preferencePage = (AbstractTrayPreferencePage) this.configurationElement.createExecutableExtension(TraySectionManager.PREFERENCEPAGE_ATT);
			} catch (CoreException e) {
				// TODO ErrorHandling
			}
		}
		return this.preferencePage;
	}





}
