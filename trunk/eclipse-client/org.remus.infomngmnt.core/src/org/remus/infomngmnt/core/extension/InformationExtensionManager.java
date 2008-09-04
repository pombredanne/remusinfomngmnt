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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.remus.infomngmnt.core.CorePlugin;
import org.remus.infomngmnt.core.internal.extension.InfoType;

/**
 * @author Tom Seidel <toms@tomosch.de>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class InformationExtensionManager {

	public static final String EXTENSION_POINT = CorePlugin.PLUGIN_ID + ".informationType"; //$NON-NLS-1$

	public static final String INFORMATION_NODE_NAME = "information"; //$NON-NLS-1$

	public static final String TYPE_ATT = "type"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String CREATION_FACTORY_ATT = "creationFactory"; //$NON-NLS-1$

	public static final String PRESENTATION_ATT = "presentation"; //$NON-NLS-1$

	private static InformationExtensionManager INSTANCE;

	private Map<String,IInfoType> items;

	public static InformationExtensionManager getInstance() {
		if (INSTANCE == null) {
			synchronized (InformationExtensionManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new InformationExtensionManager();
				}
			}
		}
		return INSTANCE;
	}

	private InformationExtensionManager() {
		init();
	}

	private void init() {
		this.items = new HashMap<String,IInfoType>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			final InfoType infoType = new InfoType(
					configurationElement,
					configurationElement.getContributor().getName(),
					configurationElement.getAttribute(TYPE_ATT),
					CREATION_FACTORY_ATT,
					configurationElement.getAttribute(ICON_ATT));
			this.items.put(infoType.getType(),infoType);
		}
	}

	public IInfoType getInfoTypeByType(final String type) {
		return this.items.get(type);
	}

}
