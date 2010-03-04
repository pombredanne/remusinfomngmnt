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

package org.remus.infomngmnt.efs.internal.extension;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.efs.EFSActivator;
import org.remus.infomngmnt.efs.extension.AbstractSecurityProvider;
import org.remus.infomngmnt.efs.extension.ISecurityProviderExtension;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SecurityProviderExtension extends PluginRegistryDynamic implements
		ISecurityProviderExtension {

	public static final String EXTENSION_POINT = EFSActivator.PLUGIN_ID + ".securityProvider"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String DESCRIPTION_ATT = "description"; //$NON-NLS-1$

	public static final String SCHEME_ATT = "scheme"; //$NON-NLS-1$

	private HashMap<String, AbstractSecurityProvider> items;

	public SecurityProviderExtension() {
		super(EXTENSION_POINT);
	}

	@Override
	protected void init() {
		this.items = new HashMap<String, AbstractSecurityProvider>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			try {
				AbstractSecurityProvider createExecutableExtension = (AbstractSecurityProvider) configurationElement
						.createExecutableExtension(CLASS_ATT);
				createExecutableExtension.setScheme(configurationElement.getAttribute(SCHEME_ATT));
				createExecutableExtension.setId(configurationElement.getAttribute(ID_ATT));
				createExecutableExtension.setName(configurationElement.getAttribute(NAME_ATT));
				IConfigurationElement[] children = configurationElement
						.getChildren(DESCRIPTION_ATT);
				if (children.length > 0) {
					createExecutableExtension.setDescription(children[0].getValue());
				}
				this.items
						.put(configurationElement.getAttribute(ID_ATT), createExecutableExtension);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Collection<AbstractSecurityProvider> getItems() {
		return this.items.values();
	}

	public AbstractSecurityProvider getProviderByFileSystem(final String filesystemId) {
		Collection<AbstractSecurityProvider> values = this.items.values();
		for (AbstractSecurityProvider abstractSecurityProvider : values) {
			if (abstractSecurityProvider.getScheme().equals(filesystemId)) {
				return abstractSecurityProvider;
			}
		}
		return null;
	}

}
