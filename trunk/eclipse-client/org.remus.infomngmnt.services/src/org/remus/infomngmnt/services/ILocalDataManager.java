/*******************************************************************************
 * Copyright (c) 2010 Tom Seidel, Remus Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *
 * Contributors:
 *     Tom Seidel - initial API and implementation
 *******************************************************************************/

package org.remus.infomngmnt.services;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.ApplicationRoot;
import org.remus.infomngmnt.Category;
import org.remus.infomngmnt.InformationUnitListItem;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface ILocalDataManager {

	void removeFromModel(final IProject project) throws CoreException;

	void addToModel(final IProject project);

	ApplicationRoot getModel();

	void addListenerToCategory(final Category category);

	Map<String, InformationUnitListItem> getAllItems(final IProgressMonitor monitor);

	InformationUnitListItem getItemById(final String id, final IProgressMonitor monitor);
}
