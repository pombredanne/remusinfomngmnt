/*******************************************************************************
 * Copyright (c) 2009 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.birtreport.extension;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.birtreport.ReportActivator;
import org.remus.infomngmnt.birtreport.parameter.AbstractParameterControl;
import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ParameterControlManager extends PluginRegistryDynamic {

	public static final String EXTENSION_POINT = ReportActivator.PLUGIN_ID + ".reporttemplate"; //$NON-NLS-1$

	public static final String NODE_NAME_PARAM_CONTROL = "parametertype"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	private static ParameterControlManager INSTANCE;

	private Map<String, IConfigurationElement> items;

	ParameterControlManager() {
		super(EXTENSION_POINT);
	}

	public static ParameterControlManager getInstance() {
		if (INSTANCE == null) {
			synchronized (ParameterControlManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new ParameterControlManager();
				}
			}
		}
		return INSTANCE;
	}

	@Override
	protected void init() {
		this.items = new HashMap<String, IConfigurationElement>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (IConfigurationElement iConfigurationElement : configurationElements) {
			if (NODE_NAME_PARAM_CONTROL.equals(iConfigurationElement.getName())) {
				this.items.put(iConfigurationElement.getAttribute(ID_ATT), iConfigurationElement);

			}
		}

	}

	public AbstractParameterControl getControlById(final String id) throws CoreException {
		IConfigurationElement iConfigurationElement = this.items.get(id);
		if (iConfigurationElement != null) {
			return (AbstractParameterControl) iConfigurationElement
					.createExecutableExtension(CLASS_ATT);
		}
		return null;
	}
}
