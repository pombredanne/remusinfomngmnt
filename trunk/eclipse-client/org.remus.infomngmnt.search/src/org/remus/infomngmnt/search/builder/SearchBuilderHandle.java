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

package org.remus.infomngmnt.search.builder;

import org.eclipse.core.resources.IProjectDescription;

import org.remus.infomngmnt.resources.util.IPostProjectHandle;
import org.remus.infomngmnt.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchBuilderHandle implements IPostProjectHandle {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.resources.util.IPostProjectHandle#postProjectCreation
	 * (org.eclipse.core.resources.IProject)
	 */
	public void postProjectCreation(final IProjectDescription project) {
		if (project.getLocationURI() == null
				|| !project.getLocationURI().getScheme().startsWith(
						ResourceUtil.SHEMAPREFIX_ENCRYPTED_PROJECTS)) {
			ResourceUtil.addBuilder(project, SearchBuilder.BUILDER_ID);
		}
	}

}
