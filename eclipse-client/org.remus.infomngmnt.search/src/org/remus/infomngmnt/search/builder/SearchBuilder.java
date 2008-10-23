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

import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.search.provider.SearchPlugin;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchBuilder extends IncrementalProjectBuilder {



	public static final String BUILDER_ID = SearchPlugin.PLUGIN_ID + ".searchBuilder"; //$NON-NLS-1$
	private SearchVisitor visitor;

	/**
	 * 
	 */
	public SearchBuilder() {

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
	throws CoreException {
		switch (kind) {
		case FULL_BUILD:
			IFolder folder = getProject().getFolder(ResourceUtil.BIN_FOLDER);
			if (!folder.exists()) {
				folder.create(true, true, monitor);
			}
			break;
		default:
			break;
		}

		this.visitor = new SearchVisitor(getProject());
		if (getDelta(getProject()) != null) {
			proceedDelta(getDelta(getProject()), monitor);
		}
		return null;
	}

	private void proceedDelta(final IResourceDelta delta, final IProgressMonitor monitor) {
		try {
			delta.accept(this.visitor);
		} catch (CoreException e) {
			InfomngmntEditPlugin.INSTANCE.log(e.getStatus());
		}
	}
}
