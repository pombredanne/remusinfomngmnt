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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.internal.extension.InfoType;
import org.remus.infomngmnt.core.services.IInformationTypeHandler;

/**
 * @author Tom Seidel <toms@tomosch.de>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class InformationExtensionManager extends PluginRegistryDynamic implements
		IInformationTypeHandler {

	public static final String EXTENSION_POINT = CorePlugin.PLUGIN_ID + ".informationType"; //$NON-NLS-1$

	public static final String INFORMATION_NODE_NAME = "information"; //$NON-NLS-1$

	public static final String TYPE_ATT = "type"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String BUILDHTML_ATT = "buildHtml"; //$NON-NLS-1$

	public static final String EXCLUDE_FROM_INDEX_ATT = "excludeFromIndex"; //$NON-NLS-1$

	public static final String POST_CREATION_ATT = "postCreationHandler"; //$NON-NLS-1$

	public static final String PRESENTATION_ATT = "presentation"; //$NON-NLS-1$

	public static final String STRUCTURE_DEFINITION = "structuredefinition"; //$NON-NLS-1$

	private static IInformationTypeHandler INSTANCE;

	private Map<String, IInfoType> items;

	private Logger log;

	public static IInformationTypeHandler getInstance() {
		if (INSTANCE == null) {
			synchronized (InformationExtensionManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new InformationExtensionManager();
				}
			}
		}
		return INSTANCE;
	}

	public InformationExtensionManager() {
		super(EXTENSION_POINT);

	}

	@Override
	protected void init() {
		this.log = Logger.getLogger(InformationExtensionManager.class);
		this.items = new HashMap<String, IInfoType>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			this.log.debug("Found new infotype: " + configurationElement.getAttribute(TYPE_ATT));
			boolean buildHtml = configurationElement.getAttribute(BUILDHTML_ATT) == null ? Boolean.TRUE
					: Boolean.valueOf(configurationElement.getAttribute(BUILDHTML_ATT));
			final InfoType infoType = new InfoType(configurationElement, configurationElement
					.getContributor().getName(), configurationElement.getAttribute(NAME_ATT),
					configurationElement.getAttribute(TYPE_ATT), POST_CREATION_ATT,
					configurationElement.getAttribute(ICON_ATT), buildHtml, Boolean
							.valueOf(configurationElement.getAttribute(EXCLUDE_FROM_INDEX_ATT)),
					configurationElement.getAttribute(STRUCTURE_DEFINITION));
			this.items.put(infoType.getType().toUpperCase(), infoType);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.IInformationTypeHandler#getInfoTypeByType
	 * (java.lang.String)
	 */
	public IInfoType getInfoTypeByType(final String type) {
		return this.items.get(type.toUpperCase());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.core.extension.IInformationTypeHandler#getTypes()
	 */
	public Collection<IInfoType> getTypes() {
		return this.items.values();
	}

}
