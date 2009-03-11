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

package org.remus.infomngmnt.ui.extension;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.remus.infomngmnt.core.extension.PluginRegistryDynamic;
import org.remus.infomngmnt.ui.service.ICollapsibleButtonExtensionService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CollapsibleButtonExtensionService extends PluginRegistryDynamic implements
		ICollapsibleButtonExtensionService {

	public CollapsibleButtonExtensionService() {
		super(EXTENSION_POINT);

	}

	private Map<String, CollapsibleButtonBar> items;

	@Override
	protected void init() {
		this.items = new HashMap<String, CollapsibleButtonBar>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
				EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint
				.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			try {
				final CollapsibleButtonBar item = (CollapsibleButtonBar) configurationElement
						.createExecutableExtension(CLASS_ATT);
				item.setId(configurationElement.getAttribute(ID_ATT));
				item.setTitle(configurationElement.getAttribute(NAME_ATT));
				item.setTooltip(configurationElement.getAttribute(TOOLTIP_ATT));
				item.setContextId(configurationElement.getAttribute(CONTEXT_ID_ATT));
				final ImageDescriptor iconDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(
						configurationElement.getContributor().getName(), configurationElement
								.getAttribute(ICON_ATT));
				if (iconDescriptor != null) {
					item.setIcon(iconDescriptor.createImage());
				}
				if (configurationElement.getAttribute(BIG_IMAGE_ATT) != null) {
					final ImageDescriptor bigImageDescriptor = AbstractUIPlugin
							.imageDescriptorFromPlugin(configurationElement.getContributor()
									.getName(), configurationElement.getAttribute(BIG_IMAGE_ATT));
					if (bigImageDescriptor != null) {
						item.setBigIcon(bigImageDescriptor.createImage());
					}
				}
				try {
					item.setOrder(Integer.valueOf(configurationElement.getAttribute(ORDER_ATT)));
				} catch (final NumberFormatException e) {
					// do nothing.
				}
				this.items.put(item.getId(), item);
			} catch (final CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Collection<CollapsibleButtonBar> getAllItems() {
		final List<CollapsibleButtonBar> itemsAsList = new LinkedList<CollapsibleButtonBar>(
				this.items.values());
		Collections.sort(itemsAsList, new Comparator<CollapsibleButtonBar>() {
			public int compare(final CollapsibleButtonBar arg0, final CollapsibleButtonBar arg1) {
				return ((Integer) arg0.getOrder()).compareTo(arg1.getOrder());
			}
		});
		return itemsAsList;
	}

}
