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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.core.CorePlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class RuleExtensionManager extends PluginRegistryDynamic{

	public static final String EXTENSION_POINT = CorePlugin.PLUGIN_ID + ".ruleDefinition"; //$NON-NLS-1$

	public static final String TRANSFERTYPE_NODENAME = "transfertype"; //$NON-NLS-1$

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String IMPLEMENTATION_ATT = "implementation"; //$NON-NLS-1$

	private static RuleExtensionManager INSTANCE;



	private Map<String, TransferWrapper> wrapperItems;

	public static RuleExtensionManager getInstance() {
		if (INSTANCE == null) {
			synchronized (RuleExtensionManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new RuleExtensionManager();
				}
			}
		}
		return INSTANCE;
	}

	private RuleExtensionManager() {
		super(EXTENSION_POINT);
	}

	@Override
	protected void init() {
		this.wrapperItems = new HashMap<String, TransferWrapper>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(TRANSFERTYPE_NODENAME)) {
				try {
					TransferWrapper wrapper = (TransferWrapper) configurationElement.createExecutableExtension(IMPLEMENTATION_ATT);
					wrapper.setName(configurationElement.getAttribute(NAME_ATT));
					this.wrapperItems.put(
							configurationElement.getAttribute(ID_ATT),wrapper);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public Map<String, TransferWrapper> getAllTransferTypes() {
		return this.wrapperItems;
	}

	public TransferWrapper getTransferTypeById(String transferId) {
		return this.wrapperItems.get(transferId);
	}

}
