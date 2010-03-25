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

package org.remus.infomngmnt.pdf.renderer;

import java.awt.Dimension;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;

import org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer;
import org.remus.infomngmnt.pdf.extension.ImageInformation;

/**
 * @author Tom Seidel <tom.seidel@remus-software.org>
 */
public class NoImageRenderer implements IPdf2ImageRenderer {

	/**
	 * 
	 */
	public NoImageRenderer() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer#convert(org.eclipse
	 * .core.resources.IFolder, org.eclipse.core.resources.IFile,
	 * org.eclipse.core.runtime.IProgressMonitor)
	 */
	public List<ImageInformation> convert(final IFolder outputFolder, final IFile pdfFile,
			final IProgressMonitor monitor) {
		return Collections.<ImageInformation> emptyList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.remus.infomngmnt.pdf.extension.IPdf2ImageRenderer#firstSlid(org.eclipse
	 * .core.resources.IFile)
	 */
	public Dimension firstSlid(final IFile pdfFile) {
		return new Dimension(500, 500);
	}

}
