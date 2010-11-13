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

package org.remus.infomngmnt.ide.integration;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.remus.resources.util.ResourceUtil;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class RIMNavigatorFilter extends ViewerFilter {

	/**
	 * 
	 */
	public RIMNavigatorFilter() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(final Viewer viewer, final Object parentElement,
			final Object element) {
		return !(element instanceof IProject && (ResourceUtil
				.isRelevantProject((IProject) element)
				|| "__tmp".equals(((IProject) element).getName())
				|| ResourceUtil.PROJECT_NAME_INTERN.equals(((IProject) element)
						.getName()) || "__internal_referencedLibraries"
				.equals(((IProject) element).getName())));
	}

}
