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

package org.remus.infomngmnt.search.internal.builder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;

import org.remus.infomngmnt.search.SearchActivator;
import org.remus.infomngmnt.search.service.ISearchService;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class SearchVisitor implements IResourceDeltaVisitor {

	public static final String INFO_FILE_EXTENSION = "info"; //$NON-NLS-1$

	private List<IFile> filesTodeleteFromIndex;

	private List<IFile> filesToAddToIndex;

	private final IProject project;

	public SearchVisitor(final IProject project) {
		this.project = project;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core
	 * .resources.IResourceDelta)
	 */
	public boolean visit(final IResourceDelta delta) throws CoreException {
		this.filesTodeleteFromIndex = new ArrayList<IFile>();
		this.filesToAddToIndex = new ArrayList<IFile>();
		visitRecursively(delta);
		handleIndexRefresh();
		return true;
	}

	private void handleIndexRefresh() {
		ISearchService searchService = SearchActivator.getDefault().getServiceTracker().getService(
				ISearchService.class);
		searchService.addToIndex(this.filesTodeleteFromIndex, this.filesToAddToIndex, this.project);
		this.filesToAddToIndex.clear();
		this.filesTodeleteFromIndex.clear();
		SearchActivator.getDefault().getServiceTracker().ungetService(searchService);
	}

	public void visitRecursively(final IResourceDelta delta) {
		IResourceDelta[] affectedChildren = delta.getAffectedChildren();
		for (IResourceDelta resourceDelta : affectedChildren) {
			if (resourceDelta.getResource() != null
					&& resourceDelta.getResource().getType() == IResource.FILE
					&& resourceDelta.getResource().getFileExtension().equals(INFO_FILE_EXTENSION)
					&& resourceDelta.getResource().getParent().getType() == IResource.PROJECT) {
				switch (resourceDelta.getKind()) {
				case IResourceDelta.CHANGED:
					if (resourceDelta.getResource().getType() == IResource.FILE
							&& resourceDelta.getResource().getFileExtension().equals(
									INFO_FILE_EXTENSION)) {
						this.filesTodeleteFromIndex.add((IFile) resourceDelta.getResource());
						this.filesToAddToIndex.add((IFile) resourceDelta.getResource());
					}

					break;
				case IResourceDelta.ADDED:
					if (resourceDelta.getResource().getType() == IResource.FILE
							&& resourceDelta.getResource().getFileExtension().equals(
									INFO_FILE_EXTENSION)) {
						this.filesToAddToIndex.add((IFile) resourceDelta.getResource());
					}
					break;
				case IResourceDelta.REMOVED:
					if (resourceDelta.getResource().getType() == IResource.FILE
							&& resourceDelta.getResource().getFileExtension().equals(
									INFO_FILE_EXTENSION)) {
						this.filesTodeleteFromIndex.add((IFile) resourceDelta.getResource());
					}
					break;
				case IResourceDelta.REPLACED:
					if (resourceDelta.getResource().getType() == IResource.FILE
							&& resourceDelta.getResource().getFileExtension().equals(
									INFO_FILE_EXTENSION)) {

						this.filesTodeleteFromIndex.add((IFile) resourceDelta.getResource());
						this.filesToAddToIndex.add((IFile) resourceDelta.getResource());

					}
					break;
				}
			}
			visitRecursively(resourceDelta);
		}
	}

}
