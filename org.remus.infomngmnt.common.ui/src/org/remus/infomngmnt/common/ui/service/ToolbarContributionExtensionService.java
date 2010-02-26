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

package org.remus.infomngmnt.common.ui.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.internal.ui.ToolbarContribution;
import org.remus.infomngmnt.common.ui.extension.IToolbarContribution;
import org.remus.infomngmt.common.ui.uimodel.provider.UimodelEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ToolbarContributionExtensionService {

	public static final String EXTENSION_POINT = UimodelEditPlugin.Implementation.PLUGIN_ID + ".trayAction"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	private Map<String, IToolbarContribution> items;

	ToolbarContributionExtensionService() {
		init();
	}

	private void init() {
		this.items = new HashMap<String, IToolbarContribution>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (IConfigurationElement configurationElement : configurationElements) {
			IToolbarContribution toolbarContribution = new ToolbarContribution(
					configurationElement,
					configurationElement.getAttribute(ID_ATT),
					configurationElement.getAttribute(NAME_ATT),
					configurationElement.getAttribute(ICON_ATT));
			this.items.put(toolbarContribution.getId(), toolbarContribution);
		}

	}


	public Collection<IToolbarContribution> getAllContributions() {
		return this.items.values();
	}

	public IToolbarContribution getContributionById(String id) {
		return this.items.get(id);
	}
}
