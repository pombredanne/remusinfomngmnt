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

package org.remus.infomngmnt.core.internal.adapters;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdapterFactory;

import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.SynchronizationMetadata;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class CategoryAdapterFactory implements IAdapterFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object,
	 * java.lang.Class)
	 */
	public Object getAdapter(final Object adaptableObject, final Class adapterType) {
		if (adaptableObject instanceof Category) {
			if (((Category) adaptableObject).eContainer() == null
					&& (adapterType == IResource.class || adapterType == IProject.class)) {
				try {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
							((Category) adaptableObject).getLabel());
					if (project.exists()) {
						return project;
					}
				} catch (Exception e) {
					return null;
				}
			} else if (adapterType == SynchronizationMetadata.class
					&& ((Category) adaptableObject).getSynchronizationMetaData() != null) {
				return ((Category) adaptableObject).getSynchronizationMetaData();
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] { IProject.class, IResource.class };
	}

}
