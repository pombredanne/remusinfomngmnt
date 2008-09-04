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

	public static final String INFORMATION_NODE_NAME = "editPage"; //$NON-NLS-1$

	public static final String ID_ATT = "type"; //$NON-NLS-1$

	public static final String TYPE_ATT = "type"; //$NON-NLS-1$

	public static final String ICON_ATT = "icon"; //$NON-NLS-1$

	public static final String EDIT_PAGE_ATT = "editpage"; //$NON-NLS-1$

	private static UIExtensionManager INSTANCE;

	private Map<String,List<IEditPage>> items;

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
		final IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(EXTENSION_POINT);
		final IConfigurationElement[] configurationElements = extensionPoint.getConfigurationElements();
		for (final IConfigurationElement configurationElement : configurationElements) {
			final IEditPage editPage = new EditPage(
					configurationElement,
					configurationElement.getContributor().getName(),
					configurationElement.getAttribute(TYPE_ATT),
					configurationElement.getAttribute(ID_ATT),
					configurationElement.getAttribute(ICON_ATT));
			if (this.items.get(configurationElement.getAttribute(TYPE_ATT)) == null) {
				this.items.put(editPage.getType(),new ArrayList<IEditPage>());
			}
			this.items.get(editPage.getType()).add(editPage);
		}
	}

	@SuppressWarnings("unchecked")
	public List<IEditPage> getEditPageByType(final String type) {
		if (this.items.get(type) == null) {
			return Collections.EMPTY_LIST;
		}
		return this.items.get(type);
	}

}
