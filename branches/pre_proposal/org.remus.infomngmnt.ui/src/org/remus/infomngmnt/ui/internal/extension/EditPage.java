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

package org.remus.infomngmnt.ui.internal.extension;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.ui.extension.AbstractInformationFormPage;
import org.remus.infomngmnt.ui.extension.IEditPage;
import org.remus.infomngmnt.ui.extension.UIExtensionManager;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class EditPage implements IEditPage {

	/** The configuration element which comes from the plugin-registry **/
	private final IConfigurationElement configurationElement;
	/** The info-types image **/
	private ImageDescriptor img;
	private final String type;
	private AbstractInformationFormPage formPage;
	private final String contributor;
	private final String imageFilePath;
	private final String id;
	private final String label;

	/**
	 * Creates
	 * 
	 * @param configurationElement
	 * @param contributor
	 * @param type
	 * @param createFactoryClass
	 * @param imageFilePath
	 */
	public EditPage(final IConfigurationElement configurationElement, final String contributor,
			final String type, final String id, final String label, final String imageFilePath) {
		this.configurationElement = configurationElement;
		this.contributor = contributor;
		this.type = type;
		this.id = id;
		this.label = label;
		this.imageFilePath = imageFilePath;

	}

	public AbstractInformationFormPage getEditPage() {
		try {
			return (AbstractInformationFormPage) this.configurationElement
					.createExecutableExtension(UIExtensionManager.EDIT_PAGE_ATT);
		} catch (CoreException e) {
			throw new IllegalArgumentException("Could not initialize edit-page");
		}
	}

	public String getType() {
		return this.type;
	}

	public ImageDescriptor getImage() {
		if (this.img == null && this.imageFilePath != null) {
			this.img = AbstractUIPlugin.imageDescriptorFromPlugin(this.contributor,
					this.imageFilePath);
		}
		return this.img;

	}

	public String getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}

}
