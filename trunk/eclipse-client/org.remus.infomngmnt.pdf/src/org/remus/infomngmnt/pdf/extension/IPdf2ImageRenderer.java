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

package org.remus.infomngmnt.pdf.extension;

import java.awt.Dimension;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public interface IPdf2ImageRenderer {

	/**
	 * Extracts all pdf to images.
	 * 
	 * @param outputFolder
	 *            the folder where all images should be stored
	 * @param pdfFile
	 *            the input pdf
	 * @param monitor
	 *            a monitor
	 * @return an ordered list with the filenames (no paths, just filenames)
	 */
	List<ImageInformation> convert(IFolder outputFolder, IFile pdfFile, IProgressMonitor monitor);

	Dimension firstSlid(IFile pdfFile);

}
