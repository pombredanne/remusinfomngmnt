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

package org.remus.infomngmnt.ui.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class InformationEditorCreationFactory implements IElementFactory {

	public static final String FACTORY_ID = "org.remus.infomngmnt.infofilefactory"; //$NON-NLS-1$

	/**
	 * Tag for the IFile.fullPath of the file resource.
	 */
	private static final String TAG_PATH = "path"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IElementFactory#createElement(org.eclipse.ui.IMemento)
	 */
	public IAdaptable createElement(final IMemento memento) {
		String fileName = memento.getString(TAG_PATH);
		if (fileName == null) {
			return null;
		}

		// Get a handle to the IFile...which can be a handle
		// to a resource that does not exist in workspace
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(fileName));
		if (file != null && file.isAccessible()) {
			return new InformationEditorInput(file);
		}
		return null;
	}

}
