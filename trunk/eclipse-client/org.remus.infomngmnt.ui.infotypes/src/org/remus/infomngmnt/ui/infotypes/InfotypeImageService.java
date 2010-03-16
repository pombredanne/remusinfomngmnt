/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.ui.infotypes;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.common.ui.image.ResourceManager;
import org.remus.infomngmnt.ui.infotypes.service.IInformationTypeImage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InfotypeImageService extends PluginRegistryDynamic implements IInformationTypeImage {

	public static final String EXTENSION_POINT = InfotypesActivator.PLUGIN_ID + ".infotypeimages"; //$NON-NLS-1$

	public static final String IMAGES_NODENAME = "image"; //$NON-NLS-1$

	private static final String INFOTYPE_ATT = "infotype";

	private static final String IMAGE_ATT = "image";

	public InfotypeImageService() {
		super(EXTENSION_POINT);

	}

	private Map<String, ImageInformation> wrapperItems;

	@Override
	protected void init() {
		this.wrapperItems = new HashMap<String, ImageInformation>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (IMAGES_NODENAME.equals(configurationElement.getName())) {
				ImageInformation info = new ImageInformation();
				info.infoType = configurationElement.getAttribute(INFOTYPE_ATT);
				info.contributor = configurationElement.getContributor().getName();
				info.path = configurationElement.getAttribute(IMAGE_ATT);
				this.wrapperItems.put(info.infoType.toLowerCase(), info);
			}
		}

	}

	private static class ImageInformation {
		private String infoType;
		private String contributor;
		private String path;
	}

	public Image getImageByInfoType(final String typeId) {
		checkForInitialization();
		ImageInformation imageInformation = this.wrapperItems.get(typeId.toLowerCase());
		if (imageInformation != null) {
			return ResourceManager.getPluginImage(Platform.getBundle(imageInformation.contributor)
					.getBundleContext(), imageInformation.path);
		}
		return null;
	}

	public ImageDescriptor getImageDescriptorByInfoType(final String typeId) {
		checkForInitialization();
		ImageInformation imageInformation = this.wrapperItems.get(typeId.toLowerCase());
		if (imageInformation != null) {
			return ResourceManager.getPluginImageDescriptor(Platform.getBundle(
					imageInformation.contributor).getBundleContext(), imageInformation.path);
		}
		return null;
	}

}
