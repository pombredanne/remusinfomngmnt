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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osgi.util.NLS;

import org.remus.infomngmnt.provider.InfomngmntEditPlugin;
import org.remus.infomngmnt.resources.util.ResourceUtil;
import org.remus.infomngmnt.search.provider.SearchPlugin;
import org.remus.infomngmnt.search.service.LuceneSearchService;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.resources.IncrementalProjectBuilder#build(int,
	 * java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IProject[] build(final int kind, final Map args, final IProgressMonitor monitor)
			throws CoreException {
		switch (kind) {
		case FULL_BUILD:
			IResource[] members = getProject().members();
			monitor.beginTask(NLS.bind("Refreshing info-object on {0}", getProject().getName()),
					members.length);
			List<IFile> file2reindex = new ArrayList<IFile>();
			for (IResource resource : members) {
				if (resource.getType() == IResource.FILE
						&& resource.getFileExtension().equals(ResourceUtil.FILE_EXTENSION)) {
					monitor.setTaskName(NLS.bind("Reindexing \"{0}\"", resource.getName()));
					file2reindex.add((IFile) resource);
					monitor.worked(1);
				}
			}
			LuceneSearchService.getInstance().addToIndex(Collections.EMPTY_LIST, file2reindex,
					getProject());
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

	@Override
	protected void clean(final IProgressMonitor monitor) throws CoreException {
		LuceneSearchService.getInstance().clean(getProject(), monitor);
		super.clean(monitor);
	}

}
