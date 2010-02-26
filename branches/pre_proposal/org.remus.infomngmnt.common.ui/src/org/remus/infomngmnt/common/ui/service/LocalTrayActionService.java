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

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;

import org.remus.infomngmnt.common.ui.model.UIEditingUtil;
import org.remus.infomngmt.common.ui.uimodel.DesktopToolItemCollection;
import org.remus.infomngmt.common.ui.uimodel.UIModelPackage;
import org.remus.infomngmt.common.ui.uimodel.provider.UimodelEditPlugin;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class LocalTrayActionService {

	private DesktopToolItemCollection model;

	private static final String DEFINITION_FILE = "tray/actiondefinition.xml"; //$NON-NLS-1$

	LocalTrayActionService() {
		// prevents instantiation
	}

	public DesktopToolItemCollection getItemCollection() {
		if (this.model == null) {
			IPath append = UimodelEditPlugin.getPlugin().getStateLocation().append(DEFINITION_FILE);
			this.model = UIEditingUtil.getInstance().getObjectFromFileUri(URI.createFileURI(append.toString()), UIModelPackage.Literals.DESKTOP_TOOL_ITEM_COLLECTION, false);
		}
		return this.model;
	}

	public void save() {
		IPath append = UimodelEditPlugin.getPlugin().getStateLocation().append(DEFINITION_FILE);
		File parentFile = append.toFile().getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		UIEditingUtil.getInstance().saveObjectToResource(this.model);
	}



}
