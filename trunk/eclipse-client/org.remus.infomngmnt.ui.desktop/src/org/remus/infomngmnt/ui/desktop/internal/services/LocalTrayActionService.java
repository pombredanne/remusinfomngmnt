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

import java.io.File;
import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.runtime.IPath;

import org.remus.infomngmnt.model.service.IResourceLoader;
import org.remus.infomngmnt.model.service.ResourceConstants;
import org.remus.infomngmnt.ui.desktop.services.ILocalTrayActionService;
import org.remus.uimodel.DesktopToolItemCollection;
import org.remus.uimodel.UimodelPackage;
import org.remus.uimodel.provider.UimodelEditPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LocalTrayActionService implements ILocalTrayActionService {

	private DesktopToolItemCollection model;

	private static final String DEFINITION_FILE = "tray/actiondefinition.xml"; //$NON-NLS-1$

	private IResourceLoader service;

	public LocalTrayActionService() {
		// prevents instantiation
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.desktop.internal.services.ILocalTrayActionService
	 * #getItemCollection()
	 */
	public DesktopToolItemCollection getItemCollection() {
		if (this.model == null) {
			IPath append = UimodelEditPlugin.getPlugin().getStateLocation().append(DEFINITION_FILE);
			File parentFile = append.toFile().getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			this.model = this.service.getObjectFromResourceUri(append.toOSString(),
					UimodelPackage.Literals.DESKTOP_TOOL_ITEM_COLLECTION, Collections.singletonMap(
							UimodelPackage.eNS_URI, UimodelPackage.eINSTANCE));
		}
		return this.model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.ui.desktop.internal.services.ILocalTrayActionService
	 * #save()
	 */
	public void save() throws IOException {
		this.model.eResource().save(ResourceConstants.SAVE_OPTIONS);
	}

	public void setResourceLoader(final IResourceLoader service) {
		this.service = service;

	}

}
