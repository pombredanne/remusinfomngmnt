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
package org.remus.infomngmnt.core.internal.extension;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.remus.infomngmnt.core.extension.AbstractCreationFactory;
import org.remus.infomngmnt.core.extension.AbstractInformationRepresentation;
import org.remus.infomngmnt.core.extension.IInfoType;
import org.remus.infomngmnt.core.extension.InformationExtensionManager;

/**
 * A object represenation of a registered information type. This class is
 * @author Tom Seidel <toms@tomosch.de>
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class InfoType implements IInfoType{

	/** The configuration element which comes from the plugin-registry **/
	private final IConfigurationElement configurationElement;
	/** The info-types image **/
	private ImageDescriptor img;
	private Image image;
	private final String type;
	private AbstractCreationFactory createFactory;
	private AbstractInformationRepresentation informationRepresentation;
	private final String contributor;
	private final String createFactoryClass;
	private final String imageFilePath;

	/**
	 * Creates
	 * @param configurationElement
	 * @param contributor
	 * @param type
	 * @param createFactoryClass
	 * @param imageFilePath
	 */
	public InfoType(final IConfigurationElement configurationElement,
			final String contributor,
			final String type,
			final String createFactoryClass,
			final String imageFilePath) {
		this.configurationElement = configurationElement;
		this.contributor = contributor;
		this.type = type;
		this.createFactoryClass = createFactoryClass;
		this.imageFilePath = imageFilePath;

	}

	/**
	 * Returns the creation factory. Every info-type contributes
	 * a separate implementation of how-to create new information
	 * objects
	 * @return the factory which creates new info-objects.
	 */
	public AbstractCreationFactory getCreationFactory() {
		if (this.createFactory == null) {
			try {
				this.createFactory = (AbstractCreationFactory) this.configurationElement.createExecutableExtension(this.createFactoryClass);
			} catch (final CoreException e) {
				//TODO Logging
			}
		}
		return this.createFactory;
	}
	public AbstractInformationRepresentation getInformationRepresentation() {
		if (this.informationRepresentation == null) {
			try {
				this.informationRepresentation =
					(AbstractInformationRepresentation) this.configurationElement
					.createExecutableExtension(InformationExtensionManager.PRESENTATION_ATT);
			} catch (final CoreException e) {
				//TODO Logging
			}
		}
		return this.informationRepresentation;
	}

	public String getType() {
		return this.type;
	}

	public ImageDescriptor getImageDescriptor() {
		if (this.img == null) {
			this.img = AbstractUIPlugin.imageDescriptorFromPlugin(this.contributor, this.imageFilePath);
		}
		return this.img;

	}

	public Image getImage() {
		if (this.image == null) {
			this.image = getImageDescriptor().createImage();
		}
		return this.image;
	}




}
