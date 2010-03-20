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

package org.remus.infomngmnt.pdf.internal.extension;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.pdf.Activator;
import org.remus.infomngmnt.pdf.extension.IPdfImageRenderer;
import org.remus.infomngmnt.pdf.service.IPDF2ImageExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class Pdf2ImageExtensionService extends PluginRegistryDynamic implements
		IPDF2ImageExtensionService {

	public static final String EXTENSION_POINT_ID = Activator.PLUGIN_ID + ".pdf2image"; //$NON-NLS-1$

	public static final String NODE_NAME = "pdfImageRenderer"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	private Map<String, IPdfImageRenderer> renderer;

	public Pdf2ImageExtensionService() {
		super(EXTENSION_POINT_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic#init()
	 */
	@Override
	protected void init() {
		this.renderer = new HashMap<String, IPdfImageRenderer>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT_ID);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(NODE_NAME)) {
				String id = configurationElement.getAttribute(ID_ATT);
				this.renderer.put(id, new PdfImageRenderer(configurationElement));
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.pdf.service.IPDF2ImageExtensionService#getAllRender
	 * ()
	 */
	public IPdfImageRenderer[] getAllRender() {
		checkForInitialization();
		Collection<IPdfImageRenderer> values = this.renderer.values();
		return values.toArray(new IPdfImageRenderer[values.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.pdf.service.IPDF2ImageExtensionService#getRendererById
	 * (java.lang.String)
	 */
	public IPdfImageRenderer getRendererById(final String id) {
		checkForInitialization();
		return this.renderer.get(id);
	}

}
