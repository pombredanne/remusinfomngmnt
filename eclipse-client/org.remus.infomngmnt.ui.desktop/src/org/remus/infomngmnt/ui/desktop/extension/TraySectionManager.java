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

package org.remus.infomngmnt.ui.desktop.extension;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.ui.desktop.internal.tray.TraySectionDefinition;
import org.remus.uimodel.provider.UimodelEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class TraySectionManager {

	public static final String EXTENSION_POINT = UimodelEditPlugin.Implementation.PLUGIN_ID + ".traySection"; //$NON-NLS-1$

	public static final String TRAYSECTION_NODENAME = "sectionBox"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String MULTIPLE_ATT = "multiple"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String IMPLEMENTATION_ATT = "sectionControl"; //$NON-NLS-1$

	public static final String PREFERENCEPAGE_ATT = "preferencesclass"; //$NON-NLS-1$

	private static TraySectionManager INSTANCE;

	private Map<String, ITraySectionDefinition> items;

	public static TraySectionManager getInstance() {
		if (INSTANCE == null) {
			synchronized (TraySectionManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new TraySectionManager();
				}
			}
		}
		return INSTANCE;
	}

	private TraySectionManager() {
		init();
	}

	private void init() {
		this.items = new HashMap<String, ITraySectionDefinition>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			final ITraySectionDefinition traySection = new TraySectionDefinition(
					configurationElement,
					configurationElement.getAttribute(ID_ATT),
					configurationElement.getAttribute(NAME_ATT),
					Boolean.valueOf(configurationElement.getAttribute(MULTIPLE_ATT)));
			if (this.items.get(configurationElement.getAttribute(ID_ATT)) == null) {
				this.items.put(configurationElement.getAttribute(ID_ATT),traySection);
			}
		}
	}

	public ITraySectionDefinition getSectionDefinitionById(final String id) {
		return this.items.get(id);
	}

	public Collection<ITraySectionDefinition> getAllSections() {
		return this.items.values();
	}

}
