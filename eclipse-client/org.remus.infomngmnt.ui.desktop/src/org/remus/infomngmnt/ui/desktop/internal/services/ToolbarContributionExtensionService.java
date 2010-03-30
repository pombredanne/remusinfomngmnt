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

package org.remus.infomngmnt.ui.desktop.internal.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.ui.desktop.extension.IToolbarContribution;
import org.remus.infomngmnt.ui.desktop.internal.tray.ToolbarContribution;
import org.remus.infomngmnt.ui.desktop.services.IToolbarContributionService;
import org.remus.uimodel.provider.UimodelEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ToolbarContributionExtensionService extends PluginRegistryDynamic implements
		IToolbarContributionService {

	public static final String EXTENSION_POINT = UimodelEditPlugin.Implementation.PLUGIN_ID
			+ ".trayAction"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	private Map<String, IToolbarContribution> items;

	public ToolbarContributionExtensionService() {
		super(EXTENSION_POINT);

	}

	@Override
	protected void init() {
		this.items = new HashMap<String, IToolbarContribution>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (IConfigurationElement configurationElement : configurationElements) {
			IToolbarContribution toolbarContribution = new ToolbarContribution(
					configurationElement, configurationElement.getAttribute(ID_ATT),
					configurationElement.getAttribute(NAME_ATT), configurationElement
							.getAttribute(ICON_ATT));
			this.items.put(toolbarContribution.getId(), toolbarContribution);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.desktop.internal.services.IToolbarContributionService
	 * #getAllContributions()
	 */
	public Collection<IToolbarContribution> getAllContributions() {
		checkForInitialization();
		return this.items.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.desktop.internal.services.IToolbarContributionService
	 * #getContributionById(java.lang.String)
	 */
	public IToolbarContribution getContributionById(final String id) {
		checkForInitialization();
		return this.items.get(id);
	}
}
