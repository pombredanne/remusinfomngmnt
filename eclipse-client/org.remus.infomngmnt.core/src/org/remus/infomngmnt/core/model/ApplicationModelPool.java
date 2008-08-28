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

package org.remus.infomngmnt.core.model;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InfomngmntFactory;
import org.remus.infomngmnt.InfomngmntPackage;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ApplicationModelPool {

	private static ApplicationModelPool INSTANCE;

	public static ApplicationModelPool getInstance() {
		if (INSTANCE == null) {
			synchronized (ApplicationModelPool.class) {
				if (INSTANCE == null) {
					INSTANCE = new ApplicationModelPool();
				}
			}
		}
		return INSTANCE;
	}

	private final ApplicationRoot model;

	private ApplicationModelPool() {
		this.model = InfomngmntFactory.eINSTANCE.createApplicationRoot();
		IProject[] relevantProjects = ResourceUtil.getRelevantProjects();
		for (IProject project : relevantProjects) {
			IFile file = project.getFile(new Path(ResourceUtil.SETTINGS_FOLDER + File.separator + ResourceUtil.PRIMARY_CONTENT_FILE));
			Category category = EditingUtil.getInstance().getObjectFromFile(file, InfomngmntPackage.eINSTANCE.getCategory(), true);
			this.model.getRootCategories().add(category);
		}
	}

	public ApplicationRoot getModel() {
		return this.model;
	}

}
