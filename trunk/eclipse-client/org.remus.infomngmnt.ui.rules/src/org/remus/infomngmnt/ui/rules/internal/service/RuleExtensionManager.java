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
package org.remus.infomngmnt.ui.rules.internal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.common.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.ui.rules.service.ICreationTrigger;
import org.remus.infomngmnt.ui.rules.service.IRuleExtensionService;
import org.remus.infomngmnt.ui.rules.transfer.TransferWrapper;
import org.remus.rules.provider.RuleEditPlugin.Implementation;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class RuleExtensionManager extends PluginRegistryDynamic implements IRuleExtensionService {

	public static final String EXTENSION_POINT = Implementation.PLUGIN_ID + ".ruleDefinition"; //$NON-NLS-1$

	public static final String TRANSFERTYPE_NODENAME = "transfertype";

	public static final String CREATION_TRIGGER_NODENAME = "creationtrigger";

	public static final String ID_ATT = "id"; //$NON-NLS-1$

	public static final String TYPEID_ATT = "typeId"; //$NON-NLS-1$

	public static final String CLASS_ATT = "class"; //$NON-NLS-1$

	public static final String NAME_ATT = "name"; //$NON-NLS-1$

	public static final String IMPLEMENTATION_ATT = "implementation"; //$NON-NLS-1$

	private Map<String, TransferWrapper> wrapperItems;

	private Map<String, IConfigurationElement> creationTrigger;

	private Map<String, List<String>> validTransfers;

	public static final String TRANSFERID_ATT = "transferId"; //$NON-NLS-1$

	public static final String TRANSFER_TYPE_NODENAME = "validTransfers"; //$NON-NLS-1$

	public RuleExtensionManager() {
		super(EXTENSION_POINT);
	}

	@Override
	public void init() {
		this.wrapperItems = new HashMap<String, TransferWrapper>();
		this.creationTrigger = new HashMap<String, IConfigurationElement>();
		this.validTransfers = new HashMap<String, List<String>>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(TRANSFERTYPE_NODENAME)) {
				try {
					TransferWrapper wrapper = (TransferWrapper) configurationElement
							.createExecutableExtension(IMPLEMENTATION_ATT);
					wrapper.setName(configurationElement.getAttribute(NAME_ATT));
					this.wrapperItems.put(configurationElement.getAttribute(ID_ATT), wrapper);
				} catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (configurationElement.getName().equals(CREATION_TRIGGER_NODENAME)) {
				try {

					this.creationTrigger.put(configurationElement.getAttribute(TYPEID_ATT),
							configurationElement);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (configurationElement.getName().equals(TRANSFER_TYPE_NODENAME)) {

				String transferId = configurationElement.getAttribute(TRANSFERID_ATT);
				String typeID = configurationElement.getAttribute(TYPEID_ATT);
				List<String> list = this.validTransfers.get(typeID);
				if (list == null) {
					list = new ArrayList<String>();
					this.validTransfers.put(typeID, list);
				}
				list.add(transferId);
			}

		}
	}

	public Map<String, TransferWrapper> getAllTransferTypes() {
		return this.wrapperItems;
	}

	public TransferWrapper getTransferTypeById(final String transferId) {
		return this.wrapperItems.get(transferId);
	}

	public ICreationTrigger getCreationTriggerByTypeId(final String typId) {
		ICreationTrigger createExecutableExtension = null;
		try {
			createExecutableExtension = (ICreationTrigger) this.creationTrigger.get(typId)
					.createExecutableExtension(CLASS_ATT);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return createExecutableExtension;
	}

	public List<String> getTransferIdsByTypeId(final String typeId) {
		return this.validTransfers.get(typeId);
	}

	public Map<String, List<String>> getTransfers() {
		return this.validTransfers;
	}

}
