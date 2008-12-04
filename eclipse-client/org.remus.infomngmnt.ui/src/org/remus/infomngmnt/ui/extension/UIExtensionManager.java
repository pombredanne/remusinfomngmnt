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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import org.remus.infomngmnt.ui.UIPlugin;
import org.remus.infomngmnt.ui.internal.extension.EditPage;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class UIExtensionManager {

	public static final String EXTENSION_POINT = UIPlugin.PLUGIN_ID + ".informationUi"; //$NON-NLS-1$

	/////-----------------------
	public static final String INFORMATION_NODE_NAME = "editPage"; //$NON-NLS-1$

	public static final String ID_ATT = "type"; //$NON-NLS-1$

	public static final String TYPE_ATT = "type"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String LABEL_ATT = "label"; //$NON-NLS-1$

	public static final String EDIT_PAGE_ATT = "editpage"; //$NON-NLS-1$

	/////-----------------------

	public static final String TRANSFERPREFERENCE_NODE_NAME = "transferpreferences"; //$NON-NLS-1$

	public static final String TYPE_ID_ATT = "typeId"; //$NON-NLS-1$

	public static final String TRANSFER_ID_ATT = "transferId"; //$NON-NLS-1$

	public static final String IMPLEMENTATION_ID_ATT = "implementation"; //$NON-NLS-1$

	/////-----------------------

	private static UIExtensionManager INSTANCE;

	private Map<String,List<IEditPage>> items;

	private Map<String, Map<String, AbstractCreationPreferencePage>> preferencePages;

	public static UIExtensionManager getInstance() {
		if (INSTANCE == null) {
			synchronized (UIExtensionManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new UIExtensionManager();
				}
			}
		}
		return INSTANCE;
	}

	private UIExtensionManager() {
		init();
	}

	private void init() {
		this.items = new HashMap<String,List<IEditPage>>();
		this.preferencePages = new HashMap<String, Map<String,AbstractCreationPreferencePage>>();
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			if (configurationElement.getName().equals(INFORMATION_NODE_NAME)) {
				final IEditPage editPage = new EditPage(
						configurationElement,
						configurationElement.getContributor().getName(),
						configurationElement.getAttribute(TYPE_ATT),
						configurationElement.getAttribute(ID_ATT),
						configurationElement.getAttribute(LABEL_ATT),
						configurationElement.getAttribute(ICON_ATT));
				if (this.items.get(configurationElement.getAttribute(TYPE_ATT)) == null) {
					this.items.put(editPage.getType(),new ArrayList<IEditPage>());
				}
				this.items.get(editPage.getType()).add(editPage);
			} else if (configurationElement.getName().equals(TRANSFERPREFERENCE_NODE_NAME)) {
				String transferId = configurationElement.getAttribute(TRANSFER_ID_ATT);
				String typeId = configurationElement.getAttribute(TYPE_ID_ATT);
				if (this.preferencePages.get(transferId) == null) {
					this.preferencePages.put(transferId, new HashMap<String, AbstractCreationPreferencePage>());
				}
				Map<String, AbstractCreationPreferencePage> map = this.preferencePages.get(transferId);
				if (map.get(typeId) == null) {
					try {
						map.put(typeId, (AbstractCreationPreferencePage) configurationElement.createExecutableExtension(IMPLEMENTATION_ID_ATT));
					} catch (CoreException e) {
						// TODO: Error-Handling
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<IEditPage> getEditPageByType(final String type) {
		if (this.items.get(type) == null) {
			return Collections.EMPTY_LIST;
		}
		return this.items.get(type);
	}

	public AbstractCreationPreferencePage getPreferencePageByTransferAndTypeId(String transferId, String typeId) {
		Map<String, AbstractCreationPreferencePage> map = this.preferencePages.get(transferId);
		if (map != null) {
			return map.get(typeId);
		}
		return null;
	}

}
