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

package org.remus.infomngmnt.image.importt;

import org.remus.infomngmnt.common.core.beans.PropertyChangeObject;


/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class ImportImageObject extends PropertyChangeObject {

	public static final String PROP_CATEGORY = "category"; //$NON-NLS-1$

	public static final String PROP_DIRECTORY = "directory"; //$NON-NLS-1$

	public static final String PROP_FOLDER_STRUCTURE = "createFolderStructure"; //$NON-NLS-1$

	private String category;

	private String directory;

	private boolean createFolderStructure;

	/**
	 * @return the category
	 */
	public String getCategory() {
		return this.category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(final String category) {
		this.category = category;
		firePropertyChange(PROP_CATEGORY, null, category);
	}

	/**
	 * @return the directory
	 */
	public String getDirectory() {
		return this.directory;
	}

	/**
	 * @param directory
	 *            the directory to set
	 */
	public void setDirectory(final String directory) {
		this.directory = directory;
		firePropertyChange(PROP_DIRECTORY, null, directory);
	}

	/**
	 * @return the createFolderStructure
	 */
	public boolean isCreateFolderStructure() {
		return this.createFolderStructure;
	}

	/**
	 * @param createFolderStructure
	 *            the createFolderStructure to set
	 */
	public void setCreateFolderStructure(final boolean createFolderStructure) {
		this.createFolderStructure = createFolderStructure;
		firePropertyChange(PROP_FOLDER_STRUCTURE, null, Boolean.valueOf(createFolderStructure));
	}

}
